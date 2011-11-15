package br.edu.ufcg.geogas.action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ufcg.geogas.bean.Usuario;
import br.edu.ufcg.geogas.dao.GasStationDAOFlex;
import br.edu.ufcg.geogas.dao.GasStationDAOIF;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class GasStationAction  extends ActionSupport{
	
	@Autowired
	private GasStationDAOIF gasStationDAO;
	
	private HttpServletResponse response;
	
	private final String geoserverUrl = "http://buchada.dsc.ufcg.edu.br/geoserver";
	
	public int id;
	public String pricegasoline = "-1";
	public String pricegasoline_user = "-1";
	public String pricealcohol = "-1";
	public String pricealcohol_user = "-1";
	public String pricediesel = "-1"; 
	public String pricediesel_user = "-1";
	public String pricegas = "-1";
	public String pricegas_user = "-1";
	public boolean isAnp = false; 
	public Integer idUser = -1;
	
	
	public String nome;
	public String facebook_id;
	public Integer cpf;
	public Integer idade;
	public String chave_facebook;

	private boolean coordValid(double longiMin, double latiMin) {
		if(longiMin<=180 && longiMin>=-180){
			if(latiMin<=90 && latiMin>=-90){
				return true;
			}
		}
		return false;
	}
	
	public void updatePrices(){
		try{
			gasStationDAO.updatePrices(id, Double.parseDouble(pricegasoline), Double.parseDouble(pricegasoline_user),
					Double.parseDouble(pricealcohol),Double.parseDouble(pricealcohol_user),
					Double.parseDouble(pricediesel), Double.parseDouble(pricediesel_user),
					Double.parseDouble(pricegas), Double.parseDouble(pricegas_user),isAnp,idUser);
		}catch(Exception e){
			
		}
	}
	
	public Integer createUsuario(){
		Usuario u = gasStationDAO.findUsuario(facebook_id);
		if(u==null){
			u = new Usuario();
			u.setChave_facebook(chave_facebook);
			u.setCpf(cpf);
			u.setFacebook_id(facebook_id);
			u.setIdade(idade);
			u.setNome(nome);
			gasStationDAO.createUsuario(u);
		}else{
			u.setChave_facebook(chave_facebook);
			u.setCpf(cpf);
			u.setFacebook_id(facebook_id);
			u.setIdade(idade);
			u.setNome(nome);
			gasStationDAO.mergeObject(u);
		}
		
		return u.getId_usuario();
	}
	
	public GasStationDAOIF getGasStationDAO() {
		return gasStationDAO;
	}

	public void setGasStationDAO(GasStationDAOIF gasStationDAO) {
		this.gasStationDAO = gasStationDAO;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}
