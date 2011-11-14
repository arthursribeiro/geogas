package classes.model
{
	import com.google.maps.LatLng;
	import com.google.maps.overlays.Marker;
	import com.google.maps.overlays.MarkerOptions;
	
	import flash.utils.Dictionary;
	
	import mx.graphics.shaderClasses.ExclusionShader;

	public class PostoCombustivel extends Marker
	{
		public var autorizacao:String ;
		
		public var cnpjcpf:String;
		
		public var razaoSocial:String;
		
		public var nomeFantasia:String;
		
		public var numerodespacho:String; 
		
		
		
		public function PostoCombustivel(latLong:LatLng, id:int = 0, data:Dictionary = null, options:MarkerOptions = null)
		{
			
			super(latLong,options);
		}
		
		private function createPosto(data:Dictionary):void{
			for(var key:Object in data){
				try{
					this[key.toString()] = data[key];
				}catch(er:Error){
					
				}
				
			}
		}
		
	}
}