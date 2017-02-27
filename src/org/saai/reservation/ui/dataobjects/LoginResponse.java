package org.saai.reservation.ui.dataobjects;

public class LoginResponse {
	private String emailId;

	public LoginResponse() {

	}

	public LoginResponse(String emailId) {
		super();
		this.emailId = emailId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
