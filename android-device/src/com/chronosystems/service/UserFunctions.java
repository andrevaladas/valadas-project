package com.chronosystems.service;

import android.content.Context;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;

public class UserFunctions {

	//Wifi = 192.168.0.101
	//Dev  = 10.0.2.2

	private static String loginURL = "http://10.0.2.2:8888/android-service/rest/device/login";
	private static String registerURL = "http://10.0.2.2:8888/android-service/rest/device/register";

	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public static Entity loginUser(String email, String password){
		// Building Parameters
		final Device device = new Device(); 
		device.setEmail(email);
		device.setPassword(password);
		
		// getting Entity Object
		final Entity entity = RestClient.executeRequest(loginURL, device);
		return entity;
	}
	
	

	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public static Entity registerUser(String name, String email, String password){
		// Building Parameters
		final Device device = new Device(); 
		device.setName(name);
		device.setEmail(email);
		device.setPassword(password);

		// getting Entity Object
		final Entity entity = RestClient.executeRequest(registerURL, device);
		return entity;
	}
	
	/**
	 * Function get Login status
	 * */
	public static boolean isUserLoggedIn(Context context){
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
	public static boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}
	
}
