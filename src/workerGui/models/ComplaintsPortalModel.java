package workerGui.models;

import java.util.stream.Collectors;

import core.guiUtilities.IServerResponseHandler;
import core.worker.Complaint;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerBaseResponse;
import javafx.application.Platform;
import core.worker.responses.ComplaintsForReviewResponse;
import workerGui.controllers.IAddItemsToTable;
import workerGui.util.ComplaintUiElement;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerRequestsFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class ComplaintsPortalModel.
 */
public class ComplaintsPortalModel implements IServerResponseHandler<WorkerBaseResponse> {
	
	/** The connection manager. */
	private WorkerConnectionManager connectionManager;
	
	/** The controller. */
	private IAddItemsToTable<ComplaintUiElement> controller;

	/**
	 * Instantiates a new complaints portal model.
	 *
	 * @param controller the controller
	 */
	public ComplaintsPortalModel(IAddItemsToTable<ComplaintUiElement> controller) {
		 connectionManager = WorkerConnectionManager.getInstance();
		 connectionManager.addServerMessageListener(this);
		 this.controller = controller;
	}

	/**
	 * Send request for pending complaints requests.
	 */
	public void sendRequestForPendingComplaintsRequests() {
		connectionManager.sendMessageToServer(WorkerRequestsFactory.CreateComplaintsForReviewRequest());
	}

	/**
	 * Approve complaint.
	 *
	 * @param complaint the complaint
	 */
	public void ApproveComplaint(ComplaintUiElement complaint) {
		sendDecision(complaint.getValue(), true, 0);
	}

	/**
	 * Acquit complaint.
	 *
	 * @param complaint the complaint
	 * @param amountToAcquit the amount to acquit
	 */
	public void AcquitComplaint(ComplaintUiElement complaint, int amountToAcquit) {
		sendDecision(complaint.getValue(), true, amountToAcquit);
	}

	/**
	 * Decline complaint.
	 *
	 * @param complaint the complaint
	 */
	public void DeclineComplaint(ComplaintUiElement complaint) {
		sendDecision(complaint.getValue(), false, 0);
	}
	
	/**
	 * Send decision.
	 *
	 * @param complaintToDecide the complaint to decide
	 * @param isComplaintApproved the is complaint approved
	 * @param amountToAcquit the amount to acquit
	 */
	private void sendDecision(Complaint complaintToDecide,Boolean isComplaintApproved, int amountToAcquit) {
		BaseRequest request = WorkerRequestsFactory.CreateDecideOnComplaintsRequest(complaintToDecide, isComplaintApproved, amountToAcquit);
		connectionManager.sendMessageToServer(request);
	}

	/* (non-Javadoc)
	 * @see core.guiUtilities.IServerResponseHandler#handleServerResponse(java.lang.Object)
	 */
	@Override
	public void handleServerResponse(WorkerBaseResponse response) {
		if (response.requestType == WorkerRequestType.REQUEST_COMPLAINTS_FOR_REVIEW) {
			ComplaintsForReviewResponse specificResponse = (ComplaintsForReviewResponse) response;
			Platform.runLater(() -> {
				controller.AddToTable(specificResponse.complaintsForReview
						.stream()
						.map((complaint) -> new ComplaintUiElement(complaint))
						.collect(Collectors.toList()));
			});
		}
	}
}
