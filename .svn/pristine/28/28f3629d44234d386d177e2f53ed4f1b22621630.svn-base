package com.example.autocomp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BusStops extends Activity {

	BusMapDataBaseHelper dbh=null;
	ListView listViewItems=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_stops);
		
		Intent i=getIntent();
		String bus=i.getStringExtra("busID");
		
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
		
		ArrayList<String> ar=new ArrayList<String>();
		ar=dbh.getBusStops(bus);
		
		ArrayAdapter  arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ar);
		listViewItems = (ListView) findViewById(R.id.busStops);
		listViewItems.setAdapter(arrayAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bus_stops, menu);
		return true;
	}

}
