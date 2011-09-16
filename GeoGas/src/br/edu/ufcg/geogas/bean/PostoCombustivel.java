package br.edu.ufcg.geogas.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "PostoCombustivel")
public class PostoCombustivel {
	
	@Id
    @NotNull
    @Column(name="id_posto_combustivel")
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
	
	public Integer getId() {
		return id_posto_combustivel;
	}

	public void setId(Integer id) {
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

}
