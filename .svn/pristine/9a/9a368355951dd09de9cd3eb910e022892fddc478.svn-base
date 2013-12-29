package com.example.autocomp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BusMarkAct extends Activity implements OnItemClickListener{

	String intiD="";
	ListView listViewItems=null;
	BusMapDataBaseHelper dbh=null;
	ArrayList<String> busNames=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_mark);
		
		Bundle extras = getIntent().getExtras();
		dbh=new BusMapDataBaseHelper(getApplicationContext());
		
		try {
			dbh.createDataBase();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "calling getbusstops-> e"+ e.toString(), Toast.LENGTH_LONG).show();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			dbh.openDataBase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "openDataBase->"+e.toString(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		try{
			if (extras != null)
			{
				
				intiD=extras.getString("initId");
			}
			}
			catch(Exception e)
			{
				Toast.makeText(getApplicationContext(),"exception->"+e.toString(), Toast.LENGTH_LONG).show();
				
			}
		busNames=new ArrayList<String>();
		busNames=dbh.testfn(intiD);
		
		CustomAdapterBus adapter = new CustomAdapterBus(this, R.layout.activity_marker_test, busNames,this);
		listViewItems = (ListView) findViewById(R.id.listStops);
		
		
		 Animation animation = new AlphaAnimation(0.0f, 1.0f);

         animation.setDuration(500);
         AnimationSet set = new AnimationSet(true);
         set.addAnimation(animation);

         animation = new TranslateAnimation(

                 Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,

                 Animation.RELATIVE_TO_SELF, -1.0f,Animation.RELATIVE_TO_SELF, 0.0f

             );
        
         animation.setDuration(100);

         set.addAnimation(animation);
        
         LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        
         listViewItems.setAdapter(adapter);
      
      listViewItems.setLayoutAnimation(controller);
		
  	listViewItems.setOnItemClickListener(new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
        	
        	int index=arg2;
        	
            registerForContextMenu(listViewItems);
            listViewItems.showContextMenu();
            String selectedItem=(String)((TextView)arg1.findViewById(R.id.textV)).getText();
           String [] tempsel=null; 
           tempsel=selectedItem.split(" ");
           String busid=tempsel[0];
           Intent intent = new Intent(BusMarkAct.this, BusStops.class);
           intent.putExtra("busID", busid);
           startActivity(intent);        
            	            
           
        }
    });
	}

	

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bus_mark, menu);
		return true;
	}






	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
