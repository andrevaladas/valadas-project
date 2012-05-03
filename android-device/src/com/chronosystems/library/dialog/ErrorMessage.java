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
public class ErrorMessage {

	/**
	 * Show error message
	 * @param message
	 * @param currentContext
	 */
	public static void show(final String message, final Context currentContext) {
		final AlertDialog aDialog = new AlertDialog.Builder(currentContext).create();
		aDialog.setTitle("Error");
		aDialog.setMessage(message);
	    // Setting Icon to Dialog
	    aDialog.setIcon(R.drawable.error);
		// Setting OK Button
		aDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// Write your code here to execute after dialog closed
				//Toast.makeText(currentContext, "You clicked on OK", Toast.LENGTH_SHORT).show();
			}
		});
		// Showing Message
		aDialog.show();
	}
}
