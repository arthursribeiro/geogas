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
@Table(name = "GasStation")
public class GasStation {
	
	@Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "gasstation_id_seq")
    private Integer id; // surrogate key
	
	@Column(name = "name")
    @NotNull
	private String name;
	
	@Column(name = "address")
    @NotNull
	private String address;
	
	@Column(name = "pricegasoline")
	private Double priceGasoline;
	
	@Column(name = "pricegas")
	private Double priceGas;
	
	@Column(name = "priceDiesel")
	private Double priceDiesel;
	
	@Column(name = "pricealcohol")
	private Double priceAlcohol;
	
	@Column(name = "latitude")
	@NotNull
	private Double latitude;
	
	@Column(name = "longitude")
	@NotNull
	private Double longitude;
	
	public String toXML() {
		String nodeId = "<id>"+id+"</id>";
		String nodeName = "<name>"+name+"</name>";
		String nodeAddress = "<address>"+address+"</address>";
		String nodePriceGasoline = "<priceGasoline>"+(priceGasoline!=null?priceGasoline:"")+"</priceGasoline>";
		String nodePriceGas = "<priceGas>"+(priceGas!=null?priceGas:"")+"</priceGas>";
		String nodePriceDiesel = "<priceDiesel>"+(priceDiesel!=null?priceDiesel:"")+"</priceDiesel>";
		String nodePriceAlcohol = "<priceAlcohol>"+(priceAlcohol!=null?priceAlcohol:"")+"</priceAlcohol>";
		String nodeLat = "<latitude>"+latitude+"</latitude>";
		String nodeLong = "<longitude>"+longitude+"</longitude>";
		
		String data = "\n"+nodeId+"\n"+nodeName+"\n"+nodeAddress+"\n"+
						nodePriceGasoline+"\n"+nodePriceGas+"\n"+nodePriceDiesel+"\n"+nodePriceAlcohol+"\n"+
						nodeLat+"\n"+nodeLong+"\n";
		
		String xml = "<gasStation>"+data+"<gasStation>";
		return xml;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getPriceGasoline() {
		return priceGasoline;
	}

	public void setPriceGasoline(Double priceGasoline) {
		this.priceGasoline = priceGasoline;
	}

	public Double getPriceGas() {
		return priceGas;
	}

	public void setPriceGas(Double priceGas) {
		this.priceGas = priceGas;
	}

	public Double getPriceDiesel() {
		return priceDiesel;
	}

	public void setPriceDiesel(Double priceDiesel) {
		this.priceDiesel = priceDiesel;
	}

	public Double getPriceAlcohol() {
		return priceAlcohol;
	}

	public void setPriceAlcohol(Double priceAlcohol) {
		this.priceAlcohol = priceAlcohol;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
}
