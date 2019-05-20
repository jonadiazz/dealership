package com.revature.services;

import java.util.List;
import java.util.Scanner;

import com.revature.beans.Car;
import com.revature.data.CarDAO;
import com.revature.data.CarOracle;

public class CarServiceOracle implements CarService {

	private CarDAO carDAO = new CarOracle();

	@Override
	public void addCar(Car car) {
		carDAO.addCar(car);
	}

	@Override
	public Car getCar(String brand, String year, String price) {

		return carDAO.getCar(brand, year, price);

	}

	@Override
	public List<Car> getCars() {

		return carDAO.getCars();

	}

	@Override
	public void addCar() {
		Scanner scan = new Scanner(System.in);

		System.out.print("Enter brand: ");
		String brand = scan.nextLine();

		System.out.print("Enter year: ");
		String year = scan.nextLine();

		System.out.print("Enter price: ");
		String price = scan.nextLine();

		Car car = new Car(null, brand, year, price);
		carDAO.addCar(car);
	}

	@Override
	public Integer removeCar() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Select car to remove: ");
		Integer key = scan.nextInt();

		return carDAO.removeCar(key);

	}

	@Override
	public Integer viewPendingOffers() {
		carDAO.viewPendingOffers();

		return null;

	}

	@Override
	public Integer makeOffer() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Select a car: ");
		Integer carId = scan.nextInt();
		System.out.print("Initial payment amount: ");
		Integer initialPaymentAmount = scan.nextInt();
		System.out.print("How many months of financing: ");
		Integer monthsOfFinancing = scan.nextInt();

		return carDAO.makeOffer(carId, initialPaymentAmount, monthsOfFinancing);

	}

	@Override
	public List<Car> getCarsOwned() {

		return carDAO.getCarsOwned();

	}

	@Override
	public Integer acceptRejectOffers() {

		return carDAO.acceptRejectOffers();

	}

}
