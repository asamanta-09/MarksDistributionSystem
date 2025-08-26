package view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Dashboard extends JFrame {
    private JTextField filePathField;
    private JTextArea viewInfoArea,queryArea;
    private JPanel mainPanel,uploadPanel,fetchPanel,queryPanel,viewInfoPanel;
    private JButton chooseFileButton, uploadButton,viewUsersButton,addUserButton,generatePDFButton,runQueryButton,generateResultButton,viewDetailsButton,clearButton;
    private JComboBox<String> uploadDeptCombo, uploadCourseCombo,uploadSemesterCombo,uploadYearCombo,fetchDeptCombo, fetchCourseCombo,fetchSemesterCombo,fetchYearCombo;


    public Dashboard() {
        setTitle("Dashboard");
        getContentPane().setBackground(new Color(240, 248, 255));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        int panelWidth = 1300;
        int panelHeight = 800;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - panelWidth) / 2;
        int y = (screenSize.height - panelHeight) / 2;

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(x, y, panelWidth, panelHeight);
        mainPanel.setBackground(new Color(240, 248, 255));
        add(mainPanel);

        // Welcome Back (Left Side)
        JLabel title = new JLabel("Welcome Back, Admin");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(0, 102, 204));
        title.setBounds(30, 10, 470, 40);
        mainPanel.add(title);

        // Add User Button (Right Side)
        addUserButton = new JButton("Add User");
        addUserButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addUserButton.setBounds(950, 15, 130, 30);
        addUserButton.setFocusPainted(false);
        addUserButton.setBackground(new Color(23, 162, 184));
        addUserButton.setForeground(Color.WHITE);
        addUserButton.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(addUserButton);

        // View Users Button (Next to Add User)
        viewUsersButton = new JButton("View Users");
        viewUsersButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        viewUsersButton.setBounds(1110, 15, 130, 30);
        viewUsersButton.setFocusPainted(false);
        viewUsersButton.setBackground(new Color(108, 117, 125));
        viewUsersButton.setForeground(Color.WHITE);
        viewUsersButton.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(viewUsersButton);


        // Upload Panel
        uploadPanel = createUploadPanel();
        uploadPanel.setBounds(30, 80, 1240, 100);
        mainPanel.add(uploadPanel);

        // Fetch Panel
        fetchPanel = createFetchPanel();
        fetchPanel.setBounds(30, 200, 1240, 100);
        mainPanel.add(fetchPanel);

        // QUERY Panel
        queryPanel = createQueryPanel();
        queryPanel.setBounds(30, 320, 1240, 150);
        mainPanel.add(queryPanel);

        // VIEW INFO Panel
        viewInfoPanel = createViewInfoPanel();
        viewInfoPanel.setBounds(30, 490, 1240, 290);
        mainPanel.add(viewInfoPanel);
    }

    private JPanel createUploadPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createTitledBorder("Upload Information"));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        JLabel deptLabel = new JLabel("Department:");
        deptLabel.setFont(labelFont);
        deptLabel.setBounds(20, 30, 100, 25);
        panel.add(deptLabel);

        uploadDeptCombo = new JComboBox<>(new String[]{"CSE"});
        uploadDeptCombo.setFont(fieldFont);
        uploadDeptCombo.setBounds(120, 30, 130, 30);
        uploadDeptCombo.setBorder(new LineBorder(Color.GRAY, 1, true));
        panel.add(uploadDeptCombo);

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setFont(labelFont);
        courseLabel.setBounds(260, 30, 60, 25);
        panel.add(courseLabel);

        uploadCourseCombo = new JComboBox<>(new String[]{"Select","B.Tech","M.Tech","P.Hd","M.Sc","MCA"});
        uploadCourseCombo.setFont(fieldFont);
        uploadCourseCombo.setBounds(320, 30, 130, 30);
        uploadCourseCombo.setBorder(new LineBorder(Color.GRAY, 1, true));
        panel.add(uploadCourseCombo);

        JLabel semLabel = new JLabel("Semester:");
        semLabel.setFont(labelFont);
        semLabel.setBounds(460, 30, 80, 25);
        panel.add(semLabel);

        String[] semesters = {"1", "2", "3", "4", "5", "6", "7", "8"};
        uploadSemesterCombo = new JComboBox<>(semesters);
        uploadSemesterCombo.setFont(fieldFont);
        uploadSemesterCombo.setBounds(540, 30, 60, 30);
        uploadSemesterCombo.setBorder(new LineBorder(Color.GRAY, 1, true));
        panel.add(uploadSemesterCombo);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setFont(labelFont);
        yearLabel.setBounds(610, 30, 40, 25);
        panel.add(yearLabel);

        String[] years = new String[51];
        for (int i = 0; i <= 50; i++) {
            years[i] = String.valueOf(2000 + i);
        }
        uploadYearCombo = new JComboBox<>(years);
        uploadYearCombo.setFont(fieldFont);
        uploadYearCombo.setBounds(650, 30, 80, 30);
        uploadYearCombo.setBorder(new LineBorder(Color.GRAY, 1, true));
        panel.add(uploadYearCombo);

        chooseFileButton = new JButton("Choose File");
        chooseFileButton.setFont(fieldFont);
        chooseFileButton.setBounds(740, 30, 120, 30);
        chooseFileButton.setFocusPainted(false);
        chooseFileButton.setBackground(new Color(0, 102, 204));
        chooseFileButton.setForeground(Color.WHITE);
        chooseFileButton.setBorder(BorderFactory.createEmptyBorder());
        panel.add(chooseFileButton);


        filePathField = new JTextField("No file chosen");
        filePathField.setFont(fieldFont);
        filePathField.setBounds(870, 30, 230, 30);
        filePathField.setEditable(false);
        filePathField.setBorder(new LineBorder(Color.GRAY, 1, true));
        panel.add(filePathField);

        uploadButton = new JButton("Upload");
        uploadButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        uploadButton.setBounds(1110, 30, 100, 30);
        uploadButton.setFocusPainted(false);
        uploadButton.setBackground(new Color(40, 167, 69));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setBorder(BorderFactory.createEmptyBorder());
        panel.add(uploadButton);

        return panel;
    }


    private JPanel createFetchPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createTitledBorder("Fetch Information"));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);
        int y = 30;

        JLabel deptLabel = new JLabel("Department:");
        deptLabel.setFont(labelFont);
        deptLabel.setBounds(20, y, 100, 25);
        panel.add(deptLabel);

        fetchDeptCombo = new JComboBox<>(new String[]{"CSE"});
        fetchDeptCombo.setFont(fieldFont);
        fetchDeptCombo.setBounds(120, y, 170, 30);
        fetchDeptCombo.setBorder(new LineBorder(Color.GRAY, 1, true));
        panel.add(fetchDeptCombo);

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setFont(labelFont);
        courseLabel.setBounds(310, y, 60, 25);
        panel.add(courseLabel);

        fetchCourseCombo = new JComboBox<>(new String[]{"Select","B.Tech","M.Tech","P.Hd","M.Sc","MCA"});
        fetchCourseCombo.setFont(fieldFont);
        fetchCourseCombo.setBounds(370, y, 150, 30);
        fetchCourseCombo.setBorder(new LineBorder(Color.GRAY, 1, true));
        panel.add(fetchCourseCombo);

        JLabel semLabel = new JLabel("Semester:");
        semLabel.setFont(labelFont);
        semLabel.setBounds(560, y, 80, 25);
        panel.add(semLabel);

        String[] semesters = {"1", "2", "3", "4", "5", "6", "7", "8"};
        fetchSemesterCombo = new JComboBox<>(semesters);
        fetchSemesterCombo.setFont(fieldFont);
        fetchSemesterCombo.setBounds(640, y, 80, 30);
        fetchSemesterCombo.setBorder(new LineBorder(Color.GRAY, 1, true));
        panel.add(fetchSemesterCombo);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setFont(labelFont);
        yearLabel.setBounds(740, y, 40, 25);
        panel.add(yearLabel);

        String[] years = new String[51];
        for (int i = 0; i <= 50; i++) {
            years[i] = String.valueOf(2000 + i);
        }
        fetchYearCombo = new JComboBox<>(years);
        fetchYearCombo.setFont(fieldFont);
        fetchYearCombo.setBounds(780, y, 80, 30);
        fetchYearCombo.setBorder(new LineBorder(Color.GRAY, 1, true));
        panel.add(fetchYearCombo);

        generateResultButton = new JButton("Generate Result");
        generateResultButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        generateResultButton.setBounds(890, y, 160, 30);
        generateResultButton.setFocusPainted(false);
        generateResultButton.setBackground(new Color(40, 167, 69));
        generateResultButton.setForeground(Color.WHITE);
        generateResultButton.setBorder(BorderFactory.createEmptyBorder());
        panel.add(generateResultButton);

        viewDetailsButton = new JButton("View Details");
        viewDetailsButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        viewDetailsButton.setBounds(1070, y, 140, 30);
        viewDetailsButton.setFocusPainted(false);
        viewDetailsButton.setBackground(new Color(0, 123, 255));
        viewDetailsButton.setForeground(Color.WHITE);
        viewDetailsButton.setBorder(BorderFactory.createEmptyBorder());
        panel.add(viewDetailsButton);

        return panel;
    }

    
    private JPanel createQueryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createTitledBorder("Write SQL Query"));

        Font font = new Font("Segoe UI", Font.PLAIN, 15);

        queryArea = new JTextArea();
        queryArea.setFont(font);
        queryArea.setLineWrap(true);
        queryArea.setWrapStyleWord(true);
        queryArea.setBorder(new LineBorder(Color.GRAY, 1, true));

        JScrollPane scrollPane = new JScrollPane(queryArea);
        scrollPane.setBounds(20, 40, 1190, 60);
        panel.add(scrollPane);

        runQueryButton = new JButton("Run");
        runQueryButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        runQueryButton.setBounds(20, 107, 120, 35);
        runQueryButton.setFocusPainted(false);
        runQueryButton.setBackground(new Color(255, 193, 7));
        runQueryButton.setForeground(Color.BLACK);
        runQueryButton.setBorder(BorderFactory.createEmptyBorder());
        panel.add(runQueryButton);

        return panel;
    }

    private JPanel createViewInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createTitledBorder("View Information"));

        viewInfoArea = new JTextArea();
        viewInfoArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        viewInfoArea.setLineWrap(false);
        viewInfoArea.setWrapStyleWord(false);
        viewInfoArea.setEditable(false);
        viewInfoArea.setBorder(new LineBorder(Color.GRAY, 1, true));
        

        JScrollPane scrollPane = new JScrollPane(viewInfoArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(20, 20, 1190, 150);
        panel.add(scrollPane);
        
        
        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        clearButton.setBounds(920, 190, 100, 35);
        clearButton.setFocusPainted(false);
        clearButton.setBackground(new Color(255, 193, 7));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBorder(BorderFactory.createEmptyBorder());
        panel.add(clearButton);


        generatePDFButton = new JButton("Generate PDF");
        generatePDFButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        generatePDFButton.setBounds(1050, 190, 160, 35);
        generatePDFButton.setFocusPainted(false);
        generatePDFButton.setBackground(new Color(0, 102, 204));
        generatePDFButton.setForeground(Color.WHITE);
        generatePDFButton.setBorder(BorderFactory.createEmptyBorder());
        panel.add(generatePDFButton);

        panel.setPreferredSize(new Dimension(1240, 190));

        return panel;
    }
    
    // Upload Panel Getters
    public JComboBox<String> getUploadDeptCombo() {
        return uploadDeptCombo;
    }

    public JComboBox<String> getUploadCourseCombo() {
        return uploadCourseCombo;
    }

    public JComboBox<String> getUploadSemesterCombo() {
        return uploadSemesterCombo;
    }

    public JComboBox<String> getUploadYearCombo() {
        return uploadYearCombo;
    }

    public JButton getChooseFileButton() {
        return chooseFileButton;
    }

    public JTextField getFilePathField() {
        return filePathField;
    }

    public JButton getUploadButton() {
        return uploadButton;
    }

    // Fetch Panel Getters
    public JComboBox<String> getFetchDeptCombo() {
        return fetchDeptCombo;
    }

    public JComboBox<String> getFetchCourseCombo() {
        return fetchCourseCombo;
    }

    public JComboBox<String> getFetchSemesterCombo() {
        return fetchSemesterCombo;
    }

    public JComboBox<String> getFetchYearCombo() {
        return fetchYearCombo;
    }

    public JButton getGenerateResultButton() {
        return generateResultButton;
    }

    public JButton getViewDetailsButton() {
        return viewDetailsButton;
    }

    // Query Panel Getters
    public JTextArea getQueryArea() {
        return queryArea;
    }

    public JButton getRunQueryButton() {
        return runQueryButton;
    }

    // View Info Panel Getters
    public JTextArea getViewInfoArea() {
        return viewInfoArea;
    }

    public JButton getGeneratePDFButton() {
        return generatePDFButton;
    }
    
    public JButton getClearButton() {
        return clearButton;
    }

    // Top Buttons
    public JButton getAddUserButton() {
        return addUserButton;
    }

    public JButton getViewUsersButton() {
        return viewUsersButton;
    }
}