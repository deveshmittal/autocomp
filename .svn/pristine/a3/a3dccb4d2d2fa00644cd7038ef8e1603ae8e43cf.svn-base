package com.example.autocomp;





import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MarkerTest extends Activity {

String stop1=null;
String stop1Temp=null;
String stop2=null;
String stop3=null;
String dept1=null;
String dept2=null;
String dept3=null;

String stopiD1=null;
String stopiD2=null;
String stopiD3=null;




String temp=null;
String trip1Text="";
String intiD="";
String tempText="";
String tempstart="";
String tempend="";
String tempdep="";
String [] tripArr=null;
String [] stopArr=null;
String [] deptArr=null;
String [] tempTest=null;
ListView listViewItems=null;
ArrayList<String> trips=null;
ArrayList<String> stops=null;
ArrayList<String> stopsId=null;
ArrayList<String> deptime=null;
ArrayList<String> stopsDisp=null;
JSONObject 	stopObj = null;
Map<String,String> mp=null;
LinkedHashMap<String,String> lhm = new LinkedHashMap<String,String>();;
TextView trip1=null;
Spinner stops_spinner=null;
Map<Integer,String> mpFin=null;
int index=0;
String trip="";
String endName="";
String depTime="";
String startName="";
JSONArray FixArray =null;
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marker_test);
		Bundle extras = getIntent().getExtras();
		String tempText=null;
		 FixArray = new JSONArray();
		stopsId=new ArrayList<String>(); 
		try{
		if (extras != null)
		{
			
			intiD=extras.getString("initId");
		}
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(),"exception->"+e.toString(), Toast.LENGTH_LONG).show();
			
		}
		
	
		
		final DataBaseHelper dbh=new DataBaseHelper(getApplicationContext());
		try {
			dbh.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			dbh.openDataBase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try{
		FixArray=dbh.getTrips(intiD);
		}
		catch(Exception ee)
		{
			Toast.makeText(getApplicationContext(),"Exception->"+ee.toString(), Toast.LENGTH_LONG).show();
		}
		//dbh.close();
		
		HashMap<String,ArrayList<String>> hm=new HashMap<String,ArrayList<String>>();
		ArrayList<String> arList=null;
		ArrayList<String> arListholder=new ArrayList<String>();
		 trips=new ArrayList<String>();
		 stopsDisp=new ArrayList<String>();	
		 String line="";
		 String dir=null;
		for (int i=0;i<FixArray.length();i++)
		{
			try {
				stopObj= FixArray.getJSONObject(i);
				startName=(String)stopObj.get("StartName");
				endName=(String)stopObj.get("EndName");
				depTime=(String)stopObj.get("DepTime");
				trip=(String)stopObj.get("tripId");
				line=(String)stopObj.get("Line");
				dir=(String)stopObj.get("direction");
				trips.add(trip);
				tempText=startName + " - "+endName +" "+depTime+"/"+line;
				
				if(hm.containsKey(dir))
				{
					arList=hm.get(dir);
					arList.add(tempText);
					hm.put(dir, arList);
					
				}
				else{
					arListholder.add(tempText);
					hm.put(dir, arListholder);
				}
				
				
				stopsDisp.add(tempText);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		Iterator it = hm.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	   //     System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        
	        Log.i("MarkerTest","pairs.getKey()->"+pairs.getKey()+" ,pairs.getValue()->"+(pairs.getValue()).toString());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		CustomAdapterTrain adapter = new CustomAdapterTrain(this, R.layout.activity_marker_test, stopsDisp,this);

		
		listViewItems = (ListView) findViewById(R.id.listView);
	
		 Animation animation = new AlphaAnimation(0.0f, 1.0f);

         animation.setDuration(500);
         AnimationSet set = new AnimationSet(true);
         set.addAnimation(animation);

         animation = new TranslateAnimation(

                 Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,

                 Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f

             );
        
         animation.setDuration(100);

         set.addAnimation(animation);
        
         LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        
         listViewItems.setAdapter(adapter);
      
      listViewItems.setLayoutAnimation(controller);
		
		
		
		
		
		
		
		
		
		
		//listViewItems.setAdapter(adapter);
		listViewItems.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	                long arg3) {
	        	
	        	index=arg2;
	        	
	            registerForContextMenu(listViewItems);
	            listViewItems.showContextMenu();
	            String selectedItem=(String)((TextView)arg1.findViewById(R.id.textV)).getText();
	            /*
	            tempTest=selectedItem.split("\\-");
	            tempstart=tempTest[0];
	            tempend=tempTest[1];
	            String [] test=tempTest[2].split("\\>");
	            String dept=test[1];
	            */
	           String tripTest= trips.get(arg2);
	           
	           ArrayList<String> ar=new ArrayList<String>();
	           ar=dbh.getStops(tripTest);
	         
	           Intent intent = new Intent(MarkerTest.this, StopsAct.class);
	           intent.putStringArrayListExtra("stoplist", ar);
	           startActivity(intent);        
	            	            
	           
	        }
	    });
		
		

	}
	
	/*
	
	public void setSpinner()
	{
		
		 	stops_spinner = (Spinner) findViewById(R.id.stops_spinner);
			ArrayAdapter<String> adapterArr = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,stopsDisp);
			adapterArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			stops_spinner.setAdapter(adapterArr);
	
	}
	
	*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.marker_test, menu);
		return true;
	}

}
