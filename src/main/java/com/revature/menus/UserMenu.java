package com.revature.menus;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.beans.Car;
import com.revature.enums.UserType;
import com.revature.login.Login;
import com.revature.login.RegisterAccount;
import com.revature.menus.Menu;
import com.revature.menus.ValidateUserSelectedOption;
import com.revature.services.CarService;
import com.revature.services.CarServiceOracle;
import com.revature.session.Session;

public class UserMenu extends Menu {
	String[] optionsAsUser = { "Login", "Register for customer account", "View cars on lot", "Quit" };

	Login login = new Login();

	Account account = null;

	private static Logger log = Logger.getLogger(UserMenu.class);

	RegisterAccount registerAccount = new RegisterAccount();

	@Override
	public Menu runMenu() {
		this.showOptions();
		int option = retrieveUserInputOption();

		switch (option) {
		case 1:
			account = login.signIn();
			log.info("Signing in");

			if (null != account) {
				log.trace(account.getAccountType());
				Session.Username = account.getUsername();
				Session.Password = account.getPassword();
				Session.ID = account.getId().toString();

				return MenuFactory.getMenu(account.getAccountType());

			} else {
				log.info("Failed to log in!");

				return MenuFactory.getMenu(UserType.USER);

			}

		case 2:
			Account accountCreated = registerAccount.register();

			if (null != accountCreated) {
				account = login.signIn(accountCreated);
				log.info("Account type: " + account.getAccountType());

				return MenuFactory.getMenu(account.getAccountType());

			}

			return MenuFactory.getMenu(UserType.USER);

		case 4:
			System.out.println("Signing out. Until next time.");
			System.exit(1);

		case 3:
			CarService cso = new CarServiceOracle();

			if (null != cso) {
				System.out.println(" \t Brand \t\t Year \t\t Price");
				for (Car car : cso.getCars()) {
					System.out.printf("%s\t b: %s \t y: %s \t p: $%s\n", car.getCar_id(), car.getBrand(), car.getYear(),
							car.getPrice());
				}
			}

			return MenuFactory.getMenu(UserType.USER);

		default:

			return MenuFactory.getMenu(UserType.USER);

		}

	}

	@Override
	public void showOptions() {
		printMenu(optionsAsUser);

	}

	@Override
	public int retrieveUserInputOption() {
		return ValidateUserSelectedOption.getInstance().validateOption(optionsAsUser.length);

	}

}