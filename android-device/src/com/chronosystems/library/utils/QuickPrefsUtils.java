/**
 * 
 */
package com.chronosystems.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * @author Andre Valadas
 */
public class QuickPrefsUtils {

	private static SharedPreferences getPreferenceManager(final Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	public static void changeLocationUpdate(final Context context) {
		final SharedPreferences preferenceManager = getPreferenceManager(context);
		final Editor edit = preferenceManager.edit();
		edit.putBoolean("location_updates", !preferenceManager.getBoolean("location_updates", false));
		edit.commit();
	}

	public static boolean locationUpdateEnabled(final Context context) {
		return getPreferenceManager(context).getBoolean("location_updates", false);
	}
	public static int getUpdateInterval(final Context context) {
		return Integer.valueOf(getPreferenceManager(context).getString("updates_interval", "3"));
	}
	public static float getMinimumDistance(final Context context) {
		return Float.valueOf(getPreferenceManager(context).getString("minimum_distance", "200"));
	}
	public static boolean showLogUpdates(final Context context) {
		return getPreferenceManager(context).getBoolean("show_log_updates", false);
	}

	public static String getWelcome(final Context context) {
		return getPreferenceManager(context).getString("welcome_message", "Welcome");
	}
}
