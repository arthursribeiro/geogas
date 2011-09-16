package br.edu.ufcg.geogas.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Embeddable
public class PostoCombustivel_TipoCombustivel_ANP_PK implements Serializable{
	
	@NotNull
	@Column(name="id_posto_combustivel")
	private Integer id_posto_combustivel;
	
	@NotNull
	@Column(name="id_tipo_combustivel")
	private Integer id_tipo_combustivel;
	
	public Integer getId_posto_combustivel() {
		return id_posto_combustivel;
	}

	public void setId_posto_combustivel(Integer id_posto_combustivel) {
		this.id_posto_combustivel = id_posto_combustivel;
	}

	public Integer getId_tipo_combustivel() {
		return id_tipo_combustivel;
	}

	public void setId_tipo_combustivel(Integer id_tipo_combustivel) {
		this.id_tipo_combustivel = id_tipo_combustivel;
	}

}
