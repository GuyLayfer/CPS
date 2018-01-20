package workerGui.util;

import java.util.HashMap;
import java.util.Map;

import core.worker.WorkerRequestType;

public class ReportsManager {
	private static ReportsManager instance;
	private WorkerRequestType requestedReportType;
	private String reportName;
	private Map<WorkerRequestType, Boolean> dateRangeIsNeededQueryMap;
	private Map<WorkerRequestType, Boolean> selectParkingLotidNeededQueryMap;
	
	private ReportsManager() {
		dateRangeIsNeededQueryMap = CreateDateRangeNeededQueryMap();
		selectParkingLotidNeededQueryMap = CreateSelectParkingLotNeededQueryMap();
	}
	
	public static ReportsManager getInstance() {
		if (instance == null) {
			instance = new ReportsManager();
			return instance;
		}

		return instance;
	}
	
	public void setReportContext(WorkerRequestType reportType) {
		requestedReportType = reportType;
	}

	public WorkerRequestType getReportContext() {
		return requestedReportType;
	}
	
	public Boolean isDateRangeRequired() {
		return dateRangeIsNeededQueryMap.get(requestedReportType);
	}
	
	public Boolean isSelectParkingLotNeeded() {
		return selectParkingLotidNeededQueryMap.get(requestedReportType);
	}
	
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	};
	
	private Map<WorkerRequestType, Boolean> CreateDateRangeNeededQueryMap() {
		Map<WorkerRequestType, Boolean> dateRangeNeededQueryMap = new HashMap<WorkerRequestType, Boolean>();
		dateRangeNeededQueryMap.put(WorkerRequestType.OUT_OF_ORDER_REPORT, true);
		dateRangeNeededQueryMap.put(WorkerRequestType.ORDERS_REPORT, true);
		dateRangeNeededQueryMap.put(WorkerRequestType.COMPLAINTS_REPORT, true);
		dateRangeNeededQueryMap.put(WorkerRequestType.LOT_SPACES_REPORT, false);
		dateRangeNeededQueryMap.put(WorkerRequestType.CURRENT_SUBSCRIBERS_REPORT, false);
		dateRangeNeededQueryMap.put(WorkerRequestType.OPERATIONS_REPORT, true);
		dateRangeNeededQueryMap.put(WorkerRequestType.PERFORMENCE_REPORT, true);
		return dateRangeNeededQueryMap;
	}
	
	private Map<WorkerRequestType, Boolean> CreateSelectParkingLotNeededQueryMap() {
		Map<WorkerRequestType, Boolean> selectParkingLotidNeededQueryMap = new HashMap<WorkerRequestType, Boolean>();
		selectParkingLotidNeededQueryMap.put(WorkerRequestType.OUT_OF_ORDER_REPORT, true);
		selectParkingLotidNeededQueryMap.put(WorkerRequestType.ORDERS_REPORT, true);
		selectParkingLotidNeededQueryMap.put(WorkerRequestType.COMPLAINTS_REPORT, false);
		selectParkingLotidNeededQueryMap.put(WorkerRequestType.LOT_SPACES_REPORT, true);
		selectParkingLotidNeededQueryMap.put(WorkerRequestType.CURRENT_SUBSCRIBERS_REPORT, false);
		selectParkingLotidNeededQueryMap.put(WorkerRequestType.OPERATIONS_REPORT, false);
		selectParkingLotidNeededQueryMap.put(WorkerRequestType.PERFORMENCE_REPORT, false);
		return selectParkingLotidNeededQueryMap;
	}
}
