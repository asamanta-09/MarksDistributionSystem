package setup;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MarksDetails_All_Table_Creation {

    public static void createAllTables(Connection connection) {
        boolean anyTableCreated = false;

        try (Statement stmt = connection.createStatement()) {
            String[] tableNames = {
                "department", "course", "semester", "paper",
                "paper_exam", "student", "marks"
            };

            String[] createStatements = {
                // department
                """
                CREATE TABLE department (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL UNIQUE
                )
                """,

                // course
                """
                CREATE TABLE course (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    department_id INT,
                    duration_semesters INT,
                    FOREIGN KEY (department_id) REFERENCES department(id)
                )
                """,

                // semester
                """
                CREATE TABLE semester (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    course_id INT,
                    semester_number INT,
                    year INT,
                    FOREIGN KEY (course_id) REFERENCES course(id)
                )
                """,

                // paper
                """
                CREATE TABLE paper (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    paper_code VARCHAR(50) NOT NULL,
                    title VARCHAR(255) NOT NULL,
                    semester_id INT,
                    FOREIGN KEY (semester_id) REFERENCES semester(id),
                    UNIQUE (paper_code, semester_id)
                )
                """,

                // paper_exam
                """
                CREATE TABLE paper_exam (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    paper_id INT NOT NULL,
                    exam_type VARCHAR(20) NOT NULL,
                    full_marks INT NOT NULL,
                    FOREIGN KEY (paper_id) REFERENCES paper(id),
                    UNIQUE (paper_id, exam_type)
                )
                """,

                // student
                """
                CREATE TABLE student (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    roll_number VARCHAR(50) NOT NULL UNIQUE,
                    course_id INT,
                    FOREIGN KEY (course_id) REFERENCES course(id)
                )
                """,

                // marks
                """
                CREATE TABLE marks (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    student_id INT NOT NULL,
                    paper_exam_id INT NOT NULL,
                    marks_obtained INT DEFAULT 0,
                    FOREIGN KEY (student_id) REFERENCES student(id),
                    FOREIGN KEY (paper_exam_id) REFERENCES paper_exam(id),
                    UNIQUE (student_id, paper_exam_id)
                )
                """
            };

            DatabaseMetaData dbMetaData = connection.getMetaData();

            for (int i = 0; i < tableNames.length; i++) {
                ResultSet rs = dbMetaData.getTables(null, null, tableNames[i], null);
                if (!rs.next()) {
                    stmt.executeUpdate(createStatements[i]);
                    anyTableCreated = true;
                }
                rs.close();
            }

            if (anyTableCreated) {
                System.out.println("All marks-related tables created successfully (normalized structure).");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
