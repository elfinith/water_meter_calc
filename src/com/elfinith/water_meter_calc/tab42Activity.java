package com.elfinith.water_meter_calc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

public class tab42Activity extends Activity {

	DBHelper dbHelper;

	String strDateFormat;

	List<String> data;

	CustomGridView gvEMain;
	ArrayAdapter<String> adapter;

	private void adjustGridView() {
		gvEMain.setNumColumns(3);
		gvEMain.setVerticalSpacing(2);
		gvEMain.setHorizontalSpacing(2);
		
	}		  
			
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab42);
		
		dbHelper = new DBHelper(this);
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    Cursor c = db.query(getBaseContext().getString(R.string.tableNameElectricity), null, null, null, null, null, null);
	    
	    data = new ArrayList<String>();
	    data.add(getBaseContext().getString(R.string.tab42date));
	    data.add(getBaseContext().getString(R.string.tab42Value));
	    data.add(getBaseContext().getString(R.string.tab42Price));
	    
	    while (c.moveToNext()) {
	    	data.add(c.getString(c.getColumnIndex("date")));	    	
	    	data.add(c.getString(c.getColumnIndex("value")));
	    	data.add(c.getString(c.getColumnIndex("price")));
	    }
		
		adapter = new ArrayAdapter<String>(this, R.layout.item41, R.id.tvText, data);
		gvEMain = (CustomGridView) findViewById(R.id.gvEMain);
		gvEMain.setAdapter(adapter);
		adjustGridView();				

	}


}
