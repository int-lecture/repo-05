
<?php
		// Error Reporting komplett abschalten
		error_reporting(0);
		
      	$chatpartner = $_GET["chatpartner"];
		$username = $_GET["user"];
		if($username==""){
			echo "<h2>Geben Sie ihren Namen und den Namen </h2>"; 
			echo "<h2>ihres Chatpartners ein</h2>";
		}elseif($chatpartner!=""){
			echo" <h2>Chatpartner</h2>";
			echo" <h2><a href=Chi-Chat.php?user=".$username."&chatpartner=".$chatpartner.">".$chatpartner."</a></h2>";	
		}
		
		//http://php.net/manual/de/function.scandir.php
		//"Gibt bei Erfolg ein Array von Dateinamen zurück"
		$dir="Chatverlauf";
		$arrayDateien= scandir($dir);
		if($username!="" and $chatpartner!=""){

			echo "<h2>Kontakte</h2>";
			foreach($arrayDateien as $value){
				//"explode — Teilt einen String anhand einer Zeichenkette"
				$arrayNamen=explode("-",$value);
				
				if($arrayNamen[0]==$username){
					echo" <h2><a href=Chi-Chat.php?user=".$username."&chatpartner=".$arrayNamen[1].">".$arrayNamen[1]."</a></h2>";	
				}elseif($arrayNamen[1]==$username){
					echo" <h2><a href=Chi-Chat.php?user=".$username."&chatpartner=".$arrayNamen[0].">".$arrayNamen[0]."</a></h2>";
				}
				
			}
		}
?>

<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Login</title>
	<link rel="stylesheet" type="text/css" href="style.css" />
  </head>
  
  <body>
			<center><form method="get" action="">
			<h4>Name</h4>
			<input type="text" name="user" />
			<br/>
			<h4>Chatpartner</h4>
			<input type="text" name="chatpartner"/>
			<br/>
			<input type="submit" value="Click!" />
			</form></center>
			
			<div id="menu" class="menu">
			<a href="index.html"		id="Hauptseite">Hauptseite</a>
			<a href="Profil.html"		id="Firmenprofil">Firmenprofil</a>
			<a href="Produkt.html" 		id="Produkt">Produkt</a>
			<a href="Impressum.html"	id="Impressum">Impressum</a>
			<a href="Newsletter.html"	id="Newsletter">Newsletter</a>
			<a href="login.php"			id="login">Chat</a>
			</div>
 </body> 
</html>