package com.sumair.fetchusersservice.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumair.fetchusersservice.beans.UserInfoObject;

public class CommonUtils  {

	public static  Object convertJsonToObject(String jsonString,Class<?> classname) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper objectMapper =null;
		Object convertedObect=null;

		objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


		convertedObect=objectMapper.readValue(jsonString, classname);
		return convertedObect;
	}

	public static boolean isNullOrEmptyCollection(Collection<?> c) {
		if(c == null || c.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}

	public static boolean isInside(Double center_x, Double center_y, Double rad, Double x, Double y) 
	{ 
		// Compare radius of circle with 
		// distance of its center from 
		// given point 
		if ((x - center_x) * (x - center_x) + 
				(y - center_y) * (y - center_y) <= rad * rad) 
			return true; 
		else
			return false; 
	}
	
	public static List<UserInfoObject> getResponse(String inputStream) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			TypeReference<List<UserInfoObject>> typeReference = new TypeReference<List<UserInfoObject>>() {};
			return objectMapper.readValue(inputStream, typeReference);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
