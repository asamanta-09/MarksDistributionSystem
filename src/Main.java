import controller.AdminLoginController;
import controller.DashboardController;
import model.AdminModel;
import model.DashboardModel;
import setup.InitialSetUp;
import view.Admin_Login;
import view.Dashboard;

public class Main {

	public static void main(String[] args) throws Exception {
		InitialSetUp.init();
		Admin_Login view=new Admin_Login();
		AdminModel model = new AdminModel();
		new AdminLoginController(view,model);
		view.setVisible(true);
	}
}
