package com.example.autocomp;

import java.util.ArrayList;

import org.json.JSONArray;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ParseException;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

public class BusFragment extends Fragment implements LocationListener{
	private GoogleMap map;
	BusMapDataBaseHelper dbh=null;
	AutoCompleteTextView autoCompViewFrom=null;
	LocationManager locationManager=null;
	boolean canGetLocation=false;
	Location location =null;
	LatLng np=null;
	LatLng tempOb=null;
	boolean flagLoc=false;
	String startID="";
	
	
	
	double currLat=0.0;
	double currLng=0.0;
	LatLng currObj=null;
	double minDist=0.0;
	TextView info=null;
	TextView info1=null;
	TextView info2=null;
	TextView info3=null;
	ImageView  searchImg=null;
	Marker marker=null;
	Marker _myLocation =null;
	JSONArray FixArray = null;
	ArrayList<LatLng> cord=null;
	ArrayList<String> stopAdapt=null;
	ArrayList<Marker> mark=null;
	public View onCreateView(LayoutInflater inflater , ViewGroup container ,Bundle savedInstanceState )
	  {
		View rootView=inflater.inflate(R.layout.activity_bus_,container,false);  
	//	Toast.makeText(getActivity().getApplicationContext(), "WHOA BUS", Toast.LENGTH_LONG).show(); 

		return rootView;

	  }
/*	public void onResume()
	{
		Toast.makeText(getActivity().getApplicationContext(), "WHOA RESUME BUS", Toast.LENGTH_LONG).show(); 

		
	}*/
	/*
	public void onCreate(Bundle savedInstanceState) {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		currLat =((ActivityFirst) getActivity()).getcurrLat();
		currLng=((ActivityFirst) getActivity()).getcurrLong();		
		
		
		map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.mapsStop))
				.getMap();
		map.setMyLocationEnabled(true);
		map.setOnCameraChangeListener(getCameraChangeListener());

		autoCompViewFrom = (AutoCompleteTextView) getView().findViewById(R.id.autocompleteBus);
		
		dbh=new BusMapDataBaseHelper(getActivity());
		if(currLat == 0.0 || currLng == 0.0)
		{
		location=getLocation();
		currLat=location.getLatitude();
		currLng=location.getLongitude();
		}
		
		
		
		
		Log.i("Radius","currLat-->"+currLat+"currLng->"+currLng);
		 currObj=new LatLng(currLat,currLng);

		_myLocation = map.addMarker(new MarkerOptions()
		.position(currObj)
		.title("My Location")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.center)));
		
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(currObj, 15));
		
		
		map.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoWindow(Marker arg0) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {
				View v = getActivity().getLayoutInflater().inflate(R.layout.busmark, null);
			
				
				
				info= (TextView) v.findViewById(R.id.info);
			

				
				String titleText=marker.getTitle();
				String [] titleArr=titleText.split("\\$");
				String stopid=titleArr[1];
				String name=titleArr[0];
			
				info.setText(name);
				
				
				return v;
			}
		});
		
		
		
		
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker markerClick) {
				
				
				String titleText=markerClick.getTitle();
				String [] titleArr=titleText.split("\\$");
				String stopid=titleArr[1];
				Bundle bundle = new Bundle();
            	
            	bundle.putString("initId", stopid);
            	
            	
               Intent intent = new Intent(getActivity(),BusMarkAct.class);
               intent.putExtras(bundle);
               startActivity(intent);
				
			}

		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	 
		
		try {
			dbh.createDataBase();
		} catch (IOException e) {
			Toast.makeText(getActivity(), "calling getbusstops-> e"+ e.toString(), Toast.LENGTH_LONG).show();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			dbh.openDataBase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getActivity(), "openDataBase->"+e.toString(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		
		
	
		
		cord=new ArrayList<LatLng>(); 
		FixArray=new JSONArray();
		FixArray=dbh.testFunction();
		setData();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,stopAdapt);
		
		autoCompViewFrom.setAdapter(adapter);
		
		autoCompViewFrom.setOnItemClickListener(new OnItemClickListener() {
			LatLng stopLtLn=null;
			JSONObject stopObj=new JSONObject();
	        @Override
	        public void onItemClick(AdapterView<?> parent, View arg1, int pos,
	                long id) {
	        String	source1 = autoCompViewFrom.getText().toString();
	        	for(int i=0;i < FixArray.length();i++)
	    		{

	    			String stopn="";

	    			try {
	    				stopObj= FixArray.getJSONObject(i);
	    			} catch (JSONException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    			try {
	    			
	    				stopLtLn=(LatLng) stopObj.get("stopLtLn");
	    				stopn=(String)stopObj.get("stopName");
	    		
	    				if(source1.equals(stopn))
	    				{
	    			
	    					
	    					
	    					map.moveCamera(CameraUpdateFactory.newLatLngZoom(stopLtLn, 15));
	    					
	    				}
	    			} catch (JSONException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    		}
	        	
	     
	        	

	        }
	    });
		
		
		
		
		
		searchImg = (ImageView) getView().findViewById(R.id.srchImg);
		
		
		 searchImg.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v){
			    	String
			    	source = autoCompViewFrom.getText().toString();
					for(int i=0;i < FixArray.length();i++)
					{	
						String stopn="";
						JSONObject 	stopObj = null;
						try {
							stopObj= FixArray.getJSONObject(i);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							
							stopn=(String)stopObj.get("stopName");
							
							
							if(source.equals(stopn))
							{
								
								
								startID=(String)stopObj.get("startingID");
							  
								Intent intent = new Intent(getActivity(),BusMarkAct.class);
								intent.putExtra("initId", startID);
								startActivity(intent);
							
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
			    }
			 });
		
		
		
		
		addBusMarkers(currObj);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public void setData()
	{
		stopAdapt=new ArrayList<String>();
		JSONObject stopDet=null;
		String stopName=null;
		for(int i=0;i < FixArray.length();i++)
		{
			
			

			try {
				stopDet= FixArray.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try 
			{
				stopName= (String)stopDet.get("stopName");
				stopAdapt.add(stopName);

				
				
				
			} catch (JSONException e) {
				Toast.makeText(getActivity(), "FixArray JSONException->"+ e.toString(), Toast.LENGTH_LONG).show();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			
			
			
		}
		
	}

	public void addBusMarkers(LatLng cur)
	{
		
		
		
		double distance=0.0;
		double stopLat=0.0;
		double stopLng=0.0;
		
		String displayStop="";
		String dispStop="";
		LatLng stopLtLn = null;
		boolean flag=false;
		LatLng tempOb=null;
		double curLat=cur.latitude;
		double curLng=cur.longitude;
		String stopName="";
		Location locationB = new Location("point B");
		JSONObject stopDet = null;
		String ids="";
		locationB.setLatitude(curLat);
		locationB.setLongitude(curLng);
		


	
	
		for(int i=0;i < FixArray.length();i++)
		{

			String stopn="";

			try {
				stopDet= FixArray.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try 
			{
				stopName= (String)stopDet.get("stopName");
				stopLtLn=(LatLng) stopDet.get("stopLtLn");
				startID=(String)stopDet.get("startingID");

				
				
				
			} catch (JSONException e) {
				Toast.makeText(getActivity(), "FixArray JSONException->"+ e.toString(), Toast.LENGTH_LONG).show();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		//	tempOb=(LatLng)cord.get(i);
			stopLat=stopLtLn.latitude;
			stopLng=stopLtLn.longitude;

			Location locationA = new Location("point A");

			locationA.setLatitude(stopLat);
			locationA.setLongitude(stopLng);

				
			distance = locationA.distanceTo(locationB);
			if(!flag)
			{
			minDist=distance;
	//		displayStop=stopn;
	//		dispStop=displayStop;
			flag=true;
			}
			if(distance < minDist)
				{
				minDist=distance;
				displayStop=stopn;
				
				}
	
			if(distance < 10000)
			{
				
				
				ids=stopName+"$"+startID;
				

				
				
				
			marker = map.addMarker(new MarkerOptions()
			.position(stopLtLn).title(ids).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_nyc_bus)));
				
				
				
			/*
				try{
				mark.add( map.addMarker(new MarkerOptions()
.position(stopLtLn).title(ids).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_nyc_bus))));
				}
				catch(Exception er)
				{
					Toast.makeText(getApplicationContext(), "Marker Exception->"+ er.toString(), Toast.LENGTH_LONG).show();
				}
				
			}

		}
		
	
	}
	*/

	public Location getLocation() {
		boolean isGPSEnabled=false;
		boolean isNetworkEnabled=false;
		
		double latitude=0.0;
		double longitude=0.0;
	    try {
	    	locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
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

	public OnCameraChangeListener getCameraChangeListener()
	{
		return new OnCameraChangeListener() 
		{
			@Override
			public void onCameraChange(CameraPosition position) 
			{
				/*
				double tempDist=0.0;
				if(!flagLoc)
				{
				np=position.target;
				addBusMarkers(np);
				flagLoc=true;
				}
			
				else
				{	
				
				Location locationA = new Location("point A");
				Location locationB = new Location("point B");
				
				locationA.setLatitude(np.latitude);
				locationA.setLongitude(np.longitude);
				LatLng tempLoc=position.target;
				locationB.setLatitude(tempLoc.latitude);
				locationB.setLongitude(tempLoc.longitude);
				
				tempDist=locationA.distanceTo(locationB);
				
				if(tempDist>1000)
				{
					np=position.target;
					_myLocation.setPosition(currObj);
					_myLocation.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.center));
					addBusMarkers(np);
					
				}
				}
			*/
			}
		};
	}
	
	
	
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		int lat = (int) (arg0.getLatitude());
		int lng = (int) (arg0.getLongitude());
		LatLng temp=new LatLng(lat,lng);
		_myLocation.setPosition(currObj);
		_myLocation.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.center));
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

}
