package com.chronosystems.library.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;

import com.chronosystems.R;
import com.chronosystems.crop.image.ImageHelper;
import com.chronosystems.entity.Location;
import com.chronosystems.library.maps.CustomItemizedOverlay;
import com.chronosystems.library.maps.CustomOverlayItem;
import com.chronosystems.maps.core.TapControlledMapView;
import com.google.android.maps.GeoPoint;

/**
 * @author Andre Valadas
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

	public static CustomItemizedOverlay<CustomOverlayItem> getItemizedOverlay(final Location location, final TapControlledMapView mapView, final Resources resources) {
		final Bitmap marker = getMarker(location, resources);
		final BitmapDrawable bitmapDrawable = new BitmapDrawable(ImageHelper.adjustOpacity(marker, 255));//no effect
		final CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(bitmapDrawable, mapView);
		// set iOS behavior attributes for overlay
		itemizedOverlay.setShowClose(false);
		itemizedOverlay.setShowDisclosure(true);
		itemizedOverlay.setSnapToCenter(true);
		return itemizedOverlay;
	}

	private static Bitmap getMarker(final Location location, final Resources resources) {
		final Long timeline = location.getDateInTime();
		/* Days */
		final long days = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()-timeline);
		if(days > 0) {
			/** after 5 days */
			if (days > 5) {
				return BitmapFactory.decodeResource(resources,R.drawable.light_gray_sphere);
			}
			/** after 3 days */
			if (days > 3) {
				return BitmapFactory.decodeResource(resources,R.drawable.dark_gray_sphere);
			}
			/** between 1-3 days */
			return BitmapFactory.decodeResource(resources,R.drawable.green_1_sphere);
		}
		/* Hours */
		final long hours = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis()-timeline);
		if (hours > 0) {
			/** between 12-24 hrs */
			if (hours > 12) {
				return BitmapFactory.decodeResource(resources,R.drawable.blue_sphere);
			}
			/** between 1-12 hrs */
			return BitmapFactory.decodeResource(resources,R.drawable.green_sphere);
		}
		/* less than 1 hour! */
		return BitmapFactory.decodeResource(resources,R.drawable.red_sphere);
		/* lastLocation */
		//final Bitmap bitmap = ImageHelper.getRoundedBitmap(location.getDevice().getImage());
		//return new BitmapDrawable(ImageHelper.getResizedBitmap(bitmap, 15, 15));
		//return resources.getDrawable(R.drawable.red_sphere);
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
		return "now";
	}

	public static String getDateTimeDescrition(final Location location) {
		return new SimpleDateFormat("dd MMM HH:mm:ss").format(new Date(location.getDateInTime()));
	}

}
