package nidhal.stackoverflowstatus.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class QuestionDao {

    private final DataSource dataSource;

    public List<Long> findCreationDatesByTagsContaining(String tag) {
        // Create a list to store the creation_date values
        List<Long> creationDates = new ArrayList<>();

        // Prepare the SQL query
        String sqlQuery = "SELECT q.creation_date " +
                          "FROM question q " +
                          "JOIN question_tags qt ON q.question_id = qt.question_question_id " +
                          "WHERE qt.tags = ?";


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

    public int countByIsAnsweredAndTagsContains(boolean isAnswered, String tag) {
        // Prepare the SQL query
        String sqlQuery = "SELECT COUNT(q) " +
                          "FROM question q " +
                          "JOIN question_tags qt ON q.question_id = qt.question_question_id " +
                          "WHERE q.is_answered = ? AND qt.tags = ?";

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

    public Integer countByIsAnswered(boolean isAnswered) {
        // Prepare the SQL query
        String sqlQuery = "SELECT COUNT(q) " +
                          "FROM question q " +
                          "WHERE q.is_answered = ?";

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

    public int countAll() {
        // Prepare the SQL query
        String sqlQuery = "SELECT COUNT(q) " +
                          "FROM question q";

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

    public int countByTagsContains(String programmingLanguage) {
        // Prepare the SQL query
        String sqlQuery = "SELECT COUNT(q) " +
                          "FROM question q " +
                          "JOIN question_tags qt ON q.question_id = qt.question_question_id " +
                          "WHERE qt.tags = ?";

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

    public int countByCreationDateBetweenAndTagsContains(long startEpoch, long endEpoch, String programmingLanguage) {
        // Prepare the SQL query
        String sqlQuery = "SELECT COUNT(q) " +
                          "FROM question q " +
                          "JOIN question_tags qt ON q.question_id = qt.question_question_id " +
                          "WHERE q.creation_date BETWEEN ? AND ? AND qt.tags = ?";

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
}

