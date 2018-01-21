package workerGui.util;

import java.util.HashMap;
import java.util.Map;

import core.worker.WorkerRequestType;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportsManager.
 */
public class ReportsManager {
	
	/** The instance. */
	private static ReportsManager instance;
	
	/** The requested report type. */
	private WorkerRequestType requestedReportType;
	
	/** The report name. */
	private String reportName;
	
	/** The date range is needed query map. */
	private Map<WorkerRequestType, Boolean> dateRangeIsNeededQueryMap;
	
	/** The select parking lotid needed query map. */
	private Map<WorkerRequestType, Boolean> selectParkingLotidNeededQueryMap;
	
	/**
	 * Instantiates a new reports manager.
	 */
	private ReportsManager() {
		dateRangeIsNeededQueryMap = CreateDateRangeNeededQueryMap();
		selectParkingLotidNeededQueryMap = CreateSelectParkingLotNeededQueryMap();
	}
	
	/**
	 * Gets the single instance of ReportsManager.
	 *
	 * @return single instance of ReportsManager
	 */
	public static ReportsManager getInstance() {
		if (instance == null) {
			instance = new ReportsManager();
			return instance;
		}

		return instance;
	}
	
	/**
	 * Sets the report context.
	 *
	 * @param reportType the new report context
	 */
	public void setReportContext(WorkerRequestType reportType) {
		requestedReportType = reportType;
	}

	/**
	 * Gets the report context.
	 *
	 * @return the report context
	 */
	public WorkerRequestType getReportContext() {
		return requestedReportType;
	}
	
	/**
	 * Checks if is date range required.
	 *
	 * @return the boolean
	 */
	public Boolean isDateRangeRequired() {
		return dateRangeIsNeededQueryMap.get(requestedReportType);
	}
	
	/**
	 * Checks if is select parking lot needed.
	 *
	 * @return the boolean
	 */
	public Boolean isSelectParkingLotNeeded() {
		return selectParkingLotidNeededQueryMap.get(requestedReportType);
	}
	
	/**
	 * Gets the report name.
	 *
	 * @return the report name
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * Sets the report name.
	 *
	 * @param reportName the new report name
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	};
	
	/**
	 * Creates the date range needed query map.
	 *
	 * @return the map
	 */
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
	
	/**
	 * Creates the select parking lot needed query map.
	 *
	 * @return the map
	 */
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
