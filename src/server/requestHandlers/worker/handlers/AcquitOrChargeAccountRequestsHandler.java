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

public class AcquitOrChargeAccountRequestsHandler extends BaseRequestsHandler {

	public AcquitOrChargeAccountRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.ACQUIT_OR_CHARGE_ACCOUNT;
	}

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
