package dao;

import utility.DatabaseConnection;
import java.sql.Connection;

import dto.AdminDTO;
import repository.MySQLRepository;
import repository.StorageRepository;

public class AdminDAOImpl implements AdminDAO {
    private final Connection conn;
    private StorageRepository storage;

    public AdminDAOImpl() {
        this.conn = DatabaseConnection.getInstance().getConnection();
        this.storage=new MySQLRepository(conn);
    }

    @Override
    public String getHashedPasswordByUsername(String username) {
        return storage.get_password_by_username(username);
    }

	@Override
	public boolean validateEmail(String email) {
	    return storage.check_for_email_validation(email);
	}

	@Override
	public boolean updatePassword(String email, String password) {
	    return storage.update_password_to_database(email,password);
	}
	
	@Override
	public boolean saveAdmin(AdminDTO admin) {
		return storage.saveAdmin(admin);
	}
}
