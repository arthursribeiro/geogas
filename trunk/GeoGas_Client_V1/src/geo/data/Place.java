package geo.data;

public class Place {
	double lat;
	double longe;
	
	public Place(String lat, String longe) {
		this.lat = Double.parseDouble(lat.replace(" ", "").replace(",", "."));
		this.longe = Double.parseDouble(longe.replace(" ", "").replace(",", "."));
	}
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLonge() {
		return longe;
	}
	public void setLonge(double longe) {
		this.longe = longe;
	}
}
