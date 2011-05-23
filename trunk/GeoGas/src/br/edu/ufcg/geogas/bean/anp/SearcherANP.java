package br.edu.ufcg.geogas.bean.anp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import br.edu.ufcg.geogas.bean.GasStation;


public class SearcherANP {
	
	private HashMap<String,ArrayList<String>> estadosCodMun = new HashMap<String,ArrayList<String>>();
	
	private HashMap< String, HashMap<String,ArrayList<String>>  > estadosMunPostos = new HashMap< String, HashMap<String,ArrayList<String>>  >();
	
	
	public static void main(String[] args) {
		SearcherANP handler = new SearcherANP();
		handler.buscaDados();
	}
	
	public void buscaDados(){
		try {
			URL anp = new URL(ConstantsTagsANP.URL_ANP_POSTOS_SEARCH);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							anp.openStream()));

			String inputLine;
			
			boolean optionsEstado = false;
			ArrayList<String> keys = new ArrayList<String>();
			
			while ((inputLine = in.readLine()) != null){
				if(inputLine.indexOf(ConstantsTagsANP.NAME_OPTION+"=\""+ConstantsTagsANP.ESTADO_SEARCH_ATT+"\"")>=0 || 
						inputLine.indexOf(ConstantsTagsANP.ID_OPTION+"=\""+ConstantsTagsANP.ESTADO_SEARCH_ATT+"\"")>=0 ){
					optionsEstado = true;
				}else if(inputLine.indexOf(ConstantsTagsANP.SELECT_END_TAG)>=0){
					optionsEstado = false;
				}
				
				if(optionsEstado){
					ArrayList<String> optionsValues = getOptionsValues(inputLine);
					keys.addAll(optionsValues);
				}
			}

			in.close();
			
			getMunCodes(keys);
			
			getAllGasStationData(keys);
			
		} catch (Exception e) {
		}
	}
	
	private void getAllGasStationData(ArrayList<String> keys) {
		for (String estado : keys) {
			ArrayList<String> muns = estadosCodMun.get(estado);
			for (String municipio : muns) {
				getMunPostos(estado,municipio);
				HashMap<String, ArrayList<String>> munPostos = estadosMunPostos.get(estado);
				if(munPostos!=null){
					ArrayList<String> postos = munPostos.get(municipio);
					for (String posto : postos) {
						getDadosPostos(estado, municipio, posto);
					}
				}
			}
		}
	}

	private void getDadosPostos(String estado, String municipio, String postoS){
		String autorizacao = "";
		String cnpjCpf = "";
		String razaoSocial = "";
		String nomeFantasia = "";
		String endereco = "";
		String complemento = "";
		String bairro = "";
		String municipioUF = "";
		String cep = "";
		String numeroDespacho = ""; 
		String dataPublicacao = "";
		String bandeira = "";
		String tipoPosto = "";
		
		try{
			
			String data = URLEncoder.encode(ConstantsTagsANP.ESTADO_RESULT_ATT, "UTF-8") + "=" + URLEncoder.encode(estado, "UTF-8");  
			data += "&" + URLEncoder.encode(ConstantsTagsANP.MUNICIPIO_RESULT_ATT, "UTF-8") + "=" + URLEncoder.encode(municipio, "UTF-8");
			data += "&" + URLEncoder.encode(ConstantsTagsANP.COD_INST_RESULT_ATT, "UTF-8") + "=" + URLEncoder.encode(postoS, "UTF-8");
			
			URL url = new URL(ConstantsTagsANP.URL_ANP_POSTOS_RESULT);  
			URLConnection conn = url.openConnection();  
			conn.setDoOutput(true);  
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());  
			wr.write(data);  
			wr.flush();  

			// Get the response  
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String inputLine;
			
			String allLines = "";

			while ((inputLine = rd.readLine()) != null){
				allLines += " "+inputLine+" ";
			}
			
			wr.close();  
			rd.close();
			
			String aux = getResultValue(ConstantsTagsANP.RESULT_AUTORIZACAO,allLines);
			if(aux!=null) autorizacao = aux.replace("&nbsp;", "").replace("\'", "\'\'").replace("\\", "/"); 
			
			aux = getResultValue(ConstantsTagsANP.RESULT_BAIRRO, allLines);
			if(aux!=null) bairro = aux.trim().replace("&nbsp;", "").replace("\'", "\'\'").replace("\\", "/");
			
			aux = getResultValue(ConstantsTagsANP.RESULT_BANDEIRA, allLines);
			if(aux!=null) bandeira = aux.trim().replace("&nbsp;", "").replace("\'", "\'\'").replace("\\", "/"); 
			
			aux = getResultValue(ConstantsTagsANP.RESULT_CEP, allLines);
			if(aux!=null) cep = aux.trim().replace("&nbsp;", "").replace("\'", "\'\'").replace("\\", "/");
			
			aux = getResultValue(ConstantsTagsANP.RESULT_COMPLEMENTO_END, allLines);
			if(aux!=null) complemento = aux.trim().replace("&nbsp;", "").replace("\'", "\'\'").replace("\\", "/");
			
			aux = getResultValue(ConstantsTagsANP.RESULT_CPF_CNPJ, allLines);
			if(aux!=null) cnpjCpf = aux.trim().replace("&nbsp;", "").replace("\'", "\'\'").replace("\\", "/"); 
			
			aux = getResultValue(ConstantsTagsANP.RESULT_DATA_PUBLICACAO, allLines);
			if(aux!=null) dataPublicacao = aux.trim().replace("&nbsp;", "").replace("\'", "\'\'").replace("\\", "/");
			
			aux = getResultValue(ConstantsTagsANP.RESULT_ENDERECO, allLines);
			if(aux!=null) endereco = aux.trim().replace("&nbsp;", "").replace("\'", "\'\'").replace("\\", "/"); 
			
			aux = getResultValue(ConstantsTagsANP.RESULT_MUNICIPIO_UF, allLines);
			if(aux!=null) municipioUF = aux.trim().replace("&nbsp;", "").replace("\'", "\'\'").replace("\\", "/");
			
			aux = getResultValue(ConstantsTagsANP.RESULT_NOME_FANTASIA, allLines);
			if(aux!=null) nomeFantasia = aux.trim().replace("&nbsp;", "").replace("\'", "\'\'").replace("\\", "/");
			
			aux = getResultValue(ConstantsTagsANP.RESULT_NUM_DESPACHO, allLines);
			if(aux!=null) numeroDespacho = aux.trim().replace("&nbsp;", "").replace("\'", "\'\'").replace("\\", "/");
			
			aux = getResultValue(ConstantsTagsANP.RESULT_RAZAO_SOCIAL, allLines);
			if(aux!=null) razaoSocial = aux.trim().replace("&nbsp;", "").replace("\'", "\'\'").replace("\\", "/");
			
			aux = getResultValue(ConstantsTagsANP.RESULT_TIPO_POSTO, allLines);
			if(aux!=null) tipoPosto = aux.trim().replace("&nbsp;", "").replace("\'", "\'\'").replace("\\", "/"); 
			
			GasStation posto = new GasStation();
			posto.setAutorizacao(autorizacao);
			posto.setBairro(bairro);
			posto.setBandeira(bandeira);
			posto.setCep(cep);
			posto.setComplemento(complemento);
			posto.setDataPublicacao(dataPublicacao);
			posto.setEndereco(endereco);
			posto.setMunicipioUF(municipioUF);
			posto.setNomeFantasia(nomeFantasia);
			posto.setNumeroDespacho(numeroDespacho);
			posto.setRazaoSocial(razaoSocial);
			posto.setTipoPosto(tipoPosto);
			
			if(cnpjCpf!=null && cnpjCpf.length()>0 && endereco!=null && endereco.length()>0 && municipioUF!=null && municipioUF.length()>0){
				posto.setCnpjCpf(cnpjCpf);
				
				String insert = "INSERT INTO GasStation(autorizacao,bairro,bandeira,cep,cnpjCpf,complemento,dataPublicacao,endereco,municipioUF,nomeFantasia,numeroDespacho,razaoSocial,tipoPosto) VALUES(";
				
				if(posto.getAutorizacao()!=null) autorizacao = posto.getAutorizacao();
				insert+="'"+autorizacao+"',";
				
				if(posto.getBairro()!=null) bairro = posto.getBairro();
				insert+="'"+bairro+"',";
				
				if(posto.getBandeira()!=null) bandeira = posto.getBandeira();
				insert+="'"+bandeira+"',";
				
				if(posto.getCep()!=null) cep = posto.getCep();
				insert+="'"+cep+"',";
				
				if(posto.getCnpjCpf()!=null) cnpjCpf = posto.getCnpjCpf();
				insert+="'"+cnpjCpf+"',";
				
				if(posto.getComplemento()!=null) complemento = posto.getComplemento();
				insert+="'"+complemento+"',";
				
				if(posto.getDataPublicacao()!=null) dataPublicacao = posto.getDataPublicacao();
				insert+="'"+dataPublicacao+"',";
				
				if(posto.getEndereco()!=null) endereco = posto.getEndereco();
				insert+="'"+endereco+"',";
				
				if(posto.getMunicipioUF()!=null) municipioUF = posto.getMunicipioUF();
				insert+="'"+municipioUF+"',";
				
				if(posto.getNomeFantasia()!=null) nomeFantasia = posto.getNomeFantasia();
				insert+="'"+nomeFantasia+"',";
				
				if(posto.getNumeroDespacho()!=null) numeroDespacho = posto.getNumeroDespacho();
				insert+="'"+numeroDespacho+"',";
				
				if(posto.getRazaoSocial()!=null) razaoSocial = posto.getRazaoSocial();
				insert+="'"+razaoSocial+"',";
				
				if(posto.getTipoPosto()!=null) tipoPosto = posto.getTipoPosto();
				insert+="'"+tipoPosto+"')";
				
				System.out.println(insert);
			}
			
		}catch(Exception e){
			
		}
		
	}
	

	private String getResultValue(String att, String inputLine) {
		int index = inputLine.indexOf(att);
		if(index>=0){
			String input2 = inputLine.substring(index+1);;
			int indexNextFont = input2.indexOf(ConstantsTagsANP.FONT_TAG);
			if(indexNextFont>=0){
				String input3 = input2.substring(indexNextFont+ConstantsTagsANP.FONT_TAG.length());
				int closeOpenTag = input3.indexOf(">");
				int closeTag = input3.indexOf(ConstantsTagsANP.CLOSE_FONT_TAG);
				if(closeOpenTag>=0 && closeTag>=0){
					String value = input3.substring(closeOpenTag+1,closeTag);
					return value;
				}
			}
		}
		return null;
	}

	private void getMunPostos(String estado, String municipio) {
		try{
			int numResults = 0;
			int pag = 1;
			while(numResults>0 || pag<2){
				String data = URLEncoder.encode(ConstantsTagsANP.ESTADO_SEARCH_ATT, "UTF-8") + "=" + URLEncoder.encode(estado, "UTF-8");  
				data += "&" + URLEncoder.encode(ConstantsTagsANP.MUNICIPIO_SEARCH_ATT, "UTF-8") + "=" + URLEncoder.encode(municipio, "UTF-8");
				
				data += "&" + URLEncoder.encode("hPesquisar", "UTF-8") + "=" + URLEncoder.encode("PESQUISAR", "UTF-8");
				data += "&" + URLEncoder.encode("sTipodePosto", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
			    data += "&" + URLEncoder.encode("sBandeira", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
			    data += "&" + URLEncoder.encode("p", "UTF-8") + "=" + URLEncoder.encode(""+pag, "UTF-8");
				
		        // Send data  
		        URL url = new URL(ConstantsTagsANP.URL_ANP_POSTOS_SEARCH);  
		        URLConnection conn = url.openConnection();  
		        conn.setDoOutput(true);  
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());  
		        wr.write(data);  
		        wr.flush();  
		      
		        // Get the response  
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
		        String inputLine;  
		        
		        boolean callFunctionPostos = false;
				ArrayList<String> postos = new ArrayList<String>();
		        
		        while ((inputLine = rd.readLine()) != null) {  
		        	if(inputLine.indexOf(ConstantsTagsANP.ON_CLICK_OPTION+"=")>=0 && 
							inputLine.indexOf(ConstantsTagsANP.FUNCTION_CALL_RESULT+"(")>=0 ){
						callFunctionPostos = true;
					}else {
						callFunctionPostos = false;
					}
					
					if(callFunctionPostos){
						ArrayList<String> optionsValues = getPostoId(inputLine);
						postos.addAll(optionsValues);
					}
		        }
		        numResults = postos.size();
		        pag++;
		        HashMap<String, ArrayList<String>> munPostos = estadosMunPostos.get(estado);
		        if(munPostos==null)
		        	munPostos = new HashMap<String, ArrayList<String>>();
		        if(munPostos!=null){
		        	ArrayList<String> stations = munPostos.get(municipio);
		        	if(stations==null)
		        		stations = new ArrayList<String>();
		        	stations.addAll(postos);
		        	munPostos.put(municipio, stations);
		        	estadosMunPostos.put(estado, munPostos);
		        }
		        
		        wr.close();  
		        rd.close();
			}
		}catch(Exception e){
		}
	}


	private ArrayList<String> getPostoId(String inputLine) {
		String[] split = inputLine.split(ConstantsTagsANP.ON_CLICK_OPTION+"=");
		ArrayList<String> idsPostos = new ArrayList<String>();
		for (String part : split) {
			if(part.indexOf(ConstantsTagsANP.FUNCTION_CALL_RESULT+"(")>=0){
				int indexLeftParen = part.indexOf("(");
				int indexRightParen = part.substring(indexLeftParen+1).indexOf(")");
				if(indexLeftParen>=0 && indexRightParen>=0){
					String value = part.substring(indexLeftParen+1,indexLeftParen+indexRightParen+1);
					value = value.replace("\'", "");
					idsPostos.add(value.trim());
				}
			}
		}
		return idsPostos;
	}


	private void getMunCodes(ArrayList<String> keys) {
		for (String estado : keys) {
			try{
				String data = URLEncoder.encode(ConstantsTagsANP.ESTADO_SEARCH_ATT, "UTF-8") + "=" + URLEncoder.encode(estado, "UTF-8");  
			      
		        // Send data  
		        URL url = new URL(ConstantsTagsANP.URL_ANP_POSTOS_SEARCH);  
		        URLConnection conn = url.openConnection();  
		        conn.setDoOutput(true);  
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());  
		        wr.write(data);  
		        wr.flush();  
		      
		        // Get the response  
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
		        String inputLine;  
		        
		        boolean optionsMunicipios = false;
				ArrayList<String> municipios = new ArrayList<String>();
		        
		        while ((inputLine = rd.readLine()) != null) {  
		        	if(inputLine.indexOf(ConstantsTagsANP.NAME_OPTION+"=\""+ConstantsTagsANP.MUNICIPIO_SEARCH_ATT+"\"")>=0 || 
							inputLine.indexOf(ConstantsTagsANP.ID_OPTION+"=\""+ConstantsTagsANP.MUNICIPIO_SEARCH_ATT+"\"")>=0 ){
						optionsMunicipios = true;
					}else if(inputLine.indexOf(ConstantsTagsANP.SELECT_END_TAG)>=0){
						optionsMunicipios = false;
					}
					
					if(optionsMunicipios){
						ArrayList<String> optionsValues = getOptionsValues(inputLine);
						municipios.addAll(optionsValues);
					}
		        }  
		        wr.close();  
		        rd.close();
				
				estadosCodMun.put(estado, municipios);
			}catch(Exception e){
			}
		}
	}

	private ArrayList<String> getOptionsValues(String inputLine) {
		String[] splitOptions = inputLine.split(ConstantsTagsANP.OPTION_TAG);
		ArrayList<String> values = new ArrayList<String>();
		for (int i = 1; i < splitOptions.length; i++) {
			String part = splitOptions[i];
			int valueIndex = part.indexOf(ConstantsTagsANP.VALUE_OPTION);
			if(valueIndex>=0){
				part = part.substring(valueIndex);
				int closeTag = part.indexOf(">");
				int openNextTag = part.indexOf("<");
				String conteudo = part.substring(closeTag+1,openNextTag);
				conteudo = conteudo.replace("&nbsp;", "");
				if(conteudo.trim().length()>0){
					int indexAspas1 = part.indexOf("\"");
					int indexAspas2 = part.substring(indexAspas1+1).indexOf("\"");
					if(indexAspas1>=0 && indexAspas2>=0){
						String value = part.substring(indexAspas1+1,indexAspas1+indexAspas2+1);
						values.add(value);
					}
				}
			}
		}
		return values;
	}
}
