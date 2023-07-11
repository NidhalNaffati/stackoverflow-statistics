package nidhal.stackoverflowstatus.service;


import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nidhal.stackoverflowstatus.configuration.StackExchangeApiConfig;
import nidhal.stackoverflowstatus.dao.QuestionDao;
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

    private final QuestionDao questionDao;
    private final StackExchangeApiConfig stackExchangeApiConfig;
    private List<String> programmingLanguages;

    @PostConstruct
    private void init() {
        programmingLanguages = stackExchangeApiConfig.getProgrammingLanguages();
    }

    // ------------------------ TOTAL NUMBER OF QUESTIONS ------------------------ //
    @Cacheable(value = "totalNumberOfQuestionsCache")
    public int getTotalNumberOfQuestions() {
        return questionDao.countAll();
    }

    @Cacheable(value = "numberOfQuestionsCache", key = "#programmingLanguage")
    public int getNumberOfQuestionsForProgrammingLanguage(String programmingLanguage) {
        return questionDao.countAllByTagsContains(programmingLanguage);
    }

    @Cacheable(value = "totalNumberOfQuestionsAskedTodayCache")
    public int getTotalNumberOfQuestionsAskedToday() {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDateTime startOfDay = LocalDateTime.of(today, LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.of(today.plusDays(1), LocalTime.MIDNIGHT);
        long startEpoch = startOfDay.atZone(ZoneId.systemDefault()).toEpochSecond();
        long endEpoch = endOfDay.atZone(ZoneId.systemDefault()).toEpochSecond();

        return questionDao.countByCreationDateBetween(startEpoch, endEpoch);
    }

    @Cacheable(value = "numberOfQuestionsAskedTodayCache", key = "#programmingLanguage")
    public int getNumberOfQuestionsAskedTodayPerProgrammingLanguage(String programmingLanguage) {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDateTime startOfDay = LocalDateTime.of(today, LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.of(today.plusDays(1), LocalTime.MIDNIGHT);
        long startEpoch = startOfDay.atZone(ZoneId.systemDefault()).toEpochSecond();
        long endEpoch = endOfDay.atZone(ZoneId.systemDefault()).toEpochSecond();

        return questionDao.countByCreationDateBetweenAndTagsContains(startEpoch, endEpoch, programmingLanguage);
    }

    @Cacheable(value = "totalNumberOfQuestionsPerDayCache")
    public LinkedHashMap<String, Integer> getTotalNumberOfQuestionsPerDay() {
        LinkedHashMap<String, Integer> totalNumberOfQuestionsPerDay = new LinkedHashMap<>();
        for (String programmingLanguage : programmingLanguages) {
            Map<String, Integer> numberOfQuestionsPerDay = getNumberOfQuestionsPerDayForProgrammingLanguage(programmingLanguage);
            for (Map.Entry<String, Integer> entry : numberOfQuestionsPerDay.entrySet()) {
                String dayOfWeek = entry.getKey();
                Integer numberOfQuestions = entry.getValue();
                totalNumberOfQuestionsPerDay.put(dayOfWeek, totalNumberOfQuestionsPerDay.getOrDefault(dayOfWeek, 0) + numberOfQuestions);
            }
        }
        return totalNumberOfQuestionsPerDay;
    }

    @Cacheable(value = "numberOfQuestionsPerDayCache", key = "#programmingLanguage")
    public LinkedHashMap<String, Integer> getNumberOfQuestionsPerDayForProgrammingLanguage(String programmingLanguage) {
        // Get the creation dates of all questions that contain the programming language
        List<Long> creationDates = questionDao.findCreationDatesByTagsContaining(programmingLanguage);

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

    @Cacheable(value = "totalNumberOfQuestionsPerProgrammingLanguageCache")
    public LinkedHashMap<String,Integer> getTotalNumberOfQuestionsPerProgrammingLanguage() {
        LinkedHashMap<String, Integer> numberOfQuestionsPerProgrammingLanguage = new LinkedHashMap<>();
        for (String programmingLanguage : programmingLanguages) {
            numberOfQuestionsPerProgrammingLanguage.put(programmingLanguage, getNumberOfQuestionsForProgrammingLanguage(programmingLanguage));
        }
        return numberOfQuestionsPerProgrammingLanguage;
    }

    // ------------------------ ANSWERED & UNANSWERED QUESTIONS ------------------------ //
    @Cacheable(value = "totalNumberOfAnsweredQuestionsCache")
    public int getTotalNumberOfAnsweredQuestions() {
        return questionDao.countByIsAnswered(true);
    }

    @Cacheable(value = "numberOfAnsweredQuestionsCache", key = "#programmingLanguage")
    public int getNumberOfAnsweredQuestionsForProgrammingLanguage(String programmingLanguage) {
        return questionDao.countByIsAnsweredAndTagsContains(true, programmingLanguage);
    }

    @Cacheable(value = "totalNumberOfUnansweredQuestionsCache")
    public int getTotalNumberOfUnansweredQuestions() {
        return questionDao.countByIsAnswered(false);
    }

    @Cacheable(value = "numberOfUnansweredQuestionsCache", key = "#programmingLanguage")
    public int getNumberOfUnansweredQuestionsForProgrammingLanguage(String programmingLanguage) {
        return questionDao.countByIsAnsweredAndTagsContains(false, programmingLanguage);
    }

    @Cacheable(value = "totalNumberOfAnsweredAndUnansweredQuestionsCache")
    public Map<String, Integer> getTotalNumberOfQuestionsAnsweredAndUnanswered() {
        Map<String, Integer> answeredUnanswered = new HashMap<>();
        int totalNumberOfAnsweredQuestions = getTotalNumberOfAnsweredQuestions();
        int totalNumberOfUnansweredQuestions = getTotalNumberOfUnansweredQuestions();

        answeredUnanswered.put("answered", totalNumberOfAnsweredQuestions);
        answeredUnanswered.put("unanswered", totalNumberOfUnansweredQuestions);
        return answeredUnanswered;
    }


    @Cacheable(value = "numberOfAnsweredAndUnansweredQuestionsCache", key = "#programmingLanguage")
    public Map<String, Integer> getNumberOfAnsweredAndUnansweredQuestionsForProgrammingLanguage(String programmingLanguage) {
        Map<String, Integer> answeredUnansweredQuestions = new HashMap<>();
        answeredUnansweredQuestions.put("answered", getNumberOfAnsweredQuestionsForProgrammingLanguage(programmingLanguage));
        answeredUnansweredQuestions.put("unanswered", getNumberOfUnansweredQuestionsForProgrammingLanguage(programmingLanguage));

        return answeredUnansweredQuestions;
    }


    // ------------------------ OPEN & CLOSED QUESTIONS ------------------------ //
    @Cacheable(value = "totalNumberOfClosedQuestionsCache")
    public int getTotalNumberOfClosedQuestions() {
        return questionDao.countByIsClosed(true);
    }

    @Cacheable(value = "numberOfClosedQuestionsCache", key = "#programmingLanguage")
    public int getNumberOfClosedQuestionsForProgrammingLanguage(String programmingLanguage) {
        return questionDao.countByIsClosedAndTagsContains(true, programmingLanguage);
    }

    @Cacheable(value = "totalNumberOfOpenQuestionsCache")
    public int getTotalNumberOfOpenQuestions() {
        return questionDao.countByIsClosed(false);
    }

    @Cacheable(value = "numberOfOpenQuestionsCache", key = "#programmingLanguage")
    public int getNumberOfOpenQuestionsForProgrammingLanguage(String programmingLanguage) {
        return questionDao.countByIsClosedAndTagsContains(false, programmingLanguage);
    }

    @Cacheable(value = "totalNumberOfOpenAndClosedQuestionsCache")
    public Map<String, Integer> getNumberOfOpenAndClosedQuestions() {
        int totalNumberOfQuestionsOpen = getTotalNumberOfOpenQuestions();
        int totalNumberOfQuestionsClosed = getTotalNumberOfClosedQuestions();

        Map<String, Integer> openClosed = new HashMap<>();
        openClosed.put("open", totalNumberOfQuestionsOpen);
        openClosed.put("closed", totalNumberOfQuestionsClosed);

        return openClosed;
    }

    @Cacheable(value = "numberOfOpenAndClosedQuestionsCache", key = "#programmingLanguage")
    public Map<String, Integer> getNumberOfOpenAndClosedQuestionsForProgrammingLanguage(String programmingLanguage) {
        int numberOfQuestionsOpen = getNumberOfOpenQuestionsForProgrammingLanguage(programmingLanguage);
        int numberOfQuestionsClosed = getNumberOfClosedQuestionsForProgrammingLanguage(programmingLanguage);

        Map<String, Integer> openClosed = new HashMap<>();
        openClosed.put("open", numberOfQuestionsOpen);
        openClosed.put("closed", numberOfQuestionsClosed);

        return openClosed;
    }
}
