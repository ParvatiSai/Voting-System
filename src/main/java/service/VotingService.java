package service;

import dao.VoteDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Blockchain;
import model.Vote;
import util.HashUtil;

/**
 * Service class for voting logic.
 */
public class VotingService {
    private static final Logger LOGGER = Logger.getLogger(VotingService.class.getName());
    private final VoteDAO voteDAO = new VoteDAO();
    private final Blockchain blockchain = new Blockchain();

    /**
     * Casts a vote and adds to blockchain.
     */
    public boolean castVote(int voterId, int candidateId) {
        try {
            if (voteDAO.hasVoted(voterId)) {
                return false; // Already voted
            }
            String voterIdHash = HashUtil.hash(String.valueOf(voterId));
            blockchain.addBlock(voterIdHash, candidateId);
            // For simplicity, create vote with blockchain data
            Vote vote = new Vote(0, voterId, candidateId, blockchain.getChain().get(blockchain.getChain().size() - 1).getCurrentHash(),
                    blockchain.getChain().get(blockchain.getChain().size() - 2).getCurrentHash(), System.currentTimeMillis());
            voteDAO.createVote(vote);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error casting vote", e);
            return false;
        }
    }

    /**
     * Verifies blockchain integrity.
     */
    public boolean verifyBlockchain() {
        return blockchain.verifyChain();
    }
}
