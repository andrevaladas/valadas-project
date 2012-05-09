/**
 * 
 */
package com.chronosystems.service.local;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.chronosystems.core.Error;
import com.chronosystems.entity.Entity;
import com.chronosystems.library.dialog.EntityErrorMessage;

/**
 * @author andrevaladas
 */
public class AsyncService extends AsyncTask<String, String, Entity> {

	// Progress Dialog
	private ProgressDialog pDialog;

	private final Context currentContext;
	private String processMessage = "Loading... please wait";
	private Class<?> backOnError;

	public AsyncService(final Context currentContext) {
		super();
		this.currentContext = currentContext;
	}

	public AsyncService(final Context currentContext, final String processMessage) {
		super();
		this.currentContext = currentContext;
		this.processMessage = processMessage;
	}

	public AsyncService(final Context currentContext, final Class<?> backOnError) {
		super();
		this.currentContext = currentContext;
		this.backOnError = backOnError;
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(currentContext);
		pDialog.setMessage(processMessage);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}

	/**
	 * Execute process...
	 */
	@Override
	protected Entity doInBackground(final String... args) {
		return doInBackground(args);
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 */
	@Override
	protected void onPostExecute(final Entity result) {
		// dismiss the dialog once done
		pDialog.dismiss();
		if (result != null && result.hasErrors()) {
			for (final Error error : result.getErrors()) {
				EntityErrorMessage.show(error, currentContext, backOnError);
			}
		}
	}

	@Override
	public String toString() {
		return "AsyncService [pDialog=" + pDialog + ", currentContext="
				+ currentContext + ", processMessage=" + processMessage + "]";
	}
}
