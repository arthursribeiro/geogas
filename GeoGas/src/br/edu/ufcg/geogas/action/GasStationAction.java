package br.edu.ufcg.geogas.action;

import javax.servlet.http.HttpServletResponse;

import br.edu.ufcg.geogas.dao.GasStationDAO;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GasStationAction  extends ActionSupport{
	
	private GasStationDAO gasStationDAO;
	
	private HttpServletResponse response;
	
	private final String geoserverUrl = "http://buchada.dsc.ufcg.edu.br/geoserver";

	private boolean coordValid(double longiMin, double latiMin) {
		if(longiMin<=180 && longiMin>=-180){
			if(latiMin<=90 && latiMin>=-90){
				return true;
			}
		}
		return false;
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
}
