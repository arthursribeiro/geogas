package br.edu.ufcg.geogas.bean.anp.parser;

public class ConstantsTagsANP {
	
	public static final String URL_ANP_POSTOS_SEARCH = "http://www.anp.gov.br/postos/consulta.asp";
	public static final String URL_ANP_POSTOS_RESULT = "http://www.anp.gov.br/postos/resultado.asp";
	
	public static final String SELECT_END_TAG = "</select";
	
	public static final String FONT_TAG = "<font";
	public static final String CLOSE_FONT_TAG = "</font";
	
	public static final String OPTION_TAG = "<option";
	
	public static final String VALUE_OPTION = "value";
	public static final String NAME_OPTION = "name";
	public static final String ID_OPTION = "id";
	public static final String ON_CLICK_OPTION = "onClick";
	
	public static final String ESTADO_SEARCH_ATT = "sEstado";
	public static final String ESTADO_RESULT_ATT = "estado";
	
	public static final String MUNICIPIO_SEARCH_ATT = "sMunicipio";
	public static final String MUNICIPIO_RESULT_ATT = "municipio";
	
	public static final String COD_INST_RESULT_ATT = "Cod_inst";
	
	public static final String PESQUISAR_ATT = "hPesquisar";
	
	public static final String TIPO_DE_POSTO_ATT = "sTipodePosto";
	
	public static final String BANDEIRA_ATT = "sBandeira";
	
	public static final String PAGINA_ATT = "p";
	
	public static final String FUNCTION_CALL_RESULT = "jogaform";
	
	
	//Para pag de dados dos postos
	
	public static final String RESULT_AUTORIZACAO = "Autorização";
	public static final String RESULT_CPF_CNPJ = "CNPJ/CPF";
	public static final String RESULT_RAZAO_SOCIAL = "Razão Social";
	public static final String RESULT_NOME_FANTASIA = "Nome Fantasia";
	public static final String RESULT_ENDERECO = "Endereço";
	public static final String RESULT_COMPLEMENTO_END = "Complemento";
	public static final String RESULT_BAIRRO = "Bairro";
	public static final String RESULT_MUNICIPIO_UF = "Município/UF";
	public static final String RESULT_CEP = "CEP";
	public static final String RESULT_NUM_DESPACHO = "Número Despacho";
	public static final String RESULT_DATA_PUBLICACAO = "Data Publicação";
	public static final String RESULT_BANDEIRA = "Bandeira";
	public static final String RESULT_TIPO_POSTO = "Tipo do Posto";
	
	
	public static final String URL_GEOCODING_GOOGLE_MAPS = "http://maps.googleapis.com/maps/api/geocode/xml?sensor=false";
	public static final String GOOGLEMAPS_LOCATION_TAG = "<location>";
	public static final String GOOGLEMAPS_END_LOCATION_TAG = "</location>";
	public static final String GOOGLEMAPS_GEOMETRY_TAG = "<geometry>";
	public static final String GOOGLEMAPS_END_GEOMETRY_TAG = "</geometry>";
	public static final String GOOGLEMAPS_LATITUDE_TAG = "<lat>";
	public static final String GOOGLEMAPS_LONGITUDE_TAG = "<lng>";
	
	public static final String GOOGLEMAPS_SRID = "4326";
		
}
