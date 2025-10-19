package model;

/**
 * Represents a Student user in the voting system.
 * Extends User and implements student-specific behaviors.
 */
public class Student extends User {

    public Student(int userId, String name, String email, String password) {
        super(userId, name, email, password, "student");
    }

    @Override
    public void performRoleAction() {
        // Student-specific action, e.g., voting
        System.out.println("Student " + name + " is performing voting action.");
    }
}
