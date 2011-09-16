package br.edu.ufcg.geogas.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Embeddable
public class Avaliacao_Entidade_Usuario_PK implements Serializable{
	
	@NotNull
	@Column(name="id_entidade")
	private Integer id_entidade;
	
	@NotNull
	@Column(name="id_usuario")
	private Integer id_usuario;
	
	public Integer getId_entidade() {
		return id_entidade;
	}

	public void setId_entidade(Integer id_entidade) {
		this.id_entidade = id_entidade;
	}

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}
}
