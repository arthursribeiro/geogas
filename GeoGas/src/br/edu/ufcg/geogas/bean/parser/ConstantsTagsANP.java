package br.edu.ufcg.geogas.bean.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.HttpParams;
import org.springframework.web.context.request.WebRequest;

public class ConstantsTagsANP {
	
	public static final String URL_ANP_POSTOS_SEARCH = "http://www.anp.gov.br/postos/consulta.asp";
	public static final String URL_ANP_POSTOS_RESULT = "http://www.anp.gov.br/postos/resultado.asp";
	
	public static final String SELECT_END_TAG = "</select";
	
	public static final String OPTION_TAG = "<option";
	
	public static final String VALUE_OPTION = "value";
	public static final String NAME_OPTION = "name";
	public static final String ID_OPTION = "id";
	public static final String ON_CLICK_OPTION = "onClick";
	
	public static final String ESTADO_SEARCH_ATT = "sEstado";
	public static final String ESTADO_RESULT_ATT = "estado";
	
	public static final String MUNICIPIO_SEARCH_ATT = "sMunicipio";
	public static final String MUNICIPIO_RESULT_ATT = "municipio";
	
	public static final String PESQUISAR_ATT = "hPesquisar";
	
	public static final String TIPO_DE_POSTO_ATT = "sTipodePosto";
	
	public static final String BANDEIRA_ATT = "sBandeira";
	
	public static final String PAGINA_ATT = "p";
	
	public static final String FUNCTION_CALL_RESULT = "jogaForm";
		
}
