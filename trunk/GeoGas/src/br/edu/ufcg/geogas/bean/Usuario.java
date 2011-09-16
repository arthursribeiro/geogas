package br.edu.ufcg.geogas.bean;


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
	
	@Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "usuario_id_usuario_seq")
    private Integer id_usuario;
	
	@NotNull
    @Column(name="nome")
	private String nome;
	
	@NotNull
    @Column(name="facebook_id")
	private String facebook_id;
	
    @Column(name="cpf")
	private long cpf;
    
    @Column(name="idade")
	private Integer idade;
	
	@NotNull
    @Column(name="chave_facebook")
	private String chave_facebook;

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}

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

	public long getCpf() {
		return cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getChave_facebook() {
		return chave_facebook;
	}

	public void setChave_facebook(String chave_facebook) {
		this.chave_facebook = chave_facebook;
	}

}
