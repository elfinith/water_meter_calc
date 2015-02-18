package com.elfinith.water_meter_calc;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TabActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// создаём вкладки
        TabHost tabHost = getTabHost();
        // инициализация
        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("вода");
        tabSpec.setContent(new Intent(this, tab1Activity.class));
        tabHost.addTab(tabSpec);
        
        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("свет");
        tabSpec.setContent(R.id.tab2);        
        tabHost.addTab(tabSpec);
        
        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setIndicator("газ");
        tabSpec.setContent(R.id.tab3);        
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag4");
        tabSpec.setIndicator("история");
        tabSpec.setContent(R.id.tab4);        
        tabHost.addTab(tabSpec);
        
        
        // первая вкладка будет выбрана по умолчанию
        tabHost.setCurrentTabByTag("tag1");
        
        // обработчик переключения вкладок
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
        	public void onTabChanged(String tabId) {
        		Toast.makeText(getBaseContext(), "tabId = " + tabId, Toast.LENGTH_SHORT).show();
        	}
        });        	
		
	}


	

}
