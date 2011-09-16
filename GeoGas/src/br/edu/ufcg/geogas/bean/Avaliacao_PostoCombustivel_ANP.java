package br.edu.ufcg.geogas.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Avaliacao_PostoCombustivel_ANP")
public class Avaliacao_PostoCombustivel_ANP {
	
	@EmbeddedId
	private Avaliacao_PostoCombustivel_ANP_PK id;
	
	@NotNull
	@Column(name="data")
	private Date data;

	public Avaliacao_PostoCombustivel_ANP_PK getId() {
		return id;
	}

	public void setId(Avaliacao_PostoCombustivel_ANP_PK id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
