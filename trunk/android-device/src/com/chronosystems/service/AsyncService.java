/**
 * 
 */
package com.chronosystems.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.chronosystems.library.dialog.AlertMessage;

/**
 * @author andrevaladas
 */
public class AsyncService extends AsyncTask<String, String, String> {

	// Progress Dialog
	private ProgressDialog pDialog;

	private final Context currentContext;
	private String message = "Loading... please wait";

	public AsyncService(final Context currentContext) {
		super();
		this.currentContext = currentContext;
	}
	public AsyncService(final Context currentContext, final String message) {
		super();
		this.currentContext = currentContext;
		this.message = message;
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(currentContext);
		pDialog.setMessage(message);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}

	/**
	 * Execute process...
	 */
	@Override
	protected String doInBackground(final String... args) {
		return doInBackground(args);
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 */
	@Override
	protected void onPostExecute(final String message) {
		// dismiss the dialog once done
		pDialog.dismiss();
		if(message != null) {
			AlertMessage.show(message, currentContext);
		}
	}

	@Override
	public String toString() {
		return "AsyncService [context=" + currentContext + "]";
	}
}
