package com.example.autocomp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
 
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;

import android.content.Intent;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;

import android.view.Menu;
import android.view.View;

import android.widget.Button;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
//Not USED ,
//In USE DISPlAY MAP

public class ActivityTransit extends Activity {
	
	
	Map<Long,String> unsortTrainish= new HashMap<Long,String>();
	//Map<Long,String> sortTrainish= new TreeMap<Long,String>();
	ArrayList<String> Trainish= new ArrayList<String>();
	ArrayList<String> trialTrainish= new ArrayList<String>();

	
	//Map<Long,String> unsortBusish= new HashMap<Long,String>();
	ArrayList<String> Busish = new ArrayList<String>();
	//Map<Long,String> sortBusish= new TreeMap<Long,String>();
	ArrayList<String> trialBusish = new ArrayList<String>();


	//Map<Long,String> unsortTransit= new HashMap<Long,String>();
	ArrayList<String> Transit = new ArrayList<String>(); 
	//Map<Long,String> sortTransit= new TreeMap<Long,String>();
	ArrayList<String> trialTransit = new ArrayList<String>(); 

	
	

	//vars
	int transitTotal;
	TextView uid;
    TextView name1;
    TextView email1;
    Button Btngetdata;
    static String setTime = "4:30pm";
    static String setDate ="10/30/2013";
    static String toLatlong = "18.9037004,72.81314320000001";
    static String fromLatlong = "19.079355000000000000,73.000033000000030000";
    float startLat=0.0f;
    float startLng=0.0f;
    float endLat=0.0f;
    float endLng=0.0f;
    String dur="";
    double dist=0.0;
    JSONObject jobj=null;
    ArrayList<String> fare=null;
    private ProgressDialog pDialog;
    //URL to get JSON Array
    //18.9037004,72.81314320000001&fromPlace=19.079355000000000000,73.000033000000030000
    //private static String urlTrainish = "http://ec2-54-211-193-175.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=TRAINISH,WALK&maxWalkDistance=6436&min=TRANSFERS&time=4:30pm&date=10/30/2013&showIntermediateStops=true&toPlace=18.9037004,72.81314320000001&fromPlace=19.079355000000000000,73.000033000000030000";
    private static String urlTrainish = "http://ec2-54-211-193-175.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=TRAINISH,WALK&maxWalkDistance=6436&min=TRANSFERS&time=4:30pm&date=10/30/2013&showIntermediateStops=true&toPlace=";
    private static String urlTransit = "http://ec2-54-211-193-175.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=TRANSIT,WALK&maxWalkDistance=6436&min=TRANSFERS&time=4:30pm&date=10/30/2013&showIntermediateStops=true&toPlace=";
       private static String urlBusish = "http://ec2-54-211-193-175.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=BUSISH,WALK&maxWalkDistance=6436&min=TRANSFERS&time=4:30pm&date=10/30/2013&showIntermediateStops=true&toPlace=";

  // private static String urlTrainish = "http://ec2-54-211-193-175.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=TRAINISH,WALK&maxWalkDistance=6436&min=TRANSFERS&time="+setTime+"&date="+setDate+"&showIntermediateStops=true&toPlace="+toLatlong+"&fromPlace="+fromLatlong&numItineraries=3&optimize=TRANSFERS;
//	private static String urlTransit = "http://ec2-54-211-193-175.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=TRANSIT,WALK&maxWalkDistance=6436&min=TRANSFERS&time="+setTime+"&date="+setDate+"&showIntermediateStops=true&toPlace="+toLatlong+"&fromPlace="+fromLatlong&numItineraries=3&optimize=TRANSFERS;
 //   private static String urlBusish = "http://ec2-54-211-193-175.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=BUSISH,WALK&maxWalkDistance=6436&min=TRANSFERS&time="+setTime+"&date="+setDate+"&showIntermediateStops=true&toPlace="+toLatlong+"&fromPlace="+fromLatlong&numItineraries=3&optimize=TRANSFERS;

  /*  
   http://ec2-184-73-126-14.compute-1.amazonaws.com:8080/opentripplanner-api-webapp/ws/plan?mode=TRAINISH,WALK&maxWalkDistance=6436&time=4:30pm&date=10/30/2013&showIntermediateStops=true&toPlace=18.9037004,72.81314320000001&fromPlace=19.079355000000000000,73.000033000000030000&numItineraries=3&optimize=TRANSFERS;
    */
    
    JSONObject jsonTrainish;
    JSONObject jsonBusish;
    JSONObject jsonTransit;
    JSONArray jsonfullArray;
    
    //JSON Node Names 
    private static final String TAG_PLAN = "plan";
    private static final String TAG_ITN = "itineraries";
    private static final String TAG_MODE = "mode" ;
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String LOG_TAG= "Main Activity";
    
   
 
    JSONArray user = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		jsonTrainish=null;
		jsonBusish=null;
		jsonTransit=null;
		transitTotal=0;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_transit);
		 
		fare=new ArrayList<String>();
		Intent dataIntent= getIntent();
		Bundle extras = dataIntent.getExtras();
		if(extras != null)
		{
		startLat=(float)extras.getDouble("startLat");
		startLng=(float)extras.getDouble("startLng");
		endLat=(float)extras.getDouble("endLat");
		endLng=(float)extras.getDouble("endLng");
		dur=(String)extras.getString("dur");
		dist=(float)extras.getDouble("distance");
		}
		fare=dataIntent.getStringArrayListExtra("fareList");
		try {
			jobj= new JSONObject(getIntent().getStringExtra("json"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		Toast.makeText(this, "LATLONG SOURCE: "+startLat+","+startLng, Toast.LENGTH_LONG).show();
		Toast.makeText(this, "LATLONG DEST: "+endLat+","+endLng, Toast.LENGTH_LONG).show();

		Log.d("MyData","startLat->"+startLat);
		Log.d("MyData","startLat->"+startLng);
		Log.d("MyData","endLat->"+endLat);
		Log.d("MyData","endLng->"+endLng);
		urlTrainish=urlTrainish+endLat+","+endLng+"&fromPlace="+startLat+","+startLng;
		urlTransit=urlTransit+endLat+","+endLng+"&fromPlace="+startLat+","+startLng;
		urlBusish=urlBusish+endLat+","+endLng+"&fromPlace="+startLat+","+startLng;
		Log.d("MYLOG"," Trainish->"+urlTrainish);
		
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
    		Log.d("MYLOG","JSONParse() Trainish");
    	    //new JSONParse().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"value1");
    	    new JSONParse().execute();
    		
    		Log.d(LOG_TAG,"JSONParse() GOT ONE Trainish");
    	    //new JSONParse1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"value2");
    		new JSONParse1().execute();
    		Log.d(LOG_TAG,"JSONParse1()GOT TWO Transit");
    	    //new JSONParse2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"value3");
    		new JSONParse2().execute();
    		Log.d(LOG_TAG,"JSONParse1()GOT THREE Busish");
    	} else {
    		Log.d(LOG_TAG,"ELSE Inside thread");
    	    new JSONParse().execute();
    	}
		
		
		
		
		
		/*
		
		 Btngetdata = (Button)findViewById(R.id.getdata);
	     Btngetdata.setOnClickListener(new View.OnClickListener() {
	    	 
	    	
	    	 
	    	 
	    	 
	    	 
	    	 
	    	 
	    	 
	 	            @Override
	            public void onClick(View view) {
	            	if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
	            		Log.d("MYLOG","JSONParse() Trainish");
	            	    //new JSONParse().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"value1");
	            	    new JSONParse().execute();
	            		
	            		Log.d(LOG_TAG,"JSONParse() GOT ONE Trainish");
	            	    //new JSONParse1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"value2");
	            		new JSONParse1().execute();
	            		Log.d(LOG_TAG,"JSONParse1()GOT TWO Transit");
	            	    //new JSONParse2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"value3");
	            		new JSONParse2().execute();
	            		Log.d(LOG_TAG,"JSONParse1()GOT THREE Busish");
	            	} else {
	            		Log.d(LOG_TAG,"ELSE Inside thread");
	            	    new JSONParse().execute();
	            	}
	            	
	            	// new JSONParse().execute();
	 
	            }
	        });
	*/
	}
	
	
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
   
       @Override
       protected void onPreExecute() {
           super.onPreExecute();

			pDialog = new ProgressDialog(ActivityTransit.this);
			pDialog.setMessage("Getting Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
    //        uid = (TextView)findViewById(R.id.uid);
            Log.d("THIS_LOG!","JSON!");
       
       }

       @Override
       protected JSONObject doInBackground(String... args) {
           JParser jParser = new JParser();
       //    Log.i("THIS SHOULD BE NULL",jsonTrainish.toString());
           // Getting JSON from URL
           //Toast.makeText(getApplicationContext(), "urlTrainish->"+urlTrainish, Toast.LENGTH_LONG).show();
           
            jsonTrainish = jParser.getJSONFromUrl(urlTrainish);
            Log.i("THIS SHOULD BE NULL",jsonTrainish.toString());
            /*
           if (jsonTrainish != null  ){
        	   Log.d(LOG_TAG,"JSON TRAINISH IS NOT NULL");
       //        Log.i("THIS SHOULD BE MY JSON ASSHOLE :",jsonTrainish.toString());
               Toast.makeText(getApplicationContext(), "jsonTrainish->"+jsonTrainish.toString(), Toast.LENGTH_LONG).show();
        	   Log.d(LOG_TAG,"ZOPHOP ++"+jsonTrainish.toString());
        	   }
           else{
        	   Toast.makeText(getApplicationContext(), "jsonTrainish->null", Toast.LENGTH_LONG).show();
        	   Log.d(LOG_TAG,"JSON Trainish IS NULL");}
           */
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
            		Trainish.add(clockSymbol+hm+"  "+rupeeSymbol+fared+"/"+rupeeSymbol+firstFared+"  Transfers:"+finalTransfers); 	
            		
            		
            		
                    Log.i("ZOPPER TRAINISH",Trainish.get(k)+" VALUE OF K is:"+k);
            	}
            //		unsortTrainish.put(dur,clockSymbol+hm+"  "+rupeeSymbol+fared+"/"+rupeeSymbol+firstFared+"  Transfers:"+finalTransfers);
           // 		Map<Long,String> sortTrainish= new TreeMap<Long,String>(unsortTrainish);
           // 		for(Map.Entry entry:sortTrainish.entrySet())
            //		{
    			//		trialTrainish.add((String)entry.getValue());
    			//	}
            				
    		//		Log.i("My Trainish!",unsortTrainish.get(k));
            	
            	
            	
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
            	
            	uid.setText(idTrainish);
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
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           
          //  name1 = (TextView)findViewById(R.id.name);

       }
       protected JSONObject doInBackground(String... args) {
           JParser jParser = new JParser();

           // Getting JSON from URL
            jsonTransit = jParser.getJSONFromUrl(urlTransit);
            
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
              			int totalBusFare = 0;
              			int extraFare = 0;
                		int rail =0;
                		int bus= 0;
                		JSONArray legsArray =ababb.getJSONArray("legs");
                		int totalTrainFare = 0;
                		int totalFirstTrainFare = 0;
                		int finalTransfers = legsArray.length();

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
                    		rail=rail+1;
                  			
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
                  				
                  			bus=bus+1;
                  			
                  				totalBusFare+=fare ;
                  				
                  				
                    		                			
                  			
                		}
                  			if(insideLegs.getString("mode").equalsIgnoreCase("WALK"))
                  			{finalTransfers=finalTransfers-1;}
                  			}
                		
                		
                		
                		fared =Integer.toString(totalTrainFare+totalBusFare+extraFare);
                		firstFared =Integer.toString(totalFirstTrainFare+totalBusFare+extraFare);
                		if((rail>=1) && (bus>=1)){
                		Transit.add(clockSymbol+hm+"  "+rupeeSymbol+fared+"/"+rupeeSymbol+firstFared+"  Transfers:"+finalTransfers);
                		Log.i("ZOPPER TRANSIT",Transit.get(k));	
                		}
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
           	
           	
           	
           	name1.setText(idTransit);
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
          @Override
          protected void onPreExecute() {
              super.onPreExecute();
              
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
            				Log.i("ZOPPER BUSISH :" ,Busish.get(k));
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
              	email1.setText(idBusish);
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
       	 
       	 
        }  
          
          
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	//	getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
