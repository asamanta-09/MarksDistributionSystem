package model;

import dto.AdminDTO;
import service.AdminService;

public class AdminModel {
    private final AdminService adminService;

    public AdminModel() {
        this.adminService = new AdminService();
    }

    public boolean validateAdmin(String username, String password) {
        AdminDTO adminDTO = new AdminDTO(username, password);
        return adminService.validateAdmin(adminDTO);
    }
}
