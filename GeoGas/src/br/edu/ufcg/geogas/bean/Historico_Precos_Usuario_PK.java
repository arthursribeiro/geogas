package br.edu.ufcg.geogas.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Historico_Precos_Usuario_PK implements Serializable{
	@Column(name="id_posto_combustivel")
	private Integer id_posto_combustivel;
	
	@Column(name="id_usuario")
	private Integer id_usuario;
	
	@Column(name="data")
	private Date data;

	public Integer getId_posto_combustivel() {
		return id_posto_combustivel;
	}

	public void setId_posto_combustivel(Integer id_posto_combustivel) {
		this.id_posto_combustivel = id_posto_combustivel;
	}

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
