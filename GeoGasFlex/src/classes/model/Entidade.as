package classes.model
{
	import classes.Constants;
	import classes.views.GeoGasInfoWindow;
	
	import com.google.maps.InfoWindowOptions;
	import com.google.maps.LatLng;
	import com.google.maps.MapMoveEvent;
	import com.google.maps.interfaces.IInfoWindow;
	import com.google.maps.overlays.Marker;
	import com.google.maps.overlays.MarkerOptions;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.utils.Dictionary;
	
	import spark.components.Image;
	
	public class Entidade extends PostoCombustivel
	{
		
		private var _data:Dictionary; // String (Editable | NotEditable) -> Dictionary[String(Label) -> String(Value)]
		private var _otherData:Dictionary; // String (TabName) -> Dictionary[String(Editable | NotEditable) -> Dictionary[String(Label) -> String(Value)] ]
		private var _id:int;
		
		private var infoOpt:InfoWindowOptions;
		
		public var pricegasoline:Number;
		
		public var pricealcohol:Number;
		
		public var pricediesel:Number;
		
		public var pricegas:Number;
		
		public var pricegasoline_user:Number;
		
		public var pricealcohol_user:Number;
		
		public var pricediesel_user:Number;
		
		public var pricegas_user:Number;
		
		public var autuacoes:Number;
		
		public var denuncias:Number;
		
		public var bandeira:String;
		
		public var latitude:Number;
		
		public var longitude:Number;
		
		public var nomefantasia:String;
		public var razaosocial:String; 
			
		public var img:String;
		
		private var content:GeoGasInfoWindow
		
		public function Entidade(latLong:LatLng, id:int = 0, data:Dictionary = null, otherData:Dictionary=null, options:MarkerOptions = null)
		{
			if(!options){
				options = new MarkerOptions();
			}
			var img:Image = new Image();
			img.source = Constants.SERVER_AD+"/geogas/imgs/"+data["img"];
			img.width = 35;
			img.height = 35;
			options.icon = img;
			super(latLong,id, data,options);
			this._id = id;
			this._data = data;
			createEntity(data);
			this._otherData = otherData;
			this.infoOpt = getInfoWindowOptions("State1");
			
		}
		
		public function getLabel():String{
			if(nomefantasia && nomefantasia.length>0){
				return nomefantasia;
			}else if(razaosocial && razaosocial.length>0){
				return razaosocial;
			}else
				return bandeira;
		}
		
		private function createEntity(data:Dictionary):void{
			for(var key:Object in data){
				try{
					this[key.toString()] = data[key];
				}catch(er:Error){
					
				}
			}
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
		
		public function openPopup(event:MouseEvent):void{
			dispatchEvent(new Event(Constants.OPENPOPU_EVENT));
			
		}
		
		public function updateInfoWindow():void{
			if(this.content){
				content.updateLabels();
			}
		}
		
		public function getInfoWindowOptions(currentState:String):InfoWindowOptions{
			if(!this.infoOpt){
				this.infoOpt = new InfoWindowOptions({drawDefaultFrame:true});
				this.infoOpt.hasCloseButton = true;
				content = GeoGasInfoWindow.getInstance(this,Constants.SERVER_AD+"/geogas/imgs/"+this.img);
				this.infoOpt.customContent = content;
				this.infoOpt.width = content.width+10;
				this.infoOpt.height = content.height+10;
				
			}
			
			var window:GeoGasInfoWindow = this.infoOpt.customContent as GeoGasInfoWindow;
			window.setCurrentState(currentState);
			return this.infoOpt;
		}
	}
}