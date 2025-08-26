package view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.Color;

@SuppressWarnings("serial")
public class CreateAccount extends JFrame {
    private JPanel createAccPanel;
    private JTextField nameField, emailIdField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton createAccBtn, backBtn;

    public CreateAccount() {
        setTitle("Create Account");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        createAccPanel = new JPanel();
        createAccPanel.setLayout(null);
        createAccPanel.setBackground(new Color(240, 248, 255));
        setContentPane(createAccPanel);

        JLabel title = new JLabel("Create Admin Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(0, 102, 204));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(100, 30, 300, 40);
        createAccPanel.add(title);

        JLabel email = new JLabel("Email ID:");
        email.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        email.setBounds(70, 100, 120, 25);
        createAccPanel.add(email);

        emailIdField = new JTextField();
        emailIdField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        emailIdField.setBounds(210, 100, 220, 30);
        emailIdField.setBorder(new LineBorder(Color.GRAY, 1, true));
        createAccPanel.add(emailIdField);

        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        password.setBounds(70, 150, 120, 25);
        createAccPanel.add(password);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBounds(210, 150, 220, 30);
        passwordField.setBorder(new LineBorder(Color.GRAY, 1, true));
        createAccPanel.add(passwordField);

        JLabel confirmPassword = new JLabel("Confirm Password:");
        confirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        confirmPassword.setBounds(70, 200, 140, 25);
        createAccPanel.add(confirmPassword);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        confirmPasswordField.setBounds(210, 200, 220, 30);
        confirmPasswordField.setBorder(new LineBorder(Color.GRAY, 1, true));
        createAccPanel.add(confirmPasswordField);

        createAccBtn = new JButton("Create Account");
        createAccBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createAccBtn.setBackground(new Color(0, 123, 255));
        createAccBtn.setForeground(Color.WHITE);
        createAccBtn.setFocusPainted(false);
        createAccBtn.setBounds(100, 250, 140, 35);
        createAccBtn.setBorder(BorderFactory.createEmptyBorder());
        createAccPanel.add(createAccBtn);

        backBtn = new JButton("Back");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backBtn.setBackground(new Color(220, 53, 69));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBounds(260, 250, 120, 35);
        backBtn.setBorder(BorderFactory.createEmptyBorder());
        createAccPanel.add(backBtn);
    }

    public JButton getCreateAccBtn() {
        return createAccBtn;
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public String getEmailId() {
        return emailIdField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }
}