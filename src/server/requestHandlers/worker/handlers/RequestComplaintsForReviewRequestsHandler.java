package server.requestHandlers.worker.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import core.worker.Complaint;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerResponse;
import ocsf.server.ConnectionToClient;
import server.requestHandlers.worker.IProvideConnectionsToClient;
import server.requestHandlers.worker.WorkerResponseFactory;
import core.worker.responses.WorkerBaseResponse;
import server.db.SqlColumns;

public class RequestComplaintsForReviewRequestsHandler extends BaseRequestsHandler {

	public RequestComplaintsForReviewRequestsHandler(IProvideConnectionsToClient connectionsToClientProvider) {
		super(connectionsToClientProvider);
	}
	
	@Override
	protected WorkerRequestType getHandlerRequestsType() {
		return WorkerRequestType.REQUEST_COMPLAINTS_FOR_REVIEW;
	}

	@Override
	protected WorkerResponse HandleSpecificRequest(BaseRequest specificRequest, ConnectionToClient client) throws SQLException {
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		regularDBAPI.selectAllOpenedComplaints(resultList);
		WorkerBaseResponse response = WorkerResponseFactory
				.CreateRequestComplaintsForReviewResponse(extractComplaints(resultList));
		return CreateWorkerResponse(response);
	}

	private List<Complaint> extractComplaints(ArrayList<Map<String, Object>> resultList) {
		ArrayList<Complaint> complaints = new ArrayList<Complaint>();
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> result = (Map<String, Object>) iterator.next();
			Complaint complaint = new Complaint((String) result.get(SqlColumns.Complaints.COMPLAINT_DESCRIPTION),
					(int) result.get(SqlColumns.Complaints.ACCOUNT_ID),
					(Date) result.get(SqlColumns.Complaints.COMPLAINT_DATETIME),
					(int) result.get(SqlColumns.Complaints.COMPLAINT_ID));
			complaints.add(complaint);
			System.out.println(complaints);
		}

		return complaints;
	}
}
