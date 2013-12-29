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

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class RailDataBaseHelper extends SQLiteOpenHelper{
      
    //The Android's default system path of your application database.
//changed to RailData
    private static String RAIL_DB_PATH = "/data/data/com.example.autocomp/databases/";
    private static String RAIL_DB_NAME = "RailData.sqlite";
    
    
    
    private SQLiteDatabase myRailDataBase; 
 
    private final Context myContext;
    
  
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public RailDataBaseHelper(Context context) {
  
        super(context, RAIL_DB_NAME, null, 1);
        this.myContext = context;
        
    }    
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    
    public void createRailDataBase() throws IOException{
    	  
        boolean dbExist = checkRailDataBase();
 
        if(dbExist){
        Log.i("RailDataBaseHelper", "database already exist");
            //do nothing - database already exist
        }else{
             Log.i("RailDataBaseHelper", "database does not exist SHOOT");
            //By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
             //this.getReadableDatabase();
            this.getWritableDatabase();
 
            try {
 
            	Log.i("Create DataBAasse ","check this line ");
                copyRailDataBase();
 
            } catch (IOException e) {
 
                throw new Error("Error copying database");
  
            }
        }
 
    }
    
   
    
    
    
    
    
    
    
    
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
      */
    
    public boolean checkRailDataBase(){
    	 
        SQLiteDatabase checkDB = null;
 
        try{
            String myPath = RAIL_DB_PATH + RAIL_DB_NAME;
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
    
    private void copyRailDataBase() throws IOException{
    	 
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(RAIL_DB_NAME);
 
        // Path to the just created empty db
         String outFileName = RAIL_DB_PATH + RAIL_DB_NAME;
 
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
    
    
    
    
    
    public void openRailDataBase() throws SQLException{
    	 
        //Open the database
        String myPath = RAIL_DB_PATH +RAIL_DB_NAME;
         myRailDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
    
    
    
    
    
    
 
    public String readData()
    {
        String select = "Select * FROM routes";
         Log.i("RailDataBaseHelper","executing query");
          Cursor cursor = myRailDataBase.rawQuery(select, null);
         if(cursor != null)
         {
             Log.i("RailDataBaseHelper","cursor is not null");
         cursor.moveToFirst();
          }
         else
         {
             Log.i("RailDataBaseHelper","cursor is null");
         }
         String route_long_name= cursor.getString(cursor
                 .getColumnIndex("route_long_name"));
          Log.i("RailDataBaseHelper","route_long_name="+route_long_name);
         return route_long_name;
    }
    
    @Override
    public synchronized void close() {
 
            if(myRailDataBase != null)
                 myRailDataBase.close();
 
            super.close();
 
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 

        
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  
    }
    

   
    
//   
//    public ArrayList<String> findStopNames(StringBuilder sb)
//    {
//
//        String querynames="SELECT stop_name FROM stops WHERE stop_id IN ("+sb+")";
//        ArrayList<String> stopNames=new ArrayList<String>();
//         
//        Cursor cursor3 =myDataBase.rawQuery(querynames, null);
//        if(cursor3 != null)
//        {
//            Log.i("RailDataBaseHelper","cursor3 is not null");
//        cursor3.moveToFirst();
//         }
//        else
//        {
//            Log.i("RailDataBaseHelper","cursor3 is null");
//        }
//        String stopNm="";
//        if(cursor3.moveToFirst()) {
//                while(!cursor3.isAfterLast()) {
//                    stopNm=cursor3.getString(cursor3
//                        .getColumnIndex("stop_name"));
//                   stopNames.add(stopNm);
//                Log.i("RailDataBaseHelper","cursor3.isAfterLast()="+cursor3.isAfterLast()+" stopNm-"+stopNm);
//                cursor3.moveToNext();
//                }
//               
//            }
//        cursor3.close();
//        return stopNames;
//        
//
//    }
    
//    public int getStopID(String stopName)
//     {
//        
//        String findQuery="SELECT stop_id FROM stops WHERE stop_name="+"\""+stopName+"\"";
//        Log.i("RailDataBaseHelper","before rawquery findQuery-"+findQuery);
//         Cursor cursorNew =myDataBase.rawQuery(findQuery, null);
//        Log.i("RailDataBaseHelper","after rawquery findQuery");
//        cursorNew.moveToFirst();
//        int stopId=cursorNew.getInt(cursorNew
//                 .getColumnIndex("stop_id"));
//         Log.i("RailDataBaseHelper","findQuery="+findQuery);
//         cursorNew.close();
//         return stopId;
//    }
    
 
    

    
    
    
    
    public ArrayList<String> getSomeStops(int start_id, int end_id, String route_id,String trip_id)
    {
    //String getStops = "select stop_name,arrival_time from(select p2.* from(select trip_id from trips where route_id="+route_id+") as p1 join(select  * from (select trip_id,stop_id as start_stop,stop_sequence as start_stop_seq  from stop_times where stop_id = "+start_id+" and arrival_time in ("+"\""+arTime+"\""+")) as t1 join  (select trip_id,stop_id as end_stop,stop_sequence as end_stop_seq from stop_times where stop_id ="+end_id +" and arrival_time in ("+"\""+depTime+"\""+")) as t2 on t1.trip_id=t2.trip_id) as p2 on p1.trip_id=p2.trip_id) as m1 join(select * from stop_times  ) as p3 where p3.trip_id =m1.trip_id and p3. stop_sequence >m1.start_stop_seq and  p3. stop_sequence<m1.end_stop_seq "    	;
    String myStopsQuery= "select stop_name,arrival_time,stop_sequence from (select p1.trip_id,p1.start_stop,p2.end_stop,p1.start_stop_seq,p2.end_stop_seq from (select trip_id,stop_id as start_stop,stop_sequence as start_stop_seq  from stop_times where  trip_id="+"\""+trip_id+"\""+" and stop_id="+start_id+") as p1 join (select trip_id,stop_id as end_stop,stop_sequence as end_stop_seq  from stop_times where  trip_id="+"\""+trip_id+"\""+" and stop_id="+end_id+") as p2 on p1.trip_id=p2.trip_id) as m1 join (select * from stop_times  ) as p3 where p3.trip_id =m1.trip_id and p3. stop_sequence >m1.start_stop_seq and  p3. stop_sequence<m1.end_stop_seq ";
    
    
    Log.i("INDIA ",myStopsQuery);
    Cursor getFinalStops=myRailDataBase.rawQuery(myStopsQuery, null);
    Log.i("MONKEY PAW",Integer.toString(getFinalStops.getCount()));
    String sched="";
    ArrayList<String> schedule=new ArrayList<String>();
    if(getFinalStops.moveToFirst()){
    	 while(!getFinalStops.isAfterLast())
    	 {
    		 Log.i("MONKEY PAW","Inside Cursor Array");
    		 Log.i("MONKEY PAW ONE",getFinalStops.getString(getFinalStops
                     .getColumnIndex("arrival_time")));
    		 Log.i("MONKEY PAW AGAIN",Integer.toString(getFinalStops.getColumnIndex("stop_name")));
    		 Log.i("MONKEY PAW STOP NAME",getFinalStops.getString(getFinalStops
                     .getColumnIndex("stop_name")));
    		 sched=getFinalStops.getString(getFinalStops
                     .getColumnIndex("stop_name"));
    		 sched=sched +"- "+ getFinalStops.getString(getFinalStops
                     .getColumnIndex("arrival_time"));
             schedule.add(sched);
             Log.i("RailDataBaseHelper","sched->"+sched);
             sched="";
             getFinalStops.moveToNext();
    	 }
    }
    if(getFinalStops!= null){
    getFinalStops.close();}
    return schedule;
        }
    
    
    
    
    
    
    
    

    
   
 
    
 public String getRouteId(String routeName)
 {
	 String getRid= "select route_id from routes where route_long_name ="+"\""+routeName+"\"";
	 Cursor cursor = myRailDataBase.rawQuery(getRid,null);
	 String Rid ="";
	 if(cursor.moveToFirst()){
		 while(!cursor.isAfterLast())
		 {
			 Rid = Integer.toString(cursor.getInt(cursor.getColumnIndex("route_id")));
			cursor.moveToNext();
			Log.i("WOHARid", Rid);
			Log.i("WOHA", Integer.toString(cursor.getColumnIndex("route_id")));
		 }
	 }
	 if(cursor!=null)
	 {
		 cursor.close();
	 }
	 return Rid;
 } 
    
    
public ArrayList<String> getInterStops(int start_id, int end_id,String arTime , String depTime, String route_id)
{
String getStops = "select stop_name,arrival_time from(select p2.* from(select trip_id from trips where route_id="+route_id+") as p1 join(select  * from (select trip_id,stop_id as start_stop,stop_sequence as start_stop_seq  from stop_times where stop_id = "+start_id+" and arrival_time in ("+"\""+arTime+"\""+")) as t1 join  (select trip_id,stop_id as end_stop,stop_sequence as end_stop_seq from stop_times where stop_id ="+end_id +" and arrival_time in ("+"\""+depTime+"\""+")) as t2 on t1.trip_id=t2.trip_id) as p2 on p1.trip_id=p2.trip_id) as m1 join(select * from stop_times  ) as p3 where p3.trip_id =m1.trip_id and p3. stop_sequence >m1.start_stop_seq and  p3. stop_sequence<m1.end_stop_seq "    	;
Log.i("INDIA ",getStops);
Cursor getFinalStops=myRailDataBase.rawQuery(getStops, null);
Log.i("MONKEY PAW",Integer.toString(getFinalStops.getCount()));
String sched="";
ArrayList<String> schedule=new ArrayList<String>();
if(getFinalStops.moveToFirst()){
	 while(!getFinalStops.isAfterLast())
	 {
		 Log.i("MONKEY PAW","Inside Cursor Array");
		 Log.i("MONKEY PAW ONE",getFinalStops.getString(getFinalStops
                 .getColumnIndex("arrival_time")));
		 Log.i("MONKEY PAW AGAIN",Integer.toString(getFinalStops.getColumnIndex("stop_name")));
		 Log.i("MONKEY PAW STOP NAME",getFinalStops.getString(getFinalStops
                 .getColumnIndex("stop_name")));
		 sched=getFinalStops.getString(getFinalStops
                 .getColumnIndex("stop_name"));
		 sched=sched +"-"+ getFinalStops.getString(getFinalStops
                 .getColumnIndex("arrival_time"));
         schedule.add(sched);
         Log.i("RailDataBaseHelper","sched->"+sched);
         sched="";
         getFinalStops.moveToNext();
	 }
}
if(getFinalStops!= null){
getFinalStops.close();}
return schedule;
    }
    
 
        // Add your public helper methods to access and get content from the database.
        // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
 
}