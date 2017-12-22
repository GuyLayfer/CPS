package webGui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MockWebClientController {

	@FXML 
	private TabPane tabPane;
	
    @FXML
    private Tab OpenComplaint;
    
    @FXML 
    private OpenComplaintController openComplaintController;

    @FXML
    private Tab cancelOrder;
    
    @FXML 
    private CancelOrderController cancelOrderController;

    @FXML
    private Tab renewSubscription;
    
//    @FXML 
//    private FooTabController fooTabPageController;

    @FXML
    private Tab orderRoutineMonthlySubscription;
    
    @FXML 
    private OrderRoutineMonthlySubscriptionController orderRoutineMonthlySubscriptionController;

    @FXML
    private Tab orderFullMonthlySubscription;
    
//    @FXML 
//    private FooTabController fooTabPageController;

    @FXML
    private Tab TrackOrderStatus;
    
    @FXML 
    private TrackOrderStatusController trackOrderStatusController;

    @FXML
    private Tab orderOneTimeParking;
    
    @FXML 
    private OrderOneTimeParkingController orderOneTimeParkingController;

}