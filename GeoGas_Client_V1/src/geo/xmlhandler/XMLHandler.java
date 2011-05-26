package geo.xmlhandler;

import geo.data.Place;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
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
//		String result = doRequest(url);
//		loadXMLfromString(result);
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
		doc = db.parse(f);
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
			Log.i("Latitude", latitude+" "+longitude);
			result.add(new Place(latitude, longitude));
		}
		return result;
	}
}
