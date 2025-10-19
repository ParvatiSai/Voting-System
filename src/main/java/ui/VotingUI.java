package ui;

import dao.CandidateDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.Candidate;
import model.Student;
import service.VotingService;

/**
 * Voting UI for students - Enhanced with better design and functionality.
 */
public class VotingUI extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(VotingUI.class.getName());
    private JComboBox<Candidate> candidateCombo;
    private JButton voteButton;
    private JButton viewResultsButton;
    private JButton logoutButton;
    private JTextArea candidateInfoArea;
    private VotingService votingService;
    private CandidateDAO candidateDAO;
    private Student student;

    public VotingUI(Student student) {
        this.student = student;
        votingService = new VotingService();
        candidateDAO = new CandidateDAO();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Student Voting Portal - " + student.getName());
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 245));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(25, 118, 210));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel headerLabel = new JLabel("Cast Your Vote");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JLabel userLabel = new JLabel("Logged in as: " + student.getName());
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        headerPanel.add(userLabel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        // Candidate selection panel
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel selectLabel = new JLabel("Select Candidate:");
        selectLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        selectionPanel.add(selectLabel, gbc);

        candidateCombo = new JComboBox<>();
        candidateCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        candidateCombo.setPreferredSize(new Dimension(400, 35));
        candidateCombo.addActionListener(e -> updateCandidateInfo());
        loadCandidates();
        gbc.gridy = 1;
        selectionPanel.add(candidateCombo, gbc);

        contentPanel.add(selectionPanel, BorderLayout.NORTH);

        // Candidate info panel
        JPanel infoPanel = new JPanel(new BorderLayout(5, 5));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Candidate Information",
            0,
            0,
            new Font("Arial", Font.BOLD, 14)
        ));

        candidateInfoArea = new JTextArea();
        candidateInfoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        candidateInfoArea.setEditable(false);
        candidateInfoArea.setBackground(new Color(250, 250, 250));
        candidateInfoArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        candidateInfoArea.setLineWrap(true);
        candidateInfoArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(candidateInfoArea);
        scrollPane.setPreferredSize(new Dimension(0, 150));
        infoPanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(infoPanel, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 240, 245));

        voteButton = new JButton("Cast Vote");
        voteButton.setFont(new Font("Arial", Font.BOLD, 16));
        voteButton.setPreferredSize(new Dimension(150, 40));
        voteButton.setBackground(new Color(76, 175, 80));
        voteButton.setForeground(Color.WHITE);
        voteButton.setFocusPainted(false);
        voteButton.addActionListener(new VoteAction());
        buttonPanel.add(voteButton);

        viewResultsButton = new JButton("View Results");
        viewResultsButton.setFont(new Font("Arial", Font.BOLD, 16));
        viewResultsButton.setPreferredSize(new Dimension(150, 40));
        viewResultsButton.setBackground(new Color(33, 150, 243));
        viewResultsButton.setForeground(Color.WHITE);
        viewResultsButton.setFocusPainted(false);
        viewResultsButton.addActionListener(e -> new ResultUI().setVisible(true));
        buttonPanel.add(viewResultsButton);

        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setPreferredSize(new Dimension(150, 40));
        logoutButton.setBackground(new Color(244, 67, 54));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginUI();
            }
        });
        buttonPanel.add(logoutButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        updateCandidateInfo();
    }

    private void loadCandidates() {
        try {
            List<Candidate> candidates = candidateDAO.getAllCandidates();
            candidateCombo.removeAllItems();
            for (Candidate c : candidates) {
                candidateCombo.addItem(c);
            }
            if (candidates.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No candidates available for voting.",
                    "No Candidates",
                    JOptionPane.INFORMATION_MESSAGE);
                voteButton.setEnabled(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading candidates: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Error loading candidates", e);
            voteButton.setEnabled(false);
        }
    }

    private void updateCandidateInfo() {
        Candidate selected = (Candidate) candidateCombo.getSelectedItem();
        if (selected != null) {
            candidateInfoArea.setText(
                "Name: " + selected.getName() + "\n\n" +
                "Department: " + selected.getDepartment() + "\n\n" +
                "Candidate ID: " + selected.getCandidateId() + "\n\n" +
                "Please review the candidate information carefully before casting your vote."
            );
        } else {
            candidateInfoArea.setText("No candidate selected.");
        }
    }

    private class VoteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Candidate selected = (Candidate) candidateCombo.getSelectedItem();
            if (selected == null) {
                JOptionPane.showMessageDialog(VotingUI.this,
                    "Please select a candidate to vote for.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Confirm vote
            int confirm = JOptionPane.showConfirmDialog(VotingUI.this,
                "Are you sure you want to vote for:\n" + 
                selected.getName() + " (" + selected.getDepartment() + ")?",
                "Confirm Vote",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Cast vote
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                boolean success = votingService.castVote(student.getUserId(), selected.getCandidateId());
                if (success) {
                    JOptionPane.showMessageDialog(VotingUI.this,
                        "Your vote has been successfully recorded!\n" +
                        "Thank you for participating in the election.",
                        "Vote Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                    voteButton.setEnabled(false);
                    voteButton.setText("Already Voted");
                } else {
                    JOptionPane.showMessageDialog(VotingUI.this,
                        "Vote failed. You may have already voted in this election.",
                        "Vote Failed",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(VotingUI.this,
                    "Error casting vote: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                LOGGER.log(Level.SEVERE, "Error casting vote", ex);
            } finally {
                setCursor(Cursor.getDefaultCursor());
            }
        }
    }
}
