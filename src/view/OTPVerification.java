package view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class OTPVerification extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField otpField;
    private JButton verifyButton;
    private JButton cancelButton;
    private JPanel otpPanel;

    public OTPVerification() {
        setTitle("OTP Verification");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        otpPanel = new JPanel();
        otpPanel.setLayout(null);
        otpPanel.setBackground(new Color(240, 248, 255));
        setContentPane(otpPanel);

        JLabel title = new JLabel("Verify OTP");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 102, 204));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(120, 30, 160, 30);
        otpPanel.add(title);

        JLabel otpLabel = new JLabel("Enter OTP:");
        otpLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        otpLabel.setBounds(60, 90, 100, 25);
        otpPanel.add(otpLabel);

        otpField = new JTextField();
        otpField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        otpField.setBounds(160, 90, 160, 30);
        otpField.setBorder(new LineBorder(Color.GRAY, 1, true));
        otpPanel.add(otpField);

        verifyButton = new JButton("Verify");
        verifyButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        verifyButton.setForeground(Color.WHITE);
        verifyButton.setBackground(new Color(0, 123, 255));
        verifyButton.setFocusPainted(false);
        verifyButton.setBounds(80, 150, 100, 40);
        verifyButton.setBorder(BorderFactory.createEmptyBorder());
        otpPanel.add(verifyButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(220, 53, 69)); // Bootstrap red
        cancelButton.setFocusPainted(false);
        cancelButton.setBounds(200, 150, 100, 40);
        cancelButton.setBorder(BorderFactory.createEmptyBorder());
        otpPanel.add(cancelButton);
    }

    public String getOTP() {
        return otpField.getText().trim();
    }

    public JButton getVerifyButton() {
        return verifyButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void clearFields() {
        otpField.setText("");
    }
}
