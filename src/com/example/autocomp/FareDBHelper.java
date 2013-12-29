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

public class FareDBHelper extends SQLiteOpenHelper{
      
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.example.autocomp/databases/";
    private static String DB_NAME = "faredatabase.sqlite";
    
    private static String FARE_DB_PATH = "/data/data/com.example.autocomp/databases/";
    private static String FARE_DB_NAME = "faredatabase.sqlite";
   
    
  
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
    public FareDBHelper(Context context) {
  
        super(context, DB_NAME, null, 1);
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
             Log.i("DataBaseHelper", "database does not exist SHOOT");
            //By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
             //this.getReadableDatabase();
            this.getWritableDatabase();
 
            try {
 
            	Log.i("Create DataBAasse ","check this line ");
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
    
    public boolean checkFareTableDataBase()
    {
    	SQLiteDatabase fareTableDBcheck = null;
    	try{
    	String fareTableDatabasePath = FARE_DB_PATH + FARE_DB_NAME ; 
    	fareTableDBcheck = SQLiteDatabase.openDatabase(fareTableDatabasePath, null, SQLiteDatabase.OPEN_READWRITE);
    	}
    	catch(SQLiteException e)
    	{
    		e.printStackTrace();
    	}
    	
    	if(fareTableDBcheck!= null)
    	{
    		fareTableDBcheck.close();
    		return true;
    	}
		return false;}
    
    
    
    public int getFareFromFareTable (int stop_id_from , int stop_id_to)
    {
    	String findFareQuery = "SELECT general_fare from fares_list where from_start_stop_id ="+Integer.toString(stop_id_from)+" AND end_stop ="+Integer.toString(stop_id_to);
        SQLiteDatabase myFareDataBase = SQLiteDatabase.openDatabase(FARE_DB_PATH+FARE_DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
		Cursor fareQueryCursor = myFareDataBase.rawQuery(findFareQuery,null);
		int getFare = 0;
		if(fareQueryCursor.moveToFirst())
		{
			while(!fareQueryCursor.isAfterLast())
			{
				getFare =
				fareQueryCursor.getInt(fareQueryCursor
	                    .getColumnIndex("general_fare"));
				fareQueryCursor.moveToNext();
				
			}
			fareQueryCursor.close();
		}
    	return getFare;
    	
    }
    
    
    public int getFirstFareFromFareTable (int stop_id_from , int stop_id_to)
    {
    	String findFareQuery = "SELECT first_fare from fares_list where from_start_stop_id ="+Integer.toString(stop_id_from)+" AND end_stop ="+Integer.toString(stop_id_to);
        SQLiteDatabase myFareDataBase = SQLiteDatabase.openDatabase(FARE_DB_PATH+FARE_DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
		Cursor fareQueryCursor = myFareDataBase.rawQuery(findFareQuery,null);
		int getFare = 0;
		if(fareQueryCursor.moveToFirst())
		{
			while(!fareQueryCursor.isAfterLast())
			{
				getFare =
				fareQueryCursor.getInt(fareQueryCursor
	                    .getColumnIndex("first_fare"));
				fareQueryCursor.moveToNext();
				
			}
			fareQueryCursor.close();
		}
    	return getFare;
    	
    }
    
    public int getBusFare(String bus_name, int dist)
    {
    	
    	int getFare =0; 
    	String findFareQuery = "SELECT CASE ( select express from busTable where route_short_name="+"\""+bus_name+"\""+") WHEN 1 THEN (select ACexpress from bus_fares where kms =(select min(kms) from bus_fares where kms>"+dist+")) WHEN 0 THEN (select ACsuper from bus_fares where kms =(select min(kms) from bus_fares where kms>"+dist+")) END";
    	SQLiteDatabase myFareDataBase = SQLiteDatabase.openDatabase(FARE_DB_PATH+FARE_DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    	Cursor fareQueryCursor = myFareDataBase.rawQuery(findFareQuery,null);
		if(fareQueryCursor.moveToFirst())
		{
			while(!fareQueryCursor.isAfterLast())
			{
				getFare =
				fareQueryCursor.getInt(0);
				fareQueryCursor.moveToNext();
				
			}
			fareQueryCursor.close();
		}
		return getFare;
}
     
    public void openDataBase() throws SQLException{
 
        //Open the database
        String myPath = DB_PATH + DB_NAME;
         myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
    }
 
    public String readData()
    {
        String select = "Select * FROM routes";
         Log.i("DataBaseHelper","executing query");
          Cursor cursor = myDataBase.rawQuery(select, null);
         if(cursor != null)
         {
             Log.i("DataBaseHelper","cursor is not null");
         cursor.moveToFirst();
          }
         else
         {
             Log.i("DataBaseHelper","cursor is null");
         }
         String route_long_name= cursor.getString(cursor
                 .getColumnIndex("route_long_name"));
          Log.i("DataBaseHelper","route_long_name="+route_long_name);
         return route_long_name;
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
            //    directions=directions+route_id+"-"+cursor.getString(cursor
         //           .getColumnIndex("Directions"));
               cursor.moveToNext();
            }
           cursor.close();
         }
        return routeMap;
    }
    
    
    public ArrayList<Integer> createDummy(int id,int longestTripId,int direction_id)
    {
        routeID=id;
        String queryTemp=queryName+"\""+id+"\""+"and trips.direction_id="+"\""+direction_id+"\"";
         Log.i("DataBaseHelper","executing query-"+queryTemp);
        myDataBase.execSQL(queryTemp);
        ArrayList<Integer> stops=new ArrayList<Integer>();
        int stopid=0;
         String testquery="SELECT stop_id FROM Temp where trip_id="+longestTripId;
        Cursor cursor2 =myDataBase.rawQuery(testquery, null);
        
        if(cursor2 != null)
        {
            Log.i("DataBaseHelper","cursor2 is not null");
         cursor2.moveToFirst();
        }
        else
        {
            Log.i("DataBaseHelper","cursor2 is null");
        }
         if(cursor2.moveToFirst()) {
                while(!cursor2.isAfterLast()) {
                    stopid=cursor2.getInt(cursor2
                        .getColumnIndex("stop_id"));
                   stops.add(stopid);
               cursor2.moveToNext();
                }
               
             }
         cursor2.close();
        return stops;
    }
    
    public void deleteDummy()
    {
        String deleteQuery="DELETE FROM Temp";
        myDataBase.execSQL(deleteQuery);
         
    }
    public ArrayList<String> findStopNames(StringBuilder sb)
    {

        String querynames="SELECT stop_name FROM stops WHERE stop_id IN ("+sb+")";
        ArrayList<String> stopNames=new ArrayList<String>();
         
        Cursor cursor3 =myDataBase.rawQuery(querynames, null);
        if(cursor3 != null)
        {
            Log.i("DataBaseHelper","cursor3 is not null");
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
                Log.i("DataBaseHelper","cursor3.isAfterLast()="+cursor3.isAfterLast()+" stopNm-"+stopNm);
                cursor3.moveToNext();
                }
               
            }
        cursor3.close();
        return stopNames;
        

    }
    
    public int getStopID(String stopName)
     {
        
        String findQuery="SELECT stop_id FROM stops WHERE stop_name="+"\""+stopName+"\"";
        Log.i("DataBaseHelper","before rawquery findQuery-"+findQuery);
         Cursor cursorNew =myDataBase.rawQuery(findQuery, null);
        Log.i("DataBaseHelper","after rawquery findQuery");
        cursorNew.moveToFirst();
        int stopId=cursorNew.getInt(cursorNew
                 .getColumnIndex("stop_id"));
         Log.i("DataBaseHelper","findQuery="+findQuery);
         cursorNew.close();
         return stopId;
    }
    
    public void getStopName()
     {
        /*
        int stopid=0;
        String findStopIds="SELECT stop_id FROM stop_times" ;
        String findQuery="SELECT stop_name FROM stops WHERE stop_id=";
        String insertQuery="UPDATE stop_times SET stop_name=";
         String tempFind="";
        String stopNM="";

        Cursor cursor5 =myDataBase.rawQuery(findStopIds, null);

        if(cursor5.moveToFirst()) {
                while(!cursor5.isAfterLast()) {
                    stopid=cursor5.getInt(cursor5
                        .getColumnIndex("stop_id"));
                   tempFind=findQuery+"\""+stopid+"\"";
                   Cursor cursor4 =myDataBase.rawQuery(tempFind, null);
                    cursor4.moveToFirst();
                   stopNM=cursor4.getString(cursor4
                        .getColumnIndex("stop_name"));
                   String tempInsert=insertQuery+"\""+stopNM+"\""+"WHERE stop_id="+"\""+stopid+"\"";
                    myDataBase.execSQL(tempInsert);    
               // Log.i("DataBaseHelper","cursor3.isAfterLast()="+cursor5.isAfterLast()+" tempFind-"+tempFind+"stopNM-"+stopNM+"tempInsert="+tempInsert);
                cursor5.moveToNext();
                }
               
            }
        
        
/*        String findQuery="SELECT stop_name FROM stops WHERE stop_id="+"\""+stop+"\"";
         Cursor cursor4 =myDataBase.rawQuery(findQuery, null);
        cursor4.moveToFirst();
        String stopName=cursor4.getString(cursor4
                .getColumnIndex("stop_id"));
         Log.i("DataBaseHelper","findQuery="+findQuery);
          */
         //return stopName;
        
        
    }
    
    
    
    
    
    public LinkedHashMap<Integer,String> findIT(int stopFrom,int longestRoute,int direction_id)
     {  
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String time=sdf.format(cal.getTime()) ;
        String test3="select trip_id,departure_time,start_stop_id,end_stop_id from Main_Table where stop_id="+"\""+stopFrom+"\""+" and direction_id="+"\""+direction_id+"\""+" and end_stop_id !="+"\""+stopFrom+"\""+" and time(departure_time) > "+"\""+time+"\""+" order by time(departure_time)";
         String stopNameQuery="select stop_name from stops where stop_id="+"\"";
        LinkedHashMap<Integer,String> depMap = new LinkedHashMap<Integer,String>();

        Cursor findTrip=myDataBase.rawQuery(test3, null);
         Cursor stopNm=null;
        int counter=0;
        int tempTrip=0;    
        int startID=0;
        int endID=0;
        int stopID=0;
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
              ++counter;
               Log.i("DataBaseHelper","trip_id->"+tempTrip+" ,timeDep->"+timeDep+"counter->"+counter+"startID->"+startID+" endID->"+endID+"stopNMSt->"+stopNMSt+"stopNMEnd->"+stopNMEnd);
                   depMap.put(tempTrip, timeDep);
                  findTrip.moveToNext();
          
               }
        }
        findTrip.close();


        String tempLastStopSeq="";
         String tempFirstStopSeq="";
        int stopIdLast=0;
        int stopIdFirst=0;
        int tripCount=0;
        String    lastStopSeq1="select stop_id from Temp where trip_id="+"\"";
         String lastStopSeq2="\""+ " AND stop_sequence in (select max(stop_sequence) from Temp where trip_id="+"\"";
        String lastStopSeq3="\""+")";
         
        String firstStopSeq1="select stop_id from Temp where trip_id="+"\"";
        String firstStopSeq2="\""+ " AND stop_sequence in (select min(stop_sequence) from Temp where trip_id="+"\"";
         String firstStopSeq3="\""+")";
        String getstops="select distinct stop_id from Temp where trip_id="+longestRoute;
//        Cursor stops_list =myDataBase.rawQuery(getstops, null);
         ArrayList<Integer> stopSEQ=new ArrayList<Integer>();
    //    int stopID=0;
//        if(stops_list.moveToFirst()) {
//                while(!stops_list.isAfterLast()) {
                    
 /*                   stopID=stops_list.getInt(stops_list
                        .getColumnIndex("stop_id"));
                   stopSEQ.add(stopID);
                   Log.i("DataBaseHelper","stopID-"+stopID);
                    stops_list.moveToNext();   */
//                }
//        }
//        int firstStop=stopSEQ.get(0);
//        int indexCurr=stopSEQ.indexOf(stopFrom);
//        Log.i("DataBaseHelper","firstStop="+firstStop);
 //        int indexFirst=0;
//        int indexLast=0;
//        String departureTime="";
        

    //    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    //    Calendar cal = Calendar.getInstance();
 //        String time=sdf.format(cal.getTime()) ;

        Log.i("DataBaseHelper","time="+time);
//        String tripsSel="select trip_id from Temp where stop_id="+"\""+stopFrom+"\""+"and time(departure_time) > "+"\""+time+"\"";
   //      String tripsSel="select trip_id from Temp where stop_id="+"\""+stopFrom+"\"";
    //      String tripsSel="select distinct trip_id from Temp";
    
//        String tripsSel="select trip_id from Temp where stop_id="+"\""+stopFrom+"\""+ "and time(departure_time) > "+"\""+time+"\"";
         String tripsSel="select trip_id from Temp where stop_id="+"\""+stopFrom+"\"";
 //      String tripsSel="select trip_id from Temp where  time(departure_time) > "+"\""+time+"\"";
     //    String tripsSel="select trip_id from Temp";
        Log.i("DataBaseHelper","tripsSel->"+tripsSel);
        
    //    Log.i("DataBaseHelper","indexCurr="+indexCurr);
     //    Cursor cursorLoc =myDataBase.rawQuery(tripsSel, null);
    
//        if(cursorLoc.moveToFirst()) {
//               while(!cursorLoc.isAfterLast()) {
     /*             
                   tempTrip=cursorLoc.getInt(cursorLoc
                         .getColumnIndex("trip_id"));
                   
                   tempLastStopSeq=lastStopSeq1+tempTrip+lastStopSeq2+tempTrip+lastStopSeq3;
                   final SQLiteStatement stmt = myDataBase
                         .compileStatement(tempLastStopSeq);
                    stopIdLast= (int)stmt.simpleQueryForLong();
                   tempFirstStopSeq=firstStopSeq1+tempTrip+firstStopSeq2+tempTrip+firstStopSeq3;
                    final SQLiteStatement stmtFirst = myDataBase
                        .compileStatement(tempFirstStopSeq);
                   stopIdFirst=(int)stmtFirst.simpleQueryForLong();
                   
                    indexFirst=stopSEQ.indexOf(stopIdFirst);
                   indexLast=stopSEQ.indexOf(stopIdLast);
                   */
    //               Log.i("DataBaseHelper","tempTrip->"+tempTrip+"stopIdFirst->"+stopIdFirst+"indexFirst-"+indexFirst+"indexCurr->"+indexCurr+"stopIdLast-"+stopIdLast+"indexLast-"+indexLast);
                //    String To="update stop_times set end =";

/*
                   String tempTo="";
                   if((indexFirst < indexLast) && (indexFirst != -1) && (indexLast != -1))
                    {
                       tempTo=To+"\""+stopID+"\""+ " where trip_id="+"\""+tempTrip+"\"";
                               
                        myDataBase.execSQL(tempTo);
                       Log.i("DataBaseHelper","tempTo="+tempTo);
                       
                   }
                   else if((indexFirst > indexLast) && (indexFirst != -1) && (indexLast != -1))
                    {
                       
                       tempTo=To+"\""+firstStop+"\""+ " where trip_id="+"\""+tempTrip+"\"";
                       myDataBase.execSQL(tempTo);
                        Log.i("DataBaseHelper","tempTo reverse ="+tempTo);
                   }
                   */
    //               if((indexFirst <= indexCurr) && (indexLast > indexCurr)   && (indexFirst != -1) && (indexLast != -1))
                    {
                       
                       //if(tripCount < 3)
                       {
                           
                   //        tripsSelected.add(tempTrip);
                   //    ++tripCount;
                        }
                   /*else
                       {
                           break;
                       }
               */        
//                       Log.i("DataBaseHelper","selected first stopIdFirst for stop_id  trip_id->"+tempTrip+"->"+"stopIdFirst->"+stopIdFirst+"stopIdLast->"+stopIdLast+"stopFrom->"+stopFrom+"indexFirst="+indexFirst+"indexCurr->"+indexCurr+"indexLast="+indexLast+"departureTime-"+departureTime);    
                    }
                   
                   
                   
//                   cursorLoc.moveToNext();
               
//               }
//                }
              
        //         String getDepart="select departure_time from stop_times where stop_id="+"\""+stopFrom+"\""+" and trip_id="+"\"";
       //         String tempDepart="";
        //         Iterator<Integer> itr=tripsSelected.iterator();
    //            int tempTr=0;
      //          String timeDep="";
           /*    Cursor cursorDep=null ;
                while(itr.hasNext())
                 {    tempTr=itr.next();
                   tempDepart=getDepart+tempTr+"\"";
                   cursorDep=myDataBase.rawQuery(tempDepart, null);
                   cursorDep.moveToFirst();
                    timeDep=cursorDep.getString(cursorDep
                        .getColumnIndex("departure_time"));
                   depMap.put(tempTr, timeDep);
                   Log.i("DataBaseHelper","trip_id="+tempTr+"tempDepart="+tempDepart);
                    
                }
                */
           //    cursorDep.close();
/*               for (Map.Entry entry : depMap.entrySet()) { 
                   Log.i("DataBaseHelper","trip_id: " + entry.getKey() + ",departure_time->" + entry.getValue()); 
                    }    
*/
    
    
              
//        cursorLoc.close();
//        stops_list.close();
    
        return depMap;
    }
    

    public void getStops(int trip_id)
     {
        String stopsFinder="select stop_id,departure_time  from stop_times where trip_id="+"\""+trip_id+"\"";
        Cursor getStops=myDataBase.rawQuery(stopsFinder, null);
         
        if(getStops.moveToFirst()) {
               while(!getStops.isAfterLast()) {
                   
          //        tempTrip=getStops.getInt(getStops
           //             .getColumnIndex("trip_id"));
               //    timeDep=getStops.getString(getStops
          //              .getColumnIndex("departure_time"));
              
              
              
                  getStops.moveToNext();
           
               }
        }
        getStops.close();
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
             Log.i("DataBaseHelper","sched->"+sched);
             sched="";
             getStops.moveToNext();
          }
    }
    
     getStops.close();
    

    return schedule;
    }
    
    
    
    public String getDirections()
    {
        
        return directions;
    }
 
        // Add your public helper methods to access and get content from the database.
        // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
 
}