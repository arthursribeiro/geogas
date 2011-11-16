package utils
{
	import classes.Constants;
	import classes.events.FacebookEvent;
	
	import com.facebook.graph.Facebook;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.http.HTTPService;

	public class FacebookConnection extends EventDispatcher
	{
		public var connected:Boolean = false;
		public var nome:String;
		public var id:String;
		public var birthday:String;
		private static var instance:FacebookConnection;
		public var amigos:ArrayCollection = null;
		
		public function FacebookConnection(enforcer:SingletonEnforcer)
		{
			if(enforcer == null) {
				throw new Error("Só pode haver uma instância de Singleton");
			}
			Facebook.init("121949401245956", loginHandler);
		}
		
		public static function getInstance():FacebookConnection {
			if(instance == null) {
				instance = new FacebookConnection(new SingletonEnforcer); 
			}
			return instance;
		}
		
		public function login():void
		{
			Facebook.login(loginHandler,{scope:"user_birthday,read_stream, publish_stream"});
			trace("entrou aqui");
		}
		
		public function logout():void {
			Facebook.logout(logoutHandler);
		}
		
		public function getFriends():void {
			Facebook.api("/me/friends", friends);
		}
		
		public function friends(result:Object, fail:Object):void {
			if(result != null) {
				amigos = new ArrayCollection(result as Array);
			}
//			Alert.show(amigos.getItemAt(0).name.toString());
		}
		
		protected function loginHandler(success:Object,fail:Object):void{
			if(success) {
				Facebook.api("/me",getMeHandler);
				getFriends();
				connected = true;
				this.dispatchEvent(new FacebookEvent(FacebookEvent.FACEBOOK_CONNECTED));
			}
		}
		
		
		protected function getMeHandler(result:Object,fail:Object):void{
			nome = result.name;
			id = result.id;
			birthday = result.birthday;
			var service:HTTPService = new HTTPService();
			service.url = Constants.SERVER_AD+"/geogas/struts/updateUsuario?facebook_id="+id+"&chave_facebook="+id+"&data_nascimento="+birthday+
				"&nome="+nome;
			service.addEventListener(ResultEvent.RESULT, saveUserResult);
			service.addEventListener(FaultEvent.FAULT, saveUserFault);
			service.send();
		}
		
		protected function saveUserResult(ev:Event):void {
//			Alert.show("deu certo!");
		}
		
		protected function saveUserFault(ev:Event):void {
//			Alert.show("deu merda!");
		}
		
		protected function logoutHandler(response:Object):void{
			connected = false;
			this.dispatchEvent(new FacebookEvent(FacebookEvent.FACEBOOK_DISCONNECTED));
		}
		
		public function submitPost(post:String):void {
			Facebook.api("/me/feed",submitPostHandler,{message:post}, "POST");
		}
		
		protected function submitPostHandler(result:Object,fail:Object):void
		{
			this.dispatchEvent(new FacebookEvent(FacebookEvent.FACEBOOK_POSTED));
		}
	}
}

class SingletonEnforcer {}