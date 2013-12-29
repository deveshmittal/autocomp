package com.example.autocomp;

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
import android.view.Menu;
import android.view.View;
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

public class Radius2 extends Activity implements LocationListener{

	private GoogleMap map;
	DataBaseHelper dbh=null;
	AutoCompleteTextView autoCompViewFrom=null;
	LocationManager locationManager=null;
	boolean canGetLocation=false;
	Location location =null;
	LatLng np=null;
	LatLng tempOb=null;
	boolean flagLoc=false;
	ArrayList<String> stopsDisplay=null;
	String startID="";
	double currLat=0.0;
	double currLng=0.0;
	String displayStop="";
	LatLng currObj=null;
	double minDist=0.0;
	TextView info=null;
	TextView info1=null;
	TextView info2=null;
	TextView info3=null;
	ImageView  searchImg=null;
	Marker marker=null;
	ArrayAdapter<String> adapterSpinner = null;
	Marker _myLocation =null;
	boolean flag=false;
	
	boolean flagStop=false;
	String dispStop=null;
	JSONArray FixArray = null;
	ArrayList<LatLng> cord=null;
	ArrayList<String> stopAdapt=null;
	ArrayList<Marker> mark=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radius);
		mark=new ArrayList<Marker>();
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		 searchImg = (ImageView) findViewById(R.id.srchImg);
		Bundle extras = getIntent().getExtras();
		
		if(extras != null)
		{
			currLat=extras.getDouble("lat");
			currLng=extras.getDouble("lng");
			
		}
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapsStop))
				.getMap();
		map.setMyLocationEnabled(true);
		map.setOnCameraChangeListener(getCameraChangeListener());

	//	autoCompViewFrom = (AutoCompleteTextView) findViewById(R.id.autocompleteFrom1);
		
		dbh=new DataBaseHelper(getApplicationContext());
		
		
	
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
				View v = getLayoutInflater().inflate(R.layout.busmark, null);
			
				
				Toast.makeText(getApplicationContext(),"getInfoContents->", Toast.LENGTH_LONG).show();
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
            	
            	
               Intent intent = new Intent(Radius2.this,BusMarkAct.class);
               intent.putExtras(bundle);
               startActivity(intent);
				
			}

		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	 
		
		try {
			dbh.createDataBase();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "calling getbusstops-> e"+ e.toString(), Toast.LENGTH_LONG).show();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			dbh.openDataBase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "openDataBase->"+e.toString(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		
		
	
		
		cord=new ArrayList<LatLng>(); 
		FixArray=new JSONArray();
		FixArray=dbh.getTrainStops();
		
		setData();
	
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,stopAdapt);
		
		autoCompViewFrom.setAdapter(adapter);
		
		
	/*	
		autoCompViewFrom.setOnItemClickListener(new OnItemClickListener() {
			LatLng stopLtLn=null;
			JSONObject stopObj=new JSONObject();
	        @Override
	        public void onItemClick(AdapterView<?> parent, View arg1, int pos,
	                long id) {
	        	
	        String	source1 = autoCompViewFrom.getText().toString();
	        String stid="";
	        String temptitle="";
	        String tempT="";
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
	    				stid=(String)stopObj.get("startingID");
	    				if(source1.equals(stopn))
	    				{
	    			
	    					temptitle=stopn+"$"+stid;
	    					map.moveCamera(CameraUpdateFactory.newLatLngZoom(stopLtLn, 15));
	    					break;
	    				}
	    			} catch (JSONException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    		}
	        	
	        	
	     for(int j=0;j<mark.size();j++)
	     {
	    	 
	    	 tempT=(mark.get(j)).getTitle();
	    	 if(temptitle.equals(tempT))
	    	 {
	    		 mark.get(j).showInfoWindow();
	    		 break;
	    	 }
	     }
	        

	        }
	    });
		*/
		autoCompViewFrom.setOnItemClickListener(new OnItemClickListener() {
			LatLng stopLtLn=null;
			JSONObject stopObj=new JSONObject();
	        @Override
	        public void onItemClick(AdapterView<?> parent, View arg1, int pos,
	                long id) {
	        	
	        String	source1 = autoCompViewFrom.getText().toString();
	        String stid="";
	        String temptitle="";
	        String tempT="";
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
	    				stid=(String)stopObj.get("startingID");
	    				if(source1.equals(stopn))
	    				{
	    					temptitle=stopn+"$"+stid;
	    					map.moveCamera(CameraUpdateFactory.newLatLngZoom(stopLtLn, 15));
	    					
	    				}
	    			} catch (JSONException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    		}
	        	
	        	 for(int j=0;j<mark.size();j++)
	    	     {
	    	    	 
	    	    	 tempT=(mark.get(j)).getTitle();
	    	    	 if(temptitle.equals(tempT))
	    	    	 {
	    	    		 
	    	    		 mark.get(j).showInfoWindow();
	    	    		 break;
	    	    	 }
	    	     }
	     
	        	

	        }
	    });
		
		/*
		
		searchImg = (ImageView) findViewById(R.id.srchImg);
		
		
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
							  
								Intent intent = new Intent(Radius2.this,BusMarkAct.class);
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
*/
	}
	

	public void addMarkers(LatLng curr)
	{
		
		
		stopsDisplay=new ArrayList<String>();
		map.clear();
		double distance=0.0;
		LatLng stopLtLn = null;
		JSONObject stopDet = null;
		double curLat=curr.latitude;
		double curLng=curr.longitude;
		String stop1="";
		String stop2="";
		String stop3="";
		String dep1="";
		String dep2="";
		String dep3="";
		String stopid1="";
		String stopid2="";
		String stopid3="";
		String ids="";
		for(int i=0;i < FixArray.length();i++)
		{

			String stopn="";

			try {
				stopDet= FixArray.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stopLtLn=(LatLng) stopDet.get("stopLtLn");
				stopn=(String)stopDet.get("stopName");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			Location locationA = new Location("point A");

			locationA.setLatitude(stopLtLn.latitude);
			locationA.setLongitude(stopLtLn.longitude);

			Location locationB = new Location("point B");

			locationB.setLatitude(curLat);
			locationB.setLongitude(curLng);			
			distance = locationA.distanceTo(locationB);
			if(!flag)
			{
			minDist=distance;
			displayStop=stopn;
			dispStop=displayStop;
			flag=true;
			}
			if(distance < minDist)
				{
				minDist=distance;
				displayStop=stopn;
				
				}
			String text=null;
			int diff =0;
			int diff2 =0;
			int diff3 =0;
			if(distance < 10000)
			{
				try {
					/*
					stop1=(String)stopDet.get("stopNm1");
					stop2=(String)stopDet.get("stopNm2");
					stop3=(String)stopDet.get("stopNm3");
					stopid1=(String)stopDet.get("stopid1");
					stopid2=(String)stopDet.get("stopid2");
					stopid3=(String)stopDet.get("stopid3");
					dep1=(String)stopDet.get("depTm1");
					dep2=(String)stopDet.get("depTm2");
					dep3=(String)stopDet.get("depTm3");
					*/
					startID=(String)stopDet.get("startingID");
					
					
				
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		           	 java.util.Date d = null;
		           	java.util.Date da = null;
		           	java.util.Date db = null;
		           	    java.util.Date d1 = null;
		           	try {
		           		Calendar cal = Calendar.getInstance();
		           	  try {
							 d = sdf.parse(dep1);
							 da=sdf.parse(dep2);
							 db=sdf.parse(dep3);
							 d1 = sdf.parse(sdf.format(cal.getTime()));
							  diff = (int) ((d.getTime() - d1.getTime())/ (1000 * 60 ));
							  diff2 = (int) ((da.getTime() - d1.getTime())/ (1000 * 60 ));
							  diff3 = (int) ((db.getTime() - d1.getTime())/ (1000 * 60 ));
						} catch (java.text.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		           	} catch (ParseException ex) {
		           	  
		           	}
					
			
		
		    	//	text=stop1+"/"+diff+"/"+stop2+"/"+diff2+"/"+stop3+"/"+diff3;
		    		
				//	ids=stopn+"/"+startID+"$"+stopid1+"]"+stopid2+"]"+stopid3;
					ids=stopn+"$"+startID;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
	
					e.printStackTrace();
				}
//				marker = map.addMarker(new MarkerOptions()
//				.position(stopLtLn)
//				.title(ids).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_nyc_subway)).snippet(text));
//			stopsDisplay.add(stopn);
			
			
			marker = map.addMarker(new MarkerOptions()
			.position(stopLtLn).title(ids).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_nyc_subway)));
		//	mark.add(marker);
	//	stopsDisplay.add(stopn);
				
				
			}

		}
		
		
		
		adapterSpinner = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,stopsDisplay);
		adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		if(!(dispStop.equals(displayStop)));
		dispStop=displayStop;


	
	}
	
	public void setData()
	{
		stopAdapt=new ArrayList<String>();
		LatLng stopLtLn = null;
		JSONObject 	stopObj = null;
		String ids="";
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
				startID=(String)stopObj.get("startingID");
				stopAdapt.add(stopn);
				ids=stopn+"$"+startID;
				marker = map.addMarker(new MarkerOptions()
				.position(stopLtLn).title(ids).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_nyc_subway)));
				mark.add(marker);
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "calling setData-> e"+ e.toString(), Toast.LENGTH_LONG).show();// TODO Auto-generated catch block
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
				Toast.makeText(getApplicationContext(), "FixArray JSONException->"+ e.toString(), Toast.LENGTH_LONG).show();
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
		//		mark.add(marker);
				
				
			
			}

		}
		
	
	}
	
	
	public Location getLocation() {
		boolean isGPSEnabled=false;
		boolean isNetworkEnabled=false;
		
		double latitude=0.0;
		double longitude=0.0;
	    try {
	    	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
				addMarkers(np);
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
					addMarkers(np);
					
				}
				}
			*/
			}
		};
	}
	
	
	
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bus_, menu);
		return true;
	}



	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
/*		int lat = (int) (arg0.getLatitude());
		int lng = (int) (arg0.getLongitude());
		LatLng temp=new LatLng(lat,lng);
		_myLocation.setPosition(currObj);
		_myLocation.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.center));
		*/
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
