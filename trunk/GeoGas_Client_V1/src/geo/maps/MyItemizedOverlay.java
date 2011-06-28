package geo.maps;

import geo.data.Place;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();
	private Context c;
	private HashMap<OverlayItem,Place> a = new HashMap<OverlayItem, Place>();
	
	public MyItemizedOverlay(Drawable defaultMarker, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
		populate();
		c = mapView.getContext();
	}

	public void addOverlay(OverlayItem overlay) {
	    m_overlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	@Override
	protected boolean onBalloonTap(int index, OverlayItem item) {
		AlertDialog alert = new AlertDialog.Builder(this.c).create();
		alert.setTitle("Informações do Posto");
		Place p = a.get(item);
		String info = "";
		info += "Nome do posto: " + p.getName() + "\n";
		info += "Latitude: " + p.getLat() + "\n";
		info += "Longitude: " + p.getLonge() + "\n";
		info += "Endereco: " + p.getEndereco() + "\n";
		info += "Bandeira: " + p.getBandeira() + "\n";
		info += "Gasolina: R$" + p.getGasolina() + "\n";
		info += "Alcool: R$" + p.getAlcool() + "\n";
		info += "Diesel: R$" + p.getDisel() + "\n";
		info += "Gas: R$" + p.getGas() + "\n";
		alert.setMessage(info);
		alert.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

		        return;
			}
		});
		alert.show();
		return true;
	}

	public void mash(OverlayItem overlayitem, Place place) {
		a.put(overlayitem, place);
	}

}
