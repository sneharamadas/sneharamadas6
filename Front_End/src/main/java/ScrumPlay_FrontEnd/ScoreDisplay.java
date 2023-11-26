package ScrumPlay_FrontEnd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ScoreDisplay extends JFrame {
    private JTable scoreTable;

    public ScoreDisplay() {
        // Set up the JFrame
        setTitle("Leaderboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fetch and update scores from JSON (replace the URL with your actual REST endpoint)
        updateScoresFromRestEndpoint("http://localhost:8080/add-player-score");

        // Create a table model with two columns: Player and Score
        String[] columnNames = {"Player", "Score"};
        String[][] data = {{"Player 1", "0"}, {"Player 2", "0"}};
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        // Create a JTable with the table model
        scoreTable = new JTable(tableModel);

        // Set layout manager
        setLayout(new BorderLayout());

        // Add the table to the frame
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Method to update the scores in the table
    private void updateScores(int scorePlayer1, int scorePlayer2) {
        DefaultTableModel tableModel = (DefaultTableModel) scoreTable.getModel();
        // Clear the existing data
        tableModel.setRowCount(0);
        // Add new data
        tableModel.addRow(new Object[]{"Player 1", scorePlayer1});
        tableModel.addRow(new Object[]{"Player 2", scorePlayer2});
    }

    // Method to fetch scores from a REST endpoint and update the table
    private void updateScoresFromRestEndpoint(String restEndpointUrl) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(restEndpointUrl))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                int scorePlayer1 = jsonNode.get("player1").asInt();
                int scorePlayer2 = jsonNode.get("player2").asInt();

                updateScores(scorePlayer1, scorePlayer2);
            } else {
                System.out.println("Failed to fetch data from the REST endpoint. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create an instance of the ScoreDisplay
        ScoreDisplay scoreDisplay = new ScoreDisplay();

        // Set visible
        scoreDisplay.setVisible(true);
    }
}
