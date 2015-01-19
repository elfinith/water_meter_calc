package com.elfinith.water_meter_calc;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	SharedPreferences sData;

	final String KITCHEN_COLD = "kitchen_cold";
	final String KITCHEN_HOT = "kitchen_hot";
	final String BATHROOM_COLD = "bathroom_cold";
	final String BATHROOM_HOT = "bathroom_hot";	
	final String PRICE_COLD = "price_cold";	
	final String PRICE_HOT = "price_hot";	
	final String DATE = "date";
	final String strDateFormat = "yyyy.MM.dd";

	EditText 
	editKOCold, editKOHot, editKNCold, editKNHot,
	editBOCold, editBOHot, 	editBNCold, editBNHot,
	editPriceCold, editPriceHot, editResultCold, editResultHot;

	TextView textOvlValue, textOldDate, textFCValue;

	Button btnCalc, btnClearData, btnSaveData;

	float fKOCold, fKOHot, 	fKNCold, fKNHot,
	fBOCold, fBOHot, fBNCold, fBNHot,
	fPCold, fPHot;

	int iResCold, iResHot;

	// �������� ������ �� �����
	public boolean checkString(String string) {
		try {
			Float.parseFloat(string);
		} catch (Exception e) {
			return false;
		}
		return string != "";
	}	

	// �������� ������������ ����� ������
	public boolean checkNewInput() {
		return checkString(editKNCold.getText().toString()) && checkString(editKNHot.getText().toString())
				&& checkString(editBNCold.getText().toString()) && checkString(editBNHot.getText().toString()); 
	}

	// �������� ������������ ���� ������
	public boolean checkAllInput() {
		return checkNewInput() && checkString(editKOCold.getText().toString()) && checkString(editKOHot.getText().toString())
				&& checkString(editBOCold.getText().toString()) && checkString(editBOHot.getText().toString()) 
				&& checkString(editPriceCold.getText().toString()) && checkString(editPriceHot.getText().toString());
	}

	// ���������� ������� ��������� � ����
	public void saveData() {
		sData = getPreferences(MODE_PRIVATE);
		Editor ed = sData.edit();
		ed.putString(KITCHEN_COLD, editKNCold.getText().toString());
		ed.putString(KITCHEN_HOT, editKNHot.getText().toString());
		ed.putString(BATHROOM_COLD, editBNCold.getText().toString());
		ed.putString(BATHROOM_HOT, editBNHot.getText().toString());
		ed.putString(PRICE_COLD, editPriceCold.getText().toString());
		ed.putString(PRICE_HOT, editPriceHot.getText().toString());
		ed.putString(DATE, DateFormat.format(strDateFormat,new Date()).toString());		    	
		ed.commit();		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// ��������� �� �����
		editKOCold = (EditText) findViewById(R.id.editKitchenOldCold);
		editKOHot = (EditText) findViewById(R.id.editKitchenOldHot);		
		editKNCold = (EditText) findViewById(R.id.editKitchenNewCold);
		editKNHot = (EditText) findViewById(R.id.editKitchenNewHot);		
		editBOCold = (EditText) findViewById(R.id.editBathroomOldCold);
		editBOHot = (EditText) findViewById(R.id.editBathroomOldHot);		
		editBNCold = (EditText) findViewById(R.id.editBathroomNewCold);
		editBNHot = (EditText) findViewById(R.id.editBathroomNewHot);
		editPriceCold = (EditText) findViewById(R.id.editPriceCold);		
		editPriceHot = (EditText) findViewById(R.id.editPriceHot);		
		btnCalc = (Button) findViewById(R.id.buttonCalc);
		editResultCold = (EditText) findViewById(R.id.editResultCold);		
		editResultHot = (EditText) findViewById(R.id.editResultHot);
		btnClearData = (Button) findViewById(R.id.buttonClearData); 
		btnSaveData = (Button) findViewById(R.id.buttonSaveData);
		textOvlValue = (TextView) findViewById(R.id.textOverallValue);
		textOldDate = (TextView) findViewById(R.id.textOldDate);
		textFCValue = (TextView) findViewById(R.id.textForecastValue);		
		// ���������� ����������
		btnCalc.setOnClickListener(this);
		btnClearData.setOnClickListener(this);		
		btnSaveData.setOnClickListener(this);
		// ��������� ����
		sData = getPreferences(MODE_PRIVATE);
		editKOCold.setText(sData.getString(KITCHEN_COLD, ""));
		editKOHot.setText(sData.getString(KITCHEN_HOT, ""));
		editBOCold.setText(sData.getString(BATHROOM_COLD, ""));
		editBOHot.setText(sData.getString(BATHROOM_HOT, ""));
		editPriceCold.setText(sData.getString(PRICE_COLD, ""));
		editPriceHot.setText(sData.getString(PRICE_HOT, ""));
		textOldDate.setText(sData.getString(DATE, ""));
	}

	// ����� ���������� ������� ������
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonCalc:
			// �������� ������ � �����
			// ������
			if (checkAllInput()) {
				fKOCold = Float.parseFloat(editKOCold.getText().toString());
				fKOHot = Float.parseFloat(editKOHot.getText().toString());
				fKNCold = Float.parseFloat(editKNCold.getText().toString());
				fKNHot = Float.parseFloat(editKNHot.getText().toString());
				fBOCold = Float.parseFloat(editBOCold.getText().toString());
				fBOHot = Float.parseFloat(editBOHot.getText().toString());
				fBNCold = Float.parseFloat(editBNCold.getText().toString());
				fBNHot = Float.parseFloat(editBNHot.getText().toString());
				fPCold = Float.parseFloat(editPriceCold.getText().toString());
				fPHot = Float.parseFloat(editPriceHot.getText().toString());
				// ���������� ���������
				iResCold = Math.round(((fKNCold - fKOCold) * fPCold) + ((fBNCold - fBOCold) * fPCold));
				iResHot = Math.round(((fKNHot - fKOHot) * fPHot) + ((fBNHot - fBOHot) * fPHot));
				// ���������� ��������
				if (textOldDate.getText().toString() != "") {
					SimpleDateFormat format = new SimpleDateFormat(strDateFormat);				
					Date dateOld = null;
					Date dateNew = null;
					try {
						dateOld = format.parse(textOldDate.getText().toString());
						dateNew = new Date();
					} catch (Exception e) {
						Toast.makeText(this, R.string.date_parsing_error, Toast.LENGTH_SHORT).show();
					}				
					long difference = dateNew.getTime() - dateOld.getTime();
					if (difference > 0 ) {
						int days = (int) difference / (24 * 60 * 60 * 1000);
						textFCValue.setText(Integer.toString((Math.round(30 * (iResCold + iResHot)) / days)));

					} else {
						Toast.makeText(this, R.string.div_by_0, Toast.LENGTH_SHORT).show();						
					}																		
				} else {
					textFCValue.setText(R.string.div_by_0);					
				}					
				// ����� ������ �� �����
				editResultCold.setText(Integer.toString(iResCold));
				editResultHot.setText(Integer.toString(iResHot));
				textOvlValue.setText(Integer.toString(iResCold + iResHot));    			
			} else {
				Toast.makeText(this, R.string.invalid_data, Toast.LENGTH_SHORT).show();    			
			}
			break;
		case R.id.buttonClearData:
			// ���������			
			new AlertDialog.Builder(this)
			.setTitle(R.string.warning)
			.setMessage(R.string.clear_confirmation)
			.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					editKOCold.setText("0");
					editKOHot.setText("0");
					editBOCold.setText("0");
					editBOHot.setText("0");
					textOldDate.setText("");
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
		case R.id.buttonSaveData:
			// ���������� ���������
			if (checkNewInput()) {
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
		if (checkNewInput()) {
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
