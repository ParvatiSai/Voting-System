package ui;

import service.ResultService;
import dao.CandidateDAO;
import model.Candidate;
import model.Admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Admin Dashboard UI - Enhanced with tabbed interface and comprehensive management.
 */
public class AdminDashboard extends JFrame {
    private ResultService resultService;
    private CandidateDAO candidateDAO;
    private Admin admin;
    private JTable candidateTable;
    private DefaultTableModel tableModel;
    private JTextArea resultsArea;

    public AdminDashboard(Admin admin) {
        this.admin = admin;
        resultService = new ResultService();
        candidateDAO = new CandidateDAO();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Admin Dashboard - " + admin.getName());
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 240, 245));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(156, 39, 176));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel headerLabel = new JLabel("Admin Control Panel");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JLabel userLabel = new JLabel("Admin: " + admin.getName());
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        headerPanel.add(userLabel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Tab 1: Candidate Management
        JPanel candidatePanel = createCandidateManagementPanel();
        tabbedPane.addTab("Manage Candidates", new ImageIcon(), candidatePanel, "Add, edit, and delete candidates");

        // Tab 2: View Results
        JPanel resultsPanel = createResultsPanel();
        tabbedPane.addTab("Election Results", new ImageIcon(), resultsPanel, "View current election results");

        // Tab 3: System Info
        JPanel systemPanel = createSystemInfoPanel();
        tabbedPane.addTab("System Info", new ImageIcon(), systemPanel, "System and blockchain information");

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Bottom button panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(new Color(240, 240, 245));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setPreferredSize(new Dimension(120, 35));
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
        bottomPanel.add(logoutButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createCandidateManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = new JButton("Add Candidate");
        addButton.setFont(new Font("Arial", Font.BOLD, 13));
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(new Dimension(150, 35));
        addButton.setFocusPainted(false);
        addButton.addActionListener(new AddCandidateAction());
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit Candidate");
        editButton.setFont(new Font("Arial", Font.BOLD, 13));
        editButton.setBackground(new Color(33, 150, 243));
        editButton.setForeground(Color.WHITE);
        editButton.setPreferredSize(new Dimension(150, 35));
        editButton.setFocusPainted(false);
        editButton.addActionListener(new EditCandidateAction());
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete Candidate");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 13));
        deleteButton.setBackground(new Color(244, 67, 54));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setPreferredSize(new Dimension(150, 35));
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new DeleteCandidateAction());
        buttonPanel.add(deleteButton);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 13));
        refreshButton.setBackground(new Color(255, 152, 0));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setPreferredSize(new Dimension(120, 35));
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadCandidates());
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        // Candidate table
        String[] columns = {"ID", "Name", "Department"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        candidateTable = new JTable(tableModel);
        candidateTable.setFont(new Font("Arial", Font.PLAIN, 13));
        candidateTable.setRowHeight(25);
        candidateTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        candidateTable.getTableHeader().setBackground(new Color(63, 81, 181));
        candidateTable.getTableHeader().setForeground(Color.WHITE);
        candidateTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(candidateTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadCandidates();

        return panel;
    }

    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton refreshButton = new JButton("Refresh Results");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 13));
        refreshButton.setBackground(new Color(33, 150, 243));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setPreferredSize(new Dimension(150, 35));
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> loadResults());
        buttonPanel.add(refreshButton);

        JButton exportButton = new JButton("Export Results");
        exportButton.setFont(new Font("Arial", Font.BOLD, 13));
        exportButton.setBackground(new Color(76, 175, 80));
        refreshButton.setForeground(Color.WHITE);
        exportButton.setPreferredSize(new Dimension(150, 35));
        exportButton.setFocusPainted(false);
        exportButton.addActionListener(e -> exportResults());
        buttonPanel.add(exportButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        // Results area
        resultsArea = new JTextArea();
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultsArea.setEditable(false);
        resultsArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(resultsArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadResults();

        return panel;
    }

    private JPanel createSystemInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextArea infoArea = new JTextArea();
        infoArea.setFont(new Font("Arial", Font.PLAIN, 13));
        infoArea.setEditable(false);
        infoArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        StringBuilder info = new StringBuilder();
        info.append("=== VOTING SYSTEM INFORMATION ===\n\n");
        info.append("System: College Voting System\n");
        info.append("Version: 1.0\n");
        info.append("Technology: Java Swing + MySQL + Blockchain\n\n");
        info.append("=== FEATURES ===\n");
        info.append("- Secure blockchain-based vote storage\n");
        info.append("- SHA-256 hash verification\n");
        info.append("- Real-time result tracking\n");
        info.append("- Role-based access (Admin/Student)\n\n");
        info.append("=== DATABASE ===\n");
        info.append("Database: voting_system\n");
        info.append("Tables: users, candidates, votes\n\n");
        info.append("=== ADMIN CAPABILITIES ===\n");
        info.append("- Manage candidates (Add/Edit/Delete)\n");
        info.append("- View real-time election results\n");
        info.append("- Export results for reporting\n");
        info.append("- Monitor system integrity\n\n");
        info.append("=== SECURITY ===\n");
        info.append("- Password hashing with SHA-256\n");
        info.append("- Email validation\n");
        info.append("- One vote per student enforcement\n");
        info.append("- Blockchain integrity verification\n");

        infoArea.setText(info.toString());

        JScrollPane scrollPane = new JScrollPane(infoArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadCandidates() {
        try {
            tableModel.setRowCount(0);
            List<Candidate> candidates = candidateDAO.getAllCandidates();
            for (Candidate c : candidates) {
                tableModel.addRow(new Object[]{
                    c.getCandidateId(),
                    c.getName(),
                    c.getDepartment()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error loading candidates: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadResults() {
        try {
            Map<Integer, Integer> tally = resultService.getVoteTally();
            StringBuilder sb = new StringBuilder();
            sb.append("========================================\n");
            sb.append("       ELECTION RESULTS\n");
            sb.append("========================================\n\n");

            if (tally.isEmpty()) {
                sb.append("No votes have been cast yet.\n");
            } else {
                int totalVotes = tally.values().stream().mapToInt(Integer::intValue).sum();
                sb.append("Total Votes Cast: ").append(totalVotes).append("\n\n");

                // Get candidate details
                for (Map.Entry<Integer, Integer> entry : tally.entrySet()) {
                    try {
                        Candidate candidate = candidateDAO.getCandidateById(entry.getKey());
                        if (candidate != null) {
                            double percentage = (entry.getValue() * 100.0) / totalVotes;
                            sb.append(String.format("%-30s | %-25s | %3d votes (%.1f%%)\n",
                                candidate.getName(),
                                candidate.getDepartment(),
                                entry.getValue(),
                                percentage));
                        } else {
                            sb.append(String.format("Candidate ID %d: %d votes\n",
                                entry.getKey(), entry.getValue()));
                        }
                    } catch (SQLException e) {
                        sb.append(String.format("Candidate ID %d: %d votes\n",
                            entry.getKey(), entry.getValue()));
                    }
                }
            }

            sb.append("\n========================================\n");
            resultsArea.setText(sb.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading results: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void exportResults() {
        JOptionPane.showMessageDialog(this,
            "Results exported successfully!\n(Export functionality would save to a file)",
            "Export Complete",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private class AddCandidateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dialog = new JDialog(AdminDashboard.this, "Add New Candidate", true);
            dialog.setSize(400, 250);
            dialog.setLocationRelativeTo(AdminDashboard.this);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(8, 8, 8, 8);

            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Candidate Name:"), gbc);
            JTextField nameField = new JTextField(20);
            gbc.gridx = 1;
            panel.add(nameField, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(new JLabel("Department:"), gbc);
            JTextField deptField = new JTextField(20);
            gbc.gridx = 1;
            panel.add(deptField, gbc);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            JButton addBtn = new JButton("Add");
            addBtn.setBackground(new Color(76, 175, 80));
            addBtn.setForeground(Color.WHITE);
            addBtn.addActionListener(ev -> {
                String name = nameField.getText().trim();
                String dept = deptField.getText().trim();

                if (name.isEmpty() || dept.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                        "All fields are required.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    Candidate candidate = new Candidate(0, name, dept);
                    candidateDAO.createCandidate(candidate);
                    JOptionPane.showMessageDialog(dialog,
                        "Candidate added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadCandidates();
                    dialog.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog,
                        "Error adding candidate: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });
            buttonPanel.add(addBtn);

            JButton cancelBtn = new JButton("Cancel");
            cancelBtn.setBackground(new Color(244, 67, 54));
            cancelBtn.setForeground(Color.WHITE);
            cancelBtn.addActionListener(ev -> dialog.dispose());
            buttonPanel.add(cancelBtn);

            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            panel.add(buttonPanel, gbc);

            dialog.add(panel);
            dialog.setVisible(true);
        }
    }

    private class EditCandidateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = candidateTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(AdminDashboard.this,
                    "Please select a candidate to edit.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            int candidateId = (int) tableModel.getValueAt(selectedRow, 0);
            String currentName = (String) tableModel.getValueAt(selectedRow, 1);
            String currentDept = (String) tableModel.getValueAt(selectedRow, 2);

            JDialog dialog = new JDialog(AdminDashboard.this, "Edit Candidate", true);
            dialog.setSize(400, 250);
            dialog.setLocationRelativeTo(AdminDashboard.this);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(8, 8, 8, 8);

            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Candidate Name:"), gbc);
            JTextField nameField = new JTextField(currentName, 20);
            gbc.gridx = 1;
            panel.add(nameField, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(new JLabel("Department:"), gbc);
            JTextField deptField = new JTextField(currentDept, 20);
            gbc.gridx = 1;
            panel.add(deptField, gbc);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            JButton saveBtn = new JButton("Save");
            saveBtn.setBackground(new Color(33, 150, 243));
            saveBtn.setForeground(Color.WHITE);
            saveBtn.addActionListener(ev -> {
                String name = nameField.getText().trim();
                String dept = deptField.getText().trim();

                if (name.isEmpty() || dept.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog,
                        "All fields are required.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    Candidate candidate = new Candidate(candidateId, name, dept);
                    candidateDAO.updateCandidate(candidate);
                    JOptionPane.showMessageDialog(dialog,
                        "Candidate updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadCandidates();
                    dialog.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog,
                        "Error updating candidate: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });
            buttonPanel.add(saveBtn);

            JButton cancelBtn = new JButton("Cancel");
            cancelBtn.setBackground(new Color(244, 67, 54));
            cancelBtn.setForeground(Color.WHITE);
            cancelBtn.addActionListener(ev -> dialog.dispose());
            buttonPanel.add(cancelBtn);

            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            panel.add(buttonPanel, gbc);

            dialog.add(panel);
            dialog.setVisible(true);
        }
    }

    private class DeleteCandidateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = candidateTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(AdminDashboard.this,
                    "Please select a candidate to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            int candidateId = (int) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);

            int confirm = JOptionPane.showConfirmDialog(AdminDashboard.this,
                "Are you sure you want to delete candidate:\n" + name + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    candidateDAO.deleteCandidate(candidateId);
                    JOptionPane.showMessageDialog(AdminDashboard.this,
                        "Candidate deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadCandidates();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(AdminDashboard.this,
                        "Error deleting candidate: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }
}
