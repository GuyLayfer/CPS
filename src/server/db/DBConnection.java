package server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import java.util.Date;
import java.util.HashMap;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;


public class DBConnection {

	public enum sqlTypeKind {INT, VARCHAR,  TIMESTAMP, DATE, DOUBLE, ENUM, TEXT;
	};

	public static void preparePSParams(String stmt, PreparedStatement ps, 
			Queue<Object> params) throws SQLException {

		for (int i = 1; params != null && !params.isEmpty(); i++) {
			Object curParam = params.remove();

			if (curParam instanceof Integer) {
				System.out.println();
				ps.setInt(i, ((Integer) curParam).intValue());
			}
			else if (curParam instanceof Double) {
				ps.setDouble(i, ((Double) curParam));
			}
			else if (curParam instanceof Timestamp) {
				ps.setTimestamp(i, (Timestamp) curParam);
			}
			else if (curParam instanceof String) {
				ps.setString(i, (String) curParam);
			}
			else if (curParam instanceof Date) {
				java.sql.Date sqlExpireDate = new java.sql.Date(((Date)curParam).getTime());
				ps.setDate(i, sqlExpireDate);
			}
		}
	}


	public static void selectSql(String stmtString,
			Queue<Object> params, ArrayList<Map<String, Object>> resultList) throws SQLException 
	{

		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {/* handle the error*/}

		try 
		{
			Connection conn =
					DriverManager.getConnection("jdbc:mysql://softengproject.cspvcqknb3vj.eu-central-1.rds.amazonaws.com/short_tailed_bat_schema",
							"short_tailed_bat","9(QxN\"&c7.52(jTS");
			System.out.println("SQL connection succeed");
			try {
				PreparedStatement ps = (PreparedStatement) conn.prepareStatement(stmtString);
				preparePSParams(stmtString, ps, params);
				ResultSet rs = ps.executeQuery();

				Map<String, Object> row = null;
				ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
				Integer columnCount = metaData.getColumnCount();

				while (rs.next()) {
					row = new HashMap<String, Object>();
					for (int i = 1; i <= columnCount; i++) {
						row.put(metaData.getColumnName(i), rs.getObject(i));
					}
					resultList.add(row);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				conn.close();	
			}
		} catch (SQLException ex) 
		{/* handle any errors*/
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			throw ex;
		}
	}

	public static int updateSql(String stmtString, Queue<Object> params) throws SQLException 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {/* handle the error*/}
		try 
		{
			Connection conn =
					DriverManager.getConnection("jdbc:mysql://softengproject.cspvcqknb3vj.eu-central-1.rds.amazonaws.com/short_tailed_bat_schema",
							"short_tailed_bat","9(QxN\"&c7.52(jTS");
			System.out.println("SQL connection succeed");
			try {
				PreparedStatement ps = (PreparedStatement) conn.prepareStatement(stmtString, Statement.RETURN_GENERATED_KEYS);
				preparePSParams(stmtString, ps, params);
				ps.executeUpdate();

				//TODO: if decide to use auto increment, use this
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					return rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.close();
		} catch (SQLException ex) 
		{/* handle any errors*/
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			throw ex;
		}
		return 0;	
	}




}
