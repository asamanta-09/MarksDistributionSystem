package controller;

import view.Admin_Login;
import view.ForgotPassword;
import view.OTPVerification;

import java.util.Random;

import javax.swing.*;

import dao.AdminDAO;
import dao.AdminDAOImpl;
import model.AdminModel;

public class ForgotPasswordController {
    private final ForgotPassword view;
    private final AdminDAO dao;

    public ForgotPasswordController(ForgotPassword view) {
        this.view = view;
        this.dao = new AdminDAOImpl();
        initController();
    }

    private void initController() {
        view.getSubmitButton().addActionListener(e -> handleSubmit());
        view.getBackButton().addActionListener(e -> handleBack());
    }

    private void handleSubmit() {
        String email = view.getEmail();

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter your email.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!dao.validateEmail(email)) {
        	JOptionPane.showMessageDialog(view, "Email is not registered", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        }

        // Generate secure 6-digit OTP
        Random rnd = new Random();
        int otp = 100000 + rnd.nextInt(900000);
        String subject = "OTP Verification";
        String msg = "Hello\nYour OTP is: " + otp + "\nThank You";

        System.out.println("Please wait OTP is sending to your registered mail id...");
        boolean isSent = utility.Mail_Service.send(email, msg, subject);
        if (isSent) {
        	view.clearFields();
            JOptionPane.showMessageDialog(view, "OTP sent successfully to: " + email, "Info", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
            OTPVerification view=new OTPVerification();
            new OTPVerificationController(view,otp,email);
            view.setVisible(true);
            
        } else {
            JOptionPane.showMessageDialog(view, "Failed to send OTP. Please check your connection or try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void handleBack() {
        view.dispose(); // Close forgot password view
        Admin_Login view=new Admin_Login();
        new AdminLoginController(view, new AdminModel()); // Restart login
        view.setVisible(true);
    }
}
