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

public class CustomAdapterAks extends ArrayAdapter {

	Context context;
    int textViewResourceId;
    ArrayList arrayList;
    private final Activity activity;
    ImageButton imgCall=null;
    public CustomAdapterAks(Context context, int textViewResourceId,  ArrayList arrayList,MapActivity act) {
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
        convertView = vi.inflate(R.layout.tview, null);
      }
  	final LayoutInflater inflater = activity.getLayoutInflater();
	
	final View spinnerEntry = inflater.inflate(
	R.layout.tview, null);   
	final TextView data = (TextView) spinnerEntry
			.findViewById(R.id.textV);
	String textTemp=(String)arrayList.get(position);
	
	Log.i("CustomAdapterAks","textTemp-> "+textTemp);
	
	return spinnerEntry;

      
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	final LayoutInflater inflater = activity.getLayoutInflater();
    	
    	View spinnerEntry=null;
    	String temp=(String)arrayList.get(position);
    	String [] test=null;
    	test=temp.split("-");
    	int flag=0;
    	Log.i("CustomAdapterAks","test[0]->"+test[0]);
    	if(test[0].equals("Duration"))
    		{
    		flag=1;
    			spinnerEntry=inflater.inflate(
    	    	    	R.layout.cview, null);
    		}
    	else
    	{	spinnerEntry = inflater.inflate(
    	    	R.layout.tview, null);
    	//btncall
    	
    	imgCall=(ImageButton)spinnerEntry.findViewById(R.id.btncall);
    	Intent callIntent = new Intent(Intent.ACTION_CALL);
    	if(test[0].equals("Taxi"))
    	{
    		callIntent.setData(Uri.parse("tel:+919940170531"));	
    	}
    	else if(test[0].equals("Cab"))
    	{
    		callIntent.setData(Uri.parse("tel:+919940170531"));
    	}
    	
    	final Intent tempIntent=callIntent;
    	imgCall.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
			
				
				context.startActivity(tempIntent);
				
				
				
			}
		});
    
    		
    	}
    	spinnerEntry.setBackgroundColor(Color.parseColor("#98AFC7"));
    	    	final TextView data = (TextView) spinnerEntry
    	    			.findViewById(R.id.textV);
    	    	
    	    	
    	    	final TextView dataTax = (TextView) spinnerEntry
    	    			.findViewById(R.id.textTaxName);
    	    	
    	    	
    	    	if(flag == 0)
    	    	{
    	    		String [] dat=null;
    	    		String [] dat2=null;
    	    		String testString=(String)arrayList.get(position);
    	    		dat=testString.split("-");
    	    		dataTax.setText(dat[0]);
    	    		dat2=testString.split(">");
    	    		data.setText(dat2[1]);
    	    	}
    	    	else
    	    	{
    	    		String [] testD=test[1].split(">");
    	    		Log.i("CustomAdapterAks","testD[1]->"+test[0]);
    	    		data.setText((CharSequence) testD[1]);
    	    	}
    	return spinnerEntry;
    }
}
