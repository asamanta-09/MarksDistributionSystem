package dto;

import java.util.ArrayList;
import java.util.List;

public class StudentResult {
    private String roll;
    private String department;
    private String course;
    private int semester;
    private int year;
    private List<PaperDetails> papers = new ArrayList<>();

    public StudentResult(String roll, String department, String course, int semester, int year) {
        this.roll = roll;
        this.department = department;
        this.course = course;
        this.semester = semester;
        this.year = year;
    }

    public void addPaper(PaperDetails details) {
        papers.add(details);
    }

    public String getRoll() { return roll; }
    public String getDepartment() { return department; }
    public String getCourse() { return course; }
    public int getSemester() { return semester; }
    public int getYear() { return year; }
    public List<PaperDetails> getPapers() { return papers; }
}

