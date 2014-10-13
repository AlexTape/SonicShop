<%@ page import="de.alextape.sonicshop.viewBeans.IndexBean"%>

<div id="header">
	<div class="shell">

		<h1 id="logo">
			<a class="notext" href="Main" title="sonicShop">sonicShop</a>
		</h1>
		<div id="top-nav">
			<ul>
				<!-- not enough time to do these active things dynamically -->
				<li class="active"><a href="/Webshop/Main" title="Start"><span>Start</span></a></li>
				<li><a href="/Webshop/SaleCardHandler" title="Warenkorb"><span>Warenkorb</span></a></li>
				<li><a href="/Webshop/Dashboard" title="Profil"><span>Profil</span></a></li>
				<li><a href="/Webshop/Main?forward=contactView.jsp"
					title="Kontakt"><span>Kontakt</span></a></li>
			</ul>
		</div>
		<div class="cl">&nbsp;</div>
		<p id="cart">
			<span class="profile">Willkommen, <a href="/Webshop/Dashboard"
				title="Profil Link">${IndexBean.name}</a> .
			</span> <span class="shopping"> Einkaufwagen <a
				href="/Webshop/SaleCardHandler" title="Warenkorb Link">(${IndexBean.shoppingItems})
					&euro;${IndexBean.shoppingPrice}</a>${IndexBean.logout}
			</span>
		</p>

	</div>
</div>