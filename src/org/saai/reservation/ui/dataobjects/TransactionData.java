package org.saai.reservation.ui.dataobjects;

public class TransactionData {
	private int numberOfSeatsToBeBooked;
	private int orderId=-1;
	private int showTimingId;
	private String uuid;
	private boolean isTransactionReselectSeats=false;
	
	public TransactionData(){
		
	}

	public TransactionData(int numberOfSeatsToBeBooked, int orderId, int showTimingId,
			boolean isTransactionReselectSeats,String uuid) {
		super();
		this.numberOfSeatsToBeBooked = numberOfSeatsToBeBooked;
		this.orderId = orderId;
		this.showTimingId = showTimingId;
		this.isTransactionReselectSeats = isTransactionReselectSeats;
		this.uuid=uuid;
	}

	public int getNumberOfSeatsToBeBooked() {
		return numberOfSeatsToBeBooked;
	}

	public void setNumberOfSeatsToBeBooked(int numberOfSeatsToBeBooked) {
		this.numberOfSeatsToBeBooked = numberOfSeatsToBeBooked;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getShowTimingId() {
		return showTimingId;
	}

	public void setShowTimingId(int showTimingId) {
		this.showTimingId = showTimingId;
	}

	public boolean isTransactionReselectSeats() {
		return isTransactionReselectSeats;
	}

	public void setTransactionReselectSeats(boolean isTransactionReselectSeats) {
		this.isTransactionReselectSeats = isTransactionReselectSeats;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	

}
