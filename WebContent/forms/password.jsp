<%@ page import="de.alextape.sonicshop.viewBeans.IndexBean"%>

<div id="main" class="shell">
	<div id="content">
		<div class="post">
			<h2>Passwort &auml;ndern</h2>
			<form action="/Webshop/PasswordChanger" method="POST"
				accept-charset="UTF-8">
				<table>
					<tr>
						<td><p>Passwort &auml;ndern</p></td>
						<td id="hint"></td>
					</tr>
					<tr>
						<td align="right">aktuelles Passwort:</td>
						<td><input type="password" name="password_old"></td>
					</tr>
					<tr>
						<td align="right">neues Passwort:</td>
						<td><input type=password name="password"></td>
					</tr>
					<tr>
						<td align="right">wiederhole Passwort:</td>
						<td><input type=password name="password2"></td>
					</tr>
					<tr>
						<td><br><input type="hidden" name="email" value="${IndexBean.name }"></td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="durchf&uuml;hren"
							class="button"></td>
					</tr>
					<tr>
						<td><a href="/Webshop/Main"
							class="more" title="Register">abbrechen</a></td>
					</tr>
				</table>

			</form>

			<div class="cl">&nbsp;</div>
		</div>
	</div>
	<div id="sidebar">
		<ul>
			<li class="widget">
				<h2>Partners</h2>
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


</div>
