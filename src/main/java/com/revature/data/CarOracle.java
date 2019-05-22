package com.revature.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.beans.Car;
import com.revature.session.Session;
import com.revature.utils.DBConnection;
import com.revature.utils.LogUtil;

import oracle.jdbc.logging.annotations.Log;

public class CarOracle implements CarDAO {
	private static Logger log = Logger.getLogger(CarOracle.class);
	private static DBConnection cu = DBConnection.getDBConnection();

	@Override
	public int addCar(Car car) {
		int key = 0;
		Connection conn = cu.getConnection();
		log.info("Adding car to the lot");

		try {
			conn.setAutoCommit(false);
			String sql = "insert into cars (brand, year, price, car_id) values (?,?,?,car_id.nextval)";
			String[] keys = { "car_id" };
			PreparedStatement stmt = conn.prepareStatement(sql, keys);
			stmt.setString(1, car.getBrand());
			stmt.setString(2, car.getYear());
			stmt.setString(3, car.getPrice());

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				log.info("Car added to lot.");
				key = rs.getInt(1);
				car.setId(key);
				conn.commit();
			} else {
				log.trace("Car not added to lot.");
				conn.rollback();
			}
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, CarOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.logException(e, CarOracle.class);
			}
		}
		return 0;
	}

	@Override
	public List<Car> getCars() {
		String sql = "select car_id, brand, year, price from Cars Minus select Car_Owner.car_id, brand, year, price from Car_Owner join Cars on (Car_Owner.car_id = Cars.car_id) join Accounts on (Car_owner.account_id = Accounts.account_id)";
//		String sql = "select * from Cars";

		List<Car> cars = new ArrayList<Car>();

		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				cars.add(new Car(rs.getInt("car_id"), rs.getString("brand"), rs.getString("year"),
						rs.getString("price")));
			}
			return cars;
		} catch (Exception e) {
			LogUtil.logException(e, CarOracle.class);
			return null;
		}
	}

	@Override
	public Car getCar(String brand, String year, String price) {
		String sql = "select * from Cars where brand=? and year=? and price=?";

		try (Connection conn = cu.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, brand);
			stmt.setString(2, year);
			stmt.setString(3, price);

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				if (brand.equals(rs.getString("brand")) && year.equals(rs.getString("year"))
						&& price.equals(rs.getString("price"))) {
					return new Car(rs.getInt("car_id"), brand, year, price);

				}
			}
		} catch (Exception e) {
			LogUtil.logException(e, Log.class);
			return null;
		}
		return null;
	}

	@Override
	public Integer removeCar(Integer key) {
		String sql = "delete from cars where car_id=?";

		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, key);

			int result = stmt.executeUpdate();

			if (result == 1) {
				log.trace("Car removed.");
				conn.commit();
			} else {
				log.trace("Car not removed.");
				conn.rollback();
			}

		} catch (SQLException e) {
			LogUtil.logException(e, Log.class);
			return 0;
		}
		return 0;
	}

	@Override
	public Integer viewPendingOffers() {
		String sql = "select Make_offer_id, brand, year, price, username, down_payment, financing from Make_offer join cars on (make_offer.car_id = cars.car_id) join Accounts on (Make_offer.Customer_id = Accounts.Account_id)";

		Connection conn = cu.getConnection();

		Integer isOffers = 0;
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				if (isOffers == 0) {
					System.out.println("\n<OfferID>\n");
				}

				isOffers = 1;
				String makeOfferId = rs.getString("make_offer_id");
				String year = rs.getString("year");
				String make = rs.getString("brand").toUpperCase();
				String price = rs.getString("price");
				String username = rs.getString("username").toUpperCase();
				String downPayment = rs.getString("down_payment");
				String financing = rs.getString("financing");
				
				System.out.printf("\n<%s> \t %s %s at $%s, %s bids with a %s down payment and at %s months financing\n", makeOfferId, year, make, price, username, downPayment, financing);
				
			}
		} catch (SQLException e) {
			LogUtil.logException(e, Log.class);

		} catch (NullPointerException e) {
			return 0;
		}
		return isOffers;
	}

	@Override
	public Integer makeOffer(Integer carId, Integer initialPaymentAmount, Integer monthsOfFinancing) {
		String sql = "insert into Make_offer (down_payment, customer_id, car_id, financing, make_offer_id) values (?,?,?,?, make_offer_id.nextval)";

		List<Car> carsAvailable = getCars();

		/** Stored procedure **/
		String sql2 = "{call monthly_pay (?,?,?,?)}";

		Connection conn = cu.getConnection();

		try {
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(sql);
			int result = 0;

			for (Car carAvailable : carsAvailable) {
				if (carAvailable.getCar_id() == carId) {
					stmt.setString(1, initialPaymentAmount.toString());
					stmt.setString(2, Session.ID);
					stmt.setString(3, carId.toString());
					stmt.setString(4, monthsOfFinancing.toString());
					result = stmt.executeUpdate();

					break;

				}
			}

			if (result != 0) {
				conn.commit();
			} else {
				conn.rollback();

				return null;

			}

			int monthly = 0;

			CallableStatement callableStmt = conn.prepareCall(sql2);
			callableStmt.setInt(1, Integer.valueOf(getCarById(carId, conn).getPrice()));
			callableStmt.setInt(2, initialPaymentAmount);
			callableStmt.setInt(3, monthsOfFinancing);
			callableStmt.registerOutParameter(4, Types.INTEGER);

			callableStmt.execute();

			monthly = callableStmt.getInt(4);

			return monthly;

		} catch (SQLException e) {
			LogUtil.logException(e, Log.class);
			return null;
		}

	}

	public Car getCarById(Integer carId) {
		return getCarById(carId, cu.getConnection());
	}

	public Car getCarById(Integer carId, Connection conn) {
		String sql = "select * from Cars where car_id=?";
		Car car = null;

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, carId.toString());

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String make = rs.getString("brand");
				String year = rs.getString("year");
				String price = rs.getString("price");
				
				car = new Car(carId, make, year, price);
			}
			
			return car;
			
		} catch (SQLException e) {
			LogUtil.logException(e, Log.class);
		}

		return null;
	}

	@Override
	public Integer numberOfPayments() {
		List<Car> carsOwned = getCarsOwned();

		try (Connection connection = cu.getConnection()) {
			log.info("\nViewing remaining payments on cars.\n");

			for (Car carOwned : carsOwned) {
				String numberOfPaymentsSQL = "select count(*) ct from all_payments where customer_id = ? and car_id = ?";

				PreparedStatement pstmt = connection.prepareStatement(numberOfPaymentsSQL);

				pstmt.setInt(1, Integer.valueOf(Session.ID));
				pstmt.setInt(2, carOwned.getCar_id());

				ResultSet rs = pstmt.executeQuery();

				String monthsToPaySQL = "select financing from car_owner where car_id = ? and account_id = ?";

				PreparedStatement monthsFinancingStmt = connection.prepareStatement(monthsToPaySQL);

				monthsFinancingStmt.setInt(1, carOwned.getCar_id());
				monthsFinancingStmt.setInt(2, Integer.valueOf(Session.ID));

				ResultSet monthsFinanceRs = monthsFinancingStmt.executeQuery();

				if (rs.next()) {
					int nop = 0;
					int totalMonths = 0;

					if (monthsFinanceRs.isBeforeFirst()) {
						monthsFinanceRs.next();
						nop = rs.getInt("ct");

						totalMonths = Integer.valueOf(monthsFinanceRs.getInt("financing"));
					}

					System.out.printf(
							"You have %s payment(s) on a %s car with car ID [%s]. You have %d payments left.\n", nop,
							carOwned.getBrand(), carOwned.getCar_id(), totalMonths - Integer.valueOf(rs.getInt(1)));
				}
			}
			return null;

		} catch (SQLException e) {
			log.trace(e);
			return null;
		}

	}

	@Override
	public List<Car> getCarsOwned() {
		String sql = "select Brand, Year, Price, Car_Owner.Car_id from Car_Owner join Cars on (Car_Owner.car_id = Cars.car_id) join Accounts on (Accounts.account_id =Car_Owner.account_id) and Car_Owner.account_id=?";

		List<Car> cars = new ArrayList<Car>();
		Connection conn = cu.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, Session.ID);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int carId = rs.getInt("car_id");
				String make = rs.getString("brand");
				String year = rs.getString("year");
				String price = rs.getString("price");
				
				cars.add(new Car(carId, make, year, price));
			}
			
			conn.close();
			
			return cars;
			
		} catch (SQLException e) {
			LogUtil.logException(e, Log.class);
		}

		return null;
	}

	@Override
	public Integer acceptRejectOffers() {
		// TODO: Validate user input assignment
		Scanner scan = new Scanner(System.in);
		int offerId = 0;

		try {
			System.out.print("\nSelect offer to reject or accept: ");
			offerId = Integer.valueOf(scan.nextLine());
		} catch (NumberFormatException e) {
			log.info("\nNumber format invalid. View pending offers on cars\n");
			return null;
		}

		String callableSQL = "{CALL WHEN_INSERT_ON_CAR_OWNER()}";

		String viewOfferSQL = "SELECT BRAND, PRICE, DOWN_PAYMENT, FINANCING, CUSTOMER_ID FROM MAKE_OFFER INNER JOIN CARS ON (MAKE_OFFER.CAR_ID = CARS.CAR_ID AND MAKE_OFFER.MAKE_OFFER_ID = ?)";

		String rejectSQL = "DELETE MAKE_OFFER WHERE MAKE_OFFER.MAKE_OFFER_ID = ?";

		String acceptSQL = "INSERT INTO CAR_OWNER (CAR_ID, ACCOUNT_ID, DOWN_PAYMENT, FINANCING) SELECT MAKE_OFFER.CAR_ID, MAKE_OFFER.CUSTOMER_ID, MAKE_OFFER.DOWN_PAYMENT, MAKE_OFFER.FINANCING FROM MAKE_OFFER WHERE MAKE_OFFER.MAKE_OFFER_ID = ?";
		Connection conn = cu.getConnection();

		try {
			CallableStatement callableStmt = conn.prepareCall(callableSQL);

			PreparedStatement viewOfferStmt = conn.prepareStatement(viewOfferSQL);

			PreparedStatement acceptStmt = conn.prepareStatement(acceptSQL);

			PreparedStatement rejectStmt = conn.prepareStatement(rejectSQL);

			viewOfferStmt.setString(1, String.valueOf(offerId));

			acceptStmt.setString(1, String.valueOf(offerId));

			rejectStmt.setString(1, String.valueOf(offerId));

			ResultSet rs = viewOfferStmt.executeQuery();

			if (rs.next()) {
				System.out.printf(
						"OfferId= [%s]\tBrand= %s\tPrice %s\tDown payment= %s\tFinancing= %s\tCustomerId= %s\n",
						offerId, rs.getString("brand"), rs.getString("price"), rs.getString("down_payment"),
						rs.getString("financing"), rs.getString("customer_id"));

				System.out.printf("[R]eject or [A]ccept this offer, enter R or A: ");
				String acceptReject = "";

				if (scan.hasNext()) {
					acceptReject = scan.nextLine();
					log.trace(CarOracle.class);
				}

				viewOfferStmt.setString(1, String.valueOf(offerId));

				if ("R".equals(acceptReject.toUpperCase())) {
					log.info("\nDeleting Offer " + offerId + "\n");

					ResultSet r = rejectStmt.executeQuery();

					if (r.next()) {
						log.info("\nRejected offer, offer has been removed.\n");
					} else {
						log.warn("\nOffer " + offerId + " not removed. Query was not properly executed.\n");
					}

				} else if ("A".equals(acceptReject.toUpperCase())) {
					// TODO: Remove all other offers for the same car

					rs = acceptStmt.executeQuery();

					if (rs.next()) {
						/** removes offer from MAKE_OFFER **/
						callableStmt.execute();
						log.info("\nOffer " + offerId + " was accepted.\n");

					} else {
						log.info("\nOffer " + offerId + " not accepted. query was not properly executed.\n");
					}
				}
			}

		} catch (SQLException e) {
			LogUtil.logException(e, CarOracle.class);
		}

		return null;
	}

}
