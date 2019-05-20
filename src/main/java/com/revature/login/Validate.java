package com.revature.login;

import java.util.Scanner;

import com.revature.login.Validate;

public class Validate {

	private static final Validate validate = new Validate();

	private Validate() {
	}

	public static Validate getInstance() {
		return validate;
	}

	public String validateUsername() {
		Scanner scan = new Scanner(System.in);
		String username = "";

		while (true) {
			try {
				System.out.print("Enter username ~$ ");
				username = scan.nextLine();

			} catch (Exception e) {

				System.out.println(e.getMessage());
				scan.next();
				continue;
			}
			break;
		}

		return username;
	}

	public String validatePassword() {
		Scanner scan = new Scanner(System.in);
		String password = "";

		System.out.print("Enter password ~$ ");
		password = scan.nextLine();
		return password;
	}

}
