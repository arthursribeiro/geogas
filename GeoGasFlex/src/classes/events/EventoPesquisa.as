package classes.events
{
	import flash.events.Event;
	
	public class EventoPesquisa extends Event
	{
		
		public var bandeira:String;
		public var raio:Number;
		public var preco:Number;
		
		public function EventoPesquisa(amigos:Boolean = false,type:String="submeter", bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			this.bandeira = bandeira;
			this.raio = raio;
			this.preco = preco;
		}
	}
}