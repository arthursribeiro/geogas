package org.project.geogas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class GetAllStationsTask implements Runnable {

	private final AllStations allStations;
	
	public GetAllStationsTask(AllStations all) {
		this.allStations = all;
	}
	
	@Override
	public void run() {
		String dataFromServer = doRequest();
		allStations.setDataText(dataFromServer);
	}

	private String doRequest() {
		String result = allStations.getResources().getString(R.string.fetch_error);
		
		HttpURLConnection con = null;
		try {
			URL url = new URL("http://174.123.19.114:8080/onibuzz-server/consulta?twitter_account=arthursribeiro&query=futebol&language=Portugues&location=Campina+Grande&search_type=4");
			con = (HttpURLConnection) url.openConnection();
			con.setReadTimeout(10000 /* milliseconds */);
			con.setConnectTimeout(15000 /* milliseconds */);
			con.setRequestMethod("GET" );
			con.setDoInput(true);
			con.connect();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(con.getInputStream(), "UTF-8" ));
			String payload = reader.readLine();
			reader.close();
			
			result = payload;
		} catch (Exception e) {
			Log.e("ERROR", e.getMessage());
		}
		
		return result;
	}

}
