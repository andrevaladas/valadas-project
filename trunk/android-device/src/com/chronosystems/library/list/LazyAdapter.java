package com.chronosystems.library.list;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Location;
import com.chronosystems.library.utils.LocationUtils;
import com.chronosystems.view.R;

public class LazyAdapter extends BaseAdapter {

	private final Entity entity;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader;

	public LazyAdapter(final Activity activity, final Entity data) {
		this.entity = data;
		this.imageLoader = new ImageLoader(activity.getApplicationContext());
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return entity.getDevices().size();
	}

	public Object getItem(final int position) {
		if(getCount() >= position) {
			return entity.getDevices().get(position);
		}
		return null;
	}

	public Entity getEntity() {
		return entity;
	}

	public long getItemId(final int position) {
		return position;
	}

	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View vi = convertView;
		if(convertView==null) {
			vi = inflater.inflate(R.layout.list_row, null);
		}

		final TextView title = (TextView)vi.findViewById(R.id.title); // title
		final TextView description = (TextView)vi.findViewById(R.id.description); // description
		final TextView lastTimeLocation = (TextView)vi.findViewById(R.id.timeline); // timeline
		vi.findViewById(R.id.list_image);

		final Device device = (Device) getItem(position);
		//get the last location
		final Location lastLocation = device.getLocations().get(0);

		// Setting all values in listview
		title.setText(device.getName());
		description.setText(device.getEmail());
		lastTimeLocation.setText(LocationUtils.getTimelineDescrition(lastLocation));
		//imageLoader.DisplayImage(song.get(Utils.KEY_THUMB_URL), thumb_image);
		return vi;
	}
}