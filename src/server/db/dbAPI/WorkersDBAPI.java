package server.db.dbAPI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import server.db.DBConnection;
import server.db.DBConstants;
import server.db.queries.WorkersQueries;

public class WorkersDBAPI extends DBAPI{
	
	
	public static void getPermissionsOfRole(DBConstants.Role role, ArrayList<Map<String, Object>> resultList) throws SQLException {
		
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		paramsValues.add(role.getName());
		paramTypes.add(DBConnection.sqlTypeKind.VARCHAR);
		
		DBConnection.selectSql(WorkersQueries.select_role_permissions, paramsValues, paramTypes, resultList);
		
	}
	
	public static void getWorkersRole(int workerId, ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> paramsValues = new LinkedList<Object>(); // push all params to q. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to q. in order of SQL
		paramsValues.add(workerId);
		paramTypes.add(DBConnection.sqlTypeKind.INT);
		DBConnection.selectSql(WorkersQueries.select_worker_role, paramsValues, paramTypes, resultList);
	}

	
	
	
	
}

