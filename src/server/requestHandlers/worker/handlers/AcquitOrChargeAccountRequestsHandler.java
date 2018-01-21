package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import server.requestHandlers.worker.WorkerResponseFactory;

import core.worker.WorkerRequestType;
import core.worker.responses.WorkerBaseResponse;
import core.worker.requests.AcquitOrChargeAccountRequest;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.db.SqlColumns;
import server.requestHandlers.worker.IProvideConnectionsToClient;

// TODO: Auto-generated Javadoc
/**
 * The Class AcquitOrChargeAccountRequestsHandler.
 */
public class AcquitOrChargeAccountRequestsHandler extends BaseRequestsHandler {

	/**
	 * Instantiates a new acquit or charge account requests handler.
	 *
	 * @param connectionsToClientProvider the connections to client provider
	 */
	public AcquitOrChargeAccountRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#getHandlerRequestsType()
	 */
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.ACQUIT_OR_CHARGE_ACCOUNT;
	}

	/**
	 * Update amount with balance.
	 *
	 * @param customerID the customer ID
	 * @param currentRequestAmount the current request amount
	 * @return the double
	 * @throws SQLException the SQL exception
	 */
	private double updateAmountWithBalance(int customerID, double currentRequestAmount) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectCustomerAccountDetails(customerID, resultList);
		double customersBalance = (double)resultList.get(0).get(SqlColumns.Account.BALANCE);
		if (customersBalance == 0)
			return currentRequestAmount;
		if (customersBalance >= currentRequestAmount) {
			customersBalance -= currentRequestAmount;
			//update balance with customersBalance:
			regularDBAPI.updateCustomerBalance(customerID, customersBalance);
			currentRequestAmount = 0.0;
		}
		if (customersBalance < currentRequestAmount) {
			currentRequestAmount -= customersBalance;
			customersBalance = 0.0;
			//update balance with customersBalance:
			regularDBAPI.updateCustomerBalance(customerID, customersBalance);
		}
		return currentRequestAmount;
	}
	
	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#HandleSpecificRequest(core.worker.requests.BaseRequest, ocsf.server.ConnectionToClient)
	 */
	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		AcquitOrChargeAccountRequest request = (AcquitOrChargeAccountRequest)specificRequest;
		double amount = request.amount;
		if (request.amount > 0)
			regularDBAPI.updateCustomerBalance(request.accountId, amount);
		else {			
			amount = updateAmountWithBalance(request.accountId, amount);
		}
		WorkerBaseResponse response = WorkerResponseFactory.CreateAcquitOrChargeAccountResponse(amount);
		return CreateWorkerResponse(response);
	}
}
