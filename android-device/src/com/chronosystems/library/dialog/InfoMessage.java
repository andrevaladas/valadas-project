package com.chronosystems.library.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.chronosystems.R;

/**
 * @author andrevaladas
 */
public class InfoMessage {

	public static void show(final String message, final Context currentContext) {
		show(message, currentContext, null);
	}

	/**
	 * Show message
	 * @param message
	 * @param currentContext
	 * @param <T>
	 */
	public static <T> void show(final String message, final Context currentContext, final Class<T> backOnError) {
		final AlertDialog aDialog = new AlertDialog.Builder(currentContext).create();
		aDialog.setTitle("Information");
		aDialog.setMessage(message);
		aDialog.setIcon(R.drawable.info);
		aDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int which) {
				// back to view
				if (backOnError != null) {
					currentContext.startActivity(new Intent(currentContext, backOnError));
				}
			}
		});
		aDialog.show();
	}
}
