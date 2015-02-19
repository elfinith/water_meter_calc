package com.elfinith.water_meter_calc;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

@SuppressWarnings("deprecation")
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
		tabSpec.setIndicator(getBaseContext().getString(R.string.text_tab1));
		tabSpec.setContent(new Intent(this, tab1Activity.class));
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec("tag2");
		tabSpec.setIndicator(getBaseContext().getString(R.string.text_tab2));
		tabSpec.setContent(new Intent(this, tab2Activity.class));        
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec("tag3");
		tabSpec.setIndicator(getBaseContext().getString(R.string.text_tab3));
		tabSpec.setContent(new Intent(this, tab3Activity.class));        
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec("tag4");
		tabSpec.setIndicator(getBaseContext().getString(R.string.text_tab4));
		tabSpec.setContent(new Intent(this, tab4Activity.class));        
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
