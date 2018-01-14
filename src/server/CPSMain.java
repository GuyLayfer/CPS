package server;

import core.ServerPorts;
import server.workerServer.WorkerRequestsHandler;

import java.sql.SQLException;

// CPS Server main class
public class CPSMain {
	// TODO: add required properties
	
	
	public static void main(String[] args) {
		try {
			IDsGenerator idsGenerator = IDsGenerator.getInstance();
			//TODO: add parking lots info
			
			WebCustomerRequestsHandler webCustomerRequestsHandler = 
					new WebCustomerRequestsHandler(ServerPorts.WEB_CUSTOMER_PORT, idsGenerator);
			KioskRequestsHandler kioskRequestsHandler = 
					new KioskRequestsHandler(ServerPorts.KIOSK_PORT, idsGenerator);
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
