package nidhal.stackoverflowstatus.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nidhal.stackoverflowstatus.service.QuestionService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
@EnableScheduling
@AllArgsConstructor
public class CacheUpdaterScheduler {

    private final QuestionService questionService;

    private final List<String> programmingLanguages = List.of("java", "python", "javascript", "go", "kotlin", "c++", "c#", "ruby", "php", "swift");

    private final int fixedDelay = 2 * 60 * 1000; // 2 minutes


    @Scheduled(fixedDelay = fixedDelay)
    public void updateCachedQuestionsPerDay() {
        log.info("Updating cached questions per day...");

        // initialize a thread pool with a maximum number of threads equal to the number of programming languages
        int maxThreads = programmingLanguages.size();
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);

        // for each programming language, update the cache in a separate thread
        for (String language : programmingLanguages) {
            CompletableFuture.runAsync(
                    () -> updateCachesRelatedToProgrammingLanguage(language),
                    executorService
            );
        }

        // update the total number of questions cache
        updateCachedTotalNumberOfQuestions();
        // update the answered unanswered questions for all languages cache
        updateCachedAnsweredUnansweredQuestionsForAllLanguages();

        // shutdown the thread pool
        executorService.shutdown();

        log.info("All cached data updated");
    }


    public void updateCachesRelatedToProgrammingLanguage(String programmingLanguage) {
        // call all the methods that update the caches related to the given programming language
        updateCachedQuestionsPerDay(programmingLanguage);
        updateCachedAnsweredUnansweredQuestions(programmingLanguage);
        updateCachedAnsweredQuestions(programmingLanguage);
        updateCachedUnansweredQuestions(programmingLanguage);
    }

    @CachePut(value = "totalNumberOfQuestionsCache")
    public void updateCachedTotalNumberOfQuestions() {
        log.info("Updating total number of questions cache...");
        questionService.getTotalNumberOfQuestions();
    }

    @CachePut(value = "answeredUnansweredQuestionsForAllLanguagesCache")
    public void updateCachedAnsweredUnansweredQuestionsForAllLanguages() {
        log.info("Updating cached answered unanswered questions for all languages...");
        questionService.getNumberOfAnsweredAndUnansweredQuestionsForAllQuestions();
    }

    @CachePut(value = "questionsPerDayCache", key = "#programmingLanguage")
    public void updateCachedQuestionsPerDay(String programmingLanguage) {
        log.info("Updating cached questions per day for {}...", programmingLanguage);
        questionService.getNumberOfQuestionsPerDay(programmingLanguage);
    }

    @CachePut(value = "answeredUnansweredQuestionsCache", key = "#programmingLanguage")
    public void updateCachedAnsweredUnansweredQuestions(String programmingLanguage) {
        log.info("Updating cached answered unanswered questions for {}...", programmingLanguage);
        questionService.getNumberOfAnsweredAndUnansweredQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @CachePut(value = "answeredQuestionsCache", key = "#programmingLanguage")
    public void updateCachedAnsweredQuestions(String programmingLanguage) {
        log.info("Updating cached answered questions for {}...", programmingLanguage);
        questionService.getNumberOfAnsweredQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @CachePut(value = "unansweredQuestionsCache", key = "#programmingLanguage")
    public void updateCachedUnansweredQuestions(String programmingLanguage) {
        log.info("Updating cached unanswered questions for {}...", programmingLanguage);
        questionService.getNumberOfUnansweredQuestionsForProgrammingLanguage(programmingLanguage);
    }

}
