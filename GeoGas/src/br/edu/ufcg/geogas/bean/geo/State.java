package br.edu.ufcg.geogas.bean.geo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "State")
public class State {
	@Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "state_id_seq")
    private Integer id; // surrogate key
	
	@Column(name = "name")
    @NotNull
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Region.class)
	@JoinColumn(name="regionid",referencedColumnName="id")
	@NotNull
	private Region region;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Region getCountry() {
		return region;
	}

	public void setCountry(Region region) {
		this.region = region;
	}
}
