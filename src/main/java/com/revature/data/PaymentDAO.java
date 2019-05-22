package com.revature.data;

import java.util.List;

import com.revature.beans.Payment;

public interface PaymentDAO {

	public List<Payment> getPayments();
	
	public Integer addPayment(Payment payment);
}
