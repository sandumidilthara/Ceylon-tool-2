package lk.ijse.ceylontool.controller;

import lk.ijse.ceylontool.db.DBConnection;
import lk.ijse.ceylontool.repo.ItemRepo;
import lk.ijse.ceylontool.view.tm.ItemTM;
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
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;



public class ManageItemsFormController {
    public AnchorPane root;
    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public JFXButton btnDelete;
    public JFXButton btnSave;
    public TableView<ItemTM> tblItems;
    public TextField txtUnitPrice;
    public JFXButton btnAddNewItem;
    @FXML
    private TextField txtSellingPrice;
    @FXML
    private TextField txtWeight;


    public void initialize() {
        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblItems.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItems.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblItems.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblItems.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        tblItems.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("weight"));

        initUI();

        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtCode.setText(newValue.getCode());
                txtDescription.setText(newValue.getDescription());
                txtUnitPrice.setText(newValue.getUnitPrice().setScale(2).toString());
                txtQtyOnHand.setText(newValue.getQtyOnHand() + "");

                txtSellingPrice.setText(newValue.getsellingPrice().setScale(2).toString());
                txtWeight.setText(newValue.getWeight());

                txtCode.setDisable(false);
                txtDescription.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtQtyOnHand.setDisable(false);
                txtSellingPrice.setDisable(false);
                txtWeight.setDisable(false);
            }
        });

        txtWeight.setOnAction(event -> btnSave.fire());
        loadAllItems();
    }

    private void loadAllItems() {
        tblItems.getItems().clear();
        try {
            /*Get all items*/
            Connection connection = DBConnection.getDbConnection().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Item");
            while (rst.next()) {
                tblItems.getItems().add(new ItemTM(rst.getString("code"), rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"),rst.getBigDecimal("sellingPrice"),rst.getString("weight")));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        txtCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtSellingPrice.clear();
        txtWeight.clear();
        txtCode.setDisable(true);
        txtDescription.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtQtyOnHand.setDisable(true);
        txtSellingPrice.setDisable(true);
         txtWeight.setDisable(true);
        txtCode.setEditable(false);
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

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtCode.setDisable(false);
        txtDescription.setDisable(false);
        txtUnitPrice.setDisable(false);
        txtQtyOnHand.setDisable(false);

        txtSellingPrice.setDisable(false);
        txtWeight.setDisable(false);
        txtCode.clear();
        txtCode.setText(generateNewId());
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtSellingPrice.clear();
        txtWeight.clear();
        txtDescription.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblItems.getSelectionModel().clearSelection();
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        /*Delete Item*/
        String code = tblItems.getSelectionModel().getSelectedItem().getCode();
        try {
            if (!existItem(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
            }
         /*   Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");
            pstm.setString(1, code);
            pstm.executeUpdate(); */
            ItemRepo.deleteItem(code);

            tblItems.getItems().remove(tblItems.getSelectionModel().getSelectedItem());
            tblItems.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
       /* Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Item WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next(); */
       return   ItemRepo.exitItem(code);
    }
    public void btnSave_OnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        String description = txtDescription.getText();

        if (!description.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtDescription.requestFocus();
            return;
        } else if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtUnitPrice.requestFocus();
            return;
        } else if (!txtQtyOnHand.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQtyOnHand.requestFocus();
            return;
        }

        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        BigDecimal sellingPrice = new BigDecimal(txtSellingPrice.getText()).setScale(2);;//.setScale(2);
        String weight = txtWeight.getText();


        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                }
                //Save Item
             /*   Connection connection = DBConnection.getDbConnection().getConnection();
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item (code, description, unitPrice, qtyOnHand,sellingPrice,weight) VALUES (?,?,?,?,?,?)");
                pstm.setString(1, code);
                pstm.setString(2, description);
                pstm.setBigDecimal(3, unitPrice);
                pstm.setInt(4, qtyOnHand);

                pstm.setBigDecimal(5, sellingPrice);
                pstm.setString(6, weight);
                pstm.executeUpdate(); */
                ItemRepo.saveItem(code,description,unitPrice,qtyOnHand,sellingPrice,weight);
                tblItems.getItems().add(new ItemTM(code, description, unitPrice, qtyOnHand,sellingPrice,weight));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {

                if (!existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
                }
                /*Update Item*/
              /*  Connection connection = DBConnection.getDbConnection().getConnection();
                PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? , sellingPrice =?,weight =? WHERE code=?");
                pstm.setString(1, description);
                pstm.setBigDecimal(2, unitPrice);
                pstm.setInt(3, qtyOnHand);
                pstm.setBigDecimal(4, sellingPrice);
                pstm.setString(5, weight);
                pstm.setString(6, code);
                pstm.executeUpdate(); */
                ItemRepo.updateItem(code,description,unitPrice,qtyOnHand,sellingPrice,weight);
                ItemTM selectedItem = tblItems.getSelectionModel().getSelectedItem();
                selectedItem.setDescription(description);
                selectedItem.setQtyOnHand(qtyOnHand);
                selectedItem.setUnitPrice(unitPrice);
                selectedItem.setSellingPrice(sellingPrice);
                selectedItem.setWeight(weight);
                tblItems.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        btnAddNewItem.fire();
    }


    private String generateNewId() {
        try {
           /* Connection connection = DBConnection.getDbConnection().getConnection();
            ResultSet rst = connection.createStatement().executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;"); */
            ResultSet rst   = ItemRepo.generateNewId();
            if (rst.next()) {
                String id = rst.getString("code");
                int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
                return String.format("I00-%03d", newItemId);
            } else {
                return "I00-001";
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I00-001";
    }
}
