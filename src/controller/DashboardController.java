package controller;

import javax.swing.*;

import dto.StudentResult;

import java.io.IOException;
import java.util.Map;

import model.DashboardModel;
import view.CreateAccount;
import view.Dashboard;

public class DashboardController {
	private String filePath;
    private final Dashboard view;
    private final DashboardModel model;

    public DashboardController(Dashboard view, DashboardModel model) throws Exception {
        this.view = view;
        this.model = model;
        initController();
    }

    private void initController() throws Exception{
        view.getChooseFileButton().addActionListener(e -> handleChooseFile());
        view.getUploadButton().addActionListener(e -> {
			try {
				handleUploadButton();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
        view.getGenerateResultButton().addActionListener(e -> {
			try {
				handleGenerateResult();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
        view.getViewDetailsButton().addActionListener(e -> {
			try {
				handleViewDetails();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
        view.getRunQueryButton().addActionListener(e -> handleRunQuery());
        view.getGeneratePDFButton().addActionListener(e -> handleGeneratePDF());
        view.getAddUserButton().addActionListener(e -> handleAddUser());
        view.getViewUsersButton().addActionListener(e -> handleViewUser());
        view.getClearButton().addActionListener(e->view.getViewInfoArea().setText(""));
    }

    private void handleChooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(view);

        if (option == JFileChooser.APPROVE_OPTION) {
            String selectedFileName = fileChooser.getSelectedFile().getName();
            filePath=fileChooser.getSelectedFile().getAbsolutePath();
            view.getFilePathField().setText(selectedFileName);
        } else {
            view.getFilePathField().setText("No file chosen");
        }
    }

    private void handleUploadButton() throws IOException {
        // Fetch values from the view
        String department = (String) view.getUploadDeptCombo().getSelectedItem();
        String course = (String) view.getUploadCourseCombo().getSelectedItem();
        String semester = (String) view.getUploadSemesterCombo().getSelectedItem();
        String year = (String) view.getUploadYearCombo().getSelectedItem();
         String fileName = view.getFilePathField().getText();

        if (fileName.equals("No file chosen") || fileName.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please choose a file first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.print(filePath);
        model.uploadData(department,course,semester,year,filePath);
        JOptionPane.showMessageDialog(view, "Sucessfully Uploaded the Excel Data", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleGenerateResult() throws Exception {
    	
    	String department = (String) view.getFetchDeptCombo().getSelectedItem();
        String course = (String) view.getFetchCourseCombo().getSelectedItem();
        String semester = (String) view.getFetchSemesterCombo().getSelectedItem();
        String year = (String) view.getFetchYearCombo().getSelectedItem();
        String fileName = department+"_"+course+"_"+semester+"_"+year;
        Map<String, StudentResult> result = model.generateResult(department, course, semester, year);
        System.out.println(fileName);
        if(result.isEmpty()) {
        	JOptionPane.showMessageDialog(view, "Cannot Fetch Student Details");
        }else {
        	utility.CreatePDF.createResultPDF(result,fileName);
        }
       
    }

    private void handleViewDetails() throws Exception {
    	String department = (String) view.getFetchDeptCombo().getSelectedItem();
        String course = (String) view.getFetchCourseCombo().getSelectedItem();
        String semester = (String) view.getFetchSemesterCombo().getSelectedItem();
        String year = (String) view.getFetchYearCombo().getSelectedItem();
    	String details = model.getFormattedMarksDetails(department,course,semester,year);
        view.getViewInfoArea().setText(details);
    }

    private void handleRunQuery() {
        String query = view.getQueryArea().getText();
        if (query.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter a query.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String result=model.getQueryResultForView(query);
        view.getViewInfoArea().setText("Query executed:\n" + query + "\nResult simulation:\n"+result);
    }

    private void handleGeneratePDF() {
        String content=view.getViewInfoArea().getText();
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(view, "TextArea is empty!");
        } else {
            utility.CreatePDF.createPDF(content);
        }
    }

    private void handleAddUser() {
        //JOptionPane.showMessageDialog(view, "Add user functionality is not implemented yet.", "Info", JOptionPane.INFORMATION_MESSAGE);
    	CreateAccount createAccountView = new CreateAccount();
    	new AdminSignUpController(createAccountView, view); 
    	createAccountView.setVisible(true);
    	view.setVisible(false);
    }

    private void handleViewUser() {
        String users=model.viewUserDetails();
        view.getViewInfoArea().setText(users);
    }
}
