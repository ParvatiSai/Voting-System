package ui;

import dao.CandidateDAO;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.Candidate;
import service.ResultService;

/**
 * Result UI to display election results - Enhanced with detailed candidate information.
 */
public class ResultUI extends JFrame {
    private JTextArea resultArea;
    private ResultService resultService;
    private CandidateDAO candidateDAO;

    public ResultUI() {
        resultService = new ResultService();
        candidateDAO = new CandidateDAO();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Election Results");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 245));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(33, 150, 243));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel headerLabel = new JLabel("Live Election Results");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Results panel
        JPanel resultsPanel = new JPanel(new BorderLayout(5, 5));
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(250, 250, 250));
        resultArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(resultsPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 240, 245));

        JButton refreshButton = new JButton("Refresh Results");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setPreferredSize(new Dimension(150, 35));
        refreshButton.setBackground(new Color(76, 175, 80));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> displayResults());
        buttonPanel.add(refreshButton);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setPreferredSize(new Dimension(150, 35));
        closeButton.setBackground(new Color(244, 67, 54));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        displayResults();
    }

    private void displayResults() {
        try {
            Map<Integer, Integer> tally = resultService.getVoteTally();
            StringBuilder sb = new StringBuilder();
            
            sb.append("╔════════════════════════════════════════════════════════════════════╗\n");
            sb.append("║                     ELECTION RESULTS                               ║\n");
            sb.append("╚════════════════════════════════════════════════════════════════════╝\n\n");

            if (tally.isEmpty()) {
                sb.append("  No votes have been cast yet.\n");
                sb.append("  The election is open. Cast your vote now!\n");
            } else {
                int totalVotes = tally.values().stream().mapToInt(Integer::intValue).sum();
                sb.append(String.format("  Total Votes Cast: %d\n\n", totalVotes));
                sb.append("────────────────────────────────────────────────────────────────────\n\n");

                // Find winner
                int maxVotes = tally.values().stream().mapToInt(Integer::intValue).max().orElse(0);

                // Display each candidate's results
                int rank = 1;
                for (Map.Entry<Integer, Integer> entry : tally.entrySet()) {
                    try {
                        Candidate candidate = candidateDAO.getCandidateById(entry.getKey());
                        if (candidate != null) {
                            double percentage = (entry.getValue() * 100.0) / totalVotes;
                            
                            sb.append(String.format("  Rank #%d\n", rank++));
                            sb.append(String.format("  Name:       %s", candidate.getName()));
                            if (entry.getValue() == maxVotes) {
                                sb.append(" 🏆 LEADING");
                            }
                            sb.append("\n");
                            sb.append(String.format("  Department: %s\n", candidate.getDepartment()));
                            sb.append(String.format("  Votes:      %d (%.1f%%)\n", entry.getValue(), percentage));
                            
                            // Visual bar
                            int barLength = (int) (percentage / 2);
                            sb.append("  Progress:   [");
                            for (int i = 0; i < 50; i++) {
                                sb.append(i < barLength ? "█" : "░");
                            }
                            sb.append("]\n\n");
                        } else {
                            sb.append(String.format("  Candidate ID %d: %d votes\n\n", 
                                entry.getKey(), entry.getValue()));
                        }
                    } catch (SQLException e) {
                        sb.append(String.format("  Candidate ID %d: %d votes\n\n", 
                            entry.getKey(), entry.getValue()));
                    }
                }

                sb.append("────────────────────────────────────────────────────────────────────\n");
            }

            sb.append("\n  Last Updated: ").append(java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");

            resultArea.setText(sb.toString());
        } catch (Exception e) {
            resultArea.setText("Error loading results:\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}
