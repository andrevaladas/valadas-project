/**
 * 
 */
package com.chronosystems.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * @author andrevaladas
 */
public class ExecuteActivity extends AsyncTask<String, String, String> {

	// Progress Dialog
	private ProgressDialog pDialog;

	private Context currentContext;

	public ExecuteActivity(Context currentContext) {
		super();
		this.currentContext = currentContext;
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(currentContext);
		pDialog.setMessage("Loading... please wait");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}

	/**
	 * Processing...
	 * */
	protected String doInBackground(String... args) {
		return doInBackground(args);
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	protected void onPostExecute(String file_url) {
		// dismiss the dialog once done
		pDialog.dismiss();
	}

	@Override
	public String toString() {
		return "ExecuteActivity [context=" + currentContext + "]";
	}
}
