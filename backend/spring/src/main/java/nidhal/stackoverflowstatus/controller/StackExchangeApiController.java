package nidhal.stackoverflowstatus.controller;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import nidhal.stackoverflowstatus.configuration.CacheConfig;
import nidhal.stackoverflowstatus.configuration.StackExchangeApiConfig;
import nidhal.stackoverflowstatus.dao.QuestionDao;
import nidhal.stackoverflowstatus.service.StackExchangeApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/questions")
public class StackExchangeApiController {

    private final StackExchangeApiService stackExchangeApiService;
    private final StackExchangeApiConfig stackExchangeApiConfig;
    private final QuestionDao questionDao;
    private final CacheConfig cacheConfig;

    private List<String> programmingLanguages;

    @PostConstruct
    private void init() {
        programmingLanguages = stackExchangeApiConfig.getProgrammingLanguages();
    }

    private static final long ONE_DAY_AGO_IN_SECONDS = Instant.now().minus(Duration.ofDays(1)).getEpochSecond();
    private static final long ONE_WEEK_AGO_IN_SECONDS = Instant.now().minus(Duration.ofDays(7)).getEpochSecond();
    private static final int MAX_THREADS = 10;

    @GetMapping("/this-week")
    public String storeThisWeekQuestions() {

        // Since our application store only the questions from the last week, we need to delete the old ones
        questionDao.deleteAllByCreationDateLessThan(ONE_WEEK_AGO_IN_SECONDS);

        // clear the cache before updating the questions
        cacheConfig.clearCache();

        try (ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS)) {
            // store all questions related to each programming language in a separate thread
            for (String language : programmingLanguages) {
                CompletableFuture.runAsync(() -> stackExchangeApiService.storeAllQuestionRelatedTo(language, ONE_WEEK_AGO_IN_SECONDS), executorService);
            }
        }

        return "This week's questions up to date";
    }


    @GetMapping("/this-day")
    public String storeThisDayQuestions() {

        // Since our application store only the questions from the last week, we need to delete the old ones
        questionDao.deleteAllByCreationDateLessThan(ONE_WEEK_AGO_IN_SECONDS);

        // clear the cache before updating the questions
        cacheConfig.clearCache();

        try (ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS)) {
            // store all questions related to each programming language in a separate thread
            for (String language : programmingLanguages) {
                CompletableFuture.runAsync(() -> stackExchangeApiService.storeAllQuestionRelatedTo(language, ONE_DAY_AGO_IN_SECONDS), executorService);
            }
        }
        
        return "Today's questions up to date";
    }
}
