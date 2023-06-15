package nidhal.stackoverflowstatus.service;

import lombok.extern.slf4j.Slf4j;
import nidhal.stackoverflowstatus.configuration.StackExchangeApiConfig;
import nidhal.stackoverflowstatus.dto.StackExchangeResponse;
import nidhal.stackoverflowstatus.entity.Question;
import nidhal.stackoverflowstatus.repository.QuestionRepository;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class StackExchangeApiService {

    private final RestTemplate restTemplate;
    private final QuestionRepository questionRepository;
    private final StackExchangeApiConfig stackExchangeApiConfig;


    public StackExchangeApiService(
            QuestionRepository questionRepository,
            StackExchangeApiConfig stackExchangeApiConfig
    ) {
        // Create an instance of HttpComponentsClientHttpRequestFactory
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory(
                        HttpClientBuilder
                                .create()
                                .build()
                );

        // Create a new RestTemplate instance with the clientHttpRequestFactory
        this.restTemplate = new RestTemplate(clientHttpRequestFactory);
        this.stackExchangeApiConfig = stackExchangeApiConfig;
        this.questionRepository = questionRepository;
    }

    public void storeAllQuestionRelatedTo(String programingLanguage) {
        boolean hasMore = true;
        int page = 1;
        int updatedQuestions = 0;
        int newQuestions = 0;

        // Calculate the timestamp for one year ago
        long oneWeekAgoInSeconds = Instant.now().minus(Duration.ofDays(7)).getEpochSecond();

        // Build the API URL
        String baseUrl = stackExchangeApiConfig.getBaseUrl() + "/questions"
                         + "?fromdate=" + oneWeekAgoInSeconds
                         + "&order=desc"
                         + "&sort=creation"
                         + "&tagged=" + programingLanguage
                         + "&site=stackoverflow"
                         + "&key=" + stackExchangeApiConfig.getApiKey()
                         + "&page=";

        while (hasMore) {
            // Construct the full URL for the current page
            String url = baseUrl + page;

            // Print the URL to the console for debugging purposes
            log.info("extracting data from the following url: {}", url);

            try {
                // Make the API request
                ResponseEntity<StackExchangeResponse> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        StackExchangeResponse.class
                );
                // Check the status code of the response
                if (response.getStatusCode() == HttpStatus.OK) {
                    // Get the response body
                    StackExchangeResponse stackExchangeResponse = response.getBody();

                    if (stackExchangeResponse != null) {
                        List<Question> questionList = stackExchangeResponse.getItems();
                        for (Question question : questionList) {
                            // Check if the question is already stored in the database
                            if (questionRepository.existsById(question.getQuestion_id())) {
                                // Update the question in the database
                                questionRepository.save(question);
                                updatedQuestions++;
                            } else {
                                // Save the question in the database
                                questionRepository.save(question);
                                newQuestions++;
                            }
                        }

                        // check if there is more data to fetch
                        if (stackExchangeResponse.has_more) {
                            page++;
                        } else {
                            hasMore = false;
                        }
                    }
                } else {
                    // Print an error message if the response status code is not OK
                    log.error("There is a problem with the Stack Exchange server: {}", response.getStatusCode());
                    log.error("response body: {}", response.getBody());
                }
            } catch (RestClientException e) {
                e.printStackTrace();
                // Print an error message if there is an exception thrown by the restTemplate object
                log.error("There was an error communicating with the Stack Exchange API: {} ", e.getLocalizedMessage());
                hasMore = false;
            }
        }
        log.info("Stored all questions related to {} there is {} new question & {} updated questions", programingLanguage, newQuestions, updatedQuestions);
    }
}
