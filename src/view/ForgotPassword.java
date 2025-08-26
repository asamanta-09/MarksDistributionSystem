package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class ForgotPassword extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField emailTextField;
    private JButton submitButton, backButton;

    public ForgotPassword() {
        setTitle("Forgot Password");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 248, 255));
        setContentPane(panel);

        JLabel title = new JLabel("Forgot Password");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(0, 102, 204));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(140, 40, 250, 40);
        panel.add(title);

        JLabel emailLabel = new JLabel("Enter Email ID:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        emailLabel.setBounds(70, 140, 140, 25);
        panel.add(emailLabel);

        emailTextField = new JTextField();
        emailTextField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailTextField.setBounds(200, 140, 220, 30);
        emailTextField.setBorder(new LineBorder(Color.GRAY, 1, true));
        panel.add(emailTextField);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(0, 123, 255));
        submitButton.setFocusPainted(false);
        submitButton.setBounds(120, 220, 110, 40);
        submitButton.setBorder(BorderFactory.createEmptyBorder());
        panel.add(submitButton);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(220, 53, 69)); // Bootstrap Red
        backButton.setFocusPainted(false);
        backButton.setBounds(260, 220, 110, 40);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        panel.add(backButton);
    }

    // Getters
    public JTextField getEmailField() {
        return emailTextField;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public String getEmail() {
        return emailTextField.getText().trim();
    }

    public void clearFields() {
        emailTextField.setText("");
    }
}
