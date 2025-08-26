package dto;

public class PaperDetails {
    private String paperTitle;
    private String paperCode;
    private String examType;
    private int fullMarks;
    private int obtainedMarks;

    public PaperDetails(String paperTitle, String paperCode, String examType, int fullMarks, int obtainedMarks) {
        this.paperTitle = paperTitle;
        this.paperCode = paperCode;
        this.examType = examType;
        this.fullMarks = fullMarks;
        this.obtainedMarks = obtainedMarks;
    }

    public String getPaperTitle() { return paperTitle; }
    public String getPaperCode() { return paperCode; }
    public String getExamType() { return examType; }
    public int getFullMarks() { return fullMarks; }
    public int getObtainedMarks() { return obtainedMarks; }
}
