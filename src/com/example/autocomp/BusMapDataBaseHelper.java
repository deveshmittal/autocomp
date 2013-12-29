package com.example.autocomp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class BusMapDataBaseHelper extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.example.autocomp/databases/";
 
    private static String DB_NAME = "BusDatsMap.sqlite";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
    
    public String directions="";
    public String queryName="INSERT INTO  Temp (trip_id,arrival_time,departure_time,stop_id,stop_sequence)  SELECT  stop_times.trip_id,stop_times.arrival_time,stop_times.departure_time,stop_times.stop_id,stop_times.stop_sequence   FROM stop_times,trips WHERE stop_times.trip_id=trips.trip_id AND trips.route_id=";
    int routeID=0;
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public BusMapDataBaseHelper(Context context) {
 
    	super(context, DB_NAME, null, 3);
        this.myContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    	Log.i("DataBaseHelper", "database already exist");
    		//do nothing - database already exist
    	}else{
    		Log.i("DataBaseHelper", "database does not exist");
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	//this.getReadableDatabase();
        	this.getWritableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
 
  
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 

		
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
	
	public Map<Integer,String> getrouteInfo()
	{
		Map<Integer,String> routeMap=new HashMap<Integer,String>();
		int route_id=0;
		String route_name=null;
		String select = "Select route_id,route_long_name,Directions FROM routes";
		Log.i("DataBaseHelper","executing query");
   	 Cursor cursor = myDataBase.rawQuery(select, null);
   	 if(cursor.moveToFirst()) {
   		 while(!cursor.isAfterLast()) {
   			
   			route_id=cursor.getInt(cursor
                    .getColumnIndex("route_id"));
   			route_name=cursor.getString(cursor
                    .getColumnIndex("route_long_name"))+"/"+cursor.getString(cursor
                            .getColumnIndex("Directions"));
   			 routeMap.put(route_id, route_name);
   		//	directions=directions+route_id+"-"+cursor.getString(cursor
         //           .getColumnIndex("Directions"));
   			cursor.moveToNext();
   		 }
   		cursor.close();
   	 }
   	 return routeMap;
	}
	
	
	public Map<String,Integer> createDummy(int id)
	{
		routeID=id;
		String queryTemp="select distinct stop_id,stop_name from stop_times where trip_id in (select trip_id from trips where route_id="+"\""+id+"\""+ ")";
		Log.i("DataBaseHelper","executing query-"+queryTemp);
		 Map<String,Integer> stopsMap=new HashMap<String,Integer>();
		ArrayList<Integer> stops=new ArrayList<Integer>();
		int stopid=0;
		Cursor cursor2 =myDataBase.rawQuery(queryTemp, null);
		String stopNM="";
		if(cursor2 != null)
   	 {
   	 cursor2.moveToFirst();
   	 }
   	 else
   	 {
   	 }
		 if(cursor2.moveToFirst()) {
	   		 while(!cursor2.isAfterLast()) {
	   			stopid=cursor2.getInt(cursor2
	                    .getColumnIndex("stop_id"));
	   			stopNM=cursor2.getString(cursor2
	                    .getColumnIndex("stop_name"));
	   			stopsMap.put(stopNM,stopid);
	   			
	   		cursor2.moveToNext();
	   		 }
	   		
	   	 }
		 cursor2.close();
		return stopsMap;
	}
	
	
	public ArrayList<String> findStopNames(StringBuilder sb)
	{

		String querynames="SELECT stop_name FROM stops WHERE stop_id IN ("+sb+")";
		ArrayList<String> stopNames=new ArrayList<String>();
		
		Cursor cursor3 =myDataBase.rawQuery(querynames, null);
		if(cursor3 != null)
   	 {
   	 cursor3.moveToFirst();
   	 }
   	 else
   	 {
   		 Log.i("DataBaseHelper","cursor3 is null");
   	 }
		String stopNm="";
		if(cursor3.moveToFirst()) {
	   		 while(!cursor3.isAfterLast()) {
	   			stopNm=cursor3.getString(cursor3
	                    .getColumnIndex("stop_name"));
	   			stopNames.add(stopNm);
	   		cursor3.moveToNext();
	   		 }
	   		
	   	 }
		cursor3.close();
		return stopNames;
		

	}
	
	
	
	
	public LinkedHashMap<Integer,String> findIT(int stopFrom,int longestRoute,int direction_id)
	{  
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String time=sdf.format(cal.getTime()) ;
		String test3="select trip_id,departure_time,start_stop_id,end_stop_id from Main_Table where stop_id="+"\""+stopFrom+"\""+" and direction_id="+"\""+direction_id+"\""+" and end_stop_id !="+"\""+stopFrom+"\""+" and time(departure_time) > "+"\""+time+"\""+" order by time(departure_time)";
		String stopNameQuery="select stop_name from stops where stop_id="+"\"";
		LinkedHashMap<Integer,String> depMap = new LinkedHashMap<Integer,String>();
		Log.i("DataBaseHelper","test3->"+test3);
		Cursor findTrip=myDataBase.rawQuery(test3, null);
		Cursor stopNm=null;
		int tempTrip=0;	
		int startID=0;
		int endID=0;
		String timeDep="";
		String tempNm="";
		String stopNMSt="";
		String stopNMEnd="";
		String fromTo="";
		if(findTrip.moveToFirst()) {
	  		 while(!findTrip.isAfterLast()) {
	  			 
	  			tempTrip=findTrip.getInt(findTrip
	                    .getColumnIndex("trip_id"));
	  			timeDep=findTrip.getString(findTrip
	                    .getColumnIndex("departure_time"));
	  			startID=findTrip.getInt(findTrip
	                    .getColumnIndex("start_stop_id"));
	  			endID=findTrip.getInt(findTrip
	                    .getColumnIndex("end_stop_id"));
	  			tempNm=stopNameQuery+startID+"\"";
	  			stopNm=myDataBase.rawQuery(tempNm, null);
	  			stopNm.moveToFirst();
	  			stopNMSt=stopNm.getString(stopNm
	                    .getColumnIndex("stop_name"));
	  			
	  			tempNm=stopNameQuery+endID+"\"";
	  			stopNm=myDataBase.rawQuery(tempNm, null);
	  			stopNm.moveToFirst();
	  			stopNMEnd=stopNm.getString(stopNm
	                    .getColumnIndex("stop_name"));
	  			stopNm.close();
	  			fromTo=stopNMSt+"-"+stopNMEnd;
	  			timeDep=timeDep+"/"+fromTo;
	  			depMap.put(tempTrip, timeDep);
	  			findTrip.moveToNext();
	  	
	  		 }
		}
		findTrip.close();
    	Log.i("DataBaseHelper","time="+time);
    	String tripsSel="select trip_id from Temp where stop_id="+"\""+stopFrom+"\"";
		Log.i("DataBaseHelper","tripsSel->"+tripsSel);	
		return depMap;
	}
	

	
	
	
	public  ArrayList<String> setNames(int routeID)
	{
		
	String getSchedule="select stop_name,departure_time from stop_times where trip_id="+routeID+" order by departure_time";
	
	Cursor getStops=myDataBase.rawQuery(getSchedule, null);
	
	String sched="";
ArrayList<String> schedule=new ArrayList<String>();

	if(getStops.moveToFirst()) {
 		 while(!getStops.isAfterLast()) {

 			sched=getStops.getString(getStops
 			               .getColumnIndex("stop_name"));
 			sched=sched +": "+ getStops.getString(getStops
		               .getColumnIndex("departure_time"));
 			schedule.add(sched);
 			sched="";
 			getStops.moveToNext();
 		 }
	}
	
	getStops.close();
	

	return schedule;
	}
	
	public void testUpdate()
	{
		String tempTrip="";
		String where="trip_id=?";
		String tableName="Main_Table";
		String tempWhere="";
		String fetchQuery="";
		Cursor testCursor=null;
		ContentValues cv ;
		int val=0;
		int dir=0;
		int tempTrp=0;
		cv= new ContentValues();
		tempTrip="update Main_Table set direction_idB=1 where trip_id="+"\""+2572+"\"";
		cv.put("direction_idB", "1");
		String [] value={""+2572};
		Log.i("DataBaseHelper","value="+value);
		//val=myDataBase.update(tableName, cv, where, value);
	//	Log.i("DataBaseHelper","val="+val);
//		fetchQuery="select trip_id,direction_idB from Main_Table where trip_id="+"\""+2572+"\"";
		int x=-1;
		fetchQuery="select trip_id,direction_id from Main_Table where direction_id="+"\""+x+"\"";
		testCursor=myDataBase.rawQuery(fetchQuery, null);
		testCursor.moveToFirst();
		
		
		
		while(!testCursor.isAfterLast()) {
			tempTrp=testCursor.getInt(testCursor
		               .getColumnIndex("trip_id"));
		dir=testCursor.getInt(testCursor
		               .getColumnIndex("direction_id"));
		Log.i("DataBaseHelper","tempTrp="+tempTrp+",indexStart < indexEnd direction=-1 , dir="+dir);
		testCursor.moveToNext();
		}
		
	}
	public void setDirections()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		String getTrips="select distinct Main_Table.trip_id ,Main_Table.start_stop_id,Main_Table.end_stop_id from Main_Table,trips where trips.route_id="+"\""+4+"\""+" and Main_Table.trip_id=trips.trip_id";
		
		String getLongest="select stop_id from stop_times where trip_id=2604";
		Cursor getTripQuery=myDataBase.rawQuery(getTrips, null);
		Cursor getLongSeq=myDataBase.rawQuery(getLongest, null);
		ArrayList<Integer> seqStop=new ArrayList<Integer>();
		//LinkedHashMap<Integer,Integer> seqMap = new LinkedHashMap<Integer,Integer>();
		int stop_id=0;
		int seq=0;
		if(getLongSeq.moveToFirst()) {
			while(!getLongSeq.isAfterLast()) {
				stop_id=getLongSeq.getInt(getLongSeq
		               .getColumnIndex("stop_id"));
			/*	seq=getLongSeq.getInt(getLongSeq
			               .getColumnIndex("stop_sequence"));
				*/
				seqStop.add(stop_id);
				Log.i("DataBaseHelper","stop_id="+stop_id);
				getLongSeq.moveToNext();
			}
			
	 		
	 		 }
		
		int trip=0;
		int endId=0;
		int startId=0;
		int indexStart=0;
		int indexEnd=0;
		String tempTrip="";
		String tableName="Main_Table";
		String tempWhere="";
		String fetchQuery="";
		String where="trip_id=?";
		int val=0;
		int dir=0;
		int tempTrp=0;
		Cursor testCursor=null;
		ContentValues cv ;
		if(getTripQuery.moveToFirst()) {
			while(!getTripQuery.isAfterLast()) {
			trip=getTripQuery.getInt(getTripQuery
		               .getColumnIndex("trip_id"));
			startId=getTripQuery.getInt(getTripQuery
		               .getColumnIndex("start_stop_id"));
			endId=getTripQuery.getInt(getTripQuery
		               .getColumnIndex("end_stop_id"));
			
			indexStart=seqStop.indexOf(startId);
			indexEnd=seqStop.indexOf(endId);
			
			int y=-2;
			/*
			if(startId == 76 && endId == 4 )
			{
				cv= new ContentValues();
				cv.put("direction_id", "2");
				String [] value={""+trip};
				val=myDataBase.update(tableName, cv, where, value);
				Log.i("DataBaseHelper","updated direction ->"+2+"trip->"+trip);
			}
			if(startId == 4 && endId == 76 )
			{
				cv= new ContentValues();
				cv.put("direction_id", "-2");
				String [] value={""+trip};
				val=myDataBase.update(tableName, cv, where, value);
				Log.i("DataBaseHelper","updated direction ->"+y+"trip->"+trip);
			}
			*/
			
			if(indexStart < indexEnd &&(indexStart !=-1)&&(indexEnd !=-1))
			{
				cv= new ContentValues();
			//	tempTrip="update Main_Table set direction_idB=1 where trip_id="+"\""+trip+"\"";
			//	myDataBase.execSQL(tempTrip);
				cv.put("direction_id", "4");
				String [] value={""+trip};
				val=myDataBase.update(tableName, cv, where, value);
			//	val=db.update(tableName, cv, where, value);
			//	fetchQuery="select trip_id,direction_idB from Main_Table where trip_id="+"\""+trip+"\"";
			///	testCursor=myDataBase.rawQuery(fetchQuery, null);
			//	testCursor.moveToFirst();
				
				Log.i("DataBaseHelper","trip="+trip+",indexStart < indexEnd direction=4 , val="+val+"arr->"+Arrays.toString(value));
				
			}
			else if(indexStart > indexEnd &&(indexStart !=-1)&&(indexEnd !=-1))
			{
				cv= new ContentValues();
				//tempTrip="update Main_Table set direction_idB=-1 where trip_id="+"\""+trip+"\"";
			//	myDataBase.execSQL(tempTrip);
				cv.put("direction_id", "-5");
				String [] value={""+trip};
				val=myDataBase.update(tableName, cv, where, value);
			//	val=db.update(tableName, cv, where, value);
		//		fetchQuery="select trip_id,direction_idB from Main_Table where trip_id="+"\""+trip+"\"";
		//		testCursor=myDataBase.rawQuery(fetchQuery, null);
		//		testCursor.moveToFirst();
			
				Log.i("DataBaseHelper","trip="+trip+",indexStart > indexEnd direction=-5 , val="+val+"arr->"+Arrays.toString(value));
			}
			/*
			if(startId == 85 && endId == 4 )
			{
				cv= new ContentValues();
				cv.put("direction_id", "2");
				String [] value={""+trip};
				val=myDataBase.update(tableName, cv, where, value);
				Log.i("DataBaseHelper","updated direction ->"+2+"trip->"+trip);
			}
			if(startId == 4 && endId == 85 )
			{
				cv= new ContentValues();
				cv.put("direction_id", "3");
				String [] value={""+trip};
				val=myDataBase.update(tableName, cv, where, value);
				Log.i("DataBaseHelper","updated direction ->"+3+"trip->"+trip);
			}
			*/
			getTripQuery.moveToNext();
			
			}
			
	 		
	 		 }
		myDataBase.close();
		}
		
	
	
	public JSONArray testFunction()
	{
		ArrayList<LatLng> cord=new ArrayList<LatLng>(); 
		int stopid=0;
		double lat=0.0;
		double lon=0.0;
		String getTrip="select distinct stop_id,stop_name,stop_lat,stop_lon from Main_Table";
		String sched=null;
		LatLng stopcod=null;
		JSONArray FixArray = new JSONArray();
		Cursor getTripQuery=myDataBase.rawQuery(getTrip, null);
		JSONObject stopDet =null;
		String idto="";
		String stopName="";
		if(getTripQuery.moveToFirst()) {
			while(!getTripQuery.isAfterLast()) {
				stopid=getTripQuery.getInt(getTripQuery
		               .getColumnIndex("stop_id"));
				stopName=getTripQuery.getString(getTripQuery
						.getColumnIndex("stop_name"));
				lat=getTripQuery.getDouble(getTripQuery
			               .getColumnIndex("stop_lat"));
				lon=getTripQuery.getDouble(getTripQuery
			               .getColumnIndex("stop_lon"));
				stopcod=new LatLng(lat,lon);
				idto=stopid+"";
				cord.add(stopcod);
				stopDet =	new JSONObject();
				String snm="SSC BOARD OFFICE/ST.LAWRENCE SCHOOL";
				
				
				try {
					stopDet.putOpt("stopName", stopName);
					stopDet.putOpt("startingID", idto);
					stopDet.putOpt("stopLtLn", stopcod);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				FixArray.put(stopDet);
				getTripQuery.moveToNext();
			}
			
	 		
	 		 }
		
		getTripQuery.close();
		
		
		return FixArray;
		
	}
	
	
	
	
	
	public ArrayList<String> testfn(String stopID)
	{
		
		ArrayList<String> busNames=new ArrayList<String>(); 
		Cursor getTripQuery=null;
		Cursor getRouteName=null;
		String routeSearch="select distinct bus_name from Main_Table where stop_id="+"\""+stopID+"\"";
		String routenm="select route_long_name from routes where route_short_name=";
		String temp="";
		String routeDesc="";
		try{
			 getTripQuery=myDataBase.rawQuery(routeSearch, null);
			}
			catch(Exception ew)
			{
				Toast.makeText(myContext, "ew->"+ew.toString(), Toast.LENGTH_LONG).show();
			}
		String tempText="";
		String busNm="";
		
		try{
			if(getTripQuery.moveToFirst()) {
				while(!getTripQuery.isAfterLast()) {
					
					busNm=getTripQuery.getString(getTripQuery
							.getColumnIndex("bus_name"));
					temp=routenm+"\""+busNm+"\"";
					getRouteName=myDataBase.rawQuery(temp, null);
					getRouteName.moveToFirst();
					routeDesc=getRouteName.getString(getRouteName
							.getColumnIndex("route_long_name"));
					getRouteName.close();
					temp=busNm+" "+routeDesc;
					busNames.add(temp);
					getTripQuery.moveToNext();
				}
			}
			}
			catch(Exception ew)
			{
				Toast.makeText(myContext, "ew->"+ew.toString(), Toast.LENGTH_LONG).show();
			}
		getTripQuery.close();
			return busNames;
	}
	
	
	
	public ArrayList<String> getBusStops(String bus)
	{
		String trip="";
		String getTrip="select longest_trip from routes where route_short_name="+"\""+bus+"\"";
		Cursor getBus=null;
		Cursor getStps=null;
		
		ArrayList<String> ar=new ArrayList<String>();
		String stpnm="";
		try
		{
		getBus=myDataBase.rawQuery(getTrip, null);
		getBus.moveToFirst();
		
		trip=getBus.getString(getBus
				.getColumnIndex("longest_trip"));
		}
		catch(Exception er)
		{
			Toast.makeText(myContext,"Exception trip->"+er.toString(), Toast.LENGTH_LONG).show();	
		}
		getBus.close();
		String getStops="SELECT stop_name FROM Main_Table where trip_id="+"\""+trip+"\""+" order by stop_sequence";
		getStps=myDataBase.rawQuery(getStops, null);
		
		if(getStps.moveToFirst()) {
			while(!getStps.isAfterLast()) {
				
				stpnm=getStps.getString(getStps
						.getColumnIndex("stop_name"));
				ar.add(stpnm);
				getStps.moveToNext();
			}
		}
		getStps.close();
		
		return ar;
	}
	
	
	
	
	
		
	}
	
	
	
	
 
 

