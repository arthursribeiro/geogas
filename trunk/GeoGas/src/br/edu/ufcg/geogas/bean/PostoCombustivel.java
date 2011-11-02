package br.edu.ufcg.geogas.bean;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "PostoCombustivel")
@PrimaryKeyJoinColumn(name="id_posto_combustivel") 
public class PostoCombustivel extends Entidade{
	
    @NotNull
    @Column(name="id_posto_combustivel", insertable=false, updatable=false)
    private Integer id_posto_combustivel; // surrogate key
	
	@Column(name = "autorizacao")
	private String autorizacao;
	
	@NotNull
	@Column(name = "cnpjCpf")
	private String cnpjCpf;
	
	@Column(name = "razaoSocial")
	private String razaoSocial;
	
	@Column(name = "nomeFantasia")
	private String nomeFantasia;
	
	@Column(name = "numeroDespacho")
	private String numeroDespacho; 
	
	@Column(name = "bandeira")
	private String bandeira;
	
	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	private Double longitude;
	
	@Column(name = "pricegasoline")
	private Double pricegasoline;
	
	@Column(name = "pricealcohol")
	private Double pricealcohol;
	
	@Column(name = "pricediesel")
	private Double pricediesel;
	
	@Column(name = "pricegas")
	private Double pricegas;
	
	@Column(name = "pricegasoline_user")
	private Double pricegasoline_user;
	
	@Column(name = "pricealcohol_user")
	private Double pricealcohol_user;
	
	@Column(name = "pricediesel_user")
	private Double pricediesel_user;
	
	@Column(name = "pricegas_user")
	private Double pricegas_user;
	
	@Column(name = "autuacoes")
	private Integer autuacoes;
	
	@Column(name = "denuncias")
	private Integer denuncias;
	
	
	public HashMap<String, Object> getHashMap() {
		HashMap<String,Object> tabs = new HashMap<String, Object>();
		HashMap<String,Object> tabEnt = new HashMap<String, Object>();
		HashMap<String,Object> tabPosto = new HashMap<String, Object>();
		HashMap<String,Object> tabPrecos = new HashMap<String, Object>();
		
		tabEnt = super.getEntHashMap();
		
		tabPosto.put("autorizacao", this.autorizacao);
		tabPosto.put("bandeira", this.bandeira);
		tabPosto.put("cnpjcpf", this.cnpjCpf);
		tabPosto.put("id_posto_combustivel", this.id_posto_combustivel);
		tabPosto.put("latitude", this.latitude);
		tabPosto.put("longitude", this.longitude);
		tabPosto.put("nomefantasia", this.nomeFantasia);
		tabPosto.put("numerodespacho", this.numeroDespacho);
		tabPosto.put("razaosocial", this.razaoSocial);
		tabPosto.put("autuacoes", this.autuacoes==null?" -- ":this.autuacoes);
		tabPosto.put("denuncias", this.denuncias==null?" -- ":this.denuncias);
		
		tabPrecos.put("pricealcohol", "R$"+(this.pricealcohol==null?" -- ":this.pricealcohol.toString().replace(".", ",")));
		tabPrecos.put("pricealcohol_user", "R$"+(this.pricealcohol_user==null?" -- ":this.pricealcohol_user.toString().replace(".", ",")));
		tabPrecos.put("pricediesel", "R$"+(this.pricediesel==null?" -- ":this.pricediesel.toString().replace(".", ",")));
		tabPrecos.put("pricediesel_user", "R$"+(this.pricediesel_user==null?" -- ":this.pricediesel_user.toString().replace(".", ",")));
		tabPrecos.put("pricegas", "R$"+(this.pricegas==null?" -- ":this.pricegas.toString().replace(".", ",")));
		tabPrecos.put("pricegas_user", "R$"+(this.pricegas_user==null?" -- ":this.pricegas_user.toString().replace(".", ",")));
		tabPrecos.put("pricegasoline", "R$"+(this.pricegasoline==null?" -- ":this.pricegasoline.toString().replace(".", ",")));
		tabPrecos.put("pricegasoline_user", "R$"+(this.pricegasoline_user==null?" -- ":this.pricegasoline_user.toString().replace(".", ",")));
		
		tabs.put("Entity", tabEnt);
		tabs.put("Gas Station", tabPosto);
		tabs.put("Prices", tabPrecos);
		return tabs;
	}
	
	
	public Integer getId_posto_combustivel() {
		return id_posto_combustivel;
	}

	public void setId_posto_combustivel(Integer id) {
		this.id_posto_combustivel = id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf){
		this.cnpjCpf = cnpjCpf;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getNumeroDespacho() {
		return numeroDespacho;
	}

	public void setNumeroDespacho(String numeroDespacho) {
		this.numeroDespacho = numeroDespacho;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}

	public Double getPricegasoline() {
		return pricegasoline;
	}

	public void setPricegasoline(Double pricegasoline) {
		this.pricegasoline = pricegasoline;
	}

	public Double getPricealcohol() {
		return pricealcohol;
	}

	public void setPricealcohol(Double pricealcohol) {
		this.pricealcohol = pricealcohol;
	}

	public Double getPricediesel() {
		return pricediesel;
	}

	public void setPricediesel(Double pricediesel) {
		this.pricediesel = pricediesel;
	}

	public Double getPricegas() {
		return pricegas;
	}

	public void setPricegas(Double pricegas) {
		this.pricegas = pricegas;
	}

	public Double getPricegasoline_user() {
		return pricegasoline_user;
	}

	public void setPricegasoline_user(Double pricegasoline_user) {
		this.pricegasoline_user = pricegasoline_user;
	}

	public Double getPricealcohol_user() {
		return pricealcohol_user;
	}

	public void setPricealcohol_user(Double pricealcohol_user) {
		this.pricealcohol_user = pricealcohol_user;
	}

	public Double getPricediesel_user() {
		return pricediesel_user;
	}

	public void setPricediesel_user(Double pricediesel_user) {
		this.pricediesel_user = pricediesel_user;
	}

	public Double getPricegas_user() {
		return pricegas_user;
	}

	public void setPricegas_user(Double pricegas_user) {
		this.pricegas_user = pricegas_user;
	}

	public Integer getAutuacoes() {
		return autuacoes;
	}

	public void setAutuacoes(Integer autuacoes) {
		this.autuacoes = autuacoes;
	}

	public Integer getDenuncias() {
		return denuncias;
	}

	public void setDenuncias(Integer denuncias) {
		this.denuncias = denuncias;
	}

}
