package com.revature.menus;

import org.apache.log4j.Logger;

import com.revature.beans.Car;
import com.revature.beans.Payment;
import com.revature.enums.UserType;
import com.revature.menus.Menu;
import com.revature.menus.ValidateUserSelectedOption;
import com.revature.services.CarService;
import com.revature.services.CarServiceOracle;
import com.revature.services.PaymentService;
import com.revature.services.PaymentServiceOracle;
import com.revature.session.Session;

public class CustomerMenu extends Menu {

	private static Logger log = Logger.getLogger(CustomerMenu.class);

	private String[] optionsAsCustomer = { "Make an offer for a car", "View cars on lot", "View cars I own",
			"View remaining payments", "Sign out", };

	@Override
	public void showOptions() {
		printMenu(this.optionsAsCustomer);

	}

	@Override
	public int retrieveUserInputOption() {
		return ValidateUserSelectedOption.getInstance().validateOption(optionsAsCustomer.length);

	}

	@Override
	public Menu runMenu() {
		this.showOptions();
		int option = retrieveUserInputOption();
		
		return runMenu(option);
	}
	
	public Menu runMenu(int option) {

		switch (option) {
		case 3:
			log.info("\nViewing cars owned\n");
			CarService carsOwned = new CarServiceOracle();

			if (null != carsOwned) {
				for (Car car : carsOwned.getCarsOwned()) {
					System.out.printf("<%s> \t %s \t %s\n", car.getCar_id(), car.getBrand(), car.getYear());
				}
			}

			return MenuFactory.getMenu(UserType.CUSTOMER);

		case 1:
			
			runMenu(2);
			
			CarService csoMakeOffer = null;
			csoMakeOffer = new CarServiceOracle();
			
			if (null != csoMakeOffer) {
				log.info("\nMaking offer for car.\n");
				int makeOfferFor = 0;
				
				try {
					makeOfferFor = csoMakeOffer.makeOffer();
				} catch (NumberFormatException numberFormatException) {
					log.info("\nInvalid input number format\n");
					return MenuFactory.getMenu(UserType.CUSTOMER);
				} catch (NullPointerException nullPointerException) {
					log.info("\nInvalid car ID. View cars on lot.\n");
					return MenuFactory.getMenu(UserType.CUSTOMER);
				}

				log.info("\nOffer submitted\n");
				
				System.out.printf("\n%s are your monthly payments.\n", makeOfferFor);
				
			}

			return MenuFactory.getMenu(UserType.CUSTOMER);

		case 2:
			CarService cso = new CarServiceOracle();

			if (null != cso) {
				System.out.println("\n \t Year \t\t Make \t\t Price");
				for (Car car : cso.getCars()) {
					System.out.printf("<%s>\t b: %s \t y: %s \t p: $%s\n", car.getCar_id(), car.getYear(), car.getBrand(),
							car.getPrice());
				}
			}

			return MenuFactory.getMenu(UserType.CUSTOMER);

		case 5:
			Session.ID = "?";
			Session.Username ="USERNAME";
			Session.Usertype = "USERTYPE";
			
			return MenuFactory.getMenu(UserType.USER);

		case 4: // TODO: View remaining payments
			CarService carService = new CarServiceOracle();
			
			if (null != carService) {
				carService.numberOfPayments();
			}

			return MenuFactory.getMenu(UserType.CUSTOMER);
			
//			PaymentService paymentService = new PaymentServiceOracle();
//			
//			if (null != paymentService) {
//				for(Payment p: paymentService.getPayments()) {
//					System.out.printf("", args)
//				}
//			}
		default:
			log.info("\nSite under construction. Come back later!\n");
			return MenuFactory.getMenu(UserType.CUSTOMER);
		}
	}
}