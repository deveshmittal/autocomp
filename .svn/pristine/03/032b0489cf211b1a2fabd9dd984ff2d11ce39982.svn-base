package com.example.autocomp;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
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

public class BusDataBaseHelper extends SQLiteOpenHelper{

	//changed to BusDatsMap
    private static String BUS_DB_PATH = "/data/data/com.example.autocomp/databases/";
    private static String BUS_DB_NAME = "BusDatsMap.sqlite";

    
    private SQLiteDatabase myBusDataBase; 
 
    private final Context myContext;
    
    
    public BusDataBaseHelper(Context context)
    {
    super(context, BUS_DB_NAME, null, 1);
    this.myContext = context;
    }
    public void createBusDataBase() throws IOException{
  	  
        boolean dbExist = checkBusDataBase();
 
        if(dbExist){
        Log.i("BusDataBaseHelper", "database already exist");
            //do nothing - database already exist
        }else{
             Log.i("BusDataBaseHelper", "database does not exist SHOOT");
            //By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
             //this.getReadableDatabase();
            try{
            this.getWritableDatabase();
            }catch(NullPointerException e)
            {e.printStackTrace();
            Log.i("DEVESH MITTAL","");
            }
 
            try {
 
            	Log.i("Create  BUS DataBAasse ","check this line ");
                copyBusDataBase();
 
            } catch (IOException e) {
 
                throw new Error("Error copying database");
  
            }
        }
 
    }
    public boolean checkBusDataBase(){
   	 
        SQLiteDatabase checkDB = null;
 
        try{
            String myPath = BUS_DB_PATH + BUS_DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
  
        }catch(SQLiteException e){
 
            //database does't exist yet.
 
        }
 
        if(checkDB != null){
 
            checkDB.close();
 
        }
 
        
        Log.i("checkBusDataBase","HERE IT IS checkDB != null ?"+(checkDB != null ? "true" : "false"));
        return checkDB != null ? true : false;
     }
    
    private void copyBusDataBase() throws IOException{
   	 
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(BUS_DB_NAME);
 
        // Path to the just created empty db
         String outFileName = BUS_DB_PATH + BUS_DB_NAME;
 
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
    
    
    public void openBusDataBase() throws SQLException{
    	 
        //Open the database
        String myPath = BUS_DB_PATH +BUS_DB_NAME;
         myBusDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
 
    public String readData()
    {
        String select = "Select * FROM routes";
         Log.i("BusDataBaseHelper","executing query");
          Cursor cursor = myBusDataBase.rawQuery(select, null);
         if(cursor != null)
         {
             Log.i("BusDataBaseHelper","cursor is not null");
         cursor.moveToFirst();
          }
         else
         {
             Log.i("BusDataBaseHelper","cursor is null");
         }
         String route_long_name= cursor.getString(cursor
                 .getColumnIndex("route_long_name"));
          Log.i("BusDataBaseHelper","route_long_name="+route_long_name);
         return route_long_name;
    }
    
    @Override
    public synchronized void close() {
 
            if(myBusDataBase != null)
                 myBusDataBase.close();
 
            super.close();
 
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
 

        
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  
    }
    
    

    
    public ArrayList<String> getBusStops(int start_id, int end_id, String trip_id){
    	//PreparedStatement statement = 
    	 String myStopsQuery = "select stop_name,stop_sequence from(select p1.trip_id,p1.start_stop,p2.end_stop,p1.start_stop_seq,p2.end_stop_seq from (select trip_id,stop_id as start_stop,stop_sequence as start_stop_seq  from Main_Table where  trip_id="+"\""+trip_id+"\""+" and stop_id="+start_id+") as p1 join(select trip_id,stop_id as end_stop,stop_sequence as end_stop_seq  from Main_Table where  trip_id="+"\""+trip_id+"\""+" and stop_id="+end_id+") as p2 on p1.trip_id=p2.trip_id) as m1 join (select * from Main_Table ) as p3 where p3.trip_id =m1.trip_id and p3.stop_sequence >=m1.start_stop_seq and  p3.stop_sequence<=m1.end_stop_seq ORDER by stop_sequence ";
    	
    	 Cursor cursor = myBusDataBase.rawQuery(myStopsQuery, null);
    	 Log.i("STUNNER",myStopsQuery);
    	    String sched="";
    	    ArrayList<String> schedule=new ArrayList<String>();
    	    if(cursor.moveToFirst()){
    	    	while(!cursor.isAfterLast())
    	    	{
    	    		sched=cursor.getString(cursor.getColumnIndex("stop_name"));
    	    	
    	    		schedule.add(sched);
    	    		sched ="";
    	    		cursor.moveToNext();
    	    	}
    	    }
    	    if(cursor!=null)
    	    {
    	    	cursor.close();
    	    	
    	    }
    	    return schedule;

    }
    
    
    
    public int selectStartSeqNo(int start_id,String trip_id)
    {
    	String myQuery ="select stop_sequence from Main_Table where trip_id="+"\""+trip_id+"\""+" and stop_id="+start_id;
    	Cursor cursor = myBusDataBase.rawQuery(myQuery, null);
    	int seqNo=0;
    	  if(cursor.moveToFirst()){
  	    	while(!cursor.isAfterLast())
  	    	{
  	    		
  	    		seqNo=cursor.getInt(cursor.getColumnIndex("stop_sequence"));
  	    		
  	    	}
  	    	}
    	  return seqNo;
    	
    }
    
    
    public int selectEndSeqNo(int start_id,String trip_id)
    {
    	String myQuery ="select stop_sequence from Main_Table where trip_id="+"\""+trip_id+"\""+" and stop_id="+start_id;
    	Cursor cursor = myBusDataBase.rawQuery(myQuery, null);
    	int seqNo=0;
    	  if(cursor.moveToFirst()){
  	    	while(!cursor.isAfterLast())
  	    	{
  	    		
  	    		seqNo=cursor.getInt(cursor.getColumnIndex("stop_sequence"));
  	    		
  	    	}
  	    	}
    	  return seqNo;
    	
    }


    public ArrayList<String> getBusStopsTrial(int start_seq, int end_seq, String trip_id){
    	//PreparedStatement statement = 
    	 String myStopsQuery ="select stop_name,stop_sequence from Main_Table where trip_id="+"\""+trip_id+"\""+" and stop_sequence between "+start_seq+" and "+end_seq+" ORDER by stop_sequence ";
    	
    	 Cursor cursor = myBusDataBase.rawQuery(myStopsQuery, null);
    	 Log.i("STUNNER",myStopsQuery);
    	    String sched="";
    	    ArrayList<String> schedule=new ArrayList<String>();
    	    if(cursor.moveToFirst()){
    	    	while(!cursor.isAfterLast())
    	    	{
    	    		sched=cursor.getString(cursor.getColumnIndex("stop_name"));
    	    	
    	    		schedule.add(sched);
    	    		sched ="";
    	    		cursor.moveToNext();
    	    	}
    	    }
    	    if(cursor!=null)
    	    {
    	    	cursor.close();
    	    	
    	    }
    	    return schedule;

    }

    
    
    
    








}


