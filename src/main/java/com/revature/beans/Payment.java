package com.revature.beans;

public class Payment {

	private String paymentAmount;
	private Integer carId;
	private Integer customerId;

	public Payment(String paymentAmount, Integer carId, Integer customerId) {
		this.paymentAmount = paymentAmount;
		this.carId = carId;
		this.customerId = customerId;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}
