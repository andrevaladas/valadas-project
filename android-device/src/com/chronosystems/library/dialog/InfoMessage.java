/**
 * 
 */
package com.chronosystems.library.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.chronosystems.view.R;

/**
 * @author andrevaladas
 *
 */
public class InfoMessage {

	/**
	 * Show information message
	 * @param message
	 * @param currentContext
	 */
	public static void show(final String message, final Context currentContext) {
		final AlertDialog aDialog = new AlertDialog.Builder(currentContext).create();
		aDialog.setTitle("Information");
		aDialog.setMessage(message);
		aDialog.setIcon(R.drawable.info);
		aDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int which) {
			}
		});
		aDialog.show();
	}
}
