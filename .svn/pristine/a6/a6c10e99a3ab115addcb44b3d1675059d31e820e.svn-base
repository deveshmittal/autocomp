package com.example.autocomp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class DataBaseHelper extends SQLiteOpenHelper{

	//The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/com.example.autocomp/databases/";
	
	//  private static String DB_NAME = "stopsDetail.sqlite";
//TRAIN = DATA3.SQLITE
	private static String DB_NAME = "RailData.sqlite";

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
	public DataBaseHelper(Context context) {

		
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
			//checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);	
		}catch(SQLiteException e){
			//Toast.makeText(myContext, "myPath->e->"+e.toString(), Toast.LENGTH_LONG).show();	
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
	public JSONArray getTrainStops()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Calendar cal = Calendar.getInstance();
		String time=sdf.format(cal.getTime()) ;
		Map<String,LatLng> ar=new HashMap<String,LatLng>(); 
		String stopName=null;
		double lat=0.0;
		double lng=0.0;
		Cursor cursorTrips = null;
		int stopID=0;
		String select = "Select * FROM stops";
		String tempTrip="";
		String endStop="";
		String depTime="";
		String selectTrips="select end_stop_id,departure_time from Main_Table where stop_id=";
		String selectStopNm="select stop_name from stops where stop_id=";
		String tempNm="";
	
		
		Log.i("DataBaseHelper","executing query");
		Cursor cursor = myDataBase.rawQuery(select, null);
		Cursor cursorNm =null;
		LatLng obj=null;
		String stopN="";
		String tempStop="";
		JSONObject stopDet = null;
		JSONArray FixArray = new JSONArray();
		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {

				stopName=cursor.getString(cursor
						.getColumnIndex("stop_name"));
				stopID=cursor.getInt(cursor
						.getColumnIndex("stop_id"));
				String idto=stopID+"";
				lat=cursor.getDouble(cursor.getColumnIndex("stop_lat"));
				lng=cursor.getDouble(cursor.getColumnIndex("stop_lon"));
				tempTrip=selectTrips+"\""+stopID+"\""+"and end_stop_id !="+stopID+" and time(departure_time)>"+"\""+time+"\""+" order by departure_time LIMIT 3";
				
			//	cursorTrips=myDataBase.rawQuery(tempTrip, null);
				int c=0;
				String stopid="";
				tempStop="";
				String updateNm="";
				String updateTm="";
				stopDet =	new JSONObject();
				try {
					stopDet.putOpt("stopName", stopName);
					stopDet.putOpt("startingID", idto);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				/*
				if(cursorTrips.moveToFirst()) {
					while(!cursorTrips.isAfterLast()) {
						++c;
						endStop=cursorTrips.getString(cursorTrips
								.getColumnIndex("end_stop_id"));
						tempNm=selectStopNm+"\""+endStop+"\"";
						cursorNm=myDataBase.rawQuery(tempNm, null);
						cursorNm.moveToFirst();
						stopN=cursorNm.getString(cursorNm
								.getColumnIndex("stop_name"));
						cursorNm.close();
						tempStop=tempStop+stopN+":";
						depTime=cursorTrips.getString(cursorTrips
								.getColumnIndex("departure_time"));
						stopid="stopid"+c;
						updateNm="stopNm"+c;
						updateTm="depTm"+c;
						
						try {
						
							stopDet.putOpt(stopid, endStop);
							stopDet.putOpt(updateNm, stopN);
							stopDet.putOpt(updateTm, depTime);
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						cursorTrips.moveToNext();
							Log.i("DataBaseHelper","endStop for stop name->"+stopName+" , "+endStop+",  depTime->"+depTime);
					}
				//	Toast.makeText(ct, "c->"+c+"tempStop->"+tempStop, Toast.LENGTH_LONG).show();
				}

				cursorTrips.close();
				*/
				obj=new LatLng(lat,lng);
				try {
					stopDet.putOpt("stopLtLn",obj);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				FixArray.put(stopDet);
				ar.put(stopName, obj);
				cursor.moveToNext();

			}
			cursor.close();
		}
		
		return FixArray;
	}

	
	
	public JSONObject getTrainStopsForMarker(String stopID,String title)
	{
		
	
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Calendar cal = Calendar.getInstance();
		String time=sdf.format(cal.getTime()) ;
		Cursor cursorTrips = null;
		LatLng obj=null;
		String tempNm="";
		Cursor cursorNm =null;
		int c=0;
		String stopid="";
		
		String updateNm="";
		String updateTm="";
		String stopN="";
		String tempStop="";
		String endStop="";
		String selectStopNm="select stop_name from stops where stop_id=";
		String depTime="";
		
		
		JSONObject stopDet = null;
		stopDet =	new JSONObject();
		try {
			stopDet.putOpt("stopName", title);
			stopDet.putOpt("startingID", stopID);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String selectTrips="select end_stop_id,departure_time from Main_Table where stop_id=";
		String tempTrip="";
		tempTrip=selectTrips+"\""+stopID+"\""+"and end_stop_id !="+stopID+" and time(departure_time)>"+"\""+time+"\""+" order by departure_time LIMIT 3";
		cursorTrips=myDataBase.rawQuery(tempTrip, null);
		if(cursorTrips.moveToFirst()) {
			while(!cursorTrips.isAfterLast()) {
				++c;
				endStop=cursorTrips.getString(cursorTrips
						.getColumnIndex("end_stop_id"));
				tempNm=selectStopNm+"\""+endStop+"\"";
				cursorNm=myDataBase.rawQuery(tempNm, null);
				cursorNm.moveToFirst();
				stopN=cursorNm.getString(cursorNm
						.getColumnIndex("stop_name"));
				cursorNm.close();
				tempStop=tempStop+stopN+":";
				depTime=cursorTrips.getString(cursorTrips
						.getColumnIndex("departure_time"));
				stopid="stopid"+c;
				updateNm="stopNm"+c;
				updateTm="depTm"+c;
				
				try {
				
					stopDet.putOpt(stopid, endStop);
					stopDet.putOpt(updateNm, stopN);
					stopDet.putOpt(updateTm, depTime);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				cursorTrips.moveToNext();
					

			}
		//	Toast.makeText(ct, "c->"+c+"tempStop->"+tempStop, Toast.LENGTH_LONG).show();
		}
		cursorTrips.close();
		return stopDet;
	}
	
	
	
	
	
	
	
	
	
	
	
	


	public  LinkedHashMap<String,String> getDetails(int endStopId,String depTime,String intiD)
	{
		
		String startingID=intiD;
		 LinkedHashMap<String,String> lhm = new LinkedHashMap<String,String>();
		Cursor cursorNm =null;
		Cursor cursorTrip =null;
		String search="select trip_id from Main_Table where stop_id="+"\""+startingID+"\""+" and end_stop_id="+"\""+endStopId+"\""+" and departure_time="+"\""+depTime+"\"";
		cursorNm=myDataBase.rawQuery(search, null);
		cursorNm.moveToFirst();
		String tripID=cursorNm.getString(cursorNm
				.getColumnIndex("trip_id"));
		cursorNm.close();
		String selectStops="select stop_name,departure_time from stop_times where trip_id="+"\""+tripID+"\""+" order by departure_time";
		cursorTrip=myDataBase.rawQuery(selectStops, null);
		String stopName="";
		String deptime="";
		if(cursorTrip.moveToFirst()) {
			while(!cursorTrip.isAfterLast()) {
				
				stopName=cursorTrip.getString(cursorTrip
						.getColumnIndex("stop_name"));
				deptime=cursorTrip.getString(cursorTrip
						.getColumnIndex("departure_time"));
				
				lhm.put(deptime, stopName);
				cursorTrip.moveToNext();
			}
		}
		cursorTrip.close();
	
	
		return lhm;
	}
	
	
	public JSONArray getBusStops()
	{
		double lat=0.0;
		double lng=0.0;
		int route=0;
		String stopName="";
		JSONArray FixArray = new JSONArray();
		Cursor cursorNm =null;
		String routeQ="";
		String temp="";
		String getRoutes="select route_id,route_short_name from routes";
		String getStops="select distinct stop_id,trip_id,stop_name,stop_lat,stop_lon from Main_Table";
		cursorNm=myDataBase.rawQuery(getRoutes, null);
		if(cursorNm.moveToFirst()) {
			while(!cursorNm.isAfterLast()) {
				
				route=cursorNm.getInt(cursorNm
						.getColumnIndex("route_id"));
				routeQ=""+"'"+route+"%"+"'";
				temp="select distinct stop_id from Main_Table where trip_id like " +routeQ;
				
		
				
				
				 cursorNm.moveToNext();
				
				//lat=cursorNm.getDouble(cursorNm.getColumnIndex("stop_lat"));
				//lng=cursorNm.getDouble(cursorNm.getColumnIndex("stop_lon"));
				
			}
		}
		cursorNm.close();
		return FixArray;
		
	}
	
	
	
	public ArrayList<String> getStops(String tripID)
	{
		String tmp="";
		String stpNm="";
		String dptime="";
		ArrayList<String> ar=new ArrayList<String>();
		String sel="select departure_time,stop_name from stop_times where trip_id="+"\""+tripID+"\""+" order by stop_sequence";
		String sel2="select distinct stop_id,departure_time from Main_Table where trip_id="+tripID+" order by departure_time";
		String selStp="select stop_name from stops where stop_id=";
		String tempStp="";
		String stpName="";
		Cursor cursorNm =null;
		Cursor stp=null;
		int stpId=0;
		
		stp=myDataBase.rawQuery(sel2, null);
		
		if(stp.moveToFirst()) {
			while(!stp.isAfterLast()) {
				
				
				stpId=stp.getInt(stp.getColumnIndex("stop_id"));
				dptime=stp.getString(stp
						.getColumnIndex("departure_time"));
				tempStp=selStp+"\""+stpId+"\"";
				
				cursorNm=myDataBase.rawQuery(tempStp, null);
				
				cursorNm.moveToFirst();
				stpName=cursorNm.getString(cursorNm
						.getColumnIndex("stop_name"));
				
				cursorNm.close();
				tmp=stpName+"-"+dptime;
				ar.add(tmp);
				
				stp.moveToNext();
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		try{
		cursorNm=myDataBase.rawQuery(sel, null);
		if(cursorNm.moveToFirst()) {
			while(!cursorNm.isAfterLast()) {
				
				stpNm=cursorNm.getString(cursorNm
						.getColumnIndex("stop_name"));
				
		//		stpId=cursorNm.getInt(cursorNm
			//					.getColumnIndex("stop_id"));
			//	tempStp=selStp+"\""+stpId+"\"";
			//	stp=myDataBase.rawQuery(tempStp, null);
			//	stpName=stp.getString(stp
			//			.getColumnIndex("stop_name"));
				dptime=cursorNm.getString(cursorNm
						.getColumnIndex("departure_time"));
				
				tmp=stpNm+"-"+dptime;
				//tmp=stpName+"-"+dptime;
				ar.add(tmp);
			//	stp.close();
				 cursorNm.moveToNext();
			}
		}
		}
		catch(Exception ee)
		{
			Toast.makeText(myContext,"ee->"+ee.toString(), Toast.LENGTH_LONG).show();
		}
		finally
		{
			if(cursorNm !=null)
			{
				cursorNm.close();		
			}
		}
		*/
		return ar;
	}
	
	
	
	
	
	
	
	
	public JSONArray getTrips(String stopID)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Calendar cal = Calendar.getInstance();
		String time=sdf.format(cal.getTime()) ;
		HashMap<String,JSONObject> hm=new HashMap<String,JSONObject>();
		int endId=0;
		String depTime="";
		String trip_id="";
		Cursor cursorLn =null;
		Cursor cursorNm =null;
		Cursor cursorN =null;
		JSONObject stopDet = null;
		String endName="select stop_name from stops where stop_id=";
		String tempEnd="";
		String endnm="";
		String startnm="";
		String startQuery="";
		String routeLng="";
		int startId=0;
		String tempseline="";
		String dir=null;
		String selectTrips="select trip_id,end_stop_id,start_stop_id,departure_time,route_long_name,directions from Main_Table where stop_id="+"\""+stopID+"\""+" and end_stop_id !="+"\""+stopID+"\""+" and time(departure_time)>"+"\""+time+"\""+" order by departure_time";
		cursorNm=myDataBase.rawQuery(selectTrips, null);
		JSONArray FixArray = new JSONArray();
		if(cursorNm.moveToFirst()) {
			while(!cursorNm.isAfterLast()) {
				
				endId=cursorNm.getInt(cursorNm
						.getColumnIndex("end_stop_id"));
				startId=cursorNm.getInt(cursorNm
						.getColumnIndex("start_stop_id"));
				dir=cursorNm.getString(cursorNm
						.getColumnIndex("directions"));
				tempEnd=endName+"\""+endId+"\"";
				cursorN=myDataBase.rawQuery(tempEnd, null);
				cursorN.moveToFirst();
				endnm=cursorN.getString(cursorN
						.getColumnIndex("stop_name"));
				tempEnd=endName+"\""+startId+"\"";
				cursorN=myDataBase.rawQuery(tempEnd, null);
				cursorN.moveToFirst();
				startnm=cursorN.getString(cursorN
						.getColumnIndex("stop_name"));
				if(cursorN != null)
					cursorN.close();
				depTime=cursorNm.getString(cursorNm
						.getColumnIndex("departure_time"));
				trip_id=cursorNm.getString(cursorNm
						.getColumnIndex("trip_id"));
				routeLng=cursorNm.getString(cursorNm
						.getColumnIndex("route_long_name"));
				stopDet=new JSONObject();
				Log.i("DataBaseHelper","dir->"+dir);
				
				//stopDet.putOpt("EndId", endId);
				try {
					stopDet.putOpt("Line", routeLng);
					stopDet.put("StartName",startnm);
					stopDet.put("EndName", endnm);
					stopDet.putOpt("DepTime", depTime);
					stopDet.putOpt("tripId", trip_id);
					stopDet.putOpt("direction", dir);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				FixArray.put(stopDet);
				
				 cursorNm.moveToNext();
				
				
			}
		}
		if(cursorNm != null)
			cursorNm.close();
	
		return FixArray;
		

	}
}