<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:fb="http://www.facebook.com/2008/fbml">
	<head>	 	
		<!-- Include support librarys first -->
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/swfobject/2.2/swfobject.js"></script>
		<script type="text/javascript" src="http://connect.facebook.net/en_US/all.js"></script>	
	</head>
	<body>
		<div id="fb-root"></div><!-- required div -->
		<div id="flashContent">
			<h1>You need at least Flash Player 10.2 to view this page.</h1>
			<p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" /></a></p>
		</div>  
		<script type="text/javascript">
			//This example uses dynamic publishing with swfObject. Login is handled in the swf
						
			//Note we are passing in attribute object with a 'name' property that is same value as the 'id'. This is REQUIRED for Chrome/Mozilla browsers			
			swfobject.embedSWF("GeoGasFlex-debug/GeoGasFlex.swf", "flashContent", "100%", "670", "10.2", null, null, null, {name:"flashContent"}); 			
		</script>
	</body>
</html>