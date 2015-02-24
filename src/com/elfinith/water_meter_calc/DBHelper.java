package com.elfinith.water_meter_calc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
	
	public static final int DB_VERSION = 2;
	String sqlCreateTableMeasures, sqlCreateTableElectricity, sqlCreateTableGas;
	
	public DBHelper(Context context) {
		super (context, context.getString(R.string.databaseName), null, DB_VERSION);
		sqlCreateTableMeasures = context.getString(R.string.sqlCreateTableMeasures);
		sqlCreateTableElectricity = context.getString(R.string.sqlCreateTableElectricity);		
		sqlCreateTableGas = context.getString(R.string.sqlCreateTableGas);		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlCreateTableMeasures);
		db.execSQL(sqlCreateTableElectricity);		
		db.execSQL(sqlCreateTableGas);		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if ((newVersion == 2) && (oldVersion == 1))
		{		
			db.beginTransaction();
			try {
				db.execSQL(sqlCreateTableElectricity);
		        db.setTransactionSuccessful();				
	        } finally {
	            db.endTransaction();
	          }				
		}		
	}
}
