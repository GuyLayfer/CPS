package db.dbAPI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import db.DBConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class DBAPI.
 */
public abstract class DBAPI {


	/**
	 * Select between 2 dates query.
	 *
	 *	selects details (according to 'query') of all the records between the dates.
	 *
	 * @param query the query - the select query the user wants to use.
	 * @param leftDate the left date - the earlier date. TODO: change the name of this param to: earlierDate
	 * @param rightDate the right date - the latter date. TODO: change the name of this param to: latterDate
	 * @param resultList the result list
	 * @throws SQLException the SQL exception
	 */
	public static void selectBetween2DatesQuery(String query, java.sql.Date leftDate, java.sql.Date rightDate, 
												ArrayList<Map<String, Object>> resultList) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(leftDate);
		paramTypes.add(DBConnection.sqlTypeKind.DATE);
		params.add(rightDate);
		paramTypes.add(DBConnection.sqlTypeKind.DATE);
		DBConnection.selectSql(query, params, paramTypes, resultList);
	}

}
