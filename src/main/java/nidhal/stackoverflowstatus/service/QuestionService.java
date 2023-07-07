package nidhal.stackoverflowstatus.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nidhal.stackoverflowstatus.repository.QuestionRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final List<String> programmingLanguages = List.of("java", "python", "javascript", "go", "kotlin", "c++", "c#", "ruby", "php", "swift");

    @Cacheable(value = "totalNumberOfQuestionsCache")
    public int getTotalNumberOfQuestions() {
        return questionRepository.countAll();
    }

    @Cacheable(value = "numberOfQuestionsCache", key = "#programmingLanguage")
    public int getNumberOfQuestionsForProgrammingLanguage(String programmingLanguage) {
        return questionRepository.countByTagsContains(programmingLanguage);
    }

    @Cacheable(value = "totalNumberOfQuestionsAskedTodayCache")
    public int getTotalNumberOfQuestionsAskedToday() {
        int totalNumberOfQuestionsAskedToday = 0;
        for (String programmingLanguage : programmingLanguages) {
            totalNumberOfQuestionsAskedToday += getNumberOfQuestionsAskedTodayPerProgrammingLanguage(programmingLanguage);
        }
        return totalNumberOfQuestionsAskedToday;
    }

    @Cacheable(value = "numberOfQuestionsAskedTodayCache", key = "#programmingLanguage")
    public int getNumberOfQuestionsAskedTodayPerProgrammingLanguage(String programmingLanguage) {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDateTime startOfDay = LocalDateTime.of(today, LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.of(today.plusDays(1), LocalTime.MIDNIGHT);
        long startEpoch = startOfDay.atZone(ZoneId.systemDefault()).toEpochSecond();
        long endEpoch = endOfDay.atZone(ZoneId.systemDefault()).toEpochSecond();

        return questionRepository.countByCreation_dateBetweenAndTagsContains(startEpoch, endEpoch, programmingLanguage);
    }

    @Cacheable(value = "totalNumberOfQuestionsPerDayCache")
    public Map<String, Integer> getTotalNumberOfQuestionsPerDay() {
        Map<String, Integer> totalNumberOfQuestionsPerDay = new HashMap<>();
        for (String programmingLanguage : programmingLanguages) {
            Map<String, Integer> numberOfQuestionsPerDay = getNumberOfQuestionsPerDay(programmingLanguage);
            for (Map.Entry<String, Integer> entry : numberOfQuestionsPerDay.entrySet()) {
                String dayOfWeek = entry.getKey();
                Integer numberOfQuestions = entry.getValue();
                totalNumberOfQuestionsPerDay.put(dayOfWeek, totalNumberOfQuestionsPerDay.getOrDefault(dayOfWeek, 0) + numberOfQuestions);
            }
        }
        return totalNumberOfQuestionsPerDay;
    }

    @Cacheable(value = "numberOfQuestionsPerDayCache", key = "#programmingLanguage")
    public LinkedHashMap<String, Integer> getNumberOfQuestionsPerDay(String programmingLanguage) {
        // Get the creation dates of all questions that contain the programming language
        List<Long> creationDates = questionRepository.findCreationDatesByTagsContaining(programmingLanguage);

        // Map to store the number of questions per day of the week while preserving insertion order
        LinkedHashMap<String, Integer> questionsCountByDayOfWeek = new LinkedHashMap<>();

        // Initialize the map with all days of the week in the desired order
        questionsCountByDayOfWeek.put("MONDAY", 0);
        questionsCountByDayOfWeek.put("TUESDAY", 0);
        questionsCountByDayOfWeek.put("WEDNESDAY", 0);
        questionsCountByDayOfWeek.put("THURSDAY", 0);
        questionsCountByDayOfWeek.put("FRIDAY", 0);
        questionsCountByDayOfWeek.put("SATURDAY", 0);
        questionsCountByDayOfWeek.put("SUNDAY", 0);

        // Iterate over the creation dates and count the number of questions per day of the week.
        for (Long creationDate : creationDates) {
            // Convert the creation date from seconds to LocalDateTime
            LocalDateTime creationDateTime = LocalDateTime
                    .ofInstant(
                            Instant.ofEpochSecond(creationDate),
                            ZoneId.systemDefault()
                    );

            // Get the day of the week from the LocalDateTime
            DayOfWeek dayOfWeek = creationDateTime.getDayOfWeek();
            String dayOfWeekName = dayOfWeek.toString();

            // Increment the number of questions for the day of the week
            questionsCountByDayOfWeek.put(dayOfWeekName, questionsCountByDayOfWeek.get(dayOfWeekName) + 1);
        }

        return questionsCountByDayOfWeek;
    }

    @Cacheable(value = "totalNumberOfAnsweredAndUnansweredQuestionsForAllLanguagesCache")
    public Map<String, Integer> getNumberOfAnsweredAndUnansweredQuestionsForAllQuestions() {
        Map<String, Integer> answeredUnanswered = new HashMap<>();
        answeredUnanswered.put("answered", questionRepository.countByIs_answered(true));
        answeredUnanswered.put("unanswered", questionRepository.countByIs_answered(false));
        return answeredUnanswered;
    }

    @Cacheable(value = "numberOfAnsweredAndUnansweredQuestionsCache", key = "#programmingLanguage")
    public Map<String, Integer> getNumberOfAnsweredAndUnansweredQuestionsForProgrammingLanguage(String programmingLanguage) {
        Map<String, Integer> answeredUnansweredQuestions = new HashMap<>();
        answeredUnansweredQuestions.put("answered", getNumberOfAnsweredQuestionsForProgrammingLanguage(programmingLanguage));
        answeredUnansweredQuestions.put("unanswered", getNumberOfUnansweredQuestionsForProgrammingLanguage(programmingLanguage));

        return answeredUnansweredQuestions;
    }

    @Cacheable(value = "numberOfAnsweredQuestionsCache", key = "#programmingLanguage")
    public int getNumberOfAnsweredQuestionsForProgrammingLanguage(String programmingLanguage) {
        return questionRepository.countByIs_answeredAndTagsContains(true, programmingLanguage);
    }

    @Cacheable(value = "numberOfUnansweredQuestionsCache", key = "#programmingLanguage")
    public int getNumberOfUnansweredQuestionsForProgrammingLanguage(String programmingLanguage) {
        return questionRepository.countByIs_answeredAndTagsContains(false, programmingLanguage);
    }

}
