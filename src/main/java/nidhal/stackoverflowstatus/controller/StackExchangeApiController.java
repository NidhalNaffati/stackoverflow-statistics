package nidhal.stackoverflowstatus.controller;

import lombok.AllArgsConstructor;
import nidhal.stackoverflowstatus.service.StackExchangeApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/questions")
public class StackExchangeApiController {

    private final StackExchangeApiService stackExchangeApiService;

    @GetMapping
    public String storeJavaQuestions() {
        List<String> programmingLanguages = List.of("java", "python", "javascript", "go", "kotlin", "c++", "c#", "ruby", "php", "swift");

        int maxThreads = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);

        for (String language : programmingLanguages) {
            CompletableFuture.runAsync(() -> stackExchangeApiService.storeAllQuestionRelatedTo(language), executorService);
        }

        executorService.shutdown();

        return "Questions stored successfully";
    }
}
