package ui;

import service.ResultService;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Result UI to display election results.
 */
public class ResultUI extends JFrame {
    private JTextArea resultArea;
    private ResultService resultService;

    public ResultUI() {
        resultService = new ResultService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Election Results");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        resultArea = new JTextArea();
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        displayResults();
        setVisible(true);
    }

    private void displayResults() {
        Map<Integer, Integer> tally = resultService.getVoteTally();
        StringBuilder sb = new StringBuilder("Election Results:\n");
        for (Map.Entry<Integer, Integer> entry : tally.entrySet()) {
            sb.append("Candidate ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" votes\n");
        }
        resultArea.setText(sb.toString());
    }
}
