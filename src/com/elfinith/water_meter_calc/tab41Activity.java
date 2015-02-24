package com.elfinith.water_meter_calc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class tab41Activity extends Activity {

	DBHelper dbHelper;

	String strDateFormat;

	List<String> data;

	CustomGridView gvMain;
	ArrayAdapter<String> adapter;

	private void adjustGridView() {
		gvMain.setNumColumns(5);
		gvMain.setVerticalSpacing(2);
		gvMain.setHorizontalSpacing(2);
		
	}		  

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab41);

		dbHelper = new DBHelper(this);
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    Cursor c = db.query(getBaseContext().getString(R.string.tableNameMeasures), null, null, null, null, null, null);
	    
	    data = new ArrayList<String>();
	    data.add(getBaseContext().getString(R.string.tab41date));
	    data.add(getBaseContext().getString(R.string.tab41kCold));
	    data.add(getBaseContext().getString(R.string.tab41kHot));
	    data.add(getBaseContext().getString(R.string.tab41bCold));
	    data.add(getBaseContext().getString(R.string.tab41bHot));
	    
	    while (c.moveToNext()) {
	    	data.add(c.getString(c.getColumnIndex("date")));	    	
	    	data.add(c.getString(c.getColumnIndex("kcold")));
	    	data.add(c.getString(c.getColumnIndex("khot")));
	    	data.add(c.getString(c.getColumnIndex("bcold")));
	    	data.add(c.getString(c.getColumnIndex("bhot")));
	    }
		
		adapter = new ArrayAdapter<String>(this, R.layout.item41, R.id.tvText, data);
		gvMain = (CustomGridView) findViewById(R.id.gvMain);
		gvMain.setAdapter(adapter);
		adjustGridView();				
	}

}
