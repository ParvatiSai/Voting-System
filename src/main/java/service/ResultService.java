package service;

import dao.VoteDAO;
import model.Vote;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for result processing.
 */
public class ResultService {
    private VoteDAO voteDAO = new VoteDAO();

    /**
     * Computes vote tally per candidate.
     */
    public Map<Integer, Integer> getVoteTally() {
        Map<Integer, Integer> tally = new HashMap<>();
        try {
            List<Vote> votes = voteDAO.getAllVotes();
            for (Vote vote : votes) {
                tally.put(vote.getCandidateId(), tally.getOrDefault(vote.getCandidateId(), 0) + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tally;
    }
}
