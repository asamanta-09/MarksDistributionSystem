package controller;

import view.Admin_Login;
import view.Dashboard;
import view.ForgotPassword;

import javax.swing.*;
import model.AdminModel;
import model.DashboardModel;

public class AdminLoginController {
    private final Admin_Login view;
    private final AdminModel model;

    public AdminLoginController(Admin_Login view,AdminModel model) {
        this.view = view;
        this.model = model;
        initController();
    }

    private void initController() {
        view.getLoginButton().addActionListener(e -> {
			try {
				handleLogin();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
        view.getForgotPassButton().addActionListener(e -> handleForgotPassword());
    }

    private void handleLogin() throws Exception {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (model.validateAdmin(username, password)) {
            JOptionPane.showMessageDialog(view, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
	        view.setVisible(false);
			Dashboard dash=new Dashboard();
			new DashboardController(dash,new DashboardModel());
			dash.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleForgotPassword() {
        view.dispose();
        view.setVisible(false);
        ForgotPassword forgotView = new ForgotPassword();
        new ForgotPasswordController(forgotView);
        forgotView.setVisible(true);
    }
}
