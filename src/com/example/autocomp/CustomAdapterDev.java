package com.example.autocomp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class CustomAdapterDev extends ArrayAdapter {
    Context context;
    //RailDataBaseHelper rdbh ;
    
    ArrayList<String> mappList = new  ArrayList<String>();
    ListView finalListView = null;
    Button button;
    ArrayList<String> arList = new ArrayList<String>();
    
   // myMap = mappList.get(0);
   
    //myMap = mappList.get(0);
    int textViewResourceId;
    String conveyance ;
    String routeNo;
    String fromTime;
    String toTime;
    String fare = new String();
    String stopsCompile= new String();
    String from;
    String to ; 
    String strMS ;
    String polylineString;
    
    //private final Activity activity;
    
    public CustomAdapterDev(Activity context, int textViewResourceId,ArrayList<String> mappList) {
      
    	super(context,textViewResourceId, mappList);
    	//  super(context, textViewResourceId, mapp);
        this.context = context;
        this.textViewResourceId = textViewResourceId;
        this.mappList = mappList;
        
     
	    
        
    }
//        
//        rdbh = new RailDataBaseHelper(context);
//        try {
//        	rdbh.createRailDataBase();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			rdbh.openRailDataBase();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
       // this.activity=act;
  

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
      if (convertView == null)
      {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //convertView = vi.inflate(android.R.layout.simple_spinner_dropdown_item, null);
        convertView = vi.inflate(R.layout.tview, null);
      }
      LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
    final View spinnerEntry = inflater.inflate(R.layout.tview, null);   
//    final TextView data = (TextView) spinnerEntry.findViewById(R.id.textV);
//    String textTemp=(String)arrayList.get(position);
//    
//    
//        data.setText(textTemp);
    
    return spinnerEntry;

      
    }
 
    
    public View getView(int position, View convertView, ViewGroup parent) {
        
    	final ArrayList<String> brList = new ArrayList<String>();
    	polylineString="";
    	
    	
        String myMap = mappList.get(position);
        Log.i("CAD !",Integer.toString(position));
        String[] brokenString = myMap.split("--");
        conveyance = brokenString[0];
        routeNo = brokenString[1];
    	fromTime = brokenString[2];
    	toTime = brokenString[3];
    	fare =brokenString[4];
    	Log.i("Fare Custom Adapter",fare);
    	stopsCompile =brokenString[5];
 
    	Log.i("Stops Combined:",stopsCompile);
    	from = brokenString[6];
    	to = brokenString[7];
    	strMS = brokenString[8];
    	polylineString = brokenString[9];
    	String stopsAdd = "";
    	
    	    	
    	
    	
    	
    	
    	
    	
    	
    	String[] stops = stopsCompile.split("\\#");
    	
    	int totalStops = stops.length;
    	
    	
    final ArrayList<String> tmpList  = new ArrayList<String>();
    for(int i=0;i<totalStops;i++)
	{
		tmpList.add(stops[i]);
	}
    
    for (String  brString:brList){
		Log.i("ArrayList added", stopsAdd);
		tmpList.add(brString);
	stopsAdd += brString + "#" ;
	}
    
    
    
    
    
    
    
   

    	
    	
    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	//LayoutInflater inflater = LayoutInflater.from(context);

    	View mySpinnerEntry= null ;
    	  

	
    		mySpinnerEntry = inflater.inflate(R.layout.list_row, null);
    		
    	
    	long fromMillis = Long.parseLong(fromTime);
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
    	df.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
    	String fromTimeDisp = df.format(fromMillis);
    	
    	
    	
    	Date fromDate=new Date(fromMillis);
    	long toMillis = Long.parseLong(toTime);
    	
    	
    	SimpleDateFormat dfTo = new SimpleDateFormat("HH:mm");
    	dfTo.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
    	String toTimeDisp = dfTo.format(toMillis);

    	
    	long millis = Long.parseLong(strMS);
    	
    	
    	
    	

    
    String showhm= new String("in ");
    	// 
    	String hm = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
    	String[] hmSplit = hm.split(":");
    	int hours = Integer.parseInt(hmSplit[0]);
    	if(hours>0)
    	{
    		if(hours==1){
    			showhm = showhm + Integer.toString(hours)+" hr ";
    			
    		}
    		else{
    		showhm = showhm + Integer.toString(hours)+" hrs ";}
    	}
    	showhm +=hmSplit[1]+"m";
    	
   
    	  
    	TextView fromtoAbsoluteTime =(TextView)mySpinnerEntry.findViewById(R.id.fromtoTime);
    	fromtoAbsoluteTime.setText(fromTimeDisp+"-"+toTimeDisp);
    	LinearLayout layout = (LinearLayout)mySpinnerEntry.findViewById(R.id.llContainer);
    	
    	
    	
    	TextView busRouteNoTitle =(TextView)mySpinnerEntry.findViewById(R.id.title);
    	
    	TextView fareFinal =(TextView)mySpinnerEntry.findViewById(R.id.fareFinal);
    	
    	String rupeeSymbol = context.getString(R.string.rupees);
    	
    	fareFinal.setText(fare);
    	
    	TextView fromToArtist =(TextView)mySpinnerEntry.findViewById(R.id.artist);
    	TextView durationInHM =(TextView)mySpinnerEntry.findViewById(R.id.duration);
    	
    	ImageView conveyanceIcon = (ImageView)mySpinnerEntry.findViewById(R.id.list_image);
    	String imageFile = conveyance;
    	conveyanceIcon.setImageResource(context.getResources().getIdentifier(imageFile, "drawable", context.getPackageName()));
    	String fromToSet = new String();
    	
    
    	fromToSet= from +" - " +to ;
    	if (conveyance.equalsIgnoreCase("walking"))
    	{  if(millis>600000)
    	{String myText ="a. walking can be replaced by auto/taxi\nb. autos do not ply in south mumbai/colaba";
    		TextView info =(TextView)mySpinnerEntry.findViewById(R.id.info);
    		info.setText(myText);
    	}
    		fromToSet = "Walk to "+ to;
    		if(position == getCount()-1)
    		{
    			fromToSet = "Walk to Destination";
    		}
    		
    	}
    	if ((conveyance.equalsIgnoreCase("train")|| conveyance.equalsIgnoreCase("bus"))&& totalStops>=2)
    	{
			//arList=rdbh.getInterStops(102,87,fromTime,toTime);
    	//	Toast.makeText(context, "totalStops->"+totalStops, Toast.LENGTH_LONG).show();
    		Button createdButton = new Button(context);
    	    createdButton.setText("Stops");
    	    createdButton.setPaintFlags(createdButton.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
    	    createdButton.setBackgroundColor(Color.TRANSPARENT);
    	    createdButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    	    layout.addView(createdButton);
    	    
    	    
    	    
    	    createdButton.setOnClickListener(new OnClickListener() {
	        	 
		  		  @SuppressWarnings("deprecation")
				@Override
		  		  public void onClick(View arg0) {
		  			
		   
		  			// custom dialog
		  			final Dialog dialog = new Dialog(context);
		  			dialog.setContentView(R.layout.stops_dialog);
		  			 WindowManager window = (WindowManager)context

		  				    .getSystemService(Context.WINDOW_SERVICE);

		  				    Display display = window.getDefaultDisplay();

		  				int    displayheight = display.getHeight();

		  				int    displaywidth = display.getWidth();
		  				    dialog.getWindow().setLayout(displaywidth ,
		  				                (displayheight-20) );
		  			
		  			
		  			dialog.setTitle("Stops");
		   
		  			
		  			// set the custom dialog components - text, image and button
//		  			TextView text = (TextView) dialog.findViewById(R.id.text);
//		  			text.setText("Android custom dialog example!");
		  			finalListView= (ListView)dialog.findViewById(R.id.stops);
		  			setAdapt(tmpList);
		  		
		  			//ImageView imgView = dialog.findViewById(R)
		   
		  			Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		  			// if button is clicked, close the custom dialog
		  			dialogButton.setOnClickListener(new OnClickListener() {
		  				@Override
		  				public void onClick(View v) {
		  					dialog.dismiss();
		  				
		  				}
		  			});
		   
		  			dialog.show();
		  		  }
		  		});
    	    
    	    
    	    
    	  
    	}
    	
    	

    	busRouteNoTitle.setText(routeNo);
    	fromToArtist.setText(fromToSet);
    	durationInHM.setText(showhm);
     //   mySpinnerEntry.setBackgroundColor(Color.parseColor("#98BDC7"));
    
        return mySpinnerEntry;
        }
        

	public void setAdapt( ArrayList<String>  myArrayMap)
    {
		 Log.i("Master","setAdapt");
		// public CustomAdapterAks(Context context, int textViewResourceId,ArrayList<String>)
        MyRailCustomAdapter adapter = new MyRailCustomAdapter((Activity) context, R.layout.activity_activity_transit, myArrayMap);
        for (String str: myArrayMap){
        Log.i("NYC!",str);
        }
        
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
           
           
           Log.i("Thank God","Reached Adapter");
           if(finalListView != null){
        	   finalListView.setAdapter(adapter);}
           else{Log.i("DELHI CODE!","listViewItems is NULL");}
           Log.i("Thank God","Reached After Adapter");
           finalListView.setLayoutAnimation(controller);
           
        
}
	
	
	
    
    
    
    
    
    
    
    
  
}
