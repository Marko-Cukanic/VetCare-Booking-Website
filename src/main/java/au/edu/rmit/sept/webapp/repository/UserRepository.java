package au.edu.rmit.sept.webapp.repository;

import au.edu.rmit.sept.webapp.H2DatabaseConnection;
import au.edu.rmit.sept.webapp.model.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    // Method to save a user to the database
    public User save(User user) {
        String sql = "INSERT INTO users (fullname, email, password) VALUES (?, ?, ?)";

        try (Connection connection = H2DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the INSERT statement
            statement.setString(1, user.fullname());
            statement.setString(2, user.email());
            statement.setString(3, user.password());

            // Execute the INSERT statement
            statement.executeUpdate();

            // Retrieve the generated ID (if any)
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    return new User(id, user.fullname(), user.email(), user.password());
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Return null if the user could not be saved
    }

    // Method to find a user by their email address
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection connection = H2DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the parameter for the SELECT statement
            statement.setString(1, email);

            // Execute the SELECT statement and process the result set
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String fullname = resultSet.getString("fullname");
                    String password = resultSet.getString("password");
                    return new User(id, fullname, email, password);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Return null if the user is not found
    }
}
