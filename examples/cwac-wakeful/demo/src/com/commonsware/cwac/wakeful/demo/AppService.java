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

import java.util.concurrent.atomic.AtomicInteger;

import android.content.Intent;
import android.util.Log;

import com.commonsware.cwac.wakeful.WakefulIntentService;

public class AppService extends WakefulIntentService {
	static AtomicInteger count = new AtomicInteger(1);
	public AppService() {
		super("AppService");
	}

	@Override
	protected void doWakefulWork(final Intent intent) {
		Log.i("AppService", "I'm awake! I'm awake! (yawn)"+count.getAndIncrement());
	}
}