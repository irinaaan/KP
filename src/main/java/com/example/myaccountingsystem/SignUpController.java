package com.example.myaccountingsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.text.Text;

public class SignUpController {

    @FXML
    private Button buttonSignUp;

    @FXML
    private Text textAlreadyAUser;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private Text textName;

    @FXML
    private TextField fieldName;

    @FXML
    private Text textSurname;

    @FXML
    private Button buttonLogin;

    @FXML
    private Text textPassword;

    @FXML
    private Text textLogin;

    @FXML
    private TextField fieldSurname;

    @FXML
    private TextField fieldLogin;

    @FXML
    private Label labelSignUp;

    @FXML
    private Label labelError;

    @FXML
    private AnchorPane ap;

    @FXML
    void initialize() {
        buttonSignUp.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            String loginField = fieldLogin.getText().trim(); //пробелы удаляются
            String passwordField = fieldPassword.getText().trim();
            String nameField = fieldName.getText().trim(); //пробелы удаляются
            String surnameField = fieldSurname.getText().trim();
            if(!loginField.equals("") && !passwordField.equals("") && !nameField.equals("") && !surnameField.equals("")) {
                signUpNewUser();
                loader.setLocation(getClass().getResource("login.fxml"));
            }
            else {
                labelError.setText("Fields must not be empty");
                labelError.setLayoutX(100);
                labelError.setLayoutY(285);
                labelError.setStyle("-fx-text-fill: red;");
            }
        });

        buttonLogin.setOnAction(actionEvent -> {
            buttonLogin.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("login.fxml"));

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

    private void signUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String firstname = fieldName.getText();
        String lastname = fieldSurname.getText();
        String login = fieldLogin.getText();
        String password = fieldPassword.getText();

        User user = new User(firstname, lastname, login, password);

        // dbHandler.signUpUser(user);
        if (dbHandler.userExists(login)) {
            labelError.setText("User with this login already exists");
            labelError.setLayoutX(87);
            labelError.setLayoutY(285);
            labelError.setStyle("-fx-text-fill: red;");
        } else {
            dbHandler.signUpUser(user);
            // переход на форму логина
        }
    }
}