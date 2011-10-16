package br.edu.ufcg.geogas.service.flex;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;

import br.edu.ufcg.geogas.bean.PostoCombustivel;
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
	
	public ArrayList<String> getBasicColumns(String tableName){
		ArrayList<String> columns = new ArrayList<String>();
		columns = getGasStationDAOFlex().getBasicColumns(tableName);
		return columns;
	}

	public GasStationDAOIF getGasStationDAOFlex() {
		return gasStationDAOFlex;
	}

	public void setGasStationDAOFlex(GasStationDAOIF gasStationDAO) {
		this.gasStationDAOFlex = gasStationDAO;
	}
	
	public HashMap<String, Object> getAllEntityData(int id, String typeEntity){
		HashMap<String, Object> ret = new HashMap<String, Object>();
		typeEntity = typeEntity.replaceAll(" ", "");
		if(typeEntity.toLowerCase().startsWith("postodecombust")){
			PostoCombustivel p = this.gasStationDAOFlex.getGasStationById(id);
			ret = p.getHashMap();
			
			ret = this.gasStationDAOFlex.getPricesByGasStationId(id,ret);
			
		}else
			ret = this.gasStationDAOFlex.getEntity(id,typeEntity);
		return ret;
	}
	
}
