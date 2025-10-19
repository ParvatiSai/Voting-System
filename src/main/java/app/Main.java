package app;

import ui.LoginUI;

/**
 * Main entry point for the Voting System application.
 * Initializes the application and launches the login UI.
 */
public class Main {
    public static void main(String[] args) {
        // Launch the login UI
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginUI().setVisible(true);
        });
    }
}
