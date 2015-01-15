package com.elfinith.water_meter_calc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	EditText 
		editKOCold, editKOHot, editKNCold, editKNHot,
		editBOCold, editBOHot, 	editBNCold, editBNHot,
		editPriceCold, editPriceHot, editResultCold, editResultHot;

	Button btnCalc;
	
	float fKOCold, fKOHot, 	fKNCold, fKNHot,
		fBOCold, fBOHot, fBNCold, fBNHot,
		fPCold, fPHot;
	
	int iResCold, iResHot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
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
		
	    OnClickListener oclBtnCalc = new OnClickListener() {
	        @Override
	        public void onClick(View v) {
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
	    		
	    		iResCold = Math.round(((fKNCold - fKOCold) * fPCold) + ((fBNCold - fBOCold) * fPCold));
	    		iResHot = Math.round(((fKNHot - fKOHot) * fPHot) + ((fBNHot - fBOHot) * fPHot));
	    		
	    		editResultCold.setText(Integer.toString(iResCold));
	    		editResultHot.setText(Integer.toString(iResHot));	    		
	        }
	    };		
	      
	    btnCalc.setOnClickListener(oclBtnCalc);	      
		
	}
}
