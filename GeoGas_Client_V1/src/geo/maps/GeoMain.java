package geo.maps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GeoMain extends Activity implements LocationListener {
	private int SLEEP_TIME = 1000;
	private int TIME_OUT = 6000;
	private boolean get_location = false;
	private String pi_string;
	private TextView tv;
	private Location loc;
	private ProgressDialog pd;
	private LocationManager lm;
	private String result;
	private CountDownTimer my_count;
	private boolean checked_setting = false;
	private boolean control_out = false;
	private Button my_button;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);

		my_button = (Button) findViewById(R.id.button);
		my_button.setOnClickListener(new ButtonClickHandler());
	}

	public class ButtonClickHandler implements View.OnClickListener {
		public void onClick(View view) {
			startVis();
		}
	}

	public void startVis() {
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		my_count = new CountDownTimer(10000, 10000) {

			public void onTick(long millisUntilFinished) {

			}

			public synchronized void onFinish() {
				finishGps();
				getCellId();
			}
		};
		my_count.start();
		start_chamada();
	}

	protected void getCellId() {
		try {
			TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();

			int cellID = location.getCid();
			int lac = location.getLac();

			// showCellId(cellID, lac);
		} catch (Exception e) {
			/*
			 * Toast.makeText( this,
			 * "Impossivel resguardar sua posi��o, tente novamente outra hora.",
			 * Toast.LENGTH_LONG).show();
			 */}

	}

	protected synchronized void finishGps() {
		lm.removeUpdates(this);
		pd.dismiss();
		Toast.makeText(getApplicationContext(),
				"N�o � possivel conseguir sua localiza��o, tente mais tarde.",
				Toast.LENGTH_LONG).show();
		chamada_mapa(-45, -6);

	}

	@Override
	protected void onResume() {
		/*
		 * onResume is is always called after onStart, even if the app hasn't
		 * been paused
		 * 
		 * add location listener and request updates every 1000ms or 10m
		 */
		if (control_out) {
			start_chamada();
		}
		control_out = true;
		super.onResume();
	}

	private void start_chamada() {
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10f, this);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000, 10f,
				this);
		pd = ProgressDialog.show(this, "Working..", "Getting Your Location",
				true, false);
	}

	@Override
	protected void onPause() {
		/* GPS, as it turns out, consumes battery like crazy */
		control_out = false;
		lm.removeUpdates(this);
		pd.dismiss();
		super.onResume();
	}

	public synchronized void onLocationChanged(Location location) {
		my_count.cancel();
		loc = location;
		showlocation(loc);
		pd.dismiss();
		lm.removeUpdates(this);
	}

	private void showlocation(Location loc2) {
		double lat = loc2.getLatitude();
		double log = loc2.getLongitude();
		/*
		 * String text = "Your current position : Latitude : "+lat +
		 * " Longitude :" + log; final AlertDialog.Builder alert2 = new
		 * AlertDialog.Builder(this); alert2.setTitle("PhotoGeo - Position");
		 * alert2.setMessage(text); alert2.setPositiveButton("Ok", new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int whichButton) { dialog.cancel(); }
		 * }); alert2.show();
		 */
		/*
		 * try{ Intent i = new Intent(GeoMain.this, GeoMaps.class);
		 * i.putExtra("latitude", lat); i.putExtra("longitude", log);
		 * GeoMain.this.startActivity(i); }catch(Exception e){
		 * Toast.makeText(getApplicationContext(), e.toString(),
		 * Toast.LENGTH_LONG).show(); }
		 */
		chamada_mapa(lat, log);
	}

	public void chamada_mapa(double lat, double log) {

		try {
			Intent i = new Intent(GeoMain.this, GeoMaps.class);
			i.putExtra("latitude", lat);
			i.putExtra("longitude", log);
			GeoMain.this.startActivity(i);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.toString(),
					Toast.LENGTH_LONG).show();
		}

		/*
		 * final Intent myIntent = new Intent(
		 * android.content.Intent.ACTION_VIEW, Uri.parse("geo:" +
		 * String.valueOf(lat) + "," + String.valueOf(log) +
		 * "?q=http://buchada.dsc.ufcg.edu.br/geogas/kml?state=PB&city=JOAO PESSOA"
		 * )); startActivity(myIntent);
		 */
	}

	private void showCellId(int cell, int lac) {
		String text = "Your current position : CellId : " + cell + " LAC :"
				+ lac;
		final AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
		alert2.setTitle("PhotoGeo - Position");
		alert2.setMessage(text);
		alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});
		alert2.show();
	}

	public void onProviderDisabled(String provider) {
		/* this is called if/when the GPS is disabled in settings */

		/* bring up the GPS settings */
		if (!checked_setting) {
			my_count.cancel();
			Intent intent = new Intent(
					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
			checked_setting = true;
			my_count.start();
		}
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		switch (status) {
		case LocationProvider.OUT_OF_SERVICE:
			checked_setting = false;
			Toast.makeText(this, "Status Changed: Out of Service",
					Toast.LENGTH_SHORT).show();
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			checked_setting = false;
			Toast.makeText(this, "Status Changed: Temporarily Unavailable",
					Toast.LENGTH_SHORT).show();
			break;
		case LocationProvider.AVAILABLE:
			checked_setting = false;
			Toast.makeText(this, "Status Changed: Available",
					Toast.LENGTH_SHORT).show();
			break;
		}

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	/*	*//** Called when the activity is first created. */
	/*
	 * @Override public void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState); setContentView(R.layout.main); try{
	 * final Intent myIntent = new Intent( android.content.Intent.ACTION_VIEW,
	 * Uri.parse(
	 * "geo:-7.06,-34.51?q=http://maps.google.com/maps?f=d&hl=en&saddr=25.04202,121.534761&daddr=25.05202,121.554761&ie=UTF8&0&om=0&output=kml"
	 * )); startActivity(myIntent); }catch(Exception e){
	 * Toast.makeText(this.getBaseContext(), e.getMessage(), 10000); } }
	 */
}