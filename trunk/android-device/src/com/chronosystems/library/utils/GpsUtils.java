/**
 * 
 */
package com.chronosystems.library.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;

import com.chronosystems.view.R;

/**
 * @author Andre Valadas
 *
 */
public class GpsUtils {

	public static boolean isEnabled(final Activity parent) {
		final LocationManager locationManager = (LocationManager) parent.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static boolean checkConfiguration(final Activity parent) {
		if (!isEnabled(parent)){
			showAlertMessageNoGps(parent);
			return false;
		}
		return true;
	}

	/** Builds an alert message to allow the user the option of enabling GPS */
	private static void showAlertMessageNoGps(final Activity parent) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(parent);
		alert.setTitle(parent.getString(R.string.disableGPS));
		alert.setMessage(parent.getString(R.string.enableGPS));
		alert.setIcon(R.drawable.warning);
		alert.setCancelable(false);

		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int id) {
				/* bring up the GPS settings */
				parent.startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
			}
		});
		alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int id) {
				dialog.cancel();
			}
		});
		alert.show();
	}

}
