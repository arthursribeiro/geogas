package org.project.geogas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class GeoGasRequest extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        View continueButton = findViewById(R.id.allPlacesActionButton);
        continueButton.setOnClickListener(this);
        View newButton = findViewById(R.id.gasStationActionButton);
        newButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.allPlacesActionButton:
			Intent i = new Intent(this, AllStations.class);
			startActivity(i);
			break;
	}
		
	}
}