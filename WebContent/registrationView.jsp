<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page isELIgnored="false" %>

<jsp:include page="fragments/siteHeader.jsp" flush="true" />

<body>

	<div id="wrapper">
	<jsp:include page="fragments/headline.jsp" flush="true" />
	<jsp:include page="fragments/navigation.jsp" flush="true" />
	<jsp:include page="forms/registration.jsp" flush="true" />		
	<jsp:include page="fragments/footline.jsp" flush="true" />
	</div>

</body>
</html>