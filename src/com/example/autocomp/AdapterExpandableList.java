package com.example.autocomp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
 
public class AdapterExpandableList extends BaseExpandableListAdapter {
 
    private Context _context;
    boolean flagT=false;
    private ArrayList<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<String>> _listDataChild;
 
    public AdapterExpandableList(Context context, ArrayList<String> listDataHeader,
            HashMap<String, ArrayList<String>> listChildData,boolean flag) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.flagT=flag;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
  
     if(!flagT)
     {
        if(groupPosition == 3)
        {
        	  ImageButton imgCall=null;
        ArrayList<String> temp=null;
        temp= _listDataChild.get("Cabs");
        String [] disp=null;
        disp=(temp.get(childPosition)).split("\\-");
       // String telid=disp[4];
        LayoutInflater infalInflater = (LayoutInflater) this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String telid=disp[2];
        String transp=disp[1];
        String fare=disp[0];
        String textF=fare;
        if(transp.equalsIgnoreCase("Auto") || transp.equalsIgnoreCase("Taxi"))
        {
        	convertView = infalInflater.inflate(R.layout.autoview, null);
        	
        	TextView auto = (TextView) convertView
        	        .findViewById(R.id.textAuto);
        	TextView autoCs = (TextView) convertView
        	        .findViewById(R.id.autoCs);
        	autoCs.setText(fare);
        	auto.setText(transp);
        	
        }
        else
        {
        	convertView = infalInflater.inflate(R.layout.cabview, null);	
        	
        
        	
        	 if(transp.equalsIgnoreCase("Auto") || transp.equalsIgnoreCase("Taxi"))
             {
             	convertView = infalInflater.inflate(R.layout.autoview, null);
             	
             	TextView auto = (TextView) convertView
             	        .findViewById(R.id.textAuto);
             	TextView autoCs = (TextView) convertView
             	        .findViewById(R.id.autoCs);
             	autoCs.setText(fare);
             	auto.setText(transp);
             	
             }
       // convertView = infalInflater.inflate(R.layout.cabview, null);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+telid));
      
    	imgCall=(ImageButton)convertView.findViewById(R.id.btncallCabs);
    	final Intent tempIntent=callIntent;
    	imgCall.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
			
				
				_context.startActivity(tempIntent);
				
				
				
			}
		});
        
    	TextView startC = (TextView) convertView
    	        .findViewById(R.id.textCs);
    	
    	
        TextView startCab = (TextView) convertView
        .findViewById(R.id.textCabs);
      
     //   String dur=disp[0];
     //   String dist=disp[3];
        
     
        startCab.setText(transp);
        startC.setText(textF);
        }
        
        }
        
        else{
        	  final String childText = (String) getChild(groupPosition, childPosition);
        	  
       // 	if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_items, null);
        //    }
        	
        	
        	
            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListItem);
            txtListChild.setText(childText);
        	
        	
        	
     
        }
    }
     else
     {
    	 
    	 if(groupPosition == 2)
         {
         	  ImageButton imgCall=null;
         ArrayList<String> temp=null;
         temp= _listDataChild.get("Cabs");
         String [] disp=null;
         disp=(temp.get(childPosition)).split("\\-");
        // String telid=disp[4];
         String telid=disp[2];
         LayoutInflater infalInflater = (LayoutInflater) this._context
                 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         String transp=disp[1];
         String fare=disp[0];
         String textF=fare;
         if(transp.equalsIgnoreCase("Auto") || transp.equalsIgnoreCase("Taxi"))
         {
         	convertView = infalInflater.inflate(R.layout.autoview, null);
         	
         	TextView auto = (TextView) convertView
         	        .findViewById(R.id.textAuto);
         	TextView autoCs = (TextView) convertView
         	        .findViewById(R.id.autoCs);
         	autoCs.setText(fare);
         	auto.setText(transp);
         	
         }
         else
         {
         
 
         
         
         
         convertView = infalInflater.inflate(R.layout.cabview, null);
         Intent callIntent = new Intent(Intent.ACTION_CALL);
         callIntent.setData(Uri.parse("tel:"+telid));
    
     	imgCall=(ImageButton)convertView.findViewById(R.id.btncallCabs);
     	final Intent tempIntent=callIntent;
     	imgCall.setOnClickListener(new View.OnClickListener() {

 			public void onClick(View v) {
 			
 				
 				_context.startActivity(tempIntent);
 				
 				
 				
 			}
 		});
         
     	TextView startC = (TextView) convertView
     	        .findViewById(R.id.textCs);
     	
     	
         TextView startCab = (TextView) convertView
         .findViewById(R.id.textCabs);
       
      //   String dur=disp[0];
      //   String dist=disp[3];
         
         startCab.setText(transp);
         startC.setText(textF);
     
         
         }
         }
         
         else{
         	  final String childText = (String) getChild(groupPosition, childPosition);
         	  
        // 	if (convertView == null) {
                 LayoutInflater infalInflater = (LayoutInflater) this._context
                         .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 convertView = infalInflater.inflate(R.layout.list_items, null);
         //    }
         	
         	
         	
             TextView txtListChild = (TextView) convertView
                     .findViewById(R.id.lblListItem);
             txtListChild.setText(childText);
         	
         	
         	
      
         }
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
    	 
     }
        /*
        TextView startTripChild = (TextView) convertView
                .findViewById(R.id.tripStarts);
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        startTripChild.setText("in 10 m");
    //    txtListChild.setText(childText);
     * */
     
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
        
        /*
        
        Iterator it = _listDataChild.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            Toast.makeText(_context, "pairs.getKey()->"+pairs.getKey()+"pairs.getValue()->"+pairs.getValue(), Toast.LENGTH_LONG).show();
            it.remove(); // avoids a ConcurrentModificationException
        }
        
        */
        
        
        
        
        
        
        
        
        
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}