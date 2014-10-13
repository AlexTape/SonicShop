<%@ page import="de.alextape.sonicshop.databaseTags.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="databaseTags" uri="/WEB-INF/tld/databaseTags.tld"%>
<%@ page isELIgnored="false"%>

<%

		String item = request.getParameter("item");

%>


<div id="main" class="shell">
	<div class="cl">&nbsp;</div>
	<div id="products">

		<databaseTags:ItemDetail item="<%=item %>" />

		<div class="cl">&nbsp;</div>
	</div>
</div>