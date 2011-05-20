package br.edu.ufcg.geogas.action;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import br.edu.ufcg.geogas.bean.GasStation;
import br.edu.ufcg.geogas.dao.GasStationDAO;

import com.opensymphony.xwork2.ActionSupport;

public class GasStationAction  extends ActionSupport{
	
	private GasStationDAO gasStationDAO;
	
	private HttpServletResponse response;
	
	private String id;
	
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

}
