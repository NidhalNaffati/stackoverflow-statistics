package nidhal.stackoverflowstatus.service;

import nidhal.stackoverflowstatus.configuration.StackExchangeApiConfig;
import nidhal.stackoverflowstatus.dao.QuestionDao;
import nidhal.stackoverflowstatus.entity.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private StackExchangeApiConfig stackExchangeApiConfig;

    @InjectMocks
    private QuestionService underTestService;


    @BeforeEach
    public void setUp() {
        // Mock behavior of stackExchangeApiConfig.getProgrammingLanguages()
        lenient().when(stackExchangeApiConfig.getProgrammingLanguages())
                .thenReturn(Arrays.asList("Java", "Python", "C++"));

        // Initialize the programmingLanguages list by calling the init() method
        underTestService.init();
    }

    // Helper method to create sample Question objects for testing
    private List<Question> createSampleQuestions(int limit) {
        List<Question> sampleQuestions = new ArrayList<>();
        for (int i = 1; i <= limit; i++) {
            Question question = new Question();
            question.setQuestion_id(i);
            question.setTitle("Sample Question " + i);
            question.setLink("https://stackoverflow.com/questions/" + i);
            question.setScore(i);
            question.setAnswer_count(i);
            question.setView_count(i);
            sampleQuestions.add(question);
        }
        return sampleQuestions;
    }

    @Test
    void testGetTotalNumberOfQuestions() {
        // Mock the behavior of questionDao.countAll()
        when(questionDao.countAll()).thenReturn(100);

        int result = underTestService.getTotalNumberOfQuestions();
        assertEquals(100, result);

        // Verify that countAll() was called exactly once
        verify(questionDao, times(1)).countAll();
    }

    @Test
    void testGetNumberOfQuestionsForProgrammingLanguage() {
        String programmingLanguage = "Java";
        // Mock the behavior of questionDao.countAllByTagsContains(programmingLanguage)
        when(questionDao.countAllByTagsContains(programmingLanguage))
                .thenReturn(50);

        int result = underTestService.getNumberOfQuestionsForProgrammingLanguage(programmingLanguage);
        assertEquals(50, result);

        // Verify that countAllByTagsContains() was called exactly once with the correct argument
        verify(questionDao, times(1)).countAllByTagsContains(programmingLanguage);
    }

    @Test
    void testGetTotalNumberOfQuestionsAskedToday() {
        // Mock the behavior of questionDao.countByCreationDateBetween()
        when(questionDao.countByCreationDateBetween(anyLong(), anyLong()))
                .thenReturn(30);

        int result = underTestService.getTotalNumberOfQuestionsAskedToday();
        assertEquals(30, result);

        // Verify that countByCreationDateBetween() was called exactly once with appropriate arguments
        verify(questionDao, times(1)).countByCreationDateBetween(anyLong(), anyLong());
    }

    @Test
    void testGetNumberOfQuestionsAskedTodayPerProgrammingLanguage() {
        String programmingLanguage = "Python";
        // Mock the behavior of questionDao.countByCreationDateBetweenAndTagsContains()
        when(questionDao.countByCreationDateBetweenAndTagsContains(anyLong(), anyLong(), eq(programmingLanguage)))
                .thenReturn(15);

        int result = underTestService.getNumberOfQuestionsAskedTodayPerProgrammingLanguage(programmingLanguage);
        assertEquals(15, result);

        // Verify that countByCreationDateBetweenAndTagsContains() was called exactly once with the correct arguments
        verify(questionDao, times(1)).countByCreationDateBetweenAndTagsContains(anyLong(), anyLong(), eq(programmingLanguage));
    }

    @Test
    void testGetTotalNumberOfQuestionsPerDay() {
        // Mock the behavior of questionDao.findCreationDatesByTagsContaining()
        when(questionDao.findCreationDatesByTagsContaining(anyString()))
                .thenReturn(Arrays.asList(
                        // One question on Thursday
                        LocalDateTime.of(2023, 7, 27, 12, 0).toEpochSecond(ZoneOffset.UTC),
                        // Two questions on Saturday
                        LocalDateTime.of(2023, 7, 29, 14, 0).toEpochSecond(ZoneOffset.UTC),
                        LocalDateTime.of(2023, 7, 29, 10, 0).toEpochSecond(ZoneOffset.UTC),
                        // No questions on Mo,day
                        LocalDateTime.of(2023, 7, 31, 11, 0).toEpochSecond(ZoneOffset.UTC)
                ));

        LinkedHashMap<String, Integer> result = underTestService.getTotalNumberOfQuestionsPerDay();

        // Assert that the result contains the expected values for the days of the week
        assertEquals(3, result.get("THURSDAY")); // 3 questions on Thursday and not 1 because we have 3 programming languages and for each programming language we have a question on Thursday
        assertEquals(6, result.get("SATURDAY")); // 6 questions on Saturday and not 2 because we have 3 programming languages and for each programming language we have a question on Saturday
        assertEquals(0, result.get("SUNDAY")); // No questions on Sunday

        // Verify that findCreationDatesByTagsContaining() was called exactly once with appropriate arguments
        verify(questionDao, times(3)).findCreationDatesByTagsContaining(anyString());
    }

    @Test
    void testGetNumberOfQuestionsPerDayForProgrammingLanguage() {
        String programmingLanguage = "C++";
        // Mock the behavior of questionDao.findCreationDatesByTagsContaining(programmingLanguage)
        when(questionDao.findCreationDatesByTagsContaining(programmingLanguage))
                .thenReturn(Arrays.asList(
                        // One question on Thursday
                        LocalDateTime.of(2023, 7, 27, 12, 0).toEpochSecond(ZoneOffset.UTC),
                        // Two questions on Saturday
                        LocalDateTime.of(2023, 7, 29, 14, 0).toEpochSecond(ZoneOffset.UTC),
                        LocalDateTime.of(2023, 7, 29, 10, 0).toEpochSecond(ZoneOffset.UTC),
                        // No questions on Mo,day
                        LocalDateTime.of(2023, 7, 31, 11, 0).toEpochSecond(ZoneOffset.UTC)
                ));

        LinkedHashMap<String, Integer> result = underTestService.getNumberOfQuestionsPerDayForProgrammingLanguage(programmingLanguage);

        // Assert that the result contains the expected values for the days of the week
        assertEquals(1, result.get("THURSDAY")); // One question on Thursday
        assertEquals(2, result.get("SATURDAY")); // Two questions on Saturday
        assertEquals(0, result.get("SUNDAY")); // No questions on Sunday

        // Verify that findCreationDatesByTagsContaining() was called exactly once with the correct argument
        verify(questionDao, times(1)).findCreationDatesByTagsContaining(programmingLanguage);
    }

    @Test
    void testGetTotalNumberOfQuestionsPerProgrammingLanguage() {
        // Mock the behavior of questionDao.countAllByTagsContains(programmingLanguage)
        when(questionDao.countAllByTagsContains(anyString()))
                .thenReturn(100); // Assume 100 questions for each programming language

        LinkedHashMap<String, Integer> result = underTestService.getTotalNumberOfQuestionsPerProgrammingLanguage();

        // Assert that the result contains the expected values for each programming language
        assertEquals(100, result.get("Java"));
        assertEquals(100, result.get("Python"));
        assertEquals(100, result.get("C++"));

        // Verify that countAllByTagsContains() was called exactly three times (for each programming language)
        verify(questionDao, times(3)).countAllByTagsContains(anyString());
    }

    @Test
    void testGetTotalNumberOfAnsweredQuestions() {
        // Mock the behavior of questionDao.countByIsAnswered
        when(questionDao.countByIsAnswered(true)).thenReturn(42);

        int result = underTestService.getTotalNumberOfAnsweredQuestions();

        // Ensure that the method returns the correct result
        assertEquals(42, result);

        // Verify that the method was called only once
        verify(questionDao, times(1)).countByIsAnswered(true);
    }

    @Test
    void testGetNumberOfAnsweredQuestionsForProgrammingLanguage() {
        // Mock the behavior of questionDao.countByIsAnsweredAndTagsContains
        when(questionDao.countByIsAnsweredAndTagsContains(true, "Java")).thenReturn(10);

        // Call the method twice with the same programming language
        int result = underTestService.getNumberOfAnsweredQuestionsForProgrammingLanguage("Java");

        // Ensure that the method returns the correct result
        assertEquals(10, result);

        // Verify that the method was called only once with the same input parameter
        verify(questionDao, times(1)).countByIsAnsweredAndTagsContains(true, "Java");
    }

    @Test
    void testGetTotalNumberOfUnansweredQuestions() {
        // Mock the behavior of questionDao.countByIsAnswered
        when(questionDao.countByIsAnswered(false)).thenReturn(25);

        // Call the method twice
        int result = underTestService.getTotalNumberOfUnansweredQuestions();

        // Ensure that the method returns the correct result
        assertEquals(25, result);

        // Verify that the method was called only once
        verify(questionDao, times(1)).countByIsAnswered(false);
    }

    @Test
    void testGetNumberOfUnansweredQuestionsForProgrammingLanguage() {
        // Mock the behavior of questionDao.countByIsAnsweredAndTagsContains
        when(questionDao.countByIsAnsweredAndTagsContains(false, "Java")).thenReturn(5);

        // Call the method twice with the same programming language
        int result = underTestService.getNumberOfUnansweredQuestionsForProgrammingLanguage("Java");

        // Ensure that the method returns the correct result
        assertEquals(5, result);

        // Verify that the method was called only once with the same input parameter
        verify(questionDao, times(1)).countByIsAnsweredAndTagsContains(false, "Java");
    }

    @Test
    void testGetTotalNumberOfQuestionsAnsweredAndUnanswered() {
        // Mock the behavior of questionDao.countByIsAnswered
        when(questionDao.countByIsAnswered(true)).thenReturn(42);
        when(questionDao.countByIsAnswered(false)).thenReturn(25);

        Map<String, Integer> result = underTestService.getTotalNumberOfQuestionsAnsweredAndUnanswered();

        // Ensure that the method returns the correct result
        assertEquals(42, result.get("answered"));
        assertEquals(25, result.get("unanswered"));

        // Verify that the methods were called exactly once
        verify(questionDao, times(1)).countByIsAnswered(true);
        verify(questionDao, times(1)).countByIsAnswered(false);
    }

    @Test
    void testGetNumberOfAnsweredAndUnansweredQuestionsForProgrammingLanguage() {
        // Mock the behavior of questionDao.countByIsAnsweredAndTagsContains
        when(questionDao.countByIsAnsweredAndTagsContains(true, "Java"))
                .thenReturn(10);
        when(questionDao.countByIsAnsweredAndTagsContains(false, "Java"))
                .thenReturn(5);

        Map<String, Integer> result = underTestService.getNumberOfAnsweredAndUnansweredQuestionsForProgrammingLanguage("Java");

        // Ensure that the method returns the correct result
        assertEquals(10, result.get("answered"));
        assertEquals(5, result.get("unanswered"));

        // Verify that the methods were called exactly once with the same input parameter
        verify(questionDao, times(1)).countByIsAnsweredAndTagsContains(true, "Java");
        verify(questionDao, times(1)).countByIsAnsweredAndTagsContains(false, "Java");
    }

    @Test
    void testGetTotalNumberOfClosedQuestions() {
        // Mock the behavior of questionDao.countByIsClosed
        when(questionDao.countByIsClosed(true))
                .thenReturn(15);

        int result = underTestService.getTotalNumberOfClosedQuestions();

        // Ensure that the method returns the correct result
        assertEquals(15, result);

        // Verify that the method was called exactly once
        verify(questionDao, times(1)).countByIsClosed(true);
    }

    @Test
    void testGetNumberOfClosedQuestionsForProgrammingLanguage() {
        // Mock the behavior of questionDao.countByIsClosedAndTagsContains
        when(questionDao.countByIsClosedAndTagsContains(true, "Java"))
                .thenReturn(5);

        int result = underTestService.getNumberOfClosedQuestionsForProgrammingLanguage("Java");

        // Ensure that the method returns the correct result
        assertEquals(5, result);

        // Verify that the method was called exactly once with the same input parameter
        verify(questionDao, times(1))
                .countByIsClosedAndTagsContains(true, "Java");
    }

    @Test
    void testGetTotalNumberOfOpenQuestions() {
        // Mock the behavior of questionDao.countByIsClosed
        when(questionDao.countByIsClosed(false))
                .thenReturn(20);

        int result = underTestService.getTotalNumberOfOpenQuestions();

        // Ensure that the method returns the correct result
        assertEquals(20, result);

        // Verify that the method was called exactly once
        verify(questionDao, times(1))
                .countByIsClosed(false);
    }

    @Test
    void testGetNumberOfOpenQuestionsForProgrammingLanguage() {
        // Mock the behavior of questionDao.countByIsClosedAndTagsContains
        when(questionDao.countByIsClosedAndTagsContains(false, "Java"))
                .thenReturn(8);

        int result = underTestService.getNumberOfOpenQuestionsForProgrammingLanguage("Java");

        // Ensure that the method returns the correct result
        assertEquals(8, result);

        // Verify that the method was called exactly once with the same input parameter
        verify(questionDao, times(1))
                .countByIsClosedAndTagsContains(false, "Java");
    }

    @Test
    void testGetNumberOfOpenAndClosedQuestions() {
        // Mock the behavior of getTotalNumberOfOpenQuestions and getTotalNumberOfClosedQuestions
        when(questionDao.countByIsClosed(false))
                .thenReturn(20);
        when(questionDao.countByIsClosed(true))
                .thenReturn(15);

        Map<String, Integer> result = underTestService.getNumberOfOpenAndClosedQuestions();

        // Ensure that the method returns the correct result
        assertEquals(20, result.get("open"));
        assertEquals(15, result.get("closed"));

        // Verify that the methods were called exactly once
        verify(questionDao, times(1))
                .countByIsClosed(false);
        verify(questionDao, times(1))
                .countByIsClosed(true);
    }

    @Test
    void testGetNumberOfOpenAndClosedQuestionsForProgrammingLanguage() {
        // Mock the behavior of getNumberOfOpenQuestionsForProgrammingLanguage and getNumberOfClosedQuestionsForProgrammingLanguage
        when(questionDao.countByIsClosedAndTagsContains(false, "Java"))
                .thenReturn(8);
        when(questionDao.countByIsClosedAndTagsContains(true, "Java"))
                .thenReturn(5);

        Map<String, Integer> result = underTestService.getNumberOfOpenAndClosedQuestionsForProgrammingLanguage("Java");

        // Ensure that the method returns the correct result
        assertEquals(8, result.get("open"));
        assertEquals(5, result.get("closed"));

        // Verify that the methods were called exactly once with the same input parameter
        verify(questionDao, times(1))
                .countByIsClosedAndTagsContains(false, "Java");
        verify(questionDao, times(1))
                .countByIsClosedAndTagsContains(true, "Java");
    }

    @Test
    void testGetTopViewsQuestions() {
        // Create sample questions for testing
        int limit = 5;
        List<Question> sampleQuestions = createSampleQuestions(limit);

        // Mock the behavior of questionDao.findByTopViews
        when(questionDao.findByTopViews(limit))
                .thenReturn(sampleQuestions);

        List<Question> result = underTestService.getTopViewsQuestions(limit);

        // Ensure that the method returns the correct result
        assertEquals(sampleQuestions, result);

        // Verify that the method was called exactly once with the same input parameter
        verify(questionDao, times(1))
                .findByTopViews(limit);
    }

    // Test for getTopViewsQuestionsForProgrammingLanguage method
    @Test
    void testGetTopViewsQuestionsForProgrammingLanguage() {
        // Create sample questions for testing
        int limit = 5;
        String programmingLanguage = "Java";
        List<Question> sampleQuestions = createSampleQuestions(limit);

        // Mock the behavior of questionDao.findByTopViewsAndTagsContains
        when(questionDao.findByTopViewsAndTagsContains(programmingLanguage, limit))
                .thenReturn(sampleQuestions);

        List<Question> result = underTestService.getTopViewsQuestionsForProgrammingLanguage(programmingLanguage, limit);

        // Ensure that the method returns the correct result
        assertEquals(sampleQuestions, result);

        // Verify that the method was called exactly once with the same input parameters
        verify(questionDao, times(1))
                .findByTopViewsAndTagsContains(programmingLanguage, limit);
    }

    // Test for getTopScoreQuestions method
    @Test
    void testGetTopScoreQuestions() {
        // Create sample questions for testing
        int limit = 5;
        List<Question> sampleQuestions = createSampleQuestions(limit);

        // Mock the behavior of questionDao.findByTopScore
        when(questionDao.findByTopScore(limit))
                .thenReturn(sampleQuestions);

        List<Question> result = underTestService.getTopScoreQuestions(limit);

        // Ensure that the method returns the correct result
        assertEquals(sampleQuestions, result);

        // Verify that the method was called exactly once with the same input parameter
        verify(questionDao, times(1))
                .findByTopScore(limit);
    }

    // Test for getTopScoreQuestionsForProgrammingLanguage method
    @Test
    void testGetTopScoreQuestionsForProgrammingLanguage() {
        // Create sample questions for testing
        int limit = 5;
        String programmingLanguage = "Java";
        List<Question> sampleQuestions = createSampleQuestions(limit);

        // Mock the behavior of questionDao.findByTopScoreAndTagsContains
        when(questionDao.findByTopScoreAndTagsContains(programmingLanguage, limit))
                .thenReturn(sampleQuestions);

        List<Question> result = underTestService.getTopScoreQuestionsForProgrammingLanguage(programmingLanguage, limit);

        // Ensure that the method returns the correct result
        assertEquals(sampleQuestions, result);

        // Verify that the method was called exactly once with the same input parameters
        verify(questionDao, times(1))
                .findByTopScoreAndTagsContains(programmingLanguage, limit);
    }

}
