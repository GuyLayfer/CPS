package server.db.dbAPI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import server.db.DBConnection;
import server.db.queries.WorkersQueries;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkersDBAPI.
 */
public class WorkersDBAPI extends DBAPI{

	private static volatile WorkersDBAPI instance;
	//	private static Object mutex = new Object();
	private WorkersQueries workersQueriesInst;


	private WorkersDBAPI() {
		workersQueriesInst = WorkersQueries.getInstance();
	}

	public static WorkersDBAPI getInstance() {
		WorkersDBAPI result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null)
					instance = result = new WorkersDBAPI();
			}
		}
		return result;
	}


	//TODO: fix this. also to change names. not tested yet.
	/**
	 * Gets the worker by name and password.
	 *
	 * @param workerId the worker id
	 * @param workerPassword the worker password
	 * @param resultList the result list
	 * @return the workers role
	 * @throws SQLException the SQL exception
	 */
	public void getWorkerByNameAndPassword(int workerId, String workerPassword, ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		paramsValues.add(workerId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		paramsValues.add(workerPassword);
		paramTypes.add(DBConnection.sqlTypeKind.VARCHAR);
		DBConnection.selectSql(workersQueriesInst.selectWorkerBtNameAndPassword, paramsValues, paramTypes, resultList);
	}
}

