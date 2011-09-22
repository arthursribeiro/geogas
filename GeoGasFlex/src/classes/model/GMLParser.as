package classes.model
{
	import flash.events.Event;
	import flash.utils.Dictionary;
	import flash.xml.XMLDocument;
	
	import mx.collections.XMLListCollection;
	import mx.rpc.http.HTTPService;
	
	public class GMLParser
	{
		private var map:Dictionary = new Dictionary();
		private var xml:XML;
		
		public function GMLParser(xmlFile:XML)
		{
			this.xml = xmlFile;
			this.parser();
		}
		
		private function parser():void{
			var elements:XMLList;
			var i:int;
			elements = xml.elements();
			for each (var element:XML in elements){
				if(element.localName() == 'featureMember') {
					var auxDic:Dictionary = new Dictionary();
					for each ( var el:XML in element.children().children()){
						var aux:String;
						var a:XMLList = el.children();
						aux = el.name().toString();
						auxDic[aux] = el.text().toString();
					}
					var str_aux:String = element.children()[0].attributes()[0].toString();
					map[str_aux] = auxDic;
				}
			}
		}
		
		public function getDictionary():Dictionary {
			return this.map;
		}
	}
}