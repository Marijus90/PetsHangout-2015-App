package eu.luckyj.petshangout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import eu.luckyj.petshangout.helpers.MarkerDataSource;
import eu.luckyj.petshangout.helpers.MyMarkerObject;

/**
 * MapsActivity.java
 * Version Rev 2
 * 24/07/2015
 * @reference https://www.youtube.com/user/505mesngr/videos
 * @reference http://stackoverflow.com/questions/17143129/add-marker-on-android-google-map-via-touch-or-tab
 * @author Marijus Jalinskas, x14125421
 */

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

	Context context = this;
	private GoogleMap mMap;
	MarkerDataSource data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		setUpMapIfNeeded();

		data = new MarkerDataSource(context);
		data.open();

		/**
		 * @reference
		 * https://www.youtube.com/user/505mesngr/videos
		 * http://stackoverflow.com/questions/17143129/add-marker-on-android-google-map-via-touch-or-tab
		 */
		// Display markers from DB onto the screen
		List<MyMarkerObject> marker = data.getMarkers();
		for (int i = 0; i < marker.size(); i++) {
			String[] slatLng = marker.get(i).getPosition().split(",");
			LatLng latLngObject = new LatLng(Double.valueOf(slatLng[0]), Double.valueOf(slatLng[1]));
			mMap.addMarker(new MarkerOptions()
							.title(marker.get(i).getTitle())
							.snippet(marker.get(i).getSnippet())
							.position(latLngObject)
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.markerimg))
			);
		}

		//Trying to check for and to turn on the GPS
		LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		String provider = lm.getBestProvider(new Criteria(), true);
		if (provider == null) {
			onProviderDisabled(provider);
		}
		// data.close(); caused crashing

		/**
		 * @reference
		 * https://www.youtube.com/user/505mesngr/videos
		 */
		//Creating a listener
		mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
			@Override
			public void onMapLongClick(final LatLng latLng) {
				LayoutInflater li = LayoutInflater.from(context);
				final View v = li.inflate(R.layout.layout_alert, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setView(v);
				// Not allowing user to cancel it
				builder.setCancelable(false);

				/**
				 * @author Marijus Jalinskas, x14125421
				 */
				// Setting values for the dialog Create button
				builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String userName = MainActivity.getUserName();
						EditText text = (EditText) v.findViewById(R.id.edit_text_marker_text);
						// Add the marker to the screen
						mMap.addMarker(new MarkerOptions().position(latLng).title(userName).snippet(text.getText().toString()).icon(BitmapDescriptorFactory.fromResource(R.drawable.markerimg)));
						// Add the maker to the DB
						String positionStr = latLng.toString(); // Output string example: "lat/lng: (53.12345678911234,-xxx.xxx)"
						data.addMarker(new MyMarkerObject(userName, text.getText().toString(), convertPosition(positionStr)));
					}

					/**
					 * @author Marijus Jalinskas, x14125421
					 */
					// My method for converting output string to decent string
					private String convertPosition(String positionStr) {
						int i, j;
						String before = positionStr;
						i = before.indexOf("(") + 1;
						j = before.indexOf(")") - 1;
						String after = positionStr.substring(i, j);
						Toast.makeText(context, "Marker placed at: " + after, Toast.LENGTH_SHORT).show();
						return after;
					}
				});
				/**
				 * @reference
				 * https://www.youtube.com/user/505mesngr/videos
				 */
				// Setting values for the dialog Cancel button
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				// Create and show the dialog for inputting information for the marker
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
		//Clicking on balloon deletes the marker
		mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
											  @Override
											  public void onInfoWindowClick(Marker marker) {
												  marker.remove();
												  // Delete marker from database
												  data.deleteMarker(new MyMarkerObject(marker.getTitle(), marker.getSnippet(), marker.getPosition().latitude + "," + marker.getPosition().longitude));
											  }
										  }
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	//Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	/**
	 * @reference
	 * https://www.youtube.com/user/505mesngr/videos
	 */
	public void onProviderDisabled(String provider) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Please Enable The Location Services");
		builder.setCancelable(false);

		// When positive button is clicked
		builder.setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent enableGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(enableGPS);
			}
		});

		//Negative button
		builder.setNegativeButton("Don't Enable", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void setUpMap() {
		// Removed addMarker method from here
		mMap.setMyLocationEnabled(true);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
	// Center view on Dublin method has been removed in Revision 2
	// A button to zoom in current users position was added instead
	}

	//Clearing the map before closing
	@Override
	public void onStop() {
		mMap.clear();
		super.onStop();
	}
}