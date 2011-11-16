package br.edu.ufcg.geogas.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Denuncia")
public class Denuncia {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "denuncia_id_denuncia_seq")
	private Integer id_denuncia; // SERIAL PRIMARY KEY,
	
	@NotNull
	@Column(name="reclamacao")
	private String reclamacao;
	
	@NotNull
	@Column(name="id_usuario")
	private String id_usuario;
	
	@ManyToMany(
	         targetEntity=Entidade.class,
	         cascade={CascadeType.ALL}, 
	         fetch=FetchType.EAGER
	     )
	     @JoinTable(
	         name="Entidade_Denuncia",
	         joinColumns={@JoinColumn(name="id_denuncia")},
	         inverseJoinColumns={@JoinColumn(name="id_entidade")}
	     )
	private Set<Entidade> entidades;

	public Integer getId_denuncia() {
		return id_denuncia;
	}

	public void setId_denuncia(Integer id_denuncia) {
		this.id_denuncia = id_denuncia;
	}

	public String getReclamacao() {
		return reclamacao;
	}

	public void setReclamacao(String reclamacao) {
		this.reclamacao = reclamacao;
	}

	public Set<Entidade> getEntidades() {
		return entidades;
	}

	public void setEntidades(Set<Entidade> entidades) {
		this.entidades = entidades;
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String idUser) {
		this.id_usuario = idUser;
	}

}
