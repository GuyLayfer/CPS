package webGui.controllers;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MockWebClientController{

    @FXML // fx:id="renewsubscriptionButton"
    private MenuItem renewSubscriptionButton; // Value injected by FXMLLoader

    @FXML // fx:id="orderOneTimeParkingButton"
    private Button orderOneTimeParkingButton; // Value injected by FXMLLoader

    @FXML // fx:id="orderRoutineMonthlySubscriptionButton"
    private MenuItem orderRoutineMonthlySubscriptionButton; // Value injected by FXMLLoader

    @FXML // fx:id="orderFullMonthlysubscriptionButton"
    private MenuItem orderFullMonthlySubscriptionButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelOrderButton"
    private Button cancelOrderButton; // Value injected by FXMLLoader

    @FXML // fx:id="TrackOrderStatusButton"
    private Button TrackOrderStatusButton; // Value injected by FXMLLoader

    @FXML // fx:id="OpenComplaintButton"
    private Button OpenComplaintButton; // Value injected by FXMLLoader

    @FXML // fx:id="SubscriptionsMenuButton"
    private MenuButton SubscriptionsMenuButton; // Value injected by FXMLLoader

    @FXML
    void SubscriptionsMenu(ActionEvent event) {

    }

    @FXML
    void orderRoutineMonthlySubscription(ActionEvent event) {
    	try {
    		URL uri = getClass().getResource("../views/MockOrderRoutineMonthlySubscriptionView.fxml");
    	    AnchorPane  pane = FXMLLoader.load(uri);
    		Stage stage = new Stage();
            stage.setTitle("Order Routine Monthly Subscription");
            stage.setScene(new Scene(pane));
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    	
    }

    @FXML
    void orderFullMonthlySubscription(ActionEvent event) {

    }

    @FXML
    void renewSubscription(ActionEvent event) {

    }

    @FXML
    void orderOneTimeParking(ActionEvent event) {
    	try {
    		URL uri = getClass().getResource("../views/MockOrderOneTimeParkingView.fxml");
    	    AnchorPane  pane = FXMLLoader.load(uri);
    		Stage stage = new Stage();
            stage.setTitle("Order One Time Parking");
            stage.setScene(new Scene(pane));
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void TrackOrderStatus(ActionEvent event) {
    	try {
    		URL uri = getClass().getResource("../views/MockTrackOrderStatusView.fxml");
    	    AnchorPane  pane = FXMLLoader.load(uri);
    		Stage stage = new Stage();
            stage.setTitle("Track Order Status");
            stage.setScene(new Scene(pane));
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void cancelOrder(ActionEvent event) {
    	try {
    		URL uri = getClass().getResource("../views/MockCancelOrderView.fxml");
    	    AnchorPane  pane = FXMLLoader.load(uri);
    		Stage stage = new Stage();
            stage.setTitle("Cancel Order");
            stage.setScene(new Scene(pane));
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    	
    @FXML
    void OpenComplaint(ActionEvent event) {
    	try {
    		URL uri = getClass().getResource("../views/MockOpenComplaintView.fxml");
    	    AnchorPane  pane = FXMLLoader.load(uri);
    		Stage stage = new Stage();
            stage.setTitle("Open Complaint");
            stage.setScene(new Scene(pane));
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}


