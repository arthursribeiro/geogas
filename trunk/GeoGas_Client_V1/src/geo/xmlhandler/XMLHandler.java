package geo.xmlhandler;

import geo.data.Place;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class XMLHandler {
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;

	public XMLHandler() throws ParserConfigurationException {
		dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
	}

	private String doRequest(String ul) {

		HttpURLConnection con = null;
		String result = null;
		try {
			URL url = new URL(ul);
			con = (HttpURLConnection) url.openConnection();
			con.setReadTimeout(10000 /* milliseconds */);
			con.setConnectTimeout(15000 /* milliseconds */);
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String payload = reader.readLine();
			reader.close();

			result = payload;
		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
		}

		return result;
	}

	public void loadXMLfromURL(String u) throws SAXException, IOException {
		// String result = doRequest(url);
		// loadXMLfromString(result);
		URL url = new URL(u);
		doc = db.parse(new InputSource(url.openStream()));
	}

	public void loadXMLfromString(String contentText) throws SAXException,
			IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				contentText.getBytes());
		doc = db.parse(byteArrayInputStream);
	}

	public void loadXMLfromFile(File f) throws SAXException, IOException {
		FileInputStream byteArrayInputStream = new FileInputStream(f);
		doc = db.parse(byteArrayInputStream);
	}

	public ArrayList<String> getElementByState() {
		NodeList lista = doc.getElementsByTagName("state");
		ArrayList<String> result = new ArrayList<String>();

		for (int i = 0; i < lista.getLength(); i++) {
			Node node = lista.item(i);
			Element fstElmnt = (Element) node;
			result.add(fstElmnt.getAttribute("nameState"));
		}
		return result;
	}

	public ArrayList<String> getElementByCity(String state) {
		NodeList lista = doc.getElementsByTagName("state");
		ArrayList<String> result = new ArrayList<String>();

		for (int i = 0; i < lista.getLength(); i++) {
			Node node = lista.item(i);
			Element fstElmnt = (Element) node;
			String estado = fstElmnt.getAttribute("nameState");
			if (estado != null && state.equalsIgnoreCase(estado)) {
				NodeList cities = fstElmnt.getElementsByTagName("city");
				Element city = (Element) cities.item(0);
				result.add(city.getAttribute("nameMun"));
			}
		}
		return result;
	}

	public ArrayList<Place> getElementByPlace() {
		NodeList lista = doc.getElementsByTagName("location");
		ArrayList<Place> result = new ArrayList<Place>();

		for (int i = 0; i < lista.getLength(); i++) {
			Node node = lista.item(i);
			Element fstElmnt = (Element) node;
			String latitude = fstElmnt.getAttribute("latitude");
			String longitude = fstElmnt.getAttribute("longitude");
			String validade = fstElmnt.getAttribute("valido");
			NodeList cities = fstElmnt.getElementsByTagName("name");
			Element name = (Element) cities.item(0);

			String posto_name = name.getAttribute("value");
			NodeList preco_gasolina = fstElmnt
					.getElementsByTagName("priceGasoline");
			Element preco_gasolina1 = (Element) preco_gasolina.item(0);
			String string_gasolina = preco_gasolina1.getAttribute("value");

			NodeList preco_gas = fstElmnt.getElementsByTagName("priceGas");
			Element preco_gas1 = (Element) preco_gas.item(0);
			String string_gas = preco_gas1.getAttribute("value");

			NodeList preco_disel = fstElmnt.getElementsByTagName("priceDiesel");
			Element preco_disel1 = (Element) preco_disel.item(0);
			String string_disel = preco_disel1.getAttribute("value");

			NodeList preco_alcool = fstElmnt
					.getElementsByTagName("priceAlcohol");
			Element preco_alcool1 = (Element) preco_alcool.item(0);
			String string_alcool = preco_alcool1.getAttribute("value");

			Log.i("Latitude", latitude + " " + longitude);
			// result.add(new Place(latitude,
			// longitude,validade,posto_name,string_gasolina,string_gas,string_disel,string_alcool));
		}
		return result;
	}

	public ArrayList<Place> getElementByPlaceBB() {
		NodeList lista = doc.getElementsByTagName("geogas:gasstation");
		ArrayList<Place> result = new ArrayList<Place>();
		for (int i = 0; i < lista.getLength(); i++) {
			Log.i("GeoGas", "Começo");
			Node node = lista.item(i);
			Element fstElmnt = (Element) node;

			String lat = getElementByTag("geogas:latitude", fstElmnt);
			String longi = getElementByTag("geogas:longitude", fstElmnt);
			String val = getElementByTag("geogas:autuacoes", fstElmnt);
			String denun = getElementByTag("geogas:denuncias", fstElmnt);
			String posto_name = getElementByTag("geogas:nomefantasia", fstElmnt);
			String bandeira = getElementByTag("geogas:bandeira", fstElmnt);
			String endereco = getElementByTag("geogas:endereco", fstElmnt)
					+ " " + getElementByTag("geogas:complemento", fstElmnt)
					+ " " + getElementByTag("geogas:municipiouf", fstElmnt)
					+ " " + getElementByTag("geogas:cep", fstElmnt);
			String gasolina = getElementByTag("geogas:pricegasoline", fstElmnt);
			String diesel = getElementByTag("geogas:pricediesel", fstElmnt);
			String alcohol = getElementByTag("geogas:pricealcohol", fstElmnt);
			String gas = getElementByTag("geogas:pricegas", fstElmnt);
			Log.i("Place", lat + " " + longi + " " + gasolina + " " + diesel + " " + alcohol + " " + gas);
			Log.i("GeoGas", lat + " " + longi + " " + posto_name + " " + val);
			Log.i("GeoGas", "fim");
			/*
			 * NodeList preco_gasolina =
			 * fstElmnt.getElementsByTagName("priceGasoline"); Element
			 * preco_gasolina1 = (Element) preco_gasolina.item(0); String
			 * string_gasolina = preco_gasolina1.getAttribute("value");
			 * 
			 * NodeList preco_gas = fstElmnt.getElementsByTagName("priceGas");
			 * Element preco_gas1 = (Element) preco_gas.item(0); String
			 * string_gas = preco_gas1.getAttribute("value");
			 * 
			 * NodeList preco_disel =
			 * fstElmnt.getElementsByTagName("priceDiesel"); Element
			 * preco_disel1 = (Element) preco_disel.item(0); String string_disel
			 * = preco_disel1.getAttribute("value");
			 * 
			 * NodeList preco_alcool =
			 * fstElmnt.getElementsByTagName("priceAlcohol"); Element
			 * preco_alcool1 = (Element) preco_alcool.item(0); String
			 * string_alcool = preco_alcool1.getAttribute("value");
			 */
			result.add(new Place(lat, longi, val,denun, posto_name, gasolina, gas,
					diesel, alcohol, bandeira, endereco));

		}
		return result;
	}

	private String getElementByTag(String tag, Element raiz) {
		String lat = "";
		NodeList latitude = raiz.getElementsByTagName(tag);
		if (latitude != null) {
			Element latElement = (Element) latitude.item(0);
			if (latElement != null) {
				latitude = latElement.getChildNodes();
				Node aux = ((Node) latitude.item(0));
				if (aux != null) {
					lat = aux.getNodeValue();
				}
			}
		}
		return lat;
	}
}
