package br.edu.ufcg.geogas.bean;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Historico_Precos_Usuario")
public class Historico_Precos_Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "historico_precos_usuario_id_seq")
	private Integer id;
	
	@Column(name="id_posto_combustivel")
	private Integer id_posto_combustivel;
	
	@Column(name="id_usuario")
	private Integer id_usuario;

	@Column(name="data")
	private Date data;

	@Column(name="pricegasoline")
	private double pricegasoline;
	
	@Column(name="pricealcohol")
	private double  pricealcohol;
	
	@Column(name="pricediesel")
	private double  pricediesel;
	
	@Column(name="pricegas")
	private double  pricegas;
	
	public Integer getId_posto_combustivel() {
		return id_posto_combustivel;
	}

	public void setId_posto_combustivel(Integer id_posto_combustivel) {
		this.id_posto_combustivel = id_posto_combustivel;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}
}
