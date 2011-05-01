<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SmartGathering</title>
<script type="text/javascript">


function addTag(tag, idPhoto) {
	var url = '<s:url action="adicionandoTag" includeParams="none"></s:url>';
	url += '?tag='+tag+'&idPhoto='+idPhoto; 
	//alert(url);
	var xmlHttp = ajaxFunction();
	xmlHttp.onreadystatechange=function() {
		if(xmlHttp.readyState==4){
			if(xmlHttp.status != 200) {
				alert("Erro ao atualizar contatos no servidor!");
			}
			refreshTags(idPhoto);
			}
	}
	xmlHttp.open("POST",url,true);
	xmlHttp.send(null); 
}

function refreshTags(idPhoto) {
	var url = '<s:url action="getTags" includeParams="none"></s:url>';
	url += '?idPhoto='+idPhoto; 
	//alert(url);
	var xmlHttp = ajaxFunction();
	xmlHttp.onreadystatechange=function() {
		if(xmlHttp.readyState==4){
			if(xmlHttp.status != 200) {
				alert("Erro ao atualizar contatos no servidor!");
			}
			document.getElementById('photoTags').innerHTML = xmlHttp.responseText;	
			}
	}
	xmlHttp.open("POST",url,true);
	xmlHttp.send(null); 
}

function ajaxFunction() {
	var xmlHttp;
	try {  
		// Firefox, Opera 8.0+, Safari  
		xmlHttp=new XMLHttpRequest();  
	} catch (e){  
		// Internet Explorer  
		try {    
			xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");    
		} catch (e){   
			try {     
				xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");      
			} catch (e){      
				alert("Your browser does not support AJAX!");      
				return false;      
			}    
		}  
	}
	return xmlHttp;  
}


</script>
</head>
<body>
SmartGathering... =D
<br>
<h3>SUGGESTION TAGS</h3>
             
         	<div id="tags">
	        	<s:iterator value="tags"  status="stat">
	        	<li>
	        		<a href="#" onclick="addTag('<s:property value='key' />',1)">
						<s:property value="key"/>
					</a>
					<br>
					</li>
	        	</s:iterator>
        	</div>

<h3>TAGS</h3>       	
        	<div id="photoTags">
	        	<s:iterator value="photoTags"  status="stat">
	        	<li>
	        		
					<s:property value="key"/>
					
					<br>
					</li>
	        	</s:iterator>
        	</div>
        	
        	<!-- ext.getAllTags("POINT(-34.822884 -7.111880)", d, 1, 3)) -->
</body>
</html>