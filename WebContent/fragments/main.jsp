<%@ page import="de.alextape.sonicshop.databaseTags.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="databaseTags" uri="/WEB-INF/tld/databaseTags.tld"%>
<%@ page isELIgnored="false"%>



<div id="main" class="shell">
	<div id="content">
		<div class="post">
			<h2>Willkommen!</h2>
			<img src="css/images/lan_party.jpg" alt="wir supporten lan partys!" />
			<p>sonicShop nun endlich auch im Internet erreichbar. Bisher
				waren unsere Angebote nur auf exklusiven LANs verf&uuml;gbar. Nun
				endlich auch f&uuml;r alle anderen online.<br>
				Da wir sehr daran interessiert sind, unsere Pr&auml;senz im Internet auszubauen, ist jede Art von Feedback erw&uuml;schnt.
				F&uuml;r mehr Informationen &uuml;ber und zu uns klickt doch einfach auf "mehr".</p>
			<p>
				Be a part! Be sonic.. <a href="#" class="more" title="Read More">mehr</a>
			</p>
			<div class="cl">&nbsp;</div>
		</div>
	</div>
	<div id="sidebar">
		<ul>
			<li class="widget">
				<h2>Zertifizierte Partner</h2>
				<div class="partner">
					<ul>
						<li><a href="#" title="Intel"><img
								src="css/images/intel.jpg" alt="Intel Logo" /></a></li>
						<li><a href="#" title="IBM"><img
								src="css/images/ibm.jpeg" alt="IBM Logo" /></a></li>
						<li><a href="#" title="Acer"><img
								src="css/images/acer.jpg" alt="Acer Logo" /></a></li>
						<li><a href="#" title="Sony"><img
								src="css/images/sony.png" alt="Sony Logo" /></a></li>
					</ul>
					<div class="cl">&nbsp;</div>
				</div>
			</li>
		</ul>
	</div>
	<div class="cl">&nbsp;</div>
	<div id="products">
		<h2>Hot Sales</h2>

		<databaseTags:HotSaleItems />

		<div class="cl">&nbsp;</div>
	</div>
	<div id="product-slider">
		<h2>Best Rated Products</h2>
		<ul>

			<databaseTags:BestRatedItems />

		</ul>
		<div class="cl">&nbsp;</div>
	</div>
</div>