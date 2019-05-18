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
//		Connection connection = null;
//		DBConnection connection = null;
		
		while (true) {
			try {
				System.out.print("Enter username: ");
//				if (scan.hasNextLine()) {
//				scan.next();
				username = scan.nextLine();
//				}
				
//				System.out.println(username);
//				connection = DBConnection.getConnect();
				
			} catch(Exception e) {
				
				System.out.println(e.getMessage());
				scan.next();
				continue;
			}
			break;
		}
		
//		scan.close();
		
		return username;
	}
	

	public String validatePassword() {
		Scanner scan = new Scanner(System.in);
		String password = "";
		
		System.out.print("Enter password: ");
		password = scan.nextLine();
		// TODO Auto-generated method stub
		return password;
	}

}
