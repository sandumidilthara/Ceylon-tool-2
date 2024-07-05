package lk.ijse.ceylontool.controller;

import lk.ijse.ceylontool.db.DBConnection;
import lk.ijse.ceylontool.repo.SupplyRepo;
import lk.ijse.ceylontool.view.tm.SupplyTM;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.Integer.parseInt;

public class ItemSupplyFormController {








    @FXML
    public JFXButton btnAddNewItem;

    @FXML
    public JFXButton btnDelete;

    @FXML
    public JFXButton btnSave;

    @FXML
    public AnchorPane root;

    @FXML
    public TableView<SupplyTM> tblSupply;

    @FXML
    public TextField txtBuyingPrice;

    @FXML
    public TextField txtItem;

    @FXML
    public TextField txtQty;

    @FXML
    public TextField txtSupplier;

    @FXML
    public TextField txtSupplierName;

    @FXML
    public TextField txtSupply;

    @FXML
    public TextField txtSupply1;











    public void initialize() {
        tblSupply.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("t_id"));
        tblSupply.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblSupply.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("s_id"));
        tblSupply.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("sup_name"));
        tblSupply.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("item_name"));
        tblSupply.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblSupply.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("buying_price"));



        initUI();

        tblSupply.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                 txtSupply.setText( newValue.getTId());
                 txtSupply1.setText(newValue.getCode());
                 txtSupplier.setText(newValue.getSId());
                 txtSupplierName.setText(newValue.getSupName());
                 txtItem.setText(newValue.getItemName());


                txtQty.setText(newValue.getQty() + "");

                txtBuyingPrice.setText(newValue.getBuyingPricePrice().setScale(2).toString());


                txtSupply.setDisable(false);
                txtSupply1.setDisable(false);
                txtSupplier.setDisable(false);
                txtSupplierName.setDisable(false);
                txtItem.setDisable(false);
                txtQty.setDisable(false);
                txtBuyingPrice.setDisable(false);
            }
        });

        txtBuyingPrice.setOnAction(event -> btnSave.fire());
        loadAllSupply();
    }

    private void loadAllSupply() {
        tblSupply.getItems().clear();
        try {
            /*Get all items*/
            Connection connection = DBConnection.getDbConnection().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Item_Supply");
            while (rst.next()) {
                tblSupply.getItems().add(new SupplyTM(rst.getString("t_id"), rst.getString("code"),rst.getString("s_id"),rst.getString("sup_name"), rst.getString("item_name"), rst.getInt("qty"),rst.getBigDecimal("buyingPrice") ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        txtSupply.clear();
        txtSupply1.clear();
        txtSupplier.clear();
        txtSupplierName.clear();
        txtItem.clear();
        txtQty.clear();
        txtBuyingPrice.clear();



        txtSupply.setDisable(true);
        txtSupply1.setDisable(true);
        txtSupplier.setDisable(true);
        txtSupplierName.setDisable(true);
        txtItem.setDisable(true);
        txtQty.setDisable(true);
        txtBuyingPrice.setDisable(true);




        txtSupply.setEditable(false);
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
         txtSupply.setDisable(false);
        txtSupply1.setDisable(false);
         txtSupplier.setDisable(false);
        txtSupplierName.setDisable(false);

        txtItem.setDisable(false);
        txtQty.setDisable(false);
        txtBuyingPrice.setDisable(false);




        txtSupply.clear();

        txtSupply.setText(generateNewId());
        txtSupply1.clear();
        txtSupplier .clear();
        txtSupplierName.clear();
        txtItem.clear();
        txtQty.clear();
        txtBuyingPrice.clear();



        txtSupply1.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblSupply.getSelectionModel().clearSelection();
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        /*Delete Item*/
        String code = tblSupply.getSelectionModel().getSelectedItem().getCode();
        try {
            if (!existSupply(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
            }
         /*   Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");
            pstm.setString(1, code);
            pstm.executeUpdate(); */
            SupplyRepo.deleteSupply(code);

            tblSupply.getItems().remove(tblSupply.getSelectionModel().getSelectedItem());
            tblSupply.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private boolean existSupply(String t_id) throws SQLException, ClassNotFoundException {
       /* Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Item WHERE code=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next(); */
        return   SupplyRepo.exitSupply(t_id);
    }
    public void btnSave_OnAction(ActionEvent actionEvent) {
        String t_id = txtSupply.getText();
        String code = txtSupply1.getText();

     /*   if (!description.matches("[A-Za-z0-9 ]+")) {
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
        }  */
        String s_id = txtSupplier.getText();
        String sup_name = txtSupplierName.getText();
        String item_name = txtItem.getText();



        int qty = Integer.parseInt(txtQty.getText());

        BigDecimal buyingPrice = new BigDecimal(txtBuyingPrice.getText()).setScale(2);;//.setScale(2);


        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existSupply(t_id)) {
                    new Alert(Alert.AlertType.ERROR,  t_id+ " already exists").show();
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
                SupplyRepo.saveSupply( t_id,code,s_id,sup_name,item_name,qty,buyingPrice);

                tblSupply.getItems().add(new SupplyTM(t_id, code, s_id,sup_name, item_name,parseInt(String.valueOf(qty)),buyingPrice));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {

                if (!existSupply(t_id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + t_id).show();
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
                SupplyRepo.updateSupply( t_id,code,s_id,sup_name,item_name,qty,buyingPrice);
                SupplyTM selectedItem = tblSupply.getSelectionModel().getSelectedItem();

                selectedItem.setCode(code);
                selectedItem.setSId(s_id);
                selectedItem.setSupName(sup_name);
                selectedItem.setItemName(item_name);
                selectedItem.setQty(qty);
                selectedItem.setBuyingPrice(buyingPrice);

                tblSupply.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        btnAddNewItem.fire();
    }


    private String generateNewId() throws ClassNotFoundException {
        try {
           /* Connection connection = DBConnection.getDbConnection().getConnection();
            ResultSet rst = connection.createStatement().executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1;"); */
            ResultSet rst   = SupplyRepo.generateNewId();
            if (rst.next()) {
                String id = rst.getString("t_id");
                int newItemId = parseInt(id.replace("T00-", "")) + 1;
                return String.format("T00-%03d", newItemId);
            } else {
                return "T00-001";
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return "T00-001";
    }
}



























