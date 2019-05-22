package com.revature.services;

import java.util.List;

import com.revature.beans.Payment;
import com.revature.data.PaymentDAO;
import com.revature.data.PaymentOracle;

public class PaymentServiceOracle implements PaymentService {

	private PaymentDAO paymentDAO = new PaymentOracle();
	
	@Override
	public List<Payment> getPayments() {

		return paymentDAO.getPayments();
		
	}

}
