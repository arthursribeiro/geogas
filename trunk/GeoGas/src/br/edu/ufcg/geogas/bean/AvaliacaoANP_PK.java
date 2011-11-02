package br.edu.ufcg.geogas.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Embeddable
public class AvaliacaoANP_PK implements Serializable{

	@NotNull
	@Column(name="id_posto_combustivel")
	private Integer id_posto_combustivel;
	
	@NotNull
	@Column(name="data")
	private Date data;

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
	
}
