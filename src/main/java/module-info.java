module com.example.layeredarchitecture {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;
    requires static lombok;

    opens lk.ijse.ceylontool to javafx.fxml;
    opens lk.ijse.ceylontool.controller to javafx.fxml;
    opens lk.ijse.ceylontool.view.tm to javafx.base;

    exports lk.ijse.ceylontool;
    exports lk.ijse.ceylontool.controller;
}
