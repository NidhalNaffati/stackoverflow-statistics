package nidhal.stackoverflowstatus.controller;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
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

    private List<String> programmingLanguages;

    @PostConstruct
    private void init() {
        programmingLanguages = stackExchangeApiConfig.getProgrammingLanguages();
    }

    @GetMapping("/this-week")
    public String storeThisWeekQuestions() {

        long oneWeekAgoInSeconds = Instant.now().minus(Duration.ofDays(7)).getEpochSecond();

        int maxThreads = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);

        // Since our application store only the questions from the last week, we need to delete the old ones
        questionDao.deleteAllByCreationDateLessThan(oneWeekAgoInSeconds);

        for (String language : programmingLanguages) {
            CompletableFuture.runAsync(() -> stackExchangeApiService.storeAllQuestionRelatedTo(language, oneWeekAgoInSeconds), executorService);
        }

        executorService.shutdown();

        return "This week's questions up to date";
    }


    @GetMapping("/this-day")
    public String storeThisDayQuestions() {

        long oneDayAgoInSeconds = Instant.now().minus(Duration.ofDays(1)).getEpochSecond();
        long oneWeekAgoInSeconds = Instant.now().minus(Duration.ofDays(7)).getEpochSecond();

        int maxThreads = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);

        // Since our application store only the questions from the last week, we need to delete the old ones
        questionDao.deleteAllByCreationDateLessThan(oneWeekAgoInSeconds);

        // store all questions related to each programming language in a separate thread
        for (String language : programmingLanguages) {
            CompletableFuture.runAsync(() -> stackExchangeApiService.storeAllQuestionRelatedTo(language, oneDayAgoInSeconds), executorService);
        }

        executorService.shutdown();

        return "Today's questions up to date";
    }
}
