package nidhal.stackoverflowstatus.controller;

import lombok.extern.slf4j.Slf4j;
import nidhal.stackoverflowstatus.entity.Question;
import nidhal.stackoverflowstatus.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String storeJavaQuestions() {
        List<String> programingLanguages = List.of("java", "python", "go", "javascript");
        programingLanguages.forEach(questionService::storeAllQuestionRelatedTo);
        return "Questions stored successfully";
    }

    @GetMapping("count-by-day-of-week")
    public Map<String, Integer> getQuestionCountByDayOfWeek() {
        return questionService.getQuestionCountByDayOfWeek("python");
    }


    @GetMapping("all")
    public ResponseEntity<List<Question>> getQuestions() {
        List<Question> questions = questionService.getQuestionsRelatedTo("go");
        return ResponseEntity.ok(questions);
    }
}

