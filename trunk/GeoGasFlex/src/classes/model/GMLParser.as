package classes.model
{
	import flash.events.Event;
	import flash.xml.XMLDocument;
	
	import mx.collections.XMLListCollection;
	import mx.rpc.http.HTTPService;
	
	public class GMLParser
	{
		var map:Object = new Object();
		var xml:XML;
		
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
					var auxDic:Object = new Object();
					for each ( var el:XML in element.children().children()){
						var aux:String;
						var a:XMLList = el.children();
						aux = el.name();
						auxDic[aux] = el.text().toString();
					}
					var str_aux:String = element.children()[0].attributes()[0].toString();
					map[str_aux] = auxDic;
				}
			}
		}
		
		public function getDictionary():Object {
			return this.map;
		}
	}
}