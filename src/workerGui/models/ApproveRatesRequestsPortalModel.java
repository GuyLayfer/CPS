package workerGui.models;

import java.util.stream.Collectors;

import core.guiUtilities.IServerResponseHandler;
import core.worker.Rates;
import core.worker.WorkerRequestType;
import core.worker.requests.BaseRequest;
import core.worker.responses.WorkerBaseResponse;
import javafx.application.Platform;
import core.worker.responses.RatesForReviewResponse;
import workerGui.controllers.IAddItemsToTable;
import workerGui.util.RatesUiElement;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerRequestsFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class ApproveRatesRequestsPortalModel.
 */
public class ApproveRatesRequestsPortalModel implements IServerResponseHandler<WorkerBaseResponse> {
	
	/** The connection manager. */
	private WorkerConnectionManager connectionManager;
	
	/** The controller. */
	private IAddItemsToTable<RatesUiElement> controller;

	/**
	 * Instantiates a new approve rates requests portal model.
	 *
	 * @param controller the controller
	 */
	public ApproveRatesRequestsPortalModel(IAddItemsToTable<RatesUiElement> controller) {
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
		this.controller = controller;
	}

	/**
	 * Send request for pending rates requests.
	 */
	public void sendRequestForPendingRatesRequests() {
		BaseRequest request = WorkerRequestsFactory.CreateRatesForReviewRequest();
		connectionManager.sendMessageToServer(request);
	}

	/**
	 * Send decline rates.
	 *
	 * @param rates the rates
	 */
	public void SendDeclineRates(RatesUiElement rates) {
		sendDecision(rates.getValue(), false);
	}

	/**
	 * Send approve rates.
	 *
	 * @param rates the rates
	 */
	public void SendApproveRates(RatesUiElement rates) {
		sendDecision(rates.getValue(), true);
	}

	/**
	 * Send decision.
	 *
	 * @param rates the rates
	 * @param isApproved the is approved
	 */
	private void sendDecision(Rates rates, Boolean isApproved) {
		BaseRequest request = WorkerRequestsFactory.CreateDecideOnRatesRequest(rates, isApproved);
		connectionManager.sendMessageToServer(request);
	}

	/* (non-Javadoc)
	 * @see core.guiUtilities.IServerResponseHandler#handleServerResponse(java.lang.Object)
	 */
	@Override
	public void handleServerResponse(WorkerBaseResponse response) {
		if (response.requestType == WorkerRequestType.REQUEST_RATES_FOR_REVIEW) {
			Platform.runLater(() -> {
			RatesForReviewResponse specificResponse = (RatesForReviewResponse) response;
			controller.AddToTable(
					specificResponse.ratesForReview
					.stream()
					.map((rates) -> new RatesUiElement(rates))
					.collect(Collectors.toList()));
			});
		}
	}
}
