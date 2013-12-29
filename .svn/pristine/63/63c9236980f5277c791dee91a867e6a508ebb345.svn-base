package com.example.autocomp;


import java.util.ArrayList;

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
import android.widget.TextView;

import com.google.android.maps.MapActivity;

public class CustomAdapterTrain extends ArrayAdapter {

	Context context;
    int textViewResourceId;
    ArrayList arrayList;
    private final Activity activity;
    TextView tv=null;
    TextView tv1=null;
    public CustomAdapterTrain(Context context, int textViewResourceId,  ArrayList arrayList,Activity act) {
        super(context, textViewResourceId, arrayList);

        this.context = context;
        this.textViewResourceId = textViewResourceId;
        this.arrayList = arrayList;
        this.activity=act;
    }
    
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
      if (convertView == null)
      {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //convertView = vi.inflate(android.R.layout.simple_spinner_dropdown_item, null);
        convertView = vi.inflate(R.layout.rowview, null);
      }
  	final LayoutInflater inflater = activity.getLayoutInflater();
	
	final View spinnerEntry = inflater.inflate(
	R.layout.rowview, null);   
	String temp=(String)arrayList.get(position);
	String [] textemp=temp.split("/");
	String setext=textemp[0];
	String set2=textemp[1];
	tv=(TextView)spinnerEntry.findViewById(R.id.textV);
	tv1=(TextView)spinnerEntry.findViewById(R.id.textV1);
	
	tv.setText(setext);
	tv1.setText(set2);
	spinnerEntry.setBackgroundColor(Color.parseColor("#98AFC7"));
	return spinnerEntry;

      
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	final LayoutInflater inflater = activity.getLayoutInflater();
    	
    	View spinnerEntry=null;
    	String temp=(String)arrayList.get(position);
    	
    	spinnerEntry=inflater.inflate(
    	    	R.layout.rowview, null);
    	
    	String [] textemp=temp.split("/");
    	String setext=textemp[0];
    	String set2=textemp[1];
    	tv=(TextView)spinnerEntry.findViewById(R.id.textV);
    	tv1=(TextView)spinnerEntry.findViewById(R.id.textV1);
    	
    	tv.setText(setext);
    	tv1.setText(set2);
    	spinnerEntry.setBackgroundColor(Color.parseColor("#98AFC7"));
    	return spinnerEntry;
    }
}
