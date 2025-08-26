package utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dto.PaperDetails;
import dto.StudentResult;

public class CreatePDF {
	
	public static void createPDF(String text) {

	    String userHome = System.getProperty("user.home");
	    String folderPath = userHome + "/Downloads/pdf_downloaded";

	    //create the folder if not exists
	    java.io.File folder = new java.io.File(folderPath);
	    if (!folder.exists()) {
	        folder.mkdirs();
	    }

	    // Set the output file path
	    String filePath = folderPath + "/output.pdf";

	    Document document = new Document();
	    try {
	        PdfWriter.getInstance(document, new FileOutputStream(filePath));
	        document.open();
	        document.add(new Paragraph(text));
	        JOptionPane.showMessageDialog(null, "PDF created at:\n" + filePath);
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error generating PDF: " + e.getMessage());
	    } finally {
	        document.close();
	    }
	}
	
	public static void createResultPDF(Map<String, StudentResult> result, String fileName) {
	    String userHome = System.getProperty("user.home");
	    String folderPath = userHome + "/Downloads/pdf_downloaded";

	    // Create folder if not exists
	    java.io.File folder = new java.io.File(folderPath);
	    if (!folder.exists()) {
	        folder.mkdirs();
	    }

	    String filePath = folderPath + "/" + fileName + ".pdf";

	    Document document = new Document();
	    try {
	        PdfWriter.getInstance(document, new FileOutputStream(filePath));
	        document.open();

	        for (StudentResult sr : result.values()) {
	            // Student info
	            document.add(new Paragraph("Roll Number: " + sr.getRoll()));
	            document.add(new Paragraph("Department: " + sr.getDepartment()));
	            document.add(new Paragraph("Course: " + sr.getCourse()));
	            document.add(new Paragraph("Semester: " + sr.getSemester()));
	            document.add(new Paragraph("Year: " + sr.getYear()));
	            document.add(Chunk.NEWLINE);

	            // Create table with 5 columns
	            PdfPTable table = new PdfPTable(5);
	            table.setWidthPercentage(100);
	            table.setWidths(new float[]{3, 2, 2, 2, 2});

	            // Table headers
	            String[] headers = {"Paper Name", "Paper Code", "Exam Type", "Full Marks", "Obtained Marks"};
	            for (String header : headers) {
	                PdfPCell headerCell = new PdfPCell(new Phrase(header));
	                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                table.addCell(headerCell);
	            }

	            // Process each subject
	            Map<String, List<PaperDetails>> subjectMap = new LinkedHashMap<>();
	            for (PaperDetails paper : sr.getPapers()) {
	                String key = paper.getPaperCode() + "_" + paper.getPaperTitle();
	                subjectMap.computeIfAbsent(key, k -> new ArrayList<>()).add(paper);
	            }

	            for (Map.Entry<String, List<PaperDetails>> entry : subjectMap.entrySet()) {
	                List<PaperDetails> papers = entry.getValue();

	                int totalMarks = 0;
	                int totalObtained = 0;

	                // Merge cells for Paper Name + Code (row span)
	                PdfPCell nameCell = new PdfPCell(new Phrase(papers.get(0).getPaperTitle()));
	                PdfPCell codeCell = new PdfPCell(new Phrase(papers.get(0).getPaperCode()));
	                nameCell.setRowspan(papers.size() + 1); // +1 for total row
	                codeCell.setRowspan(papers.size() + 1);
	                nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                codeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

	                // Add merged cells only once
	                table.addCell(nameCell);
	                table.addCell(codeCell);

	                // Add each exam type row
	                boolean firstRow = true;
	                for (PaperDetails paper : papers) {
	                    if (!firstRow) {
	                        // For subsequent rows, need to skip paper name & code
	                        // so add empty cells already covered by rowspan
	                    }
	                    table.addCell(centerCell(paper.getExamType()));
	                    table.addCell(centerCell(String.valueOf(paper.getFullMarks())));
	                    table.addCell(centerCell(String.valueOf(paper.getObtainedMarks())));

	                    totalMarks += paper.getFullMarks();
	                    totalObtained += paper.getObtainedMarks();
	                    firstRow = false;
	                }

	                // Add TOTAL row under Exam Type
	                table.addCell(centerCell("TOTAL"));
	                table.addCell(centerCell(String.valueOf(totalMarks)));
	                table.addCell(centerCell(String.valueOf(totalObtained)));
	            }

	            document.add(table);
	            document.newPage();
	        }

	        JOptionPane.showMessageDialog(null, "PDF created at:\n" + filePath);
	    } catch (DocumentException | IOException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error generating PDF: " + e.getMessage());
	    } finally {
	        document.close();
	    }
	}

	// Helper method for centered cells
	private static PdfPCell centerCell(String content) {
	    PdfPCell cell = new PdfPCell(new Phrase(content));
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    return cell;
	}
}