package br.edu.ufcg.geogas.action;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import br.edu.ufcg.geogas.bean.GasStation;
import br.edu.ufcg.geogas.bean.googlemaps.GeocodingGMaps;
import br.edu.ufcg.geogas.dao.GasStationDAO;

import com.opensymphony.xwork2.ActionSupport;

import de.micromata.opengis.kml.v_2_2_0.AbstractObject;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

@SuppressWarnings("serial")
public class GasStationAction  extends ActionSupport{
	
	private GasStationDAO gasStationDAO;
	
	private HttpServletResponse response;
	
	private String id;
	
	private String state;
	
	private final String geoserverUrl = "http://localhost:8080/geoserver";
	
	public void getGasStationById(){
		if(id!=null){
			DataOutputStream dos = null;
			try{
				Integer intId = new Integer(id);
				GasStation g = getGasStationDAO().getGasStationById(intId);
				String xml = g.toXML();
				this.response = ServletActionContext.getResponse();
				dos = new DataOutputStream(response.getOutputStream());
				dos.write(xml.getBytes());
				dos.close();
			}catch(Exception e){
//				return ERROR;
			}
		}
	}
	
	public void getAllGasStations(){
		ArrayList<GasStation> stations = getGasStationDAO().getAllGasStations();
		String places = "<places>";
//		String places = "";
		for (GasStation gasStation : stations) {
			places+="\n"+gasStation.toXML();
		}
		places+= "\n</places>";
		DataOutputStream dos = null;
		this.response = ServletActionContext.getResponse();
		try {
			dos = new DataOutputStream(response.getOutputStream());
			dos.write(places.getBytes());
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void geocodingState(){
		ArrayList<GasStation> stations = getGasStationDAO().getPostosEstado(state);
		GeocodingGMaps geo = new GeocodingGMaps();
		ArrayList<String> updates = geo.geocodingEstado(stations);
		getGasStationDAO().runUpdates(updates);
	}
	
	private InputStream getKmlInputStringFromUrl(String url) throws IOException{
		URL urlCon = new URL(url);
		URLConnection uc = urlCon.openConnection();
		return uc.getInputStream();
	}
	
	public void giveGasStationsKML(){
		try {
			String url = this.geoserverUrl+"/wms?request=GetMap&version=1.1.1&srs=EPSG:4326&width=2096&height=2096&bbox=-37,-8,-30,-6&format_options=SUPEROVERLAY:false;KMPLACEMARK:true;KMSCORE:40;KMATTR:true;&layers=gasstation&Format=application/vnd.google-earth.kml+xml";
			InputStream is = getKmlInputStringFromUrl(url);
			Kml kml = Kml.unmarshal(is);
			
//			fixKml(kml);
			
			this.response = ServletActionContext.getResponse();
			this.response.setContentType("application/vnd.google-earth.kml+xml");
			ServletOutputStream dos = response.getOutputStream();
			kml.marshal(dos);
			dos.flush();
			dos.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void fixKml(Kml kml) {
		Feature feature = kml.getFeature();
		List<Feature> places = ((Document)feature).getFeature();
		for (Feature feat : places) {
			if(feat instanceof Placemark){
				Placemark placemark = ((Placemark)feat);
				placemark.setDescription("Mudei");
			}
		}
	}

	public GasStationDAO getGasStationDAO() {
		return gasStationDAO;
	}

	public void setGasStationDAO(GasStationDAO gasStationDAO) {
		this.gasStationDAO = gasStationDAO;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
