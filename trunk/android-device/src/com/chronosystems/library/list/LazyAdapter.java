package com.chronosystems.library.list;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Location;
import com.chronosystems.view.R;

public class LazyAdapter extends BaseAdapter {

	private final List<Device> deviceList;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader;

	public LazyAdapter(final Activity activity, final List<Device> list) {
		this.deviceList = list;
		this.imageLoader = new ImageLoader(activity.getApplicationContext());
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return deviceList.size();
	}

	public Object getItem(final int position) {
		if(getCount() >= position) {
			return deviceList.get(position);
		}
		return null;
	}

	public List<Device> getDeviceList() {
		return deviceList;
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

		final Device device = deviceList.get(position);
		final List<Location> locations = device.getLocations();
		Long timelineInTime = new Date().getTime();
		if (!locations.isEmpty()) {
			final Location location = locations.get(0);
			timelineInTime = location.getTimelineInTime();
		}

		// Setting all values in listview
		title.setText(device.getName());
		description.setText(device.getEmail());
		lastTimeLocation.setText(getTimeLine(timelineInTime));
		//imageLoader.DisplayImage(song.get(Utils.KEY_THUMB_URL), thumb_image);
		return vi;
	}

	private String getTimeLine(final Long timelineInTime) {
		//Days
		final long days = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()-timelineInTime);
		if(days > 0) {
			return new SimpleDateFormat("dd MMM").format(new Date(timelineInTime));
		}
		//Hours
		final long hours = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis()-timelineInTime);
		if (hours > 0) {
			return String.format("%sh", hours);
		}
		//Minutes
		final long minutes = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()-timelineInTime);
		if (minutes > 0) {
			return String.format("%sm", minutes);
		}
		//Seconds
		final long seconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-timelineInTime);
		if (seconds > 0) {
			return String.format("%ss", seconds);
		}
		return null;
	}
}