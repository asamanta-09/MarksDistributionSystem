package controller;

import javax.swing.JOptionPane;

import dao.AdminDAO;
import dao.AdminDAOImpl;
import model.DashboardModel;
import utility.Encryptor;
import view.Dashboard;
import view.ForgotPassword;
import view.ResetPassword;

public class ResetPasswordController {
	private final ResetPassword view;
	private final AdminDAO dao;
	private String email;
	
	ResetPasswordController(ResetPassword view,String email){
		this.email=email;
		this.view=view;
		dao=new AdminDAOImpl();
		initController();
	}
	
	private void initController() {
        view.getUpdateButton().addActionListener(e -> {
			try {
				handleUpdate();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
        view.getSkipButton().addActionListener(e -> {
			try {
				handleSkip();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}

	private void handleSkip() throws Exception {
		view.dispose();
        view.setVisible(false);
		Dashboard dash=new Dashboard();
		new DashboardController(dash,new DashboardModel());
		dash.setVisible(true);
	}

	private void handleUpdate() throws Exception {
		String plain_pass = view.getNewPassword();
		if(!plain_pass.equals(view.getConfirmPassword())) {
			JOptionPane.showMessageDialog(view, "Password and Confirm password doesnot match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
		}
		String hash_pass=Encryptor.hashPassword(plain_pass);
		if(dao.updatePassword(email,hash_pass)) {
			JOptionPane.showMessageDialog(view, "Password updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
			view.dispose();
	        view.setVisible(false);
			Dashboard dash=new Dashboard();
			new DashboardController(dash,new DashboardModel());
			dash.setVisible(true);
		}
		
	}
}
