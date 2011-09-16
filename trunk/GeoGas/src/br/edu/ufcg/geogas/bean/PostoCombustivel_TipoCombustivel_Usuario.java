package br.edu.ufcg.geogas.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="PostoCombustivel_TipoCombustivel_Usuario")
public class PostoCombustivel_TipoCombustivel_Usuario {
	
	@EmbeddedId
	private PostoCombustivel_TipoCombustivel_Usuario_PK id;
	
	@NotNull
	@Column(name="preco")
	private double preco;
	
	@NotNull
	@Column(name="data")
	private Date data;

	public PostoCombustivel_TipoCombustivel_Usuario_PK getId() {
		return id;
	}

	public void setId(PostoCombustivel_TipoCombustivel_Usuario_PK id) {
		this.id = id;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
