package ui;

import service.AuthService;
import model.User;
import model.Admin;
import model.Student;
import util.ValidationUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Login UI using Swing - Enhanced with better design and validation.
 */
public class LoginUI extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(LoginUI.class.getName());
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private final AuthService authService;

    public LoginUI() {
        authService = new AuthService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("College Voting System - Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);

        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(new Color(240, 240, 245));

        // Header
        JLabel headerLabel = new JLabel("College Voting System", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(25, 118, 210));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Email label and field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(emailField, gbc);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(passwordField, gbc);

        // Show password toggle
        JCheckBox showPassword = new JCheckBox("Show password");
        showPassword.setFont(new Font("Arial", Font.PLAIN, 13));
        showPassword.setForeground(new Color(50, 50, 50));
        showPassword.setBackground(Color.WHITE);
        showPassword.addActionListener((ActionEvent e) -> {
            boolean selected = ((JCheckBox) e.getSource()).isSelected();
            if (selected) {
                passwordField.setEchoChar((char) 0);
            } else {
                // Reset to default echo char
                passwordField.setEchoChar((new JPasswordField()).getEchoChar());
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        formPanel.add(showPassword, gbc);

    // Buttons panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    buttonPanel.setBackground(Color.WHITE);

    // Login button
    loginButton = new JButton("Login");
    loginButton.setFont(new Font("Arial", Font.BOLD, 14));
    loginButton.setPreferredSize(new Dimension(120, 35));
    loginButton.setEnabled(true);
    styleButton(loginButton, new Color(25, 118, 210), Color.WHITE);
    loginButton.addActionListener(new LoginAction());
    buttonPanel.add(loginButton);

    // Register button
    registerButton = new JButton("Register");
    registerButton.setFont(new Font("Arial", Font.BOLD, 14));
    registerButton.setPreferredSize(new Dimension(120, 35));
    registerButton.setEnabled(true);
    styleButton(registerButton, new Color(56, 142, 60), Color.WHITE);
    registerButton.addActionListener(new RegisterAction());
    buttonPanel.add(registerButton);

    gbc.gridx = 0;
    gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Footer
        JLabel footerLabel = new JLabel("Secure Blockchain-Based Voting", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(Color.GRAY);
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Enter key support
        getRootPane().setDefaultButton(loginButton);
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(bg.darker(), 1, true));
    }
    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            // Validation
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginUI.this,
                    "Please enter both email and password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!ValidationUtil.isValidEmail(email)) {
                JOptionPane.showMessageDialog(LoginUI.this,
                    "Please enter a valid email address.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Attempt login
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                User user = authService.login(email, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(LoginUI.this,
                        "Welcome, " + user.getName() + "!",
                        "Login Successful",
                        JOptionPane.INFORMATION_MESSAGE);

                    if (user instanceof Admin admin) {
                        new AdminDashboard(admin).setVisible(true);
                    } else if (user instanceof Student student) {
                        new VotingUI(student).setVisible(true);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this,
                        "Invalid email or password. Please try again.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(LoginUI.this,
                    "An unexpected error occurred: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.SEVERE, "Login unexpected error", ex);
            } finally {
                setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showRegistrationDialog();
        }
    }

    private void showRegistrationDialog() {
        JDialog registerDialog = new JDialog(this, "Register New User", true);
        registerDialog.setSize(450, 400);
        registerDialog.setLocationRelativeTo(this);
        registerDialog.setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Name
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panel.add(new JLabel("Full Name:"), gbc);
        JTextField nameField = new JTextField(20);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panel.add(nameField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panel.add(new JLabel("Email:"), gbc);
        JTextField regEmailField = new JTextField(20);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panel.add(regEmailField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        panel.add(new JLabel("Password:"), gbc);
        JPasswordField regPasswordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panel.add(regPasswordField, gbc);

        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        panel.add(new JLabel("Confirm Password:"), gbc);
        JPasswordField confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panel.add(confirmPasswordField, gbc);

        // Role
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        panel.add(new JLabel("Role:"), gbc);
        String[] roles = {"Student", "Admin"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panel.add(roleCombo, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton submitButton = new JButton("Register");
        submitButton.setBackground(new Color(76, 175, 80));
        submitButton.setForeground(Color.WHITE);
        submitButton.setPreferredSize(new Dimension(100, 30));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                source.setEnabled(false);
            String name = nameField.getText().trim();
            String email = regEmailField.getText().trim();
            String password = new String(regPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String role = roleCombo.getSelectedItem().toString().toLowerCase();

            // Validation
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(registerDialog,
                    "All fields are required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!ValidationUtil.isValidEmail(email)) {
                JOptionPane.showMessageDialog(registerDialog,
                    "Please enter a valid email address.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!ValidationUtil.isValidPassword(password)) {
                JOptionPane.showMessageDialog(registerDialog,
                    "Password must be at least 6 characters long.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(registerDialog,
                    "Passwords do not match.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Register user
            try {
                User user = role.equals("admin") 
                    ? new Admin(0, name, email, password) 
                    : new Student(0, name, email, password);
                
                if (authService.register(user)) {
                    JOptionPane.showMessageDialog(registerDialog,
                        "Registration successful! You can now login.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    registerDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(registerDialog,
                        "Registration failed. Email may already be in use.",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(registerDialog,
                    "An unexpected error occurred: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.SEVERE, "Registration unexpected error", ex);
            } finally {
                source.setEnabled(true);
            }
            }
        });
        buttonPanel.add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(244, 67, 54));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener((ActionEvent e) -> {
            if (e.getSource() == cancelButton) {
                registerDialog.dispose();
            }
        });
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        registerDialog.add(panel);
        registerDialog.setVisible(true);
    }
}
