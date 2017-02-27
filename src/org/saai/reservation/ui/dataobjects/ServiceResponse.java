package org.saai.reservation.ui.dataobjects;

public class ServiceResponse {
	private boolean hasErrors;
	private String[] errorList;
	private String successMsg = "";

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public String[] getErrorList() {
		return errorList;
	}

	public void setErrorList(String[] errorList) {
		this.errorList = errorList;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	@Override
	public String toString() {
		return "SaveResponse{" + "hasErrors=" + hasErrors + ", errorList=" + errorList + ", successMsg=" + successMsg
				+ '}';
	}
}