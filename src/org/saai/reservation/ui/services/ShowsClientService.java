package org.saai.reservation.ui.services;

import org.apache.http.HttpResponse;
import org.saai.reservation.ui.dataobjects.CartTimeoutSeats;
import org.saai.reservation.ui.dataobjects.ServiceResponse;
import org.saai.reservation.ui.utils.CommonUtils;
import org.saai.reservation.ui.utils.Constants;

import com.google.gson.Gson;

public class ShowsClientService {
	
	public String getListOfAllRunningShows() {

		String serviceURL = Constants.getServiceurl() + "/shows";

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
	
	public String getDetailsOfSelectedShow(int showId) {

		String serviceURL = Constants.getServiceurl() + "/shows/"+showId;

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
	
	public String updateCartStatusOfSeat(String seatName,int cartStatus) {

		String serviceURL = Constants.getServiceurl() + "/shows/seats/"+seatName+"/carts/status/"+cartStatus;
		ServiceResponse response = new ServiceResponse();
		CommonUtils util = new CommonUtils();
		Gson gson = new Gson();
		HttpResponse httpResponse = null;
		try {
			httpResponse = util.executeClientPutWithoutBody(serviceURL);
		} catch (Exception e) {
			response.setHasErrors(true);
			response.setErrorList(new String[] { e.getMessage() });
			return gson.toJson(response);
		}
		util.updateServiceResponse(response, httpResponse);
		return gson.toJson(response);
	}
	
	public String clearTimedOutSeatSelections(CartTimeoutSeats cartTimeoutSeats) {

		String serviceURL = Constants.getServiceurl() + "/shows/seats/carts";

		ServiceResponse response = new ServiceResponse();
		CommonUtils util = new CommonUtils();
		Gson gson = new Gson();
		HttpResponse httpResponse = null;
		try {
			httpResponse = util.executeClientPut(serviceURL,gson.toJson(cartTimeoutSeats));
		} catch (Exception e) {
			response.setHasErrors(true);
			response.setErrorList(new String[] { e.getMessage() });
			return gson.toJson(response);
		}
		util.updateServiceResponse(response, httpResponse);
		return gson.toJson(response);
	}
	
	
	public String listAllSeatsByShowTimingId(int showTimingId) {

		String serviceURL = Constants.getServiceurl() + "/shows/timings/"+showTimingId+"/seats/carts";

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
