package app;

import java.util.logging.Level;
import java.util.logging.Logger;
import ui.LoginUI;

/**
 * Main entry point for the Voting System application.
 * Initializes the application and launches the login UI.
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Set System Look & Feel for native appearance
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.INFO, "System Look & Feel not found", ex);
        } catch (InstantiationException ex) {
            LOGGER.log(Level.INFO, "Could not instantiate Look & Feel", ex);
        } catch (IllegalAccessException ex) {
            LOGGER.log(Level.INFO, "Illegal access setting Look & Feel", ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.INFO, "Unsupported Look & Feel", ex);
        }

        // Set a default uncaught exception handler to catch UI thread errors
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            LOGGER.log(Level.SEVERE, "Uncaught error on thread {0}", t.getName());
            LOGGER.log(Level.SEVERE, "Stacktrace", e);
            javax.swing.SwingUtilities.invokeLater(() -> javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "An unexpected error occurred. Please check logs for details.",
                    "Application Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            ));
        });

        // Launch the login UI
        javax.swing.SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
    }
}
