package com.chronosystems.library;

import org.json.JSONObject;

import android.content.Context;

import com.chronosystems.entity.Device;

public class UserFunctions {

	private JSONParser jsonParser;
	//Wifi = 192.168.0.101
	//Dev  = 10.0.2.2

	private static String loginURL = "http://192.168.0.101/android-service/rest/device/login";
	private static String registerURL = "http://192.168.0.101/android-service/rest/device/register";

	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password){
		// Building Parameters
		final Device device = new Device(); 
		device.setEmail(email);
		device.setPassword(password);
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, device);
		return json;
	}

	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String name, String email, String password){
		// Building Parameters
		final Device device = new Device(); 
		device.setName(name);
		device.setEmail(email);
		device.setPassword(password);

		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, device);
		// return json
		return json;
	}
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
	
}
