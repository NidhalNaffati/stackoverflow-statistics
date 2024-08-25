using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using stackoverflow_statistics.Configuration;

namespace stackoverflow_statistics.Controllers;

[ApiController]
[Route("[controller]")]
public class StackExchangeController : ControllerBase
{
    private readonly StackExchangeApiConfig _config;

    public StackExchangeController(IOptions<StackExchangeApiConfig> config)
    {
        _config = config.Value;
    }

    [HttpGet("config")]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public IActionResult Get()
    {
        // Use _config.ApiKey, _config.BaseUrl, _config.ProgrammingLanguages
        return Ok(_config);
    }
}
