package br.edu.ufcg.geogas.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Avaliacao_Entidade_Usuario")
public class Avaliacao_Entidade_Usuario {

	@EmbeddedId
	private Avaliacao_Entidade_Usuario_PK id;
	
	@NotNull
	@Column(name="nota")
	private Integer nota;
	
	@NotNull
	@Column(name="data")
	private Date data;
	
	public Avaliacao_Entidade_Usuario_PK getId() {
		return id;
	}

	public void setId(Avaliacao_Entidade_Usuario_PK id) {
		this.id = id;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
