package db.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import db.DBConstants;
import db.dbAPI.DBAPI;
import db.dbAPI.RegularDBAPI;
import db.dbAPI.ReportsDBAPI;
import db.queries.ReportsQueries;

class DBAPITest {

	private int entranceId;
	private int lotId;
	private int accountId;
	private String carId;
	private String email;
	private DBConstants.trueFalse hasSubscription;
	private DBConstants.orderType orderType;
	private Date dateArrive;
	private Calendar calendarLeft;
	private ArrayList<Map<String, Object>> resultList;
	private Map<String, Object> curMap;
	private double cash;
	private Calendar firstDateCalendar;
	private java.sql.Date firstDate;
	private java.sql.Date secondDate;


	@Before
	void setUp() throws Exception {
		dateArrive =  new Date(System.currentTimeMillis());
		calendarLeft = new GregorianCalendar();
		resultList = new ArrayList<Map<String, Object>>();
		lotId = 4;
		accountId = 12342;
		carId = "hsgfjk";
		orderType = DBConstants.orderType.ORDER;
		email = "asdas@jsdflkj.com";
		hasSubscription = DBConstants.trueFalse.FALSE;
		cash = 100;
		entranceId = 5;
		firstDateCalendar = new GregorianCalendar();
		firstDate = new java.sql.Date(firstDateCalendar.getTimeInMillis());
		firstDateCalendar.add(Calendar.DATE, -7); //get a week back
		secondDate = new java.sql.Date(firstDateCalendar.getTimeInMillis());
	}



	@Test
	void testGetAllOfReservationsBetween2Dates() throws SQLException {
		DBAPI.selectBetween2DatesQuery(ReportsQueries.select_all_reservations_between_2_dates, firstDate, secondDate, resultList);
		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			for (Map.Entry<String, Object> column : row.entrySet()) {
				System.out.println(column.getKey() + "/" + column.getValue());
			}
		}
		int i = 123;
	}
	
	
	@Test
	void testNumberOfReservationsOfLastWeekGroupedByOrder() throws SQLException {
		ReportsDBAPI.getNumberOfReservationsOfLastWeekGroupedByOrder(resultList);
		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			for (Map.Entry<String, Object> column : row.entrySet()) {
				System.out.println(column.getKey() + "/" + column.getValue());
			}
		}
	}
	
	
	@Test
	void testGetNumberOfReservationsOfLastDay() throws SQLException {
		ReportsDBAPI.getNumberOfReservationsOfLastDay(resultList);
		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			for (Map.Entry<String, Object> column : row.entrySet()) {
				System.out.println(column.getKey() + "/" + column.getValue());
			}
		}
	}	
	
	
	@Test
	void testCancelOrder()  {
		calendarLeft.set(2017, 11, 31, 23, 59, 01);
		Date dateLeave = calendarLeft.getTime();
		Date timeArrive = new Date(0);
		Date timeLeave = new Date(0);
		try {
			resultList.clear();
			entranceId = RegularDBAPI.insertParkingReservation(carId , accountId,
					lotId, dateArrive, dateLeave, timeArrive, timeLeave, orderType);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("insertParkingReservation");
		}

		//check that balance added to account
		try {
			resultList.clear();
			RegularDBAPI.selectAccountDetails(accountId, resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("getAccountDetails");
		}
		curMap = resultList.get(0);
		double oldBalance = (double) curMap.get(DBConstants.sqlColumns.BALANCE.getName());

		try {
			resultList.clear();
			ReportsDBAPI.getDailyStatsOfToday(resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("getDailyStatsOfToday");
		}
		curMap = resultList.get(0);
		int oldCancelledReservations = (int) curMap.get(DBConstants.sqlColumns.CANCELED_ORDERS.getName()); 

		try {
			RegularDBAPI.cancelOrder(entranceId, cash);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("cancelOrder");
		}

		try {
			resultList.clear();
			RegularDBAPI.selectAccountDetails(accountId, resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("getAccountDetails2");
		}
		curMap = resultList.get(0);
		double newBalance = (double) curMap.get(DBConstants.sqlColumns.BALANCE.getName());
		Assert.assertTrue(oldBalance+cash == newBalance);

		//get entranceId from current_parks_in_parking and check that it is empty
		resultList = new ArrayList<Map<String, Object>>();
		try {
			resultList.clear();
			RegularDBAPI.selectOrderStatus(entranceId, resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("trackOrderStatus");
		}
		Assert.assertTrue(resultList.isEmpty());

		try {
			resultList.clear();
			ReportsDBAPI.getDailyStatsOfToday(resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("getDailyStatsOfToday2");
		}
		curMap = resultList.get(0);
		int newCanceledReservations = (int) curMap.get(DBConstants.sqlColumns.CANCELED_ORDERS.getName());
		System.out.println("today canceled orders: " + newCanceledReservations);
		Assert.assertTrue((oldCancelledReservations+1) == newCanceledReservations);


	}


	@Test
	void testCarLeftParking() throws SQLException {
		calendarLeft.set(2017, 11, 31, 23, 59, 01);
		Date dateLeave = calendarLeft.getTime();
		Date timeArrive = new Date(0);
		Date timeLeave = new Date(0);
		entranceId = RegularDBAPI.insertParkingReservation(carId , accountId,
														lotId, dateArrive, dateLeave, timeArrive, timeLeave, orderType);	
		RegularDBAPI.carLeftParking(entranceId, timeLeave);

		//get entranceId from current_cars_in_parking and check that it is empty
		RegularDBAPI.selectOrderStatus(entranceId, resultList);
		Assert.assertTrue(resultList.isEmpty());

	}
	@Test
	void testCreateNewAccountAndGetCustomerAccountDetails() throws SQLException {

		RegularDBAPI.insertNewAccount(accountId, email, carId, hasSubscription);
		RegularDBAPI.selectAccountDetails(accountId, resultList);

		curMap = resultList.get(0);
		Assert.assertTrue(curMap.get(DBConstants.sqlColumns.ACCOUNT_ID.getName()).equals(accountId));
		Assert.assertTrue(curMap.get(DBConstants.sqlColumns.CAR_ID.getName()).equals(carId));
		System.out.println((curMap.get(DBConstants.sqlColumns.HAS_SUBSCRIPTION.getName()).toString()));
		System.out.println(hasSubscription.getValue());
		Assert.assertTrue((curMap.get(DBConstants.sqlColumns.HAS_SUBSCRIPTION.getName()).toString()).equals(hasSubscription.getValue()));
		Assert.assertTrue(curMap.get(DBConstants.sqlColumns.EMAIL.getName()).equals(email));


	}	
	@Test
	void testInsertOrderAndTrackOrder() throws SQLException {
		calendarLeft.set(2017, 11, 31, 23, 59, 01);
		Date dateLeave = calendarLeft.getTime();
		Date timeArrive = new Date(0);
		Date timeLeave = new Date(0);
		entranceId = RegularDBAPI.insertParkingReservation(carId , accountId,
				lotId, dateArrive, dateLeave, timeArrive, timeLeave, orderType);	
		RegularDBAPI.selectOrderStatus(entranceId, resultList);

		curMap = resultList.get(0);
		//			System.out.println(curMap.get(DBConstants.sqlColumns.LOT_ID.getName()));
		//			System.out.println(dateArrive);
		//			System.out.println((curMap.get(DBConstants.sqlColumns.ORDER_TYPE.getName())));
		//			System.out.println(orderType.getValue());
		Assert.assertTrue(curMap.get(DBConstants.sqlColumns.LOT_ID.getName()).equals(lotId));
		Assert.assertTrue(curMap.get(DBConstants.sqlColumns.ACCOUNT_ID.getName()).equals(accountId));
		Assert.assertTrue(curMap.get(DBConstants.sqlColumns.CAR_ID.getName()).equals(carId));
		Assert.assertTrue((curMap.get(DBConstants.sqlColumns.ORDER_TYPE.getName()).toString()).equals(orderType.getValue()));
		Assert.assertTrue(curMap.get(DBConstants.sqlColumns.ENTRANCE_ID.getName()).equals(entranceId));


	}



}
