package org.saai.reservation.ui.dataobjects;

public class OrderUpdate {

	private int[] seatIds;

	public OrderUpdate() {

	}

	public OrderUpdate(int[] seatIds) {
		super();
		this.seatIds = seatIds;
	}

	public int[] getSeatIds() {
		return seatIds;
	}

	public void setSeatIds(int[] seatIds) {
		this.seatIds = seatIds;
	}

}
