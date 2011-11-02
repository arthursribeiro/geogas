package br.edu.ufcg.geogas.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Autuacoes_ANP")
public class AvaliacaoANP {
	
	@EmbeddedId
	private AvaliacaoANP_PK id_avaliacao_anp;
	
	@NotNull
	@Column(name="autuacao")
	private String avaliacao;

	public AvaliacaoANP_PK getId_avaliacao_anp() {
		return id_avaliacao_anp;
	}

	public void setId_avaliacao_anp(AvaliacaoANP_PK id_avaliacao_anp) {
		this.id_avaliacao_anp = id_avaliacao_anp;
	}

	public String getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(String avaliacao) {
		this.avaliacao = avaliacao;
	}
}
