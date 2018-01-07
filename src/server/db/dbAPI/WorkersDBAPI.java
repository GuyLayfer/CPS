package server.db.dbAPI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import server.db.DBConnection;
import server.db.DBConstants;
import server.db.queries.WorkersQueries;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkersDBAPI.
 */
public class WorkersDBAPI extends DBAPI{
	
	//TODO: test
	/**
	 * Gets the permissions of role.
	 *
	 * @param role the role
	 * @param resultList the result list
	 * @return the permissions of role
	 * @throws SQLException the SQL exception
	 */
	public static void getPermissionsOfRole(DBConstants.Role role, ArrayList<Map<String, Object>> resultList) throws SQLException {
		
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		paramsValues.add(role.getName());
		paramTypes.add(DBConnection.sqlTypeKind.VARCHAR);
		
		DBConnection.selectSql(WorkersQueries.select_role_permissions, paramsValues, paramTypes, resultList);
		
	}
	
	
	//TODO: fix this. also to change names. not tested yet.
	/**
	 * Gets the workers role.
	 *
	 * @param workerId the worker id
	 * @param resultList the result list
	 * @return the workers role
	 * @throws SQLException the SQL exception
	 */
	public static void getWorkersRole(int workerId, ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		paramsValues.add(workerId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.selectSql(WorkersQueries.select_worker_role, paramsValues, paramTypes, resultList);
	}

	
	
	
	
}

