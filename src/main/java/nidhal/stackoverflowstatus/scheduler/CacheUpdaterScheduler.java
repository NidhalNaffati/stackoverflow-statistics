package nidhal.stackoverflowstatus.scheduler;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nidhal.stackoverflowstatus.configuration.StackExchangeApiConfig;
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
    private final StackExchangeApiConfig stackExchangeApiConfig;
    private List<String> programmingLanguages;

    @PostConstruct
    private void init() {
        programmingLanguages = stackExchangeApiConfig.getProgrammingLanguages();
    }
    private final int fixedDelay = 5 * 60 * 1000; // 10 minutes


    @Scheduled(fixedDelay = fixedDelay)
    public void updateCachedNumberOfQuestionsPerDay() {
        log.info("START UPDATING CACHE DATA ...");

        for (String language : programmingLanguages) {
            log.info("UPDATING DATA FOR : {} ", language);
            updateCachesRelatedToProgrammingLanguage(language);
        }

        // update the total number of questions cache
        updateCachedTotalNumberOfQuestions();
        // update the answered unanswered questions for all languages cache
        updateCachedAnsweredUnansweredQuestions();
        // update the number of questions asked today cache
        updateCachedTotalNumberOfQuestionsAskedToday();
        // update the total number of questions per day cache
        updateCachedTotalNumberOfQuestionsPerDay();
        // update the total number of answered questions
        updateCachedTotalNumberOfAnsweredQuestions();
        // update the total number of unanswered questions
        updateCachedTotalNumberOfUnansweredQuestions();
        // update the total number of closed questions
        updateCachedTotalNumberOfClosedQuestions();
        // update the total number of open questions
        updateCachedTotalNumberOfOpenQuestions();
        // update the total number of open and closed questions
        updateCachedTotalOpenAndClosedQuestions();

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
        updateCachedClosedQuestions(programmingLanguage);
        updateCachedOpenQuestions(programmingLanguage);
        updateCachedOpenAndClosedQuestions(programmingLanguage);
    }

    // ------------------------ TOTAL NUMBER OF QUESTIONS ------------------------ //
    @CachePut(value = "totalNumberOfQuestionsCache")
    public void updateCachedTotalNumberOfQuestions() {
        questionService.getTotalNumberOfQuestions();
    }

    @CachePut(value = "numberOfQuestionsCache", key = "#programmingLanguage")
    public void updateCachedNumberOfQuestions(String programmingLanguage) {
        questionService.getNumberOfQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @CachePut(value = "totalNumberOfQuestionsAskedTodayCache")
    public void updateCachedTotalNumberOfQuestionsAskedToday() {
        questionService.getTotalNumberOfQuestionsAskedToday();
    }

    @CachePut(value = "numberOfQuestionsAskedTodayCache", key = "#programmingLanguage")
    public void updateCachedQuestionsAskedToday(String programmingLanguage) {
        questionService.getNumberOfQuestionsAskedTodayPerProgrammingLanguage(programmingLanguage);
    }

    @CachePut(value = "totalNumberOfQuestionsPerDayCache")
    public void updateCachedTotalNumberOfQuestionsPerDay() {
        questionService.getTotalNumberOfQuestionsPerDay();
    }

    @CachePut(value = "numberOfQuestionsPerDayCache", key = "#programmingLanguage")
    public void updateCachedNumberOfQuestionsPerDay(String programmingLanguage) {
        questionService.getNumberOfQuestionsPerDayForProgrammingLanguage(programmingLanguage);
    }

    // ------------------------ ANSWERED & UNANSWERED QUESTIONS ------------------------ //
    @CachePut(value = "totalNumberOfAnsweredQuestionsCache")
    public void updateCachedTotalNumberOfAnsweredQuestions() {
        questionService.getTotalNumberOfAnsweredQuestions();
    }

    @CachePut(value = "numberOfAnsweredQuestionsCache", key = "#programmingLanguage")
    public void updateCachedAnsweredQuestions(String programmingLanguage) {
        questionService.getNumberOfAnsweredQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @CachePut(value = "totalNumberOfUnansweredQuestionsCache")
    public void updateCachedTotalNumberOfUnansweredQuestions() {
        questionService.getTotalNumberOfUnansweredQuestions();
    }

    @CachePut(value = "numberOfUnansweredQuestionsCache", key = "#programmingLanguage")
    public void updateCachedUnansweredQuestions(String programmingLanguage) {
        questionService.getNumberOfUnansweredQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @CachePut(value = "totalNumberOfAnsweredAndUnansweredQuestionsCache")
    public void updateCachedAnsweredUnansweredQuestions() {
        questionService.getTotalNumberOfQuestionsAnsweredAndUnanswered();
    }

    @CachePut(value = "numberOfAnsweredAndUnansweredQuestionsCache", key = "#programmingLanguage")
    public void updateCachedAnsweredAndUnansweredQuestions(String programmingLanguage) {
        questionService.getNumberOfAnsweredAndUnansweredQuestionsForProgrammingLanguage(programmingLanguage);
    }

    // ------------------------ OPEN & CLOSED QUESTIONS ------------------------ //
    @CachePut(value = "totalNumberOfClosedQuestionsCache")
    public void updateCachedTotalNumberOfClosedQuestions() {
        questionService.getTotalNumberOfClosedQuestions();
    }

    @CachePut(value = "numberOfClosedQuestionsCache", key = "#programmingLanguage")
    public void updateCachedClosedQuestions(String programmingLanguage) {
        questionService.getNumberOfClosedQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @CachePut(value = "totalNumberOfOpenQuestionsCache")
    public void updateCachedTotalNumberOfOpenQuestions() {
        questionService.getTotalNumberOfOpenQuestions();
    }

    @CachePut(value = "numberOfOpenQuestionsCache", key = "#programmingLanguage")
    public void updateCachedOpenQuestions(String programmingLanguage) {
        questionService.getNumberOfOpenQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @CachePut(value = "totalNumberOfOpenAndClosedQuestionsCache")
    public void updateCachedTotalOpenAndClosedQuestions() {
        questionService.getNumberOfOpenAndClosedQuestions();
    }

    @CachePut(value = "numberOfOpenAndClosedQuestionsCache", key = "#programmingLanguage")
    public void updateCachedOpenAndClosedQuestions(String programmingLanguage) {
        questionService.getNumberOfOpenAndClosedQuestionsForProgrammingLanguage(programmingLanguage);
    }

}