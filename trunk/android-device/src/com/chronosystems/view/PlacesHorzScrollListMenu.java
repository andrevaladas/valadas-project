package com.chronosystems.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chronosystems.library.horzscroll.MyHorizontalScrollView;
import com.chronosystems.library.horzscroll.MyHorizontalScrollView.SizeCallback;
import com.google.android.maps.MapActivity;

/**
 * @author Andre Valadas
 */
public class PlacesHorzScrollListMenu extends MapActivity {
	// Lista de Estados que será exibida
	private final String[] ESTADOS = new String[] {
			"Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", "Distrito Federal",
			"Espírito Santo", "Goiás", "Maranhão", "Mato Grosso", "Mato Grosso do Sul",
			"Minas Gerais", "Pará", "Paraíba", "Paraná", "Pernambuco", "Piauí", "Rio de Janeiro",
			"Rio Grande do Norte", "Rio Grande do Sul", "Rondônia", "Roraima", "Santa Catarina",
			"São Paulo", "Sergipe", "Tocantins"
	};

	private MyHorizontalScrollView scrollView;
	private ImageView btnSlide;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (MyHorizontalScrollView) inflater.inflate(R.layout.horz_scroll_with_list_menu, null);
		setContentView(scrollView);

		final View filterList = inflater.inflate(R.layout.place_types_list, null);
		final View placesView = inflater.inflate(R.layout.places_view, null);

		final ListView listViewPlaceTypes = (ListView) filterList.findViewById(R.id.place_types_list);
		// Adapter para implementar o layout customizado de cada item
		final ArrayAdapter<String> placeTypesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1) {
			@Override
			public View getView(final int position, final View convertView, final ViewGroup parent) {
				// dataList
				final String estado = ESTADOS[position];

				View view = convertView;
				if(view==null) {
					final LayoutInflater inflater = getLayoutInflater();
					view = inflater.inflate(R.layout.place_types, null);
				}

				// checkbox
				final CheckBox chkPlaceType = (CheckBox) view.findViewById(R.id.chk_place_types);
				chkPlaceType.setTag(estado); //data
				chkPlaceType.setOnClickListener(new View.OnClickListener() {
					public void onClick(final View v) {
						final CheckBox chk = (CheckBox) v;
						final String estado = (String) chk.getTag();
						if(chk.isChecked()) {
							Toast.makeText(getApplicationContext(), "Checbox de " + estado + " marcado!", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), "Checbox de " + estado + " desmarcado!", Toast.LENGTH_SHORT).show();
						}
					}
				});

				// text
				final TextView txv = (TextView) view.findViewById(R.id.tx_place_types);
				txv.setText(estado);
				return view;
			}

			@Override
			public long getItemId(final int position) {
				return position;
			}

			@Override
			public int getCount() {
				return ESTADOS.length;
			}
		};
		listViewPlaceTypes.setAdapter(placeTypesAdapter);

		btnSlide = (ImageView) placesView.findViewById(R.id.btnPlaceTypes);
		btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView, filterList));

		final View[] children = new View[] {filterList, placesView};
		// Scroll to placesView (view[1]) when layout finished.
		final int scrollToViewIdx = 1;
		scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));
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

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
