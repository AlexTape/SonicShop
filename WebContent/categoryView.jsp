<%@ page import="de.alextape.sonicshop.databaseTags.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="databaseTags" uri="/WEB-INF/tld/databaseTags.tld" %>
<%@ page isELIgnored="false" %>

<%-- <jsp:useBean id="HotSaleItems" scope="page" type="viewBeans.HotSaleItems" class="viewBeans.HotSaleItems"></jsp:useBean> --%>

<jsp:include page="fragments/siteHeader.jsp" flush="true" />

<body>

	<div id="wrapper">
	<jsp:include page="fragments/headline.jsp" flush="true" />
	<jsp:include page="fragments/navigation.jsp" flush="true" />
	<jsp:include page="fragments/slider.jsp" flush="true" />
	<jsp:include page="fragments/categories.jsp" flush="true" />		
	<jsp:include page="fragments/footline.jsp" flush="true" />
	</div>

</body>
</html>