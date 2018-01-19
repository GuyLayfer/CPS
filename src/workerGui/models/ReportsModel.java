package workerGui.models;

import java.util.Date;
import java.util.stream.Collectors;

import core.guiUtilities.IServerResponseHandler;
import core.worker.responses.ReportResponse;
import core.worker.responses.WorkerBaseResponse;
import javafx.application.Platform;
import workerGui.controllers.IAddItemsToTable;
import workerGui.util.ReportItemUiElement;
import workerGui.util.ReportsManager;
import workerGui.util.WorkerConnectionManager;
import workerGui.util.WorkerRequestsFactory;

public class ReportsModel implements IServerResponseHandler<WorkerBaseResponse>{
	private WorkerConnectionManager connectionManager;
	private ReportsManager reportsManager;
	private IAddItemsToTable<ReportItemUiElement> controller;

	public ReportsModel(IAddItemsToTable<ReportItemUiElement> controller) {
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
		reportsManager = ReportsManager.getInstance();
		this.controller = controller;
	}

	public void sendReportRequest() {
		connectionManager.sendMessageToServer(WorkerRequestsFactory.CreateReportRequest(reportsManager.getReportContext()));
	}

	public void sendReportRequest(Date startdate, Date endDate) {
		connectionManager.sendMessageToServer(WorkerRequestsFactory.CreatePeriodicReportRequest(
						reportsManager.getReportContext(),
						startdate,
						endDate));
	}

	public Boolean isDateRangeRequired() {
		return reportsManager.isDateRangeRequired();
	}
	
	public String getReportName() {
		return reportsManager.getReportName();
	}

	@Override
	public void handleServerResponse(WorkerBaseResponse response) {
		if (response instanceof ReportResponse) {
			Platform.runLater(() -> {
				ReportResponse reportResponse = (ReportResponse) response;
				controller.AddToTable(
						reportResponse.reportItems
						.stream()
						.map((reportItem) -> new ReportItemUiElement(reportItem))
						.collect(Collectors.toList()));
			});
		}
	}
}
