package dao;

import dto.AdminDTO;

public interface AdminDAO {
    String getHashedPasswordByUsername(String username);
    boolean validateEmail(String email);
    boolean updatePassword(String email,String password);
	boolean saveAdmin(AdminDTO admin);
}
