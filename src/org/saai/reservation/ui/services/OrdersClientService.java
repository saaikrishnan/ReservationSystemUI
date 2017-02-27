package org.saai.reservation.ui.services;

import org.apache.http.HttpResponse;
import org.saai.reservation.ui.dataobjects.OrderUpdate;
import org.saai.reservation.ui.dataobjects.OrdersRequest;
import org.saai.reservation.ui.dataobjects.ServiceResponse;
import org.saai.reservation.ui.utils.CommonUtils;
import org.saai.reservation.ui.utils.Constants;

import com.google.gson.Gson;

public class OrdersClientService {

	public String createNewOrder(OrdersRequest ordersRequest, int seatsCount) {

		String serviceURL = Constants.getServiceurl() + "/orders?seats=" + seatsCount;

		ServiceResponse response = new ServiceResponse();
		CommonUtils util = new CommonUtils();
		Gson gson = new Gson();
		HttpResponse httpResponse = null;
		try {
			httpResponse = util.executeClientPost(serviceURL, gson.toJson(ordersRequest));
		} catch (Exception e) {
			response.setHasErrors(true);
			response.setErrorList(new String[] { e.getMessage() });
			return gson.toJson(response);
		}
		util.updateServiceResponse(response, httpResponse);
		return gson.toJson(response);
	}
	
	public String deleteCartSeats(int orderId) {

		String serviceURL = Constants.getServiceurl() + "/orders/"+orderId+"/carts";

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
	
	public String updateExistingOrder(OrderUpdate orderUpdate,int orderId) {

		
		String serviceURL = Constants.getServiceurl() + "/orders/" + orderId;

		ServiceResponse response = new ServiceResponse();
		CommonUtils util = new CommonUtils();
		Gson gson = new Gson();
		HttpResponse httpResponse = null;
		try {
			httpResponse = util.executeClientPut(serviceURL, gson.toJson(orderUpdate));
		} catch (Exception e) {
			response.setHasErrors(true);
			response.setErrorList(new String[] { e.getMessage() });
			return gson.toJson(response);
		}
		util.updateServiceResponse(response, httpResponse);
		return gson.toJson(response);
	}
	
	

}
