package server;

import core.ServerPorts;
import server.parkingLot.ParkingLotsManager;
import server.rates.PriceCalculator;
import server.rates.RatesManager;
import server.requestHandlers.KioskRequestsHandler;
import server.requestHandlers.WebCustomerRequestsHandler;
import server.requestHandlers.worker.WorkerRequestsHandler;

import java.sql.SQLException;

// CPS Server main class
public class CPSMain {
	// TODO: add required properties
	
	
	public static void main(String[] args) {
		try {
			
			//AutoStatsGenerator.generateStatsLastWeekPeriodly();
			ParkingLotsManager.initialize();
//			RatesManager.initialize();
			PriceCalculator.initialize();
			
			WebCustomerRequestsHandler webCustomerRequestsHandler = 
					new WebCustomerRequestsHandler(ServerPorts.WEB_CUSTOMER_PORT);
			KioskRequestsHandler kioskRequestsHandler = 
					new KioskRequestsHandler(ServerPorts.KIOSK_PORT);
			WorkerRequestsHandler workerRequestsHandler = 
					new WorkerRequestsHandler(ServerPorts.WORKER_PORT);
			
			webCustomerRequestsHandler.listen();
			kioskRequestsHandler.listen();
			workerRequestsHandler.listen();
		} catch (SQLException ex) {
			System.out.println("ERROR - Could not retrieve data structures info from DB!");
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
