package nidhal.stackoverflowstatus.controller;

import lombok.AllArgsConstructor;
import nidhal.stackoverflowstatus.service.StackExchangeApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/questions")
public class StackExchangeApiController {

    private final StackExchangeApiService stackExchangeApiService;

    @GetMapping
    public String storeJavaQuestions() {
        List<String> programingLanguages = List.of("java", "python", "go", "javascript");
        programingLanguages.forEach(stackExchangeApiService::storeAllQuestionRelatedTo);
        return "Questions stored successfully";
    }
}
