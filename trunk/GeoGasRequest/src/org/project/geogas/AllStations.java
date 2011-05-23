package org.project.geogas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class AllStations extends Activity {

	private TextView dadosGUI;
	
	private Handler guiThread;
	private ExecutorService transThread;
	private Runnable updateTask;
	private Future transPending;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_stations);
		
		getGUIElements();
		
		dadosGUI.setText(R.string.loading);
		
		initThreading();
		
		guiThread.removeCallbacks(updateTask);
		
    	guiThread.postDelayed(updateTask, 300);
	}
	
	public void getGUIElements() {
		dadosGUI = (TextView) findViewById(R.id.dataTextView);
	}
	
	public void initThreading() {
		guiThread = new Handler();
		transThread = Executors.newSingleThreadExecutor();
		
		updateTask = new Runnable() {
			
			@Override
			public void run() {
				if (transPending != null)
    				transPending.cancel(true);
				
				try {
					GetAllStationsTask allStationsTask = new GetAllStationsTask(AllStations.this);
					transPending = transThread.submit(allStationsTask);
				} catch (RejectedExecutionException e) {
					// Unable to start new task
					dadosGUI.setText(R.string.fetch_error);
					dadosGUI.setText(R.string.fetch_error);
				}
				
			}
		};
	}
	
	private void guiSetText(final TextView view, final String text) {
    	guiThread.post(new Runnable() {
    		public void run() {
    			view.setText(text);
    		}
    	});
    }
	
	public void setDataText(String text) {
		guiSetText(dadosGUI, text);
	}
	
}
