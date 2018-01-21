package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;

import core.worker.ReportItem;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.requests.ReportRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.db.dbAPI.SubscriptionsDBAPI;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;

public class CurrentSubscribersReportRequestsHandler extends BaseRequestsHandler {

	public CurrentSubscribersReportRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}

	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.CURRENT_SUBSCRIBERS_REPORT;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {

		ReportRequest reportRequest = (ReportRequest) specificRequest;
		ArrayList<ReportItem> reportItems = new ArrayList<ReportItem>(); 
		
		reportItems.add(new ReportItem("number of subscriptions that have more than one car: ", 
				Integer.toString(SubscriptionsDBAPI.getInstance().getNumberOfSubscriptionsHasMoreThanOneCar())));
		
		reportItems.add(new ReportItem("number of subscriptions: ", 
				Integer.toString(SubscriptionsDBAPI.getInstance().selectNumberOfSubscriptionsOfAllLots(0/*dummy*/))));		
		
		 WorkerBaseResponse response = WorkerResponseFactory.CreateReportResponse(reportItems, getHandlerRequestsType());
		 return CreateWorkerResponse(response);
	}
}
