package br.edu.ufcg.geogas.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufcg.geogas.bean.GasStation;

public class GasStationDAO {
	
	protected EntityManager em;
	
	private final Integer SRID = 29100;
	
	private final Class<GasStation> CLASSE = GasStation.class;

	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveGasStation(GasStation gasStation) {
		try{
			if(getEntityManager().contains(gasStation)){
				getEntityManager().merge(gasStation);
			}else{
				getEntityManager().persist(gasStation);
				if(gasStation.getId() != null){
					String query = "UPDATE GasStation g SET location = GeomFromText('POINT("+gasStation.getLongitude()+" "+gasStation.getLatitude()+")', "+SRID+") WHERE g.id = "+gasStation.getId();
					Query q = getEntityManager().createQuery(query);
					q.executeUpdate();
				}
			}
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public GasStation getGasStationById(Integer intId) {
		GasStation v = null;
		v = getEntityManager().find(CLASSE,intId);
		return v;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateGasStation(GasStation v) {
		getEntityManager().merge(v);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteGasStationById(Long id){
		GasStation v = getEntityManager().find(CLASSE, id);
		getEntityManager().remove(v);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ArrayList<GasStation> getAllGasStations() {
		ArrayList<GasStation> postos = new ArrayList<GasStation>();
		Query q = getEntityManager().createQuery("SELECT g FROM GasStation g WHERE geom IS NOT NULL");
		List<Object> stations = q.getResultList();
		for (Object station : stations) {
			if(station instanceof GasStation){
				postos.add((GasStation)station);
			}
		}
		return postos;
	}
	
	@PersistenceContext()
	public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    protected EntityManager getEntityManager() {
        return em;
    }
    
    //SELECT * FROM GasStation WHERE INTERSECTS(geom,GeomFromText('POLYGON(("+longMin+" "+latMin+","+longMin+" "+latMax+","+longMax+" "+latMax+","+longMax+" "+latMin+","+longMin+" "+latMin"+"))',4326))
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ArrayList<GasStation> getAllGasStationsByBBox(String longMin,String latMin,String longMax,String latMax) {
		String sql = "SELECT * FROM GasStation g WHERE INTERSECTS(geom,GeomFromText('POLYGON(("+longMin+" "+latMin+","+longMin+" "+latMax+","+longMax+" "+latMax+","+longMax+" "+latMin+","+longMin+" "+latMin+"))',4326))";
		ArrayList<GasStation> postos = new ArrayList<GasStation>();
		Query q = getEntityManager().createNativeQuery(sql, GasStation.class);
		List<Object> stations = q.getResultList();
		for (Object station : stations) {
			if(station instanceof GasStation){
				postos.add((GasStation)station);
			}
		}
		return postos;
	}
    
    
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ArrayList<GasStation> getAllGasStationsByState(String estado) {
		String sql = "SELECT g FROM GasStation g WHERE g.municipioUF LIKE '%"+estado+"'";
		ArrayList<GasStation> postos = new ArrayList<GasStation>();
		Query q = getEntityManager().createQuery(sql);
		List<Object> stations = q.getResultList();
		for (Object station : stations) {
			if(station instanceof GasStation){
				postos.add((GasStation)station);
			}
		}
		return postos;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void runUpdates(ArrayList<String> updates) {
		for (String query : updates) {
			Query q = getEntityManager().createQuery(query);
			q.executeUpdate();
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<String> getStatesByName() {
		ArrayList<String> states = new ArrayList<String>();
		String sql = "SELECT SUBSTRING (municipioUF FROM '..$') FROM GasStation GROUP BY SUBSTRING (municipioUF FROM '..$') ORDER BY SUBSTRING (municipioUF FROM '..$')";
		Query q = getEntityManager().createNativeQuery(sql);
		List<Object> stations = q.getResultList();
		for (Object object : stations) {
			if(object instanceof String) states.add((String)object);
		}
		return states;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<String> getCityByState(String state) {
		ArrayList<String> states = new ArrayList<String>();
		String sql = "SELECT municipioUF FROM GasStation GROUP BY municipioUF HAVING municipioUF LIKE '%"+state+"'  ORDER BY municipioUF";
		Query q = getEntityManager().createNativeQuery(sql);
		List<Object> stations = q.getResultList();
		for (Object object : stations) {
			if(object instanceof String){
				String mun = (String) object;
				int index = mun.indexOf("/");
				mun = mun.substring(0, index);
				states.add(mun);
			}
		}
		return states;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ArrayList<GasStation> getAllGasStationsByCity(String city,
			String state) {
		ArrayList<GasStation> postos = new ArrayList<GasStation>();
		Query q = getEntityManager().createQuery("SELECT g FROM GasStation g WHERE geom IS NOT NULL AND municipioUF LIKE '"+city+"_"+state+"'");
		List<Object> stations = q.getResultList();
		for (Object station : stations) {
			if(station instanceof GasStation){
				postos.add((GasStation)station);
			}
		}
		return postos;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public GasStation getGasStationByCnpj(String id) {
		Query q = getEntityManager().createQuery("SELECT g FROM GasStation g WHERE cnpjcpf = '"+id+"'");
		List<Object> stations = q.getResultList();
		for (Object station : stations) {
			if(station instanceof GasStation){
				return (GasStation) station;
			}
		}
		return null;
	}
}
