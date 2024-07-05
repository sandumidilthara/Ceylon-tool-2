package lk.ijse.ceylontool.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lk.ijse.ceylontool.db.DBConnection;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DailyReviewFormController {










    @FXML
    public Label lblItemCount;

    @FXML
    public Label lblOrderCount;

    @FXML
    public Label lblSupplierCount;

    @FXML
    private AnchorPane root;


    public Label lblCustomerCount;
    private int customerCount;
    private int itemCount;
    private int orderCount;
    private int supplierCount;

    public void initialize() throws ClassNotFoundException {
        try {
            customerCount = getCustomerCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setCustomerCount(customerCount);




        try {
            itemCount = getItemCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setItemCount(itemCount);



       try {
            orderCount = getOrderCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setOrderCount(orderCount);




        try {
            supplierCount = getSupplierCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setSupplierCount(supplierCount);

    }

    private void setCustomerCount(int customerCount) {
        lblCustomerCount.setText(String.valueOf(customerCount));
    }

    private int getCustomerCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) AS customer_count FROM Customer";

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int customerCount = 0;
        if(resultSet.next()) {
            customerCount = resultSet.getInt("customer_count");
        }
        return customerCount;




    }


    public void navigateToHome(MouseEvent mouseEvent) throws IOException {





        URL resource = this.getClass().getResource("/lk/ijse/ceylontool/view/main-form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    private void setItemCount(int itemCount) {
        lblItemCount.setText(String.valueOf(itemCount));
    }

    private int getItemCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) AS item_count FROM    Item";

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int itemCount = 0;
        if(resultSet.next()) {
            itemCount = resultSet.getInt("item_count");
        }
        return itemCount;
    }







    private void setOrderCount(int orderCount) {
        lblOrderCount.setText(String.valueOf(orderCount));
    }

    private int getOrderCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) AS order_count FROM    Orders";

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        int orderCount;
        try (ResultSet resultSet = pstm.executeQuery()) {

            orderCount = 0;
            if (resultSet.next()) {
                orderCount = resultSet.getInt("order_count");
            }
        }
        return orderCount;
    }


    private void setSupplierCount(int supplierCount) {
        lblSupplierCount.setText(String.valueOf(supplierCount));
    }

    public int getSupplierCount() throws SQLException, ClassNotFoundException {String sql = "SELECT COUNT(*) AS supplier_count FROM    Supplier";

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int supplierCount = 0;
        if (resultSet.next()) {
            supplierCount = resultSet.getInt("supplier_count");
        }
        return supplierCount;


    }





}
