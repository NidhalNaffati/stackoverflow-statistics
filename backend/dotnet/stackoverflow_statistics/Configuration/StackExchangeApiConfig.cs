namespace stackoverflow_statistics.Configuration;

public class StackExchangeApiConfig
{
    public string ApiKey { get; set; }
    public string BaseUrl { get; set; }
    public List<string> ProgrammingLanguages { get; set; }
}