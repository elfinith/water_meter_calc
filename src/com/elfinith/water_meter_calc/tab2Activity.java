package com.elfinith.water_meter_calc;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class tab2Activity extends Activity implements OnClickListener {

	DBHelper dbHelper;
	
	String strDateFormat, strDatabaseName, strMeasuresTableName, strCreateMeasuresTableSQL;	
			
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2);
		
		strDateFormat = getBaseContext().getString(R.string.dateFormat);
		strDatabaseName = getBaseContext().getString(R.string.databaseName);
		strMeasuresTableName = getBaseContext().getString(R.string.tableNameMeasures);
		strCreateMeasuresTableSQL = getBaseContext().getString(R.string.sqlCreateTableMeasures);						
	}

	// общий обработчик нажатия кнопок
	@Override
	public void onClick(View v) {
		//
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
