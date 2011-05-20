package br.edu.ufcg.geogas.bean.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


public class ExcelHandler {
	
	private static HashMap<String,ArrayList<String>> estadosCodMun = new HashMap<String,ArrayList<String>>();
	
	private static HashMap< String, HashMap<String,ArrayList<String>>  > estadosMunPostos = new HashMap< String, HashMap<String,ArrayList<String>>  >();
	
	private static ArrayList<String> tiposDePostos = new ArrayList<String>();
	
	public static void main(String[] args) {
		try {
			URL anp = new URL(ConstantsTagsANP.URL_ANP_POSTOS_SEARCH);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							anp.openStream()));

			String inputLine;
			
			boolean optionsEstado = false;
			ArrayList<String> keys = new ArrayList<String>();
			
			boolean tiposPostos = false;
			
			while ((inputLine = in.readLine()) != null){
				if(inputLine.indexOf(ConstantsTagsANP.NAME_OPTION+"=\""+ConstantsTagsANP.ESTADO_SEARCH_ATT+"\"")>=0 || 
						inputLine.indexOf(ConstantsTagsANP.ID_OPTION+"=\""+ConstantsTagsANP.ESTADO_SEARCH_ATT+"\"")>=0 ){
					optionsEstado = true;
				}else if(inputLine.indexOf(ConstantsTagsANP.NAME_OPTION+"=\""+ConstantsTagsANP.TIPO_DE_POSTO_ATT+"\"")>=0 || 
						inputLine.indexOf(ConstantsTagsANP.ID_OPTION+"=\""+ConstantsTagsANP.TIPO_DE_POSTO_ATT+"\"")>=0 ){
					tiposPostos = true;
				}else if(inputLine.indexOf(ConstantsTagsANP.SELECT_END_TAG)>=0){
					optionsEstado = false;
					tiposPostos = false;
				}
				
				if(optionsEstado){
					ArrayList<String> optionsValues = getOptionsValues(inputLine);
					keys.addAll(optionsValues);
				}else if(tiposPostos){
					ArrayList<String> optionsValues = getOptionsValues(inputLine);
					tiposDePostos.addAll(optionsValues);
				}
			}

			in.close();
			
			getMunCodes(keys);
			
			for (String estado : keys) {
				ArrayList<String> muns = estadosCodMun.get(estado);
				for (String municipio : muns) {
					getMunPostos(estado,municipio);
				}
			}
			//Dentro do for nos arrays
//			String dataEst = URLEncoder.encode("sEstado", "UTF-8") + "=" + URLEncoder.encode("PB", "UTF-8");
//		    
//		    String dataMun = "&" + URLEncoder.encode("sMunicipio", "UTF-8") + "=" + URLEncoder.encode("4848", "UTF-8");
//		    
//		    
//		    String dataPesq = "&" + URLEncoder.encode("hPesquisar", "UTF-8") + "=" + URLEncoder.encode("PESQUISAR", "UTF-8");
//		    String dataTipo = "&" + URLEncoder.encode("sTipodePosto", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
//		    String dataBand = "&" + URLEncoder.encode("sBandeira", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
//		    String dataPag = "&" + URLEncoder.encode("p", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
		} catch (Exception e) {
		}
	}
	

	private static void getMunPostos(String estado, String municipio) {
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
	        
	        boolean callFunctionPostos = false;
			ArrayList<String> postos = new ArrayList<String>();
	        
	        while ((inputLine = rd.readLine()) != null) {  
	        	if(inputLine.indexOf(ConstantsTagsANP.ON_CLICK_OPTION+"=")>=0 || 
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
	        wr.close();  
	        rd.close();
			
		}catch(Exception e){
		}
	}


	private static ArrayList<String> getPostoId(String inputLine) {
		// TODO Auto-generated method stub
		return null;
	}


	private static void getMunCodes(ArrayList<String> keys) {
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
				for (String mun : municipios) {
					HashMap<String,ArrayList<String>> aux = new HashMap<String, ArrayList<String>>();
					ArrayList<String> auxList = new ArrayList<String>();
					aux.put(mun, auxList);
					estadosMunPostos.put(estado, aux);
				}
			}catch(Exception e){
			}
		}
	}

	private static ArrayList<String> getOptionsValues(String inputLine) {
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

	private void realizaPrimeiraBusca(String data) {
		try{
			URL url = new URL(ConstantsTagsANP.URL_ANP_POSTOS_SEARCH);
//		    URL url = new URL("http://localhost:8080/geogas");
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.write(data);
		    wr.flush();

		    // Get the response
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    while ((line = rd.readLine()) != null) {
		        System.out.println(line);
		    }
		    wr.close();
		    rd.close();
		}catch(Exception e){
		}
	}
}
