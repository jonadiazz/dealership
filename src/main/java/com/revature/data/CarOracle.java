package com.revature.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.Car;
import com.revature.session.Session;
import com.revature.utils.DBConnection;
import com.revature.utils.LogUtil;

import oracle.jdbc.logging.annotations.Log;

public class CarOracle implements CarDAO {
	private static Logger log = Logger.getLogger(AccountOracle.class);
	private static DBConnection cu = DBConnection.getDBConnection();
	
	@Override
	public int addCar(Car car) {
		int key = 0;
		Connection conn = cu.getConnection();
		log.info("Adding car to the lot");
		
		try {
			conn.setAutoCommit(false);
			String sql = "insert into cars (brand, year, price, car_id) values (?,?,?,car_id.nextval)";
			String [] keys = {"car_id"};
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
		// TODO Auto-generated method stub
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
				cars.add(new Car(rs.getInt("car_id"), rs.getString("brand"), rs.getString("year"), rs.getString("price")));
			}
			return cars;
		} catch (Exception e) {
			LogUtil.logException(e, Log.class);
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
				if (brand.equals(rs.getString("brand")) 
						&& year.equals(rs.getString("year")) 
							&& price.equals(rs.getString("price"))) {
					return new Car(rs.getInt("car_id"), brand, year, price);
					
				}
			}
		} catch (Exception e) {
			LogUtil.logException(e, Log.class);
			return null;
		}
		// TODO Auto-generated method stub
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
//			ResultSet rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			LogUtil.logException(e, Log.class);
			return 0;
		}
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Integer viewPendingOffers() {
		// TODO Auto-generated method stub
//		select * from Make_offer join cars on (make_offer.car_id = cars.car_id) join Accounts on (Make_offer.Customer_id = Accounts.Account_id);
		String sql = "select Make_offer_id, brand, year, price, username, down_payment, financing from Make_offer join cars on (make_offer.car_id = cars.car_id) join Accounts on (Make_offer.Customer_id = Accounts.Account_id)";

		Connection conn = cu.getConnection();
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				System.out.printf("%s:  Brand= %s\tYear= %s\tPrice= %s\tDown Payment= %s\tFinancing= %s\tCustomer= %s\n\n", rs.getString("make_offer_id"), rs.getString("brand"), rs.getString("year"), rs.getString("price"), rs.getString("down_payment"), rs.getString("financing"), rs.getString("username"));
			}
		} catch (SQLException e) {
			LogUtil.logException(e, Log.class);
			
		}
		return null;
	}
	
	@Override
	public Integer makeOffer(Integer carId, Integer initialPaymentAmount, Integer monthsOfFinancing) {
		String sql = "insert into Make_offer (down_payment, customer_id, car_id, financing, make_offer_id) values (?,?,?,?, make_offer_id.nextval)";
		
		/** Stored procedure **/
		String sql2 = "{call monthly_pay (?,?,?,?)}";
		
		Connection conn = cu.getConnection();
		
		try {
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, initialPaymentAmount.toString());
			stmt.setString(2, Session.ID);
			stmt.setString(3, carId.toString());
			stmt.setString(4, monthsOfFinancing.toString());
			log.info(Session.ID);
			
			int result = stmt.executeUpdate();
									
			if (result == 1) {
				conn.commit();
			} else {
				conn.rollback();
			}
			
			int monthly = 0;
			
			CallableStatement callableStmt = conn.prepareCall(sql2);
			callableStmt.setInt(1, Integer.valueOf(getCarById(carId).getPrice()));
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
		
		// TODO Auto-generated method stub
	}

	private Car getCarById(Integer carId) {
		String sql = "select * from Cars where car_id=?";
		Car car = null;
		Connection conn = cu.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, carId.toString());
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				car = new Car(Integer.valueOf(rs.getString("car_id")), rs.getString("brand"), rs.getString("year"), rs.getString("price"));
			}
			return car;
		} catch (SQLException e) {
			LogUtil.logException(e, Log.class);
		}
		// TODO Auto-generated method stub
		return null;
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
				cars.add(new Car(rs.getInt("car_id"), rs.getString("brand"), rs.getString("year"), rs.getString("price")));
			}
			return cars;
		} catch (SQLException e) {
			LogUtil.logException(e, Log.class);
		}
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer acceptRejectOffers() {
		String sql = "{call welcome_msg (?)}";
		
		Connection conn = cu.getConnection();
		
		try {
			CallableStatement stmt = conn.prepareCall(sql);
			
			stmt.setString(1, "Tierno");
			int result = stmt.executeUpdate();
//			
			if (result == 1) {
				System.out.println("Executed stored procedure.");
			} else {
				log.info("Not executed procedure.");
			}
		} catch (SQLException e) {
			LogUtil.logException(e, Log.class);
		}
		// TODO Auto-generated method stub
		return null;
	}




}
