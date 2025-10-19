package dao;

import model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Candidate operations.
 */
public class CandidateDAO {

    /**
     * Creates a new candidate.
     */
    public void createCandidate(Candidate candidate) throws SQLException {
        String sql = "INSERT INTO candidates (name, department) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, candidate.getName());
            stmt.setString(2, candidate.getDepartment());
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves a candidate by ID.
     */
    public Candidate getCandidateById(int candidateId) throws SQLException {
        String sql = "SELECT * FROM candidates WHERE candidate_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, candidateId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String department = rs.getString("department");
                return new Candidate(candidateId, name, department);
            }
        }
        return null;
    }

    /**
     * Retrieves all candidates.
     */
    public List<Candidate> getAllCandidates() throws SQLException {
        List<Candidate> candidates = new ArrayList<>();
        String sql = "SELECT * FROM candidates";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int candidateId = rs.getInt("candidate_id");
                String name = rs.getString("name");
                String department = rs.getString("department");
                candidates.add(new Candidate(candidateId, name, department));
            }
        }
        return candidates;
    }

    /**
     * Updates a candidate.
     */
    public void updateCandidate(Candidate candidate) throws SQLException {
        String sql = "UPDATE candidates SET name = ?, department = ? WHERE candidate_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, candidate.getName());
            stmt.setString(2, candidate.getDepartment());
            stmt.setInt(3, candidate.getCandidateId());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a candidate by ID.
     */
    public void deleteCandidate(int candidateId) throws SQLException {
        String sql = "DELETE FROM candidates WHERE candidate_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, candidateId);
            stmt.executeUpdate();
        }
    }
}
