package br.edu.ufcg.geogas.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Embeddable
public class Avaliacao_PostoCombustivel_ANP_PK implements Serializable{

	@NotNull
	@Column(name="id_posto_combustivel")
	private Integer id_posto_combustivel;
	
	@NotNull
	@Column(name="id_avaliacao_anp")
	private Integer id_avaliacao_anp;

	public Integer getId_posto_combustivel() {
		return id_posto_combustivel;
	}

	public void setId_posto_combustivel(Integer id_posto_combustivel) {
		this.id_posto_combustivel = id_posto_combustivel;
	}

	public Integer getId_avaliacao_anp() {
		return id_avaliacao_anp;
	}

	public void setId_avaliacao_anp(Integer id_avaliacao_anp) {
		this.id_avaliacao_anp = id_avaliacao_anp;
	}
	
}
