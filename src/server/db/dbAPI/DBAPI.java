package server.db.dbAPI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import server.db.DBConnection;
import server.db.queries.ParkingMapQueries;

// TODO: Auto-generated Javadoc
/**
 * The Class DBAPI.
 */
public abstract class DBAPI {

	/** The mutex. */
	protected static Object mutex = new Object();
	
	/** The parking map queries inst. */
	protected ParkingMapQueries parkingMapQueriesInst; //this query is commons to several inherited instances

	//	protected RegularQueries regularQueriesInst;
	//	protected ParkingMapQueries parkingMapQueriesInst;


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
		params.add(leftDate);
		params.add(rightDate);
		DBConnection.selectSql(query, params, resultList);
	}
	
	
	/**
	 * Select between 2 dates query of lot id.
	 *
	 * @param query the query
	 * @param leftDate the left date
	 * @param rightDate the right date
	 * @param resultList the result list
	 * @param lotId the lot id
	 * @throws SQLException the SQL exception
	 */
	public static void selectBetween2DatesQueryOfLotId(String query, java.sql.Date leftDate, java.sql.Date rightDate, 
			ArrayList<Map<String, Object>> resultList, int lotId) throws SQLException {
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		params.add(leftDate);
		params.add(rightDate);
		params.add(lotId);
		DBConnection.selectSql(query, params, resultList);
	}

}

