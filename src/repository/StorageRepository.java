package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dto.AdminDTO;
import dto.StudentDetails;
import dto.StudentResult;

public interface StorageRepository {
    //for Admin data handling
    String get_password_by_username(String username);
    boolean check_for_email_validation(String email);
    boolean update_password_to_database(String email, String password);
    String get_user_details();
    
    //dashboard activity
    void store_marks_details(List<StudentDetails> data, String department, String course, String semester, String year);
    String runQuery(String query);
    Map<Integer, String> fetchAllPapers() throws SQLException;
	ResultSet fetchStudentMarksDetails(String deptName, String courseName, int semester, int year) throws SQLException; 
	Map<String, StudentResult> fetchResult(String deptName, String courseName, int semester, int year) throws SQLException;
	boolean saveAdmin(AdminDTO admin);
}
