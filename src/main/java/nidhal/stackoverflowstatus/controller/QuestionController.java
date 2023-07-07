package nidhal.stackoverflowstatus.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nidhal.stackoverflowstatus.service.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("count-all")
    public int retrieveTotalNumberOfQuestions() {
        return questionService.getTotalNumberOfQuestions();
    }

    @GetMapping("count-all/{programmingLanguage}")
    public int getNumberOfQuestions(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfQuestionsForProgrammingLanguage(programmingLanguage);
    }

    @GetMapping("number-of-questions-asked-today")
    public int retrieveTotalNumberOfQuestionsAskedToday() {
        return questionService.getTotalNumberOfQuestionsAskedToday();
    }

    @GetMapping("number-of-questions-asked-today/{programmingLanguage}")
    public int retrieveNumberOfQuestionsAskedToday(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfQuestionsAskedTodayPerProgrammingLanguage(programmingLanguage);
    }

    @GetMapping("number-of-questions-per-day")
    public Map<String, Integer> retrieveTotalNumberOfQuestionsPerDay() {
        return questionService.getTotalNumberOfQuestionsPerDay();
    }

    @GetMapping("number-of-questions-per-day/{programmingLanguage}")
    public Map<String, Integer> retrieveNumberOfQuestionsPerDay(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfQuestionsPerDay(programmingLanguage);
    }


    @GetMapping("answered-unanswered")
    public Map<String, Integer> retrieveAnsweredUnansweredQuestionsForAllQuestions() {
        return questionService.getNumberOfAnsweredAndUnansweredQuestionsForAllQuestions();
    }


    @GetMapping("answered-unanswered/{programmingLanguage}")
    public Map<String, Integer> retrieveTheNumberOfAnsweredAndUnansweredQuestions(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfAnsweredAndUnansweredQuestionsForProgrammingLanguage(programmingLanguage);
    }

}