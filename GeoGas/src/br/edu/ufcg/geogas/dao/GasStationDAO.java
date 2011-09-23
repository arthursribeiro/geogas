package br.edu.ufcg.geogas.dao;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufcg.geogas.bean.PostoCombustivel;


public class GasStationDAO implements GasStationDAOIF{
	
	protected EntityManager em;
	
	private final Integer SRID = 29100;
	
	private final Class<PostoCombustivel> CLASSE = PostoCombustivel.class;

	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveGasStation(PostoCombustivel gasStation) {
		try{
			if(getEntityManager().contains(gasStation)){
				getEntityManager().merge(gasStation);
			}else{
				getEntityManager().persist(gasStation);
				if(gasStation.getId_posto_combustivel() != null){
					String query = "UPDATE GasStation g SET location = GeomFromText('POINT("+gasStation.getLongitude()+" "+gasStation.getLatitude()+")', "+SRID+") WHERE g.id = "+gasStation.getId_posto_combustivel();
					Query q = getEntityManager().createQuery(query);
					q.executeUpdate();
				}
			}
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public PostoCombustivel getGasStationById(Integer intId) {
		PostoCombustivel v = null;
		v = getEntityManager().find(CLASSE,intId);
		return v;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateGasStation(PostoCombustivel v) {
		getEntityManager().merge(v);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteGasStationById(Long id){
		PostoCombustivel v = getEntityManager().find(CLASSE, id);
		getEntityManager().remove(v);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ArrayList<PostoCombustivel> getAllGasStations() {
		ArrayList<PostoCombustivel> postos = new ArrayList<PostoCombustivel>();
		Query q = getEntityManager().createQuery("SELECT g FROM GasStation g WHERE geom IS NOT NULL");
		List<Object> stations = q.getResultList();
		for (Object station : stations) {
			if(station instanceof PostoCombustivel){
				postos.add((PostoCombustivel)station);
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
	public ArrayList<PostoCombustivel> getAllGasStationsByBBox(String longMin,String latMin,String longMax,String latMax) {
		String sql = "SELECT * FROM GasStation g WHERE INTERSECTS(geom,GeomFromText('POLYGON(("+longMin+" "+latMin+","+longMin+" "+latMax+","+longMax+" "+latMax+","+longMax+" "+latMin+","+longMin+" "+latMin+"))',4326))";
		ArrayList<PostoCombustivel> postos = new ArrayList<PostoCombustivel>();
		Query q = getEntityManager().createNativeQuery(sql, PostoCombustivel.class);
		List<Object> stations = q.getResultList();
		for (Object station : stations) {
			if(station instanceof PostoCombustivel){
				postos.add((PostoCombustivel)station);
			}
		}
		return postos;
	}
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Dictionary<String, String> getTraducoes(int lingua){
    	Dictionary<String, String> dic = new Hashtable<String, String>();
    	String sql = "SELECT * FROM Traducao WHERE id_lingua = "+lingua;
    	Query q = getEntityManager().createNativeQuery(sql);
    	for (Object result : q.getResultList()) {
    		Object[] tupla = (Object[]) result;
			String doBanco = (String) tupla[0];
			String traducao = (String) tupla[1];
			
			dic.put(doBanco, traducao);
		}
    	return dic;
    }
    
    
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ArrayList<PostoCombustivel> getAllGasStationsByState(String estado) {
		String sql = "SELECT g FROM GasStation g WHERE g.municipioUF LIKE '%"+estado+"'";
		ArrayList<PostoCombustivel> postos = new ArrayList<PostoCombustivel>();
		Query q = getEntityManager().createQuery(sql);
		List<Object> stations = q.getResultList();
		for (Object station : stations) {
			if(station instanceof PostoCombustivel){
				postos.add((PostoCombustivel)station);
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
	public ArrayList<PostoCombustivel> getAllGasStationsByCity(String city,
			String state) {
		ArrayList<PostoCombustivel> postos = new ArrayList<PostoCombustivel>();
		Query q = getEntityManager().createQuery("SELECT g FROM GasStation g WHERE geom IS NOT NULL AND municipioUF LIKE '"+city+"_"+state+"'");
		List<Object> stations = q.getResultList();
		for (Object station : stations) {
			if(station instanceof PostoCombustivel){
				postos.add((PostoCombustivel)station);
			}
		}
		return postos;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public PostoCombustivel getGasStationByCnpj(String id) {
		Query q = getEntityManager().createQuery("SELECT g FROM GasStation g WHERE cnpjcpf = '"+id+"'");
		List<Object> stations = q.getResultList();
		for (Object station : stations) {
			if(station instanceof PostoCombustivel){
				return (PostoCombustivel) station;
			}
		}
		return null;
	}
}