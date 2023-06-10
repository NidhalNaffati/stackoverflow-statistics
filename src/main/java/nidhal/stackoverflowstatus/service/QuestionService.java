package nidhal.stackoverflowstatus.service;


import lombok.extern.slf4j.Slf4j;
import nidhal.stackoverflowstatus.configuration.StackExchangeApiConfig;
import nidhal.stackoverflowstatus.dto.StackExchangeResponse;
import nidhal.stackoverflowstatus.entity.Question;
import nidhal.stackoverflowstatus.repository.QuestionRepository;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class QuestionService {
    private final RestTemplate restTemplate;
    private final QuestionRepository questionRepository;
    private final StackExchangeApiConfig stackExchangeApiConfig;

    public QuestionService(
            QuestionRepository questionRepository,
            StackExchangeApiConfig stackExchangeApiConfig
    ) {
        // Create an instance of HttpComponentsClientHttpRequestFactory
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory(
                        HttpClientBuilder
                                .create()
                                .build()
                );

        // Create a new RestTemplate instance with the clientHttpRequestFactory
        this.restTemplate = new RestTemplate(clientHttpRequestFactory);
        this.stackExchangeApiConfig = stackExchangeApiConfig;
        this.questionRepository = questionRepository;
    }


    public void storeAllQuestionRelatedTo(String programingLanguage) {
        boolean hasMore = true;
        int page = 1;
        int updatedQuestions = 0;
        int newQuestions = 0;

        // Calculate the timestamp for one year ago
        long oneWeekAgoInSeconds = Instant.now().minus(Duration.ofDays(7)).getEpochSecond();

        // Build the API URL
        String baseUrl = stackExchangeApiConfig.getBaseUrl() + "/questions"
                         + "?fromdate=" + oneWeekAgoInSeconds
                         + "&order=desc"
                         + "&sort=creation"
                         + "&tagged=" + programingLanguage
                         + "&site=stackoverflow"
                         + "&key=" + stackExchangeApiConfig.getApiKey()
                         + "&page=";

        while (hasMore) {
            // Construct the full URL for the current page
            String url = baseUrl + page;

            // Print the URL to the console for debugging purposes
            log.info("extracting data from the following url: {}", url);

            try {
                // Make the API request
                ResponseEntity<StackExchangeResponse> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        StackExchangeResponse.class
                );
                log.info("response status code: {}", response.getStatusCode());
                // Check the status code of the response
                if (response.getStatusCode() == HttpStatus.OK) {
                    // Get the response body
                    StackExchangeResponse stackExchangeResponse = response.getBody();

                    if (stackExchangeResponse != null) {
                        List<Question> items = stackExchangeResponse.getItems();
                        for (Question item : items) {
                            // Check if the question is already stored in the database
                            if (questionRepository.existsById(item.getQuestion_id())) {
                                // Update the question in the database
                                questionRepository.save(item);
                                updatedQuestions++;
                            } else {
                                // Save the question in the database
                                questionRepository.save(item);
                                newQuestions++;
                            }
                        }

                        // check if there is more data to fetch
                        if (stackExchangeResponse.has_more) {
                            page++;
                        } else {
                            hasMore = false;
                        }
                    }
                } else {
                    // Print an error message if the response status code is not OK
                    log.error("There is a problem with the Stack Exchange server");
                }
            } catch (RestClientException e) {
                e.printStackTrace();
                // Print an error message if there is an exception thrown by the restTemplate object
                log.error("There was an error communicating with the Stack Exchange API: {} ", e.getLocalizedMessage());
                hasMore = false;
            }
        }
        log.info("Stored all questions related to {} there is {} new question & {} updated questions", programingLanguage, newQuestions, updatedQuestions);
    }


    public List<Question> getQuestionsRelatedTo(String programingLanguage) {
        boolean hasMore = true;
        int page = 1;
        List<Question> result = new ArrayList<>();

        // Calculate the timestamp for one year ago
        long oneWeekAgoInSeconds = Instant.now().minus(Duration.ofDays(7)).getEpochSecond();

        // Build the API URL
        String baseUrl = stackExchangeApiConfig.getBaseUrl() + "/questions"
                         + "?fromdate=" + oneWeekAgoInSeconds
                         + "&order=desc"
                         + "&sort=creation"
                         + "&tagged=" + programingLanguage
                         + "&site=stackoverflow"
                         + "&key=" + stackExchangeApiConfig.getApiKey()
                         + "&page=";

        while (hasMore) {
            // Construct the full URL for the current page
            String url = baseUrl + page;

            // Print the URL to the console for debugging purposes
            log.info("EXTRACTING DATA FROM THIS URL: {}", url);

            try {
                // Make the API request
                ResponseEntity<StackExchangeResponse> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        StackExchangeResponse.class
                );
                log.info("extracting data from the following url: {}", url);
                // Check the status code of the response
                if (response.getStatusCode() == HttpStatus.OK) {
                    // Get the response body
                    StackExchangeResponse stackExchangeResponse = response.getBody();

                    if (stackExchangeResponse != null) {
                        List<Question> items = stackExchangeResponse.getItems();
                        for (Question item : items) {
                            // add the item to the result list
                            if (item != null) {
                                result.add(item);
                            }
                        }

                        // check if there is more data to fetch
                        if (stackExchangeResponse.has_more) {
                            page++;
                        } else {
                            hasMore = false;
                        }
                    }
                } else {
                    // Print an error message if the response status code is not OK
                    log.error("There is a problem with the Stack Exchange server");
                }
            } catch (RestClientException e) {
                e.printStackTrace();
                // Print an error message if there is an exception thrown by the restTemplate object
                log.error("There was an error communicating with the Stack Exchange API: {} ", e.getLocalizedMessage());
                hasMore = false;
            }
        }
        log.info("Stored all questions related to {} there is {} questions", programingLanguage, result.size());
        return result;
    }

    public Map<String, Integer> getQuestionCountByDayOfWeek(String tag) {
        log.error("getQuestionCountByDayOfWeek for tag: {}", tag);

        List<Long> creationDates = questionRepository.findCreationDatesByTagsContaining(tag);
        Map<String, Integer> questionsCountByDayOfWeek = new HashMap<>();

        for (Long creationDate : creationDates) {
            LocalDateTime creationDateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(creationDate),
                    ZoneId.systemDefault()
            );
            DayOfWeek dayOfWeek = creationDateTime.getDayOfWeek();
            String dayOfWeekName = dayOfWeek.toString().toLowerCase();

            questionsCountByDayOfWeek.put(
                    dayOfWeekName,
                    questionsCountByDayOfWeek.getOrDefault(dayOfWeekName, 0) + 1
            );
        }

        return questionsCountByDayOfWeek;
    }

}
