package classes.model
{
	import avmplus.USE_ITRAITS;
	
	import com.google.maps.LatLng;
	
	import mx.collections.ArrayList;

	public class GeoLocation {
		
		private var  radLat:Number;  // latitude in radians
		private var  radLon:Number;  // longitude in radians
		
		private var  degLat:Number;  // latitude in degrees
		private var  degLon:Number;  // longitude in degrees
		
		private static var MIN_LAT:Number = toRadians(-90); 	
		private static var MAX_LAT:Number = toRadians(90);
		private static var MIN_LON:Number = toRadians(-180); 
		private static var MAX_LON:Number = toRadians(180);
		
		private static var RADIUS:Number = 6371.010; //km
		
		private static function toRadians(degrees:Number):Number{
			return degrees * Math.PI/180;;
		}
		
		private static function toDegrees(radians:Number):Number{
			return radians * 180/Math.PI;
		}
		
		public static function getInstanceDegrees(latitude:Number, longitude:Number):GeoLocation{
			var result:GeoLocation = new GeoLocation();
			result.radLat = toRadians(latitude);
			result.radLon = toRadians(longitude);
			result.degLat = latitude;
			result.degLon = longitude;
			result.checkBounds();
			return result;
		}
		
		public static function getInstanceRadians(latitude:Number, longitude:Number):GeoLocation{
			var result:GeoLocation = new GeoLocation();
			result.radLat = latitude;
			result.radLon = longitude;
			result.degLat = toDegrees(latitude);
			result.degLon = toDegrees(longitude);
			result.checkBounds();
			return result;
		}
		
		public static function fromDegrees(latitude:Number, longitude:Number):LatLng {
			var result:GeoLocation = new GeoLocation();
			result.radLat = toRadians(latitude);
			result.radLon = toRadians(longitude);
			result.degLat = latitude;
			result.degLon = longitude;
			result.checkBounds();
			var latL:LatLng = new LatLng(result.degLat,result.degLon);
			return latL;
		}
		
		
		public static function fromRadians(latitude:Number, longitude:Number):LatLng {
			var result:GeoLocation = new GeoLocation();
			result.radLat = latitude;
			result.radLon = longitude;
			result.degLat = toDegrees(latitude);
			result.degLon = toDegrees(longitude);
			var latL:LatLng = new LatLng(result.degLat,result.degLon);
			return latL;
		}
		
		private function checkBounds():void {
			if (radLat < MIN_LAT || radLat > MAX_LAT ||
				radLon < MIN_LON || radLon > MAX_LON)
				throw new Error();
		}
		
		
		public function getLatitudeInDegrees():Number {
			return degLat;
		}
		
		
		public function getLongitudeInDegrees():Number {
			return degLon;
		}
		
		public function toString(): String {
			return ""+degLon + "," + degLat;
		}
		
		public function boundingCoordinates(userDistance:Number):ArrayList {
			
			
			if (RADIUS < 0 || userDistance<=0)
				throw new Error();
			
			var radDist:Number = userDistance / RADIUS;
			
			var minLat:Number = radLat - radDist;
			var maxLat:Number = radLat + radDist;
			
			var minLon:Number
			var maxLon:Number;
			if (minLat > MIN_LAT && maxLat < MAX_LAT) {
				var deltaLon:Number = Math.asin(Math.sin(radDist) /
					Math.cos(radLat));
				minLon = radLon - deltaLon;
				if (minLon < MIN_LON) minLon += 2 * Math.PI;
				maxLon = radLon + deltaLon;
				if (maxLon > MAX_LON) maxLon -= 2 * Math.PI;
			} else {
				// a pole is within the distance
				minLat = Math.max(minLat, MIN_LAT);
				maxLat = Math.min(maxLat, MAX_LAT);
				minLon = MIN_LON;
				maxLon = MAX_LON;
			}
			
			var ret:ArrayList = new ArrayList();
			ret.addItem(fromRadians(minLat, (minLon+maxLon)/2));
			ret.addItem(fromRadians((minLat+maxLat)/2, maxLon));
			ret.addItem(fromRadians(maxLat, (minLon+maxLon)/2));
			ret.addItem(fromRadians((minLat+maxLat)/2, minLon));
			ret.addItem(fromRadians(minLat, (minLon+maxLon)/2));
			
			return ret;
		}
		
		
	}
}