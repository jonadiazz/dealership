package com.revature.menus;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.revature.enums.UserType;

public class TestMenuFactory {

	@Test
	public void getMenuShouldReturnNotNullWhenGivenUserType() {
		assertNotEquals(MenuFactory.getMenu(UserType.USER), null);
	}
	
	@Test
	public void getMenuShouldReturnAnInstanceOfMenu() {
		assertTrue("", MenuFactory.getMenu(UserType.CUSTOMER.toString()) instanceof Menu);
	}
}
