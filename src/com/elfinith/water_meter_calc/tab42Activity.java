package com.elfinith.water_meter_calc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class tab42Activity extends Activity {

	DBHelper dbHelper;

	String strDateFormat, strValue, strDate, strOldDate, strPrice, strFirstDate;

	List<String> data;

	float fValue, fPrice, fValue0;

	int iForecast, iAvgValue;

	CustomGridView gvEMain;
	ArrayAdapter<String> adapter;
	TextView tvAvgValue;	

	private void adjustGridView() {
		gvEMain.setNumColumns(4);
		gvEMain.setVerticalSpacing(2);
		gvEMain.setHorizontalSpacing(2);		
	}		  

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab42);

		strDateFormat = getBaseContext().getString(R.string.dateFormat);
		dbHelper = new DBHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor c = db.query(getBaseContext().getString(R.string.tableNameElectricity), null, null, null, null, null, null);
		SimpleDateFormat format = new SimpleDateFormat(strDateFormat);	    

		data = new ArrayList<String>();
		data.add(getBaseContext().getString(R.string.tab42date));
		data.add(getBaseContext().getString(R.string.tab42Value));
		data.add(getBaseContext().getString(R.string.tab42Price));
		data.add(getBaseContext().getString(R.string.tab4Forecast));
		fValue = 0;
		fPrice = 0;
		iForecast = 0;
		fValue0 = 0;
		strFirstDate = "";

		while (c.moveToNext()) {
			strDate = c.getString(c.getColumnIndex("date"));
			if (strFirstDate == "") strFirstDate = strDate;	    	
			strValue = c.getString(c.getColumnIndex("value"));
			strPrice = c.getString(c.getColumnIndex("price")); 
			data.add(strDate);	    	
			data.add(strValue);
			data.add(strPrice);
			if (fValue0 == 0) fValue0 = Float.parseFloat(strValue);	    	
			if (!((fValue == 0) && (fPrice == 0))) {	    		
				try {
					long difference = format.parse(strDate).getTime() - format.parse(strOldDate).getTime();
					int days = (int)(difference / (24 * 60 * 60 * 1000));
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
			// debug section begin
//			try {			
//				Toast.makeText(this, "strValue=" + strValue + "; strPrice=" + strPrice + "; strOldDate=" + strOldDate + " ;strFirstDate=" + strFirstDate 
//					+ "; olvdays=" + Long.toString((long)(format.parse(strDate).getTime() - format.parse(strFirstDate).getTime())), Toast.LENGTH_SHORT).show();						
//			} catch (Exception e) {
//				Toast.makeText(this, R.string.date_parsing_error, Toast.LENGTH_SHORT).show();
//			}
			// debug section end			
		}    
		try {
			long ovldays = (long)(format.parse(strDate).getTime() - format.parse(strFirstDate).getTime()) / (24 * 60 * 60 * 1000);	    
			iAvgValue = Math.round(30 * (fValue - fValue0) * Float.parseFloat(strPrice) / ovldays);		
		} catch (Exception e) {
			Toast.makeText(this, R.string.date_parsing_error, Toast.LENGTH_SHORT).show();
			iAvgValue = 0;
		}	    		
		adapter = new ElectricityAdapter(this, R.layout.item41, R.id.tvText, data, iAvgValue);		
		gvEMain = (CustomGridView) findViewById(R.id.gvEMain);
		gvEMain.setAdapter(adapter);
		adjustGridView();				
		tvAvgValue = (TextView) findViewById(R.id.tab42AvgValue);
		tvAvgValue.setText(Integer.toString(iAvgValue));
	}


}
