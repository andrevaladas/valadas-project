/**
 * 
 */
package com.chronosystems.library.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;

import com.chronosystems.entity.Location;
import com.chronosystems.library.maps.CustomItemizedOverlay;
import com.chronosystems.library.maps.CustomOverlayItem;
import com.chronosystems.maps.core.TapControlledMapView;
import com.chronosystems.view.R;
import com.google.android.maps.GeoPoint;

/**
 * @author Andre Valadas
 *
 */
public class LocationUtils {

	public static String getGeocoderAddress(final android.location.Location location, final Context currentContext) {
		// create a point
		final GeoPoint point = new GeoPoint((int)(location.getLatitude()*1E6),(int)(location.getLongitude()*1E6));
		return getGeocoderAddress(point, currentContext);
	}
	public static String getGeocoderAddress(final Location location, final Context currentContext) {
		// create a point
		final GeoPoint point = new GeoPoint((int)(location.getLatitude()*1E6),(int)(location.getLongitude()*1E6));
		return getGeocoderAddress(point, currentContext);
	}
	public static String getGeocoderAddress(final GeoPoint point, final Context currentContext) {
		// find address location by Geocoder
		final Geocoder geoCoder = new Geocoder(currentContext, Locale.getDefault());
		String addressValue = "";
		try {
			final List<Address> addresses = geoCoder.getFromLocation(point.getLatitudeE6() / 1E6, point.getLongitudeE6() / 1E6, 1);
			if (addresses.size() > 0) {
				final Address address = addresses.get(0);
				for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
					addressValue += address.getAddressLine(i) + " ";
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if(addressValue.isEmpty()) {
				addressValue = "Endereço não encontrado.";
			}
		}
		return addressValue;
	}

	public static CustomItemizedOverlay<CustomOverlayItem> getItemizedOverlay(final Location location, final boolean isLastLocation, final TapControlledMapView mapView, final Resources resources) {
		final CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(getMarker(location, resources, isLastLocation), mapView);
		// set iOS behavior attributes for overlay
		itemizedOverlay.setShowClose(false);
		itemizedOverlay.setShowDisclosure(true);
		itemizedOverlay.setSnapToCenter(true);
		return itemizedOverlay;
	}

	public static Drawable getMarker(final Location location, final Resources resources, final boolean isLastLocation) {
		if (!isLastLocation) {
			final Long timeline = location.getDateInTime();
			/* Days */
			final long days = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()-timeline);
			if(days > 0) {
				return resources.getDrawable(R.drawable.marker_red);
			}
			/* Hours */
			final long hours = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis()-timeline);
			if (hours > 0) {
				/** between 12/24 hrs */
				if (hours > 12) {
					return resources.getDrawable(R.drawable.marker_green);
				}
				/** between 3/12 hrs */
				if (hours > 3) {
					return resources.getDrawable(R.drawable.marker_green);
				}
				/** between 1/3 hrs */
				return resources.getDrawable(R.drawable.marker_green);
			}
			/* Minutes */
			final long minutes = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()-timeline);
			if (minutes > 3) {
				/** between 30/60 min */
				if (minutes > 30) {
					return resources.getDrawable(R.drawable.marker_green);
				}
				/** between 15/30 min */
				if (minutes > 15) {
					return resources.getDrawable(R.drawable.marker_green);
				}
				/** between 3/15 min */
				return resources.getDrawable(R.drawable.marker_green);
			}
			/* less than 3 minutes, now! */
			return resources.getDrawable(R.drawable.marker);
		}
		/* lastLocation */
		//final Bitmap bitmap = ImageHelper.getRoundedBitmap(location.getDevice().getImage());
		//return new BitmapDrawable(ImageHelper.getResizedBitmap(bitmap, 15, 15));
		return resources.getDrawable(R.drawable.marker_lastlocation);
	}

	public static String getTimelineDescrition(final Location location) {
		final Long timeline = location.getDateInTime();
		//Days
		final long days = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()-timeline);
		if(days > 0) {
			return new SimpleDateFormat("dd MMM").format(new Date(timeline));
		}
		//Hours
		final long hours = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis()-timeline);
		if (hours > 0) {
			return String.format("%sh", hours);
		}
		//Minutes
		final long minutes = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()-timeline);
		if (minutes > 0) {
			return String.format("%sm", minutes);
		}
		//Seconds
		final long seconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-timeline);
		if (seconds > 0) {
			return String.format("%ss", seconds);
		}
		return null;
	}

	public static String getDateTimeDescrition(final Location location) {
		return new SimpleDateFormat("dd MMM HH:mm:ss").format(new Date(location.getDateInTime()));
	}

}
