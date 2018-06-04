package com.yash.tcvm.model;

import java.util.Date;

import com.yash.tcvm.enumeration.BeverageType;

public class Order {

	private int quantity;
	private BeverageType beverageType;
	private Date orderDate = new Date();
	private Boolean orderStatus;

	public Order() {
	}

	public Order(int quantity, BeverageType beverageType) {
		super();
		this.quantity = quantity;
		this.beverageType = beverageType;
	}

	public Order(int quantity, BeverageType beverageType, Date orderDate, Boolean status) {
		super();
		this.quantity = quantity;
		this.beverageType = beverageType;
		this.orderDate = orderDate;
		this.orderStatus = status;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BeverageType getBeverageTypeEnum() {
		return beverageType;
	}

	public void setBeverageTypeEnum(BeverageType beverageType) {
		this.beverageType = beverageType;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Boolean getStatus() {
		return orderStatus;
	}

	public void setStatus(Boolean status) {
		this.orderStatus = status;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Order{ \r\n");
		sb.append("Qty : " + quantity + "\r\n");
		sb.append("Type : " + beverageType + "\r\n");
		sb.append("Date : " + orderDate + "\r\n");
		sb.append("Status : " + orderStatus + "\r\n");
		sb.append("}");
		return sb.toString();
	}

}
