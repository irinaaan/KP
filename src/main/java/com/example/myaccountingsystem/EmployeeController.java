package com.example.myaccountingsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeController {

    @FXML
    private VBox employeeContainer;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldDate;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldJobTitle;

    @FXML
    private ScrollPane scrollPane;

    private int employeeCount = 0;

    private DatabaseHandler databaseHandler;

    public void initialize() {
        scrollPane.setFitToWidth(true);
        databaseHandler = new DatabaseHandler();

        ResultSet employees = databaseHandler.getAllEmployees();

        try {
            while (employees.next()) {
                int id = employees.getInt("idemployees");
                String name = employees.getString("name");
                String jobTitle = employees.getString("job_title");
                String date = employees.getString("date_of_birth");
                String email = employees.getString("email");
                Employee employee = new Employee(name, jobTitle, date, email);
                employee.setId(id);
                employeeCount++;

                Label employeeLabel = new Label(employee.getId() + ". " + employee.getName() + ", " + employee.getJobTitle() + ", " + employee.getDate()+ ", " + employee.getEmail());
                employeeLabel.setStyle("-fx-font-size: 16px; -fx-padding: 0 10 0 10;");
                employeeContainer.getChildren().add(employeeLabel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addEmployee() {

        String name = textFieldName.getText();
        String jobTitle = textFieldJobTitle.getText();
        String date = textFieldDate.getText();
        String email = textFieldEmail.getText();


        if (name.isEmpty() || jobTitle.isEmpty() || textFieldDate.getText().isEmpty() || textFieldEmail.getText().isEmpty()) {
            return;
        }

        Employee newEmployee = new Employee(name, jobTitle, date, email);
        int newEmployeeId = databaseHandler.addEmployee(newEmployee);
        newEmployee.setId(newEmployeeId);
        employeeCount++;

        Label employeeLabel = new Label(newEmployee.getId() + ". " + newEmployee.getName() + ", " + newEmployee.getJobTitle()+ ", " + newEmployee.getDate() + ", " + newEmployee.getEmail());
        employeeLabel.setStyle("-fx-font-size: 16px; -fx-padding: 0 10 0 10;");
        employeeContainer.getChildren().add(employeeLabel);

        databaseHandler.addEmployee(newEmployee);

        textFieldName.clear();
        textFieldJobTitle.clear();
        textFieldDate.clear();
        textFieldEmail.clear();
    }
}