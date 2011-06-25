package geo.maps;

import geo.data.Place;
import geo.xmlhandler.GeoLocation;
import geo.xmlhandler.XMLHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GeoMaps extends MapActivity {
	private static final int radius = 100;
	private MapView mapView;
	private MapController mc;
	private GeoPoint p;
	private ArrayList<GeoPoint> postos;
	private XMLHandler xml;
	private boolean sucess;
	private List<Overlay> listOfOverlays;
	private ArrayList<Place> all_places;
	private final int MAX_POSTOS = 75;
	private final int AJUSTE_USER = 16;
	private final int AJUSTE_POSTO = 16;
	private boolean min;
	private boolean max;
	private boolean bandeira;
	private boolean distancia;
	private double preco_min = -1;
	private double preco_max = Double.MAX_VALUE;
	private String v_bandeira;
	private double v_distancia = 0;
	double lat;
	double lng;
	GeoPoint center;
	// GeoPoint inic;
	GeoLocation[] auxiliar;
	MyItemizedOverlay itemizedOverlay;
	MyItemizedOverlay itemizedOverlay2;
	MyItemizedOverlay itemizedOverlay3;
	MyItemizedOverlay itemizedOverlay4;
	MyItemizedOverlay itemizedOverlay5;
	MyItemizedOverlay itemizedOverlay6;
	MyItemizedOverlay itemizedOverlay7;
	MyItemizedOverlay itemizedOverlay8;
	MyItemizedOverlay itemizedOverlay9;

	class MapOverlayPosto extends com.google.android.maps.Overlay {
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			super.draw(canvas, mapView, shadow);

			// ---translate the GeoPoint to screen pixels---
			if (sucess) {
				Point screenPts = new Point();
				mapView.getProjection().toPixels(p, screenPts);

				// ---add the marker---
				Bitmap bmp = BitmapFactory.decodeResource(getResources(),
						R.drawable.user);
				canvas.drawBitmap(bmp, screenPts.x, screenPts.y - 16, null);
			}

			// for (int i = 0; i<Math.min(postos.size(), MAX_POSTOS);i++) {
			// GeoPoint x = postos.get(i);
			// Point screenPts1 = new Point();
			// mapView.getProjection().toPixels(x, screenPts1);
			//
			// // ---add the marker---
			// Bitmap bmp1 = BitmapFactory.decodeResource(getResources(),
			// R.drawable.iconpostovalido);
			// canvas.drawBitmap(bmp1, screenPts1.x, screenPts1.y - 16, null);
			// }
			return true;
		}
	}

	/**
	 * public class PostoOverLayItem extends
	 * com.google.android.maps.ItemizedOverlay { private ArrayList<OverlayItem>
	 * mOverlays = new ArrayList<OverlayItem>(); private Context mContext;
	 * 
	 * 
	 * public PostoOverLayItem(Drawable defaultMarker) {
	 * super(boundCenterBottom(defaultMarker)); }
	 * 
	 * public PostoOverLayItem(Drawable defaultMarker, Context context) {
	 * super(defaultMarker); mContext = context; } public void
	 * addOverlay(OverlayItem overlay) { mOverlays.add(overlay); populate(); }
	 * 
	 * @Override protected OverlayItem createItem(int i) { // TODO
	 *           Auto-generated method stub return mOverlays.get(i); }
	 * @Override public int size() { return mOverlays.size(); }
	 * @Override protected boolean onTap(int index) { OverlayItem item =
	 *           mOverlays.get(index); if(mContext == null) Log.d("OBJETO",
	 *           "eh nulo"); AlertDialog.Builder dialog = new
	 *           AlertDialog.Builder(mContext);
	 *           dialog.setTitle(item.getTitle());
	 *           dialog.setMessage(item.getSnippet()); dialog.show(); return
	 *           true; }
	 * 
	 *           }
	 **/

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
		lat = -7.000;
		lng = -38.000;
		int zoom = 13;

		sucess = getIntent().getExtras().getBoolean("sucess");
		min = getIntent().getExtras().getBoolean("min_sucess");
		max = getIntent().getExtras().getBoolean("max_sucess");
		bandeira = getIntent().getExtras().getBoolean(
				"bandeira_sucess");
		distancia = getIntent().getExtras().getBoolean("dis_sucess");
		
		try {
			preco_min = Double.valueOf(getIntent().getExtras().getString(
					"preco_min"));
		}catch(Exception e){
			
		} finally {
			if(min && preco_min != -1){
				min = true;
			}else{
				min = false;
			}
		}
		try{
			preco_max = Double.valueOf(getIntent().getExtras().getString(
			"preco_max"));
		}catch(Exception e){
			
		}finally{
			if(max && preco_max != Double.MAX_VALUE){
				max = true;
			}else{
				max = false;
			}
		}
		try{
			v_distancia = Double.valueOf(getIntent().getExtras().getString(
			"distancia"));
		}catch(Exception e){
			
		}finally{
			if(distancia && v_distancia != 0){
				distancia = true;
			}else{
				distancia = false;
			}
		}
		v_bandeira = getIntent().getExtras().getString("bandeira");
		if (sucess) {
			lat = getIntent().getExtras().getDouble("latitude");
			lng = getIntent().getExtras().getDouble("longitude");
			zoom = 17;
		}

		p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

		mc.animateTo(p);
		mc.setZoom(zoom);
		plotaPostos();
		listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();
		createOverlays();
		MapOverlayPosto os = new MapOverlayPosto();
		loadOverLay();
		listOfOverlays.add(os);
		mapView.invalidate();
		mapView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				center = mapView.getMapCenter();
				getBoundingBox();
				if (testeCenter()) {
					Reload();
				}
				return false;
			}
		});
	}

	private void createOverlays() {
		Drawable valido = this.getResources().getDrawable(
				R.drawable.iconpostovalido);
		Drawable invalido = this.getResources().getDrawable(
				R.drawable.iconepostoinvalido);
		Drawable ban = this.getResources().getDrawable(R.drawable.petrobras);
		Drawable ban1 = this.getResources().getDrawable(R.drawable.shell);
		Drawable ban2 = this.getResources().getDrawable(R.drawable.texaco);
		Drawable ban3 = this.getResources().getDrawable(R.drawable.whiteflag);
		Drawable ban4 = this.getResources().getDrawable(R.drawable.esso);
		Drawable ban5 = this.getResources().getDrawable(R.drawable.ello);
		Drawable ban6 = this.getResources().getDrawable(R.drawable.dislub);
		itemizedOverlay = new MyItemizedOverlay(valido, mapView);
		itemizedOverlay2 = new MyItemizedOverlay(invalido, mapView);
		itemizedOverlay3 = new MyItemizedOverlay(ban, mapView);
		itemizedOverlay4 = new MyItemizedOverlay(ban1, mapView);
		itemizedOverlay5 = new MyItemizedOverlay(ban2, mapView);
		itemizedOverlay6 = new MyItemizedOverlay(ban3, mapView);
		itemizedOverlay7 = new MyItemizedOverlay(ban4, mapView);
		itemizedOverlay8 = new MyItemizedOverlay(ban5, mapView);
		itemizedOverlay9 = new MyItemizedOverlay(ban6, mapView);
	}

	private boolean testeCenter() {
		double lat = center.getLatitudeE6() / 1E6;
		double lng = center.getLongitudeE6() / 1E6;
		if (noIntervalo(lat, auxiliar[0].getLatitudeInDegrees(),
				auxiliar[1].getLatitudeInDegrees())
				&& noIntervalo(lng, auxiliar[0].getLongitudeInDegrees(),
						auxiliar[1].getLongitudeInDegrees())) {
			return true;
		}
		return false;
	}

	private boolean noIntervalo(double lat2, double latitudeInDegrees,
			double latitudeInDegrees2) {
		boolean result = false;
		if (lat2 <= Math.min(latitudeInDegrees, latitudeInDegrees2)
				|| lat2 >= Math.max(latitudeInDegrees, latitudeInDegrees2))
			result = true;
		return result;
	}

	public void loadOverLay() {
		int postos_num = postos.size();
		int min = 75;
		int i = 0;
		HashMap<Integer, Integer> latlng = new HashMap<Integer, Integer>();
		while (postos_num > 0 && min > 0) {
			GeoPoint ponto = all_places.get(i).getPoint();
			OverlayItem overlayitem = new OverlayItem(ponto, all_places.get(i)
					.getName(), "Gasolina: " + all_places.get(i).getGasolina()
					+ "\nAlcool: " + all_places.get(i).getAlcool()
					+ "\nDiesel: " + all_places.get(i).getDisel() + "\nGás: "
					+ all_places.get(i).getGas());

			if (!latlng.containsKey(ponto.getLatitudeE6())
					|| latlng.get(ponto.getLatitudeE6()) != ponto
							.getLongitudeE6()) {
				Log.i("Entrou",
						ponto.getLatitudeE6() + " " + ponto.getLongitudeE6());

				if (all_places.get(i).isValidade()) {
					if (all_places.get(i).getBandeira()
							.equalsIgnoreCase("PETROBRAS DISTRIBUIDORA S.A")
							|| all_places.get(i).getBandeira()
									.equalsIgnoreCase("PETROBRAS")) {
						itemizedOverlay3.addOverlay(overlayitem);
					} else if (all_places.get(i).getBandeira()
							.equalsIgnoreCase("SHELL")) {
						itemizedOverlay4.addOverlay(overlayitem);
					} else if (all_places.get(i).getBandeira()
							.equalsIgnoreCase("IPP")) {
						itemizedOverlay5.addOverlay(overlayitem);
					} else if (all_places.get(i).getBandeira()
							.equalsIgnoreCase("BANDEIRA BRANCA")) {
						itemizedOverlay6.addOverlay(overlayitem);
					} else if (all_places.get(i).getBandeira()
							.equalsIgnoreCase("COSAN COMBUSTÍVEIS")) {
						itemizedOverlay7.addOverlay(overlayitem);
					} else if (all_places.get(i).getBandeira()
							.equalsIgnoreCase("ELLO")) {
						itemizedOverlay8.addOverlay(overlayitem);
					} else if (all_places.get(i).getBandeira()
							.equalsIgnoreCase("DISLUB")) {
						itemizedOverlay9.addOverlay(overlayitem);
					} else {
						itemizedOverlay.addOverlay(overlayitem);
					}
				} else {
					itemizedOverlay2.addOverlay(overlayitem);
				}
				min--;
				latlng.put(ponto.getLatitudeE6(), ponto.getLongitudeE6());
			}
			i++;
			postos_num--;
		}

		if (itemizedOverlay4.size() > 0)
			listOfOverlays.add(itemizedOverlay4);
		if (itemizedOverlay.size() > 0)
			listOfOverlays.add(itemizedOverlay);
		if (itemizedOverlay3.size() > 0)
			listOfOverlays.add(itemizedOverlay3);
		if (itemizedOverlay5.size() > 0)
			listOfOverlays.add(itemizedOverlay5);
		if (itemizedOverlay6.size() > 0)
			listOfOverlays.add(itemizedOverlay6);
		if (itemizedOverlay7.size() > 0)
			listOfOverlays.add(itemizedOverlay7);
		if (itemizedOverlay8.size() > 0)
			listOfOverlays.add(itemizedOverlay8);
		if (itemizedOverlay9.size() > 0)
			listOfOverlays.add(itemizedOverlay9);
		if (itemizedOverlay2.size() > 0)
			listOfOverlays.add(itemizedOverlay2);
	}

	public String getBoundingBox() {
		GeoLocation aux = GeoLocation.fromDegrees(lat, lng);
		double distance = (20 - mapView.getZoomLevel()) * 2.5;
		if(distancia) distance = v_distancia;
		auxiliar = aux.boundingCoordinates(distance);
		String bbox = auxiliar[0].toString() + " " + auxiliar[1].toString();
		return bbox;
	}

	public void plotaPostos() {
		all_places = getPostos();
		postos = new ArrayList<GeoPoint>();
		for (Place p : all_places) {
			// Log.i("Place", "Latitude :" + String.valueOf(p.getLat())+
			// "  Longitude :"+String.valueOf(p.getLonge())
			GeoPoint point = new GeoPoint((int) (p.getLat() * 1E6),
					(int) (p.getLonge() * 1E6));
			// listOfOverlays.add(new MapOverlayPosto(point));
			postos.add(point);
		}
	}

	public ArrayList<Place> getPostos() {
		ArrayList<Place> all_places1 = new ArrayList<Place>();
		try {
			String preco = "";
			String ban = "";
			if(min || max){
				preco = "<PropertyIsBetween>               <PropertyName>pricegasoline</PropertyName>               <LowerBoundary><Literal>"+preco_min+"</Literal></LowerBoundary>               <UpperBoundary><Literal>"+preco_max+"</Literal></UpperBoundary>           </PropertyIsBetween>";
			}
			if(bandeira){
				ban = "  <PropertyIsLike escapeChar=\"\\\" singleChar=\"_\" wildCard=\"%\">                        <PropertyName>bandeira</PropertyName>                    <Literal>%"+v_bandeira+"%</Literal>            </PropertyIsLike>";
			}
			String url = "<Filter xmlns:gml=\"http://www.opengis.net/gml\" xmlns=\"http://www.opengis.net/ogc\">    <And>        <BBOX>            <PropertyName>geom</PropertyName>            <gml:Box srsName=\"http://www.opengis.net/gml/srs/epsg.xml#4326\">                <gml:coordinates>"
					+ getBoundingBox()
					+ "                </gml:coordinates>             </gml:Box>          </BBOX>    "+preco+ban+"</And></Filter>";
			xml = new XMLHandler();
			// xml.loadXMLfromURL("http://buchada.dsc.ufcg.edu.br/geoserver/wfs?request=GetFeature&version=1.0.0&typeName=geogas:gasstation&BBOX="
			// + getBoundingBox());
			String allurl = "http://buchada.dsc.ufcg.edu.br/geoserver/wfs?request=GetFeature&version=1.0.0&typeName=geogas:gasstation&FILTER="
					+ URLEncoder.encode(url, "UTF-8");
			Log.i("URL", allurl);
			xml.loadXMLfromURL(allurl);

			all_places1 = xml.getElementByPlaceBB();
		} catch (IOException e) {
			Log.i("Error", e.toString());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			Log.i("Error", e.toString());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			Log.i("Error", e.toString());
		}
		return all_places1;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i("Click", "Testando");
		MapController mc = mapView.getController();
		switch (keyCode) {
		case KeyEvent.KEYCODE_3:
			// ReloadView();
			break;
		case KeyEvent.KEYCODE_1:
			mc.zoomOut();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void Reload() {
		lat = mapView.getMapCenter().getLatitudeE6() / 1E6;
		lng = mapView.getMapCenter().getLongitudeE6() / 1E6;
		mapView.removeAllViews();
		mapView = (MapView) findViewById(R.id.mapView);
		// LinearLayout zoomLayout = (LinearLayout) findViewById(R.id.zoom);
		// View zoomView = mapView.getZoomControls();
		//
		// zoomLayout.addView(zoomView, new LinearLayout.LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		// mapView.displayZoomControls(true);
		mc = mapView.getController();

		sucess = getIntent().getExtras().getBoolean("sucess");

		p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
		plotaPostos();
		listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();
		createOverlays();
		MapOverlayPosto os = new MapOverlayPosto();
		loadOverLay();
		listOfOverlays.add(os);
		mapView.invalidate();
		mapView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				center = mapView.getMapCenter();
				if (testeCenter()) {
					mapView.removeAllViews();
					Reload();
				}
				return false;
			}
		});
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
