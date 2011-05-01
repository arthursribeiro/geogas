<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:iterator value="photoTags"  status="stat">
      	<li>
      	<s:property value="key"/>
		<br>
		</li>
</s:iterator>