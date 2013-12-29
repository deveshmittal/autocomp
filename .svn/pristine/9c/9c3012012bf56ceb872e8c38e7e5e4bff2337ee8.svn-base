package com.example.autocomp;


import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
public class ActivityDetailedSummary extends Activity {
	JSONObject currlegJSONObject;
	ArrayList<String> myArrLatLongList = null;
	ArrayList<String> fullPolyLine= null;
	 ListView listViewItems=null;
	 String weaString=null;
	 ArrayList<String> polyMap= null;
	 ArrayList<String> myList =new ArrayList<String>();
	    ArrayList<String> arList = new ArrayList<String>();
	    ArrayList<String> brList = new ArrayList<String>();
	   // getSomeStops
	    String stopsAdd=new String();
	    String polyAdd=new String();
	    String stopsSomeAdd="";
	    JSONObject currJSONObject= new JSONObject();
	    ArrayList<String> flagArrayTransit= null;

//	  HashMap<Integer, ArrayList<String>> phraseMap =
//	            new HashMap<Integer,  ArrayList<String>>();
//	  
//	 ArrayList<HashMap<Integer, ArrayList<String>>> finalArrayMap = new ArrayList<HashMap<Integer, ArrayList<String>>>();
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
{		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_summary);
		String rupeeSymbol = getApplicationContext().getString(R.string.rupees);
		FareDBHelper fdbh= new FareDBHelper(getApplicationContext());
	    RailDataBaseHelper rdbh =new RailDataBaseHelper(getApplicationContext());
	    BusDataBaseHelper bdbh = new BusDataBaseHelper(getApplicationContext());
	    try{
	    	
	    	weaString=getIntent().getExtras().getString("wString");
	    }
	    catch(Exception er)
	    {
	    	
	    }
		try {
			bdbh.createBusDataBase();
		} catch (IOException e) {
			  Toast.makeText(getApplicationContext(), "IOException->"+e.toString(), Toast.LENGTH_LONG).show();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bdbh.openBusDataBase();
		} catch (SQLException e) {
			  Toast.makeText(getApplicationContext(), "SQLException->"+e.toString(), Toast.LENGTH_LONG).show();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    
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
		
		try {
			rdbh.createRailDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rdbh.openRailDataBase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		polyAdd="";
		currlegJSONObject=null;
		currJSONObject = null;
		fullPolyLine= new ArrayList<String>();
		polyMap= new ArrayList<String>();
		myArrLatLongList = new ArrayList<String>();
	listViewItems = (ListView)findViewById(R.id.legsLen);
	
	if(listViewItems == null){
		
		}
	


	  Bundle extras  =getIntent().getExtras();
	  String ObjTillLegs = "";
	  
	  
	  if(extras!=null)
	  	{
		ObjTillLegs = extras.getString("JSONObjectTillLegs");
		flagArrayTransit=extras.getStringArrayList("FlagArrayTransit");
		}
	  
		
		try 
	  {
			currJSONObject= new JSONObject(ObjTillLegs);
		
			
			int legsLen = currJSONObject.getJSONArray("legs").length();
			
			
			for (int i =0; i<legsLen ; i++)
			{	String insideLegsFromStopId="";
    		String insideLegsToStopId="";	
				
				String thisLeg = "";	
			String fare = new String("0");
				
				JSONObject legsJSONObject = currJSONObject.getJSONArray("legs").getJSONObject(i);
				String fromName = legsJSONObject.getJSONObject("from").getString("name");
				
				
		
      			
      			
      			String busName ="";
      			int dist = 0;
      			int fares = 0;
      			int totalBusFare = 0;
      			int extraFare = 0;
      			String fared = new String("0");
      			
      			
      			
      			
      			
      			
      			
				
					String toName = legsJSONObject.getJSONObject("to").getString("name");
					String dur=legsJSONObject.getString("duration");
					String modal=legsJSONObject.getString("mode");
					
					
					if(modal.equalsIgnoreCase("RAIL"))
					{ insideLegsFromStopId=legsJSONObject.getJSONObject("from").getJSONObject("stopId").getString("id");
					
					
					insideLegsToStopId=legsJSONObject.getJSONObject("to").getJSONObject("stopId").getString("id");
						
					int fromId = Integer.parseInt(insideLegsFromStopId);
	      			int toId = Integer.parseInt(insideLegsToStopId);
	      			JSONArray interStops = new JSONArray();
	      			interStops=legsJSONObject.getJSONArray("intermediateStops");
	      			
	      		
	      			
	      			
	      			
	      			
	      			
	      			for(int it=0; it<interStops.length();it++)
	      			{
	      				String arrivalTime= interStops.getJSONObject(it).getString("arrival");
	      				long toMillis = Long.parseLong(arrivalTime);
	      		    	SimpleDateFormat dfTo = new SimpleDateFormat("HH:mm");
	      		    	dfTo.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
	      		    	String toTimeDisp = dfTo.format(toMillis);
	      		    	String lon=interStops.getJSONObject(it).getString("lon");
						String lat =interStops.getJSONObject(it).getString("lat");
						
	      				stopsAdd +=interStops.getJSONObject(it).getString("name")+"-"+toTimeDisp+"#";
	      			}
	    			

						
						
						
						
						thisLeg= thisLeg + "train"+"--";
						//myList.add("train");
						//myList.add(legsJSONObject.getString("route"));
						thisLeg= thisLeg + legsJSONObject.getString("route")+"--";
						//fromTime
						thisLeg= thisLeg + legsJSONObject.getString("startTime")+"--";
						
					// arrival time 
						thisLeg= thisLeg + legsJSONObject.getString("endTime")+"--";
						
						//String route_id = rdbh.getRouteId(legsJSONObject.getString("route"));
						
						SimpleDateFormat df = new SimpleDateFormat("HH:mm");
				    	df.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
				    	String fromTimeDisp = df.format(Long.parseLong(legsJSONObject.getString("startTime")));
				    	SimpleDateFormat dfto = new SimpleDateFormat("HH:mm");
				    	dfto.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
				    	String toTimeDisp = df.format(Long.parseLong(legsJSONObject.getString("endTime")));
						String trip_id = new String() ; 
						trip_id = legsJSONObject.getString("tripId");
						int start_id = fromId;
						int end_id = toId;
//						brList  =rdbh.getInterStops(start_id, end_id, route_id, trip_id);
//						for (String  arString:brList){
//							stopsSomeAdd = arString + "xxx" ;
//							}
						
						
						
//						arList=rdbh.getInterStops(fromId,toId,fromTimeDisp,toTimeDisp,route_id);
//						for (String  arString:arList){
//						stopsAdd += arString + "#" ;
//						}
						
						int	singleFare=fdbh.getFareFromFareTable(fromId,toId);
						
						int singleFirstFare = fdbh.getFirstFareFromFareTable(fromId,toId);
						
						
						fare = rupeeSymbol+Integer.toString(singleFare)+"/"+rupeeSymbol+Integer.toString(singleFirstFare);
						if (singleFare==0||singleFirstFare==0)
						{
							fare="";
						}
						thisLeg= thisLeg +fare+"--";
						thisLeg= thisLeg +stopsAdd+"--";

						stopsAdd ="";
						arList.clear();
					

						
					}
					if(modal.equalsIgnoreCase("BUS"))
					{
						
						busName = legsJSONObject.getString("route");
          				int distance= (int)Double.parseDouble(legsJSONObject.getString("distance"));
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
          				fares = fdbh.getBusFare(busName, dist);
						
						
          				fared = Integer.toString(fares+extraFare);
          																													
          				fare =rupeeSymbol+fared;
          				if (fares==0)
						{
							fare="";
						}
						

    	      		
    				
    	      			
						
						//myList.add("bus"); 
						thisLeg= thisLeg + "bus"+"--";
						thisLeg= thisLeg + legsJSONObject.getString("route")+"--";
					
						//fromTime
						thisLeg= thisLeg + legsJSONObject.getString("startTime")+"--";
						
					// arrival time 
						thisLeg= thisLeg + legsJSONObject.getString("endTime")+"--";
						
						int start_id=Integer.parseInt(legsJSONObject.getJSONObject("from").getJSONObject("stopId").getString("id"));
						int end_id=Integer.parseInt(legsJSONObject.getJSONObject("to").getJSONObject("stopId").getString("id"));
						String trip_id = legsJSONObject.getString("tripId");
						
						JSONArray interStops = new JSONArray();
		      			interStops=legsJSONObject.getJSONArray("intermediateStops");
		      			for(int it=0; it<interStops.length();it++)
		      			{
		      				String arrivalTime= interStops.getJSONObject(it).getString("arrival");
		      				long toMillis = Long.parseLong(arrivalTime);
		      		    	SimpleDateFormat dfTo = new SimpleDateFormat("HH:mm");
		      		    	dfTo.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
		      		    	String toTimeDisp = dfTo.format(toMillis);
		      				stopsAdd +=interStops.getJSONObject(it).getString("name").trim()+"#";
		      			}
						
						
//						
//						brList= bdbh.getBusStops( start_id, end_id, trip_id);
//						
//						for (String  brString:brList){
//							Log.i("ArrayList added", stopsAdd);
//						stopsAdd += brString + "#" ;
//						}
						
						
						
						thisLeg= thisLeg +fare+"--";
						thisLeg= thisLeg +stopsAdd+"--";
						stopsAdd = "";

						
						//myList.add(legsJSONObject.getString("route"));
					//	Log.i("LEGS  IRAN !:",myList.get(1));
						
					}
					if(modal.equalsIgnoreCase("WALK"))
					{
						
						thisLeg= thisLeg + "walking"+"--";
						thisLeg= thisLeg + legsJSONObject.getString("route")+"--";
						//fromTime
						thisLeg= thisLeg + legsJSONObject.getString("startTime")+"--";
						
					// arrival time 
						thisLeg= thisLeg + legsJSONObject.getString("endTime")+"--";
						thisLeg= thisLeg +" "+"--";
						thisLeg= thisLeg +" "+"--";

						//myList.add("walking"); 
						//myList.add("onRoad");
						
					
				//myList.add("RAIL"); 
					}
					
					thisLeg= thisLeg +fromName+"--";
					thisLeg= thisLeg +toName+"--";
					thisLeg= thisLeg +dur+"--";
					
					fullPolyLine.add(legsJSONObject.getJSONObject("legGeometry").getString("points"));
					String temp=legsJSONObject.getJSONObject("legGeometry").getString("points")+" "+modal;
					//polyMap.put(legsJSONObject.getJSONObject("legGeometry").getString("points"), modal);
					polyMap.add(temp);
					thisLeg= thisLeg +legsJSONObject.getJSONObject("legGeometry").getString("points");
					 
					polyAdd= legsJSONObject.getJSONObject("legGeometry").getString("points");
					
					 

					myList.add(thisLeg);
}	
			
} 
		catch (JSONException e) 
{
			Toast.makeText(getApplicationContext(), "JSONException->"+e.toString(), Toast.LENGTH_LONG).show();
			// TODO Auto-generated catch block
			e.printStackTrace();
}
		catch (NullPointerException exp) 
		{
			Toast.makeText(getApplicationContext(), "NullPointerException->"+exp.toString(), Toast.LENGTH_LONG).show();
					exp.printStackTrace();
		}
		
		setAdapt(myList);
	
}
	
	
	 public void setAdapt( ArrayList<String>  myArrayMap)
     {
	
		 CustomAdapterDev adapter = new CustomAdapterDev(this, R.layout.activity_detailed_summary, myArrayMap);
		 
         
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
            
            
            
            if(listViewItems != null){
            listViewItems.setAdapter(adapter);} else{}
            listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            	

          
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
            //		position =  leg 
    				
					try {
						currlegJSONObject = currJSONObject.getJSONArray("legs").getJSONObject(position);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Toast.makeText(getApplicationContext(), "JSONException->"+e.toString(), Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
    				
    				
    				
    				
    				
    				
					try {
						polyAdd= currlegJSONObject.getJSONObject("legGeometry").getString("points");
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

    				
    				
    				
    				try {
						if(currlegJSONObject.getString("mode").equalsIgnoreCase("RAIL")||currlegJSONObject.getString("mode").equalsIgnoreCase("BUS"))
						{
							
							
							JSONArray interStops = new JSONArray();
							interStops=currlegJSONObject.getJSONArray("intermediateStops");
							for(int it=0; it<interStops.length();it++)
							{
								
								String lon=interStops.getJSONObject(it).getString("lon");
								String lat =interStops.getJSONObject(it).getString("lat");
								
								String latlong = lon+","+lat;
								myArrLatLongList.add(latlong);
								
							}

						}
					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(), "JSONException->"+e.toString(), Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
    				try {
						if(currlegJSONObject.getString("mode").equalsIgnoreCase("WALK"))
						{

							JSONArray steps=currlegJSONObject.getJSONArray("steps");
							int stepsLen = steps.length();
							for(int iterator=0;iterator<stepsLen ;iterator++)
							{
							String lon=steps.getJSONObject(iterator).getString("lon");
							String lat =steps.getJSONObject(iterator).getString("lat");
							
							String latlong = lat+","+lon;
							myArrLatLongList.add(latlong);
							}
						}
					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(), "JSONException->"+e.toString(), Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
					 Intent myIntent = new Intent(view.getContext(), MappingAct.class);
					 Bundle bundle  = new Bundle();
					 
					 //bundle.putStringArrayList("latlong", myArrLatLongList);
					 bundle.putString("LegPolyLine", polyAdd);
					 bundle.putString("Type", "PolyString");
					 bundle.putStringArrayList("polyLinesHashMap", polyMap);
					 bundle.putString("wString",weaString);
					 myIntent.putExtras(bundle);
			//		 myIntent.putStringArrayListExtra("latlong",myArrLatLongList);
					
					    startActivity(myIntent);
				
				
				}
            
            });
          
           
           
           
         listViewItems.setLayoutAnimation(controller);
 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detailed_summary, menu);
		
		return true;
	}
	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {
	   
		MenuItem item = menu.findItem(R.id.item_text);

	    // Use this if you set with default actionbar item
	    //item.setTitle("sampleText");

	    TextView tv = (TextView)MenuItemCompat.getActionView(item).findViewById(R.id.ab_text);
	    String actText="Trip Detailed Summary";
	    tv.setText(actText);
	    return super.onPrepareOptionsMenu(menu);
		
		// final MenuItem menuItem = this.mMenu.findItem(R.id.user_id_label);  
	   // menuItem.setTitle(getString(R.string.user_id_text));
	  //  menuItem.setTitle(getString(R.string.user_id_text,
	  //          getIntent().getIntExtra(ConnectToServerActivity.USER_ID, -1)));
	 //   return super.onPrepareOptionsMenu(aMenu);
	}
	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
	    case R.id.mapper:
	      
	      
	      Intent actionBarIntent = new Intent(this, MappingAct.class);
	      
	      Bundle bundle  = new Bundle();
	      
	      bundle.putStringArrayList("completePolyLine", fullPolyLine);
	//      bundle.putSerializable("polyLinesHashMap", polyMap);
	      bundle.putStringArrayList("polyLinesHashMap", polyMap);
	      bundle.putString("wString",weaString);
	      bundle.putString("Type", "PolyArrayList");
			// bundle.putStringArrayList("complete", myArrLatLongList);
			// bundle.putString("Type", "Transit");
			 actionBarIntent.putExtras(bundle);
	//		 myIntent.putStringArrayListExtra("latlong",myArrLatLongList);
			
			    startActivity(actionBarIntent);
	      
	      break;
	    /*
	      case R.id.menuitem2:
	      Toast.makeText(this, "Menu item 2 selected", Toast.LENGTH_SHORT)
	          .show();
	      break;
	      */

	    default:
	      break;
	    }

	    return true;
	  } 

}