package com.example.autocomp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

public class PlacesAutocompleteActivity extends Activity implements
		OnItemClickListener, LocationListener {

	public String bestProvider;
	List<android.location.Address> addresses;
	android.location.Address address;
	String city = null;
	String country = null;
	String state = null;
	String add = null;
	String time = "";
	String addressLineZero = null;
	String addressLineOne = null;
	String addressLineTwo = null;
	String myLocation = "My Location";
	String source = null;
	String destination = null;
	Button btnSelectDate, btnSelectTime;
	Button go;
	double lat = 0.0;
	double lng = 0.0;
	ImageView searchImg = null;
	ImageView Imgo = null;
	ImageView delSource = null;
	ImageView delDest = null;
	ImageView searchImgTrain = null;
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;
	static int SELECT_ID = 2;
	public int flag = 2;
	TextView arrtv;
	EditText et;
	boolean canGetLocation = false;
	Location location = null;
	TextView deptv;
	AutoCompleteTextView autoCompViewFrom;
	AutoCompleteTextView autoCompViewTo;
	Geocoder start;
	Geocoder end;
	public ProgressDialog pDialog;
	org.w3c.dom.Document doc = null;
	List<Address> sourceAdd;
	ArrayList<LatLng> ar = new ArrayList<LatLng>();
	String date = "";
	Polyline polylin = null;
	LocationManager locationManager;
	JSONArray results = null;
	public int yearSelected, monthSelected, daySelected, hourSelected,
			minuteSelected;
	public int year, month, day, hour, minute;
	// declare the variables to show the date and time whenTime and Date Picker
	// Dialog first appears
	private int mYear, mMonth, mDay, mHour, mMinute;
	double sourceLat = 0;
	double sourceLng = 0;
	double destLat = 0;
	double destLng = 0;

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places_autocomplete);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		actionBarSetup();
		location = getLocation();
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		String finMin = "";

		int finHr = 0;
		if (mMinute < 10) {
			finMin = "0" + mMinute;

		} else {
			finMin = "" + mMinute;
		}
		if (mHour > 12) {
			finHr = mHour - 12;

			time = finHr + ":" + finMin + "pm";
		} else {
			time = mHour + ":" + finMin + "am";
		}

		// btnSelectDate=(Button)findViewById(R.id.buttonSelectDate);
		et = (EditText) findViewById(R.id.editTexts);
		et.setText(time);
		et.setCompoundDrawables(null, null,
				getResources().getDrawable(R.drawable.step_wait), null);
		// btnSelectTime=(Button)findViewById(R.id.buttonSelectTime);
		et.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				flag = 1;
				SELECT_ID = 3;
				// Show the TimePickerDialog
				showDialog(TIME_DIALOG_ID);
				// showDialog(DATE_DIALOG_ID);
			}
		});
		go = (Button) findViewById(R.id.Go);
		// image = (ImageView) findViewById(R.id.btn_ic_red_cross_pressed);
		delSource = (ImageView) findViewById(R.id.delsrc);
		delSource.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				autoCompViewFrom.setText("");
			}
		});

		delDest = (ImageView) findViewById(R.id.deldest);
		delDest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				autoCompViewTo.setText("");
			}
		});

		go.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				getLatLng();
			}
		});

		autoCompViewFrom = (AutoCompleteTextView) findViewById(R.id.autocompleteFrom);
		autoCompViewFrom.setAdapter(new PlacesAutoCompleteAdapter(this,
				R.layout.list_item));
		autoCompViewFrom.setOnItemClickListener(this);
		// autoCompViewFrom.setText("Vashi, Sector 2, Navi Mumbai, Maharashtra, India");

		if (address != null)
			autoCompViewFrom.setText(address.toString());
		autoCompViewTo = (AutoCompleteTextView) findViewById(R.id.autocompleteTo);
		autoCompViewTo.setAdapter(new PlacesAutoCompleteAdapter(this,
				R.layout.list_item));
		autoCompViewTo.setOnItemClickListener(this);
		// autoCompViewTo.setText("Carter Road, Union Park, Pali Hill, Mumbai, Maharashtra, India");

		autoCompViewTo.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});

	}

	public Location getLocation() {
		boolean isGPSEnabled = false;
		boolean isNetworkEnabled = false;

		double latitude = 0.0;
		double longitude = 0.0;
		try {
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			// locationManager = (LocationManager) mContext
			// .getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				this.canGetLocation = true;
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, 1000, 0, this);
					Log.i("Network", "Network Enabled");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER, 1000, 0, this);
						Log.i("GPS", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}

						}
					}
				}
			}

		} catch (Exception e) {
			Log.i("Network", "Network Exception->" + e.toString());
			e.printStackTrace();
		}

		return location;
	}

	// Register DatePickerDialog listener
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		// the callback received when the user "sets" the Date in the
		// DatePickerDialog
		public void onDateSet(DatePicker view, int yearSelected,
				int monthOfYear, int dayOfMonth) {
			year = yearSelected;
			month = monthOfYear;
			day = dayOfMonth;
			Log.i("PlacesAutocompleteActivity", "flag->" + flag);
			if (flag == 0) {
				// arrtv.setText(year+"/"+month+"/"+day+" - "+hour+":"+minute);
			} else if (flag == 1) {
				// deptv.setText(year+"/"+month+"/"+day+" - "+hour+":"+minute);
				// deptv.setText(mYear+"/"+mMonth+"/"+mDay+" - "+hour+":"+minute);

			}

		}
	};

	// Register TimePickerDialog listener
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		// the callback received when the user "sets" the TimePickerDialog in
		// the dialog
		public void onTimeSet(TimePicker view, int hourOfDay, int min) {
			hour = hourOfDay;
			minute = min;

			Log.i("PlacesAutocompleteActivity", "flag->" + flag);
			if (flag == 0) {
				// arrtv.setText(year+"/"+month+"/"+day+" - "+hour+":"+minute);
			} else if (flag == 1) {
				String finMin = "";

				int finHour = 0;
				final Calendar c = Calendar.getInstance();
				// mYear = c.get(Calendar.YEAR);
				// mMonth = c.get(Calendar.MONTH);
				// mDay = c.get(Calendar.DAY_OF_MONTH);
				if (minute < 10) {
					finMin = "0" + minute;

				} else {
					finMin = "" + minute;
				}
				if (hour >= 12) {
					finHour = hour - 12;

					time = finHour + ":" + finMin + "pm";
				} else {
					time = hour + ":" + finMin + "am";
				}

				et.setText(time);
			}

		}
	};

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// create a new DatePickerDialog with values you want to show
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);

			// create a new TimePickerDialog with values you want to show
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);

		}
		return null;
	}

	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		String str = (String) adapterView.getItemAtPosition(position);
	}

	public void getLatLng() {
		source = autoCompViewFrom.getText().toString();
		destination = autoCompViewTo.getText().toString();

		int c = 0;

		String mode = "driving";
		String durText = "";
		Log.i("PlacesAutocompleteActivity", "sourceLat->" + sourceLat
				+ "sourceLng->" + sourceLng + "destLat->" + destLat
				+ "destLng->" + destLng);
		/*
		 * String finMin=""; String finHour=""; if(minute < 10) {
		 * finMin="0"+minute;
		 * 
		 * } else{ finMin=""+minute; } if(hour >= 12) { finHour=hour-12;
		 * 
		 * 
		 * time=finHour+":"+finMin+"pm"; } else { time=hour+":"+finMin+"am"; }
		 */
		++mMonth;
		date = mMonth + "/" + mDay + "/" + mYear;
		// Toast.makeText(getApplicationContext(),
		// "date/time->"+date+",time->"+time, Toast.LENGTH_LONG).show();
		// Toast.makeText(getApplicationContext(),
		// "date/time->"+date+",time->"+time, Toast.LENGTH_LONG).show();
		Intent mapIntent = new Intent(getApplicationContext(), DisplayMap.class);
		Bundle bundle = new Bundle();
		String weaString = getIntent().getStringExtra("wString");
		bundle.putString("wString", weaString);

		bundle.putDouble("sourceLatitude", sourceLat);
		bundle.putDouble("sourceLng", sourceLng);
		bundle.putDouble("destLat", destLat);
		bundle.putDouble("destLng", destLng);
		bundle.putString("source", source);
		bundle.putString("destination", destination);
		bundle.putString("date", date);
		bundle.putString("time", time);
		mapIntent.putExtras(bundle);

		startActivity(mapIntent);

	}

	public void newMap() {
		Intent i = new Intent(getApplicationContext(), DisplayMap.class);
		startActivity(i);
	}

	private void actionBarSetup() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar ab = getActionBar();
			ab.setTitle("Find My Route");

		}
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

		lat = arg0.getLatitude();
		lng = arg0.getLongitude();

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackPressed() {

		this.finish();
		Intent mapIntent = new Intent(getApplicationContext(),
				ActivityStart.class);
		startActivity(mapIntent);
	}

	/*
	 * @Override public void onProviderDisabled(String provider) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void onProviderEnabled(String provider) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void onStatusChanged(String provider, int status, Bundle
	 * extras) { // TODO Auto-generated method stub
	 * 
	 * }
	 */

}