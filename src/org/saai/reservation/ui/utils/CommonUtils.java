package org.saai.reservation.ui.utils;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.saai.reservation.ui.dataobjects.ServiceResponse;
import org.saai.reservation.ui.exceptions.ServiceException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CommonUtils {

	public static final String CHARACTER_SET_ISO_8859_1 = "ISO-8859-1";
	public static final String ERROR_MSG = "ERROR_MSG";

	public HttpResponse executeClientPost(String url, String json) throws Exception {
		org.apache.http.client.HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		request.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
		org.apache.http.entity.StringEntity entity = new StringEntity(json, MediaType.APPLICATION_JSON,
				CHARACTER_SET_ISO_8859_1);
		request.setEntity(entity);
		return client.execute(request);
	}

	public HttpResponse executeClientPut(String url, String json) throws Exception {
		org.apache.http.client.HttpClient client = new DefaultHttpClient();
		HttpPut request = new HttpPut(url);
		request.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
		org.apache.http.entity.StringEntity entity = new StringEntity(json, MediaType.APPLICATION_JSON,
				CHARACTER_SET_ISO_8859_1);
		request.setEntity(entity);
		return client.execute(request);
	}
	
	public HttpResponse executeClientPostWithoutBody(String url) throws Exception {
		org.apache.http.client.HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		request.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
		return client.execute(request);
	}
	
	public HttpResponse executeClientPutWithoutBody(String url) throws Exception {
		org.apache.http.client.HttpClient client = new DefaultHttpClient();
		HttpPut request = new HttpPut(url);
		request.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
		return client.execute(request);
	}

	public HttpResponse executeClientDeleteWithoutBody(String url) throws Exception {
		org.apache.http.client.HttpClient client = new DefaultHttpClient();
		HttpDelete request = new HttpDelete(url);
		request.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
		return client.execute(request);
	}

	public HttpResponse executeClientGet(String url) throws Exception {
		org.apache.http.client.HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		request.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
		return client.execute(request);
	}

	public void httpResponseStatus(HttpResponse httpResponse) throws IOException, ServiceException {

		int status = httpResponse.getStatusLine().getStatusCode();

		switch (status) {
		case HttpStatus.SC_OK: {
			break;
		}
		case HttpStatus.SC_CREATED: {
			break;
		}
		case HttpStatus.SC_NO_CONTENT: {
			break;
		}
		default: {
			throw new ServiceException();
		}
		}
	}

	public void updateServiceResponse(ServiceResponse serviceResponse, HttpResponse httpResponse) {
		try {
			httpResponseStatus(httpResponse);
			serviceResponse.setHasErrors(false);
			serviceResponse.setSuccessMsg(EntityUtils.toString(httpResponse.getEntity()));
		} catch (ServiceException e) {
			serviceResponse.setHasErrors(true);
			String errorMessage = "Unknown Error";
			try {
				String responseJsonString = EntityUtils.toString(httpResponse.getEntity());
				final JsonObject jsonObj = new JsonParser().parse(responseJsonString).getAsJsonObject();
				errorMessage = jsonObj.get("errorMessage").toString();
			} catch (ParseException | IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			serviceResponse.setErrorList(new String[] { errorMessage });
		} catch (IOException e) {
			serviceResponse.setHasErrors(true);
			serviceResponse.setErrorList(new String[] { e.getMessage() });
		}
	}

}
