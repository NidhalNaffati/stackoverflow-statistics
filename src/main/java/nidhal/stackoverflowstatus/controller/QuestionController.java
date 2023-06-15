package nidhal.stackoverflowstatus.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nidhal.stackoverflowstatus.service.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;


    @GetMapping("count-by-day-of-week")
    public Map<String, Integer> getQuestionCountByDayOfWeek() {
        return questionService.getQuestionCountByDayOfWeek("python");
    }

}

