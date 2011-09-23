package br.edu.ufcg.geogas.bean.googlemaps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import br.edu.ufcg.geogas.bean.PostoCombustivel;
import br.edu.ufcg.geogas.bean.anp.parser.ConstantsTagsANP;

public class GeocodingGMaps {
	
	public ArrayList<String> geocodingEstado(ArrayList<PostoCombustivel> postosEstado) {
		try{
			ArrayList<String> updates = new ArrayList<String>();
			for (PostoCombustivel gasStation : postosEstado) {
				String url = ConstantsTagsANP.URL_GEOCODING_GOOGLE_MAPS;
//				url+="&address="+gasStation.getAddressForGMaps();
				URL geocoding = new URL(url);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(
								geocoding.openStream()));

				String inputLine;
				boolean locationOptions = false;
				boolean geometryOptions = false;
				
				String lat = "";
				String longi = "";
				
				while ((inputLine = in.readLine()) != null){
					if(inputLine.indexOf(ConstantsTagsANP.GOOGLEMAPS_GEOMETRY_TAG)>=0){
						geometryOptions = true;
					}
					
					if(inputLine.indexOf(ConstantsTagsANP.GOOGLEMAPS_LOCATION_TAG)>=0){
						locationOptions = true;
					}
					
					if(geometryOptions && locationOptions){
						if(inputLine.indexOf(ConstantsTagsANP.GOOGLEMAPS_LATITUDE_TAG)>=0){
							int indexLat = inputLine.indexOf(ConstantsTagsANP.GOOGLEMAPS_LATITUDE_TAG);
							String part = inputLine.substring(indexLat+2);
							int i1 = part.indexOf(">");
							int i2 = part.indexOf("<");
							lat = part.substring(i1+1,i2);
						}
						if(inputLine.indexOf(ConstantsTagsANP.GOOGLEMAPS_LONGITUDE_TAG)>=0){
							int indexLongi = inputLine.indexOf(ConstantsTagsANP.GOOGLEMAPS_LONGITUDE_TAG);
							String part = inputLine.substring(indexLongi+2);
							int i1 = part.indexOf(">");
							int i2 = part.indexOf("<");
							longi = part.substring(i1+1,i2);
						}
					}
					
					if(inputLine.indexOf(ConstantsTagsANP.GOOGLEMAPS_END_LOCATION_TAG)>=0){
						locationOptions = false;
					}
					
					if(inputLine.indexOf(ConstantsTagsANP.GOOGLEMAPS_END_GEOMETRY_TAG)>=0){
						geometryOptions = false;
					}
				}
				if(longi!=null && longi.length()>0 && lat!=null && lat.length()>0){
					String query = "UPDATE GasStation g SET longitude = "+longi+" , latitude="+lat+" WHERE g.id = "+gasStation.getId_posto_combustivel();
					updates.add(query);
				}
			}
			return updates;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
}
