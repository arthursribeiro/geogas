package classes.model
{
	import flash.utils.Dictionary;
	
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;

	public class Tesaurus
	{
		
		private static var singleton:Tesaurus;
		
		private var _dicionario:Object;
		
		public static function getInstance():Tesaurus{
			if(!singleton){
				singleton = new Tesaurus();
				singleton.updateDic();
			}
			return singleton;
		}
		
		public function getTraducao(word:String):String{
			var traducao:String = _dicionario[word];
			if(!traducao || traducao.length<=0){
				traducao = word;
			}
			
			return traducao;
		}
		
		public function receiveDic(event:ResultEvent):void{
			_dicionario = event.result;
			_dicionario.toString();
		}
		
		public function updateDic():void{
			if(needDictionary()){
				var ro:RemoteObject = new RemoteObject("PostoCombustivelService");
				ro.addEventListener(ResultEvent.RESULT,receiveDic);
				ro.addEventListener(FaultEvent.FAULT,error);
				ro.getTraducoes.send();
			}
		}
		
		public function error(event:FaultEvent):void{
			Alert.show(event.fault.message);
		}
		
		public static function needDictionary():Boolean{
			if(!singleton._dicionario){
				return true;
			}
			return false;
		}
		
		public static function set dicionario(dic:Dictionary):void{
			getInstance()._dicionario = dic;
		}
		
	}
}