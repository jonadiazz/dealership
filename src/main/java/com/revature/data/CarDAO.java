package com.revature.data;

import java.util.List;

import com.revature.beans.Car;

public interface CarDAO {

	int addCar(Car car);

	Car getCar(String brand, String year, String price);

	List<Car> getCars();

	Integer removeCar(Integer key);

	Integer makeOffer(Integer carId, Integer initialPaymentAmount, Integer monthsOfFinancing);

	List<Car> getCarsOwned();

	Integer acceptRejectOffers();

	Integer viewPendingOffers();
	
	Integer numberOfPayments();

}
