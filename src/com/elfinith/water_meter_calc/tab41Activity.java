package com.elfinith.water_meter_calc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class tab41Activity extends Activity {

	DBHelper dbHelper;

	String strDateFormat, strDatabaseName, strMeasuresTableName, strCreateMeasuresTableSQL;

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

		strDateFormat = getBaseContext().getString(R.string.dateFormat);
		strDatabaseName = getBaseContext().getString(R.string.databaseName);
		strMeasuresTableName = getBaseContext().getString(R.string.tableNameMeasures);
		strCreateMeasuresTableSQL = getBaseContext().getString(R.string.sqlCreateTableMeasures);

		dbHelper = new DBHelper(this);
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    Cursor c = db.query(strMeasuresTableName, null, null, null, null, null, null);
	    
	    data = new ArrayList<String>();
	    data.add("дата");
	    data.add("кх");
	    data.add("кг");
	    data.add("вх");
	    data.add("вг");
	    
	    if (c.moveToNext()) {
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

	class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super (context, strDatabaseName, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(strCreateMeasuresTableSQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}


}
