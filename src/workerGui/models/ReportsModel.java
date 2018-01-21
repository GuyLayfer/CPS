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

// TODO: Auto-generated Javadoc
/**
 * The Class ReportsModel.
 */
public class ReportsModel implements IServerResponseHandler<WorkerBaseResponse>{
	
	/** The connection manager. */
	private WorkerConnectionManager connectionManager;
	
	/** The reports manager. */
	private ReportsManager reportsManager;
	
	/** The controller. */
	private IAddItemsToTable<ReportItemUiElement> controller;

	/**
	 * Instantiates a new reports model.
	 *
	 * @param controller the controller
	 */
	public ReportsModel(IAddItemsToTable<ReportItemUiElement> controller) {
		connectionManager = WorkerConnectionManager.getInstance();
		connectionManager.addServerMessageListener(this);
		reportsManager = ReportsManager.getInstance();
		this.controller = controller;
	}

	/**
	 * Send report request.
	 *
	 * @param startdate the startdate
	 * @param endDate the end date
	 * @param parkingLotId the parking lot id
	 */
	public void sendReportRequest(Date startdate, Date endDate, int parkingLotId) {
		connectionManager.sendMessageToServer(WorkerRequestsFactory.CreateReportRequest(
						reportsManager.getReportContext(),
						startdate,
						endDate,
						parkingLotId));
	}

	/**
	 * Checks if is date range required.
	 *
	 * @return the boolean
	 */
	public Boolean isDateRangeRequired() {
		return reportsManager.isDateRangeRequired();
	}
	
	/**
	 * Checks if is select parking lot id required.
	 *
	 * @return the boolean
	 */
	public Boolean isSelectParkingLotIdRequired() {
		return reportsManager.isSelectParkingLotNeeded();
	}
	
	/**
	 * Gets the report name.
	 *
	 * @return the report name
	 */
	public String getReportName() {
		return reportsManager.getReportName();
	}

	/* (non-Javadoc)
	 * @see core.guiUtilities.IServerResponseHandler#handleServerResponse(java.lang.Object)
	 */
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
