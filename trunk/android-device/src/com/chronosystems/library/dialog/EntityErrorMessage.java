/**
 * 
 */
package com.chronosystems.library.dialog;

import android.content.Context;

import com.chronosystems.core.ErrorType;

/**
 * @author andrevaladas
 *
 */
public class EntityErrorMessage {

	public static void show(final com.chronosystems.core.Error error, final Context currentContext) {
		show(error, currentContext, null);
	}

	/**
	 * Show message
	 * @param error
	 * @param currentContext
	 * @param <T>
	 */
	public static <T> void show(final com.chronosystems.core.Error error, final Context currentContext, final Class<T> backOnError) {
		if (ErrorType.ERROR.equals(error.getType())) {
			ErrorMessage.show(error.getMessage(), currentContext, backOnError);
		} else if (ErrorType.ALERT.equals(error.getType())) {
			AlertMessage.show(error.getMessage(), currentContext, backOnError);
		} else if (ErrorType.INFO.equals(error.getType())) {
			InfoMessage.show(error.getMessage(), currentContext, backOnError);
		} else if (ErrorType.WARNING.equals(error.getType())) {
			WarningMessage.show(error.getMessage(), currentContext, backOnError);
		} else if (ErrorType.SUCCESS.equals(error.getType())) {
			SuccessMessage.show(error.getMessage(), currentContext, backOnError);
		}
	}
}
