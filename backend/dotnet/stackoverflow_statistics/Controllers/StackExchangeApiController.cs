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
            foreach (var language in _programmingLanguages)
            {
                await _stackExchangeApiService.StoreAllQuestionsRelatedToAsync(language, OneWeekAgoInSeconds);
            }

            return Ok("This week's questions up to date");
        }

        [HttpGet("this-day")]
        public async Task<IActionResult> StoreThisDayQuestions()
        {
            foreach (var language in _programmingLanguages)
            {
                await _stackExchangeApiService.StoreAllQuestionsRelatedToAsync(language, OneDayAgoInSeconds);
            }

            return Ok("Today's questions up to date");
        }
    }
}