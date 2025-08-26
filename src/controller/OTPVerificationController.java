package controller;

import javax.swing.JOptionPane;

import model.AdminModel;
import view.Admin_Login;
import view.OTPVerification;
import view.ResetPassword;

public class OTPVerificationController {
	private final int otp;
	private final OTPVerification view;
	private String email;
	
	OTPVerificationController(OTPVerification view, int otp,String email){
		this.email=email;
		this.view=view;
		this.otp=otp;
		initController();
	}
	
	 private void initController() {
	        view.getVerifyButton().addActionListener(e -> handleVerify());
	        view.getCancelButton().addActionListener(e -> handleCancel());
	 }

	private void handleCancel() {
		view.dispose();
        Admin_Login view=new Admin_Login();
        new AdminLoginController(view, new AdminModel());
        view.setVisible(true);
	}

	private void handleVerify() {
		int user_otp =Integer.parseInt(view.getOTP());
		view.clearFields();
		if(user_otp != otp) {
			JOptionPane.showMessageDialog(view, "OTP does not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
		}
		view.dispose();
		ResetPassword reset_view=new ResetPassword();
        new ResetPasswordController(reset_view,email);
        reset_view.setVisible(true);
	}
}
