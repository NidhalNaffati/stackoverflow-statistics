using Microsoft.Extensions.Options;
using Newtonsoft.Json;
using stackoverflow_statistics.Configuration;
using stackoverflow_statistics.Data;
using stackoverflow_statistics.Dto;

namespace stackoverflow_statistics.Services
{
    public class StackExchangeApiService
    {
        private readonly HttpClient _httpClient;
        private readonly QuestionRepository _questionRepository;
        private readonly StackExchangeApiConfig _stackExchangeApiConfig;
        private readonly ILogger<StackExchangeApiService> _logger;
        private int _numberOfRequestsMade = 0;

        public StackExchangeApiService(
            QuestionRepository questionRepository,
            IOptions<StackExchangeApiConfig> stackExchangeApiConfig,
            HttpClient httpClient,
            ILogger<StackExchangeApiService> logger
        )
        {
            _httpClient = httpClient;
            _stackExchangeApiConfig = stackExchangeApiConfig.Value;
            _questionRepository = questionRepository;
            _logger = logger;
            _httpClient.DefaultRequestHeaders.Add("User-Agent", "stackoverflow_statistics");
        }

        public async Task StoreAllQuestionsRelatedToAsync(string programmingLanguage, long fromDate)
        {
            bool hasMore = true;
            int page = 1;
            int updatedQuestions = 0;
            int newQuestions = 0;

            // Build the API URL
            string baseUrl = $"{_stackExchangeApiConfig.BaseUrl}/questions" +
                             $"?fromdate={fromDate}" +
                             $"&order=desc" +
                             $"&sort=creation" +
                             $"&tagged={programmingLanguage}" +
                             $"&site=stackoverflow" +
                             $"&key={_stackExchangeApiConfig.ApiKey}" +
                             $"&page=";

            while (hasMore)
            {
                var url = baseUrl + page;
                _logger.LogInformation("Fetching data from URL: {Url}", url);

                try
                {
                    // Make the API request
                    var response = await _httpClient.GetAsync(url);
                    _numberOfRequestsMade++;
                    _logger.LogInformation("Number of requests made: {RequestCount}", _numberOfRequestsMade);

                    if (response.IsSuccessStatusCode)
                    {
                        _logger.LogDebug("API request Status Code: {StatusCode}", response.StatusCode);
                        // Process the response
                        var content = await response.Content.ReadAsStringAsync();
                        var stackExchangeResponse = JsonConvert.DeserializeObject<StackExchangeResponse>(content);

                        if (stackExchangeResponse != null)
                        {
                            foreach (var question in stackExchangeResponse.Items)
                            {
                                if (await _questionRepository.ExistsAsync(question.QuestionId))
                                {
                                    _logger.LogInformation("Question with ID {QuestionId} already exists. Updating...",
                                        question.QuestionId);
                                    await _questionRepository.UpdateAsync(question);
                                    updatedQuestions++;
                                }
                                else
                                {
                                    _logger.LogInformation("Adding new question with ID {QuestionId}",
                                        question.QuestionId);
                                    await _questionRepository.AddAsync(question);
                                    newQuestions++;
                                }
                            }

                            // Check if there are more pages
                            hasMore = stackExchangeResponse.HasMore;
                            if (hasMore)
                            {
                                page++;
                            }
                        }
                    }
                    else
                    {
                        // Log an error message if the response status code is not OK
                        _logger.LogError("API request failed with status code: {StatusCode}", response.StatusCode);
                        hasMore = false;
                    }

                    // Sleep for 1 second after every 30 requests to avoid hitting the API rate limit
                    if (_numberOfRequestsMade % 30 == 0)
                    {
                        _logger.LogInformation("Pausing for 1 second to comply with rate limit.");
                        await Task.Delay(1000);
                    }
                }
                catch (HttpRequestException httpEx)
                {
                    _logger.LogError("HTTP request error: {Message}", httpEx.Message);
                    hasMore = false;
                }
                catch (Exception ex)
                {
                    _logger.LogError("An unexpected error occurred: {Message}", ex.Message);
                    hasMore = false;
                }
            }

            _logger.LogInformation(
                "Completed processing. Stored {NewQuestions} new questions and updated {UpdatedQuestions} questions related to {ProgrammingLanguage}.",
                newQuestions, updatedQuestions, programmingLanguage);
        }
    }
}