package br.edu.ufcg.geogas.bean;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Historico_Precos_Usuario")
public class Historico_Precos_Usuario {
	
	@EmbeddedId
	public Historico_Precos_Usuario_PK id;

	@Column(name="pricegasoline")
	private double pricegasoline;
	
	@Column(name="pricealcohol")
	private double  pricealcohol;
	
	@Column(name="pricediesel")
	private double  pricediesel;
	
	@Column(name="pricegas")
	private double  pricegas;
	
	public double getPricegasoline() {
		return pricegasoline;
	}

	public void setPricegasoline(double pricegasoline) {
		this.pricegasoline = pricegasoline;
	}

	public double getPricealcohol() {
		return pricealcohol;
	}

	public void setPricealcohol(double pricealcohol) {
		this.pricealcohol = pricealcohol;
	}

	public double getPricediesel() {
		return pricediesel;
	}

	public void setPricediesel(double pricediesel) {
		this.pricediesel = pricediesel;
	}

	public double getPricegas() {
		return pricegas;
	}

	public void setPricegas(double pricegas) {
		this.pricegas = pricegas;
	}
}
