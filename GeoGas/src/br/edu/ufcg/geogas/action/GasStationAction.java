package br.edu.ufcg.geogas.action;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ufcg.geogas.bean.AvaliacaoANP;
import br.edu.ufcg.geogas.bean.AvaliacaoANP_PK;
import br.edu.ufcg.geogas.bean.Avaliacao_Entidade_Usuario;
import br.edu.ufcg.geogas.bean.Avaliacao_Entidade_Usuario_PK;
import br.edu.ufcg.geogas.bean.Denuncia;
import br.edu.ufcg.geogas.bean.Entidade;
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
	public String idUser = "";
	
	
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
	public String nota;

	private boolean coordValid(double longiMin, double latiMin) {
		if(longiMin<=180 && longiMin>=-180){
			if(latiMin<=90 && latiMin>=-90){
				return true;
			}
		}
		return false;
	}
	
	private void resetParameters(){
		id = 0;
		
		
		pricegasoline = "-1";
		pricegasoline_user = "-1";
		pricealcohol = "-1";
		pricealcohol_user = "-1";
		pricediesel = "-1"; 
		pricediesel_user = "-1";
		pricegas = "-1";
		pricegas_user = "-1";
		isAnp = false; 
		idUser = "";
		
		
		nome = "";
		facebook_id = "";
		cpf = "";
		data_nascimento = "";
		chave_facebook = "";
		
		nomeFantasia = "";
		bandeira = "";
		cnpjCpf = "";
		autorizacao = "";
		numeroDespacho = "";
		
		autuacao = "";
		nota = "";
	}
	
	public void updatePrices(){
		try{
			gasStationDAO.updatePrices(id, Double.parseDouble(pricegasoline), Double.parseDouble(pricegasoline_user),
					Double.parseDouble(pricealcohol),Double.parseDouble(pricealcohol_user),
					Double.parseDouble(pricediesel), Double.parseDouble(pricediesel_user),
					Double.parseDouble(pricegas), Double.parseDouble(pricegas_user),isAnp,idUser);
		}catch(Exception e){
			
		}
		resetParameters();
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
		resetParameters();
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
		resetParameters();
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
		resetParameters();
	}
	
	public void denunciar(){
		if(id>0 && idUser.length()>0){
			Denuncia d = new Denuncia();
			d.setId_usuario(idUser);
			d.setReclamacao(autuacao);
			PostoCombustivel p = gasStationDAO.getGasStationById(id);
			if(p!=null){
				if(d.getEntidades()==null){
					d.setEntidades(new TreeSet<Entidade>());
				}
				gasStationDAO.saveDenuncia(d);
				d.getEntidades().add((Entidade) p);
				gasStationDAO.mergeObject(d);
			}
		}
		resetParameters();
	}
	
	public void avaliar(){
		if(id>0 && idUser.length()>0){
			Avaliacao_Entidade_Usuario aval = null;
			aval = gasStationDAO.findAvaliacaoUsuario(""+id,idUser);
			if(aval==null){
				aval = new Avaliacao_Entidade_Usuario();
				aval.setId(new Avaliacao_Entidade_Usuario_PK());
			}
			aval.getId().setId_entidade(id);
			aval.getId().setId_usuario(idUser);
			aval.setData(new Date());
			aval.setNota(Integer.parseInt(nota));
			gasStationDAO.saveAvaliacao(aval);
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
