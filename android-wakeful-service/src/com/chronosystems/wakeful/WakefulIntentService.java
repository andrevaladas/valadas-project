package com.chronosystems.wakeful;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;

abstract public class WakefulIntentService extends IntentService {
	abstract protected void doWakefulWork(Intent intent);

	static final String NAME = "com.chronosystems.wakeful.WakefulIntentService";
	static final String LAST_ALARM = "lastAlarm";
	private static volatile PowerManager.WakeLock lockStatic = null;

	synchronized private static PowerManager.WakeLock getLock(final Context context) {
		if (lockStatic == null) {
			final PowerManager mgr = (PowerManager)context.getSystemService(Context.POWER_SERVICE);

			lockStatic=mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, NAME);
			lockStatic.setReferenceCounted(true);
		}

		return(lockStatic);
	}

	public static void sendWakefulWork(final Context ctxt, final Intent i) {
		getLock(ctxt.getApplicationContext()).acquire();
		ctxt.startService(i);
	}

	public static void sendWakefulWork(final Context ctxt, final Class<?> clsService) {
		sendWakefulWork(ctxt, new Intent(ctxt, clsService));
	}

	public static void scheduleAlarms(final AlarmListener listener, final Context ctxt) {
		scheduleAlarms(listener, ctxt, true);
	}

	public static void scheduleAlarms(final AlarmListener listener, final Context ctxt, final boolean force) {
		final SharedPreferences prefs=ctxt.getSharedPreferences(NAME, 0);
		final long lastAlarm=prefs.getLong(LAST_ALARM, 0);

		if (lastAlarm == 0
				|| force
				|| (System.currentTimeMillis() > lastAlarm && System.currentTimeMillis() - lastAlarm > listener.getMaxAge())) {
			final AlarmManager mgr = (AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
			final Intent i = new Intent(ctxt, AlarmReceiver.class);
			final PendingIntent pi = PendingIntent.getBroadcast(ctxt, 0, i, 0);
			listener.scheduleAlarms(mgr, pi, ctxt);
		}
	}

	public static void cancelAlarms(final Context ctxt) {
		final AlarmManager mgr= (AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
		final Intent i=new Intent(ctxt, AlarmReceiver.class);
		final PendingIntent pi=PendingIntent.getBroadcast(ctxt, 0, i, 0);
		mgr.cancel(pi);
	}

	public WakefulIntentService(final String name) {
		super(name);
		setIntentRedelivery(true);
	}

	@Override
	public int onStartCommand(final Intent intent, final int flags, final int startId) {
		final PowerManager.WakeLock lock=getLock(this.getApplicationContext());

		if (!lock.isHeld() || (flags & START_FLAG_REDELIVERY) != 0) {
			lock.acquire();
		}

		super.onStartCommand(intent, flags, startId);
		return(START_REDELIVER_INTENT);
	}

	@Override
	final protected void onHandleIntent(final Intent intent) {
		try {
			doWakefulWork(intent);
		} finally {
			final PowerManager.WakeLock lock = getLock(this.getApplicationContext());

			if (lock.isHeld()) {
				lock.release();
			}
		}
	}

	public interface AlarmListener {
		void scheduleAlarms(AlarmManager mgr, PendingIntent pi, Context ctxt);

		void sendWakefulWork(Context ctxt);

		long getMaxAge();
	}
}
