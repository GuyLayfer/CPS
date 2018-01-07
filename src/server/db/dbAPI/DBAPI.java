package db.dbAPI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import db.DBConnection;

public abstract class DBAPI {


	public static void selectBetween2DatesQuery(String query, java.sql.Date leftDate, java.sql.Date rightDate, ArrayList<Map<String, Object>> resultList) throws SQLException {
		
		Queue<Object> params = new LinkedList<Object>(); // push all params to paramsValues. in order of SQL
		Queue<DBConnection.sqlTypeKind> paramTypes = new LinkedList<DBConnection.sqlTypeKind>(); // push all params to paramsValues. in order of SQL
		params.add(leftDate);
		paramTypes.add(DBConnection.sqlTypeKind.DATE);
		params.add(rightDate);
		paramTypes.add(DBConnection.sqlTypeKind.DATE);
		DBConnection.selectSql(query, params, paramTypes, resultList);
	}

}
