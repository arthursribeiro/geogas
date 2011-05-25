package geo.maps;

import geo.data.Place;
import geo.xmlhandler.XMLHandler;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.google.android.maps.Overlay;

public class GeoMaps extends MapActivity {
	private MapView mapView;
	private MapController mc;
	private GeoPoint p;
	private ArrayList<GeoPoint> postos;
	private XMLHandler xml;
	private List<Overlay> listOfOverlays;
	private final int AJUSTE_USER = 16;
	private final int AJUSTE_POSTO = 16;


	class MapOverlayPosto extends com.google.android.maps.Overlay {
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			super.draw(canvas, mapView, shadow);

			// ---translate the GeoPoint to screen pixels---
			Point screenPts = new Point();
			mapView.getProjection().toPixels(p, screenPts);

			// ---add the marker---
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.user);
			canvas.drawBitmap(bmp, screenPts.x, screenPts.y - 16, null);
			for(GeoPoint x :postos){
				Point screenPts1 = new Point();
				mapView.getProjection().toPixels(x, screenPts1);

				// ---add the marker---
				Bitmap bmp1 = BitmapFactory.decodeResource(getResources(),
						R.drawable.iconpostovalido);
				canvas.drawBitmap(bmp1, screenPts1.x, screenPts1.y - 16, null);
			}
			return true;
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maplayout);
		mapView = (MapView) findViewById(R.id.mapView);
		LinearLayout zoomLayout = (LinearLayout) findViewById(R.id.zoom);
		View zoomView = mapView.getZoomControls();

		zoomLayout.addView(zoomView, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mapView.displayZoomControls(true);
		mc = mapView.getController();
		double lat = getIntent().getExtras().getDouble("latitude");
		double lng = getIntent().getExtras().getDouble("longitude");

		p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

		mc.animateTo(p);
		mc.setZoom(14);
		
		plotaPostos();
		MapOverlayPosto us = new MapOverlayPosto();
		listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();
		listOfOverlays.add(us);
		mapView.invalidate();
	}

	public void plotaPostos() {
		ArrayList<Place> all_places = getPostos();
		postos = new ArrayList<GeoPoint>();
		for (Place p : all_places) {
			Log.i("Latitude","("+String.valueOf(p.getLat())+","+String.valueOf(p.getLonge())+")");
//			Log.i("Place", "Latitude :" + String.valueOf(p.getLat())+ "  Longitude :"+String.valueOf(p.getLonge()));
			GeoPoint point = new GeoPoint((int) (p.getLat() * 1E6),
					(int) (p.getLonge() * 1E6));
//			listOfOverlays.add(new MapOverlayPosto(point));
			postos.add(point);
		}
	}

	public ArrayList<Place> getPostos() {
		ArrayList<Place> all_places = new ArrayList<Place>();;
		try {
//			double x = Double.parseDouble("6");
			xml = new XMLHandler();
			String teste = "<placemark><location latitude=\"-6,4320\" longitude=\"-37,104100\" valido=\"true\"></location><location latitude=\"-6,4370\" longitude=\"-37,103910\" valido=\"false\"></location></placemark>";
			xml.loadXMLfromString(teste);
			// xml.loadXMLfromURL("String url");
			all_places = xml.getElementByPlace();
		} catch (Exception e) {
			Log.i("Error", e.toString());
		}
		return all_places;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		MapController mc = mapView.getController();
		switch (keyCode) {
		case KeyEvent.KEYCODE_3:
			break;
		case KeyEvent.KEYCODE_1:
			mc.zoomOut();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
