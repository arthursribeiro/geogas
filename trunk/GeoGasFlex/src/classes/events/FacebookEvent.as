package classes.events
{
	import flash.events.Event;
	
	public class FacebookEvent extends Event
	{

		public static var FACEBOOK_CONNECTED:String = "CONECTOU";
		public static var FACEBOOK_DISCONNECTED:String = "DESCONECTOU";
		public static var FACEBOOK_POSTED:String = "POSTOU";
		
		public function FacebookEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}