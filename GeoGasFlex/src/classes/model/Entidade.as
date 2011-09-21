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
		private var _otherData:Dictionary;
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
				
				var infoContent:GeoGasInfoWindow = GeoGasInfoWindow.getInstance(this.id,data,otherData);
				
				this.infoOpt.customContent = infoContent;
				this.infoOpt.width = infoContent.width+20;
				this.infoOpt.height = infoContent.height+20;
			}
			
			return this.infoOpt;
		}
	}
}