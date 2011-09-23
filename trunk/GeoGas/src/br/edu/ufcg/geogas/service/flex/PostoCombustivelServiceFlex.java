package br.edu.ufcg.geogas.service.flex;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;

import br.edu.ufcg.geogas.dao.GasStationDAOIF;
import br.edu.ufcg.geogas.service.PostoCombustivelServiceIF;

@RemotingDestination(value="PostoCombustivelService")
public class PostoCombustivelServiceFlex implements PostoCombustivelServiceIF{
	
	@Autowired
	private GasStationDAOIF gasStationDAOFlex;
	
	public String olaJava(String nome) {
		return "Olá Mundo! Olá "+nome;
	}
	
	public String encodeUrl(String url,String filters) throws UnsupportedEncodingException{
		return url+URLEncoder.encode(filters, "UTF-8");
	}
	
	public Dictionary<String, String> getTraducoes(){
		return gasStationDAOFlex.getTraducoes(1);
	}

	public GasStationDAOIF getGasStationDAOFlex() {
		return gasStationDAOFlex;
	}

	public void setGasStationDAOFlex(GasStationDAOIF gasStationDAO) {
		this.gasStationDAOFlex = gasStationDAO;
	}
	
}
