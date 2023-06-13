package com.example.myaccountingsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class LogInController {

    @FXML
    private Button buttonSignUp;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private Button buttonLogin;

    @FXML
    private Text textPassword;

    @FXML
    private Label labelLogin;

    @FXML
    private Text textLogin;

    @FXML
    private Text textNeedAnAccount;

    @FXML
    private TextField fieldLogin;

    @FXML
    private Label labelError;

    @FXML
    private AnchorPane ap;

    @FXML
    void initialize() {
        buttonLogin.setOnAction(event ->{
            String loginField = fieldLogin.getText().trim(); //пробелы удаляются
            String passwordField = fieldPassword.getText().trim();
            if(!loginField.equals("") && !passwordField.equals("")) {
                loginUser(loginField, passwordField);
            }
            else {
                labelError.setText("Login or password is empty");
                labelError.setStyle("-fx-text-fill: red;");
            }
        });

        buttonSignUp.setOnAction(actionEvent -> {
            openNewScene("signup.fxml");
        });

        buttonLogin.setOnMouseEntered(event -> {
            buttonLogin.setStyle("-fx-background-color: #D87373;");
        });
        buttonLogin.setOnMouseExited(event -> {
            buttonLogin.setStyle("-fx-background-color: #F08080;");
        });

        buttonSignUp.setOnMouseEntered(event -> {
            buttonSignUp.setStyle("-fx-background-color: #D87373;");
        });
        buttonSignUp.setOnMouseExited(event -> {
            buttonSignUp.setStyle("-fx-background-color: #F08080;");
        });
    }

    private void loginUser(String loginField, String passwordField) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setLogin(loginField);
        user.setPassword(passwordField);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;

        while(true) {
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } //пройдется по всем пользователям и посчитает сколько их
            counter++;
        }

        if(counter >=1) {
            openNewScene("main.fxml");
        }
        else {
            labelError.setText("Incorrect login or password");
            labelError.setStyle("-fx-text-fill: red;");
        }
    }

    public void openNewScene(String window) {
        buttonSignUp.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        stage.getIcons().add(icon);

        stage.show();
    }
}