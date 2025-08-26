package model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.DashboardDAO;
import dto.StudentDetails;
import dto.StudentResult;
import utility.ExcelRead;

public class DashboardModel {
	private DashboardDAO dao;
	public DashboardModel(){
		this.dao=new DashboardDAO();
	}

	public void uploadData(String department, String course, String semester, String year, String filePath) throws IOException {
	    List<StudentDetails> marks_info = ExcelRead.readExcelData(filePath);
	    dao.insertStudentMarks(marks_info, department, course, semester, year);
	}
	
	public String getQueryResultForView(String query) {
		return dao.getRunQueryResult(query);
	}
	
	
	public String getFormattedMarksDetails(String d,String c,String s,String y) throws Exception {
        StringBuilder result = new StringBuilder();

        try {
            Map<Integer, String> paperTitles = dao.getAllPaperTitles();
            List<Integer> paperIds = new ArrayList<>(paperTitles.keySet());
            result.append("    "+String.format("%-14s%-12s%-12s%-10s", "Roll", "Dept", "Course", "Semester"));
            for (String title : paperTitles.values()) {
                result.append(String.format("%-10s%-10s%-10s%-12s", title + "_IA   ", title + "_TH   ", title + "_PRC  ", title + "_Total  "));
            }
            result.append(String.format("%-12s", "GrandTotal")).append("\n");

            ResultSet rs = dao.getStudentMarkDetails(d,c,Integer.parseInt(s),Integer.parseInt(y));

            String currentRoll = null, dept = "", course = "";
            int semester = 0;
            Map<Integer, Map<String, Integer>> studentMarks = new LinkedHashMap<>();

            while (rs.next()) {
                String roll = rs.getString("roll_number");

                if (currentRoll != null && !roll.equals(currentRoll)) {
                    appendStudentRow(result, currentRoll, dept, course, semester, studentMarks, paperIds);
                    studentMarks.clear();
                }

                if (currentRoll == null || !roll.equals(currentRoll)) {
                    currentRoll = roll;
                    dept = rs.getString("dept");
                    course = rs.getString("course");
                    semester = rs.getInt("semester_number");
                }

                int paperId = rs.getInt("paper_id");
                String type = rs.getString("type");
                int marks = rs.getInt("marks_obtained");

                studentMarks.putIfAbsent(paperId, new HashMap<>());
                if (type != null) {
                    studentMarks.get(paperId).put(type, marks);
                }
            }

            if (currentRoll != null) {
            	System.out.print("  ");
                appendStudentRow(result, currentRoll, dept, course, semester, studentMarks, paperIds);
            }

            rs.close();
        } catch (SQLException e) {
            result.append("Error: ").append(e.getMessage());
        }

        return result.toString();
    }

    private void appendStudentRow(StringBuilder result, String roll, String dept, String course, int sem,Map<Integer, Map<String, Integer>> studentMarks, List<Integer> paperIds) {
        result.append("    "+String.format("%-14s%-12s%-14s%-10d", roll, dept, course, sem));
        int grandTotal = 0;

        for (int paperId : paperIds) {
            Map<String, Integer> marks = studentMarks.getOrDefault(paperId, new HashMap<>());
            int ia = marks.getOrDefault("IA", 0);
            int th = marks.getOrDefault("TH", 0);
            int prc = marks.getOrDefault("PRC", 0);
            int total = ia + th + prc;

            result.append(String.format("%-10d%-10d%-10d%-12d", ia, th, prc, total));
            grandTotal += total;
        }

        result.append(String.format("%-12d", grandTotal)).append("\n");
    }
    
    
    public String viewUserDetails() {
    	return dao.getUserDetails();
    }

	public Map<String, StudentResult> generateResult(String department, String course, String semester, String year) throws Exception{
		return dao.getStudentResult(department, course, Integer.parseInt(semester), Integer.parseInt(year));
	}
}















