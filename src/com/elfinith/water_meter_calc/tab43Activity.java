package com.elfinith.water_meter_calc;

import java.text.SimpleDateFormat;
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
import android.widget.Toast;

public class tab43Activity extends Activity {

	DBHelper dbHelper;

	String strDateFormat, strValue, strDate, strOldDate, strPrice;

	List<String> data;

	float fValue, fPrice;
	
	int iForecast;	
	
	CustomGridView gvGMain;
	ArrayAdapter<String> adapter;

	private void adjustGridView() {
		gvGMain.setNumColumns(4);
		gvGMain.setVerticalSpacing(2);
		gvGMain.setHorizontalSpacing(2);		
	}		  
			
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab43);
		
		strDateFormat = getBaseContext().getString(R.string.dateFormat);		
		
		dbHelper = new DBHelper(this);
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    Cursor c = db.query(getBaseContext().getString(R.string.tableNameGas), null, null, null, null, null, null);
	    
	    data = new ArrayList<String>();
	    data.add(getBaseContext().getString(R.string.tab43date));
	    data.add(getBaseContext().getString(R.string.tab43Value));
	    data.add(getBaseContext().getString(R.string.tab43Price));
	    data.add(getBaseContext().getString(R.string.tab4Forecast));
	    fValue = 0;
	    fPrice = 0;
	    iForecast = 0;	    	    
	    
	    while (c.moveToNext()) {
	    	strDate = c.getString(c.getColumnIndex("date"));
	    	strValue = c.getString(c.getColumnIndex("value"));
	    	strPrice = c.getString(c.getColumnIndex("price")); 
	    	data.add(strDate);	    	
	    	data.add(strValue);
	    	data.add(strPrice);
	    	if (!((fValue == 0) && (fPrice == 0))) {
				SimpleDateFormat format = new SimpleDateFormat(strDateFormat);	    		
				try {
					long difference = format.parse(strDate).getTime() - format.parse(strOldDate).getTime();
					int days = (int) difference / (24 * 60 * 60 * 1000);
					if (days > 0 ) {						
						iForecast = (Math.round(30 * Math.round((Float.parseFloat(strValue) - fValue) * Float.parseFloat(strPrice))) / days);						
					}
					else {
						iForecast = 0;						
					}																					
				} catch (Exception e) {
					Toast.makeText(this, R.string.date_parsing_error, Toast.LENGTH_SHORT).show();
					iForecast = 0;
				}												
	    	}	    	
	    	data.add(Integer.toString(iForecast));
	    	// сохранение предыдущих
		    fValue = Float.parseFloat(strValue);
		    fPrice = Float.parseFloat(strPrice);
		    strOldDate = strDate;	    	
	    }
		
		adapter = new ArrayAdapter<String>(this, R.layout.item41, R.id.tvText, data);
		gvGMain = (CustomGridView) findViewById(R.id.gvGMain);
		gvGMain.setAdapter(adapter);
		adjustGridView();				

	}


}

