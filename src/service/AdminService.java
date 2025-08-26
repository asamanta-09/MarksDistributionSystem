package service;

import dao.AdminDAO;
import dao.AdminDAOImpl;
import dto.AdminDTO;
import utility.Encryptor;

public class AdminService {
    private final AdminDAO adminDAO;

    public AdminService() {
        this.adminDAO = new AdminDAOImpl();
    }

    public boolean validateAdmin(AdminDTO adminDTO) {
        String storedHash = adminDAO.getHashedPasswordByUsername(adminDTO.getUsername());
        if (storedHash == null) return false;

        return Encryptor.checkPassword(adminDTO.getPassword(), storedHash);
    }
    
    public boolean registerAdmin(AdminDTO adminDTO) {
    	// Check for Duplicate email
    	if (adminDAO.validateEmail(adminDTO.getUsername())) {
            return false;
        }

        return adminDAO.saveAdmin(adminDTO);
    }
}


//flow: main -> view -> controller ->model -> dto -> service -> dao -> utility