package nidhal.stackoverflowstatus.dao;

import nidhal.stackoverflowstatus.entity.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class QuestionDaoTest {

    // Mock DataSource
    @Mock
    private DataSource dataSource;

    // Mock Connection, PreparedStatement, and ResultSet
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet resultSet;

    // Create an instance of the QuestionDao to be tested
    private QuestionDao questionDao;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        questionDao = new QuestionDao(dataSource);

        // Mocking dataSource.getConnection() to return the mock Connection
        when(dataSource.getConnection()).thenReturn(connection);

        // Mocking connection.prepareStatement() to return the mock PreparedStatement
        when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);

        // Mocking statement.executeQuery() to return the mock ResultSet
        when(statement.executeQuery()).thenReturn(resultSet);
    }

    @AfterEach
    public void tearDown() {
        // Clear any interactions with mocks after each test
        Mockito.clearInvocations(dataSource, connection, statement, resultSet);
    }

    private final Question question1 = Question.builder()
            .question_id(1L)
            .is_answered(true)
            .title("How to use Spring Boot?")
            .link("https://stackoverflow.com/questions/tagged/spring-boot")
            .score(30)
            .answer_count(5)
            .view_count(1000)
            .tags(List.of("java", "spring-boot"))
            .creation_date(LocalDate.of(2021, 7, 24).toEpochDay())
            .closed_date(0)
            .build();

    private final Question question2 = Question.builder()
            .question_id(2L)
            .is_answered(false)
            .title("How to use Spring Data JPA?")
            .link("https://stackoverflow.com/questions/tagged/spring-data-jpa")
            .score(20)
            .answer_count(10)
            .view_count(2000)
            .tags(List.of("java", "spring-data-jpa"))
            .creation_date(LocalDate.of(2021, 7, 25).toEpochDay())
            .closed_date(0)
            .build();

    @Test
    public void testCountAll() throws SQLException {
        // Mocking resultSet.getInt() to return a value
        when(resultSet.next())
                .thenReturn(true);

        when(resultSet.getInt(1))
                .thenReturn(10);

        int count = questionDao.countAll();
        assertEquals(10, count);
    }

    @Test
    public void testCountAllByTagsContains() throws SQLException {
        // Mocking resultSet.getInt() to return a value
        when(resultSet.next())
                .thenReturn(true);
        when(resultSet.getInt(1))
                .thenReturn(5);

        int count = questionDao.countAllByTagsContains("java");
        assertEquals(5, count);
    }

    @Test
    public void testCountByCreationDateBetween() throws SQLException {
        // Mocking resultSet.getInt() to return a value
        when(resultSet.next())
                .thenReturn(true);
        when(resultSet.getInt(1))
                .thenReturn(8);

        int count = questionDao.countByCreationDateBetween(1627152000L, 1672550400L);
        assertEquals(8, count);
    }

    @Test
    public void testCountByCreationDateBetweenAndTagsContains() throws SQLException {
        // Mocking resultSet.getInt() to return a value
        when(resultSet.next())
                .thenReturn(true);
        // Mocking resultSet.getInt() returning 3
        when(resultSet.getInt(1))
                .thenReturn(2);

        int count = questionDao.countByCreationDateBetweenAndTagsContains(question1.getCreation_date(), question2.getCreation_date(), "java");
        assertEquals(2, count);
    }

    @Test
    public void testFindCreationDatesByTagsContaining() throws SQLException {
        // Mocking resultSet.getLong() to return values
        when(resultSet.next())
                .thenReturn(true, true, false);
        // Mocking resultSet.getLong() returning
        when(resultSet.getLong("creation_date"))
                .thenReturn(question1.getCreation_date(), question2.getCreation_date());

        List<Long> creationDates = questionDao.findCreationDatesByTagsContaining("java");
        assertEquals(2, creationDates.size());
        long creationDate1 = LocalDate.of(2021, 7, 24).toEpochDay();
        long creationDate2 = LocalDate.of(2021, 7, 25).toEpochDay();
        assertEquals(creationDate1, creationDates.get(0));
        assertEquals(creationDate2, creationDates.get(1));
    }

    @Test
    public void testCountByIsAnswered() throws SQLException {
        // Mocking resultSet.getInt() to return a value
        when(resultSet.next())
                .thenReturn(true);
        when(resultSet.getInt(1))
                .thenReturn(15);

        int count = questionDao.countByIsAnswered(true);
        assertEquals(15, count);
    }

    @Test
    public void testCountByIsAnsweredAndTagsContains() throws SQLException {
        // Mocking resultSet.getInt() to return a value
        when(resultSet.next())
                .thenReturn(true);
        when(resultSet.getInt(1))
                .thenReturn(7);

        int count = questionDao.countByIsAnsweredAndTagsContains(true, "java");
        assertEquals(7, count);
    }

    @Test
    public void testCountByIsClosed() throws SQLException {
        // Mocking resultSet.getInt() to return a value
        when(resultSet.next())
                .thenReturn(true, false);

        // Mocking resultSet.getInt() returning 2
        when(resultSet.getInt("closed_question_count"))
                .thenReturn(2);

        int count = questionDao.countByIsClosed(true);
        assertEquals(2, count);
    }

    @Test
    public void testCountByIsClosedAndTagsContains() throws SQLException {
        // Mocking resultSet.getInt() to return a value
        when(resultSet.next())
                .thenReturn(true, false);

        // Mocking resultSet.getInt() returning 1
        when(resultSet.getInt("closed_question_contain_tags_counter"))
                .thenReturn(1);

        int count = questionDao.countByIsClosedAndTagsContains(true, "java");
        assertEquals(1, count);
    }

    private void mockResultSetBehaviorForQuestions(Question questionX, Question questionY) throws SQLException {
        // Mocking resultSet.next() to return a value
        when(resultSet.next())
                .thenReturn(true, true, false);

        // Mocking resultSet.getLong() returning the value of question_id column
        when(resultSet.getLong("question_id"))
                .thenReturn(questionX.getQuestion_id(), questionY.getQuestion_id());

        // Mocking resultSet.getBoolean() returning the value of is_answered column
        when(resultSet.getBoolean("is_answered"))
                .thenReturn(questionX.getIs_answered(), questionY.getIs_answered());

        // Mocking resultSet.getString() returning the value of title column
        when(resultSet.getString("title"))
                .thenReturn(questionX.getTitle(), questionY.getTitle());

        // Mocking resultSet.getString() returning the value of link column
        when(resultSet.getString("link"))
                .thenReturn(questionX.getLink(), questionY.getLink());

        // Mocking resultSet.getInt() returning the value of score column
        when(resultSet.getInt("score"))
                .thenReturn(questionX.getScore(), questionY.getScore());

        // Mocking resultSet.getInt() returning the value of answer_count column
        when(resultSet.getInt("answer_count"))
                .thenReturn(questionX.getAnswer_count(), questionY.getAnswer_count());

        // Mocking resultSet.getInt() returning the value of view_count column
        when(resultSet.getInt("view_count"))
                .thenReturn(questionX.getView_count(), questionY.getView_count());

        // Mocking resultSet.getString() returning the value of tags column
        when(resultSet.getString("tags"))
                .thenReturn(questionX.getTags().get(0) + "," + questionX.getTags().get(1),
                        questionY.getTags().get(0) + "," + questionY.getTags().get(1));

        // Mocking resultSet.getLong() returning the value of creation_date column
        when(resultSet.getLong("creation_date"))
                .thenReturn(questionX.getCreation_date(), questionY.getCreation_date());

        // Mocking resultSet.getLong() returning the value of closed_date column
        when(resultSet.getLong("closed_date"))
                .thenReturn(questionX.getClosed_date(), questionY.getClosed_date());
    }

    @Test
    public void testFindByTopScore() throws SQLException {
        mockResultSetBehaviorForQuestions(question1, question2);

        List<Question> result = questionDao.findByTopScore(2);

        List<Question> expectedResult = Arrays.asList(question1, question2);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testFindByTopScoreAndTagsContains() throws SQLException {
        mockResultSetBehaviorForQuestions(question1, question2);

        List<Question> result = questionDao.findByTopScoreAndTagsContains("java", 2);

        List<Question> expectedResult = Arrays.asList(question1, question2);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testFindByTopViews() throws SQLException {

        mockResultSetBehaviorForQuestions(question1, question2);

        List<Question> result = questionDao.findByTopViews(2);

        List<Question> expectedResult = Arrays.asList(question1, question2);

        assertEquals(expectedResult, result);

    }

    @Test
    public void testFindByTopViewsAndTagsContains() throws SQLException {

        mockResultSetBehaviorForQuestions(question1, question2);

        List<Question> result = questionDao.findByTopViewsAndTagsContains("java", 2);

        List<Question> expectedResult = Arrays.asList(question1, question2);

        assertEquals(expectedResult, result);

    }
}

