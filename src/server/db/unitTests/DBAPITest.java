package server.db.unitTests;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.db.DBConstants;
import server.db.DBConstants.SubscriptionType;
import server.db.dbAPI.DBAPI;
import server.db.dbAPI.RegularDBAPI;
import server.db.dbAPI.ReportsDBAPI;
import server.db.dbAPI.ServerUtils;
import server.db.dbAPI.SubscriptionsDBAPI;
import server.db.dbAPI.WorkersDBAPI;
import server.db.queries.ReportsQueries;

public class DBAPITest {

	private int entranceId;
	private int lotId;
	private int accountId;
	private String carId;
	private String email;
	private DBConstants.TrueFalse hasSubscription;
	private DBConstants.OrderType orderType;
	private Date dateArrive;
	private Calendar calendarLeft;
	private ArrayList<Map<String, Object>> resultList;
	private Map<String, Object> curMap;
	private double cash;

	private double oneTimePrice;
	private double orderPrice;
	private double subscriptionFullPrice;
	private double subscriptionOccasionalPrice;
	private double subscriptionMultipleCarsPrice;


	private Calendar firstDateCalendar;
	private java.sql.Date laterDate;
	private java.sql.Date earlierDate;

	private java.sql.Date departureDate;
	private java.sql.Date expiredDate;
	private java.sql.Date subscriptionStartTime;




	private RegularDBAPI regularDBAPIInst;
	private SubscriptionsDBAPI subscriptionsDBAPIInst;
	private ReportsDBAPI reportsDBAPIInst;
	private WorkersDBAPI workersDBAPIInst;

	private ReportsQueries reportsQueries;


	@Before
	public void setUp() throws Exception {
		dateArrive = new Date(System.currentTimeMillis());
		calendarLeft = new GregorianCalendar();
		resultList = new ArrayList<Map<String, Object>>();
		lotId = 4;
		accountId = 12342;
		carId = "hsgfjk";
		orderType = DBConstants.OrderType.ORDER;
		email = "asdas@jsdflkj.com";
		hasSubscription = DBConstants.TrueFalse.FALSE;
		cash = 100;
		entranceId = 5;
		firstDateCalendar = new GregorianCalendar();
		laterDate = new java.sql.Date(firstDateCalendar.getTimeInMillis());
		firstDateCalendar.add(Calendar.DATE, -9); // get a week back
		earlierDate = new java.sql.Date(firstDateCalendar.getTimeInMillis());

		departureDate = new java.sql.Date(firstDateCalendar.getTimeInMillis());
		expiredDate = new java.sql.Date(firstDateCalendar.getTimeInMillis());
		subscriptionStartTime  = new java.sql.Date(firstDateCalendar.getTimeInMillis());

		regularDBAPIInst = RegularDBAPI.getInstance();
		subscriptionsDBAPIInst = SubscriptionsDBAPI.getInstance();
		reportsDBAPIInst = ReportsDBAPI.getInstance();
		workersDBAPIInst = WorkersDBAPI.getInstance();


		reportsQueries = ReportsQueries.getInstance();


		oneTimePrice = 1;
		orderPrice = 2;
		subscriptionFullPrice = 3;
		subscriptionOccasionalPrice = 4;
		subscriptionMultipleCarsPrice = 5;
	}



	@Test
	public void testInsertIntoParkingServerInfo() {
		try {
			int newLotId1 = regularDBAPIInst.getNewParkingLotId();
			int newLotId2 = regularDBAPIInst.getNewParkingLotId();
			int newLotId3 = regularDBAPIInst.getNewParkingLotId();

			regularDBAPIInst.insertParkingLot(newLotId1, "1111111111111111111123123321231lfgdjklfdjsklj skjdfgklsjdfkjfdlksj kljsfkdjg kljkfdljgsklfdj lkjsdgfkj kjdfskgl jdlfkjflk jkldfjklgjdfk kjklj" +
					" sdfklgjklfsdgjfgkdsjsgdfkjgsfdkgsjfdkgjfdkjsdklgjsfdkjfakjgakjd");
			regularDBAPIInst.insertParkingLot(newLotId2, "222222222222222123123321231k jkldfjklgjdfk kjklj" +
					" sdfklgjklfsdgjfgkdsjsgdfkjgsfdkgsjfdkgjfdkjsdklgjsfdkjfakjgakjd");			
			regularDBAPIInst.insertParkingLot(newLotId3, "33333333333333333323123321231k jkldfjklgjdfk kjklj" +
					" sdfklgjklfsdgjfgkdsjsgdfkjgsfdkgsjfdkgjfdkjsdklgjsfdkjfakjgakjd");					
			Map<Integer, String> map = new HashMap<Integer, String>();
			regularDBAPIInst.selectAllParkingLots(map);
			for (Map.Entry<Integer, String> column : map.entrySet()) {
				System.out.println(column.getKey() + "/" + column.getValue());
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
//	@Test
//	public void testGet() {
//		try {
//			reportsDBAPIInst.generateReportsDataOfLotId("reservations");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Test
	public void testGenerateReportsData() {
		try {
			reportsDBAPIInst.generateReportsDataOfLotId("reservations", lotId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testInsertRates()  {
		try {
			workersDBAPIInst.insertRatesOfLotId(true, lotId, oneTimePrice, orderPrice, subscriptionFullPrice, subscriptionOccasionalPrice, subscriptionMultipleCarsPrice);


		workersDBAPIInst.selectAllLotsRates(false, resultList);
		ServerUtils.printAllInResultSet(resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Test
//	public void testInsertComplaint() {
//		int complaintId;
//		try {
//			complaintId = regularDBAPIInst.insertComplaint(accountId, "complaint description", entranceId, lotId, laterDate/*complaintDate*/);
//			regularDBAPIInst.selectComplaintDetails(complaintId, resultList);
//			ServerUtils.printAllInResultSet(resultList);
//		} catch (Exception e) { Assert.assertTrue(false);
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//	}


	@Test
	public void testInsertSubscription() {

		ArrayList<String> cars = new ArrayList<String>();

		cars.add("1234");
		cars.add("5678");

		int subscriptionId;
		try {
			subscriptionId = subscriptionsDBAPIInst.insertNewSubscription(accountId, lotId, SubscriptionType.FULL/*full*/, expiredDate, subscriptionStartTime, departureDate, cars);
	

		subscriptionsDBAPIInst.selectSubscriptionDetails(subscriptionId, resultList);
		ServerUtils.printAllInResultSet(resultList);
		System.out.println("cars of this subscription");
		resultList.clear();
		subscriptionsDBAPIInst.selectCarsOfSubscriptionId(subscriptionId, resultList);
		ServerUtils.printAllInResultSet(resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetNumberOfSubscriptionsHasMoreThanOneCar() {
		int numberOfSubsHavingMoreThanOneCar;
		try {
			numberOfSubsHavingMoreThanOneCar = subscriptionsDBAPIInst.getNumberOfSubscriptionsHasMoreThanOneCar();

		System.out.println(numberOfSubsHavingMoreThanOneCar);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetNumberOfCanceledReservationsBetween2Dates() {
		try {
			DBAPI.selectBetween2DatesQuery(reportsQueries.select_canceled_reservations_between_2_dates, earlierDate, laterDate, resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServerUtils.printAllInResultSet(resultList);

	}

	@Test
	public void testGetAllOfReservationsBetween2Dates()  {
		try {
			DBAPI.selectBetween2DatesQuery(reportsQueries.select_all_reservations_between_2_dates, laterDate, earlierDate, resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServerUtils.printAllInResultSet(resultList);
	}

	@Test
	public void testNumberOfReservationsOfLastWeekGroupedByOrder()  {
		try {
			reportsDBAPIInst.getNumberOfReservationsOfLastWeekGroupedByOrder(resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServerUtils.printAllInResultSet(resultList);
	}

	@Test
	public void testGetNumberOfReservationsOfLastDay()  {
		try {
			reportsDBAPIInst.getNumberOfReservationsOfLastDay(resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServerUtils.printAllInResultSet(resultList);
	}

	@Test
	public void testCancelOrder()  {
		calendarLeft.set(2017, 11, 31, 23, 59, 01);
		Date dateLeave = calendarLeft.getTime();
		Date timeArrive = new Date(0);
		Date timeLeave = new Date(0);
		try {
			resultList.clear();
			entranceId = regularDBAPIInst.insertParkingReservation(carId, accountId, lotId, dateArrive, dateLeave, timeArrive, timeLeave, orderType);
		} catch (Exception e) { Assert.assertTrue(false);
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("insertParkingReservation");
		}

		// check that balance added to account
		try {
			resultList.clear();
			regularDBAPIInst.selectAccountDetails(accountId, resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("getAccountDetails");
		}
		curMap = resultList.get(0);
		double oldBalance = ((double) (curMap.get(DBConstants.DbSqlColumns.BALANCE.getName())));

		try {
			regularDBAPIInst.cancelOrder(entranceId, cash);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("cancelOrder");
		}

		try {
			resultList.clear();
			regularDBAPIInst.selectAccountDetails(accountId, resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("getAccountDetails2");
		}
		curMap = resultList.get(0);
		double newBalance = ((double) (curMap.get(DBConstants.DbSqlColumns.BALANCE.getName())));
		Assert.assertTrue(oldBalance + cash == newBalance);

		// get entranceId from current_parks_in_parking and check that it is empty
		resultList = new ArrayList<Map<String, Object>>();
		try {
			resultList.clear();
			regularDBAPIInst.selectOrderStatus(entranceId, resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("trackOrderStatus");
		}
		Assert.assertTrue(resultList.isEmpty());

	}

	@Test
	public void testCarLeftParking() {
		calendarLeft.set(2017, 11, 31, 23, 59, 01);
		Date dateLeave = calendarLeft.getTime();
		Date timeArrive = new Date(0);
		java.sql.Date timeLeave = new java.sql.Date(System.currentTimeMillis());
		try {
			entranceId = regularDBAPIInst.insertParkingReservation(carId, accountId, lotId, dateArrive, dateLeave, timeArrive, timeLeave, orderType);
		} catch (Exception e) { Assert.assertTrue(false);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			regularDBAPIInst.carLeftParkingByEntranceId(entranceId, timeLeave);
		} catch (Exception e) { Assert.assertTrue(false);
			// TODO Auto-generated catch block
			Assert.assertTrue(false);
			e.printStackTrace();
		}

		// get entranceId from current_cars_in_parking and check that it is empty
		try {
			regularDBAPIInst.selectOrderStatus(entranceId, resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(resultList.isEmpty());

	}
	
	
	@Test
	public void testCarLeftParkingByCarId() {
		calendarLeft.set(2017, 11, 31, 23, 59, 01);
		Date dateLeave = calendarLeft.getTime();
		Date timeArrive = new Date(0);
		Date timeLeave = new Date(System.currentTimeMillis());
		try {
			entranceId = regularDBAPIInst.insertParkingReservation(carId, accountId, lotId, dateArrive, dateLeave, timeArrive, timeLeave, orderType);
		} catch (Exception e) { Assert.assertTrue(false); Assert.assertTrue(false);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			regularDBAPIInst.carLeftParkingByCarId(carId, lotId, timeLeave);
		} catch (Exception e) { Assert.assertTrue(false); Assert.assertTrue(false);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get entranceId from current_cars_in_parking and check that it is empty
		try {
			regularDBAPIInst.selectOrderStatus(entranceId, resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(resultList.isEmpty());

	}

	
	@Test
	public void testUpdateArriveTime()  {
		calendarLeft.set(2017, 11, 31, 23, 59, 01);
		Date dateLeave = calendarLeft.getTime();
		Date timeArrive = new Date(0);
		Date timeLeave = new Date(0);
		try {
			entranceId = regularDBAPIInst.insertParkingReservation(carId, accountId, lotId, dateArrive, dateLeave, timeArrive, timeLeave, orderType);
		} catch (Exception e) { Assert.assertTrue(false);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			regularDBAPIInst.updateArriveTime(carId, new java.sql.Date(System.currentTimeMillis()));
		} catch (Exception e) { Assert.assertTrue(false);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ServerUtils.printAllInResultSet(resultList);
	}

	@Test
	public void testCreateNewAccountAndGetCustomerAccountDetails()  {

		try {
			regularDBAPIInst.insertNewAccount(accountId, email, carId, hasSubscription);
			regularDBAPIInst.selectAccountDetails(accountId, resultList);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		curMap = resultList.get(0);
		Assert.assertTrue(curMap.get(DBConstants.DbSqlColumns.ACCOUNT_ID.getName()).equals(accountId));
		Assert.assertTrue(curMap.get(DBConstants.DbSqlColumns.CAR_ID.getName()).equals(carId));
		System.out.println((curMap.get(DBConstants.DbSqlColumns.HAS_SUBSCRIPTION.getName()).toString()));
		System.out.println(hasSubscription);
		System.out.println(curMap.get(DBConstants.DbSqlColumns.HAS_SUBSCRIPTION.getName()));
		Assert.assertTrue(curMap.get(DBConstants.DbSqlColumns.HAS_SUBSCRIPTION.getName()).equals(hasSubscription.getValue()));
		Assert.assertTrue(curMap.get(DBConstants.DbSqlColumns.EMAIL.getName()).equals(email));

	}

	@Test
	public void testInsertOrderAndTrackOrder(){
		calendarLeft.set(2017, 11, 31, 23, 59, 01);
		Date dateLeave = calendarLeft.getTime();
		Date timeArrive = new Date(0);
		Date timeLeave = new Date(0);
		try {
			entranceId = regularDBAPIInst.insertParkingReservation(carId, accountId, lotId, dateArrive, dateLeave, timeArrive, timeLeave, orderType);
		} catch (Exception e) { Assert.assertTrue(false);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			regularDBAPIInst.selectOrderStatus(entranceId, resultList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		curMap = resultList.get(0);
		// System.out.println(curMap.get(DBConstants.sqlColumns.LOT_ID.getName()));
		// System.out.println(dateArrive);
		// System.out.println((curMap.get(DBConstants.sqlColumns.ORDER_TYPE.getName())));
		// System.out.println(orderType.getValue());
		Assert.assertTrue(curMap.get(DBConstants.DbSqlColumns.LOT_ID.getName()).equals(lotId));
		Assert.assertTrue(curMap.get(DBConstants.DbSqlColumns.ACCOUNT_ID.getName()).equals(accountId));
		Assert.assertTrue(curMap.get(DBConstants.DbSqlColumns.CAR_ID.getName()).equals(carId));
		System.out.println((DBConstants.DbSqlColumns.ORDER_TYPE.getName()));

		System.out.println((curMap.get(DBConstants.DbSqlColumns.ORDER_TYPE.getName())));
		Assert.assertTrue(((curMap.get(DBConstants.DbSqlColumns.ORDER_TYPE.getName()))).equals(orderType.getValue()));
		Assert.assertTrue(curMap.get(DBConstants.DbSqlColumns.ENTRANCE_ID.getName()).equals(entranceId));

	}

}
