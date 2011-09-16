package br.edu.ufcg.geogas.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TipoEntidade")
public class TipoEntidade {
	
	@Id
	@NotNull
	private Integer id_tipo_entidade;
	
	@NotNull
	private String tipo_entidade;

	public Integer getId_tipo_entidade() {
		return id_tipo_entidade;
	}

	public void setId_tipo_entidade(Integer id_tipo_entidade) {
		this.id_tipo_entidade = id_tipo_entidade;
	}

	public String getTipo_entidade() {
		return tipo_entidade;
	}

	public void setTipo_entidade(String tipo_entidade) {
		this.tipo_entidade = tipo_entidade;
	}

}
