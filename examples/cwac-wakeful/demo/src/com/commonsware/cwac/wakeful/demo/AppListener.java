/***
  Copyright (c) 2009-11 CommonsWare, LLC

  Licensed under the Apache License, Version 2.0 (the "License"); you may
  not use this file except in compliance with the License. You may obtain
  a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.commonsware.cwac.wakeful.demo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.SystemClock;

import com.commonsware.cwac.wakeful.WakefulIntentService;

public class AppListener implements WakefulIntentService.AlarmListener {
	@Override
	public void scheduleAlarms(final AlarmManager mgr, final PendingIntent pi,
			final Context ctxt) {
		mgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				SystemClock.elapsedRealtime()+1000,
				5*1000, pi);
	}

	@Override
	public void sendWakefulWork(final Context ctxt) {
		WakefulIntentService.sendWakefulWork(ctxt, AppService.class);
	}

	@Override
	public long getMaxAge() {
		return AlarmManager.RTC_WAKEUP;
	}
}
