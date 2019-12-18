package com.sumair.fetchusersservice;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.sumair.fetchusersservice.beans.UserInfoObject;
import com.sumair.fetchusersservice.utils.CommonUtils;
import com.sumair.fetchusersservice.utils.Constants;

public class FetchUsersService {

	private final static CloseableHttpClient httpClient = HttpClients.createDefault();


	public static void fetchUsers(String[] args) {

		List<UserInfoObject> userInfoObjList = null;
		HttpGet request = null;

		try {

			request = new HttpGet(Constants.SERVICE_ENDPOINT);
			request.addHeader(HttpHeaders.ACCEPT, Constants.CONTENT_TYPE_APPLICATION_JSON);

			CloseableHttpResponse response = httpClient.execute(request);

			if(response != null && null != response.getStatusLine() && 200 == response.getStatusLine().getStatusCode()) {
				if (response.getEntity() != null) {
					userInfoObjList = CommonUtils.getResponse(EntityUtils.toString(response.getEntity()));
					if(userInfoObjList == null || CommonUtils.isNullOrEmptyCollection(userInfoObjList)) {
						System.out.println("No users found!");
					}else {
						System.out.println("Users found in the London or within 50 miles of London: ");
						for(UserInfoObject userInfo : userInfoObjList) {
							if(CommonUtils.isInside(Constants.LONDON_CENTER_LAT,Constants.LONDON_CENTER_LNG ,Constants.SURROUNDING_RADIUS_IN_MILES + Constants.LONDON_APPROX_RADIUS_IN_MILES, Double.valueOf(userInfo.getLatitude()), Double.valueOf(userInfo.getLongitude()))) {
								System.out.println(userInfo.getId()+" "+userInfo.getFirstName() + " " + userInfo.getLastName());
							}
						}
					}
				}
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

}
