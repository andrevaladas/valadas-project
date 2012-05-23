package com.chronosystems.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.chronosystems.crop.image.ImageHelper;
import com.chronosystems.library.enumeration.PlacesTypes;
import com.chronosystems.library.horzscroll.MyHorizontalScrollView;
import com.chronosystems.library.horzscroll.MyHorizontalScrollView.SizeCallback;
import com.chronosystems.library.maps.CustomItemizedOverlay;
import com.chronosystems.library.maps.CustomOverlayItem;
import com.chronosystems.library.sliding.SlidingPanel;
import com.chronosystems.library.utils.GpsUtils;
import com.chronosystems.maps.core.OnSingleTapListener;
import com.chronosystems.maps.core.TapControlledMapView;
import com.chronosystems.places.AsyncGooglePlacesService;
import com.chronosystems.places.data.Location;
import com.chronosystems.places.data.Place;
import com.chronosystems.places.data.PlaceFilter;
import com.chronosystems.places.data.PlaceSearch;
import com.chronosystems.places.view.R;
import com.chronosystems.service.local.CheckinService;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

/**
 * @author Andre Valadas
 */
public class PlacesViewActivity extends MapActivity implements OnSingleTapListener, LocationListener {

	private final List<String> typesFilter = new ArrayList<String>();

	// Places filter list
	private final PlacesTypes[] placesTypesList = new PlacesTypes[] {
			PlacesTypes.bank,
			PlacesTypes.bar,
			PlacesTypes.beauty_salon,
			PlacesTypes.cafe,
			PlacesTypes.church,
			PlacesTypes.city_hall,
			PlacesTypes.dentist,
			PlacesTypes.establishment,
			PlacesTypes.food,
			PlacesTypes.gym,
			PlacesTypes.hospital,
			PlacesTypes.night_club,
			PlacesTypes.pharmacy,
			PlacesTypes.police,
			PlacesTypes.restaurant,
			PlacesTypes.store,
			PlacesTypes.university
	};
	private List<String> getPlacesTypesLabel() {
		final List<String> labels = new ArrayList<String>(placesTypesList.length);
		for (final PlacesTypes placeType : placesTypesList) {
			labels.add(placeType.getLabel(getApplicationContext()));
		}
		return labels;
	}

	//sroll control
	private MyHorizontalScrollView scrollView;
	private View filterView;
	private ImageView btnSlide;

	//map properties
	private SlidingPanel menuPanel;
	private TapControlledMapView placesView;
	private MyLocationOverlay myLocationOverlay;
	private LocationManager locationManager;
	private CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay;
	private GeoPoint currentPoint;
	private Drawable defaultPlacesMarker;

	private final CheckinService checkinService = new CheckinService();

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (MyHorizontalScrollView) inflater.inflate(R.layout.horz_scroll_view, null);
		setContentView(scrollView);

		filterView = inflater.inflate(R.layout.place_types_list, null);
		final View mainView = inflater.inflate(R.layout.places_view, null);

		// spinner
		final Spinner spinnerPlaceType = (Spinner) filterView.findViewById(R.id.spinner_place_types);
		// Adapter para implementar o layout customizado de cada item
		final ArrayAdapter<String> placeTypesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getPlacesTypesLabel());
		placeTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPlaceType.setAdapter(placeTypesAdapter);
		//attach the listener to the spinner
		spinnerPlaceType.setOnItemSelectedListener(new FilterOnItemSelectedListener());
		spinnerPlaceType.setSelection(7);//establishment

		btnSlide = (ImageView) mainView.findViewById(R.id.btnPlaceTypes);
		btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView, filterView));

		menuPanel = (SlidingPanel) mainView.findViewById(R.id.panel);
		final Button btnSlide2 = (Button) menuPanel.findViewById(R.id.places_filter);
		btnSlide2.setOnClickListener(new ClickListenerForScrolling(scrollView, filterView));

		final View[] children = new View[] {filterView, mainView};
		// Scroll to placesView (view[1]) when layout finished.
		final int scrollToViewIdx = 1;
		scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));

		// map view
		placesView = (TapControlledMapView) findViewById(R.id.places_view);
		placesView.setBuiltInZoomControls(true);
		placesView.getController().setZoom(14);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);

		myLocationOverlay = new MyLocationOverlay(this, placesView);
		placesView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				placesView.getController().animateTo(myLocationOverlay.getMyLocation());
			}
		});

		// dismiss balloon upon single tap of MapView (iOS behavior)
		placesView.setOnSingleTapListener(this);

		// places marker
		defaultPlacesMarker = getResources().getDrawable(R.drawable.places_pin);

		final ImageButton btnCheckin = (ImageButton) findViewById(R.id.btnCheckin);
		btnCheckin.setOnClickListener(new View.OnClickListener() {
			public void onClick(final View v) {
				checkinService.execute(PlacesViewActivity.this);
			}
		});
	}

	public class FilterOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
			typesFilter.clear();

			//check which spinner triggered the listener
			switch (parent.getId()) {
			//country spinner
			case R.id.spinner_place_types:
				// filterList
				final PlacesTypes placeType = placesTypesList[position];
				typesFilter.add(placeType.toString());
				break;
			}
		}

		public void onNothingSelected(final AdapterView<?> parent) {
			// Do nothing.
		}
	}

	/**
	 * Helper for examples with a HSV that should be scrolled by a menu View's width.
	 */
	static class ClickListenerForScrolling implements OnClickListener {
		HorizontalScrollView scrollView;
		View menu;
		/**
		 * Menu must NOT be out/shown to start with.
		 */
		boolean menuOut = false;

		public ClickListenerForScrolling(final HorizontalScrollView scrollView, final View menu) {
			super();
			this.scrollView = scrollView;
			this.menu = menu;
		}

		public void onClick(final View v) {
			final int menuWidth = menu.getMeasuredWidth();

			// Ensure menu is visible
			menu.setVisibility(View.VISIBLE);

			if (!menuOut) {
				// Scroll to 0 to reveal menu
				final int left = 0;
				scrollView.smoothScrollTo(left, 0);
			} else {
				// Scroll to menuWidth so menu isn't on screen.
				final int left = menuWidth;
				scrollView.smoothScrollTo(left, 0);
			}
			menuOut = !menuOut;
		}
	}

	/**
	 * Helper that remembers the width of the 'slide' button, so that the 'slide' button remains in view, even when the menu is
	 * showing.
	 */
	static class SizeCallbackForMenu implements SizeCallback {
		int btnWidth;
		View btnSlide;

		public SizeCallbackForMenu(final View btnSlide) {
			super();
			this.btnSlide = btnSlide;
		}

		public void onGlobalLayout() {
			btnWidth = btnSlide.getMeasuredWidth();
		}

		public void getViewSize(final int idx, final int w, final int h, final int[] dims) {
			dims[0] = w;
			dims[1] = h;
			final int menuIdx = 0;
			if (idx == menuIdx) {
				dims[0] = w - btnWidth;
			}
		}
	}

	private void executePlaceSearch(final PlaceFilter placeFilter) {
		new AsyncGooglePlacesService(this) {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(defaultPlacesMarker, placesView);
				itemizedOverlay.setShowClose(false);
				itemizedOverlay.setShowDisclosure(true);
				itemizedOverlay.setSnapToCenter(true);
				removeOverlays();
			};

			@Override
			protected void onPostExecute(final PlaceSearch result) {
				super.onPostExecute(result);
				if (result == null) {
					return;
				}
				// updating UI from Background Thread
				runOnUiThread(new Runnable() {
					public void run() {
						final List<Place> placeResult = result.getResult();
						Log.d("PlacesViewActivity", "placesService.onPostExecute: " + placeResult.size());
						for (final Place place : placeResult) {

							final Location location = place.getGeometry().getLocation();
							final GeoPoint placePoint = new GeoPoint((int)(Double.valueOf(location.getLat())*1E6),(int)(Double.valueOf(location.getLng())*1E6));

							// define data point
							final CustomOverlayItem overlayItem = new CustomOverlayItem(
									placePoint,
									place.getName(),
									place.getVicinity(),
									ImageHelper.getBitmapFromUrl(place.getIcon()));

							//add item on overlay
							itemizedOverlay.addOverlay(overlayItem);
							Log.d("YourApp", "onPostExecute : result = " + place.getName());
						}
						placesView.getOverlays().add(itemizedOverlay);
						Log.d("PlacesViewActivity", "placesService.onPostExecute");
					}
				});
			}
		}.execute(placeFilter);
	}

	private void removeOverlays() {
		if (placesView.getOverlays().size() > 0) {
			final List<Overlay> overlaysToRemove = new ArrayList<Overlay>();
			for (final Overlay overlay : placesView.getOverlays()) {
				if (overlay instanceof CustomItemizedOverlay<?>) {
					overlaysToRemove.add(overlay);
				}
			}
			placesView.getOverlays().removeAll(overlaysToRemove);
			placesView.invalidate();
		}
	}

	public void onLocationChanged(final android.location.Location location) {
		final int lat = (int) (location.getLatitude() * 1E6);
		final int lng = (int) (location.getLongitude() * 1E6);
		currentPoint = new GeoPoint(lat, lng);
	}

	public void onProviderDisabled(final String provider) {}
	public void onProviderEnabled(final String provider) {}
	public void onStatusChanged(final String provider, final int status, final Bundle extras) {}

	@Override
	protected void onResume() {
		super.onResume();
		if (myLocationOverlay != null) {
			if (GpsUtils.isEnabled(this)) {
				myLocationOverlay.enableMyLocation();
				myLocationOverlay.enableCompass();
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		checkinService.stopService();
		if (myLocationOverlay != null) {
			myLocationOverlay.disableMyLocation();
			myLocationOverlay.disableCompass();
			locationManager.removeUpdates(this);
		}
	}

	@Override
	protected void onStop() {
		super.onPause();
		checkinService.stopService();
		if (myLocationOverlay != null) {
			myLocationOverlay.disableMyLocation();
			myLocationOverlay.disableCompass();
			locationManager.removeUpdates(this);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		checkinService.stopService();
		if (myLocationOverlay != null) {
			myLocationOverlay.disableMyLocation();
			myLocationOverlay.disableCompass();
			locationManager.removeUpdates(this);
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public boolean onSingleTap(final MotionEvent e) {
		if (placesView.getOverlays().size() > 0) {
			for (final Overlay overlay : placesView.getOverlays()) {
				if (overlay instanceof CustomItemizedOverlay<?>) {
					@SuppressWarnings("unchecked")
					final CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay = (CustomItemizedOverlay<CustomOverlayItem>) overlay;
					itemizedOverlay.hideAllBalloons();
					break;
				}
			}
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_places, menu);
		if (placesView.isSatellite()) {
			final MenuItem item = menu.findItem(R.id.road);
			item.setTitle(getString(R.string.road));
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_home:
			startActivity(new Intent(getApplicationContext(), TabDashboardActivity.class));
			return true;
		case R.id.search_places:
			if (GpsUtils.isEnabled(this)) {
				if (currentPoint == null) {
					android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (lastKnownLocation == null) {
						lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					}
					currentPoint = new GeoPoint((int)(lastKnownLocation.getLatitude()*1E6),(int)(lastKnownLocation.getLongitude()*1E6));;
				}

				final PlaceFilter placeFilter = new PlaceFilter();
				placeFilter.setLocation(currentPoint);
				placeFilter.setLanguage("pt-BR");
				placeFilter.setRadius(500);
				placeFilter.setSensor(true);
				if(!typesFilter.isEmpty()) {
					placeFilter.setType(typesFilter);
				}
				executePlaceSearch(placeFilter);
			}
			return true;
		case R.id.road:
			final String satellite = getString(R.string.satellite);
			if (item.getTitle().equals(satellite)) {
				placesView.setSatellite(true);
				item.setTitle(getString(R.string.road));
				item.setChecked(true);
			} else {
				placesView.setSatellite(false);
				item.setTitle(satellite);
			}
			return true;
		case R.id.toggle:
			placesView.setBuiltInZoomControls(!menuPanel.toggle());
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
