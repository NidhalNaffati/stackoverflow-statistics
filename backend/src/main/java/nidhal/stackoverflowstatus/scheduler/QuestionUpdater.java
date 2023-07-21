package nidhal.stackoverflowstatus.scheduler;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import nidhal.stackoverflowstatus.configuration.StackExchangeApiConfig;
import nidhal.stackoverflowstatus.service.StackExchangeApiService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.Instant;
import java.util.List;


@Configuration
@EnableScheduling
@AllArgsConstructor
public class QuestionUpdater {

    StackExchangeApiService stackExchangeApiService;
    private final StackExchangeApiConfig stackExchangeApiConfig;
    private List<String> programmingLanguages;

    @PostConstruct
    private void init() {
        programmingLanguages = stackExchangeApiConfig.getProgrammingLanguages();
    }

    private final int fixedDelayForTodayQuestions = 655; // ≈ 11 minutes
    private final int fixedDelayForWeekQuestions = 2 * 60 * 60 * 1000; // 2 hours

    @Scheduled(fixedDelay = fixedDelayForTodayQuestions)
    public void updateTodayQuestions() {
        long onDayAgoInSeconds = Instant.now().minus(Duration.ofDays(1)).getEpochSecond();
        for (String language : programmingLanguages)
            stackExchangeApiService.storeAllQuestionRelatedTo(language, onDayAgoInSeconds);
    }

    @Scheduled(fixedDelay = fixedDelayForWeekQuestions, initialDelay = fixedDelayForWeekQuestions)
    public void updateWeekQuestions() {
        long oneWeekAgoInSeconds = Instant.now().minus(Duration.ofDays(7)).getEpochSecond();
        for (String language : programmingLanguages)
            stackExchangeApiService.storeAllQuestionRelatedTo(language, oneWeekAgoInSeconds);
    }
}
