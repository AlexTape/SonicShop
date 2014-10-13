<%@ page import="de.alextape.sonicshop.databaseTags.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="databaseTags" uri="/WEB-INF/tld/databaseTags.tld"%>
<%@ page isELIgnored="false"%>
<%@ page session="true" %>

<div id="main" class="shell">
	<div class="cl">&nbsp;</div>
	<div id="products">
		<h2>Warenkorb</h2>
		
		<databaseTags:SaleCard />
		
		<p>Bezahlungen sind im Moment nur via Bank&uuml;berweisung m&ouml;glich. Als Versandpartner konnten wir bisher nur DHL gewinnen.</p>
		<div class="cl">&nbsp;</div>
	</div>
</div>