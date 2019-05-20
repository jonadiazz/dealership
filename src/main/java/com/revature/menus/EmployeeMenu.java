package com.revature.menus;

import org.apache.log4j.Logger;

import com.revature.beans.Car;
import com.revature.enums.UserType;
import com.revature.services.CarService;
import com.revature.services.CarServiceOracle;

public class EmployeeMenu extends Menu {

	private static Logger log = Logger.getLogger(EmployeeMenu.class);

	String[] optionsAsEmployee = { "Add a car", "Accept or reject offer for a car", "Remove a car", "View all payments",
			"View all cars", "View pending offers on a car", "Sign out" };

	@Override
	public void showOptions() {
		printMenu(optionsAsEmployee);

	}

	@Override
	public int retrieveUserInputOption() {

		return ValidateUserSelectedOption.getInstance().validateOption(optionsAsEmployee.length);

	}

	@Override
	public Menu runMenu() {
		this.showOptions();
		int option = retrieveUserInputOption();
		CarService cs = new CarServiceOracle();

		switch (option) {
		case 6:
			cs.viewPendingOffers();

			return MenuFactory.getMenu(UserType.EMPLOYEE);

		case 2:
			cs.acceptRejectOffers();

			return MenuFactory.getMenu(UserType.EMPLOYEE);

		case 7:
			return MenuFactory.getMenu(UserType.USER);

		case 1:
			cs.addCar();

			return MenuFactory.getMenu(UserType.EMPLOYEE);

		case 5:
			if (null != cs) {
				System.out.println(" \t Brand \t\t Year \t\t Price");

				for (Car car : cs.getCars()) {
					System.out.printf("%s\t b: %s \t y: %s \t p: $%s\n", car.getCar_id(), car.getBrand(), car.getYear(),
							car.getPrice());
				}
			}

			return MenuFactory.getMenu(UserType.EMPLOYEE);

		case 3:
			if (null != cs) {
				log.info("Removing car from lot.");
				cs.removeCar();
			}

			return MenuFactory.getMenu(UserType.EMPLOYEE);

		case 4: // TODO: case 4
		default:
			System.out.println("Option not implemented yet. Under construction!");

			return MenuFactory.getMenu(UserType.EMPLOYEE);

		}
	}

}
