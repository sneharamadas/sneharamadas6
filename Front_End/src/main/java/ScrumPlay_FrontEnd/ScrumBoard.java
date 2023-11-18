package ScrumPlay_FrontEnd;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;

public class ScrumBoard extends JFrame {
    public Map<String, Map<String, String>> userData;
    public JComboBox<String> userDropdown;
    public JPanel boardPanel;
    public JTextArea updateArea;

    public ScrumBoard() {
        // Initialize user data
        userData = new HashMap<>();

        Map<String, String> user1Data = new HashMap<>();
        user1Data.put("Implement User Authentication", "To Do");
        user1Data.put("Design Scrum Board UI", "In Progress");
        user1Data.put("Implement Task Management", "Done");

        Map<String, String> user2Data = new HashMap<>();
        user2Data.put("Create Sprint Planning Module", "To Do");
        user2Data.put("Test Scrum Board Functionality", "Done");

        Map<String, String> user3Data = new HashMap<>();
        user3Data.put("Refactor Codebase", "In Progress");
        user3Data.put("Optimize Database Queries", "Done");

        Map<String, String> user4Data = new HashMap<>();
        user4Data.put("Implement Notifications", "To Do");
        user4Data.put("Conduct Usability Testing", "In Progress");

        Map<String, String> user5Data = new HashMap<>();
        user5Data.put("Write User Documentation", "To Do");
        user5Data.put("Prepare Release Notes", "Done");

        userData.put("JohnDoe", user1Data);
        userData.put("JaneSmith", user2Data);
        userData.put("RobertJohnson", user3Data);
        userData.put("EmilyDavis", user4Data);
        userData.put("MichaelBrown", user5Data);

        // Set up the main frame
        setTitle("Scrum Board");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0,255,255));// Set background color

        userDropdown = new JComboBox<>(userData.keySet().toArray(new String[0]));
        userDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBoard();
            }
        });

        boardPanel = createBoardPanel();
        add(userDropdown, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        JPanel updatePanel = createUpdatePanel();
        add(updatePanel, BorderLayout.SOUTH);

        final int[] countdown = {10 * 60};

        JLabel countdownLabel = new JLabel();
        countdownLabel.setHorizontalAlignment(JLabel.CENTER);
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                countdown[0]--; // Decrease the countdown value

                int minutes = countdown[0] / 60;
                int seconds = countdown[0] % 60;

                countdownLabel.setText(String.format("Countdown: %02d:%02d", minutes, seconds));

                // If countdown reaches 0, stop the timer and show a message
                if (countdown[0] <= 0) {
                    ((Timer)e.getSource()).stop();
                    endScrumCall();
                }
            }
        });

        JButton startScrumCallButton = new JButton("Start Scrum Call");
        startScrumCallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Start the timer when the button is clicked
                timer.start();
            }
        });

        JButton endScrumButton = new JButton("End Scrum Call");
        endScrumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                endScrumCall();
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        bottomPanel.add(countdownLabel);
        bottomPanel.add(startScrumCallButton);
        bottomPanel.add(endScrumButton);

        add(bottomPanel, BorderLayout.PAGE_END);

        // Initial board update
        updateBoard();
    }

    public JPanel createBoardPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(1, 3));

        for (String lane : new String[]{"To Do", "In Progress", "Done"}) {
            JPanel lanePanel = new JPanel();
            lanePanel.setLayout(new BoxLayout(lanePanel, BoxLayout.Y_AXIS));
            lanePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), lane));
            lanePanel.setBackground(new Color(255, 255, 224)); // Set lane background color

            boardPanel.add(lanePanel);
        }

        return boardPanel;
    }

    public void updateBoard() {
        String selectedUser = (String) userDropdown.getSelectedItem();
        if (selectedUser != null) {
            Map<String, String> userStories = userData.get(selectedUser);
            clearBoard();

            for (Map.Entry<String, String> entry : userStories.entrySet()) {
                String userStory = entry.getKey();
                String status = entry.getValue();

                JPanel cardPanel = createCardPanel(userStory, status);
                addToLanePanel(status, cardPanel);
            }
        }
    }

    public JPanel createCardPanel(String userStory, String status) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEtchedBorder());
        cardPanel.setBackground(Color.RED); // Set card background color
        cardPanel.setPreferredSize(new Dimension(800, 200)); // Set fixed size

        JLabel userStoryLabel = new JLabel(userStory);
        userStoryLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Reduce font size
        userStoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(userStoryLabel);

        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton updateStatus = new JButton("Update Status");
        updateStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show daily status input dialog
                showStatusInputDialog(userStory);
            }
        });

        JPanel flipPanel = new JPanel();
        flipPanel.setLayout(new BoxLayout(flipPanel, BoxLayout.Y_AXIS));
        flipPanel.setBorder(BorderFactory.createEtchedBorder());
        flipPanel.setBackground(Color.GREEN); // Set card background color
        flipPanel.setPreferredSize(new Dimension(800, 200));
        flipPanel.setVisible(false);

        JLabel flippedUserStoryLabel = new JLabel(userStory);
        flippedUserStoryLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Reduce font size
        flippedUserStoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        flipPanel.add(flippedUserStoryLabel);

        JLabel storyPoints = new JLabel("Story Points");
        storyPoints.setFont(new Font("Arial", Font.BOLD, 12)); // Reduce font size
        storyPoints.setAlignmentX(Component.CENTER_ALIGNMENT);
        flipPanel.add(storyPoints);

        JLabel description = new JLabel("Description");
        description.setFont(new Font("Arial", Font.BOLD, 12)); // Reduce font size
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        flipPanel.add(description);

        JLabel acceptanceCriteria = new JLabel("Acceptance Criteria");
        acceptanceCriteria.setFont(new Font("Arial", Font.BOLD, 12)); // Reduce font size
        acceptanceCriteria.setAlignmentX(Component.CENTER_ALIGNMENT);
        flipPanel.add(acceptanceCriteria);

        JLabel todayUpdate = new JLabel("Today's Update");
        todayUpdate.setFont(new Font("Arial", Font.BOLD, 12)); // Reduce font size
        todayUpdate.setAlignmentX(Component.CENTER_ALIGNMENT);
        flipPanel.add(todayUpdate);

        JButton provideUpdate = new JButton("Provide Update");
        provideUpdate.setAlignmentX(Component.CENTER_ALIGNMENT);
        provideUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show daily status input dialog
                cardPanel.setVisible(!cardPanel.isVisible());
                flipPanel.setVisible(!flipPanel.isVisible());
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show daily status input dialog
                cardPanel.setVisible(!cardPanel.isVisible());
                flipPanel.setVisible(!flipPanel.isVisible());
            }
        });
        flipPanel.add(backButton);
        cardPanel.add(updateStatus);
        cardPanel.add(provideUpdate);

        JPanel container = new JPanel();
        container.add(cardPanel);
        container.add(flipPanel);

        return container;
    }

    public void addToLanePanel(String status, JPanel cardPanel) {
        Component[] lanePanels = boardPanel.getComponents();
        switch (status) {
            case "To Do":
                ((JPanel) lanePanels[0]).add(cardPanel);
                break;
            case "In Progress":
                ((JPanel) lanePanels[1]).add(cardPanel);
                break;
            case "Done":
                ((JPanel) lanePanels[2]).add(cardPanel);
                break;
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public void clearBoard() {
        Component[] lanePanels = boardPanel.getComponents();
        for (Component lanePanel : lanePanels) {
            ((JPanel) lanePanel).removeAll();
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public JPanel createUpdatePanel() {
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new BorderLayout());
        updatePanel.setBackground(new Color(240, 240, 240)); // Set background color

        // Update area
        updateArea = new JTextArea();
        updateArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(updateArea);
        updatePanel.add(scrollPane, BorderLayout.CENTER);

        return updatePanel;
    }

    public String getUpdateForUserStory(String userStory) {

        return "Update for " + userStory + ": Today's progress...";
    }

    public void showStatusInputDialog(String userStory) {
        JTextArea statusInput = new JTextArea();
        statusInput.setLineWrap(true);
        statusInput.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(statusInput);

        int result = JOptionPane.showConfirmDialog(this, scrollPane, "Enter Daily Status for " + userStory,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String dailyStatus = statusInput.getText();
            updateArea.setText("Daily Status for " + userStory + ":\n" + dailyStatus);
        }
    }

    public void endScrumCall() {
        JOptionPane.showMessageDialog(this, "Scrum Call Ended!", "Scrum Ended", JOptionPane.INFORMATION_MESSAGE);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new ScrumBoard().setVisible(true);
//            }
//        });
//    }
}
