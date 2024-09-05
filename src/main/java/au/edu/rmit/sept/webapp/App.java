package au.edu.rmit.sept.webapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        // MySQL connection details
        String url = "jdbc:mysql://localhost:3306/vetcaredb";
        String user = "vetuser";
        String password = "1234";

        try {
            // Step 1: Establish a connection to the MySQL database
            Connection connection = DriverManager.getConnection(url, user, password);

            // Step 2: Create a table named 'users' if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255))";
            PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL);
            createTableStatement.executeUpdate();

            // Step 3: Insert a new user into the 'users' table
            String insertDataSQL = "INSERT INTO users (name) VALUES (?)";
            PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL);
            insertDataStatement.setString(1, "John Doe");
            insertDataStatement.executeUpdate();

            // Step 4: Query the data from the 'users' table
            String selectDataSQL = "SELECT * FROM users";
            PreparedStatement selectDataStatement = connection.prepareStatement(selectDataSQL);
            ResultSet resultSet = selectDataStatement.executeQuery();

            // Step 5: Print out the data from the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println("ID: " + id + ", Name: " + name);
            }

            // Closing the resources
            resultSet.close();
            selectDataStatement.close();
            insertDataStatement.close();
            createTableStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
