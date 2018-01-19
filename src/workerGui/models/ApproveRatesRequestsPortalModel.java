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

public class ApproveRatesRequestsPortalModel implements IServerResponseHandler<WorkerBaseResponse> {
	private WorkerConnectionManager connectionManager;
	private IAddItemsToTable<RatesUiElement> controller;

	public ApproveRatesRequestsPortalModel(IAddItemsToTable<RatesUiElement> controller) {
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
		this.controller = controller;
	}

	public void sendRequestForPendingRatesRequests() {
		BaseRequest request = WorkerRequestsFactory.CreateRatesForReviewRequest();
		connectionManager.sendMessageToServer(request);
	}

	public void SendDeclineRates(RatesUiElement rates) {
		sendDecision(rates.getValue(), false);
	}

	public void SendApproveRates(RatesUiElement rates) {
		sendDecision(rates.getValue(), true);
	}

	private void sendDecision(Rates rates, Boolean isApproved) {
		BaseRequest request = WorkerRequestsFactory.CreateDecideOnRatesRequest(rates, isApproved);
		connectionManager.sendMessageToServer(request);
	}

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
