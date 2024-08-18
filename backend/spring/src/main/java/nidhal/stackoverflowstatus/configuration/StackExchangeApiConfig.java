package nidhal.stackoverflowstatus.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "stack-exchange")
public class StackExchangeApiConfig {

    private String apiKey;

    private String baseUrl;

    private List<String> programmingLanguages;
}