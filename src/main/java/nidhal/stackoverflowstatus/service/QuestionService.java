package nidhal.stackoverflowstatus.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nidhal.stackoverflowstatus.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Map<String, Integer> getNumberOfQuestionsPerDay(String programmingLanguage) {

        List<Long> creationDates = questionRepository.findCreationDatesByTagsContaining(programmingLanguage);
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

    public int getNumberOfAnsweredQuestionsForProgrammingLanguage(String programmingLanguage) {
        return questionRepository.countByIs_answeredAndTagsContains(true, programmingLanguage);
    }

    public int getNumberOfUnansweredQuestionsForProgrammingLanguage(String programmingLanguage) {
        return questionRepository.countByIs_answeredAndTagsContains(false, programmingLanguage);
    }

    public Map<String, Integer> getNumberOfAnsweredAndUnansweredQuestionsForProgrammingLanguage(String programmingLanguage) {
        Map<String, Integer> answeredUnansweredQuestions = new HashMap<>();
        answeredUnansweredQuestions.put("answered", getNumberOfAnsweredQuestionsForProgrammingLanguage(programmingLanguage));
        answeredUnansweredQuestions.put("unanswered", getNumberOfUnansweredQuestionsForProgrammingLanguage(programmingLanguage));

        return answeredUnansweredQuestions;
    }

    public Map<String, Integer> getNumberOfAnsweredAndUnansweredQuestionsForAllQuestions() {
        Map<String, Integer> answeredUnanswered = new HashMap<>();
        answeredUnanswered.put("answered", questionRepository.countByIs_answered(true));
        answeredUnanswered.put("unanswered", questionRepository.countByIs_answered(false));
        return answeredUnanswered;

    }

    public int getTotalNumberOfQuestions() {
        return questionRepository.countAll();
    }

}
