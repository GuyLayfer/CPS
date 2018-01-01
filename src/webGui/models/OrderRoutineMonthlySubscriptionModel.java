package webGui.models;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import core.Customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;
import webGui.util.ServerMessageHandler;

public class OrderRoutineMonthlySubscriptionModel {
	private MockWebClientConnectionManager connectionManager;
		
	public OrderRoutineMonthlySubscriptionModel(ServerMessageHandler controller) {
		connectionManager = MockWebClientConnectionManager.getInstance();
		connectionManager.addServerMessageListener(controller);
	}

	// TODO: change liscencePlate to String when Raz changes it in the DB
	public void SendOrderRoutineMonthlySubscriptionRequestToServer(int customerID,List<String> liscencePlates, String email, int parkingLotID, Date startingDate, 
			LocalTime routineDepartureTime){
		CustomerRequest request = CustomerRequestFactory.CreateOrderRoutineMonthlySubscriptionRequest(customerID, liscencePlates, email, parkingLotID, startingDate, 
				 routineDepartureTime);
		connectionManager.sendMessageToServer(request);
	}

}
