package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.CancelCustomerOrderRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import server.requestHandlers.worker.WorkerResponseFactory;

public class CancelRequestsHandler extends BaseRequestsHandler {

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.CANCEL_CUSTOMER_ORDER;
	}
	
	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest) throws SQLException {
//		TODO: This implementation was copied as is from the WebCustomerRequestsHandler. Copy same implementation to here also.
		CancelCustomerOrderRequest cancelRequest = (CancelCustomerOrderRequest)specificRequest;
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectOrderStatus(cancelRequest.orderId, resultList);
		if (resultList.isEmpty()) {
			return createRequestDeniedResponse("Wrong Order ID");
		} else {
			double refund = 0.0; // TODO calculate refund
			// if cancelTime <
			regularDBAPI.cancelOrder(cancelRequest.orderId, refund);
			WorkerBaseResponse response = WorkerResponseFactory.CreateCancelResponse(refund);
			return CreateWorkerResponse(response);
		}
	}
}
