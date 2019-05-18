package com.revature.menus;

import org.apache.log4j.Logger;

import com.revature.beans.Car;
import com.revature.driver.Driver;
import com.revature.enums.UserType;
import com.revature.menus.Menu;
import com.revature.menus.ValidateUserSelectedOption;
import com.revature.services.CarService;
import com.revature.services.CarServiceOracle;

public class EmployeeMenu extends Menu {

	private static Logger log = Logger.getLogger(EmployeeMenu.class);

    String [] optionsAsEmployee = 
    	{"Add a car",  "Accept or reject offer for a car", "Remove a car", "View all payments", "View all cars", "View pending offers on a car", "Sign out"};

	@Override
	public void showOptions() {
		printMenu(optionsAsEmployee);
		// TODO Auto-generated method stub

	}

	@Override
	public int retrieveUserInputOption() {
		return ValidateUserSelectedOption.getInstance().validateOption(optionsAsEmployee.length);

		// TODO Auto-generated method stub
	}

	@Override
	public Menu runMenu() {
		this.showOptions();
		int option = retrieveUserInputOption();
		
		pickedOption: switch(option) {
			case 7:
				MenuFactory.getMenu(UserType.USER).runMenu();
				break pickedOption;
			case 1:
				CarService cs = new CarServiceOracle();
				cs.addCar();
				MenuFactory.getMenu(UserType.EMPLOYEE).runMenu();
				break pickedOption;
			case 5:
				CarService cso = new CarServiceOracle();
				
				if (null != cso) {
					System.out.println(" \t Brand \t\t Year \t\t Price");
					for (Car car: cso.getCars()) {
						System.out.printf("%s\t b: %s \t y: %s \t p: $%s\n", car.getCar_id(), car.getBrand(), car.getYear(), car.getPrice());
					}
				}
				MenuFactory.getMenu(UserType.EMPLOYEE).runMenu();
				break pickedOption;
			case 3:
				CarService csoDelete = new CarServiceOracle();
				
				if (null != csoDelete) {
					log.info("Removing car from lot.");
					csoDelete.removeCar();
				}
				
				MenuFactory.getMenu(UserType.EMPLOYEE).runMenu();
				break pickedOption;
		}
		// TODO Auto-generated method stub
		return null;
	}

}
