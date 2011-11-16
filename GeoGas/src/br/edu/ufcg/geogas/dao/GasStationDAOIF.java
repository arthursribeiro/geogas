package br.edu.ufcg.geogas.dao;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;

import br.edu.ufcg.geogas.bean.AvaliacaoANP;
import br.edu.ufcg.geogas.bean.Denuncia;
import br.edu.ufcg.geogas.bean.PostoCombustivel;
import br.edu.ufcg.geogas.bean.Usuario;

public interface GasStationDAOIF {
	
	public void saveGasStation(PostoCombustivel gasStation);
	
	public PostoCombustivel getGasStationById(Integer intId);
	
	public void updateGasStation(PostoCombustivel v);
	
	public void deleteGasStationById(Long id);
	
	public ArrayList<PostoCombustivel> getAllGasStations();
	
	public ArrayList<PostoCombustivel> getAllGasStationsByBBox(String longMin,String latMin,String longMax,String latMax);
    
    public Dictionary<String, String> getTraducoes(int lingua);
    
	public ArrayList<PostoCombustivel> getAllGasStationsByState(String estado) ;

	public void runUpdates(ArrayList<String> updates);
	
	public List<String> getStatesByName();
	
	public List<String> getCityByState(String state) ;

	public ArrayList<PostoCombustivel> getAllGasStationsByCity(String city,
			String state);

	public PostoCombustivel getGasStationByCnpj(String id) ;

	public ArrayList<String> getBasicColumns(String tableName);

	public HashMap<String, Object> getEntity(int id, String typeEntity);

	public HashMap<String, Object> getPricesByGasStationId(int id, HashMap<String, Object> ret);

	public void updatePrices(int id, double pricegasoline,
			double pricegasoline_user, double pricealcohol,
			double pricealcohol_user, double pricediesel,
			double pricediesel_user, double pricegas, double pricegas_user, boolean isAnp, String idUser);

	public Integer createUsuario(Usuario u);

	public Usuario findUsuario(String facebook_id);

	public void mergeObject(Object u);

	public void saveAutuacao(AvaliacaoANP a);

	public void saveDenuncia(Denuncia d);
}
