package br.edu.ufcg.geogas.bean.anp.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PricesANP {

	private ArrayList<String> estados = new ArrayList<String>();
	private ArrayList<String> combs = new ArrayList<String>();
	
	public static void main(String[] args) {
		PricesANP handler = new PricesANP();
		handler.buscaDados();
	}

	public void buscaDados(){
		try {
			URL anp = new URL(ConstantsPricesANP.URL_ANP_PRICES_SEARCH_BEGIN);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							anp.openStream()));

			String inputLine;
			HashMap<String, String> attsValues = new HashMap<String, String>();
			
			while ((inputLine = in.readLine()) != null){
				getAttValue(inputLine, attsValues);
				ArrayList<String> estados = getSelectValues(ConstantsPricesANP.SELECT_ATT_SELESTADO,inputLine);
				if(estados!=null){
					this.estados = estados;
				}
				ArrayList<String> combustiveis = getSelectValues(ConstantsPricesANP.SELECT_ATT_SELCOMBUSTIVEL,inputLine);
				if(combustiveis!=null){
					this.combs = combustiveis;
				}
			}
			in.close();
			
			if(this.estados.size()>0 && this.combs.size()>0 && attsValues.keySet().size()>0){
				getMunsList(attsValues);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getMunsList(HashMap<String, String> attsValues) {
		String data = "";
		String sep = "&";
		
		URL url;
		
		out:for (String estado : this.estados) {
			for (String comb : this.combs) {
				try {
					if(comb.indexOf("GLP")<0){
						data="";
						data+=URLEncoder.encode(ConstantsPricesANP.SELECT_ATT_SELESTADO,"UTF-8")+"="+URLEncoder.encode(estado,"UTF-8");
						data+=sep+URLEncoder.encode(ConstantsPricesANP.SELECT_ATT_SELCOMBUSTIVEL, "UTF-8")+"="+URLEncoder.encode(comb, "UTF-8");
						for (String key : attsValues.keySet()) {
							data+=sep+URLEncoder.encode(key,"UTF-8")+"="+URLEncoder.encode(attsValues.get(key),"UTF-8");
						}
						url = new URL(ConstantsPricesANP.URL_ANP_PRICES_SEARCH_MUNS);
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
						readMunResponse(allLines,comb,estado);
					}
				} catch (MalformedURLException e) {
//					e.printStackTrace();
				} catch (IOException e) {
//					e.printStackTrace();
				}  
			}
		}
	}

	private void readMunResponse(String allLines, String comb, String estado) {
		HashMap<String, String> attsValues = new HashMap<String, String>();
		
		getInputValue(ConstantsPricesANP.INPUT_MUN_ATT_DESCSEMANA,attsValues,allLines);
		getInputValue(ConstantsPricesANP.INPUT_MUN_ATT_CODSEMANA,attsValues,allLines);
		getInputValue(ConstantsPricesANP.INPUT_MUN_ATT_TIPO,attsValues,allLines);
		getInputValue(ConstantsPricesANP.INPUT_MUN_ATT_CODCOMBUSTIVEL,attsValues,allLines);
		getInputValue(ConstantsPricesANP.INPUT_MUN_ATT_DESCCOMBUSTIVEL,attsValues,allLines);
		getInputValue(ConstantsPricesANP.INPUT_MUN_ATT_SELMUNICIPIO,attsValues,allLines);
		
		ArrayList<String> muns = getMunsValues(allLines);
		for (String mun : muns) {
			getPricesMun(mun,attsValues,comb,estado);
		}
	}

	private void getPricesMun(String mun, HashMap<String, String> attsValues, String comb, String estado) {
		String data = "";
		String sep = "&";
		
		URL url;
		try {
			data+=URLEncoder.encode(ConstantsPricesANP.INPUT_MUN_ATT_SELMUNICIPIO, "UTF-8")+"="+URLEncoder.encode(mun, "UTF-8");
			for (String key : attsValues.keySet()) {
				data+=sep+URLEncoder.encode(key,"UTF-8")+"="+URLEncoder.encode(attsValues.get(key),"UTF-8");
			}
			url = new URL(ConstantsPricesANP.URL_ANP_PRICE_MUN);
			URLConnection conn = url.openConnection();  
			conn.setDoOutput(true);  
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());  
			wr.write(data);  
			wr.flush();  

			// Get the response  
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String inputLine;
			

			while ((inputLine = rd.readLine()) != null){
				readMunPrices(inputLine,comb,estado);
			}
			wr.close();  
			rd.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private void readMunPrices(String inputLine, String comb, String estado) {
		String data = Calendar.getInstance().getTime().toString();
		String[] elems = data.split(" ");
		String ano = elems[elems.length-1];
		ArrayList<String> celsVals = new ArrayList<String>();
		ArrayList<ArrayList<String>> tabela = new ArrayList<ArrayList<String>>();
		if(inputLine.indexOf(ConstantsPricesANP.INPUT_MUN_HREF_MUNICIPIO)>=0){
			String[] cels = inputLine.split("</td>");
			for (String string : cels) {
				int i = string.lastIndexOf(">");
				int j = string.indexOf(ConstantsPricesANP.INPUT_MUN_HREF_MUNICIPIO);
				if(i>=0 || j>=0){
					String val = "";
					if(i>=0){
						 val = string.substring(i+1);
					}
					if(j>=0){
						int openP = string.indexOf("(",j);
						if(openP>=0){
							int closeP = string.indexOf(")",j);
							if(openP < closeP-1){
								val = string.substring(openP+2,closeP-1);
								val = val.replaceAll("@", "_");
							}
						}
					}
					val = val.replaceAll("\'", "\'\'");
					if(val.length()>0){
						celsVals.add(val);
						if(val.endsWith("/"+ano)){
							tabela.add(celsVals);
							celsVals = new ArrayList<String>();
						}
					}
				}
			}
			writeUpdates(tabela,comb,estado);
		}
	}

	private void writeUpdates(ArrayList<ArrayList<String>> tabela, String comb, String estado) {
		for (ArrayList<String> arrayList : tabela) {
			String razaoSocial = arrayList.get(0);
			String bairro = arrayList.get(2);
			String preco = arrayList.get(4);
			estado = estado.substring(0,2);
			System.out.println("UPDATE GasStation SET <"+comb+"> = "+preco.replace(",",".")+" WHERE razaosocial LIKE '"+razaoSocial.toUpperCase()+"' AND bairro LIKE '"+bairro+"' AND municipiouf LIKE '%/"+estado+"';");
		}
	}

	private ArrayList<String> getMunsValues(String allLines) {
		String[] splitOptions = allLines.split(ConstantsPricesANP.INPUT_MUN_HREF_MUNICIPIO);
		ArrayList<String> values = new ArrayList<String>();
		for (int i = 0; i < splitOptions.length; i++) {
			String part = splitOptions[i];
			if(part.trim().startsWith("(") && part.indexOf(")")>0){
				int openP = part.indexOf("(");
				int closeP = part.indexOf(")");
				try{
					String conteudo = part.substring(openP+2,closeP-1);
					if(conteudo.trim().length()>0){
						conteudo = conteudo.replace("&nbsp;", "");
						values.add(conteudo);
					}
					part = part.substring(closeP+1);
				}catch(Exception e){
					continue;
				}
			}
		}
		return values;
	}

	private void getAttValue(String inputLine,
			HashMap<String, String> attsValues) {
		getInputValue(ConstantsPricesANP.INPUT_ATT_SELSEMANA,attsValues,inputLine);
		getInputValue(ConstantsPricesANP.INPUT_ATT_DESCSEMANA,attsValues,inputLine);
		getInputValue(ConstantsPricesANP.INPUT_ATT_CODSEMANA,attsValues,inputLine);
		getInputValue(ConstantsPricesANP.INPUT_ATT_TIPO,attsValues,inputLine);
		getInputValue(ConstantsPricesANP.INPUT_ATT_CODCOMBUSTIVEL,attsValues,inputLine);
		
		
	}

	private ArrayList<String> getSelectValues(String selectAttSelestado, String inputLine) {
		int i = inputLine.indexOf(selectAttSelestado);
		if(i>=0){
			if(inputLine.indexOf(ConstantsPricesANP.TAG_NAME,i-7)>=0){
				return getOptionsValues(inputLine);
			}
		}
		return null;
	}
	
	private ArrayList<String> getOptionsValues(String inputLine) {
		String[] splitOptions = inputLine.split(ConstantsTagsANP.OPTION_TAG);
		ArrayList<String> values = new ArrayList<String>();
		for (int i = 1; i < splitOptions.length; i++) {
			String part = splitOptions[i];
			int valueIndex = part.indexOf(ConstantsTagsANP.VALUE_OPTION);
			if(valueIndex>=0){
				String conteudo = getTagValue(part);
				part = part.substring(valueIndex);
				conteudo = conteudo.replace("&nbsp;", "");
				if(conteudo.trim().length()>0){
					values.add(conteudo);
				}
			}
		}
		return values;
	}

	private void getInputValue(String inputAttSelsemana,
			HashMap<String, String> attsValues, String inputLine) {
		int i = inputLine.indexOf(inputAttSelsemana);
		if(i-7>=0){
			if(inputLine.indexOf(ConstantsPricesANP.TAG_NAME+"=", i-7)>=0){
				String value = getTagValue(inputLine.substring(i-7));
				attsValues.put(inputAttSelsemana, value);
			}
		}
	}

	private String getTagValue(String inputLine) {
		String value = "";
		String searchThis = ConstantsPricesANP.TAG_VALUE+"=";
		int i = inputLine.indexOf(searchThis);
		int j = i+searchThis.length();
		String delimitador = ""+inputLine.charAt(j);
		int jFim = inputLine.indexOf(delimitador,j+1);
		value = (inputLine.substring(j+1, jFim));
		return value;
	}
}
