package com.example.autocomp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ProgressDialog;
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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class TransitFragment extends Fragment implements OnItemClickListener, LocationListener {
	//fragment_transit;
	public String bestProvider;
	List<android.location.Address> addresses;
	android.location.Address address;
	String city=null;
	String country=null;
	String state=null;
	String add=null;
	String addressLineZero=null;
	String addressLineOne=null;
	String addressLineTwo=null;
	String  myLocation="My Location";
	String source=null;
	String destination=null;
	Button btnSelectDate,btnSelectTime;
	Button go;
	double lat=0.0;
	double lng=0.0;
	ImageView  searchImg=null;
	ImageView  Imgo=null;
	ImageView  searchImgTrain=null;
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID=1;
	static  int  SELECT_ID=2;
	public int flag=2;
	TextView arrtv;
	
	boolean canGetLocation=false;
	Location location =null;
	TextView deptv;
	AutoCompleteTextView autoCompViewFrom;
	AutoCompleteTextView autoCompViewTo;
	Geocoder start;
	Geocoder end;
	public ProgressDialog pDialog;
	org.w3c.dom.Document doc=null;
	List<Address> sourceAdd;
	ArrayList<LatLng> ar=new ArrayList<LatLng>(); 
	
		Polyline polylin =null;
		 LocationManager locationManager;
	    JSONArray results = null;
	public  int yearSelected,monthSelected,daySelected,hourSelected,minuteSelected;
	public  int year,month,day,hour,minute;  
	// declare  the variables to show the date and time whenTime and Date Picker Dialog first appears
	private int mYear, mMonth, mDay,mHour,mMinute; 
	 double sourceLat =0;
	  double sourceLng=0;
	  double destLat =0;
	  double destLng=0;
	 
	  	   public View onCreateView(LayoutInflater inflater , ViewGroup container ,Bundle savedInstanceState )
	  {
		View rootView=inflater.inflate(R.layout.activity_places_autocomplete,container,false);  
		return rootView;
	  }		

/*
	  @SuppressWarnings("deprecation")
		@Override
		@TargetApi(14)
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			LayoutInflater inflater;
			
		
			 if (android.os.Build.VERSION.SDK_INT > 9) {
			      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			      StrictMode.setThreadPolicy(policy);
			    }
			 
			 location=getLocation();
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);
		//	btnSelectDate=(Button)findViewById(R.id.buttonSelectDate);
		//	btnSelectTime=(Button)findViewById(R.id.buttonSelectTime);
//			go=(Button)findViewById(R.id.Go);
//			arrtv=(TextView)findViewById(R.id.arrTime);
//			deptv=(TextView)findViewById(R.id.depTime);
			// Set ClickListener on btnSelectDate
			
			btnSelectDate.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					// Show the DatePickerDialog
					flag=0;
					SELECT_ID=2;
					showDialog(TIME_DIALOG_ID);
					showDialog(DATE_DIALOG_ID);
				}
			});

			btnSelectTime.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					flag=1;
					SELECT_ID=3;
					// Show the TimePickerDialog
					showDialog(TIME_DIALOG_ID);
					showDialog(DATE_DIALOG_ID);
				}
			});
	
			
			go.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					
					 
					getLatLng();
				}
			});
			
			try{
			Imgo = (ImageView) getView().findViewById(R.id.srchImg);
			}catch(Exception e)
			{
				Toast.makeText(getActivity(), e.toString()+"JACKAL"+(R.id.srchImg), 20).show();
				
			}
			Imgo.setOnClickListener(new View.OnClickListener() {
				    @Override
				    public void onClick(View v){
				    	getLatLng();
				    }
				 });
			
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
			Criteria criteria = new Criteria();
			Geocoder geocoder =
					new Geocoder(this, Locale.getDefault());

			bestProvider = locationManager.getBestProvider(criteria, false);
			
			if(bestProvider == null)
			{
				//	Toast.makeText(getApplicationContext(),"bestProvider->null", Toast.LENGTH_LONG).show();
			}
			else
			{
				Location location = locationManager.getLastKnownLocation(bestProvider);
			
			
			
				try {
					Log.i("PlacesAutocompleteActivity","before geocoder.getFromLocation");
					addresses=geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
					Log.i("PlacesAutocompleteActivity","after geocoder.getFromLocation");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.i("PlacesAutocompleteActivity","inside catch");
					if(addresses == null)
					{
						//Log.i("PlacesAutocompleteActivity","addresses is null");
					}
					e.printStackTrace();
				}
				catch(NullPointerException e1)
				{
					Log.i("PlacesAutocompleteActivity","addresses is null");
					e1.printStackTrace();
				}
				if (addresses != null && addresses.size() > 0) {
					// Get the first address
					Log.i("PlacesAutocompleteActivity","inside if");
					address = addresses.get(0);
					addressLineZero=address.getAddressLine(0);
					addressLineOne=address.getAddressLine(1);
					addressLineTwo=address.getAddressLine(2);
					add=addressLineZero+","+addressLineOne+","+addressLineTwo;
					Log.i("PlacesAutocompleteActivity","before city");
					city=address.getLocality();
					country=address.getCountryName();
					state=address.getAdminArea();       
					Log.i("PlacesAutocompleteActivity","city->"+city+",country->"+country);
				}

			}
	  
			
			
			searchImg = (ImageView) getView().findViewById(R.id.busImg);
			
			
			 searchImg.setOnClickListener(new View.OnClickListener() {
				    @Override
				    public void onClick(View v){
				    	
				   
				    	Intent mapIntent = new Intent(getActivity(), Bus_Activity.class);
				    	Bundle bun=new Bundle();
				    	bun.putDouble("lat", lat);
				    	bun.putDouble("lng", lng);
				    	mapIntent.putExtras(bun);
				    	startActivity(mapIntent);
				    	
				    	
				    }
				 });
			
			 
			 
			 
			 
			 
			 searchImgTrain = (ImageView) getView().findViewById(R.id.trainImg);
				
				
			 searchImgTrain.setOnClickListener(new View.OnClickListener() {
				    @Override
				    public void onClick(View v){
				    	
				   
				    	Intent mapIntent = new Intent(getActivity(), Radius.class);
				    	Bundle bun=new Bundle();
				    	bun.putDouble("lat", lat);
				    	bun.putDouble("lng", lng);
				    	mapIntent.putExtras(bun);
				    	startActivity(mapIntent);
				    	
				    	
				    }
				 });
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			autoCompViewFrom = (AutoCompleteTextView) getView().findViewById(R.id.autocompleteFrom);
			autoCompViewFrom.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.list_item));
			autoCompViewFrom.setOnItemClickListener(this);
			 
			if(address !=null)
				autoCompViewFrom.setText(address.toString());
			autoCompViewTo = (AutoCompleteTextView) getView().findViewById(R.id.autocompleteTo);
			autoCompViewTo.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.list_item));
			autoCompViewTo.setOnItemClickListener(this);
			autoCompViewTo.setOnItemClickListener(new OnItemClickListener() {
				 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				        // TODO Auto-generated method stub
		
				                        }
				                });


		}
	  */
	  
		public Location getLocation() {
			boolean isGPSEnabled=false;
			boolean isNetworkEnabled=false;
			
			double latitude=0.0;
			double longitude=0.0;
		    try {
		    	locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		        //locationManager = (LocationManager) mContext
		         //       .getSystemService(LOCATION_SERVICE);

		    	
		    	
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
		                        LocationManager.NETWORK_PROVIDER,
		                        1000,
		                        0, this);
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
		                            LocationManager.GPS_PROVIDER,
		                            1000,
		                            0, this);
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
		    	Log.i("Network", "Network Exception->"+e.toString());
		        e.printStackTrace();
		    }

		    return location;
		}
		public void getLatLng()
		{
			source = autoCompViewFrom.getText().toString();
			destination=autoCompViewTo.getText().toString();
		
	    	
	    	  int c=0;

	    		
	    		
			String mode="driving";
			String durText="";
			Log.i("PlacesAutocompleteActivity","sourceLat->"+sourceLat+"sourceLng->"+sourceLng+"destLat->"+destLat+"destLng->"+destLng); 
			Intent mapIntent = new Intent(getActivity(), DisplayMap.class);
			Bundle bundle = new Bundle();
			bundle.putDouble("sourceLatitude", sourceLat);
			bundle.putDouble("sourceLng", sourceLng);
			bundle.putDouble("destLat", destLat);
			bundle.putDouble("destLng", destLng);
			bundle.putString("source", source);
			bundle.putString("destination", destination);
			
			mapIntent.putExtras(bundle);
		
			
			startActivity(mapIntent);
		
		

		}


		public void newMap()
		{
			Intent i = new Intent(getActivity(), DisplayMap.class);
			startActivity(i);
		}
			
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		 lat=location.getLatitude();
		 lng=location.getLongitude();
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
