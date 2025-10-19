package dao;

import model.User;
import model.Student;
import model.Admin;
import util.HashUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for User operations.
 */
public class UserDAO {

    /**
     * Creates a new user in the database.
     */
    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, HashUtil.hash(user.getPassword())); // Hash password
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves a user by email.
     */
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String role = rs.getString("role");
                if ("student".equals(role)) {
                    return new Student(userId, name, email, password);
                } else if ("admin".equals(role)) {
                    return new Admin(userId, name, email, password);
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all users.
     */
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                if ("student".equals(role)) {
                    users.add(new Student(userId, name, email, password));
                } else if ("admin".equals(role)) {
                    users.add(new Admin(userId, name, email, password));
                }
            }
        }
        return users;
    }

    /**
     * Updates a user.
     */
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ?, password = ?, role = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, HashUtil.hash(user.getPassword()));
            stmt.setString(4, user.getRole());
            stmt.setInt(5, user.getUserId());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a user by ID.
     */
    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
}
