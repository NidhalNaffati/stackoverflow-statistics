package nidhal.stackoverflowstatus.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nidhal.stackoverflowstatus.service.QuestionService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
@AllArgsConstructor
public class CacheUpdaterScheduler {

    private final QuestionService questionService;

    private final List<String> programmingLanguages = List.of("java", "python", "javascript", "go", "kotlin", "c++", "c#", "ruby", "php", "swift");

    private final int fixedDelay = 5 * 60 * 1000; // 10 minutes


    @Scheduled(fixedDelay = fixedDelay)
    public void updateCachedNumberOfQuestionsPerDay() {
        log.info("Updating cached questions per day...");

        for (String language : programmingLanguages) {
            log.info("UPDATING DATA FOR : {} ", language);
            updateCachesRelatedToProgrammingLanguage(language);
        }

        // update the total number of questions cache
        updateCachedTotalNumberOfQuestions();
        // update the answered unanswered questions for all languages cache
        updateCachedAnsweredUnansweredQuestionsForAllLanguages();
        // update the number of questions asked today cache
        updateCachedTotalNumberOfQuestionsAskedToday();
        // update the total number of questions per day cache
        updateCachedTotalNumberOfQuestionsPerDay();

        log.info("ALL CACHED DATA UPDATED.");
    }

    public void updateCachesRelatedToProgrammingLanguage(String programmingLanguage) {
        // call all the methods that update the caches related to the given programming language
        updateCachedNumberOfQuestionsPerDay(programmingLanguage);
        updateCachedAnsweredQuestions(programmingLanguage);
        updateCachedUnansweredQuestions(programmingLanguage);
        updateCachedAnsweredAndUnansweredQuestions(programmingLanguage);
        updateCachedQuestionsAskedToday(programmingLanguage);
        updateCachedNumberOfQuestions(programmingLanguage);
    }

    @CachePut(value = "totalNumberOfQuestionsCache")
    public void updateCachedTotalNumberOfQuestions() {
        log.info("Updating total number of questions cache...");
        questionService.getTotalNumberOfQuestions();
    }

    @CachePut(value = "numberOfQuestionsCache", key = "#programmingLanguage")
    public void updateCachedNumberOfQuestions(String programmingLanguage) {
        log.info("Updating cached number of questions for {}...", programmingLanguage);
        questionService.getNumberOfQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @CachePut(value = "totalNumberOfQuestionsAskedTodayCache")
    public void updateCachedTotalNumberOfQuestionsAskedToday() {
        log.info("Updating cached total number of questions asked today...");
        questionService.getTotalNumberOfQuestionsAskedToday();
    }

    @CachePut(value = "numberOfQuestionsAskedTodayCache", key = "#programmingLanguage")
    public void updateCachedQuestionsAskedToday(String programmingLanguage) {
        log.info("Updating cached questions asked today for {}...", programmingLanguage);
        questionService.getNumberOfQuestionsAskedTodayPerProgrammingLanguage(programmingLanguage);
    }

    @CachePut(value = "totalNumberOfQuestionsPerDayCache")
    public void updateCachedTotalNumberOfQuestionsPerDay() {
        log.info("Updating cached total number of questions per day...");
        questionService.getTotalNumberOfQuestionsPerDay();
    }

    @CachePut(value = "numberOfQuestionsPerDayCache", key = "#programmingLanguage")
    public void updateCachedNumberOfQuestionsPerDay(String programmingLanguage) {
        log.info("Updating cached questions per day for {}...", programmingLanguage);
        questionService.getNumberOfQuestionsPerDay(programmingLanguage);
    }

    @CachePut(value = "totalNumberOfAnsweredAndUnansweredQuestionsForAllLanguagesCache")
    public void updateCachedAnsweredUnansweredQuestionsForAllLanguages() {
        log.info("Updating cached answered unanswered questions for all languages...");
        questionService.getNumberOfAnsweredAndUnansweredQuestionsForAllQuestions();
    }

    @CachePut(value = "numberOfAnsweredAndUnansweredQuestionsCache", key = "#programmingLanguage")
    public void updateCachedAnsweredAndUnansweredQuestions(String programmingLanguage) {
        log.info("Updating cached answered unanswered questions for {}...", programmingLanguage);
        questionService.getNumberOfAnsweredAndUnansweredQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @CachePut(value = "numberOfAnsweredQuestionsCache", key = "#programmingLanguage")
    public void updateCachedAnsweredQuestions(String programmingLanguage) {
        log.info("Updating cached answered questions for {}...", programmingLanguage);
        questionService.getNumberOfAnsweredQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @CachePut(value = "numberOfUnansweredQuestionsCache", key = "#programmingLanguage")
    public void updateCachedUnansweredQuestions(String programmingLanguage) {
        log.info("Updating cached unanswered questions for {}...", programmingLanguage);
        questionService.getNumberOfUnansweredQuestionsForProgrammingLanguage(programmingLanguage);
    }

}