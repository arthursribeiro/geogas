package br.edu.ufcg.geogas.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="AvaliacaoANP")
public class AvaliacaoANP {
	@Id
	@NotNull
	@Column(name="id_avaliacao_anp")
	private Integer id_avaliacao_anp;
	
	@NotNull
	@Column(name="avaliacao")
	private String avaliacao;

	public Integer getId_avaliacao_anp() {
		return id_avaliacao_anp;
	}

	public void setId_avaliacao_anp(Integer id_avaliacao_anp) {
		this.id_avaliacao_anp = id_avaliacao_anp;
	}

	public String getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(String avaliacao) {
		this.avaliacao = avaliacao;
	}
}
