package webGui.models;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import core.customer.CustomerRequest;
import webGui.util.CustomerRequestFactory;
import webGui.util.MockWebClientConnectionManager;

public class OrderRoutineMonthlySubscriptionModel {
	private MockWebClientConnectionManager connectionManager;

	public OrderRoutineMonthlySubscriptionModel() {
		connectionManager = MockWebClientConnectionManager.getInstance();
	}

	public void SendOrderRoutineMonthlySubscriptionRequestToServer(int customerID, List<String> liscencePlates, String email, int parkingLotID, Date startingDate,
			LocalTime routineDepartureTime) {
		CustomerRequest request = CustomerRequestFactory.CreateOrderRoutineMonthlySubscriptionRequest(customerID, liscencePlates, email, parkingLotID, startingDate,
				routineDepartureTime);
		connectionManager.sendMessageToServer(request);
	}

}
