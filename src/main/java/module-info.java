module com.example.myaccountingsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires mysql.connector.j;
    requires com.google.gson;

    opens com.example.myaccountingsystem to javafx.fxml;
    exports com.example.myaccountingsystem;
}