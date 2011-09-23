package br.edu.ufcg.geogas.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Entidade")
@Inheritance(strategy=InheritanceType.JOINED)
public class Entidade {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "entidade_id_entidade_seq")
	private Integer id_entidade; // SERIAL PRIMARY KEY,
	
	@NotNull
	@Column(name="nome")
	private String nome; //NOT NULL,
	
	@NotNull
	@Column(name="data_criacao")
	private Date data_criacao;//NOT NULL
	
	@NotNull
	@Column(name="endereco")
	private String endereco;//NOT NULL,
	
	@Column(name="bairro")
	private String bairro;
	
	@Column(name="cidade")
	private String cidade;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="pais")
	private String pais;
	
	@Column(name="cep")
	private String cep;
	
	@NotNull
	@OneToOne(fetch=FetchType.EAGER)
	@JoinTable(
	         name="Entidade_TipoEntidade",
	         joinColumns={@JoinColumn(name="id_tipo_entidade")},
	         inverseJoinColumns={@JoinColumn(name="id_entidade")}
	     )
	//@JoinColumn(name = "id_entidade",referencedColumnName="id_tipo_entidade")
	private TipoEntidade tipo_entidade;

	public Integer getId_entidade() {
		return id_entidade;
	}

	public void setId_entidade(Integer id_entidade) {
		this.id_entidade = id_entidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(Date data_criacao) {
		this.data_criacao = data_criacao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public TipoEntidade getTipo_entidade() {
		return tipo_entidade;
	}

	public void setTipo_entidade(TipoEntidade tipo_entidade) {
		this.tipo_entidade = tipo_entidade;
	}
	
}
