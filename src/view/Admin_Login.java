package view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Admin_Login extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton login;
    private JButton forgotPass;

    public Admin_Login() {
        setTitle("Admin Login");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(new Color(240, 248, 255));
        setContentPane(loginPanel);

        JLabel title = new JLabel("Admin Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(0, 102, 204));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(150, 40, 200, 40);
        loginPanel.add(title);

        JLabel username = new JLabel("Username:");
        username.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        username.setBounds(70, 140, 120, 25);
        loginPanel.add(username);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameField.setBounds(200, 140, 220, 30);
        usernameField.setBorder(new LineBorder(Color.GRAY, 1, true));
        loginPanel.add(usernameField);

        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        password.setBounds(70, 190, 120, 25);
        loginPanel.add(password);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBounds(200, 190, 220, 30);
        passwordField.setBorder(new LineBorder(Color.GRAY, 1, true));
        loginPanel.add(passwordField);

        login = new JButton("Login");
        login.setFont(new Font("Segoe UI", Font.BOLD, 16));
        login.setForeground(Color.WHITE);
        login.setBackground(new Color(0, 123, 255));
        login.setFocusPainted(false);
        login.setBounds(100, 260, 120, 40);
        login.setBorder(BorderFactory.createEmptyBorder());
        loginPanel.add(login);

        forgotPass = new JButton("Forgot Password");
        forgotPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        forgotPass.setBackground(new Color(240, 248, 255));
        forgotPass.setForeground(new Color(0, 102, 204));
        forgotPass.setFocusPainted(false);
        forgotPass.setBorder(BorderFactory.createEmptyBorder());
        forgotPass.setBounds(240, 265, 160, 30);
        loginPanel.add(forgotPass);

    }
    
    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return login;
    }

    public JButton getForgotPassButton() {
        return forgotPass;
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
