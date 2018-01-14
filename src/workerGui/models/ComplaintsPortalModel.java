package workerGui.models;

import java.util.stream.Collectors;

import core.guiUtilities.IServerResponseHandler;
import core.worker.Complaint;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.BaseResponse;
import core.worker.responses.ComplaintsForReviewResponse;
import workerGui.controllers.IAddItemsToTable;
import workerGui.util.ComplaintUiElement;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerRequestsFactory;

public class ComplaintsPortalModel implements IServerResponseHandler{
	private WorkerConnectionManager connectionManager;
	private IAddItemsToTable<ComplaintUiElement> controller;

	public ComplaintsPortalModel(IAddItemsToTable<ComplaintUiElement> controller) {
		 connectionManager = WorkerConnectionManager.getInstance();
		 connectionManager.addServerMessageListener(this);
		 this.controller = controller;
	}

	public void sendRequestForPendingRatesRequests() {

	}

	public void ApproveComplaint(ComplaintUiElement complaint) {
		sendDecision(complaint.getValue(), true, 0);
	}

	public void AcquitComplaint(ComplaintUiElement complaint, int amountToAcquit) {
		sendDecision(complaint.getValue(), true, amountToAcquit);
	}

	public void DeclineComplaint(ComplaintUiElement complaint) {
		sendDecision(complaint.getValue(), false, 0);
	}
	
	private void sendDecision(Complaint complaintToDecide,Boolean isComplaintApproved, int amountToAcquit) {
		BaseRequest request = WorkerRequestsFactory.CreateDecideOnComplaintsRequest(complaintToDecide, isComplaintApproved, amountToAcquit);
		connectionManager.sendMessageToServer(request);
	}

	@Override
	public void handleServerResponse(BaseResponse response) {
		if (response.requestType == WorkerRequestType.REQUEST_COMPLAINTS_FOR_REVIEW) {
			ComplaintsForReviewResponse specificResponse = (ComplaintsForReviewResponse) response;
			controller.AddToTable(
					specificResponse.complaintsForReview
					.stream()
					.map((complaint) -> new ComplaintUiElement(complaint))
					.collect(Collectors.toList()));
		}
	}
}
