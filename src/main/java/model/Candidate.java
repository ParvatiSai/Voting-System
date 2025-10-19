package model;

/**
 * Represents a Candidate in an election.
 */
public class Candidate {
    private int candidateId;
    private String name;
    private String department;

    public Candidate(int candidateId, String name, String department) {
        this.candidateId = candidateId;
        this.name = name;
        this.department = department;
    }

    // Getters and setters
    public int getCandidateId() { return candidateId; }
    public void setCandidateId(int candidateId) { this.candidateId = candidateId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return name + " (" + department + ")";
    }
}
