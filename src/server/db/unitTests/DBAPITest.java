package server.db.unitTests;


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
import server.db.DBConstants;
import server.db.DBConstants.TrueFalse;
import server.db.dbAPI.DBAPI;
import server.db.dbAPI.RegularDBAPI;
import server.db.dbAPI.ReportsDBAPI;
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
	
	private Calendar firstDateCalendar;
	private java.sql.Date laterDate;
	private java.sql.Date earlierDate;

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

		regularDBAPIInst = RegularDBAPI.getInstance();
		subscriptionsDBAPIInst = SubscriptionsDBAPI.getInstance();
		reportsDBAPIInst = ReportsDBAPI.getInstance();
		workersDBAPIInst = WorkersDBAPI.getInstance();


		reportsQueries = ReportsQueries.getInstance();
		
		
		 oneTimePrice = 1;
		 orderPrice = 2;
		 subscriptionFullPrice = 3;
		 subscriptionOccasionalPrice = 4;
	}


	
	@Test
	public void testInsertRates() throws SQLException {
		workersDBAPIInst.insertRatesOfLotId(false, lotId, oneTimePrice, orderPrice, subscriptionFullPrice, subscriptionOccasionalPrice);

		workersDBAPIInst.selectAllLotsRates(false, resultList);
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			for (Map.Entry<String, Object> column : row.entrySet()) {
				System.out.println(column.getKey() + "/" + column.getValue());
			}
		}
	}
	
	@Test
	public void testInsertComplaint() throws SQLException {
		int complaintId = regularDBAPIInst.insertComplaint(accountId, "complaint description", laterDate);

		regularDBAPIInst.selectComplaintDetails(complaintId, resultList);
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			for (Map.Entry<String, Object> column : row.entrySet()) {
				System.out.println(column.getKey() + "/" + column.getValue());
			}
		}
	}
	
	@Test
	public void testInsertSubscription() throws SQLException {
		
		ArrayList<String> cars = new ArrayList<String>();
		
		cars.add("1234");
		cars.add("5678");
		int subscriptionId = subscriptionsDBAPIInst.insertNewSubscription(accountId, lotId, TrueFalse.TRUE/*full*/, laterDate/*expiredDate*/, cars);

		subscriptionsDBAPIInst.selectSubscriptionDetails(subscriptionId, resultList);
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			for (Map.Entry<String, Object> column : row.entrySet()) {
				System.out.println(column.getKey() + "/" + column.getValue());
			}
		}
		System.out.println("cars of this subscription");
		resultList.clear();
		subscriptionsDBAPIInst.selectCarsOfSubscriptionId(subscriptionId, resultList);
		Iterator<Map<String, Object>> iteratorTwo = resultList.iterator();
		while (iteratorTwo.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iteratorTwo.next();
			for (Map.Entry<String, Object> column : row.entrySet()) {
				System.out.println(column.getKey() + "/" + column.getValue());
			}
		}

	}

	@Test
	public void testGetNumberOfSubscriptionsHasMoreThanOneCar() throws SQLException {
		int numberOfSubsHavingMoreThanOneCar = subscriptionsDBAPIInst.getNumberOfSubscriptionsHasMoreThanOneCar();
		System.out.println(numberOfSubsHavingMoreThanOneCar);
	}

	@Test
	public void testGetNumberOfCanceledReservationsBetween2Dates() throws SQLException {
		DBAPI.selectBetween2DatesQuery(reportsQueries.select_canceled_reservations_between_2_dates, earlierDate, laterDate, resultList);
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext())
			for (; iterator.hasNext();) {
				Map<String, Object> row = (Map<String, Object>) iterator.next();
				for (Map.Entry<String, Object> column : row.entrySet()) {
					System.out.println(column.getKey() + "/" + column.getValue());
				}
			}
	}

	@Test
	public void testGetAllOfReservationsBetween2Dates() throws SQLException {
		DBAPI.selectBetween2DatesQuery(reportsQueries.select_all_reservations_between_2_dates, laterDate, earlierDate, resultList);
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			for (Map.Entry<String, Object> column : row.entrySet()) {
				System.out.println(column.getKey() + "/" + column.getValue());
			}
		}
	}

	@Test
	public void testNumberOfReservationsOfLastWeekGroupedByOrder() throws SQLException {
		reportsDBAPIInst.getNumberOfReservationsOfLastWeekGroupedByOrder(resultList);
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			for (Map.Entry<String, Object> column : row.entrySet()) {
				System.out.println(column.getKey() + "/" + column.getValue());
			}
		}
	}

	@Test
	public void testGetNumberOfReservationsOfLastDay() throws SQLException {
		reportsDBAPIInst.getNumberOfReservationsOfLastDay(resultList);
		Iterator<Map<String, Object>> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> row = (Map<String, Object>) iterator.next();
			for (Map.Entry<String, Object> column : row.entrySet()) {
				System.out.println(column.getKey() + "/" + column.getValue());
			}
		}
	}

	@Test
	public void testCancelOrder() {
		calendarLeft.set(2017, 11, 31, 23, 59, 01);
		Date dateLeave = calendarLeft.getTime();
		Date timeArrive = new Date(0);
		Date timeLeave = new Date(0);
		try {
			resultList.clear();
			entranceId = regularDBAPIInst.insertParkingReservation(carId, accountId, lotId, dateArrive, dateLeave, timeArrive, timeLeave, orderType);
		} catch (SQLException e) {
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
	public void testCarLeftParking() throws SQLException {
		calendarLeft.set(2017, 11, 31, 23, 59, 01);
		Date dateLeave = calendarLeft.getTime();
		Date timeArrive = new Date(0);
		Date timeLeave = new Date(0);
		entranceId = regularDBAPIInst.insertParkingReservation(carId, accountId, lotId, dateArrive, dateLeave, timeArrive, timeLeave, orderType);
		regularDBAPIInst.carLeftParking(entranceId, timeLeave);

		// get entranceId from current_cars_in_parking and check that it is empty
		regularDBAPIInst.selectOrderStatus(entranceId, resultList);
		Assert.assertTrue(resultList.isEmpty());

	}

	@Test
	public void testCreateNewAccountAndGetCustomerAccountDetails() throws SQLException {

		regularDBAPIInst.insertNewAccount(accountId, email, carId, hasSubscription);
		regularDBAPIInst.selectAccountDetails(accountId, resultList);

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
	public void testInsertOrderAndTrackOrder() throws SQLException {
		calendarLeft.set(2017, 11, 31, 23, 59, 01);
		Date dateLeave = calendarLeft.getTime();
		Date timeArrive = new Date(0);
		Date timeLeave = new Date(0);
		entranceId = regularDBAPIInst.insertParkingReservation(carId, accountId, lotId, dateArrive, dateLeave, timeArrive, timeLeave, orderType);
		regularDBAPIInst.selectOrderStatus(entranceId, resultList);

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
