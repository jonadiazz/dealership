package com.revature.beans;

public class Car {
	
	private String brand;
	private String year;
	private String price;
	private Integer car_id;
	private int id;
	
	public Car(Integer car_id, String brand, String year, String price) {
		this.brand = brand;
		this.year = year;
		this.price = price;
		this.car_id = car_id;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}

	public void setId(int key) {
		// TODO Auto-generated method stub
		this.id = key;
	}

	public Integer getCar_id() {
		return car_id;
	}

	public void setCar_id(Integer car_id) {
		this.car_id = car_id;
	}
	

}
