package br.edu.ufcg.geogas.dao;

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
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ArrayList<GasStation> getAllGasStations() {
		ArrayList<GasStation> postos = new ArrayList<GasStation>();
		Query q = getEntityManager().createQuery("SELECT g.id, g.name, g.address, g.priceGasoline, g.priceGas, g.priceDiesel, g.priceAlcohol, g.latitude, g.longitude FROM GasStation g");
		List<Object> stations = q.getResultList();
		for (Object station : stations) {
			if(station instanceof Object[]){
				Object[] values = (Object[]) station;
				GasStation g = new GasStation();
				if(values[0]!=null && values[0] instanceof Integer)
					g.setId((Integer) values[0]);
				if(values[1]!=null && values[1] instanceof String)
					g.setName((String) values[1]);
				if(values[2]!=null && values[2] instanceof String)
					g.setAddress((String) values[2]);
				if(values[3]!=null && values[3] instanceof Double)
					g.setPriceGasoline((Double) values[3]);
				if(values[4]!=null && values[4] instanceof Double)
					g.setPriceGas((Double) values[4]);
				if(values[5]!=null && values[5] instanceof Double)
					g.setPriceDiesel((Double) values[5]);
				if(values[6]!=null && values[6] instanceof Double)
					g.setPriceAlcohol((Double) values[6]);
				if(values[7]!=null && values[7] instanceof Double)
					g.setLatitude((Double) values[7]);
				if(values[8]!=null && values[8] instanceof Double)
					g.setLongitude((Double) values[8]);
				postos.add(g);
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
}
