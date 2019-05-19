package com.revature.menus;

import org.apache.log4j.Logger;

import com.revature.beans.Car;
import com.revature.enums.UserType;
import com.revature.menus.Menu;
import com.revature.menus.ValidateUserSelectedOption;
import com.revature.services.CarService;
import com.revature.services.CarServiceOracle;

public class CustomerMenu extends Menu {

	private static Logger log = Logger.getLogger(EmployeeMenu.class);

    private String[] optionsAsCustomer = 
    	{"Make an offer for a car", "View cars on lot", "View cars I own", "View remaining payments", "Sign out", };

	@Override
	public void showOptions() {
		printMenu(this.optionsAsCustomer);
		// TODO Auto-generated method stub
		
	}

	@Override
	public int retrieveUserInputOption() {
		return ValidateUserSelectedOption.getInstance().validateOption(optionsAsCustomer.length);
		// TODO Auto-generated method stub
	}

	@Override
	public Menu runMenu() {
		this.showOptions();
		int option = retrieveUserInputOption();
		
		pickedOption: switch(option) {
			case 3:
				log.info("Viewing cars owned");
				CarService carsOwned = new CarServiceOracle();
				
				if (null != carsOwned) {
					for(Car car: carsOwned.getCarsOwned()) {
						System.out.printf("%s \t %s \t %s\n", car.getCar_id(), car.getBrand(), car.getYear());
					}
				}
				return MenuFactory.getMenu(UserType.CUSTOMER);
//				break;
			case 1:
				log.info("Making offer for a car");
				System.out.print("Pick a car: ");
				CarService csoMakeOffer = new CarServiceOracle();
				
				if (null != csoMakeOffer) {
					log.info("Making offer for car.");
					int mp = csoMakeOffer.makeOffer();
					System.out.printf("%s are your monthly payments.\n", mp);
					log.info("Offer submitted");
				}
				return MenuFactory.getMenu(UserType.CUSTOMER);
//				break pickedOption;
			case 2:
				CarService cso = new CarServiceOracle();
				
				if (null != cso) {
					System.out.println(" \t Brand \t\t Year \t\t Price");
					for (Car car: cso.getCars()) {
						System.out.printf("%s\t b: %s \t y: %s \t p: $%s\n", car.getCar_id(), car.getBrand(), car.getYear(), car.getPrice());
					}
				}
				return MenuFactory.getMenu(UserType.CUSTOMER);
//				break pickedOption;
			case 5:
				return MenuFactory.getMenu(UserType.USER);
//				break pickedOption;
		}
		// TODO Auto-generated method stub
		return null;
	}
}