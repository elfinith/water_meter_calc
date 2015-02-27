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

public class tab3Activity extends Activity implements OnClickListener {

	SharedPreferences sData;	
	DBHelper dbHelper;

	final String GAS_OLD = "gas_old";	
	final String PRICE_GAS = "price_gas";	
	final String DATE_GAS = "date_gas";		

	String strDateFormat, strOldDate;

	float fOld, fNew, fPrice;

	int iRes;

	EditText editGOld, editGNew, editGPrice;

	Button btnCalc, btnClearData, btnSaveData;

	TextView textOvlValue, textFCValue, textGOld;

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
		Cursor c = db.query(getBaseContext().getString(R.string.tableNameGas), null, null, null, null, null, null);
		strOldDate = "";
		if (c.moveToLast()) {
			// заполняем форму
			editGOld.setText(c.getString(c.getColumnIndex("value")));
			editGPrice.setText(c.getString(c.getColumnIndex("price")));
			strOldDate = c.getString(c.getColumnIndex("date"));
		} else {
			Toast.makeText(this, R.string.emptyDB, Toast.LENGTH_SHORT).show();
			sData = getPreferences(MODE_PRIVATE);
			editGOld.setText(sData.getString(GAS_OLD, ""));
			editGPrice.setText(sData.getString(PRICE_GAS, ""));
			strOldDate = sData.getString(DATE_GAS, "");			
		}
		textGOld.setText(getBaseContext().getString(R.string.textEOld) 
				+ " [" + strOldDate + "]");				        	    			
		// закрываем подключение к БД
		dbHelper.close();		
	}

	// сохранение текущих показаний и цены
	public void saveData() {
		sData = getPreferences(MODE_PRIVATE);
		Editor ed = sData.edit();
		ed.putString(GAS_OLD, editGNew.getText().toString());
		ed.putString(PRICE_GAS, editGPrice.getText().toString());
		ed.putString(DATE_GAS, DateFormat.format(strDateFormat,new Date()).toString());		    	
		ed.commit();

		// ПОШЛА ПЛЯСКА С БД
		dbHelper = new DBHelper(this);		
		ContentValues cv = new ContentValues();		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		cv.put("date", DateFormat.format(strDateFormat,new Date()).toString());
		cv.put("value", editGNew.getText().toString());
		cv.put("price", editGPrice.getText().toString());	    
		// вставляем запись и получаем ее ID
		long rowID = db.insert(getBaseContext().getString(R.string.tableNameGas), null, cv);
		Toast.makeText(this, "row inserted, ID = " + rowID, Toast.LENGTH_SHORT).show();
		// закрываем подключение к БД
		dbHelper.close();		
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab3);

		strDateFormat = getBaseContext().getString(R.string.dateFormat);
		// цепляемся за вьюхи
		editGOld = (EditText) findViewById(R.id.editGOld);
		editGNew = (EditText) findViewById(R.id.editGNew);		
		editGPrice = (EditText) findViewById(R.id.editGPrice);		
		btnCalc = (Button) findViewById(R.id.buttonGCalc);
		btnClearData = (Button) findViewById(R.id.buttonGClearData); 
		btnSaveData = (Button) findViewById(R.id.buttonGSaveData);
		textOvlValue = (TextView) findViewById(R.id.textGOverallValue);
		textFCValue = (TextView) findViewById(R.id.textGForecastValue);
		textGOld = (TextView) findViewById(R.id.textGOld);
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
		case R.id.buttonGCalc:
			if (checkString(editGOld.getText().toString()) && checkString(editGNew.getText().toString())
					&& checkString(editGPrice.getText().toString())) 
			{
				fOld = Float.parseFloat(editGOld.getText().toString());
				fNew = Float.parseFloat(editGNew.getText().toString());			
				fPrice = Float.parseFloat(editGPrice.getText().toString());
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
		case R.id.buttonGClearData:
			// обнуление						
			new AlertDialog.Builder(this)
			.setTitle(R.string.warning)
			.setMessage(R.string.clear_confirmation)
			.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					editGOld.setText("0");
					editGNew.setText("0");
					strOldDate = "";
					textGOld.setText(getBaseContext().getString(R.string.textEOld) 
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
		case R.id.buttonGSaveData:
			// сохранение показаний
			if (checkString(editGOld.getText().toString()) && checkString(editGNew.getText().toString())
					&& checkString(editGPrice.getText().toString())) {
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
		if (checkString(editGOld.getText().toString()) && checkString(editGNew.getText().toString())
				&& checkString(editGPrice.getText().toString())) {
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
