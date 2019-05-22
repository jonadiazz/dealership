package com.revature.services;

import java.util.InputMismatchException;
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

		System.out.print("Enter make: ");
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
		try {
			Scanner scan = new Scanner(System.in);
			System.out.print("Select car to remove: ");
			Integer key = Integer.valueOf(scan.nextLine());
			return carDAO.removeCar(key);
		} catch (Exception e) {
			System.out.println("Invalid input");
			return null;
		}
	}

	@Override
	public Integer viewPendingOffers() {
		carDAO.viewPendingOffers();

		return null;

	}

	@Override
	public Integer makeOffer() throws NumberFormatException {
		Scanner scan = new Scanner(System.in);

		System.out.print("Select a car by ID ~$ ");
		Integer carId = Integer.valueOf(scan.nextLine());

		System.out.print("Initial payment amount ~$ ");
		Integer initialPaymentAmount = Integer.valueOf(scan.nextLine());

		System.out.print("How many months of financing ~$ ");
		Integer monthsOfFinancing = Integer.valueOf(scan.nextLine());

		return carDAO.makeOffer(carId, initialPaymentAmount, monthsOfFinancing);

	}

	@Override
	public List<Car> getCarsOwned() {

		return carDAO.getCarsOwned();

	}

	public Integer numberOfPayments() {

		return carDAO.numberOfPayments();
	}

	@Override
	public Integer acceptRejectOffers() {

		return carDAO.acceptRejectOffers();

	}

	@Override
	public Car getCarById(Integer carId) {

		return carDAO.getCarById(carId);

	}

}
