package com.chronosystems.service.remote;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;

public class DeviceService {

	private static String loginURL = "/device/login";
	private static String registerURL = "/device/register";
	private static String findAllURL = "/device/search";

	/**
	 * function make Login Request
	 * */
	public static Entity loginUser(final Device device){
		// getting Entity Object
		return WebServiceManager.executeRequest(loginURL, device);
	}

	/**
	 * function make Login Request
	 * */
	public static Entity registerUser(final Device device){
		// getting Entity Object
		return WebServiceManager.executeRequest(registerURL, device);
	}

	/**
	 * search devices
	 * */
	public static Entity searchDevices(final Entity entity){
		// getting Entity Object
		return WebServiceManager.executeRequest(findAllURL, entity);
	}
}
