import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
class Bug {
    private int id;
    private String title, description;
    private boolean isOpen; //to check whether the bug is open/not open
//Bug is open means the bug or issue is still active and unresolved
//Bug is closed means the bug has been resolved, fixed, or is no longer an issue

    public Bug(int id, String title, String description) { // constructor
        this.id = id;
        this.title=title;
        this.description=description;
        this.isOpen= true;     //Bugs start as open always.
    }

    // Using getters and setters
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public boolean isOpen() {
        return isOpen;
    }
    public void setOpen(boolean open) {
        isOpen = open;
    }
    @Override
    public String toString() {
        return "Bug ID: " + id + ", Title: " + title + ", Status: " + (isOpen ? "Open" : "Closed");
    }
}



public class BugTrackingSystem {
    private JFrame frame;
    private JTextArea bugListTextArea;
    private ArrayList<Bug> bugList;
    private int nextBugId = 1;

    public BugTrackingSystem() {
        bugList = new ArrayList<>();
        frame = new JFrame("Bug Tracking System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Bug Tracking System");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.RED);
        frame.add(titleLabel, BorderLayout.NORTH);
        bugListTextArea = new JTextArea();
        bugListTextArea.setEditable(false);
        bugListTextArea.setBackground(Color.WHITE);
        bugListTextArea.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(bugListTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 1));
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        JPanel addBugPanel = new JPanel(new FlowLayout());
        addBugPanel.setBackground(Color.LIGHT_GRAY);
        JLabel titleInputLabel = new JLabel("Bug Title: ");
        titleInputLabel.setForeground(Color.BLUE);
        JTextField titleInput = new JTextField(15);
        JLabel descInputLabel = new JLabel("Description: ");
        descInputLabel.setForeground(Color.BLUE);
        JTextField descInput = new JTextField(15);
        JButton addBugButton = new JButton("Add Bug");
        addBugButton.setBackground(Color.GREEN);  // Button background color
        addBugButton.setForeground(Color.BLACK);
        addBugPanel.add(titleInputLabel);
        addBugPanel.add(titleInput);
        addBugPanel.add(descInputLabel);
        addBugPanel.add(descInput);
        addBugPanel.add(addBugButton);
        bottomPanel.add(addBugPanel);

        JPanel updateBugPanel = new JPanel(new FlowLayout());
        JLabel bugIdLabel = new JLabel("Bug ID: ");
        JTextField bugIdInput = new JTextField(5);
        JButton closeBugButton = new JButton("Close Bug");
        JButton openBugButton = new JButton("Open Bug");
        updateBugPanel.add(bugIdLabel);
        updateBugPanel.add(bugIdInput);
        updateBugPanel.add(closeBugButton);
        updateBugPanel.add(openBugButton);
        bottomPanel.add(updateBugPanel);

        JPanel deleteBugPanel = new JPanel(new FlowLayout());
        deleteBugPanel.setBackground(Color.LIGHT_GRAY);
        JLabel deleteBugIdLabel = new JLabel("Bug ID: ");
        deleteBugIdLabel.setForeground(Color.BLUE);
        JTextField deleteBugIdInput = new JTextField(5);
        JButton deleteBugButton = new JButton("Delete Bug");
        deleteBugButton.setBackground(Color.RED);
        deleteBugButton.setForeground(Color.WHITE);
        deleteBugPanel.add(deleteBugIdLabel);
        deleteBugPanel.add(deleteBugIdInput);
        deleteBugPanel.add(deleteBugButton);
        bottomPanel.add(deleteBugPanel);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        addBugButton.addActionListener(e -> {
            String title = titleInput.getText();
            String description = descInput.getText();
            addBug(title, description);
            titleInput.setText("");
            descInput.setText("");
        });

        closeBugButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(bugIdInput.getText());
                updateBugStatus(id, false); // Close Bug
            } catch (NumberFormatException ex) {
                showMessage("Invalid Bug ID");
            }
        });

        openBugButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(bugIdInput.getText());
                updateBugStatus(id, true); // Open Bug
            } catch (NumberFormatException ex) {
                showMessage("Invalid Bug ID");
            }
        });

        deleteBugButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(deleteBugIdInput.getText());
                deleteBug(id);
            } catch (NumberFormatException ex) {
                showMessage("Invalid Bug ID");
            }
        });

        frame.setVisible(true);
    }
    private void addBug(String title, String description) {
        Bug newBug = new Bug(nextBugId++, title, description);
        bugList.add(newBug);
        updateBugListDisplay();
    }
    private void updateBugStatus(int id, boolean open) {
        for (Bug bug : bugList) {
            if (bug.getId() == id) {
                bug.setOpen(open);
                updateBugListDisplay();
                return;
            }
        }
        showMessage("Bug not found");
    }

    private void deleteBug(int id) {
        bugList.removeIf(bug -> bug.getId() == id);
        updateBugListDisplay();
    }
    private void updateBugListDisplay() {
        StringBuilder sb = new StringBuilder();
        for (Bug bug : bugList) {
            sb.append(bug.toString()).append("\n");
        }
        bugListTextArea.setText(sb.toString());
    }
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BugTrackingSystem::new);
    }
}
