package model;

/**
 * Base class for users in the voting system.
 * Represents common attributes and behaviors for all user types.
 */
public abstract class User {
    protected int userId;
    protected String name;
    protected String email;
    protected String password; // Hashed password
    protected String role; // "student" or "admin"

    public User(int userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    /**
     * Abstract method to be implemented by subclasses for role-specific actions.
     */
    public abstract void performRoleAction();
}
