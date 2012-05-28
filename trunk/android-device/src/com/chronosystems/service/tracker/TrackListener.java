package com.chronosystems.service.tracker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.SystemClock;

import com.chronosystems.wakeful.WakefulIntentService;

public class TrackListener implements WakefulIntentService.AlarmListener {

	public static Activity mainActivity;
	public static TrackUpdateListener updateListener;
	public static final float minimumDistance = 200f; //meters
	private static final int timerTrack = 3 * 60 * 1000; //minutes

	public TrackListener() {
		super();
	}

	public TrackListener(final Activity activity) {
		super();
		mainActivity = activity;
	}
	public TrackListener(final Activity activity, final TrackUpdateListener trackUpdateListener) {
		super();
		mainActivity = activity;
		updateListener = trackUpdateListener;
	}

	public void scheduleAlarms(final AlarmManager mgr, final PendingIntent pi, final Context ctxt) {
		mgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 60000, timerTrack, pi);
	}

	public void sendWakefulWork(final Context ctxt) {
		WakefulIntentService.sendWakefulWork(ctxt, TrackLocationService.class);
	}

	public long getMaxAge() {
		return AlarmManager.INTERVAL_FIFTEEN_MINUTES;
	}
}
