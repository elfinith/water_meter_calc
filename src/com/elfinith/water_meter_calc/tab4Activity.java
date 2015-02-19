package com.elfinith.water_meter_calc;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

@SuppressWarnings("deprecation")
public class tab4Activity extends TabActivity implements OnClickListener {

	DBHelper dbHelper;
	
	String strDateFormat, strDatabaseName, strMeasuresTableName, strCreateMeasuresTableSQL;	
			
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab4);
		
		// создаём вкладки
		TabHost tabHost = getTabHost();
		// инициализация
		TabHost.TabSpec tabSpec;

		tabSpec = tabHost.newTabSpec("tag41");      
		tabSpec.setIndicator(getBaseContext().getString(R.string.text_tab1));
		tabSpec.setContent(new Intent(this, tab41Activity.class));
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec("tag42");
		tabSpec.setIndicator(getBaseContext().getString(R.string.text_tab2));
		tabSpec.setContent(new Intent(this, tab42Activity.class));        
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec("tag43");
		tabSpec.setIndicator(getBaseContext().getString(R.string.text_tab3));
		tabSpec.setContent(new Intent(this, tab43Activity.class));        
		tabHost.addTab(tabSpec);
		
		// первая вкладка будет выбрана по умолчанию
		tabHost.setCurrentTabByTag("tag41");

		// обработчик переключения вкладок
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String tabId) {
				Toast.makeText(getBaseContext(), "tabId = " + tabId, Toast.LENGTH_SHORT).show();
			}
		});        	
		
		
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
