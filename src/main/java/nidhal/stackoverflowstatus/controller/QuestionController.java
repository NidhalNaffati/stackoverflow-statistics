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

    @GetMapping("number-of-questions-per-day/{programmingLanguage}")
    public Map<String, Integer> retrieveNumberOfQuestionsPerDay(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfQuestionPerDay(programmingLanguage);
    }


    @GetMapping("answered-unanswered/{programmingLanguage}")
    public Map<String, Integer> retrieveTheNumberOfAnsweredAndUnansweredQuestions(@PathVariable String programmingLanguage) {
        return questionService.getNumberOfQuestionsAnsweredAndUnAnsweredForTheProgrammingLanguage(programmingLanguage);
    }

    @GetMapping("answered-unanswered-for-all-languages")
    public Map<String, Integer> getAnsweredUnansweredForAllQuestions() {
        return questionService.getNumberOfQuestionsAnsweredAndUnAnsweredForAllQuestions();
    }

    @GetMapping("count-all")
    public int getNumberOfQuestions() {
        return questionService.getNumberOfQuestions();
    }

}

