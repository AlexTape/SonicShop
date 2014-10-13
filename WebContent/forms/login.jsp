
<div id="main" class="shell">
	<div id="content">
		<div class="post">
			<h2>Login</h2>
			<form action="/Webshop/LoginHandler" method="POST"
				accept-charset="UTF-8">
				<table>
					<tr>
						<td><p>Login</p></td>
						<td id="hint"></td>
					</tr>
					<tr>
						<td>eMail:</td>
						<td><input type="text" name="email"></td>
					</tr>
					<tr>
						<td>Passwort:</td>
						<td><input type=password name="password"></td>
					</tr>
					<tr>
						<td><br></td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="login" class="button"></td>
					</tr>
					<tr>
						<td><a href="/Webshop/Main?forward=registrationView.jsp" class="more" title="Register">Noch kein
								Mitglied..?</a></td>
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
