package dto;

public class StudentDetails {
    private String paper_title;
    private String paper_code;
    private String examType;
    private String roll;
    private int full_marks;
    private int obtained_marks;

    public StudentDetails(String paper_title, String paper_code, String type, String roll, int full_marks, int obtained_marks) {
        this.paper_title = paper_title;
        this.paper_code = paper_code;
        this.examType = type;
        this.roll=roll;
        this.full_marks = full_marks;
        this.obtained_marks = obtained_marks;
    }

    public String getPaper_title() {
        return paper_title;
    }

    public String getPaper_code() {
        return paper_code;
    }

    public String getType() {
        return examType;
    }

    public String getRoll() {
        return roll;
    }
    
    public int getFull_marks() {
        return full_marks;
    }

    public int getObtained_marks() {
        return obtained_marks;
    }
}
