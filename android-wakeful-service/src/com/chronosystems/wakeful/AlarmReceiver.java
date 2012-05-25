package com.chronosystems.wakeful;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.XmlResourceParser;

import com.chronosystems.wakeful.WakefulIntentService.AlarmListener;

public class AlarmReceiver extends BroadcastReceiver {
	private static final String WAKEFUL_META_DATA = "com.chronosystems.wakeful";

	@Override
	public void onReceive(final Context ctxt, final Intent intent) {
		final AlarmListener listener = getListener(ctxt);

		if (listener != null) {
			if (intent.getAction() == null) {
				final SharedPreferences prefs = ctxt.getSharedPreferences(WakefulIntentService.NAME, 0);
				prefs.edit().putLong(WakefulIntentService.LAST_ALARM,System.currentTimeMillis()).commit();

				listener.sendWakefulWork(ctxt);
			} else {
				WakefulIntentService.scheduleAlarms(listener, ctxt, true);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private WakefulIntentService.AlarmListener getListener(final Context ctxt) {
		final PackageManager pm = ctxt.getPackageManager();
		final ComponentName cn = new ComponentName(ctxt, getClass());

		try {
			final ActivityInfo ai = pm.getReceiverInfo(cn, PackageManager.GET_META_DATA);
			final XmlResourceParser xpp = ai.loadXmlMetaData(pm, WAKEFUL_META_DATA);

			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {
					if (xpp.getName().equals("WakefulIntentService")) {
						final String clsName = xpp.getAttributeValue(null, "listener");
						final Class<AlarmListener> cls = (Class<AlarmListener>) Class.forName(clsName);
						return (cls.newInstance());
					}
				}

				xpp.next();
			}
		} catch (final NameNotFoundException e) {
			throw new RuntimeException("Cannot find own info???", e);
		} catch (final XmlPullParserException e) {
			throw new RuntimeException("Malformed metadata resource XML", e);
		} catch (final IOException e) {
			throw new RuntimeException("Could not read resource XML", e);
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException("Listener class not found", e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException("Listener is not public or lacks public constructor", e);
		} catch (final InstantiationException e) {
			throw new RuntimeException("Could not create instance of listener", e);
		}

		return (null);
	}
}