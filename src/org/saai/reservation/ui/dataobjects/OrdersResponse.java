package org.saai.reservation.ui.dataobjects;

public class OrdersResponse {

	private int orderId;

	public OrdersResponse() {

	}

	public OrdersResponse(int orderId) {
		super();
		this.orderId = orderId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}
