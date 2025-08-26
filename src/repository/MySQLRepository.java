package repository;

import dto.StudentDetails;
import dto.StudentResult;
import dto.AdminDTO;
import dto.PaperDetails;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLRepository implements StorageRepository {

    private final Connection conn;

    public MySQLRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void store_marks_details(List<StudentDetails> data, String department, String course, String semesterStr, String yearStr) {
        try {
            conn.setAutoCommit(false);

            int departmentId = insertOrGetDepartmentId(department);
            int courseId = insertOrGetCourseId(course, departmentId);
            int semesterNumber = Integer.parseInt(semesterStr);
            int year = Integer.parseInt(yearStr);
            int semesterId = insertOrGetSemesterId(courseId, semesterNumber, year);

            for (StudentDetails sd : data) {
                int paperId = insertOrGetPaperId(sd.getPaper_code(), sd.getPaper_title(), semesterId);
                int paperExamId = insertOrGetPaperExamId(paperId, sd.getType(), sd.getFull_marks());
                int studentId = insertOrGetStudentId(String.valueOf(sd.getRoll()), courseId);
                insertOrUpdateMarks(studentId, paperExamId, sd.getObtained_marks());
            }

            conn.commit();
            System.out.println("Data uploaded successfully with transaction.");

        } catch (Exception e) {
            try {
                conn.rollback();
                System.out.println("Transaction failed. Rolled back.");
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    private int insertOrGetDepartmentId(String name) throws SQLException {
        String query = "SELECT id FROM department WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }

        String insert = "INSERT INTO department (name) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }

    private int insertOrGetCourseId(String name, int departmentId) throws SQLException {
        String query = "SELECT id FROM course WHERE name = ? AND department_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, departmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }

        String insert = "INSERT INTO course (name, department_id, duration_semesters) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setInt(2, departmentId);
            ps.setInt(3, 8); // Assuming default 8 semesters
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }

    private int insertOrGetSemesterId(int courseId, int semester, int year) throws SQLException {
        String query = "SELECT id FROM semester WHERE course_id = ? AND semester_number = ? AND year = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, courseId);
            ps.setInt(2, semester);
            ps.setInt(3, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }

        String insert = "INSERT INTO semester (course_id, semester_number, year) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, courseId);
            ps.setInt(2, semester);
            ps.setInt(3, year);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }

    private int insertOrGetStudentId(String rollNumber, int courseId) throws SQLException {
        String query = "SELECT id FROM student WHERE roll_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, rollNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }

        String insert = "INSERT INTO student (roll_number, course_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rollNumber);
            ps.setInt(2, courseId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }

    private int insertOrGetPaperId(String code, String title, int semesterId) throws SQLException {
        String query = "SELECT id FROM paper WHERE paper_code = ? AND semester_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, code);
            ps.setInt(2, semesterId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }

        String insert = "INSERT INTO paper (paper_code, title, semester_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, code);
            ps.setString(2, title);
            ps.setInt(3, semesterId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }

    private int insertOrGetPaperExamId(int paperId, String examType, int fullMarks) throws SQLException {
        String query = "SELECT id, full_marks FROM paper_exam WHERE paper_id = ? AND exam_type = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, paperId);
            ps.setString(2, examType);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int existingFullMarks = rs.getInt("full_marks");
                if (existingFullMarks != fullMarks) {
                    String update = "UPDATE paper_exam SET full_marks = ? WHERE id = ?";
                    try (PreparedStatement ups = conn.prepareStatement(update)) {
                        ups.setInt(1, fullMarks);
                        ups.setInt(2, id);
                        ups.executeUpdate();
                    }
                }
                return id;
            }
        }
        String insert = "INSERT INTO paper_exam (paper_id, exam_type, full_marks) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, paperId);
            ps.setString(2, examType);
            ps.setInt(3, fullMarks);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        }
    }

    private void insertOrUpdateMarks(int studentId, int paperExamId, int obtainedMarks) throws SQLException {
        String check = "SELECT id FROM marks WHERE student_id = ? AND paper_exam_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(check)) {
            ps.setInt(1, studentId);
            ps.setInt(2, paperExamId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String update = "UPDATE marks SET marks_obtained = ? WHERE student_id = ? AND paper_exam_id = ?";
                try (PreparedStatement ups = conn.prepareStatement(update)) {
                    ups.setInt(1, obtainedMarks);
                    ups.setInt(2, studentId);
                    ups.setInt(3, paperExamId);
                    ups.executeUpdate();
                }
            } else {
                String insert = "INSERT INTO marks (student_id, paper_exam_id, marks_obtained) VALUES (?, ?, ?)";
                try (PreparedStatement ins = conn.prepareStatement(insert)) {
                    ins.setInt(1, studentId);
                    ins.setInt(2, paperExamId);
                    ins.setInt(3, obtainedMarks);
                    ins.executeUpdate();
                }
            }
        }
    }

    
    //for admin data handling
	@Override
	public String get_password_by_username(String username) {
		String query = "SELECT password FROM admin WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
	}

	@Override
	public boolean check_for_email_validation(String email) {
		String query = "SELECT * FROM admin WHERE email = ?";
	    try (PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setString(1, email);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	//update password
	@Override
	public boolean update_password_to_database(String email, String password) {
		String query = "UPDATE admin SET password = ? WHERE email = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, password);
			ps.setString(2, email);
			int rowsAffected = ps.executeUpdate(); // Use executeUpdate()
			return rowsAffected > 0; // Check if one or more rows were affected
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}



	@Override
	public String runQuery(String query) {
	    try {
	        // Detect query type
	        String trimmedQuery = query.trim().toLowerCase();

	        if (trimmedQuery.startsWith("select")) {
	            // Handle SELECT queries
	            try (PreparedStatement ps = conn.prepareStatement(query)) {
	                ResultSet rs = ps.executeQuery();
	                ResultSetMetaData metaData = rs.getMetaData();
	                int columnCount = metaData.getColumnCount();

	                StringBuilder result = new StringBuilder();

	                // Append column names
	                for (int i = 1; i <= columnCount; i++) {
	                    result.append(metaData.getColumnName(i)).append("\t");
	                }
	                result.append("\n");

	                // Append row data
	                while (rs.next()) {
	                    for (int i = 1; i <= columnCount; i++) {
	                        result.append(rs.getString(i)).append("\t");
	                    }
	                    result.append("\n");
	                }

	                return result.toString().trim().isEmpty() ? "Query executed. No data returned." : result.toString();
	            }
	        } else {
	            // Handle DML/DDL queries
	            try (PreparedStatement ps = conn.prepareStatement(query)) {
	                int affected = ps.executeUpdate();
	                return "Query executed successfully. Rows affected: " + affected;
	            }
	        }

	    } catch (SQLException e) {
	        // Return the SQL error message
	        return "Error: " + e.getMessage();
	    }
	}

	@Override
	public ResultSet fetchStudentMarksDetails(String deptName, String courseName, int semester, int year) throws SQLException {
	    String query = """
	        SELECT 
	    		s.roll_number,
	    		d.name AS dept,
	    		c.name AS course,
	    		se.semester_number,
	    		se.year,
	    		p.id AS paper_id,
	    		p.title,
	    		p.paper_code,
	    		pe.exam_type AS type,
	    		m.marks_obtained
	    		FROM student s
	    		JOIN course c ON s.course_id = c.id
	    		JOIN department d ON c.department_id = d.id
	    		JOIN marks m ON s.id = m.student_id
	    		JOIN paper_exam pe ON m.paper_exam_id = pe.id
	    		JOIN paper p ON pe.paper_id = p.id
	    		JOIN semester se ON p.semester_id = se.id
	    		WHERE d.name = ? AND c.name = ? 
	    		AND se.semester_number = ? AND se.year = ?
	    		ORDER BY s.roll_number, p.id, pe.exam_type;
	    		""";

	    PreparedStatement ps = conn.prepareStatement(query);
	    ps.setString(1, deptName);
	    ps.setString(2, courseName);
	    ps.setInt(3, semester);
	    ps.setInt(4, year);
	    return ps.executeQuery();
	}
	
	public Map<String, StudentResult> fetchResult(
	        String deptName, String courseName, int semester, int year) {

	    String sql = """
	    		SELECT s.roll_number, d.name AS dept, c.name AS course,
	    		se.semester_number, se.year,
	    		p.title AS paper_title, p.paper_code,
	    		pe.exam_type, pe.full_marks, m.marks_obtained
	    		FROM student s
	    		JOIN course c ON s.course_id = c.id
	    		JOIN department d ON c.department_id = d.id
	    		JOIN marks m ON s.id = m.student_id
	    		JOIN paper_exam pe ON m.paper_exam_id = pe.id
	    		JOIN paper p ON pe.paper_id = p.id
	    		JOIN semester se ON p.semester_id = se.id 
	    		WHERE d.name = ? AND c.name = ? AND se.semester_number = ? 
	    		AND se.year = ?
	    		ORDER BY s.roll_number, p.paper_code, pe.exam_type;
	    		""";

	    Map<String, StudentResult> map = new HashMap<>();

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, deptName);
	        ps.setString(2, courseName);
	        ps.setInt(3, semester);
	        ps.setInt(4, year);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                String roll = rs.getString("roll_number");

	                map.putIfAbsent(roll, new StudentResult(
	                        roll,
	                        rs.getString("dept"),
	                        rs.getString("course"),
	                        rs.getInt("semester_number"),
	                        rs.getInt("year")
	                ));

	                PaperDetails details = new PaperDetails(
	                        rs.getString("paper_title"),
	                        rs.getString("paper_code"),
	                        rs.getString("exam_type"),
	                        rs.getInt("full_marks"),
	                        rs.getInt("marks_obtained")
	                );

	                map.get(roll).addPaper(details);
	            }
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
	        return null;
	    }
	    if(map.isEmpty()) {
	    	System.out.println("Null");
	    }else {
	    	System.out.println("Not Empty");
	    }

	    return map;
	}



	
	@Override
	public Map<Integer, String> fetchAllPapers() throws SQLException {
	    String query = "SELECT id, title FROM paper ORDER BY id";
	    Map<Integer, String> papers = new HashMap<>(); 

	    try (Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String title = rs.getString("title");
	            papers.put(id, title);
	        }
	    }

	    return papers;
	}


	@Override
	public String get_user_details() {
	    String query = "SELECT * FROM admin";
	    StringBuilder result = new StringBuilder();

	    try (PreparedStatement ps = conn.prepareStatement(query)) {
	        ResultSet rs = ps.executeQuery();
	        ResultSetMetaData meta = rs.getMetaData();
	        int columnCount = meta.getColumnCount();

	        // Add column headers
	        for (int i = 1; i <= columnCount; i++) {
	            result.append(String.format("%-40s", meta.getColumnName(i)));
	        }
	        result.append("\n");

	        // Add row data
	        boolean hasRows = false;
	        while (rs.next()) {
	            hasRows = true;
	            for (int i = 1; i <= columnCount; i++) {
	                result.append(String.format("%-40s", rs.getString(i)));
	            }
	            result.append("\n");
	        }

	        if (!hasRows) {
	            return "No users to display";
	        }

	        return result.toString();

	    } catch (SQLException e) {
	        return "Error: " + e.getMessage();
	    }
	}

	@Override
	public boolean saveAdmin(AdminDTO admin) {
	    String getMaxIdQuery = "SELECT id FROM admin ORDER BY id DESC LIMIT 1";
	    String insertQuery = "INSERT INTO admin (id, email, password) VALUES (?, ?, ?)";

	    try (
	        PreparedStatement getMaxIdStmt = conn.prepareStatement(getMaxIdQuery);
	        ResultSet rs = getMaxIdStmt.executeQuery()
	    ) {
	        String newId = "A00001"; //first Id
	        if (rs.next()) {
	            String lastId = rs.getString("id"); 
	            int num = Integer.parseInt(lastId.substring(1)); 
	            num += 1;
	            newId = String.format("A%05d", num); 
	        }
	        
	        System.out.println(newId+" "+admin.getUsername()+" "+admin.getPassword());

	        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
	            insertStmt.setString(1, newId);
	            insertStmt.setString(2, admin.getUsername());
	            insertStmt.setString(3, admin.getPassword());

	            int rowsInserted = insertStmt.executeUpdate();
	            return rowsInserted > 0;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
}
