package nidhal.stackoverflowstatus.scheduler;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import nidhal.stackoverflowstatus.configuration.CacheConfig;
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
public class QuestionUpdaterScheduler {

    private final CacheConfig cacheConfig;
    private final StackExchangeApiService stackExchangeApiService;
    private final StackExchangeApiConfig stackExchangeApiConfig;
    private List<String> programmingLanguages;

    @PostConstruct
    private void init() {
        programmingLanguages = stackExchangeApiConfig.getProgrammingLanguages();
    }

    private static final int FIXED_DELAY_FOR_TODAY_QUESTIONS = 655 * 1000; // ≈ 11 minutes
    private static final int FIXED_DELAY_FOR_WEEK_QUESTIONS = 2 * 60 * 60 * 1000; // 2 hours

    @Scheduled(fixedDelay = FIXED_DELAY_FOR_TODAY_QUESTIONS, initialDelay = FIXED_DELAY_FOR_TODAY_QUESTIONS)
    public void updateTodayQuestions() {
        // clear the cache before updating the questions
        cacheConfig.clearCache();
        // get the date of one day ago
        long onDayAgoInSeconds = Instant.now().minus(Duration.ofDays(1)).getEpochSecond();
        // store all questions related to the programming languages from the last day
        for (String language : programmingLanguages)
            stackExchangeApiService.storeAllQuestionRelatedTo(language, onDayAgoInSeconds);
    }

    @Scheduled(fixedDelay = FIXED_DELAY_FOR_WEEK_QUESTIONS, initialDelay = FIXED_DELAY_FOR_WEEK_QUESTIONS)
    public void updateWeekQuestions() {
        // clear the cache before updating the questions
        cacheConfig.clearCache();
        // get the date of one week ago
        long oneWeekAgoInSeconds = Instant.now().minus(Duration.ofDays(7)).getEpochSecond();
        // store all questions related to the programming languages from the last wee
        for (String language : programmingLanguages)
            stackExchangeApiService.storeAllQuestionRelatedTo(language, oneWeekAgoInSeconds);
    }
}
