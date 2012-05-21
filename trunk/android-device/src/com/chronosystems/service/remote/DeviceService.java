package com.chronosystems.service.remote;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;

public class DeviceService {

	private static String loginURL = "/device/login";
	private static String registerURL = "/device/register";

	private static String searchURL = "/device/search";

	private static String findFollowingURL = "/device/findFollowing";
	private static String findFollowersURL = "/device/findFollowers";

	/**
	 * function make Login Request
	 * */
	public static Entity loginUser(final Device device){
		return WebServiceManager.executeRequest(loginURL, device);
	}

	/**
	 * function make Login Request
	 * */
	public static Entity registerUser(final Device device){
		return WebServiceManager.executeRequest(registerURL, device);
	}

	/**
	 * search devices
	 * */
	public static Entity search(final Entity entity){
		return WebServiceManager.executeRequest(searchURL, entity);
	}

	/**
	 * search following
	 * */
	public static Entity findFollowing(final Entity entity){
		return WebServiceManager.executeRequest(findFollowingURL, entity);
	}

	/**
	 * search followers
	 * */
	public static Entity findFollowers(final Entity entity){
		return WebServiceManager.executeRequest(findFollowersURL, entity);
	}
}
