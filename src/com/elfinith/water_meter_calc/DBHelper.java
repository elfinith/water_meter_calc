package com.elfinith.water_meter_calc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	public static final int DB_VERSION = 2;
	String sqlCreateTableMeasures, sqlCreateTableElectricity;
	
	public DBHelper(Context context) {
		super (context, context.getString(R.string.databaseName), null, DB_VERSION);
		sqlCreateTableMeasures = context.getString(R.string.sqlCreateTableMeasures);
		sqlCreateTableElectricity = context.getString(R.string.sqlCreateTableElectricity);		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlCreateTableMeasures);
		db.execSQL(sqlCreateTableElectricity);		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if ((newVersion == 1) && (newVersion == 2))
		{
			db.execSQL(sqlCreateTableElectricity);
		}		
	}
}
