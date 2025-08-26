package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dto.StudentDetails;

public class ExcelRead {

    public static List<StudentDetails> readExcelData(String filePath) throws IOException {
        List<StudentDetails> studentList = new ArrayList<>();

        FileInputStream inputStream = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        int startRow = 9;

        for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;

            String paper_title = getCellValueAsString(row.getCell(1));
            String paper_code  = getCellValueAsString(row.getCell(2));
            String type        = getCellValueAsString(row.getCell(3));
            String coll        = getCellValueAsString(row.getCell(5));
            String cate        = getCellValueAsString(row.getCell(6));
            int number         = parseIntSafe(row.getCell(7));
            int full_marks     = parseIntSafe(row.getCell(8));
            int obtained_marks = parseIntSafe(row.getCell(9));

            // Skip rows that have no data
            if (paper_title.isEmpty() && paper_code.isEmpty() && number == 0 && obtained_marks == 0) {
                continue;
            }

            String roll=coll+cate+number;
            StudentDetails student = new StudentDetails( paper_title, paper_code, type, roll, full_marks, obtained_marks );
            studentList.add(student);
        }

        workbook.close();
        inputStream.close();
        return studentList;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        //cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    private static int parseIntSafe(Cell cell) {
        if (cell == null) return 0;
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return (int) cell.getNumericCellValue();
                case STRING:
                    String val = cell.getStringCellValue().trim();
                    return Integer.parseInt(val);
                default:
                    return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }
}
