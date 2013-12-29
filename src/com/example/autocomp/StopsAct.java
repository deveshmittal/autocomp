package com.example.autocomp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class StopsAct extends Activity {
	ListView listViewItems=null;
	ArrayList<String> stop_list=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stops);
		
		Intent i = getIntent();  
		stop_list = i.getStringArrayListExtra("stoplist");
		ArrayAdapter  arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stop_list);
	//	CustomAdapterAks adapter = new CustomAdapterAks(this, R.layout.activity_marker_test, stop_list,this);
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
       
       
         listViewItems.setAdapter(arrayAdapter);
         listViewItems.setLayoutAnimation(controller);
       
     
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stops, menu);
		return true;
	}

}
