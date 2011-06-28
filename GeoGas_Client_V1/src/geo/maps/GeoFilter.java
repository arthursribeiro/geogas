package geo.maps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class GeoFilter extends Activity {
	private Button my_button;
	private double lat;
	private double lng;
	private boolean sucess;
	private CheckBox preco_min;
	private CheckBox dis;
	private CheckBox bandeira;
	private CheckBox preco_max;
	private EditText value_min;
	private EditText dis_value;
	private EditText value_max;
	private EditText value_ban;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		sucess = getIntent().getExtras().getBoolean("sucess");
		lat = getIntent().getExtras().getDouble("latitude");
		lng = getIntent().getExtras().getDouble("longitude");
		setContentView(R.layout.filter);
		dis = (CheckBox)findViewById(R.id.widget40);
		bandeira = (CheckBox)findViewById(R.id.widget33);
		preco_max = (CheckBox)findViewById(R.id.widget32);
		preco_min = (CheckBox)findViewById(R.id.widget31);
		dis_value = (EditText)findViewById(R.id.widget38);
		value_min = (EditText)findViewById(R.id.widget35);
		value_max = (EditText)findViewById(R.id.widget36);
		value_ban = (EditText)findViewById(R.id.widget37);
		my_button = (Button) findViewById(R.id.widget39);
		my_button.setOnClickListener(new ButtonClickHandler());
	}
	
	public class ButtonClickHandler implements View.OnClickListener {
		public void onClick(View view) {
			Intent i = new Intent(GeoFilter.this, GeoMaps.class);
			
			i.putExtra("min_sucess", preco_min.isChecked());
			i.putExtra("max_sucess", preco_max.isChecked());
			i.putExtra("bandeira_sucess",bandeira.isChecked());
			i.putExtra("dis_sucess", dis.isChecked());
			Log.i("TESTEDIS", String.valueOf(dis.isChecked()));
			i.putExtra("preco_min", value_min.getText().toString());
			i.putExtra("preco_max", value_max.getText().toString());
			i.putExtra("bandeira", value_ban.getText().toString());
			i.putExtra("distancia", dis_value.getText().toString());
			
			i.putExtra("latitude", lat);
			i.putExtra("longitude", lng);
			i.putExtra("sucess", sucess);
			GeoFilter.this.startActivity(i);
		}
	}
}
