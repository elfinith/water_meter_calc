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

public class tab43Activity extends Activity {

	DBHelper dbHelper;

	String strDateFormat;

	List<String> data;

	CustomGridView gvGMain;
	ArrayAdapter<String> adapter;

	private void adjustGridView() {
		gvGMain.setNumColumns(3);
		gvGMain.setVerticalSpacing(2);
		gvGMain.setHorizontalSpacing(2);
		
	}		  
			
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab43);
		
		dbHelper = new DBHelper(this);
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    Cursor c = db.query(getBaseContext().getString(R.string.tableNameGas), null, null, null, null, null, null);
	    
	    data = new ArrayList<String>();
	    data.add(getBaseContext().getString(R.string.tab43date));
	    data.add(getBaseContext().getString(R.string.tab43Value));
	    data.add(getBaseContext().getString(R.string.tab43Price));
	    
	    while (c.moveToNext()) {
	    	data.add(c.getString(c.getColumnIndex("date")));	    	
	    	data.add(c.getString(c.getColumnIndex("value")));
	    	data.add(c.getString(c.getColumnIndex("price")));
	    }
		
		adapter = new ArrayAdapter<String>(this, R.layout.item41, R.id.tvText, data);
		gvGMain = (CustomGridView) findViewById(R.id.gvGMain);
		gvGMain.setAdapter(adapter);
		adjustGridView();				

	}


}

