package com.elfinith.water_meter_calc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class tab41Activity extends Activity {

	DBHelper dbHelper;

	String strDateFormat, strDate, strKCold, strKHot, strBCold, strBHot,
		strPCold, strPHot, strOldDate;

	List<String> data;

	CustomGridView gvMain;
	ArrayAdapter<String> adapter;

	float fKCold, fKHot,
	fBCold, fBHot;
	
	int iForecast;		

	private void adjustGridView() {
		gvMain.setNumColumns(6);
		gvMain.setVerticalSpacing(2);
		gvMain.setHorizontalSpacing(2);	
	}		  

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab41);

		strDateFormat = getBaseContext().getString(R.string.dateFormat);		
		
		dbHelper = new DBHelper(this);
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    Cursor c = db.query(getBaseContext().getString(R.string.tableNameMeasures), null, null, null, null, null, null);	    
	    
	    data = new ArrayList<String>();
	    data.add(getBaseContext().getString(R.string.tab41date));
	    data.add(getBaseContext().getString(R.string.tab41kCold));
	    data.add(getBaseContext().getString(R.string.tab41kHot));
	    data.add(getBaseContext().getString(R.string.tab41bCold));
	    data.add(getBaseContext().getString(R.string.tab41bHot));
	    data.add(getBaseContext().getString(R.string.tab4Forecast));	    
	    fKCold = 0;
	    fKHot = 0;
	    fBCold = 0;
	    fBHot = 0;
	    iForecast = 0;
	    while (c.moveToNext()) {
	    	strDate = c.getString(c.getColumnIndex("date"));
	    	strKCold = c.getString(c.getColumnIndex("kcold"));
	    	strKHot = c.getString(c.getColumnIndex("khot"));
	    	strBCold = c.getString(c.getColumnIndex("bcold"));
	    	strBHot = c.getString(c.getColumnIndex("bhot"));
	    	strPCold = c.getString(c.getColumnIndex("prcold"));
	    	strPHot = c.getString(c.getColumnIndex("prhot"));	    		    	
	    	data.add(strDate);	    	
	    	data.add(strKCold);
	    	data.add(strKHot);
	    	data.add(strBCold);
	    	data.add(strBHot);
	    	if (!((fKCold == 0) && (fKHot == 0) && (fBCold == 0) && (fBHot == 0))) {
				SimpleDateFormat format = new SimpleDateFormat(strDateFormat);
				try {
					long difference = format.parse(strDate).getTime() - format.parse(strOldDate).getTime();
					if (difference > 0 ) {
						int iResCold = Math.round(((Float.parseFloat(strKCold) - fKCold) * Float.parseFloat(strPCold)) 
								+ ((Float.parseFloat(strBCold) - fBCold) * Float.parseFloat(strPCold)));
						int iResHot = Math.round(((Float.parseFloat(strKHot) - fKHot) * Float.parseFloat(strPHot)) 
								+ ((Float.parseFloat(strBHot) - fBHot) * Float.parseFloat(strPHot)));						
						int days = (int) difference / (24 * 60 * 60 * 1000);						
						iForecast = (Math.round(30 * (iResCold + iResHot)) / days);
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
		    fKCold = Float.parseFloat(strKCold);
		    fKHot = Float.parseFloat(strKHot);
		    fBCold = Float.parseFloat(strBCold);
		    fBHot = Float.parseFloat(strBHot);
		    strOldDate = strDate;
	    }
	    
		adapter = new ArrayAdapter<String>(this, R.layout.item41, R.id.tvText, data);
		gvMain = (CustomGridView) findViewById(R.id.gvMain);
		gvMain.setAdapter(adapter);
		adjustGridView();				
	}

}
