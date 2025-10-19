package model;

/**
 * Represents a Vote cast by a student.
 */
public class Vote {
    private int voteId;
    private int voterId;
    private int candidateId;
    private String hash;
    private String prevHash;
    private long timestamp;

    public Vote(int voteId, int voterId, int candidateId, String hash, String prevHash, long timestamp) {
        this.voteId = voteId;
        this.voterId = voterId;
        this.candidateId = candidateId;
        this.hash = hash;
        this.prevHash = prevHash;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public int getVoteId() { return voteId; }
    public void setVoteId(int voteId) { this.voteId = voteId; }

    public int getVoterId() { return voterId; }
    public void setVoterId(int voterId) { this.voterId = voterId; }

    public int getCandidateId() { return candidateId; }
    public void setCandidateId(int candidateId) { this.candidateId = candidateId; }

    public String getHash() { return hash; }
    public void setHash(String hash) { this.hash = hash; }

    public String getPrevHash() { return prevHash; }
    public void setPrevHash(String prevHash) { this.prevHash = prevHash; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
