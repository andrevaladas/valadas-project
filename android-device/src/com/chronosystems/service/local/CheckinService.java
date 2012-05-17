/**
 * 
 */
package com.chronosystems.service.local;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Location;
import com.chronosystems.gps.GpsLocationService;
import com.chronosystems.library.dialog.ErrorMessage;
import com.chronosystems.library.dialog.SuccessMessage;
import com.chronosystems.library.utils.GpsUtils;
import com.chronosystems.library.utils.LocationUtils;
import com.chronosystems.service.remote.LocationService;
import com.chronosystems.view.R;

/**
 * @author Andre Valadas
 *
 */
public class CheckinService {

	GpsLocationService locationService;

	/**
	 * Checkin Location
	 */
	public void execute(final Activity parent) {
		// Verifica se GPS está ativo
		if (GpsUtils.checkConfiguration(parent)){

			// param to set location
			final Device localDevice = new Device();

			// Cria mensagem de confirmação do usuário para checkin
			final AlertDialog.Builder confirm = new AlertDialog.Builder(parent);
			confirm.setTitle(parent.getString(R.string.confirmCheckin));
			confirm.setIcon(R.drawable.info);
			confirm.setPositiveButton(parent.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(final DialogInterface dialog, final int id) {
					// force shutdown service
					locationService.stopService();

					new AsyncService(parent) {
						@Override
						protected Entity doInBackground(final String... args) {
							// get current user and set current location
							final Device currentUser = UserFunctions.getCurrentUser(parent.getApplicationContext());
							currentUser.setLocations(localDevice.getLocations());

							// send data to server
							return LocationService.checkinLocation(currentUser);
						}
						@Override
						protected void onPostExecute(final Entity result) {
							super.onPostExecute(result);
							if(result != null && !result.hasErrors()) {
								SuccessMessage.show(parent.getString(R.string.checkinSuccess), parent);
							} else {
								ErrorMessage.show(parent.getString(R.string.checkinError), parent);
							}
						}
					}.execute();
				}
			});
			confirm.setNegativeButton(parent.getString(R.string.no), new DialogInterface.OnClickListener() {
				public void onClick(final DialogInterface dialog, final int id) {
					dialog.cancel();
					// force shutdown service
					locationService.stopService();
				}
			});

			//get GPS service
			locationService = new GpsLocationService((LocationManager) parent.getSystemService(Context.LOCATION_SERVICE));

			// Chama servico de localização do GPS
			new AsyncService(parent, "Checking your location...") {
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					locationService.startService();
				}

				@Override
				protected Entity doInBackground(final String... args) {

					//search for current local for checkin
					final android.location.Location checkin = locationService.getCurrentLocation();
					if (checkin != null) {

						//create a location checkin
						final Location location = new Location(
								checkin.getLatitude(),
								checkin.getLongitude());
						localDevice.addLocation(location);

						// busca dados da localizacao
						final String geocoderAddress = LocationUtils.getGeocoderAddress(location, parent.getApplicationContext());
						confirm.setMessage(geocoderAddress);
					}

					return null;
				}

				@Override
				protected void onCancelled() {
					super.onCancelled();
					// force shutdown service
					locationService.stopService();
				}

				@Override
				protected void onPostExecute(final Entity result) {
					super.onPostExecute(result);
					// force shutdown service
					locationService.stopService();

					if (!localDevice.getLocations().isEmpty()) {
						// mostra o alerta de confirmação
						confirm.show();
					}
				}
			}.execute();
		}
	}

	public void stopService() {
		if (locationService != null) {
			locationService.stopService();
		}
	}
}
