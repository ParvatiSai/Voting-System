package dao;

import model.Vote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Vote operations.
 */
public class VoteDAO {

    /**
     * Creates a new vote.
     */
    public void createVote(Vote vote) throws SQLException {
        String sql = "INSERT INTO votes (voter_id, candidate_id, hash, prev_hash, timestamp) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vote.getVoterId());
            stmt.setInt(2, vote.getCandidateId());
            stmt.setString(3, vote.getHash());
            stmt.setString(4, vote.getPrevHash());
            stmt.setLong(5, vote.getTimestamp());
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves a vote by ID.
     */
    public Vote getVoteById(int voteId) throws SQLException {
        String sql = "SELECT * FROM votes WHERE vote_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, voteId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int voterId = rs.getInt("voter_id");
                int candidateId = rs.getInt("candidate_id");
                String hash = rs.getString("hash");
                String prevHash = rs.getString("prev_hash");
                long timestamp = rs.getLong("timestamp");
                return new Vote(voteId, voterId, candidateId, hash, prevHash, timestamp);
            }
        }
        return null;
    }

    /**
     * Retrieves all votes.
     */
    public List<Vote> getAllVotes() throws SQLException {
        List<Vote> votes = new ArrayList<>();
        String sql = "SELECT * FROM votes";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int voteId = rs.getInt("vote_id");
                int voterId = rs.getInt("voter_id");
                int candidateId = rs.getInt("candidate_id");
                String hash = rs.getString("hash");
                String prevHash = rs.getString("prev_hash");
                long timestamp = rs.getLong("timestamp");
                votes.add(new Vote(voteId, voterId, candidateId, hash, prevHash, timestamp));
            }
        }
        return votes;
    }

    /**
     * Checks if a voter has already voted in a specific election.
     * Assuming election_id is added to votes table or logic here.
     * For simplicity, check if voter has voted at all.
     */
    public boolean hasVoted(int voterId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM votes WHERE voter_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, voterId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}
