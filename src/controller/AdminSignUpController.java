package controller;

import view.CreateAccount;
import view.Dashboard;
import dto.AdminDTO;
import service.AdminService;
import utility.Encryptor;

import javax.swing.*;

public class AdminSignUpController {
    private final CreateAccount view;
    private final AdminService service;
    private final Dashboard dashboard; // existing dashboard reference

    public AdminSignUpController(CreateAccount view, Dashboard dashboard) {
        this.view = view;
        this.service = new AdminService();
        this.dashboard = dashboard;
        initController();
    }

    private void initController() {
        view.getCreateAccBtn().addActionListener(e -> handleSignUp());
        view.getBackBtn().addActionListener(e -> handleBack());
    }

    private void handleSignUp() {
        String email = view.getEmailId();
        String password = view.getPassword();
        String confirmPass = view.getConfirmPassword();

        if (email.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(view, "All Fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            if (!password.equals(confirmPass)) {
                JOptionPane.showMessageDialog(view, "Password and Confirm password Mismatch.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        AdminDTO dto = new AdminDTO();
        dto.setUsername(email);
        dto.setPassword(Encryptor.hashPassword(password));

        if (service.registerAdmin(dto)) {
            JOptionPane.showMessageDialog(view, "Admin registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
            dashboard.setVisible(true); // show dashboard again
        } else {
            JOptionPane.showMessageDialog(view, "Registration failed. Try another Email.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBack() {
        view.dispose(); // close CreateAccount
        dashboard.setVisible(true); // return to existing dashboard session
    }
}