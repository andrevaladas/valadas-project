/**
 * 
 */
package com.chronosystems.service.local;

import android.content.Context;
import android.os.AsyncTask;

import com.chronosystems.core.Error;
import com.chronosystems.entity.Entity;
import com.chronosystems.library.dialog.EntityErrorMessage;

/**
 * @author andrevaladas
 */
public class AsyncSimpleTask extends AsyncTask<String, String, Entity> {

	private final Context currentContext;
	private Class<?> backOnError;

	public AsyncSimpleTask(final Context currentContext) {
		super();
		this.currentContext = currentContext;
	}

	public AsyncSimpleTask(final Context currentContext, final Class<?> backOnError) {
		super();
		this.currentContext = currentContext;
		this.backOnError = backOnError;
	}

	/**
	 * Before starting background thread
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	/**
	 * Execute process...
	 */
	@Override
	protected Entity doInBackground(final String... args) {
		return doInBackground(args);
	}

	/**
	 * After completing background task
	 */
	@Override
	protected void onPostExecute(final Entity result) {
		// dismiss the dialog once done
		if (result != null && result.hasErrors()) {
			for (final Error error : result.getErrors()) {
				EntityErrorMessage.show(error, currentContext, backOnError);
			}
		}
	}
}
