package com.revature.data;

import java.util.List;

import com.revature.beans.Car;

public interface CarDAO {
	
	int addCar(Car car);
	Car getCar(String brand, String year, String price);
	List<Car> getCars();
	
	Integer removeCar(Integer key);
}
