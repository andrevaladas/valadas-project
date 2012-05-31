package com.chronosystems.service.tracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.SystemClock;

import com.chronosystems.library.utils.QuickPrefsUtils;
import com.chronosystems.wakeful.WakefulIntentService;

public class TrackListener implements WakefulIntentService.AlarmListener {

	public static TrackUpdateListener updateListener;

	public TrackListener() {
		super();
	}

	public TrackListener(final TrackUpdateListener trackUpdateListener) {
		super();
		updateListener = trackUpdateListener;
	}

	public void scheduleAlarms(final AlarmManager mgr, final PendingIntent pi, final Context ctxt) {
		final int updateInterval = QuickPrefsUtils.getUpdateInterval(ctxt); //minutes
		mgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 60000, updateInterval * 60 * 1000, pi);
	}

	public void sendWakefulWork(final Context ctxt) {
		WakefulIntentService.sendWakefulWork(ctxt, TrackLocationService.class);
	}

	public long getMaxAge() {
		return AlarmManager.INTERVAL_FIFTEEN_MINUTES;
	}
}
