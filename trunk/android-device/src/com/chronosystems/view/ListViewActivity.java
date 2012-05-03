package com.chronosystems.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.library.list.LazyAdapter;
import com.chronosystems.service.UserFunctions;

public class ListViewActivity extends Activity {
	ListView list;
	LazyAdapter adapter;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		final List<Device> deviceList = new ArrayList<Device>();
		final Entity entity = UserFunctions.findAllUsers();

		// looping through all devices
		for (final Device device : entity.getDevices()) {
			deviceList.add(device);
		}

		list = (ListView)findViewById(R.id.list);

		// Getting adapter by passing xml data ArrayList
		adapter = new LazyAdapter(ListViewActivity.this, deviceList);
		list.setAdapter(adapter);

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
				if(parent.getAdapter() instanceof LazyAdapter) {
					final LazyAdapter adapter = (LazyAdapter)parent.getAdapter();
					final Intent i = new Intent(getApplicationContext(), MapViewActivity.class);
					final Device device = (Device) adapter.getItem(position);
					i.putExtra("device", device);
					startActivity(i);
				}
			};
		});
	}
}