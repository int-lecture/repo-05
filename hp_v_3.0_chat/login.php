<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Login</title>
	<link rel="stylesheet" type="text/css" href="style.css" />
  </head>
  
  <body>
			<h2>Geben Sie ihren Namen und den Namen ihres Chatpartners ein </h2>
  <?php
		//http://stackoverflow.com/questions/1053424/how-do-i-get-php-errors-to-display
		//Error Reporting komplett abschalten
		//error_reporting(0);
	  
	  	//stackoverflow.com/questions/15949304/turn-off-display-error-php-ini
	 	if(DEBUG == true){
			 //Alle Fehler anzeigen
  			 display_errors(true);
		}else{
  			 display_errors(false); 
		}
		
      		$chatpartner = $_GET["chatpartner"];
		$username = $_GET["user"];
		
		if($username!="" and $chatpartner!=""){
			//Automatisch weiterleiten zur Chatbox
			header("Location:Chi-Chat.php?user=".$username."&chatpartner=".$chatpartner);
		}
?>		
		<form method="GET" action=" ">
		<center>
		<h4>Name</h4>
		<input type="text" name="user"/>
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
