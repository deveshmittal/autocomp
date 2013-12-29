package com.example.autocomp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.example.autocomp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class MyRailCustomAdapter extends ArrayAdapter {
    Context context;
    
    ArrayList<String> mappList = new  ArrayList<String>();
    
    int textViewResourceId;
   
    //private final Activity activity;
    
    public MyRailCustomAdapter(Activity context, int textViewResourceId,ArrayList<String> mappList) {
      
    	super(context,textViewResourceId, mappList);
    	//  super(context, textViewResourceId, mapp);
        this.context = context;
        this.textViewResourceId = textViewResourceId;
        this.mappList = mappList;
       // this.activity=act;
    }


	@Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
      if (convertView == null)
      {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //convertView = vi.inflate(android.R.layout.simple_spinner_dropdown_item, null);
        convertView = vi.inflate(R.layout.none_tview, null);
      }
      LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
    final View spinnerEntry = inflater.inflate(R.layout.none_tview, null);   
//    final TextView data = (TextView) spinnerEntry.findViewById(R.id.textV);
//    String textTemp=(String)arrayList.get(position);
//    
//    
//        data.setText(textTemp);
    
    return spinnerEntry;

      
    }
 
    
    public View getView(int position, View convertView, ViewGroup parent) {
        
        String myMap = mappList.get(position);
        Log.i("CAD !",Integer.toString(position));
        
        
        //Log.i("MyMap Length is:",Integer.toString(mappList.size()));
        
    	
    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    	View mySpinnerEntry= null ;
    	mySpinnerEntry = inflater.inflate(R.layout.adapter_list_row, null);
    		
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
    	df.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
  
    	TextView stopString=(TextView)mySpinnerEntry.findViewById(R.id.textView1);
    	stopString.setText(myMap);


        mySpinnerEntry.setBackgroundColor(Color.parseColor("#d3d3d3"));
        return mySpinnerEntry;
        }
        
    	

}
