package model;

/**
 * Represents an Admin user in the voting system.
 * Extends User and implements admin-specific behaviors.
 */
public class Admin extends User {

    public Admin(int userId, String name, String email, String password) {
        super(userId, name, email, password, "admin");
    }

    @Override
    public void performRoleAction() {
        // Admin-specific action, e.g., managing elections
        System.out.println("Admin " + name + " is performing election management action.");
    }
}
