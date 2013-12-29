package com.example.autocomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.maps.MapActivity;
public class Radius extends MapActivity implements LocationListener  {

	DataBaseHelper dbh=null;
	List<android.location.Address> addresses;
	android.location.Address address;
	boolean canGetLocation=false;
	private GoogleMap map;
	JSONObject json=null; 
	JSONArray user = null;
	JSONObject obj1=null;
	JSONObject locObj=null;
	JSONObject locObjGeo=null;
	String addressLineZero=null;
	String addressLineOne=null;
	String addressLineTwo=null;
	String startID="";
	String displayStop="";
	String preSelect=null;
	String city=null;
	String country=null;
	String [] temptexty=null;
	Polyline polylin =null;
	String state=null;
	String type=null;
	String stopstartID=null;
	String source=null;
	String dispStop=null;
	Marker _myLocation =null;
	boolean flag=false;
	boolean flagLoc=false;
	boolean flagStop=false;
	String add=null;
	Location location =null;
	LatLng np=null;
	AutoCompleteTextView autoCompViewFrom=null;
	AutoCompleteTextView textViewFrom=null;
	Marker marker=null;
	TextView infotest=null;
	TextView info=null;
	TextView info1=null;
	TextView info2=null;
	TextView info3=null;
	ImageButton searchButton;
	double tempDist=0.0;
	Button go;
	public String bestProvider;
	double currLat=0.0;
	double currLng=0.0;
	double minDist=0.0;
	Button button;
	LatLng currObj=null;
	JSONArray FixArray =null;
	ArrayList<String> stopAdapt=null;
	ArrayList<String> stopsDisplay=null;
	ArrayAdapter<String> adapter =null;
	ArrayList<Marker> mark=null;
	Spinner stops_spinner=null;
	LocationManager locationManager=null;
	ArrayAdapter<String> adapterSpinner = null;
	String API_KEY="AIzaSyBEO-54Cb7c819FO7zGVsQvKrqxOPZQvcs";
	ImageView  searchImg=null;
	ExpandableListAdapter listAdapter;
  //  ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Spinner image_spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radius);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		Log.i("Radius","starting activity");
	//	button = (Button) findViewById(R.id.btnRoute);
		//addListenerOnButton();
		 ActionBar actionBar = getActionBar();
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        actionBar.setDisplayShowCustomEnabled(true);
	        // actionBar.setDisplayShowTitleEnabled(false);
	        // actionBar.setIcon(R.drawable.ic_action_search);

	        LayoutInflater inflator = (LayoutInflater) this
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View v = inflator.inflate(R.layout.actionbar, null);

	        actionBar.setCustomView(v);
	        
	        
	        
	    	textViewFrom = (AutoCompleteTextView) v
	                .findViewById(R.id.autocompleteBusBar);
	        
	        
	        
	        
	        
		mark=new ArrayList<Marker>();
		 searchImg = (ImageView) findViewById(R.id.srchImg);
		 searchImg.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v){
			    	source = textViewFrom.getText().toString();
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
							  
								Intent intent = new Intent(Radius.this,MarkerTest.class);
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
		
		
		
		 textViewFrom.setOnItemClickListener(new OnItemClickListener() {
				LatLng stopLtLn=null;
				JSONObject stopObj=new JSONObject();
		        @Override
		        public void onItemClick(AdapterView<?> parent, View arg1, int pos,
		                long id) {
		        	
		        String	source1 = textViewFrom.getText().toString();
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
		       /* 	
		        	 for(int j=0;j<mark.size();j++)
		    	     {
		    	    	 
		    	    	 tempT=(mark.get(j)).getTitle();
		    	    	 if(temptitle.equals(tempT))
		    	    	 {
		    	    		 
		    	    		 mark.get(j).showInfoWindow();
		    	    		 break;
		    	    	 }
		    	     }
		     */
		        	

		        }
		    });
  
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		   
	//	autoCompViewFrom = (AutoCompleteTextView) findViewById(R.id.autocompleteFrom1);
		
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
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapsStop))
				.getMap();
		map.setMyLocationEnabled(true);
		Log.i("Radius","after map");

		map.setOnCameraChangeListener(getCameraChangeListener());
		 locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		Log.i("Radius","after locationManager");
		Criteria criteria = new Criteria();
		
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker markerClick) {

				if(markerClick == marker)
				{
					Intent intent = new Intent(Radius.this,MarkerTest.class);
					startActivity(intent);
				}
				else if(markerClick == marker)
				{
					Intent intent = new Intent(Radius.this,MarkerTest.class);
					startActivity(intent);
				}
			}

		});
		bestProvider = locationManager.getBestProvider(criteria, false);
		Log.i("Radius","after bestProvider->"+bestProvider);
		if(bestProvider == null)
		{
			Toast.makeText(getApplicationContext(),"bestProvider->null", Toast.LENGTH_LONG).show();
		}
		else
		{
			location = locationManager.getLastKnownLocation(bestProvider);
		}

		int c=0;
	
		Log.i("Radius","bestProvider-->"+bestProvider);
		/*
		location=getLocation();
		
		currLat=location.getLatitude();
		currLng=location.getLongitude();
		Log.i("Radius","currLat-->"+currLat+"currLng->"+currLng);
	    currObj=new LatLng(currLat,currLng);

		_myLocation = map.addMarker(new MarkerOptions()
		.position(currObj)
		.title("My Location")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.center)));


		Log.i("Radius","currLat->"+currLat+"currLng->"+currLng);

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(currObj, 15));
		*/
/*
		View v = getLayoutInflater().inflate(R.layout.marker, null);
		
		info= (TextView) v.findViewById(R.id.info);
		info1= (TextView) v.findViewById(R.id.info1);
		info2= (TextView) v.findViewById(R.id.info2);
		info3= (TextView) v.findViewById(R.id.info3);


*/
		map.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoWindow(Marker arg0) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {
				View v = getLayoutInflater().inflate(R.layout.marker, null);
				if(v == null)
					Toast.makeText(getApplicationContext(),"view is null", Toast.LENGTH_LONG).show();
				
				
				
			
				String titleText=marker.getTitle();
			
				
				info= (TextView) v.findViewById(R.id.info);
				info1= (TextView) v.findViewById(R.id.info1);
				info2= (TextView) v.findViewById(R.id.info2);
				info3= (TextView) v.findViewById(R.id.info3);

				
				String title=marker.getTitle();
				
				String [] testy=title.split("\\$");
				String titleFin=testy[0];
				stopstartID=testy[1];
			
				
		
				String firstLine=null;
				String secondLine=null;
				String thirdLine=null;
				JSONObject jobj=new JSONObject();
				
				jobj=dbh.getTrainStopsForMarker(stopstartID, titleFin);
				
				
				String stop1="";
				String stop2="";
				String stop3="";
				String dep1="";
				String dep2="";
				String dep3="";
				String stopid1="";
				String stopid2="";
				String stopid3="";
				
				
				
				
				
				try {
					stop1=(String)jobj.get("stopNm1");
					stop2=(String)jobj.get("stopNm2");
					stop3=(String)jobj.get("stopNm3");
					stopid1=(String)jobj.get("stopid1");
					stopid2=(String)jobj.get("stopid2");
					stopid3=(String)jobj.get("stopid3");
					dep1=(String)jobj.get("depTm1");
					dep2=(String)jobj.get("depTm2");
					dep3=(String)jobj.get("depTm3");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int diff =0;
				int diff2 =0;
				int diff3 =0;
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
				
				
	        	int temp=0;
				if(diff==0)
				{
					firstLine="To "+stop1+"->"+"Now";
				}
				else
				{
			
					if(diff > 60)
					{
						firstLine="To "+stop1+"->"+" Over 60 mins";
					}
					else
					firstLine="To "+stop1+"->"+diff+" min";
				}
				
				
				if(diff2==0)
				{
					secondLine="To "+stop2+"->"+"Now";
				}
				else
				{
					
					if(diff2 > 60)
					{
						secondLine="To "+stop2+"->"+" Over 60 mins";
					}
					else					
					secondLine="To "+stop2+"->"+diff2+" min";
				}
				
				
				
				if(diff3 == 0)
				{
					thirdLine="To "+stop3+"->"+"Now";
				}
				else
				{
					
					if(diff3 > 60)
					{
						thirdLine="To "+stop3+"->"+" Over 60 mins";
					}
					else
					thirdLine="To "+stop3+"->"+diff3+" min";
				}
				
				
			
		
				
				info.setText(titleFin);
				info1.setText(firstLine);
				info2.setText(secondLine);
				info3.setText(thirdLine);
				
			
				
				
				return v;
			}
		});
		
	
		
		
		
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
            	
            
            	Bundle bundle = new Bundle();
            	
            	bundle.putString("initId", stopstartID);
            	
            	
               Intent intent = new Intent(Radius.this,MarkerTest.class);
               intent.putExtras(bundle);
               startActivity(intent);


            }
        });
		
		
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			
			type=(String)extras.get("transitType");
			
			
		}
		
	
	
		{
			 dbh=new DataBaseHelper(getApplicationContext());
			 initDB();
		FixArray=dbh.getTrainStops();
		stopAdapt=new ArrayList<String>();
		LatLng stopLtLn = null;
		JSONObject 	stopObj = null;
		setData();
		
	
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,stopAdapt);
		
	//	autoCompViewFrom.setAdapter(adapter);
		textViewFrom.setAdapter(adapter);
		
		}
		
	}
	
	
	
	
	
	
	public void setData()
	{
		  listDataHeader = new ArrayList<String>();
	        listDataChild = new HashMap<String, List<String>>();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		  listDataHeader.add("Select Stop");
		   listDataChild.put(listDataHeader.get(0), stopAdapt);
		
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
	
	
	
	
	
public void initDB()
{
	
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
		stopsDisplay.add(stopn);
				
				
			}

		}
		
		
		
		adapterSpinner = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,stopsDisplay);
		adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		if(!(dispStop.equals(displayStop)));
		dispStop=displayStop;


	
	}


	public OnCameraChangeListener getCameraChangeListener()
	{
		return new OnCameraChangeListener() 
		{
			@Override
			public void onCameraChange(CameraPosition position) 
			{
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
			
			}
		};
	}



	protected String getJSON(String url) {
		return getUrlContents(url);
	}


	private String getUrlContents(String theUrl) {
		StringBuilder content = new StringBuilder();
		try {
			URL url = new URL(theUrl);
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()), 8);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line + "\n");
			}
			bufferedReader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.radius, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
	//	int lat = (int) (arg0.getLatitude());
	//	int lng = (int) (arg0.getLongitude());
	//	LatLng temp=new LatLng(lat,lng);
	//	_myLocation.setPosition(temp);
		_myLocation = map.addMarker(new MarkerOptions()
		.position(currObj)
		.title("My Location")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.center)));

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
