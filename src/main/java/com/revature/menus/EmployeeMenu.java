package com.revature.menus;

import org.apache.log4j.Logger;

import com.revature.beans.Car;
import com.revature.beans.Payment;
import com.revature.enums.UserType;
import com.revature.services.CarService;
import com.revature.services.CarServiceOracle;
import com.revature.services.PaymentService;
import com.revature.services.PaymentServiceOracle;
import com.revature.session.Session;

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
		
		return runMenu(option);
		
	}

	
	public Menu runMenu(int option) {
		
		CarService cs = new CarServiceOracle();

		switch (option) {
		case 6:
			cs.viewPendingOffers();

			return MenuFactory.getMenu(UserType.EMPLOYEE);

		case 2:
			cs.viewPendingOffers();
			
			cs.acceptRejectOffers();

			return MenuFactory.getMenu(UserType.EMPLOYEE);

		case 7:
			Session.ID = "?";
			Session.Username ="?";
			
			return MenuFactory.getMenu(UserType.USER);

		case 1:
			cs.addCar();

			return MenuFactory.getMenu(UserType.EMPLOYEE);

		case 5:
			if (null != cs) {
				System.out.println(" \t Make \t\t Year \t\t Price");

				// TODO: Fix getCars as to return All cars (including those with owned_by and in_lot status
				for (Car car : cs.getCars()) {
					System.out.printf("<%s>\t b: %s \t y: %s \t p: $%s\n", car.getCar_id(), car.getBrand(), car.getYear(),
							car.getPrice());
				}
			}

			return MenuFactory.getMenu(UserType.EMPLOYEE);

		case 3:
			
			runMenu(5);
			
			if (null != cs) {
				log.info("Removing car from lot.");
				cs.removeCar();
			}

			return MenuFactory.getMenu(UserType.EMPLOYEE);

		case 4:
			log.info("Fetching all payments");
			PaymentService paymentService = new PaymentServiceOracle();
			
			if (null != paymentService) {
				for(Payment p: paymentService.getPayments()) {
//					Session.ID = String.valueOf(p.getCustomerId());
//					new CarServiceOracle().getCarsOwned();
					System.out.printf("Customer %s has paid %s for a car with ID [%s]\n", p.getCustomerId(), p.getPaymentAmount(), p.getCarId());
				}
			}
			return MenuFactory.getMenu(UserType.EMPLOYEE);
		default:
			System.out.println("Option not implemented yet. Under construction!");

			return MenuFactory.getMenu(UserType.EMPLOYEE);

		}
	}

}
