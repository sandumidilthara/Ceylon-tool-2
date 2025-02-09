package lk.ijse.ceylontool.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainFormController {
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imgCustomer;
    @FXML
    private ImageView imgItem;
    @FXML
    private ImageView imgOrder;





    @FXML
    private ImageView imgSupplier;

    @FXML
    private ImageView imgSupplierDetails;

    @FXML
    private ImageView imgdailyreview;

    @FXML
    private ImageView imgdetails;

    @FXML
    private Label lblMenu;
    @FXML
    private Label lblDescription;


    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    @FXML
    private void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()) {
                case "imgCustomer":
                    lblMenu.setText("Manage Customers");
                    lblDescription.setText("Click to add, edit, delete, search or view customers");
                    break;
                case "imgItem":
                    lblMenu.setText("Manage Items");
                    lblDescription.setText("Click to add, edit, delete, search or view items");
                    break;
                case "imgOrder":
                    lblMenu.setText("Place Orders");
                    lblDescription.setText("Click here if you want to place a new order");
                    break;
                case "imgSupplier":
                    lblMenu.setText("Manage Suppliers");
                    lblDescription.setText("Click to add, edit, delete, search or view suppliers");
                    break;
                case "imgSupplierDetails":
                    lblMenu.setText("Manage Item Supply");
                    lblDescription.setText("Click to add, edit, delete, search or view item supply");
                    break;
                case "imgdailyreview":
                    lblMenu.setText("Daily Review");
                    lblDescription.setText("Click here if you want to know daily review");
                    break;
                case "imgdetails":
                    lblMenu.setText("what are the best and bad");
                    lblDescription.setText("Click here id you want to konw details");
                    break;



            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }


    @FXML
    private void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            Parent root = null;

            switch (icon.getId()) {
                case "imgCustomer":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/ceylontool/view/manage-customers-form.fxml"));
                    break;
                case "imgItem":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/ceylontool/view/manage-items-form.fxml"));
                    break;
                case "imgOrder":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/ceylontool/view/place-order-form.fxml"));
                    break;
                case "imgSupplier":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/ceylontool/view/manage-supplier-form.fxml"));

                    break;
                case "imgSupplierDetails":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/ceylontool/view/item-supply-form.fxml"));

                    break;


                case "imgdailyreview":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/ceylontool/view/daily-review-form.fxml"));

                    break;
                case "imgdetails":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/ceylontool/view/what-are-the-best-or-bad-form.fxml"));

                    break;

            }

            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();

            }
        }
    }
}
