package dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import dto.StudentDetails;
import dto.StudentResult;
import repository.*;
import utility.DatabaseConnection;

public class DashboardDAO {
	
	private final Connection conn;
	private final StorageRepository repo;

    public DashboardDAO() {
        this.conn = DatabaseConnection.getInstance().getConnection();
        this.repo=new MySQLRepository(conn);
    }
	
    public void insertStudentMarks(List<StudentDetails> student, String dept, String course, String sem, String year) {
    	repo.store_marks_details(student, dept, course, sem, year);
    }
    
    public String getRunQueryResult(String query) {
    	return repo.runQuery(query);
    }
    
    
    public Map<Integer, String> getAllPaperTitles() throws Exception {
        return repo.fetchAllPapers();
    }
    
    public ResultSet getStudentMarkDetails(String dept, String course, int semester, int year) throws Exception {
        return repo.fetchStudentMarksDetails(dept, course, semester, year);
    }

    public Map<String, StudentResult> getStudentResult(String dept, String course, int semester, int year) throws Exception {
        return repo.fetchResult(dept, course, semester, year);
    }
    
    public String getUserDetails() {
    	return repo.get_user_details();
    }

}
