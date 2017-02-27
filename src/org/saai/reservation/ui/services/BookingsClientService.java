package org.saai.reservation.ui.services;

import org.apache.http.HttpResponse;
import org.saai.reservation.ui.dataobjects.ServiceResponse;
import org.saai.reservation.ui.utils.CommonUtils;
import org.saai.reservation.ui.utils.Constants;

import com.google.gson.Gson;

public class BookingsClientService {

	public String confirmOrderAvailableForBooking(int orderId) {

		String serviceURL = Constants.getServiceurl() + "/orders/" + orderId + "/confirmations";

		ServiceResponse response = new ServiceResponse();
		CommonUtils util = new CommonUtils();
		Gson gson = new Gson();
		HttpResponse httpResponse = null;
		try {
			httpResponse = util.executeClientPostWithoutBody(serviceURL);
		} catch (Exception e) {
			response.setHasErrors(true);
			response.setErrorList(new String[] { e.getMessage() });
			return gson.toJson(response);
		}
		util.updateServiceResponse(response, httpResponse);
		return gson.toJson(response);
	}

	public String cancelOrderAvailableForBooking(int orderId) {

		String serviceURL = Constants.getServiceurl() + "/orders/" + orderId + "/cancellations";

		ServiceResponse response = new ServiceResponse();
		CommonUtils util = new CommonUtils();
		Gson gson = new Gson();
		HttpResponse httpResponse = null;
		try {
			httpResponse = util.executeClientDeleteWithoutBody(serviceURL);
		} catch (Exception e) {
			response.setHasErrors(true);
			response.setErrorList(new String[] { e.getMessage() });
			return gson.toJson(response);
		}
		util.updateServiceResponse(response, httpResponse);
		return gson.toJson(response);
	}

}
