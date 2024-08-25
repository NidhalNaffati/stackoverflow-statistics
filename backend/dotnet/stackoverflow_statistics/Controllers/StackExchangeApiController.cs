using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using stackoverflow_statistics.Configuration;
using stackoverflow_statistics.Services;


namespace stackoverflow_statistics.Controllers
{
    [ApiController]
    [Route("api/v1/questions")]
    public class StackExchangeApiController : ControllerBase
    {
        private readonly StackExchangeApiService _stackExchangeApiService;
        private readonly List<string> _programmingLanguages;

        public StackExchangeApiController(
            StackExchangeApiService stackExchangeApiService,
            IOptions<StackExchangeApiConfig> stackExchangeApiConfig)
        {
            _stackExchangeApiService = stackExchangeApiService;
            _programmingLanguages = stackExchangeApiConfig.Value.ProgrammingLanguages;
        }

        private static readonly long OneDayAgoInSeconds = DateTimeOffset.UtcNow.AddDays(-1).ToUnixTimeSeconds();
        private static readonly long OneWeekAgoInSeconds = DateTimeOffset.UtcNow.AddDays(-7).ToUnixTimeSeconds();

        [HttpGet("this-week")]
        public async Task<IActionResult> StoreThisWeekQuestions()
        {
            var tasks = _programmingLanguages
                .Select(language => Task.Run(() =>
                    _stackExchangeApiService.StoreAllQuestionsRelatedToAsync(language, OneWeekAgoInSeconds)))
                .ToArray();

            await Task.WhenAll(tasks);

            return Ok("This week's questions up to date");
        }

        [HttpGet("this-day")]
        public async Task<IActionResult> StoreThisDayQuestions()
        {
            var tasks = _programmingLanguages
                .Select(language => Task.Run(
                    () => _stackExchangeApiService.StoreAllQuestionsRelatedToAsync(language, OneDayAgoInSeconds))
                )
                .ToArray();

            await Task.WhenAll(tasks);

            return Ok("Today's questions up to date");
        }
    }
}