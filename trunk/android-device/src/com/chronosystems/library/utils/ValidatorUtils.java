/**
 * 
 */
package com.chronosystems.library.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author andrevaladas
 * 
 */
public class ValidatorUtils {

	public static boolean isValidEmail(final String email) {
		final Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
		final Matcher emailMatcher = emailPattern.matcher(email);
		return emailMatcher.matches();
	}
}
