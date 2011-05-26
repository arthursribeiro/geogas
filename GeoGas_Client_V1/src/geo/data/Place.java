package geo.data;

import org.w3c.dom.Element;

public class Place {
	double lat;
	double longe;
	boolean validade;
	String name;
	String gasolina;
	String gas;
	String disel;
	String alcool;
	
	public Place(String lat, String longe) {
		this.lat = Double.parseDouble(lat.replace(" ", "").replace(",", "."));
		this.longe = Double.parseDouble(longe.replace(" ", "").replace(",", "."));
	}
	

	public Place(String latitude, String longitude, String validade2,
			String posto_name) {
		this.lat = Double.parseDouble(latitude.replace(" ", "").replace(",", "."));
		this.longe = Double.parseDouble(longitude.replace(" ", "").replace(",", "."));
		this.validade = Boolean.parseBoolean(validade2);
		this.name = posto_name;
	}

	public Place(String latitude, String longitude, String validade2,
			String posto_name, String string_gasolina, String string_gas,
			String string_disel, String string_alcool) {
		this.lat = Double.parseDouble(latitude.replace(" ", "").replace(",", "."));
		this.longe = Double.parseDouble(longitude.replace(" ", "").replace(",", "."));
		this.validade = Boolean.parseBoolean(validade2);
		this.name = posto_name;
		this.gasolina = string_gasolina;
		this.gas = string_gas;
		this.disel = string_disel;
		this.alcool = string_alcool;
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


	public boolean isValidade() {
		return validade;
	}


	public void setValidade(boolean validade) {
		this.validade = validade;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getGasolina() {
		return gasolina;
	}


	public void setGasolina(String gasolina) {
		this.gasolina = gasolina;
	}


	public String getGas() {
		return gas;
	}


	public void setGas(String gas) {
		this.gas = gas;
	}


	public String getDisel() {
		return disel;
	}


	public void setDisel(String disel) {
		this.disel = disel;
	}


	public String getAlcool() {
		return alcool;
	}


	public void setAlcool(String alcool) {
		this.alcool = alcool;
	}
}
