<%@ page import="de.alextape.sonicshop.databaseTags.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="databaseTags" uri="/WEB-INF/tld/databaseTags.tld"%>
<%@ page isELIgnored="false"%>

<%
	String category = null;
	String item = null;
	if (request.getParameter("category") != null) {
		category = request.getParameter("category");
	} else {
		category = "database error";
	}
	if (request.getParameter("item") != null) {
		item = request.getParameter("item");
	} else {
		item = "...";
	}
%>


<div id="main" class="shell">
	<div class="cl">&nbsp;</div>
	<div id="products">
		<h2><%=category%> / <%=item%></h2>
		<br>
		<databaseTags:CategoryItems category="<%=category%>"
			item="<%=item%>" />

		<div class="cl">&nbsp;</div>
	</div>
</div>