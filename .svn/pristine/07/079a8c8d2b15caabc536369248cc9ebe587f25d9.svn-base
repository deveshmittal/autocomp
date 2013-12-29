package com.example.autocomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.maps.MapActivity;

public class DisplayMap extends MapActivity {


	double sourceLat =0.0;
	double sourceLng=0.0;
	double destLat =0;
	double destLng=0;
	String dist="";
	String source="";
	String destination="";
	String sourceCity=null;
	String sourceHead=null;
	String take2source=null;
	String take2dest=null;
	Geocoder start;
	Geocoder end;
	ArrayList<LatLng> ar=new ArrayList<LatLng>();
	ArrayList<LatLng> arPoly=new ArrayList<LatLng>();
	ArrayList<String> Transit = new ArrayList<String>(); 
	ArrayList<String> Busish =new ArrayList<String>();
	ArrayList<String> Trainish= new ArrayList<String>();
	List<LatLng> polyz;
	Polyline polylin =null;
	TextView distText=null;
	TextView fareText=null;
	TextView provText=null;
	TextView durText=null;
	TextView transitDest=null;
	TextView transit2=null;
	TextView tollView=null;
	String destHead=null;
	 TextView transitText=null;
	 ImageButton btnMap=null;
	JSONArray user = null;
	JSONArray legs = null;
	JSONObject jObj=null;
	 ListView listViewItems=null;
	JSONObject json=null; 
	ListView listV=null;
	 LatLng sourceLatLng=null;
	static ArrayList<String> fareList=null;
	JSONObject jsonTrainish;
	JSONObject jsonTransit;
	String dur="";
	Context cont;
	double distance=0.0;
	double startLat=0.0;
	double startLng=0.0;
	double endLat=0.0;
	double endLng=0.0;
	String date="";
	String time="";
	String localityAddress=null;
	String weaString = null;
	private ProgressDialog pDialog;
	ArrayList<Integer> flagArrayTransit=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaymap);
		actionBarSetup();
		flagArrayTransit=new ArrayList<Integer>();
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null)
		{
			weaString=extras.getString("wString");
			Log.i("DisplayMap","extras not null");
			sourceLat = extras.getDouble("sourceLatitude");
			
			sourceLng= extras.getDouble("sourceLng"); 
			destLat=extras.getDouble("destLat");
			destLng=extras.getDouble("destLng");
			source=extras.getString("source");
			destination=extras.getString("destination");
			date=extras.getString("date");
			time=extras.getString("time");
		}
		
		start=new Geocoder(this,Locale.getDefault());
		end=new Geocoder(this,Locale.getDefault());
		Log.i("DisplayMap","sourceLat->"+sourceLat+"sourceLng->"+sourceLng);
	
		new MapTask().execute(0);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		Log.i("DisplayMap","item.getItemId()->"+item.getItemId());
      
            this.finish();
            return true;
       
        
      //  return super.onOptionsItemSelected(item);
    }






	public class MapTask extends AsyncTask<Integer , Integer,  JSONArray>{



		@Override
		protected void onPreExecute() {


			pDialog = new ProgressDialog(DisplayMap.this);
			pDialog.setMessage("Planning your Trip...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

			super.onPreExecute();
		}





		@Override
		protected  JSONArray doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			
			Log.i("DisplayMap","before location display");
			String tempSource="";
			String [] locArr=null;
			String [] destArr=null;
		
			 sourceHead=null;
			 destHead=null;
			int tempLength=0;
			String testSource=null;
			String testDest=null;
			testSource=source.replaceAll("\\s+","%20");
		
			tempSource=source.replaceAll("\\s+","");
			locArr=tempSource.split(",");
			tempLength=locArr.length;
			sourceHead=locArr[0];
			
			
			
			
		    sourceCity=locArr[tempLength-3];
		    
		    testDest=destination.replaceAll("\\s+","%20");
		  String tempDestn=destination.replaceAll("\\s+","");
			locArr=tempDestn.split(",");
			destHead=locArr[0];
			Log.i("DisplayMap","sourceCity->"+locArr[tempLength-3]);
			String tempDest="";
			tempDest=destination.replaceAll("\\s+","");
			destArr=tempDest.split(",");		
			
			
			Log.i("DisplayMap","source->"+tempSource+", dest->"+tempDest);
			JSONParser jParser = new JSONParser();
/*
			String url = "http://maps.googleapis.com/maps/api/directions/json?"
					+ "origin=" + tempSource
					+ "&destination="+tempDest
					+ "&sensor=true&units=metric&mode=driving";
			*/
			String url = "http://maps.googleapis.com/maps/api/directions/json?"
					+ "origin=" + testSource
					+ "&destination="+testDest
					+ "&sensor=true&units=metric&mode=driving";
		
		//	Toast.makeText(getApplicationContext(), "url->"+url, Toast.LENGTH_LONG).show();
			Log.i("DisplayMap","url->"+url);
			// getting JSON string from URL


			try{
				json= jParser.getJSONFromUrl(url);
			}
			catch(Exception e6)
			{
				Log.i("DisplayMap","Exception in getting json");
			}
			try {
				Log.i("DisplayMap","json test ->"+json.toString());
				user=json.getJSONArray("routes");
				Log.i("DisplayMap","user ->"+json.toString());
				Log.i("DisplayMap","user length->"+user.length());
				jObj= user.getJSONObject(0);
			} catch (JSONException e1) {
				Log.i("DisplayMap","Exception in getting array");
				
				
				take2source=source.replaceAll("\\s+","%22");
				take2dest=destination.replaceAll("\\s+","%22");
				String url2 = "http://maps.googleapis.com/maps/api/directions/json?"
						+ "origin=" + take2source
						+ "&destination="+take2dest
						+ "&sensor=true&units=metric&mode=driving";
				
				try{
					Log.i("DisplayMap","Exception in getting json trying with url2");
					json= jParser.getJSONFromUrl(url2);
					user=json.getJSONArray("routes");
					Log.i("DisplayMap","user ->"+json.toString());
					Log.i("DisplayMap","user length->"+user.length());
				}
				catch(Exception e6)
				{
					Log.i("DisplayMap","Exception in getting json");
				}
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			

			return user;
		}

		@Override
		protected void onPostExecute(JSONArray user) {



			try {
				 jObj= user.getJSONObject(0);
				String test=jObj.toString();
				Log.i("DisplayMap","test->"+test);
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
			
				jObjstart=stepsArr.getJSONObject(0);
				
				Log.i("PlacesAutocompleteActivity","got jObj2 ->"+jObj2.toString());
				
				//testin
				Log.i("DisplayMap","jObjstart->"+jObjstart.toString());
		
				startObject=jObj2.getJSONObject("start_location");

				startLat=startObject.getDouble("lat");
				startLng=startObject.getDouble("lng");
				

				JSONObject endObject=jObj2.getJSONObject("end_location");
				
				endLat=endObject.getDouble("lat");
				endLng=endObject.getDouble("lng");
				

				Log.i("PlacesAutocompleteActivity","got startLat ->"+startLat+"startLng->"+startLng+"endLat->"+endLat+"endLng->"+endLng);

				String polyLine=null;
				
				String testToll=user.toString();
				boolean checkToll=false;
				String tollText="This route may contain tolls , the fares displayed are not inclusive of tolls";
				checkToll=testToll.contains("toll");

				
				
				
				
				Log.i("DisplayMap","user TEST->"+testToll);
				
				Log.i("DisplayMap","checkToll->"+checkToll);
				//use for displaying map
			
			} catch (JSONException e1) {
				Log.i("DisplayMap","Exception in getting object");

				e1.printStackTrace();
			}


			 
			 
			 
			  sourceLatLng = new LatLng(sourceLat,sourceLng);
			 

			pDialog.dismiss();
			String url=null;
			
			url="http://2.zophop2013.appspot.com/testingwebapp/testservlet?city="+sourceCity+"&dist="+distance;

			Log.i("DisplayMap","url->"+url);
			
			
			JSONObject fare=null;
			try {
				fare=readJsonFromUrl(url);
			} catch (IOException e) {

				e.printStackTrace();
			} catch (JSONException e) {

				e.printStackTrace();
			}
			Log.i("DisplayMap","fare json->"+fare.toString());

			 fareList=new ArrayList<String>();
			double fares=0.0;
			int transpId=0;
			String transpString=null;
			String temp=null;
			String tempAuto="";
			String tempTaxi="";
			String headerString="";
			headerString="Duration->"+dur;
		//	fareList.add(headerString);
			int i=0;
			int j=0;
			  Iterator<?> keys = fare.keys();
			  
			 String rups="";
			 rups=getApplicationContext().getString(R.string.rupees);
			  String [] keysp=null;
			  String transpName="";
			  String telId="";
			  int indexAuto=0;
			  int indexTaxi=0;
			   while( keys.hasNext() ){
		            String key = (String)keys.next();
		            Log.i("DisplayMap"," key->"+key);
		            ++i;
		            try {
					
							Log.i("DisplayMap","fare.get(key) for key->"+key+"-->"+fare.get(key));
							
							Object obj=fare.get(key);
							 if (obj instanceof Double) {
								 fares = (Double) obj;
							    }
							 else
							 {
								 fares=(Integer) obj;
							 }
							
							 keysp=key.split("/");
							 transpName=keysp[0]; 
							 telId=keysp[1];
							 keysp=null;
							 if(transpName.equalsIgnoreCase("Auto"))
							 {
								 indexAuto=i;
								 tempAuto=rups+fares+"-"+transpName+"-"+telId;
							 }
							 else if(transpName.equalsIgnoreCase("Taxi"))
							 {
								 indexTaxi=i;
								 tempTaxi=rups+fares+"-"+transpName+"-"+telId;
							 }
													
							
							
						
					} catch (JSONException e) {
						Log.i("DisplayMap"," exception in getting value");
						
						e.printStackTrace();
					}
		        }
			   fareList.add(tempAuto);
			   fareList.add(tempTaxi);
			 i=0;
			   keys = fare.keys();
			   while( keys.hasNext() ){
		            String key = (String)keys.next();
		            Log.i("DisplayMap"," key->"+key);
		            ++i;
		            try {
					
							Log.i("DisplayMap","fare.get(key) for key->"+key+"-->"+fare.get(key));
							
							Object obj=fare.get(key);
							 if (obj instanceof Double) {
								 fares = (Double) obj;
							    }
							 else
							 {
								 fares=(Integer) obj;
							 }
							
							 keysp=key.split("/");
							 transpName=keysp[0]; 
							 telId=keysp[1];
							 keysp=null;
							 if(!(transpName.equalsIgnoreCase("Auto")) && !(transpName.equalsIgnoreCase("Taxi")))
							 {
								 temp=rups+fares+"-"+transpName+"-"+telId;
								 fareList.add(temp);
							 }
							       
						
							
						
					} catch (JSONException e) {
						Log.i("DisplayMap"," exception in getting value");
						
						e.printStackTrace();
					}
		        }
			   
			
			Log.i("DisplayMap","SUPERR "+user.toString());
		
			
			
			new JSONParse().execute();
			new JSONParse1().execute();
			new JSONParse2().execute();
			
			super.onPostExecute(user);
			
		
			
		}
	
		/* Method to decode polyline points */
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

		
		 private JSONObject getOutputFromUrl(String url) {
			 JSONObject output = null;
	            try {
	                DefaultHttpClient httpClient = new DefaultHttpClient();
	                HttpGet httpGet = new HttpGet(url);
	  
	                HttpResponse httpResponse = httpClient.execute(httpGet);
	                HttpEntity httpEntity = httpResponse.getEntity();
	              //  output = EntityUtils.toString(httpEntity);
	                output=  (JSONObject) httpEntity;
	            } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	            } catch (ClientProtocolException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            return output;
	        }
		
		
		 public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
			    InputStream is = new URL(url).openStream();
			    try {
			      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			      String jsonText = readAll(rd);
			      JSONObject json = new JSONObject(jsonText);
			      return json;
			    } finally {
			      is.close();
			    }
			  }
		 public  String readAll(Reader rd) throws IOException {
			    StringBuilder sb = new StringBuilder();
			    int cp;
			    while ((cp = rd.read()) != -1) {
			      sb.append((char) cp);
			    }
			    return sb.toString();
			  }
	


	}
	
	
	
	
	
	
	
	
	
	
	
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
		   
	    private static final String TAG_PLAN = "plan";
	    private static final String TAG_ITN = "itineraries";
	    private static final String TAG_MODE = "mode" ;
	    private static final String TAG_NAME = "name";
	    private static final String TAG_EMAIL = "email";
	    private static final String LOG_TAG= "Main Activity";

	//    private  String urlTrainish = "http://ec2-54-211-193-175.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=TRAINISH,WALK&maxWalkDistance=6436&min=TRANSFERS&time=4:30pm&date=10/30/2013&showIntermediateStops=true&toPlace=";
	    private  String urlTrainish = "http://ec2-184-73-126-14.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=TRAINISH,WALK&maxWalkDistance=6436&min=TRANSFERS&time="+time+"&date="+date+"&showIntermediateStops=true&toPlace=";
	       @Override
	       protected void onPreExecute() {
	           super.onPreExecute();
	           urlTrainish=urlTrainish+endLat+","+endLng+"&fromPlace="+startLat+","+startLng;
	         //  Toast.makeText(getApplicationContext(), "urlTrainish->"+urlTrainish, Toast.LENGTH_LONG).show();    
				pDialog = new ProgressDialog(DisplayMap.this);
				pDialog.setMessage("Planning your Trip...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
	    //        uid = (TextView)findViewById(R.id.uid);
	            Log.d("THIS_LOG!","JSON!");
	       
	       }

	       @Override
	       protected JSONObject doInBackground(String... args) {
	           JParser jParser = new JParser();
	           Log.i("DEV URL",urlTrainish);
	           
	            jsonTrainish = jParser.getJSONFromUrl(urlTrainish);
	            Log.i("THIS SHOULD BE NULL",jsonTrainish.toString());
	
	           return jsonTrainish;
	       }
	        @Override
	        protected void onPostExecute(JSONObject jsonTrainish) {
	          
				
			
	        	 Log.d(LOG_TAG,"BEFORE  TRY");
	                   // Getting JSON Array
	            	try{
	            		 Log.d(LOG_TAG,"BEFORE DBH");
	            		 FareDBHelper fdbh= new FareDBHelper(getApplicationContext());
	            	
	            	 Log.d(LOG_TAG,"AFTER DBH");
	            	try {
	            		Log.d(LOG_TAG,"BEFORE CREATE");
	            	
						fdbh.createDataBase();
		            	 Log.d(LOG_TAG,"AFTER CREATE");

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		try {
	        			Log.d(LOG_TAG,"BEFORE OPEN");
						fdbh.openDataBase();
	        			Log.d(LOG_TAG,"AFTER OPEN");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            		
	            		
	            		
	            
	            	JSONObject json_plan0 = jsonTrainish.getJSONObject("plan");
	            	String date_plan = json_plan0.getString("date");
	                String idTrainish = "";

	            	JSONArray json_plan_itn0 =json_plan0.getJSONArray(TAG_ITN);
	            	int arrLength = json_plan_itn0.length();
	            	for(int k=0;k<arrLength;k++)            	
	            	{
	            		JSONObject ababb = json_plan_itn0.getJSONObject(k);
	            		String trans = ababb.getString("transfers");
	            		long dur = Long.valueOf(ababb.getString("duration"));
	            				Log.i("YAY!",Long.toString(dur));
	            		int n=k;
	            		String index = Integer.toString(n);
	            		String hm = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(dur),
	            		TimeUnit.MILLISECONDS.toMinutes(dur) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(dur)));
	            		String rupeeSymbol = getApplicationContext().getString(R.string.rupees);
	            		String clockSymbol = getApplicationContext().getString(R.string.clock);
	            		String fared = new String("0");
	                    String firstFared =new String("NA");
	                    JSONArray legsArray =ababb.getJSONArray("legs");
	                    int finalTransfers = legsArray.length();
	                    int totalTrainFare = 0;
	                    int totalFirstTrainFare = 0;
	                    for (int j = 0;j < legsArray.length(); j++)
	                    {     
	                    	String insideLegsFromStopId;
	                    	String insideLegsToStopId ;
	                    	JSONObject insideLegs = legsArray.getJSONObject(j);
	           			 	String additive0 = insideLegs.getString("mode");
	           			 	//idTrainish =idTrainish + " "+ additive0;
	           			 	if(insideLegs.getString("mode").equalsIgnoreCase("RAIL"))
	           			 	{
	                      		insideLegsFromStopId =insideLegs.getJSONObject("from").getJSONObject("stopId").getString("id");
	                      		insideLegsToStopId=insideLegs.getJSONObject("to").getJSONObject("stopId").getString("id");
	                      		int fromId = Integer.parseInt(insideLegsFromStopId);
	                      		int toId = Integer.parseInt(insideLegsToStopId);
	                      		int	singleFare=fdbh.getFareFromFareTable(fromId,toId);
	                        	int singleFirstFare = fdbh.getFirstFareFromFareTable(fromId,toId);
	                        	totalTrainFare+=singleFare ;
	                        	totalFirstTrainFare+=singleFirstFare;
	                        }
	                      	if(insideLegs.getString("mode").equalsIgnoreCase("WALK"))
	                      	{
	                      				finalTransfers= finalTransfers-1;
	                      				
	                      	}
	                      			
	                    }
	                    
	                    fared =Integer.toString(totalTrainFare);
	                    firstFared = Integer.toString(totalFirstTrainFare);
	                    
	                    String fareOut=rupeeSymbol+fared+"/"+rupeeSymbol+firstFared;
	                    if(totalTrainFare==0||totalFirstTrainFare==0)
	                    {
	                    	fareOut="";
	                    }
	            		Trainish.add(clockSymbol+hm+"  "+fareOut+"  Transfers:"+finalTransfers); 	
	            		
	            		
	            		
	                    Log.i("ZOPPER TRAINISH",Trainish.get(k)+" VALUE OF K is:"+k);
	            	}
	           
	            	
	            	
	            	
	            	JSONObject ababber = json_plan_itn0.getJSONObject(0); 
	           	
	            Log.d(LOG_TAG, "ZOPPY "+idTrainish);
	         
	            	{
	            		 HashMap<String, String> map = new HashMap<String, String>();
	            		 JSONObject c0 = json_plan_itn0.getJSONObject(0);
	            		 JSONArray legs0 = c0.getJSONArray("legs");
	            	
	            		 for (int j = 0;j < legs0.length(); j++)
	            		 {
	            			 JSONObject idObj0 = legs0.getJSONObject(j);
	            			 String additive0 = idObj0.getString("mode");
	            			 //if(additive0.equalsIgnoreCase("RAIL")){}
	            			 
	           			 
	            			 Log.d(LOG_TAG,"THIS IS THE OUTSIDE CODE:"+additive0);
	            			 if(additive0.equalsIgnoreCase("WALK"))
	            			 {
	           			 idTrainish =idTrainish + " "+ additive0;
	            			 Log.d(LOG_TAG,"THIS IS THE CODE: "+additive0);
	           			 }
	            			 if(additive0.equalsIgnoreCase("RAIL")){
	       				 idTrainish =idTrainish + " "+ additive0;
	           			 Log.d(LOG_TAG,"THIS IS THE CODE: "+additive0);
	      			 }
	         		 }
	   	}
	            	
	 
	                fdbh.close();
	            	}
	            	catch(Exception e)
	            	{
	            		e.printStackTrace();
	            		Log.d(LOG_TAG,"NULL POINTER TRAINISH JSON PLAN");
	            	}
	            	
	        }
		
		}	
	
	
	
	private class JSONParse1 extends AsyncTask<String, String, JSONObject> {
		
//		   private  String urlTransit = "http://ec2-54-211-193-175.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=TRANSIT,WALK&maxWalkDistance=6436&min=TRANSFERS&time=4:30pm&date=10/30/2013&showIntermediateStops=true&toPlace=";
		   private  String urlTransit = "http://ec2-184-73-126-14.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=TRANSIT,WALK&maxWalkDistance=6436&min=TRANSFERS&time="+time+"&date="+date+"&showIntermediateStops=true&toPlace=";
		   //&numItineraries=3&optimize=TRANSFERS
		   private static final String TAG_PLAN = "plan";
		    private static final String TAG_ITN = "itineraries";
		    private static final String TAG_MODE = "mode" ;
		    private static final String TAG_NAME = "name";
		    private static final String TAG_EMAIL = "email";
		    private static final String LOG_TAG= "Main Activity";
		    
	       @Override
	       protected void onPreExecute() {
	           super.onPreExecute();
	           urlTransit=urlTransit+endLat+","+endLng+"&fromPlace="+startLat+","+startLng;
	           
	       //    Toast.makeText(getApplicationContext(), "urlTransit->"+urlTransit, Toast.LENGTH_LONG).show();
	          //  name1 = (TextView)findViewById(R.id.name);

	       }
	       protected JSONObject doInBackground(String... args) {
	           JParser jParser = new JParser();

	           // Getting JSON from URL
	            jsonTransit = jParser.getJSONFromUrl(urlTransit);
		           Log.i("DEV URL",urlTransit);

	            if (jsonTransit != null ){
	         	   Log.d(LOG_TAG,"JSON TRANSIT IS NOT NULL");
	         	   }
	            else{
	         	   Log.d(LOG_TAG,"JSON TRANSIT IS NULL");}
	            return jsonTransit;
		
		
		}
		
	       @Override
	       protected void onPostExecute(JSONObject jsonTransit) {
	        //   pDialog.dismiss();
				
				
				try {
	           	
	                  // Getting JSON Array
	           	try{
	           		FareDBHelper fdbh= new FareDBHelper(getApplicationContext());
	           		try {
						fdbh.createDataBase();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		try {
						fdbh.openDataBase();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	           Log.i("JSON TRANSIT",jsonTransit.getJSONObject("plan").getString("date"));
	           	JSONObject json_plan1 = jsonTransit.getJSONObject("plan");
	           	String date_plan0 = json_plan1.getString("date");
	           	Log.d(LOG_TAG,date_plan0);
	           	
	           	
	           	//if (JSONObject == null){}
	           	JSONArray json_plan_itn1 =json_plan1.getJSONArray(TAG_ITN);
	           	int arrLength = json_plan_itn1.length();
	           	Log.d(LOG_TAG+"Length",Integer.toString(arrLength));
	        	for(int k=0;k<arrLength;k++)        	{
	        		JSONObject ababb = json_plan_itn1.getJSONObject(k);

	        				String trans = ababb.getString("transfers");
	        				long dur = Long.valueOf(ababb.getString("duration"));
	        				int n =k;
	        				String index = Integer.toString(n);
	        				long minutes = TimeUnit.MILLISECONDS.toMinutes(dur);
	        				long seconds = TimeUnit.MILLISECONDS.toSeconds(dur);
	        				Log.i("YAY!","INSIDE LOOP jsonTransit:K is :"+Integer.toString(k)+" minutes:"+Long.toString(minutes)+" seconds:"+Long.toString(seconds)+"index:"+index+"  Transfers:"+trans);
	        				String rupeeSymbol = getApplicationContext().getString(R.string.rupees);
	        				String clockSymbol = getApplicationContext().getString(R.string.clock);
	        				String hm = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(dur),
	        		                TimeUnit.MILLISECONDS.toMinutes(dur) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(dur)));
	        				
	                		String fared = new String("0");
	                		String firstFared = new String("0");
	                		String busName ="";
	              			int dist = 0;
	              			int fare = 0;
	              			int rail=0;
	              			int bus=0;
	              			int totalBusFare = 0;
	              			int extraFare = 0;
	                		
	                		JSONArray legsArray =ababb.getJSONArray("legs");
	                		int totalTrainFare = 0;
	                		int totalFirstTrainFare = 0;
	                		int finalTransfers = 0;

	                		for (int j = 0;j < legsArray.length(); j++)
	                		{     String insideLegsFromStopId="";
	                		String insideLegsToStopId="";
	                		
	                  			 JSONObject insideLegs = legsArray.getJSONObject(j);
	                  			if(insideLegs.getString("mode").equalsIgnoreCase("RAIL")){
	                  				insideLegsFromStopId =insideLegs.getJSONObject("from").getJSONObject("stopId").getString("id");
	                  				insideLegsToStopId=insideLegs.getJSONObject("to").getJSONObject("stopId").getString("id");
	                  			
	                  			
	                  			int fromId = Integer.parseInt(insideLegsFromStopId);
	                  			int toId = Integer.parseInt(insideLegsToStopId);
	                  		
	                    			
	                    		int	singleFare=fdbh.getFareFromFareTable(fromId,toId);
	                    		int singleFirstFare = fdbh.getFirstFareFromFareTable(fromId,toId);
	                    		totalTrainFare+=singleFare ;
	                    		totalFirstTrainFare+=singleFirstFare ;   
	                    		rail =rail+1;
	                    		finalTransfers=finalTransfers+1;
	                    	
	                  			
	                		}
	                  			
	                  			if(insideLegs.getString("mode").equalsIgnoreCase("BUS")){
	                  				busName = insideLegs.getString("route");
	                  				int distance= (int)Double.parseDouble(insideLegs.getString("distance"));
	                  				dist =  distance/1000 ;
	                  				if(busName.substring(busName.length() - 1).equalsIgnoreCase("E"))
	                  				{
	                  					if (dist>50)
	                  					{extraFare = (((dist-50)/5)+1)*20;
	                  					dist=50;}
	                  				}
	                  				else
	                  				{
	                  					if (dist>50)
	              					{extraFare = (((dist-50)/5)+1)*20;
	              					dist=50;}
	                  				}
	                  				 
	                  				fare = fdbh.getBusFare(busName, dist);
	                  				
	                  			bus =bus+1;
	                  			
	                  				totalBusFare+=fare ;
		                    		finalTransfers=finalTransfers+1;

	                  				
	                    		                			
	                  			
	                		}
	                  			if(insideLegs.getString("mode").equalsIgnoreCase("WALK"))
	                  			{finalTransfers=finalTransfers-1;}
	                  			}
	                		
	                		
	                		
	                		fared =Integer.toString(totalTrainFare+totalBusFare+extraFare);
	                		firstFared =Integer.toString(totalFirstTrainFare+totalBusFare+extraFare);
	                		

	                		int ft = rail+bus;
	                
	                	if(rail>=1 && bus>=1)
	                	{
	                		flagArrayTransit.add(k);
	                	
	                		Transit.add(clockSymbol+hm+"  "+rupeeSymbol+fared+"/"+rupeeSymbol+firstFared+"  Transfers:"+ft);
	                		}
	                /*
	                	else
	                		{
	                			flagArrayTransit.add("0/99");
	                			}*/
	                    		
	                    //		Toast.makeText(getApplicationContext(), "TF:"+finalTransfers, Toast.LENGTH_SHORT).show();
	                    		
	                		
	        	}
	                		
	                		
	                	/*	
	        				unsortTransit.put(dur,clockSymbol+hm+"  "+rupeeSymbol+fared+"/"+rupeeSymbol+firstFared+"  Transfers:"+finalTransfers);	
	        				Map<Long,String> sortTransit= new TreeMap<Long,String>(unsortTransit);
	        				for(Map.Entry entry :sortTransit.entrySet())
	        				{
	        					trialTransit.add((String)entry.getValue());
	        				}
	        				Log.i("My Transit!",unsortTransit.get(k));
	        	*/
	           	
	           	String idTransit = "";
	           	
	           	JSONObject ababb = json_plan_itn1.getJSONObject(0); 
	           	//id = id + ababb.getString("transitTime");
	           	Log.d(LOG_TAG, "ZOPPY  200"+idTransit);
	           	{
	           		 HashMap<String, String> map = new HashMap<String, String>();
	           		 JSONObject c0 = json_plan_itn1.getJSONObject(0);
	           		 JSONArray legs0 = c0.getJSONArray("legs");
	           	
	           		 for (int j = 0;j < legs0.length(); j++)
	           		 {
	           			 JSONObject idObj0 = legs0.getJSONObject(j);
	           			 String additive0 = idObj0.getString("mode");
	           			 Log.d(LOG_TAG,additive0);
	           			 if(additive0.equalsIgnoreCase("WALK"))
	           			 {
	           				idTransit =idTransit + " "+ additive0; 
	           			 }
	           			 else if(additive0.equalsIgnoreCase("RAIL")){
	           				idTransit =idTransit + " "+ additive0;
	           			 }
	           			 else if(additive0.equalsIgnoreCase("BUS")){
	           				idTransit =idTransit + " "+ additive0;
	           			 }
	           		 }
	           		 
	           		 
	           		
	           	}
	           	
	           	
	           	
	       //    	name1.setText(idTransit);
	               fdbh.close();
	               
	                

	           	}
	           	catch(NullPointerException e)
	           	{
	           		e.printStackTrace();
	           		Log.d(LOG_TAG,"NULL POINTER TRANSIT JSON PLAN");
	           	}

	          } catch (JSONException e) {
	              e.printStackTrace();
	          }

	       }
		
		}
	
 	private class JSONParse2 extends AsyncTask<String, String, JSONObject> {
        //  private ProgressDialog pDialog;
 	    private static final String TAG_PLAN = "plan";
 	    private static final String TAG_ITN = "itineraries";
 	    private static final String TAG_MODE = "mode" ;
 	    private static final String TAG_NAME = "name";
 	    private static final String TAG_EMAIL = "email";
 	    private static final String LOG_TAG= "Main Activity";
 	  
 		JSONObject jsonBusish;
 		
// 	      private  String urlBusish = "http://ec2-184-73-126-14.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=BUSISH,WALK&maxWalkDistance=6436&min=TRANSFERS&time=4:30pm&date=10/30/2013&showIntermediateStops=true&toPlace=";
	      private  String urlBusish = "http://ec2-184-73-126-14.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=BUSISH,WALK&maxWalkDistance=6436&min=TRANSFERS&time="+time+"&date="+date+"&showIntermediateStops=true&toPlace=";
         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             urlBusish=urlBusish+endLat+","+endLng+"&fromPlace="+startLat+","+startLng;
       //      Toast.makeText(getApplicationContext(), "urlBusish->"+urlBusish, Toast.LENGTH_LONG).show();
     //         email1 = (TextView)findViewById(R.id.email);

         }
         protected JSONObject doInBackground(String... args) {
             JParser jParser = new JParser();

             // Getting JSON from URL
              jsonBusish = jParser.getJSONFromUrl(urlBusish);
              
              if (jsonBusish != null ){
           	   Log.d(LOG_TAG,"JSON BUSISH IS NOT NULL");
           	   Log.i("myJSONBusish",jsonBusish.toString());
           	   }
              else{
           	   Log.d(LOG_TAG,"JSON BUSISH IS NULL");}
              return jsonBusish;
  	
  	
  	}
         @Override
         protected void onPostExecute(JSONObject jsonBusish) {
       	  
       	  
  			try {              	
                    // Getting JSON Array
             	try{    
              		
             		FareDBHelper fdbh= new FareDBHelper(getApplicationContext());

             Log.i("JSON BUSISH NULL",jsonBusish.getJSONObject("plan").getString("date"));
             	JSONObject json_plan2 = jsonBusish.getJSONObject("plan");
             	String date_plan2 = json_plan2.getString("date");
             	Log.d(LOG_TAG,date_plan2);
             	
             	
             	
             	
             	JSONArray json_plan_itn2 =json_plan2.getJSONArray(TAG_ITN);
             	
             	
             	int arrLength = json_plan_itn2.length();
           	for(int k=0;k<arrLength;k++)
           	{
           		JSONObject ababb = json_plan_itn2.getJSONObject(k);
           		Log.i("YAY!","INSIDE LOOP jsonBusish"+ Integer.toString(k));

           				String trans = ababb.getString("transfers");
           				long dur = Long.valueOf(ababb.getString("duration"));
           				int n = k;
           				String index = Integer.toString(n);
           				long minutes = TimeUnit.MILLISECONDS.toMinutes(dur);
           				long seconds = TimeUnit.MILLISECONDS.toSeconds(dur);
           				Log.i("YAY!","INSIDE LOOP jsonBusish:K is :"+Integer.toString(k)+" minutes:"+Long.toString(minutes)+" seconds:"+Long.toString(seconds)+"index:"+index+"  Transfers:"+trans);
           				String rupeeSymbol = getApplicationContext().getString(R.string.rupees);
           				String clockSymbol = getApplicationContext().getString(R.string.clock);
           				String hm = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(dur),
           		                TimeUnit.MILLISECONDS.toMinutes(dur) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(dur)));
           				String busName ="";
                 			int dist = 0;
                 			int fare = 0;
                 			int totalBusFare = 0;
                 			int extraFare = 0;
                 			String fared = new String("0");
           				
           				
           				JSONArray legsArray =ababb.getJSONArray("legs");
           				int finalTransfers =legsArray.length();
           				for (int j = 0;j < legsArray.length(); j++)
                   		{
           					
           					 JSONObject insideLegs = legsArray.getJSONObject(j);
           				
         	if(insideLegs.getString("mode").equalsIgnoreCase("BUS")){
                 				busName = insideLegs.getString("route");
                 				int distance= (int)Double.parseDouble(insideLegs.getString("distance"));
                 				dist =  distance/1000 ;
                 				if(busName.substring(busName.length() - 1).equalsIgnoreCase("E"))
                 				{
                 					if (dist>50)
                 					{extraFare = (((dist-50)/5)+1)*20;
                 					dist=50;}
                 				}
                 				else
                 				{
                 					if (dist>50)
             					{extraFare = (((dist-50)/5)+1)*20;
             					dist=50;
             					}
                 				}
                 				 
                 				fare = fdbh.getBusFare(busName, dist);
                 				
                 			
                 			
                 				totalBusFare+=fare ;
                 				
                 				
                   		                			
                 			
               		}
         	if(insideLegs.getString("mode").equalsIgnoreCase("WALK"))
 			{finalTransfers=finalTransfers-1;}
 			}
         	
           				fared = Integer.toString(totalBusFare+extraFare);
           				
           				Busish.add(clockSymbol+hm+"  "+rupeeSymbol+fared+"  Transfers:"+finalTransfers);
           			//Log.i("ZOPPER BUSISH :" ,Busish.get(k));
           				//unsortBusish.put(dur,clockSymbol+hm+"  "+rupeeSymbol+fared+"  Transfers:"+finalTransfers);	
           				//Map<Long,String> sortBusish= new TreeMap<Long,String>(unsortBusish);
           				//for(Map.Entry entry :sortBusish.entrySet())
           				//{
           				//	trialBusish.add((String)entry.getValue());
           				//}
           				Log.i("My Busish!",Busish.get(k));
           	}
             	
             	
             	
             	
             	String idBusish = "";
             	
             	JSONObject ababber = json_plan_itn2.getJSONObject(0); 
             	Log.d(LOG_TAG, "ZOPPY  "+idBusish);
             	{
             		 HashMap<String, String> map = new HashMap<String, String>();
             		 JSONObject c2 = json_plan_itn2.getJSONObject(0);
             		 JSONArray legs2 = c2.getJSONArray("legs");
    
             		 for (int j = 0;j < legs2.length(); j++)
             		 {
             			 JSONObject idObj2 = legs2.getJSONObject(j);
             			 String additive2 = idObj2.getString("mode");
             			 Log.d(LOG_TAG,additive2);
             			 if(additive2.equalsIgnoreCase("WALK"))
             			 {
             			 idBusish =idBusish + " "+ additive2; 
             			 }
             			 else if(additive2.equalsIgnoreCase("BUS")){
             				idBusish =idBusish + " "+ additive2;
             			 }
             		 }
}
          //   	email1.setText(idBusish);
              	}
             	catch(NullPointerException e)
             	{
             		e.printStackTrace();
             		Log.d(LOG_TAG,"NULL POINTER BUSISH JSON PLAN");
             	}

            } catch (JSONException e) {
                e.printStackTrace();
            }
  			callDisplayActivity();
         }
       protected void callDisplayActivity()
       {
       	
    	   
    	   
    	
    	   
    	   
    	   
    	   
    	   
    	   Intent intent = new Intent(getApplicationContext(), ActivityMainDisplay.class);
      	 intent.putStringArrayListExtra("Train", Trainish);
      	 intent.putStringArrayListExtra("Bus", Busish);
      	 intent.putStringArrayListExtra("Both", Transit);
      	 intent.putStringArrayListExtra("fares", fareList);
      	intent.putIntegerArrayListExtra("flagArrayTransit", flagArrayTransit);
      	 Bundle b = new Bundle();
      	 b.putString("wString",weaString);
      	
      	         	 
      	 b.putString("jsonTrainish",jsonTrainish.toString());
      	 b.putString("jsonTransit",jsonTransit.toString());
      	 b.putString("jsonBusish",jsonBusish.toString());
      	 b.putString("duration",dur);
      	 b.putDouble("startLat", startLat);
      	 b.putDouble("startLng", startLng);
      	 b.putString("distance",dist);
      	 b.putString("sourceHead",sourceHead);
      	 b.putString("destHead",destHead);
      	 intent.putExtras(b);
      	 intent.putExtra("json", jObj.toString());
          startActivity(intent);  
    	   
    	   
    	   
    	   
    	   
    	   
    	   
    	   
    	   
    	   
    	   
    	   
    	   
    	   
    	   
    	   /*
       	 Intent intent = new Intent(getApplicationContext(), ActivityMainDisplay.class);
       	 intent.putStringArrayListExtra("Train", Trainish);
       	 intent.putStringArrayListExtra("Bus", Busish);
       	 intent.putStringArrayListExtra("Both", Transit);
       	 intent.putStringArrayListExtra("fares", fare);
       	 
       	 Bundle b = new Bundle();
       	         	 
       	 b.putString("jsonTrainish",jsonTrainish.toString());
       	 b.putString("jsonTransit",jsonTransit.toString());
       	 b.putString("jsonBusish",jsonBusish.toString());
       	 b.putString("duration",dur);
       	 b.putDouble("distance", dist);
       	 b.putDouble("startLat", startLat);
       	 b.putDouble("startLng", startLng);
       	 intent.putExtras(b);
       	 intent.putExtra("json", jobj.toString());
           startActivity(intent);  
           Trainish.clear();
      	 Busish.clear();
      	 Transit.clear();
      	 */
      	 
       }  
         
         
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	 public void setAdapt(ArrayList<String> fares)
	 {
		 CustomAdapterAks adapter = new CustomAdapterAks(this, R.layout.displaymap, fares,this);
		 AnimationSet set = new AnimationSet(true);

		 

	        Animation animation = new AlphaAnimation(0.0f, 1.0f); 

	        animation.setDuration(500);

	        set.addAnimation(animation);

	        animation = new TranslateAnimation(

	                Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,

	                Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f

	            );
	        
	        animation.setDuration(100);

	        set.addAnimation(animation);
	        
	        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
	        
	//        listViewItems.setAdapter(adapter);
		 
	//	 listViewItems.setLayoutAnimation(controller);
	 }
	
	 
	 private void actionBarSetup() {
	   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	     ActionBar ab = getActionBar();
	     ab.setTitle("Select Route");
	     
	   }
	 }
	
	
}
