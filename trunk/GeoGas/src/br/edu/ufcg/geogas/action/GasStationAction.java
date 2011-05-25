package br.edu.ufcg.geogas.action;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
	
	private String city;
	
	private final String geoserverUrl = "http://buchada.dsc.ufcg.edu.br/geoserver";
	
	public void getStates(){
		DataOutputStream dos = null;
		try{
			List<String> g = getGasStationDAO().getStatesByName();
			
			String xml = mountXMLStates(g);
			this.response = ServletActionContext.getResponse();
			dos = new DataOutputStream(response.getOutputStream());
			dos.write(xml.getBytes());
			dos.close();
		}catch(Exception e){
			//				return ERROR;
		}
	}
	
	public void getStatesXML(){
		DataOutputStream dos = null;
		try{
			List<String> g = getGasStationDAO().getStatesByName();
			
			String xml = mountXMLStates(g);
			this.response = ServletActionContext.getResponse();
			this.response.setContentType("text/xml");
			dos = new DataOutputStream(response.getOutputStream());
			dos.write(xml.getBytes());
			dos.close();
		}catch(Exception e){
			//				return ERROR;
		}
	}
	
	public void getCities(){
		if(state!=null){
			DataOutputStream dos = null;
			try{
				List<String> g = getGasStationDAO().getCityByState(state);
				HashMap<String,List<String>> mapa = new HashMap<String, List<String>>();
				mapa.put(state, g);
				String xml = mountXMLStates(mapa);
				this.response = ServletActionContext.getResponse();
				dos = new DataOutputStream(response.getOutputStream());
				dos.write(xml.getBytes());
				dos.close();
			}catch(Exception e){
				//				return ERROR;
			}
		}
	}
	
	public void getCitiesXML(){
		if(state!=null){
			DataOutputStream dos = null;
			try{
				List<String> g = getGasStationDAO().getCityByState(state);
				HashMap<String,List<String>> mapa = new HashMap<String, List<String>>();
				mapa.put(state, g);
				String xml = mountXMLStates(mapa);
				this.response = ServletActionContext.getResponse();
				this.response.setContentType("text/xml");
				dos = new DataOutputStream(response.getOutputStream());
				dos.write(xml.getBytes());
				dos.close();
			}catch(Exception e){
				//				return ERROR;
			}
		}
	}
	
	private String mountXMLStates(List<String> g) {
		String xml = "<places>";
		for (String string : g) {
			String tag = "<state nameState=\""+string+"\"></state>";
			xml+=tag;
		}
		
		xml+="</places>";
		return xml;
	}
	
	private String mountXMLStates(HashMap<String,List<String>> g) {
		String xml = "<places>";
		for (String string : g.keySet()) {
			String tag = "<state nameState=\""+string+"\">";
			List<String> muns = g.get(string);
			for (String string2 : muns) {
				String tagMun = "<city nameMun=\""+string2+"\"></city>";
				tag += tagMun;
			}
			tag+="</state>";
			xml+=tag;
		}
		
		xml+="</places>";
		return xml;
	}

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
		String places = "<placemark>\n";
//		String places = "";
		for (GasStation gasStation : stations) {
			places+="\n"+gasStation.toXML();
		}
		places+= "\n</placemark>";
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
	
	public void getAllGasStationsByCity(){
		if(state!=null && city!=null){
			ArrayList<GasStation> stations = getGasStationDAO().getAllGasStationsByCity(city,state);
			String places = "<placemark>\n";
//			String places = "";
			for (GasStation gasStation : stations) {
				places+="\n"+gasStation.toXML();
			}
			places+= "\n</placemark>";
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
	}
	
	public void getAllGasStationsByState(){
		if(state!=null){
			ArrayList<GasStation> stations = getGasStationDAO().getAllGasStationsByState(state);
			String places = "<placemark>\n";
//			String places = "";
			for (GasStation gasStation : stations) {
				places+="\n"+gasStation.toXML();
			}
			places+= "\n</placemark>";
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
	}
	
	public void geocodingState(){
//		String[] states = {"AC","AL","RN","SE"};
//		for (String state1 : states) {
//			ArrayList<GasStation> stations = getGasStationDAO().getPostosEstado(state1);
//			GeocodingGMaps geo = new GeocodingGMaps();
//			ArrayList<String> updates = geo.geocodingEstado(stations);
//			getGasStationDAO().runUpdates(updates);
//		}
		if(state!=null){
			ArrayList<GasStation> stations = getGasStationDAO().getAllGasStationsByState(state);
			GeocodingGMaps geo = new GeocodingGMaps();
			ArrayList<String> updates = geo.geocodingEstado(stations);
			getGasStationDAO().runUpdates(updates);
		}
	}
	
	private InputStream getKmlInputStringFromUrl(String url) throws IOException{
		URL urlCon = new URL(url);
		URLConnection uc = urlCon.openConnection();
		return uc.getInputStream();
	}
	
	public void redirectGeoServer(){
		if(state==null && city==null){
			state = "PB";
			city = "JOAO PESSOA";
		}
		if(state!=null && city!=null){
			state = state.replace(" ", "%20");
			String url = this.geoserverUrl+"/wms?request=GetMap&version=1.1.1&srs=EPSG:4326&width=2096&height=2096&bbox=-180,-90,180,90&format_options=SUPEROVERLAY:false;KMPLACEMARK:false;KMSCORE:40;KMATTR:true;&layers=gasstation&Format=application/vnd.google-earth.kml+xml&cql_Filter=municipiouf%20=%20%27"+city+"/"+state+"%27";
			this.response = ServletActionContext.getResponse();
			try {
				this.response.setContentType("application/vnd.google-earth.kml+xml");
				this.response.sendRedirect(this.response.encodeRedirectURL(url));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void giveGasStationsKML(){
		try {
			if(state==null && city==null){
				state = "PB";
				city = "JOAO PESSOA";
			}
			if(state!=null && city!=null){
				state = state.replace(" ", "%20");
				city = city.replace(" ", "%20");
				String url = this.geoserverUrl+"/wms?request=GetMap&version=1.1.1&srs=EPSG:4326&width=2096&height=2096&bbox=-180,-90,180,90&format_options=SUPEROVERLAY:false;KMPLACEMARK:false;KMSCORE:40;KMATTR:true;&layers=gasstation&Format=application/vnd.google-earth.kml+xml&cql_Filter=municipiouf%20=%20%27"+city+"/"+state+"%27";
				InputStream is = getKmlInputStringFromUrl(url);
				Kml kml = Kml.unmarshal(is);
				
//				fixKml(kml);
				
				this.response = ServletActionContext.getResponse();
				this.response.setContentType("application/vnd.google-earth.kml+xml");
				ServletOutputStream dos = response.getOutputStream();
				kml.marshal(dos);
				dos.flush();
				dos.close();
				is.close();
			}
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
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
