package lk.ijse.ceylontool.controller;

import lk.ijse.ceylontool.db.DBConnection;
import lk.ijse.ceylontool.view.tm.SupplierTM;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lk.ijse.ceylontool.repo.SupplierRepo;

import static java.lang.Integer.parseInt;


public class SupplierFormController  {
    @FXML
    private JFXButton btnAddNewCustomer;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<SupplierTM> tblSuppliers;

    @FXML
    private TextField txtMobileNumber;

    @FXML
    private TextField txtSupplierAddress;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtSupplierName;

    public void initialize() {
         tblSuppliers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblSuppliers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblSuppliers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblSuppliers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("tp"));

        initUI();

        tblSuppliers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtSupplierId.setText(newValue.getId());
                txtSupplierName.setText(newValue.getName());
                txtSupplierAddress.setText(newValue.getAddress());
                txtMobileNumber.setText(String.valueOf(newValue.getTp() + ""));

                txtSupplierId .setDisable(false);
                txtSupplierName.setDisable(false);
                txtSupplierAddress.setDisable(false);
                txtMobileNumber.setDisable(false);
            }
        });

        txtMobileNumber.setOnAction(event -> btnSave.fire());
        loadAllSuppliers();
    }

    private void loadAllSuppliers() {

        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Supplier");

            while (rst.next()) {
                tblSuppliers.getItems().add(new SupplierTM(rst.getString("id"), rst.getString("name"), rst.getString("address"),rst.getInt("tp")));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }



    private void initUI() {
        txtSupplierId.clear();
        txtSupplierName.clear();
        txtSupplierAddress.clear();
        txtMobileNumber.clear();
        txtSupplierId.setDisable(true);
        txtSupplierName .setDisable(true);
        txtSupplierAddress.setDisable(true);
        txtMobileNumber.setDisable(true);
        txtSupplierId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/ceylontool/view/main-form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) throws ClassNotFoundException {
         txtSupplierId.setDisable(false);
        txtSupplierName.setDisable(false);
        txtSupplierAddress.setDisable(false);
        txtMobileNumber.setDisable(false);
         txtSupplierId.clear();
         txtSupplierId.setText(generateNewId());
        txtSupplierName.clear();
         txtSupplierAddress.clear();
         txtMobileNumber.clear();
         txtSupplierName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
         tblSuppliers.getSelectionModel().clearSelection();
    }


    public void btnSave_OnAction(ActionEvent actionEvent) {
        String id = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String address = txtSupplierAddress.getText();
        int tp = Integer.parseInt(txtMobileNumber.getText());

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtSupplierName.requestFocus();
            return;
        } else if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();txtSupplierAddress.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Customer*/
            try {
                if (existSupplier(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
            /*    Connection connection = DBConnection.getDbConnection().getConnection();
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer (id,name, address) VALUES (?,?,?)");
                pstm.setString(1, id);
                pstm.setString(2, name);
                pstm.setString(3, address);
                pstm.executeUpdate(); */
                SupplierRepo.saveSupplier(id,name,address, parseInt(String.valueOf(tp)));

                tblSuppliers.getItems().add(new SupplierTM(id, name, address,parseInt(String.valueOf(tp))));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } else {
            /*Update customer*/
            try {
                if (!existSupplier(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
                }
                /*Connection connection = DBConnection.getDbConnection().getConnection();
                PreparedStatement pstm = connection.prepareStatement("UPDATE customer SET name=?, address=? WHERE id=?");
                pstm.setString(1, name);
                pstm.setString(2, address);
                pstm.setString(3, id);
                pstm.executeUpdate(); */
                SupplierRepo.updateSupplier(name,address, Integer.parseInt(String.valueOf(tp)),id);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            SupplierTM selectedCustomer = tblSuppliers.getSelectionModel().getSelectedItem();
            selectedCustomer.setName(name);
            selectedCustomer.setAddress(address);
            selectedCustomer.setTp(Integer.parseInt(String.valueOf(tp)));
            tblSuppliers.refresh();
        }

        btnAddNewCustomer.fire();
    }


    boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        /*Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT id FROM customer WHERE id=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next(); */
        return SupplierRepo.exitSupplier(id);
    }


    public void btnDelete_OnAction(ActionEvent actionEvent) {
        /*Delete Customer*/
        String id = tblSuppliers.getSelectionModel().getSelectedItem().getId();
        try {
            if (!existSupplier(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
           /* Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
            pstm.setString(1, id);
            pstm.executeUpdate(); */
            SupplierRepo.deleteSupplier(id);

             tblSuppliers.getItems().remove( tblSuppliers.getSelectionModel().getSelectedItem());
             tblSuppliers.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String generateNewId() throws ClassNotFoundException {
        try {
            /*Connection connection = DBConnection.getDbConnection().getConnection();
            ResultSet rst = connection.createStatement().executeQuery("SELECT id FROM customer ORDER BY id DESC LIMIT 1;"); */
            ResultSet rst=  SupplierRepo.generateNewId();
            if (rst.next()) {
                String id = rst.getString("id");
                int newCustomerId = parseInt(id.replace("S00-", "")) + 1;
                return String.format("S00-%03d", newCustomerId);
            } else {
                return "S00-001";
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        }


        if (tblSuppliers.getItems().isEmpty()) {
            return "S00-001";
        } else {
            String id = getLastSupplierId();
            int newCustomerId = parseInt(id.replace("S", "")) + 1;
            return String.format("S00-%03d", newCustomerId);
        }

    }
    private String getLastSupplierId() {
        List<SupplierTM> tempSupplierList = new ArrayList< >(tblSuppliers.getItems());
        Collections.sort(tempSupplierList);
        return tempSupplierList.get(tempSupplierList.size() - 1).getId();
    }

}
