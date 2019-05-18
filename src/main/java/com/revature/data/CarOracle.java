package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.Car;
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
		String sql = "select * from Cars";

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



}
