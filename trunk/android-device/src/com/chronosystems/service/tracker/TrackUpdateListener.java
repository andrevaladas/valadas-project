package com.chronosystems.service.tracker;

import android.location.Location;

/**
 * @author andrevaladas
 */
public interface TrackUpdateListener {
	public void update(final Location location, final float distance, final boolean update);
}
