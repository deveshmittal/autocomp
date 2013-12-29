package com.example.autocomp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class ActivityMainDisplay extends Activity {

    AdapterExpandableList listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listDataHeader;
    HashMap<String, ArrayList<String>> listDataChild;
    ArrayList<String> fares=null;
    ArrayList<String> transitList = new ArrayList<String>();
    ArrayList<String> busishList = new ArrayList<String>();
    ArrayList<String> trainishList = new ArrayList<String>();
    ArrayList<Integer> flagArrayTransit = new ArrayList<Integer>();
    //Intent b = getIntent().getExtras();
    double startLat=0.0;
    double startLng=0.0;
    String stringJSONTrainish;
    boolean flag=false;
    String stringJSONTransit;
    String sourceHead=null;
    String destHead=null;
    String distance=null;
    String stringJSONBusish;
    JSONObject JSONTrainish;
    JSONObject JSONTransit;
    JSONObject JSONBusish;
    JSONObject jobj=null;
    JSONObject polyObject=null;
    List<LatLng> polyz=null;
    Menu mMenu;
    String weaString= null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		flagArrayTransit=null;
		transitList= null;
		busishList =null;
		trainishList =null;
		fares = new ArrayList<String>();
		 transitList=getIntent().getExtras().getStringArrayList("Both");   
		 busishList=getIntent().getExtras().getStringArrayList("Bus");
		 trainishList=getIntent().getExtras().getStringArrayList("Train");
		 startLat=getIntent().getExtras().getDouble("startLat");
		 startLng=getIntent().getExtras().getDouble("startLng");
		 sourceHead=getIntent().getExtras().getString("sourceHead");
		 destHead=getIntent().getExtras().getString("destHead");
		 distance=getIntent().getExtras().getString("distance");
		 weaString=getIntent().getExtras().getString("wString");
				 try
		 {
		 fares=getIntent().getStringArrayListExtra("fares");
		 }
		 catch(Exception ee)
		 {
			// Toast.makeText(getApplicationContext(), "ee->"+ee.toString(), Toast.LENGTH_LONG).show();
		 }
				 //un comment it
		 
			try {
				jobj= new JSONObject(getIntent().getStringExtra("json"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 stringJSONTrainish = getIntent().getExtras().getString("jsonTrainish");
		 stringJSONTransit = getIntent().getExtras().getString("jsonTransit");
		 stringJSONBusish = getIntent().getExtras().getString("jsonBusish");
		 flagArrayTransit=getIntent().getIntegerArrayListExtra("flagArrayTransit");
		 try {
			JSONTrainish = new JSONObject(stringJSONTrainish);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			JSONTransit = new JSONObject(stringJSONTransit);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 try {
			JSONBusish  =  new JSONObject(stringJSONBusish) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 

		 
		 
		 
		 // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
 
        // preparing list data
        prepareListData();
 
        listAdapter = new AdapterExpandableList(this, listDataHeader, listDataChild,flag);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
 
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
 
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
                
                return false;
            }
        });
 
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
 
            @Override
            public void onGroupExpand(int groupPosition) {
                
            }
        });
 
        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
 
            @Override
            public void onGroupCollapse(int groupPosition) {
                
 
            }
        });
 
        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {
 
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
            	
             String currChild = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
          ///   Toast.makeText(getApplicationContext(),"getInfoContents->"+groupPosition+"currChild->"+currChild, Toast.LENGTH_LONG).show();
             JSONObject currJSON = new JSONObject();
             
            
             if(groupPosition==0 )
             {
            	 currJSON = JSONTrainish; 
            	 
             }
             if(groupPosition ==  1)
             {
            	 currJSON = JSONBusish;
             }
             JSONObject jObjTransitPlan;

             if(groupPosition ==  2)
             {
            	 
            	int transitAr=0; 
            	for(int i =0 ;i<=2;i++){}
            	transitAr=(flagArrayTransit.get(childPosition));
            	 
            	 
            	
            	 currJSON = JSONTransit;
                  try {
                		  
					jObjTransitPlan= currJSON.getJSONObject("plan").getJSONArray("itineraries").getJSONObject(transitAr);
					Intent i = new Intent(getApplicationContext(), ActivityDetailedSummary.class);
					Bundle b =new Bundle();
					b.putString("wString",weaString);
					b.putString("JSONObjectTillLegs",jObjTransitPlan.toString());
				//	b.putIntegerArrayList("FlagArrayTransit", flagArrayTransit);
					Log.i("jObjTransitPlan:",jObjTransitPlan.toString());
					i.putExtras(b);
					startActivity(i);
                	  
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

             }

             if(groupPosition ==  3)
             {
            //	 Toast.makeText(getApplicationContext(),"groupPosition ==  3->", Toast.LENGTH_LONG).show();            	 
            	 	 try {
						polyObject= jobj.getJSONObject("overview_polyline");
						String polyline = polyObject.getString("points");
						
						
						
						Intent in = new Intent(getApplicationContext(), MappingAct.class);
						Bundle args = new Bundle();
						args.putString("Type", "Cab");
						args.putString("poly", polyline);
						args.putDouble("startLat", startLat);
						args.putDouble("startLng", startLng);
						args.putString("wString",weaString);
						in.putExtras(args);
						startActivity(in);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	 	
            	 //currJSON = JSONTransit;
             }
            // my code here
             JSONObject jObjPlan;
             if(groupPosition != 3 && groupPosition !=2)
             {
             try {
				jObjPlan = currJSON.getJSONObject("plan").getJSONArray("itineraries").getJSONObject(childPosition);
				
				Intent i = new Intent(getApplicationContext(), ActivityDetailedSummary.class);
				Bundle b =new Bundle();
				b.putString("JSONObjectTillLegs",jObjPlan.toString());
				b.putIntegerArrayList("FlagArrayTransit", flagArrayTransit);
				b.putString("wString",weaString);
				Log.i("YAY! EVA:",jObjPlan.toString());
				i.putExtras(b);
				startActivity(i);
            
       } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           //  JSONArray jArray= jObjPlan.plan
             }
            	
                
                return false;
            }
        });
	
	}
	
	
	
	
	private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<String>>();
 
        // Adding child data
        listDataHeader.add("Train");
        listDataHeader.add("Bus");
        if(transitList.size()>0){
        	
        listDataHeader.add("Transit");
        listDataHeader.add("Cabs");}
        else
        {
        	listDataHeader.add("Cabs");
        }
 
        listDataChild.put(listDataHeader.get(0), trainishList); // Header, Child data
        listDataChild.put(listDataHeader.get(1), busishList);
        if(transitList.size()>0){
        listDataChild.put(listDataHeader.get(2), transitList);
        listDataChild.put(listDataHeader.get(3), fares);}
        else
        {
        	flag=true;
            listDataChild.put(listDataHeader.get(2), fares);

        }
        
    }

	
	
	
	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
		this.mMenu = menu;
		return true;
	}
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		    case R.id.mapper:
		      
		    	try {
					polyObject= jobj.getJSONObject("overview_polyline");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Toast.makeText(this, "Exception in maindisplay e->"+e.toString(), Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				String polyline="";
				try {
					polyline = polyObject.getString("points");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				try
				{
				Intent in = new Intent(getApplicationContext(), MappingAct.class);
				Bundle args = new Bundle();
				args.putString("Type", "Cab");
				args.putString("poly", polyline);
		//		args.putString("poly", polyObject.toString());
				args.putDouble("startLat", startLat);
				args.putDouble("startLng", startLng);
				args.putString("wString",weaString);
				in.putExtras(args);
				//Toast.makeText(this, "weaString in maindisplay calling MappingAct->"+weaString, Toast.LENGTH_SHORT).show();
				startActivity(in);
				}
				catch(Exception ew)
				{
					Toast.makeText(this, "Exception in maindisplay>"+ew.toString(), Toast.LENGTH_SHORT);
				}
		      break;
		    /*
		      case R.id.menuitem2:
		      Toast.makeText(this, "Menu item 2 selected", Toast.LENGTH_SHORT)
		          .show();
		      break;
		      */

		    default:
		      break;
		    }

		    return true;
		  } 
	
	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {
	   
		MenuItem item = menu.findItem(R.id.item_text);

	    // Use this if you set with default actionbar item
	    //item.setTitle("sampleText");

	    TextView tv = (TextView)MenuItemCompat.getActionView(item).findViewById(R.id.ab_text);
	    String actText=sourceHead+" To "+destHead+ " "+distance;
	    tv.setText(actText);
	    return super.onPrepareOptionsMenu(menu);
		
		// final MenuItem menuItem = this.mMenu.findItem(R.id.user_id_label);  
	   // menuItem.setTitle(getString(R.string.user_id_text));
	  //  menuItem.setTitle(getString(R.string.user_id_text,
	  //          getIntent().getIntExtra(ConnectToServerActivity.USER_ID, -1)));
	 //   return super.onPrepareOptionsMenu(aMenu);
	}
	@Override
	public void onBackPressed()
	{
		
		this.finish();
		Intent mapIntent = new Intent(getApplicationContext(), PlacesAutocompleteActivity.class);
		startActivity(mapIntent);
	}

}
