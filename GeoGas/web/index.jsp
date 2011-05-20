<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SmartGathering</title>
<script type="text/javascript">
	function submit(){
		formu.submit();
	}
</script>
</head>
<body onload="submit();">
	<form method="Post" action="http://www.anp.gov.br/postos/consulta.asp" name="formu" id="formu" onSubmit="">
		<input value="AC" type="hidden" name="sEstado">
		<input value="1" type="hidden" name="sMunicipio" class="busca">
		<input type="hidden" name="hPesquisar" value="PESQUISAR">
		<input value="0" type="hidden" name="sBandeira" class="busca">
		<input value="0" type="hidden" name="sTipodePosto">
		<!-- 
		
		
		
		 -->
    </form>
</body>
</html>