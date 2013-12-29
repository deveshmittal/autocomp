package com.example.autocomp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.MapActivity;
public class MappingAct extends MapActivity implements LocationListener {
	 
	private GoogleMap map;
	JSONObject jObj=null;
	JSONArray legs = null;
	LatLng sourceLatLng=null;
	String dist="";
	String dur="";
	double distance=0.0;
	double sourceLat=0.0;
	double sourceLng=0.0;
	double destLat =0;
	double destLng=0;
	String source="";
	String destination="";
	ArrayList<LatLng> ar=new ArrayList<LatLng>();
	ArrayList<String> ltln=null;
	ArrayList<String> polyLn=null;
	Polyline polylin =null;
	List<LatLng> polyz;
	//HashMap<String,String> polyMap= null;
	ArrayList<String> polyMap=null;
	//HashMap polyMap= null;
	String line="";
	Marker marker=null;
	String type=null;
	LatLng toCenter=null;
	String weaString=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapping);
		actionBarSetup();
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
					.getMap();
		 map.setMyLocationEnabled(true);
		 TextView myTv=(TextView)findViewById(R.id.weather);
		 
		 Bundle extras = getIntent().getExtras();
			if (extras != null)
			{
				
				type=extras.getString("Type");
				 
				if("Cab".equals(type))
				{
					
					try{
				sourceLat = extras.getDouble("startLat");	
				sourceLng= extras.getDouble("startLng"); 
				line=extras.getString("poly");
				weaString=extras.getString("wString");
			
					}
					catch(Exception er)
					{
						   Toast.makeText(this, "Exception in mapping", Toast.LENGTH_SHORT);
					}
				
				
				myTv.setText(weaString);
				 putMapCab(line);
				}
				else if("Transit".equals(type))
				{
					ltln=	extras.getStringArrayList("latlong");
					convertStrToLL(ltln);
					plotList();
				}
				else if("PolyString".equals(type))
				{
					weaString=extras.getString("wString");
					line=extras.getString("LegPolyLine");
					polyMap= extras.getStringArrayList("polyLinesHashMap");
				//	putMapLine(line);
					myTv.setText(weaString);
					plotArList();
					
					
				}else if("PolyArrayList".equals(type))
				{
					weaString=extras.getString("wString");
					polyLn=new ArrayList<String>();
					myTv.setText(weaString);
					polyLn=extras.getStringArrayList("completePolyLine");
					try
					{
					polyMap= extras.getStringArrayList("polyLinesHashMap");
					}
					catch(Exception ee)
					{
						Toast.makeText(getApplicationContext(), "PolyArrayList after polyMap exception->"+ee.toString(), Toast.LENGTH_LONG).show();
					}
					
					plotArList();
				}
				
			}
			extras.getString("LegPolyLine");
	
		
		 
		 
/*		 
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			Log.i("MappingAct","extras not null");
			sourceLat = extras.getDouble("sourceLat");
			sourceLng= extras.getDouble("sourceLng"); 
			destLat= extras.getDouble("destLat");
			destLng= extras.getDouble("destLng");
			source=extras.getString("source");
			destination=extras.getString("destination");
		}
		*/
		 /*
		 
		Log.i("MappingAct","sourceLat->"+sourceLat+", sourceLng->"+sourceLng+", destLat->"+destLat+", destLng-->"+destLng);
		LatLng HAMBURG = new LatLng(sourceLat,sourceLng);
		LatLng KIEL = new LatLng(destLat, destLng);
		map.addMarker(new MarkerOptions().position(HAMBURG).title(source));
		map.addMarker(new MarkerOptions()
		.position(KIEL)
		.title(destination)
				);
		
	map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
		
		
		
		
		
		
		try {
			jObj = new JSONObject(getIntent().getStringExtra("json"));
		
			Log.i("MappingAct","jObj  ->"+jObj.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			// Enabling MyLocation Layer of Google Map
			map.setMyLocationEnabled(true);
			
			
			try {
				String test=jObj.toString();
				//Log.i("DisplayMap","test->"+test);
				legs=jObj.getJSONArray("legs");
				JSONObject jObj2= legs.getJSONObject(0);
				JSONObject distObj= jObj2.getJSONObject("distance");
				dist=distObj.getString("text");
				String [] distArr=dist.split(" ");
				String distTest=distArr[0];
				Log.i("DisplayMap","distTest->"+distTest);
				
				distance=Double.parseDouble(distTest);
				Log.i("DisplayMap","distance->"+distance);
				JSONObject durObj= jObj2.getJSONObject("duration");
				dur=durObj.getString("text");
	
			
				JSONArray stepsArr=null;
				JSONObject jObjsteps=null;
				JSONObject jObjstart=null;
				JSONObject startObject=null;
				try
				{

					Log.i("DisplayMap","got stepsArray");
					jObjsteps= legs.getJSONObject(0);

					stepsArr=jObjsteps.getJSONArray("steps");
					Log.i("DisplayMap","got stepsArr");
					
				}
				catch(Exception e3)
				{
					Log.i("DisplayMap","Exception in getting steps");
				}
				JSONObject polyObject=null;
				double startLat=0.0;
				jObjstart=stepsArr.getJSONObject(0);
				Log.i("DisplayMap","stepsArr.length"+stepsArr.length());

				//testin

				startObject=jObjstart.getJSONObject("start_location");

				startLat=startObject.getDouble("lat");
				
				Log.i("PlacesAutocompleteActivity","got start_location lat double ->"+startLat);
				
				
				
				//use for displaying map
				
				try
				{
					polyObject= jObj.getJSONObject("overview_polyline");
					String polyline = polyObject.getString("points");
					polyz = decodePoly(polyline);
					
					for(int i=0;i<polyz.size();i++)
					{
						ar.add((LatLng)polyz.get(i));
					}
				}
				catch(Exception e4)
				{
					Log.i("DisplayMap","exception in for loop->"+e4.toString());
				}
			
			} catch (JSONException e1) {
				Log.i("DisplayMap","Exception in getting object");

				e1.printStackTrace();
			}
			

			if(polylin != null)
				polylin.remove();
			if(map != null)
			{
				map.clear();
			}
			
			PolylineOptions rectLine = new PolylineOptions().width(10).color(
					Color.BLUE);
			for (int i = 0; i < ar.size(); i++) {

				rectLine.add((LatLng)ar.get(i));
			}
			ar.clear();
			polylin = map.addPolyline(rectLine);
			
			
			*/

	}
	
	
	public void convertStrToLL(ArrayList<String> latLongStringList){
		
		for(String str:latLongStringList)
			{
			String [] bits = str.split(",");
			LatLng	point = new LatLng(Float.parseFloat(bits[0]),Float.parseFloat(bits[1]));
			ar.add(point);
			}
			
		}
	
	
	public void putMapCab(String polyLine)
	{
//		LatLng HAMBURG = new LatLng(sourceLat,sourceLng);
//		map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
		polyz = decodePoly(polyLine);
		MarkerOptions options = new MarkerOptions();
		
		for(int i=0;i<polyz.size();i++)
		{
			ar.add((LatLng)polyz.get(i));
		}
		
	//	marker = map.addMarker(new MarkerOptions()
	//		.position((LatLng)ar.get(0)).title("Start").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_start)));
		
		
		
		if(polylin != null)
			polylin.remove();
		if(map != null)
		{
			map.clear();
		}
		
		PolylineOptions rectLine = new PolylineOptions().width(10).color(
				Color.BLUE);
		int k=0;
		int minLat4 = Integer.MAX_VALUE;
	    int maxLat4 = Integer.MIN_VALUE;
	    int minLon4 = Integer.MAX_VALUE;
	    int maxLon4= Integer.MIN_VALUE;
	    LatLng tt=null;
		for ( k = 0; k < ar.size(); k++) {

			
			tt=(LatLng)ar.get(k);
			int lat2 = (int) (tt.latitude * 1E6);
	        int lon2 = (int) (tt.longitude * 1E6);
	        maxLat4 = Math.max(lat2, maxLat4);
	        minLat4 = Math.min(lat2, minLat4);
	        maxLon4 = Math.max(lon2, maxLon4);
	        minLon4 = Math.min(lon2, minLon4);
			rectLine.add((LatLng)ar.get(k));
		}
		
		
		
		
		
		polylin = map.addPolyline(rectLine);
		
		LatLng start=ar.get(0);
		LatLng end=ar.get(k-1);
		options.position(start);
		marker = map.addMarker(options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_start)));
		options.position(end);
		marker = map.addMarker(options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_end)));
		
		LatLng southWestLatLon = new LatLng(minLat4 / 1E6, minLon4 / 1E6);
	    LatLng northEastLatLon = new LatLng(maxLat4 / 1E6, maxLon4 / 1E6);
	    zoomInUntilAllMarkersAreStillVisible(southWestLatLon, northEastLatLon);
		
		
		
		
		
		 ar.clear();
	//	marker = map.addMarker(new MarkerOptions()
	//	.position((LatLng)ar.get(0)).title("Start").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_start)));
	/*
		marker = map.addMarker(new MarkerOptions()
		.position((LatLng)ar.get(k)).title("End").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_end)));
	
*/		
		
	}
	
	
	
	
	
	public void plotArList()
	{
		
		
	
		
		boolean flg=false;
		boolean flMode=false;
		MarkerOptions options=null;
		LatLng start=null;
		LatLng startPlot=null;
		if(polylin != null)
			polylin.remove();
		if(map != null)
		{
			map.clear();
		}
		int c=0;
		int mode=0;
		int si=0;
		PolylineOptions rectLine = null;
		si=polyMap.size();
//		Set set = polyMap.entrySet();
//	      // Get an iterator
//	      Iterator iter = set.iterator();
		//Iterator iter = polyMap.keySet().iterator(); 
		 int minLat2 = Integer.MAX_VALUE;
		    int maxLat2 = Integer.MIN_VALUE;
		    int minLon2 = Integer.MAX_VALUE;
		    int maxLon2= Integer.MIN_VALUE;
	
		String [] temp=null;
		for(int j=0;j<polyMap.size();j++)
		{
			temp=polyMap.get(j).split(" ");
			
		
			if(temp[1].equals("WALK"))
			{
				rectLine=new PolylineOptions().width(10).color(
						Color.RED);
				mode=1;
			}
			else if(temp[1].equals("RAIL"))
			{
				rectLine=new PolylineOptions().width(10).color(
						Color.GREEN);
				mode=2;
			}
			else if(temp[1].equals("BUS"))
			{
				rectLine=new PolylineOptions().width(10).color(
						Color.DKGRAY);
				mode=3;
			}
			
			polyz = decodePoly((String)temp[0]);
			
			
			for(int i=0;i<polyz.size();i++)
			{
				
				ar.add((LatLng)polyz.get(i));
				
			}
			
			
			switch(mode)
			{
			
			case 1:
				 options = new MarkerOptions();
				 start=ar.get(0);
				options.position(start);
				if(j == 0)
				{
					startPlot=start;
					marker = map.addMarker(options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_start)));
					
				}
				else
				{
					
				marker = map.addMarker(options.icon(BitmapDescriptorFactory.fromResource(R.drawable.step_walk_man)));
				}
				break;
			case 2:
				 options = new MarkerOptions();
				 start=ar.get(0);
				options.position(start);
				if(j == 0)
				{
					startPlot=start;
					marker = map.addMarker(options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_start)));
					flg=true;
				}
				else
				{
					
				marker = map.addMarker(options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_nyc_subway)));
				}
				break;
			
			case 3:
				 options = new MarkerOptions();
				 start=ar.get(0);
				options.position(start);
				if(j == 0)
				{
					startPlot=start;
					marker = map.addMarker(options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_start)));
					flg=true;
				}
				else
				{
					
				marker = map.addMarker(options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_nyc_bus)));
				}
				break;
				
			
			}
			LatLng tr=null;
			
			int k = 0;
			for ( k = 0; k < ar.size(); k++) {

				
				tr=(LatLng)ar.get(k);
				int lat2 = (int) (tr.latitude * 1E6);
		        int lon2 = (int) (tr.longitude * 1E6);
		        maxLat2 = Math.max(lat2, maxLat2);
		        minLat2 = Math.min(lat2, minLat2);
		        maxLon2 = Math.max(lon2, maxLon2);
		        minLon2 = Math.min(lon2, minLon2);
				
				rectLine.add((LatLng)ar.get(k));
			}
		
			if(j==si-1)
			{
				LatLng end=ar.get(k-1);
				options.position(end);
				marker = map.addMarker(options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_end)));
			}
			polylin = map.addPolyline(rectLine);
			ar.clear();
			polyz.clear();
			

		}
		 int minLat = Integer.MAX_VALUE;
		    int maxLat = Integer.MIN_VALUE;
		    int minLon = Integer.MAX_VALUE;
		    int maxLon = Integer.MIN_VALUE;
		  

	      
		
	try{	
		
		if(!(line.equals("")))
		{
			polyz = decodePoly(line);
			LatLng p=null;
			  Builder bounds= new LatLngBounds.Builder();  
		//	LatLngBounds bounds = new LatLngBounds();
			    for(int i=0;i<polyz.size();i++)
				{
			    	
			    //	bounds.include((LatLng)polyz.get(i));
			   p=(LatLng)polyz.get(i);
			   int lat1 = (int) (p.latitude * 1E6);
		        int lon1 = (int) (p.longitude * 1E6);
		        maxLat = Math.max(lat1, maxLat);
		        minLat = Math.min(lat1, minLat);
		        maxLon = Math.max(lon1, maxLon);
		        minLon = Math.min(lon1, minLon);
//				//	ar.add((LatLng)polyz.get(i));
				}
			    double latitudeToGo = (maxLat + minLat) / 1E6 / 2;
			    double longitudeToGo = (maxLon + minLon) / 1E6 / 2;
			    toCenter = new LatLng(latitudeToGo, longitudeToGo);

			    
			    
			//    centerCameraToProperPosition(toCenter);
			    

			    LatLng southWestLatLon = new LatLng(minLat / 1E6, minLon / 1E6);
			    LatLng northEastLatLon = new LatLng(maxLat / 1E6, maxLon / 1E6);
			    zoomInUntilAllMarkersAreStillVisible(southWestLatLon, northEastLatLon);
			    
			  /*  
			    map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50));
			LatLng zoomIt=(LatLng)polyz.get(0);
		//	map.setCenter(latlngbounds.getCenter(), map.getBoundsZoomLevel(latlngbounds));
		//	map.moveCamera(CameraUpdateFactory.newLatLngZoom(zoomIt, 15));
			*/
		}
		else
		{
			
			  LatLng southWestLatLon = new LatLng(minLat2 / 1E6, minLon2 / 1E6);
			    LatLng northEastLatLon = new LatLng(maxLat2 / 1E6, maxLon2 / 1E6);
			    zoomInUntilAllMarkersAreStillVisible(southWestLatLon, northEastLatLon);
			
			
			
			
//			LatLng HAMBURG = new LatLng(startPlot.latitude,startPlot.longitude);
	//		map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
			
		}
	}
	catch(Exception t)
	{
		//Toast.makeText(getApplicationContext(), " polyMap exception->"+t.toString(), Toast.LENGTH_LONG).show();
	}
		polyz.clear();
	}
	
	
	private void zoomInUntilAllMarkersAreStillVisible(final LatLng southWestLatLon, final     LatLng northEastLatLon) {

	    map.setOnCameraChangeListener(new OnCameraChangeListener() {

	        @Override
	        public void onCameraChange(CameraPosition arg0) {

	            map.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(southWestLatLon, northEastLatLon), 50));
	            map.setOnCameraChangeListener(null);

	        }
	    });

	}
	public void putMapLine(String polyLine)
	{
		LatLng HAMBURG = new LatLng(sourceLat,sourceLng);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
		polyz = decodePoly(polyLine);
		MarkerOptions options = new MarkerOptions();
		
		for(int i=0;i<polyz.size();i++)
		{
			ar.add((LatLng)polyz.get(i));
		}
		
	//	marker = map.addMarker(new MarkerOptions()
	//		.position((LatLng)ar.get(0)).title("Start").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_start)));
		
		
		
		if(polylin != null)
			polylin.remove();
		if(map != null)
		{
			map.clear();
		}
		
		PolylineOptions rectLine = new PolylineOptions().width(10).color(
				Color.BLUE);
		int k=0;
		for ( k = 0; k < ar.size(); k++) {

			rectLine.add((LatLng)ar.get(k));
		}
		
		
		
		
		
		polylin = map.addPolyline(rectLine);
	}
	

	
	
	public void plotList()
	{
		
		if(polylin != null)
			polylin.remove();
		if(map != null)
		{
			map.clear();
		}
		
		PolylineOptions rectLine = new PolylineOptions().width(10).color(
				Color.BLUE);
		int k=0;
		for ( k = 0; k < ar.size(); k++) {

			rectLine.add((LatLng)ar.get(k));
		}
		
		
		
		
		
		polylin = map.addPolyline(rectLine);
		
		
		
	}
	
	
	

	
	
	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainmenu, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		Log.i("DisplayMap","item.getItemId()->"+item.getItemId());
      
            this.finish();
            return true;
       
        
      //  return super.onOptionsItemSelected(item);
    }

	 private void actionBarSetup() {
		   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
		     ActionBar ab = getActionBar();
		     ab.setTitle("Map");
		     
		   }
		 }
	
	
	
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

}
