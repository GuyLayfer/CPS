package db;
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
import java.util.Vector;
import java.util.Date;
import java.util.HashMap;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;



public class DBConnection {



	enum sqlTypeKind {INT, VARCHAR, FLOAT, TIMESTAMP, DATE};
	enum orderType {ONE_TIME, ORDER, SUBSCRIPTION, SUBSCRIPTION_FULL};
	enum parkingMap {FREE, PARKED, RESERVED, BROKEN, MAINTENENCE};


	public static void preparePSParams(String stmt, PreparedStatement ps, 
			Queue<Object> params, Queue<sqlTypeKind> types) throws SQLException {

		for (int i = 1; params != null && !params.isEmpty(); i++) {
			Object curParam = params.remove();
			sqlTypeKind curType = types.remove();
			System.out.println(curParam);
			System.out.println(curType);
			switch(curType) {
			case INT:
				ps.setInt(i, (int) curParam);
				break;
			case FLOAT:
				ps.setFloat(i, (Float) curParam);
				break;
			case TIMESTAMP:
				//				Date d = (Date) curParam;


				//			    java.util.Date utilDate = new java.util.Date();
				//			    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

				java.util.Date CreatedDate = new Date();
				Timestamp ts = new Timestamp(CreatedDate.getTime());


				String date = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(ts);

				//			    System.out.println(date);

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date parsedDate;
				try {
					parsedDate = dateFormat.parse(date);
					Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
					ps.setTimestamp(i, timestamp);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case VARCHAR:
				ps.setString(i, (String) curParam);
				break;
			default:
				break;
			}
		}	
	}


	public static void selectSql(String stmtString,
			Queue<Object> params, Queue<sqlTypeKind> types, ArrayList<Map<String, Object>> resultList) throws SQLException 
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
			Statement stmt;
			try {
				PreparedStatement ps = (PreparedStatement) conn.prepareStatement(stmtString);

				preparePSParams(stmtString, ps, params, types);

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




	public static void updateSql(String stmtString, Queue<Object> params, Queue<sqlTypeKind> types) throws SQLException 
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

				preparePSParams(stmtString, ps, params, types);


				ps.executeUpdate();
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
	}




}
