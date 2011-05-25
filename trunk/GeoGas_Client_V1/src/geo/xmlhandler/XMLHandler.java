package geo.xmlhandler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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

public class XMLHandler{
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	public XMLHandler() throws ParserConfigurationException{
		dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
	}
	
	public void loadXMLfromURL(String url) throws SAXException, IOException{
		URL path = new URL(url);
		doc = db.parse(new InputSource(path.openStream()));
	}
	public void loadXMLfromString(String contentText) throws SAXException, IOException{
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contentText.getBytes("UTF-8"));
		doc = db.parse(byteArrayInputStream);
	}
	public void loadXMLfromFile(File f) throws SAXException, IOException{
		doc = db.parse(f);
	}
	public ArrayList<String> getElementByState(){
		NodeList lista = doc.getElementsByTagName("state");
		ArrayList<String> result = new ArrayList<String>();
		
		for(int i = 0; i < lista.getLength() ; i++){
			Node node = lista.item(i);
			Element fstElmnt = (Element) node;
			result.add(fstElmnt.getAttribute("nameState"));
		}
		return result;
	}
	
	public ArrayList<String> getElementByCity(String state){
		NodeList lista = doc.getElementsByTagName("state");
		ArrayList<String> result = new ArrayList<String>();
		
		for(int i = 0; i < lista.getLength() ; i++){
			Node node = lista.item(i);
			Element fstElmnt = (Element) node;
			String estado = fstElmnt.getAttribute("nameState");
			if(estado != null && state.equalsIgnoreCase(estado)){
				NodeList cities = fstElmnt.getElementsByTagName("city");
				Element city = (Element) cities.item(0);
				result.add(city.getAttribute("nameMun"));
			}
		}
		return result;
	}
}
