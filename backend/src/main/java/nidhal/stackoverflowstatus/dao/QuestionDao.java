package nidhal.stackoverflowstatus.dao;

import lombok.AllArgsConstructor;
import nidhal.stackoverflowstatus.entity.Question;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Repository
@AllArgsConstructor
public class QuestionDao {

    private final DataSource dataSource;

    public int countAll() {
        // Prepare the SQL query
        String sqlQuery = """
                SELECT COUNT(q)
                FROM question q
                """;

        // Initialize the count
        int count = 0;

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Retrieve the count
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int countAllByTagsContains(String programmingLanguage) {
        // Prepare the SQL query
        String sqlQuery = """
                SELECT COUNT(q)
                FROM question q
                JOIN question_tags qt ON q.question_id = qt.question_question_id
                WHERE qt.tags = ?
                """;

        // Initialize the count
        int count = 0;

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {

            // Set the tag parameter
            statement.setString(1, programmingLanguage);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Retrieve the count
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int countByCreationDateBetween(long startEpoch, long endEpoch) {
        String sqlQuery = """ 
                SELECT COUNT(q)
                FROM question q
                WHERE q.creation_date BETWEEN ? AND ?
                """;

        // Initialize the count
        int count = 0;

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {

            // Set the startEpoch parameter
            statement.setLong(1, startEpoch);

            // Set the endEpoch parameter
            statement.setLong(2, endEpoch);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Retrieve the count
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int countByCreationDateBetweenAndTagsContains(long startEpoch, long endEpoch, String programmingLanguage) {
        // Prepare the SQL query

        String sqlQuery = """
                SELECT COUNT(q)
                FROM question q
                JOIN question_tags qt ON q.question_id = qt.question_question_id
                WHERE q.creation_date BETWEEN ? AND ? AND qt.tags = ?
                """;

        // Initialize the count
        int count = 0;

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {

            // Set the startEpoch parameter
            statement.setLong(1, startEpoch);

            // Set the endEpoch parameter
            statement.setLong(2, endEpoch);

            // Set the programmingLanguage parameter
            statement.setString(3, programmingLanguage);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Retrieve the count
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public List<Long> findCreationDatesByTagsContaining(String tag) {
        // Create a list to store the creation_date values
        List<Long> creationDates = new ArrayList<>();

        // Prepare the SQL query
        String sqlQuery = """
                SELECT q.creation_date
                FROM question q
                JOIN question_tags qt ON q.question_id = qt.question_question_id
                WHERE qt.tags = ?
                """;


        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {

            // Set the tag parameter
            statement.setString(1, tag);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Iterate through the result set and retrieve the creation_date values
            while (resultSet.next()) {
                long creationDate = resultSet.getLong("creation_date");
                creationDates.add(creationDate);
            }

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creationDates;
    }

    public Integer countByIsAnswered(boolean isAnswered) {
        // Prepare the SQL query
        String sqlQuery = """
                SELECT COUNT(q)
                FROM question q
                WHERE q.is_answered = ?
                """;

        // Initialize the count
        int count = 0;

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {

            // Set the is_answered parameter
            statement.setBoolean(1, isAnswered);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Retrieve the count
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int countByIsAnsweredAndTagsContains(boolean isAnswered, String tag) {
        // Prepare the SQL query
        String sqlQuery = """
                SELECT COUNT(q)
                FROM question q
                JOIN question_tags qt ON q.question_id = qt.question_question_id
                WHERE q.is_answered = ? AND qt.tags = ?
                """;

        // Initialize the count
        int count = 0;

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {

            // Set the is_answered parameter
            statement.setBoolean(1, isAnswered);

            // Set the tag parameter
            statement.setString(2, tag);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Retrieve the count
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int countByIsClosed(boolean isClosed) {
        String sqlQuery;
        // Prepare the SQL query
        if (isClosed)
            sqlQuery = """
                    SELECT COUNT(*) AS closed_question_count
                    FROM question
                    WHERE closed_date > 0;
                    """;
        else {
            // if the question is not closed then is_closed = 0
            sqlQuery = """
                    SELECT COUNT(*) AS open_question_count
                    FROM question
                    WHERE closed_date = 0;
                    """;
        }

        // Initialize the count
        int count = 0;

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Retrieve the count
            // Process the result set
            if (resultSet.next()) {
                if (isClosed) {
                    count = resultSet.getInt("closed_question_count");
                } else {
                    count = resultSet.getInt("open_question_count");
                }
            }

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public int countByIsClosedAndTagsContains(boolean isClosed, String programmingLanguage) {
        String sqlQuery;
        if (isClosed) // if the question is closed then closed_date > 0
            sqlQuery = """
                    SELECT COUNT(*) AS closed_question_contain_tags_counter
                    FROM question q
                    JOIN question_tags qt ON q.question_id = qt.question_question_id
                    WHERE q.closed_date > 0 AND qt.tags = ?
                    """;

        else // if the question is not closed then closed_date = 0
            sqlQuery = """
                    SELECT COUNT(*) AS open_question_contain_tags_counter
                    FROM question q
                    JOIN question_tags qt ON q.question_id = qt.question_question_id
                    WHERE q.closed_date = 0 AND qt.tags = ?
                    """;

        int count = 0;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {

            // Set the tag parameter
            statement.setString(1, programmingLanguage);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result set
            if (resultSet.next()) {
                // Retrieve the count
                if (isClosed) {
                    count = resultSet.getInt("closed_question_contain_tags_counter");
                } else {
                    count = resultSet.getInt("open_question_contain_tags_counter");
                }
            }

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Question> findByTopScore(int limit) {
        // Prepare the SQL query
        String sqlQuery = """
                SELECT q.question_id, q.is_answered, q.title, q.link, q.score, q.answer_count, q.accepted_answer_id, q.view_count,
                       q.creation_date, q.closed_date, q.last_activity_date, q.last_edit_date,
                       qt.tags
                FROM question q
                INNER JOIN (
                    SELECT question_question_id, STRING_AGG(tags, ',') AS tags
                    FROM question_tags
                    GROUP BY question_question_id
                ) qt ON q.question_id = qt.question_question_id
                ORDER BY q.score DESC
                LIMIT ?
                """;

        List<Question> topQuestions = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {
            // Set the limit parameter
            statement.setInt(1, limit);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                // Map the row to a Question object using the helper method
                Question question = mapResultSetToQuestion(resultSet);

                // Add the question to the list
                topQuestions.add(question);
            }

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topQuestions;
    }

    public List<Question> findByTopScoreAndTagsContains(String programmingLanguage, int limit) {
        // Prepare the SQL query
        String sqlQuery = """
                SELECT q.question_id, q.is_answered, q.title, q.link, q.score, q.answer_count, q.accepted_answer_id, q.view_count,
                       q.creation_date, q.closed_date, q.last_activity_date, q.last_edit_date,
                       qt.tags
                FROM question q
                INNER JOIN (
                    SELECT question_question_id, STRING_AGG(tags, ',') AS tags
                    FROM question_tags
                    GROUP BY question_question_id
                ) qt ON q.question_id = qt.question_question_id
                WHERE qt.tags LIKE ?
                ORDER BY q.score DESC
                LIMIT ?
                """;

        List<Question> topQuestions = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {
            // Set the tag parameter with '%' to find any questions with the given programmingLanguage tag
            statement.setString(1, programmingLanguage);
            statement.setInt(2, limit);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                // Map the row to a Question object using the helper method
                Question question = mapResultSetToQuestion(resultSet);

                // Add the question to the list
                topQuestions.add(question);
            }

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topQuestions;
    }

    public List<Question> findByTopViews(int limit) {
        // Prepare the SQL query
        String sqlQuery = """
                SELECT q.question_id, q.is_answered, q.title, q.link, q.score, q.answer_count, q.accepted_answer_id, q.view_count,
                       q.creation_date, q.closed_date, q.last_activity_date, q.last_edit_date,
                       qt.tags
                FROM question q
                INNER JOIN (
                    SELECT question_question_id, STRING_AGG(tags, ',') AS tags
                    FROM question_tags
                    GROUP BY question_question_id
                ) qt ON q.question_id = qt.question_question_id
                ORDER BY q.view_count DESC
                LIMIT ?
                """;

        List<Question> topViewedQuestions = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {
            // Set the limit parameter
            statement.setInt(1, limit);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                // Map the row to a Question object using the helper method
                Question question = mapResultSetToQuestion(resultSet);

                // Add the question to the list
                topViewedQuestions.add(question);
            }

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topViewedQuestions;
    }

    public List<Question> findByTopViewsAndTagsContains(String programmingLanguage, int limit) {
        // Prepare the SQL query
        String sqlQuery = """
                SELECT q.question_id, q.is_answered, q.title, q.link, q.score, q.answer_count, q.accepted_answer_id, q.view_count,
                       q.creation_date, q.closed_date, q.last_activity_date, q.last_edit_date,
                       qt.tags
                FROM question q
                INNER JOIN (
                    SELECT question_question_id, STRING_AGG(tags, ',') AS tags
                    FROM question_tags
                    GROUP BY question_question_id
                ) qt ON q.question_id = qt.question_question_id
                WHERE qt.tags LIKE ?
                ORDER BY q.view_count DESC
                LIMIT ?
                """;

        List<Question> topViewedQuestionsWithTags = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)
        ) {
            // Set the tag parameter with '%' to find any questions with the given programmingLanguage tag
            statement.setString(1, programmingLanguage);
            statement.setInt(2, limit);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                // Map the row to a Question object using the helper method
                Question question = mapResultSetToQuestion(resultSet);

                // Add the question to the list
                topViewedQuestionsWithTags.add(question);
            }

            // Close the result set
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topViewedQuestionsWithTags;
    }

    // Helper method to map a result set to a Question object
    private Question mapResultSetToQuestion(ResultSet resultSet) throws SQLException {
        // Extract values from the result set
        long questionId = resultSet.getLong("question_id");
        boolean isAnswered = resultSet.getBoolean("is_answered");
        String title = resultSet.getString("title");
        String link = resultSet.getString("link");
        int score = resultSet.getInt("score");
        int answerCount = resultSet.getInt("answer_count");
        long acceptedAnswerId = resultSet.getLong("accepted_answer_id");
        int viewCount = resultSet.getInt("view_count");
        long creationDate = resultSet.getLong("creation_date");
        long closedDate = resultSet.getLong("closed_date");
        long lastActivityDate = resultSet.getLong("last_activity_date");
        long lastEditDate = resultSet.getLong("last_edit_date");
        String tags = resultSet.getString("tags");

        // Build the question object using the builder pattern

        return Question.builder()
                .question_id(questionId)
                .is_answered(isAnswered)
                .title(title)
                .link(link)
                .score(score)
                .answer_count(answerCount)
                .accepted_answer_id(acceptedAnswerId)
                .view_count(viewCount)
                .creation_date(creationDate)
                .closed_date(closedDate)
                .last_activity_date(lastActivityDate)
                .last_edit_date(lastEditDate)
                .tags(Arrays.asList(tags.split(","))) // Convert the comma-separated tags string to a List
                .build();
    }

}

