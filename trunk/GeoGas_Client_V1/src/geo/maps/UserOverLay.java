package geo.maps;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class UserOverLay extends Overlay {
	Bitmap bmp = null;
	GeoPoint p = null;
	int ajuste = 0;
	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
			long when) {
		super.draw(canvas, mapView, shadow);

		// ---translate the GeoPoint to screen pixels---
		Point screenPts = new Point();
		mapView.getProjection().toPixels(p, screenPts);

		// ---add the marker---
		
		canvas.drawBitmap(bmp, screenPts.x, screenPts.y - ajuste, null);
		return true;
	}
	public void setBitmap(Resources res,int id){
		Bitmap bmp = BitmapFactory.decodeResource(res,id);
	}
	public void setGeoPoint(GeoPoint point){
		this.p = point;
	}
	public void setAjuste(int i){
		this.ajuste = i;
	}
}
