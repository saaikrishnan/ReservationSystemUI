package org.saai.reservation.ui.services;

import org.apache.http.HttpResponse;
import org.saai.reservation.ui.dataobjects.ServiceResponse;
import org.saai.reservation.ui.utils.CommonUtils;
import org.saai.reservation.ui.utils.Constants;

import com.google.gson.Gson;

public class OrderSummaryClientService {

	public String getOrderSummaryByOrderId(int orderId) {

		String serviceURL = Constants.getServiceurl() + "/orders/"+orderId;

		ServiceResponse response = new ServiceResponse();
		CommonUtils util = new CommonUtils();
		Gson gson = new Gson();
		HttpResponse httpResponse = null;
		try {
			httpResponse = util.executeClientGet(serviceURL);
		} catch (Exception e) {
			response.setHasErrors(true);
			response.setErrorList(new String[] { e.getMessage() });
			return gson.toJson(response);
		}
		util.updateServiceResponse(response, httpResponse);
		return gson.toJson(response);
	}

}
