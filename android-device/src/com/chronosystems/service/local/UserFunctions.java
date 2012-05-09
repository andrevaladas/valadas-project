package com.chronosystems.service.local;

import android.content.Context;

import com.chronosystems.entity.Device;

public class UserFunctions {

	/**
	 * Getting user data from database
	 * */
	public static Device getCurrentUser(final Context context){
		return new DatabaseHandler(context).getCurrentUser();
	}

	/**
	 * Function get Login status
	 * */
	public static boolean isUserLoggedIn(final Context context){
		final DatabaseHandler db = new DatabaseHandler(context);
		final int count = db.getRowCount();
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
	public static boolean logoutUser(final Context context){
		final DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}

}
