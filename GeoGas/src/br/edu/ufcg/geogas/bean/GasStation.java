package br.edu.ufcg.geogas.bean;

import java.text.Normalizer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "GasStation")
public class GasStation {
	
	@Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "gasstation_id_seq")
    private Integer id; // surrogate key
	
	@Column(name = "autorizacao")
	private String autorizacao;
	
	@Column(name = "cnpjCpf")
	@NotNull
	private String cnpjCpf;
	
	@Column(name = "razaoSocial")
	private String razaoSocial;
	
	@Column(name = "nomeFantasia")
	private String nomeFantasia;
	
	@Column(name = "endereco")
	@NotNull
	private String endereco;
	
	@Column(name = "complemento")
	private String complemento;
	
	@Column(name = "bairro")
	private String bairro;
	
	@Column(name = "municipioUF")
	@NotNull
	private String municipioUF;
	
	@Column(name = "cep")
	private String cep;
	
	@Column(name = "numeroDespacho")
	private String numeroDespacho; 
	
	@Column(name = "dataPublicacao")
	private String dataPublicacao;
	
	@Column(name = "bandeira")
	private String bandeira;
	
	@Column(name = "tipoPosto")
	private String tipoPosto;
	
	@Column(name = "priceGasoline")
	private Double priceGasoline;
	
	@Column(name = "pricegas")
	private Double priceGas;
	
	@Column(name = "priceDiesel")
	private Double priceDiesel;
	
	@Column(name = "pricealcohol")
	private Double priceAlcohol;
	
	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	private Double longitude;
	
	@Column(name = "infracoes")
	private Integer infracoes;
	
	public String toXML() {
		String name = "";
		if(nomeFantasia!=null && nomeFantasia.trim().length()>0) name = nomeFantasia;
		else if(razaoSocial!=null && razaoSocial.trim().length()>0) name = razaoSocial;
		else name = "CNPJ/CPF: "+cnpjCpf;
		
		name = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}Á«]", "");
		String band = Normalizer.normalize(bandeira, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}Á«]", "");
		
		String nodeId = "<id value=\""+cnpjCpf+"\"></id>";
		String nodeName = "<name value=\""+name+"\"></name>";
		String nodeBandeira = "<bandeira value=\""+band+"\"></bandeira>";
		String nodePriceGasoline = "<priceGasoline value=\""+(priceGasoline!=null?priceGasoline:"")+"\"></priceGasoline>";
		String nodePriceGas = "<priceGas value=\""+(priceGas!=null?priceGas:"")+"\"></priceGas>";
		String nodePriceDiesel = "<priceDiesel value=\""+(priceDiesel!=null?priceDiesel:"")+"\"></priceDiesel>";
		String nodePriceAlcohol = "<priceAlcohol value=\""+(priceAlcohol!=null?priceAlcohol:"")+"\"></priceAlcohol>";
		
		//nodeName+"\n"+nodeAddress+"\n"+
		String data = "\n"+nodeName+"\n"+nodeId+"\n"+nodeBandeira+"\n"+
						nodePriceGasoline+"\n"+nodePriceGas+"\n"+nodePriceDiesel+"\n"+nodePriceAlcohol+"\n";
		
		String xml = "<location latitude=\""+latitude+"\" longitude=\""+longitude+"\" valido=\""+(!(infracoes>0))+"\">"+data+"</location>\n";
		return xml;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPriceGasoline() {
		return priceGasoline;
	}

	public void setPriceGasoline(Double priceGasoline) {
		this.priceGasoline = priceGasoline;
	}

	public Double getPriceGas() {
		return priceGas;
	}

	public void setPriceGas(Double priceGas) {
		this.priceGas = priceGas;
	}

	public Double getPriceDiesel() {
		return priceDiesel;
	}

	public void setPriceDiesel(Double priceDiesel) {
		this.priceDiesel = priceDiesel;
	}

	public Double getPriceAlcohol() {
		return priceAlcohol;
	}

	public void setPriceAlcohol(Double priceAlcohol) {
		this.priceAlcohol = priceAlcohol;
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipioUF() {
		return municipioUF;
	}

	public void setMunicipioUF(String municipioUF) {
		this.municipioUF = municipioUF;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumeroDespacho() {
		return numeroDespacho;
	}

	public void setNumeroDespacho(String numeroDespacho) {
		this.numeroDespacho = numeroDespacho;
	}

	public String getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}

	public String getTipoPosto() {
		return tipoPosto;
	}

	public void setTipoPosto(String tipoPosto) {
		this.tipoPosto = tipoPosto;
	}

	public String getAddressForGMaps() {
		String address = this.endereco+"+"+this.complemento+"+"+this.cep+"+"+this.bairro+"+"+this.municipioUF+"+Brasil";
		return address.replace(" ", "+");
	}

	public Integer getInfracoes() {
		return infracoes;
	}

	public void setInfracoes(Integer infracoes) {
		this.infracoes = infracoes;
	}
	
}
