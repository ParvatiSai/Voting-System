package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Blockchain for storing votes securely.
 * Manages a chain of blocks and verifies integrity.
 */
public class Blockchain {
    private List<Block> chain;
    private static final String GENESIS_PREV_HASH = "0";

    public Blockchain() {
        chain = new ArrayList<>();
        // Add genesis block
        chain.add(new Block(0, "", 0, System.currentTimeMillis(), GENESIS_PREV_HASH));
    }

    /**
     * Adds a new block to the chain.
     */
    public void addBlock(String voterIdHash, int candidateId) {
        Block prevBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(chain.size(), voterIdHash, candidateId, System.currentTimeMillis(), prevBlock.getCurrentHash());
        chain.add(newBlock);
    }

    /**
     * Verifies the integrity of the blockchain.
     */
    public boolean verifyChain() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            if (!current.getPrevHash().equals(previous.getCurrentHash())) {
                return false;
            }
        }
        return true;
    }

    public List<Block> getChain() { return chain; }
}
