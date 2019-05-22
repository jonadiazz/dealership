package com.revature.services;

import java.util.List;

import com.revature.beans.Car;

public interface CarService {

	public void addCar(Car car);

	public Car getCar(String brand, String year, String price);

	public List<Car> getCars();

	public void addCar();

	public Integer removeCar();

	public Integer makeOffer();

	List<Car> getCarsOwned();

	public Integer acceptRejectOffers();

	public Integer viewPendingOffers();
	
	Integer numberOfPayments();
}
