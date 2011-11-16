package br.edu.ufcg.geogas.action;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ufcg.geogas.bean.AvaliacaoANP;
import br.edu.ufcg.geogas.bean.AvaliacaoANP_PK;
import br.edu.ufcg.geogas.bean.PostoCombustivel;
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
	public String cpf;
	public String data_nascimento;
	public String chave_facebook;
	
	public String nomeFantasia;
	public String bandeira;
	public String cnpjCpf;
	public String autorizacao;
	public String numeroDespacho;
	
	public String autuacao;

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
	
	public void updateData(){
		if(id>0){
			PostoCombustivel p = gasStationDAO.getGasStationById(id);
			if(p!=null){
				if(nomeFantasia != null && nomeFantasia.length()>0)
					p.setNomeFantasia(nomeFantasia);
				if(bandeira != null && bandeira.length()>0)
					p.setBandeira(bandeira);
				if(cnpjCpf != null && cnpjCpf.length()>0)
					p.setCnpjCpf(cnpjCpf);
				if(autorizacao != null && autorizacao.length()>0)
					p.setAutorizacao(autorizacao);
				if(numeroDespacho != null && numeroDespacho.length()>0)
					p.setNumeroDespacho(numeroDespacho);
				
				gasStationDAO.mergeObject(p);
			}
		}
	}
	
	public void createUsuario() throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");
		Usuario u = gasStationDAO.findUsuario(facebook_id);
		if(u==null){
			u = new Usuario();
			if(chave_facebook!=null && chave_facebook.length()>0)
				u.setChave_facebook(chave_facebook);
			if(cpf!=null && cpf.length()>0)
				u.setCpf(cpf);
			u.setFacebook_id(facebook_id);
			if(data_nascimento!=null&&data_nascimento.length()>0)
				u.setData_nascimento(sf.parse(data_nascimento));
			u.setNome(nome);
			gasStationDAO.createUsuario(u);
		}else{
			if(chave_facebook!=null && chave_facebook.length()>0)
				u.setChave_facebook(chave_facebook);
			if(cpf!=null && cpf.length()>0)
				u.setCpf(cpf);
			if(facebook_id!=null && facebook_id.length()>0)
				u.setFacebook_id(facebook_id);
			if(data_nascimento!=null&&data_nascimento.length()>0)
				u.setData_nascimento(sf.parse(data_nascimento));
			if(chave_facebook!=null && chave_facebook.length()>0)
				u.setNome(nome);
			gasStationDAO.mergeObject(u);
		}
	}
	
	public void autuar(){
		if(id>0){
			AvaliacaoANP a = new AvaliacaoANP();
			AvaliacaoANP_PK id_avaliacao_anp = new AvaliacaoANP_PK();
			id_avaliacao_anp.setData(new Date());
			id_avaliacao_anp.setId_posto_combustivel(id);
			a.setId_avaliacao_anp(id_avaliacao_anp);
			a.setAvaliacao(autuacao);
			
			gasStationDAO.saveAutuacao(a);
		}
	}
	
	public void denunciar(){
		if(id>0){
			
		}
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
