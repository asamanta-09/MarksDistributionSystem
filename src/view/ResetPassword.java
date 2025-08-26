package view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ResetPassword extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel resetPanel;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton updateButton;
    private JButton skipButton;

    public ResetPassword() {
        setTitle("Reset Password");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        resetPanel = new JPanel();
        resetPanel.setLayout(null);
        resetPanel.setBackground(new Color(240, 248, 255));
        setContentPane(resetPanel);

        JLabel title = new JLabel("Reset Password");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(0, 102, 204));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(140, 40, 220, 40);
        resetPanel.add(title);

        JLabel newPassLabel = new JLabel("New Password:");
        newPassLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        newPassLabel.setBounds(70, 120, 150, 25);
        resetPanel.add(newPassLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        newPasswordField.setBounds(220, 120, 200, 30);
        newPasswordField.setBorder(new LineBorder(Color.GRAY, 1, true));
        resetPanel.add(newPasswordField);

        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        confirmPassLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        confirmPassLabel.setBounds(70, 170, 160, 25);
        resetPanel.add(confirmPassLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        confirmPasswordField.setBounds(220, 170, 200, 30);
        confirmPasswordField.setBorder(new LineBorder(Color.GRAY, 1, true));
        resetPanel.add(confirmPasswordField);

        updateButton = new JButton("Update Password");
        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        updateButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(0, 123, 255));
        updateButton.setFocusPainted(false);
        updateButton.setBounds(90, 250, 160, 40);
        updateButton.setBorder(BorderFactory.createEmptyBorder());
        resetPanel.add(updateButton);

        skipButton = new JButton("Skip");
        skipButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        skipButton.setForeground(Color.WHITE);
        skipButton.setBackground(new Color(108, 117, 125)); // Gray
        skipButton.setFocusPainted(false);
        skipButton.setBounds(260, 250, 100, 40);
        skipButton.setBorder(BorderFactory.createEmptyBorder());
        resetPanel.add(skipButton);
    }

    public String getNewPassword() {
        return new String(newPasswordField.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getSkipButton() {
        return skipButton;
    }

    public void clearFields() {
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }
}
