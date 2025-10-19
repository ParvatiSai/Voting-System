package model;

import util.HashUtil;

/**
 * Represents a Block in the blockchain.
 * Each block contains vote data and is linked to the previous block.
 */
public class Block {
    private int blockId;
    private String voterIdHash; // Hashed voter ID for privacy
    private int candidateId;
    private long timestamp;
    private String prevHash;
    private String currentHash;

    public Block(int blockId, String voterIdHash, int candidateId, long timestamp, String prevHash) {
        this.blockId = blockId;
        this.voterIdHash = voterIdHash;
        this.candidateId = candidateId;
        this.timestamp = timestamp;
        this.prevHash = prevHash;
        this.currentHash = calculateHash();
    }

    // Calculate hash based on block data
    private String calculateHash() {
        String data = blockId + voterIdHash + candidateId + timestamp + prevHash;
        return HashUtil.hash(data);
    }

    // Getters
    public int getBlockId() { return blockId; }
    public String getVoterIdHash() { return voterIdHash; }
    public int getCandidateId() { return candidateId; }
    public long getTimestamp() { return timestamp; }
    public String getPrevHash() { return prevHash; }
    public String getCurrentHash() { return currentHash; }
}
