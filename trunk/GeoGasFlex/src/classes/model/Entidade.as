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
		
		private var _data:Dictionary; // String (Editable | NotEditable) -> Dictionary[String(Label) -> String(Value)]
		private var _otherData:Dictionary; // String (TabName) -> Dictionary[String(Editable | NotEditable) -> Dictionary[String(Label) -> String(Value)] ]
		private var _id:int;
		
		private var infoOpt:InfoWindowOptions;
		
		public function Entidade(latLong:LatLng, id:int = 0, data:Dictionary = null, otherData:Dictionary=null, options:MarkerOptions = null)
		{
			super(latLong,options);
			this._id = id;
			this._data = data;
			this._otherData = otherData;
			this.infoOpt = getInfoWindowOptions();
		}
		
		public function get data():Dictionary{
			return _data;
		}
		
		public function get otherData():Dictionary{
			return _otherData;
		}
		
		public function get id():int{
			return _id;
		}
		
		public function set otherData(othDat:Dictionary):void{
			this._otherData = othDat;
		}
		
		public function getInfoWindowOptions():InfoWindowOptions{
			if(!this.infoOpt){
				this.infoOpt = new InfoWindowOptions({drawDefaultFrame:true});
				this.infoOpt.hasCloseButton = true;
				
				var infoContent:GeoGasInfoWindow = GeoGasInfoWindow.getInstance(this.id,data,otherData);
//				
				this.infoOpt.customContent = infoContent;
				this.infoOpt.width = infoContent.width+30;
				this.infoOpt.height = infoContent.height+30;
			}
			
			return this.infoOpt;
		}
	}
}