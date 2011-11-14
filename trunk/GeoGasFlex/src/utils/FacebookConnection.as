package utils
{
	import classes.events.FacebookEvent;
	
	import com.facebook.graph.Facebook;
	
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;

	public class FacebookConnection extends EventDispatcher
	{
		public var connected:Boolean = false;
		private static var instance:FacebookConnection;
		
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
		
		protected function loginHandler(success:Object,fail:Object):void
		{
			if(success) {
				Facebook.api("/me",getMeHandler);
				connected = true;
				this.dispatchEvent(new FacebookEvent(FacebookEvent.FACEBOOK_CONNECTED));
			}
		}
		
		protected function getMeHandler(result:Object,fail:Object):void{
			//nameLbl.text=result.name;
			//birthdayLbl.text=result.birthday;
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