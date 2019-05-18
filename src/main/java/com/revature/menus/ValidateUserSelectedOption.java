package com.revature.menus;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.menus.ValidateUserSelectedOption;
import com.revature.driver.Driver;
import com.revature.menus.NonOptionException;

public class ValidateUserSelectedOption {
	
	private static Logger log = Logger.getLogger(ValidateUserSelectedOption.class);

	private static final ValidateUserSelectedOption validate = new ValidateUserSelectedOption();
	
	private ValidateUserSelectedOption() {
	}

	public static ValidateUserSelectedOption getInstance() {
		return validate;
	}
	
	public int validateOption(int length) {
		Scanner scan = new Scanner(System.in);
		int option;
		
		while (true) {
			try {
				System.out.print("Enter option: ");
				option = scan.nextInt();
				isValidOption(option, length);
			} catch (NonOptionException e) {
				System.out.printf("%s\n\n", e.getMessage());
				continue;
				// TODO Auto-generated method stub
			} catch (Exception e) {
				scan.next();
				log.info("Invalid input. Try again\n\n");
				continue;
			}
			break;
		}
		
//		scan.close();
		
		return option;
	}

	private void isValidOption(int o, int L) throws NonOptionException {
		if (o > L || o < 1) {
			throw new NonOptionException(null);
		}
		// TODO Auto-generated method stub	
	}

	
}
