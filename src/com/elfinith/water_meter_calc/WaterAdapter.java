package com.elfinith.water_meter_calc;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class WaterAdapter extends ArrayAdapter<String> {
    protected int AvgValue;

    public static int 
		NUM_COLUMNS = 6,
		COLOR_RED = 0xffff6347,
		COLOR_GREEN = 0xff00ff7f,
		COLOR_SILVER = 0xffc0c0c0;    
    
    public WaterAdapter(Context context, int resource, int textViewResourceId, List<String> objects, int AvgVal) {   	
        super(context, resource, textViewResourceId, objects);
        AvgValue = AvgVal;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View view = super.getView(position, convertView, parent);
      if (((position + 1) % NUM_COLUMNS == 0) && (position > NUM_COLUMNS - 1)) {
    	  try {
        	  if (Float.parseFloat(this.getItem(position)) > AvgValue) {
        		  view.setBackgroundColor(COLOR_RED);
        	  }
        	  else {
        		  view.setBackgroundColor(COLOR_GREEN);    		  
        	  }  		      		  
    	  }
    	  catch (Exception e) {
    		  view.setBackgroundColor(0);    		   	
  		  }
      } else {
    	  if (position < NUM_COLUMNS) {
    		  view.setBackgroundColor(COLOR_SILVER);    		  
    	  }
      }    	  
      return view;
    }
}
