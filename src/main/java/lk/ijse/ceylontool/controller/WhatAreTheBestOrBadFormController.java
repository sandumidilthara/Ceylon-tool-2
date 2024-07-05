package lk.ijse.ceylontool.controller;

import lk.ijse.ceylontool.db.DBConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WhatAreTheBestOrBadFormController {





    @FXML
    public Label lblOrderCount;


    @FXML
    private AnchorPane root;


    public Label lblCustomerCount;
    private String bestItem;

    private String badItem;

    public void initialize() throws ClassNotFoundException {
        try {
            bestItem = getBestItem();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setBestItem(bestItem);






        try {
            badItem = getBadItem();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setBadItem(badItem);





    }

    private void setBestItem(String bestItem) {
        lblCustomerCount.setText(String.valueOf(bestItem));
    }

    private String getBestItem() throws SQLException, ClassNotFoundException {

   String sql = " Select  i.description as best_item from  Item i join  order_detail od on i.code = od.item_code group by  i.code, i.description order by sum(od.qty) desc limit 1;";

        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        String bestItem = "";
        if(resultSet.next()) {
            bestItem = resultSet.getString("best_item");
        }
        return bestItem;




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







    private void setBadItem(String badItem) {
        lblOrderCount.setText(String.valueOf(badItem));
    }

    private String getBadItem() throws SQLException, ClassNotFoundException {
        String sql = " Select  i.description as bad_item from  Item i join  order_detail od on i.code = od.item_code group by  i.code, i.description order by sum(od.qty) asc limit 1;";


        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        String badItem;
        try (ResultSet resultSet = pstm.executeQuery()) {

            badItem = "";
            if (resultSet.next()) {
                badItem = resultSet.getString("bad_item");
            }
        }
        return badItem;
    }







}
