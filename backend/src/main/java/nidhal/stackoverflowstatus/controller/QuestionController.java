package nidhal.stackoverflowstatus.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nidhal.stackoverflowstatus.entity.Question;
import nidhal.stackoverflowstatus.service.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // ------------------------ TOTAL NUMBER OF QUESTIONS ------------------------ //
    @GetMapping("number-of-asked-questions")
    public int retrieveTotalNumberOfQuestions() {
        return questionService.getTotalNumberOfQuestions();
    }

    @GetMapping("number-of-asked-questions/{programmingLanguage}")
    public int retrieveNumberOfQuestionsForProgrammingLanguage(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @GetMapping("number-of-questions-asked-today")
    public int retrieveTotalNumberOfQuestionsAskedToday() {
        return questionService.getTotalNumberOfQuestionsAskedToday();
    }

    @GetMapping("number-of-questions-asked-today/{programmingLanguage}")
    public int retrieveNumberOfQuestionsAskedTodayForProgrammingLanguage(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfQuestionsAskedTodayPerProgrammingLanguage(programmingLanguage);
    }

    @GetMapping("number-of-questions-per-day")
    public Map<String, Integer> retrieveTotalNumberOfQuestionsPerDay() {
        return questionService.getTotalNumberOfQuestionsPerDay();
    }

    @GetMapping("number-of-questions-per-day/{programmingLanguage}")
    public Map<String, Integer> retrieveNumberOfQuestionsPerDayForProgrammingLanguage(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfQuestionsPerDayForProgrammingLanguage(programmingLanguage);
    }

    @GetMapping("number-of-questions-per-programming-language")
    public LinkedHashMap<String, Integer> retrieveTotalNumberOfQuestionsPerProgrammingLanguage() {
        return questionService.getTotalNumberOfQuestionsPerProgrammingLanguage();
    }

    // ------------------------ ANSWERED & UNANSWERED QUESTIONS ------------------------ //
    @GetMapping("number-of-answered-questions")
    public int retrieveTotalNumberOfAnsweredQuestions() {
        return questionService.getTotalNumberOfAnsweredQuestions();
    }

    @GetMapping("number-of-answered-questions/{programmingLanguage}")
    public int retrieveNumberOfAnsweredQuestionsForProgrammingLanguage(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfAnsweredQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @GetMapping("number-of-unanswered-questions")
    public int retrieveTotalNumberOfUnansweredQuestions() {
        return questionService.getTotalNumberOfUnansweredQuestions();
    }

    @GetMapping("number-of-unanswered-questions/{programmingLanguage}")
    public int retrieveNumberOfUnansweredQuestionsForProgrammingLanguage(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfUnansweredQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @GetMapping("number-of-answered-and-unanswered-questions")
    public Map<String, Integer> retrieveNumberOfQuestionsAnsweredAndUnanswered() {
        return questionService.getTotalNumberOfQuestionsAnsweredAndUnanswered();
    }

    @GetMapping("number-of-answered-and-unanswered-questions/{programmingLanguage}")
    public Map<String, Integer> retrieveNumberOfQuestionsAnsweredAndUnansweredForProgrammingLanguage(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfAnsweredAndUnansweredQuestionsForProgrammingLanguage(programmingLanguage);
    }

    // ------------------------ OPEN & CLOSED QUESTIONS ------------------------ //
    @GetMapping("number-of-closed-questions")
    public int retrieveTotalNumberOfQuestionsClosed() {
        return questionService.getTotalNumberOfClosedQuestions();
    }

    @GetMapping("number-of-closed-questions/{programmingLanguage}")
    public int retrieveNumberOfQuestionsClosedForProgrammingLanguage(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfClosedQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @GetMapping("number-of-open-questions")
    public int retrieveTotalNumberOfQuestionsOpen() {
        return questionService.getTotalNumberOfOpenQuestions();
    }

    @GetMapping("number-of-open-questions/{programmingLanguage}")
    public int retrieveNumberOfQuestionsOpenForProgrammingLanguage(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfOpenQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @GetMapping("number-of-open-and-closed-questions")
    public Map<String, Integer> retrieveNumberOfQuestionsOpenAndClosed() {
        return questionService.getNumberOfOpenAndClosedQuestions();
    }

    @GetMapping("number-of-open-and-closed-questions/{programmingLanguage}")
    public Map<String, Integer> retrieveNumberOfQuestionsOpenAndClosedForProgrammingLanguage(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfOpenAndClosedQuestionsForProgrammingLanguage(programmingLanguage);
    }

    // ------------------------ TOP VIEWS QUESTIONS ------------------------ //
    @GetMapping("top-views/{numberOfQuestions}")
    public List<Question> retrieveTopViewsQuestions(@PathVariable int numberOfQuestions) {
        return questionService.getTopViewsQuestions(numberOfQuestions);
    }

    @GetMapping("top-views/{programmingLanguage}/{numberOfQuestions}")
    public List<Question> retrieveTopViewsQuestionsForProgrammingLanguage(@PathVariable String programmingLanguage, @PathVariable int numberOfQuestions) {
        return questionService.getTopViewsQuestionsForProgrammingLanguage(programmingLanguage, numberOfQuestions);
    }

    // ------------------------ TOP SCORE QUESTIONS ------------------------ //
    @GetMapping("top-score/{numberOfQuestions}")
    public List<Question> retrieveTopScoreQuestions(@PathVariable int numberOfQuestions) {
        return questionService.getTopScoreQuestions(numberOfQuestions);
    }

    @GetMapping("top-score/{programmingLanguage}/{numberOfQuestions}")
    public List<Question> retrieveTopScoreQuestionsForProgrammingLanguage(@PathVariable String programmingLanguage, @PathVariable int numberOfQuestions) {
        return questionService.getTopScoreQuestionsForProgrammingLanguage(programmingLanguage, numberOfQuestions);
    }
}