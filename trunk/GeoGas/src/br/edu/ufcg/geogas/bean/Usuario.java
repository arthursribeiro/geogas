package br.edu.ufcg.geogas.bean;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Usuario")
public class Usuario {
	
	@NotNull
    @Column(name="nome")
	private String nome;
	
	@Id
	@NotNull
    @Column(name="facebook_id")
	private String facebook_id;
	
    @Column(name="cpf")
	private String cpf;
    
    @Column(name="data_nascimento")
	private Date data_nascimento;
	
	@NotNull
    @Column(name="chave_facebook")
	private String chave_facebook;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFacebook_id() {
		return facebook_id;
	}

	public void setFacebook_id(String facebook_id) {
		this.facebook_id = facebook_id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date idade) {
		this.data_nascimento = idade;
	}

	public String getChave_facebook() {
		return chave_facebook;
	}

	public void setChave_facebook(String chave_facebook) {
		this.chave_facebook = chave_facebook;
	}

}
