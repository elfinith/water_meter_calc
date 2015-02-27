package com.elfinith.water_meter_calc;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class tab2Activity extends Activity implements OnClickListener {

	SharedPreferences sData;	
	DBHelper dbHelper;

	final String ELECTRICITY_OLD = "electricity_old";	
	final String PRICE_ELECTRICITY = "price_electricity";	
	final String DATE_ELECTRICITY = "date_electricity";	

	String strDateFormat, strOldDate;

	float fOld, fNew, fPrice;

	int iRes;

	EditText editEOld, editENew, editEPrice;

	Button btnCalc, btnClearData, btnSaveData;

	TextView textOvlValue, textFCValue, textEOld; 

	// проверка строки на число
	public boolean checkString(String string) {
		try {
			Float.parseFloat(string);
		} catch (Exception e) {
			return false;
		}
		return string != "";
	}		

	// загрузка данных на форму
	public void loadData() {		
		// ПОШЛА ПЛЯСКА С БД
		dbHelper = new DBHelper(this);
		// подключаемся к БД
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor c = db.query(getBaseContext().getString(R.string.tableNameElectricity), null, null, null, null, null, null);
		strOldDate = "";
		if (c.moveToLast()) {
			// заполняем форму
			editEOld.setText(c.getString(c.getColumnIndex("value")));
			editEPrice.setText(c.getString(c.getColumnIndex("price")));
			strOldDate = c.getString(c.getColumnIndex("date"));
		} else {
			Toast.makeText(this, R.string.emptyDB, Toast.LENGTH_SHORT).show();
			sData = getPreferences(MODE_PRIVATE);
			editEOld.setText(sData.getString(ELECTRICITY_OLD, ""));
			editEPrice.setText(sData.getString(PRICE_ELECTRICITY, ""));
			strOldDate = sData.getString(DATE_ELECTRICITY, "");			
		}
		textEOld.setText(getBaseContext().getString(R.string.textEOld) 
				+ " [" + strOldDate + "]");				        	    			
		// закрываем подключение к БД
		dbHelper.close();		
	}

	// сохранение текущих показаний и цены
	public void saveData() {
		sData = getPreferences(MODE_PRIVATE);
		Editor ed = sData.edit();
		ed.putString(ELECTRICITY_OLD, editENew.getText().toString());
		ed.putString(PRICE_ELECTRICITY, editEPrice.getText().toString());
		ed.putString(DATE_ELECTRICITY, DateFormat.format(strDateFormat,new Date()).toString());		    	
		ed.commit();

		// ПОШЛА ПЛЯСКА С БД
		dbHelper = new DBHelper(this);		
		ContentValues cv = new ContentValues();		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		cv.put("date", DateFormat.format(strDateFormat,new Date()).toString());
		cv.put("value", editENew.getText().toString());
		cv.put("price", editEPrice.getText().toString());	    
		// вставляем запись и получаем ее ID
		long rowID = db.insert(getBaseContext().getString(R.string.tableNameElectricity), null, cv);
		Toast.makeText(this, "row inserted, ID = " + rowID, Toast.LENGTH_SHORT).show();
		// закрываем подключение к БД
		dbHelper.close();		
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2);

		strDateFormat = getBaseContext().getString(R.string.dateFormat);
		// цепляемся за вьюхи
		editEOld = (EditText) findViewById(R.id.editEOld);
		editENew = (EditText) findViewById(R.id.editENew);		
		editEPrice = (EditText) findViewById(R.id.editEPrice);		
		btnCalc = (Button) findViewById(R.id.buttonECalc);
		btnClearData = (Button) findViewById(R.id.buttonEClearData); 
		btnSaveData = (Button) findViewById(R.id.buttonESaveData);
		textOvlValue = (TextView) findViewById(R.id.textEOverallValue);
		textFCValue = (TextView) findViewById(R.id.textEForecastValue);
		textEOld = (TextView) findViewById(R.id.textEOld);
		// навешиваем обработчик
		btnCalc.setOnClickListener(this);
		btnClearData.setOnClickListener(this);		
		btnSaveData.setOnClickListener(this);
		// заполняем поля
		loadData();			
	}

	// общий обработчик нажатия кнопок
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonECalc:
			if (checkString(editEOld.getText().toString()) && checkString(editENew.getText().toString())
					&& checkString(editEPrice.getText().toString())) 
			{
				fOld = Float.parseFloat(editEOld.getText().toString());
				fNew = Float.parseFloat(editENew.getText().toString());			
				fPrice = Float.parseFloat(editEPrice.getText().toString());
				// вычисление стоимости
				iRes = Math.round((fNew - fOld) * fPrice);
				// вычисление прогноза
				if (strOldDate != "") {
					SimpleDateFormat format = new SimpleDateFormat(strDateFormat);				
					Date dateOld = null;
					Date dateNew = null;
					try {
						dateOld = format.parse(strOldDate);
						dateNew = new Date();
					} catch (Exception e) {
						Toast.makeText(this, R.string.date_parsing_error, Toast.LENGTH_SHORT).show();
					}				
					long difference = dateNew.getTime() - dateOld.getTime();
					int days = (int) difference / (24 * 60 * 60 * 1000);
					if (days > 0 ) {						
						textFCValue.setText(Integer.toString((Math.round(30 * iRes) / days)));
					} else {
						Toast.makeText(this, R.string.div_by_0, Toast.LENGTH_SHORT).show();						
					}
				} else {
					textFCValue.setText(R.string.div_by_0);					
				}	
				// вывод данных на форму
				textOvlValue.setText(Integer.toString(iRes));
			} else {
				Toast.makeText(this, R.string.invalid_data, Toast.LENGTH_SHORT).show();    			
			}
			break;
		case R.id.buttonEClearData:
			// обнуление						
			new AlertDialog.Builder(this)
			.setTitle(R.string.warning)
			.setMessage(R.string.clear_confirmation)
			.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					editEOld.setText("0");
					editENew.setText("0");
					strOldDate = "";
					textEOld.setText(getBaseContext().getString(R.string.textEOld) 
							+ " [" + strOldDate + "]");				        	    								
				}
			})
			.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					// do nothing
				}
			})
			.setIcon(android.R.drawable.ic_dialog_alert)
			.show();
			
			break;
		case R.id.buttonESaveData:
			// сохранение показаний
			if (checkString(editEOld.getText().toString()) && checkString(editENew.getText().toString())
					&& checkString(editEPrice.getText().toString())) {
				saveData();
				Toast.makeText(this, R.string.data_saved, Toast.LENGTH_SHORT).show();	    		
			} else {
				Toast.makeText(this, R.string.invalid_data, Toast.LENGTH_SHORT).show();	    		
			}			
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if (checkString(editEOld.getText().toString()) && checkString(editENew.getText().toString())
				&& checkString(editEPrice.getText().toString())) {
			new AlertDialog.Builder(this)
			.setTitle(R.string.warning)
			.setMessage(R.string.save_data)
			.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					saveData();
					finish();					
				}
			})
			.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();					
				}
			})
			.setIcon(android.R.drawable.ic_dialog_alert)
			.show();				
		} else {
			finish();
		}
	}		
	


}
