package br.edu.ufcg.geogas.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ufcg.geogas.bean.AvaliacaoANP;
import br.edu.ufcg.geogas.bean.Denuncia;
import br.edu.ufcg.geogas.bean.Entidade;
import br.edu.ufcg.geogas.bean.Historico_Precos_Anp;
import br.edu.ufcg.geogas.bean.Historico_Precos_Usuario;
import br.edu.ufcg.geogas.bean.PostoCombustivel;
import br.edu.ufcg.geogas.bean.Usuario;


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

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ArrayList<String> getBasicColumns(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public HashMap<String, Object> getEntity(int id, String typeEntity) {
		Query q = getEntityManager().createQuery("SELECT g FROM ?1 g WHERE id = ?2");
		q.setParameter(2, id);
		q.setParameter(1, typeEntity);
		try{
			Entidade station = (Entidade) q.getSingleResult();
			return station.getEntHashMap();
		}catch(Exception e){
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public HashMap<String,Object> getPricesByGasStationId(int id, HashMap<String,Object> ret) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updatePrices(int id, double pricegasoline,
			double pricegasoline_user, double pricealcohol,
			double pricealcohol_user, double pricediesel,
			double pricediesel_user, double pricegas, double pricegas_user, boolean isAnp, String idUser) {
		if(isAnp){
			Historico_Precos_Anp h = new Historico_Precos_Anp();
			h.setId_posto_combustivel(id);
			if(pricealcohol>0)
			h.setPricealcohol(pricealcohol);
			if(pricediesel>0)
			h.setPricediesel(pricediesel);
			if(pricegas>0)
			h.setPricegas(pricegas);
			if(pricegasoline>0)
			h.setPricegasoline(pricegasoline);
			h.setData(new Date());
			
			try{
				getEntityManager().persist(h);
				getEntityManager().merge(h);
			}catch(Throwable t){
				t.printStackTrace();
			}
		}
		else if(!isAnp && idUser.length()>0){
			Historico_Precos_Usuario hu = new Historico_Precos_Usuario();
			hu.setData(new Date());
			hu.setId_posto_combustivel(id);
			hu.setId_usuario(idUser);
			if(pricealcohol_user>0)
			hu.setPricealcohol(pricealcohol_user);
			if(pricediesel_user>0)
			hu.setPricediesel(pricediesel_user);
			if(pricegas_user>0)
			hu.setPricegas(pricegas_user);
			if(pricegasoline_user>0)
			hu.setPricegasoline(pricegasoline_user);
			getEntityManager().persist(hu);
		}
		
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Integer createUsuario(Usuario u) {
		getEntityManager().persist(u);
		getEntityManager().merge(u);
		return 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Usuario findUsuario(String facebook_id) {
		Query q = getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.facebook_id = "+facebook_id);
		Usuario u = null;
		try{
			u = (Usuario) q.getSingleResult();
		}catch(Exception e){
			
		}
		return u;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void mergeObject(Object u) {
		getEntityManager().merge(u);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveAutuacao(AvaliacaoANP a) {
		getEntityManager().persist(a);
		getEntityManager().merge(a);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveDenuncia(Denuncia d) {
		getEntityManager().persist(d);
		getEntityManager().merge(d);
		
	}
}
