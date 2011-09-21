package classes.model
{
	import classes.views.GeoGasInfoWindow;
	
	import com.google.maps.InfoWindowOptions;
	import com.google.maps.LatLng;
	import com.google.maps.interfaces.IInfoWindow;
	import com.google.maps.overlays.Marker;
	import com.google.maps.overlays.MarkerOptions;
	
	import flash.utils.Dictionary;

	public class Entidade extends Marker
	{
		
		private var _data:Dictionary;
		private var _id:int;
		
		private var infoOpt:InfoWindowOptions;
		
		public function Entidade(latLong:LatLng, options:MarkerOptions = null, id:int = 0, data:Dictionary = null)
		{
			super(latLong,options);
			this._id = id;
			this._data = data;
			this.infoOpt = getInfoWindowOptions();
		}
		
		public function get data():Dictionary{
			return data;
		}
		
		public function get id():int{
			return id;
		}
		
		override public function openInfoWindow(arg0:InfoWindowOptions=null, arg1:Boolean=false):IInfoWindow{
			var bool:Boolean = arg1;
			if(!this.infoOpt){
				this.infoOpt = getInfoWindowOptions();
			}
			return super.openInfoWindow(this.infoOpt,bool);
		}
		
		private function getInfoWindowOptions():InfoWindowOptions{
			if(!this.infoOpt){
				this.infoOpt = new InfoWindowOptions({drawDefaultFrame:true});
				this.infoOpt.hasCloseButton = true;
				
				var infoContent:GeoGasInfoWindow = GeoGasInfoWindow.getInstance(this.id,data);
				
				this.infoOpt.customContent = infoContent;
			}
			
			return this.infoOpt;
		}
	}
}