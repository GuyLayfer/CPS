package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import core.worker.ReportItem;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerBaseResponse;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.db.DBConstants.DbSqlColumns;
import server.db.dbAPI.RegularDBAPI;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;

public class ComplaintsReportRequestsHandler extends BaseRequestsHandler {

	public ComplaintsReportRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}
	
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.COMPLAINTS_REPORT;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {

		
		ArrayList<ReportItem> reportItems = new ArrayList<ReportItem>(); 
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		RegularDBAPI.getInstance().selectAllOpenedComplaints(resultList);
		RegularDBAPI.getInstance().selectAllClosedComplaints(resultList);
		
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			String details = "account id: " + row.get(DbSqlColumns.ACCOUNT_ID.getName()) + 
							"\ncomplaint description: " + row.get(DbSqlColumns.COMPLAINT_DESCRIPTION.getName()) +
							"\nalready filled?: " + row.get(DbSqlColumns.COMPLAINT_FILLED.getName()) +
							"\ncustomer service response: " + row.get(DbSqlColumns.COMPLAINT_CUSTOMER_SERVICE_RESPOND.getName()) +
							"\ndate complaint generated: " + row.get(DbSqlColumns.COMPLAINT_DATETIME.getName());

			reportItems.add(new ReportItem(Integer.toString((int)row.get(DbSqlColumns.COMPLAINT_ID.getName())), details));
		}
		
		WorkerBaseResponse response = WorkerResponseFactory.CreateReportResponse(reportItems, getHandlerRequestsType());
		return CreateWorkerResponse(response);
	}
}
