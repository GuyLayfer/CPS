package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import core.worker.Rates;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.db.SqlColumns;
import server.requestHandlers.worker.WorkerResponseFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestRatesForReviewRequestsHandler.
 */
public class RequestRatesForReviewRequestsHandler extends BaseRequestsHandler {

	/**
	 * Instantiates a new request rates for review requests handler.
	 *
	 * @param connectionsToClientProvider the connections to client provider
	 */
	public RequestRatesForReviewRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}
	
	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#getHandlerRequestsType()
	 */
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.REQUEST_RATES_FOR_REVIEW;
	}

	/* (non-Javadoc)
	 * @see server.requestHandlers.worker.handlers.BaseRequestsHandler#HandleSpecificRequest(core.worker.requests.BaseRequest, ocsf.server.ConnectionToClient)
	 */
	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		workersDBAPI.selectAllLotsRates(true, resultList);
		WorkerBaseResponse response = WorkerResponseFactory.CreateRequestRatesForReviewResponse(extractRates(resultList));		
		return CreateWorkerResponse(response);
	}

	/**
	 * Extract rates.
	 *
	 * @param resultList the result list
	 * @return the list
	 */
	private List<Rates> extractRates(ArrayList<Map<String, Object>> resultList) {
		ArrayList<Rates> rates = new ArrayList<Rates>();
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> result = (Map<String, Object>) iterator.next();
			Rates rate = new Rates((int)result.get(SqlColumns.Rates.LOT_ID)
					, (double)result.get(SqlColumns.Rates.OCCASIONAL)
					,(double)result.get(SqlColumns.Rates.PRE_ORDERED)
					, (double)result.get(SqlColumns.Rates.SUBSCRIPTION)
					,(double)result.get(SqlColumns.Rates.SUBSCRIPTION_MULTIPLE_CARS)
					,(double)result.get(SqlColumns.Rates.SUBSCRIPTION_FULL));
			rates.add(rate);
			System.out.println(rates);
		}
		return rates;

	}
}

