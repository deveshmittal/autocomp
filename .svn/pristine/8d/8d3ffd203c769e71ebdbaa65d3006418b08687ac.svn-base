package com.example.autocomp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityStart extends Activity implements LocationListener {
	TextView tView;
	private GoogleMap map;
	LocationManager locationManager=null;
	boolean canGetLocation=false;
	Location location =null;
	double currLat=0.0;
	double currLng=0.0;
	LatLng currObj=null;
	Marker _myLocation =null;
	String weaString = null;
	 String cutAcross; 
	 class MyWeather{
		  String description;
		  String city;
		  String region;
		  String country;
		  
		  String windChill;
		  String windDirection;
		  String windSpeed;
		  
		  String sunrise;
		  String sunset;
		  
		  String conditiontext;
		  String conditionCode;
		  String conditiondate;
		  String conditionTemp;
		  String humidity;
		  public String toMyString(){
			  
			  return  conditionTemp+"/"+humidity+"%";
			  
			  /*  

			     return conditiondate +"\n\n"+"Temperature: " + conditionTemp + "\t"
			    + "Forecast: " + conditiontext + "\n"
			    + "Humidity: " + humidity + "\n"
			    +"Wind\n"
			    + "chill: " + windChill + "\t"
			    + "direction: " + windDirection + "\t"
			    + "speed: " + windSpeed + "\n"
			    
			    + "Sunrise: " + sunrise + "\t"
			    + "Sunset: " + sunset + "\n\n";
			      */
			  
			  
			  
			/*  
			  

		    return "\n- " + description + " -\n\n"
		    + "city: " + city + "\n"
		    + "region: " + region + "\n"
		    + "country: " + country + "\n\n"
		    
		    + "Wind\n"
		    + "chill: " + windChill + "\n"
		    + "direction: " + windDirection + "\n"
		    + "speed: " + windSpeed + "\n\n"
		    
		    + "Sunrise: " + sunrise + "\n"
		    + "Sunset: " + sunset + "\n\n"
		      + "Temperature: " + conditionTemp + "\n"
		    + "Condition: " + conditiontext + "\n"
		    + conditiondate +"\n";

		     */
		  }
		 }
	
	
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		weaString = null;
	      if (android.os.Build.VERSION.SDK_INT > 9) {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
				}
			tView=(TextView)findViewById(R.id.weather);
	      cutAcross="";
	      try{
	      
  String weatherString = QueryYahooWeather();
  
	        Document weatherDoc = convertStringToDocument(weatherString);

	        MyWeather weatherResult = parseWeather(weatherDoc);
	         cutAcross = weatherResult.toMyString();
	         weaString = weatherResult.toMyString();
			tView.setText(weatherResult.toMyString());}
	      catch(Exception e)
	      {		if(cutAcross.length()==0)	{
	    	  tView.setText("");}

	    	  
	      }
	     

	        
	        
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.setMyLocationEnabled(true);
	/*
		location=getLocation();
		
		currLat=location.getLatitude();
		currLng=location.getLongitude();
		
	    currObj=new LatLng(currLat,currLng);
		_myLocation = map.addMarker(new MarkerOptions()
		.position(currObj)
		.title("My Location")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.center)));

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(currObj, 15));
*/

		 final Button button1 = (Button) findViewById(R.id.btndestination);
         button1.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click   

                 Intent activityChangeIntent = new Intent(ActivityStart.this, PlacesAutocompleteActivity.class);
                 activityChangeIntent.putExtra("wString",weaString);
                 // currentContext.startActivity(activityChangeIntent);

                 ActivityStart.this.startActivity(activityChangeIntent);
             }
         });
         final Button button2 = (Button) findViewById(R.id.btnBus);
         button2.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click   

                 Intent activityChangeIntent = new Intent(ActivityStart.this, Bus_Activity.class);

                 // currentContext.startActivity(activityChangeIntent);

                 ActivityStart.this.startActivity(activityChangeIntent);
             }
         });
         final Button button3 = (Button) findViewById(R.id.btnTrain);
         button3.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click   

                 Intent activityChangeIntent = new Intent(ActivityStart.this, Radius.class);

                 // currentContext.startActivity(activityChangeIntent);

                 ActivityStart.this.startActivity(activityChangeIntent);
             }
         });
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
	private MyWeather parseWeather(Document srcDoc){
	     Log.i("PARSER WEATHER",srcDoc.toString());
	     MyWeather myWeather = new MyWeather();
	     
	     //<description>Yahoo! Weather for New York, NY</description>
	     myWeather.description = srcDoc.getElementsByTagName("description")
	       .item(0)
	       .getTextContent();
	     
	     //<yweather:location city="New York" region="NY" country="United States"/>
	     Node locationNode = srcDoc.getElementsByTagName("yweather:location").item(0);
	     myWeather.city = locationNode.getAttributes()
	    .getNamedItem("city")
	    .getNodeValue()
	    .toString();
	  myWeather.region = locationNode.getAttributes()
	    .getNamedItem("region")
	    .getNodeValue()
	    .toString();
	  myWeather.country = locationNode.getAttributes()
	    .getNamedItem("country")
	    .getNodeValue()
	    .toString();
	  
	  
	  Node humidityNode = srcDoc.getElementsByTagName("yweather:atmosphere").item(0);

	  
	  myWeather.humidity = humidityNode.getAttributes()
			    .getNamedItem("humidity")
			    .getNodeValue()
			    .toString();
	  //<yweather:atmosphere humidity="35" visibility="2"
	  
	  
	  //<yweather:wind chill="60" direction="0" speed="0"/>
	  Node windNode = srcDoc.getElementsByTagName("yweather:wind").item(0);
	  myWeather.windChill = windNode.getAttributes()
	    .getNamedItem("chill")
	    .getNodeValue()
	    .toString();
	  myWeather.windDirection = windNode.getAttributes()
	    .getNamedItem("direction")
	    .getNodeValue()
	    .toString();
	  myWeather.windSpeed = windNode.getAttributes()
	    .getNamedItem("speed")
	    .getNodeValue()
	    .toString();

	   //<yweather:astronomy sunrise="6:52 am" sunset="7:10 pm"/>
	  Node astronomyNode = srcDoc.getElementsByTagName("yweather:astronomy").item(0);
	  myWeather.sunrise = astronomyNode.getAttributes()
	    .getNamedItem("sunrise")
	    .getNodeValue()
	    .toString();
	  myWeather.sunset = astronomyNode.getAttributes()
	    .getNamedItem("sunset")
	    .getNodeValue()
	    .toString();
	  
	  //<yweather:condition text="Fair" code="33" temp="60" date="Fri, 23 Mar 2012 8:49 pm EDT"/>
	  Node conditionNode = srcDoc.getElementsByTagName("yweather:condition").item(0);
	  myWeather.conditiontext = conditionNode.getAttributes()
	    .getNamedItem("text")
	    .getNodeValue()
	    .toString();
	  myWeather.conditionCode = conditionNode.getAttributes()
			    .getNamedItem("code")
			    .getNodeValue()
			    .toString();
	  myWeather.conditiondate = conditionNode.getAttributes()
	    .getNamedItem("date")
	    .getNodeValue()
	    .toString();
	  myWeather.conditionTemp = conditionNode.getAttributes()
			    .getNamedItem("temp")
			    .getNodeValue()
			    .toString()+getApplicationContext().getString(R.string.degree)+"C";

//	  RelativeLayout rLayout= (RelativeLayout)findViewById(R.id.rlayout);
	 /* String weatherString =myWeather.conditiontext.toLowerCase();
	  // ATTEN: Change the third argument as well
	  int picId = getResources().getIdentifier(weatherString, "drawable", "com.example.weatherapp");
	  rLayout.setBackgroundResource(picId);
	  */
	     return myWeather;
	    }
    private Document convertStringToDocument(String src){
	     Document dest = null;
	     
	     DocumentBuilderFactory dbFactory =
	       DocumentBuilderFactory.newInstance();
	     DocumentBuilder parser;

	     try {
	      parser = dbFactory.newDocumentBuilder();
	   dest = parser.parse(new ByteArrayInputStream(src.getBytes()));
	  } catch (ParserConfigurationException e1) {
	   e1.printStackTrace();
	  
	  } catch (SAXException e) {
	   e.printStackTrace();
	   
	  } catch (IOException e) {
	   e.printStackTrace();
	  
	  }
	     
	     return dest;
	    }
    private String QueryYahooWeather(){
	     // use the api to get WOEID which i used directly in this demo
	     //http://where.yahooapis.com/geocode?q=bangalore=%5Byourappidhere%5D
	     String qResult = "";
	     String queryString = "http://weather.yahooapis.com/forecastrss?w=2295411&u=c";
	     Log.i("queryString:",queryString);
	     HttpClient httpClient = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(queryString);
	          
	        
	        try {
	         HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();
	        
	         Log.i("httpEntity:",httpEntity.toString());

	         if (httpEntity != null){
	          InputStream inputStream = httpEntity.getContent();
	          Reader in = new InputStreamReader(inputStream);
	          BufferedReader bufferedreader = new BufferedReader(in);
	          StringBuilder stringBuilder = new StringBuilder();

	          
	          String stringReadLine = null;

	          while ((stringReadLine = bufferedreader.readLine()) != null) {
	           stringBuilder.append(stringReadLine + "\n"); 
	          }
	          
	          qResult = stringBuilder.toString(); 
	          Log.i("FIRST RESULT",qResult);

	         }

	   } catch (ClientProtocolException e) {
	   e.printStackTrace();
	  
	  } catch (IOException e) {
	   e.printStackTrace();
	  
	  } catch (Exception e) {
		   e.printStackTrace();
			  
			  }
	        
	     
	        return qResult;
	    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
