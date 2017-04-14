<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Chi-Chat</title>
	<link rel="stylesheet" type="text/css" href="style.css" />
  </head>
  
  <body>
		
<?php
	// Error Reporting komplett abschalten
	error_reporting(0);
	$user=$_GET['user'];
	$chatpartner=$_GET['chatpartner'];
	$datei;
	$pfad;
	
	if((strcasecmp($user,$chatpartner))>0){
		$pfad="Chatverlauf/".$user."-".$chatpartner;
		$d=fopen($pfad, "a");
	}elseif((strcasecmp($user,$chatpartner))<=0){
		$pfad="Chatverlauf/".$chatpartner."-".$user;
		$d=fopen($pfad, "a");
	}
	
	$date=date("Y-m-d h:i:sa");
	$text=$_POST['textf'];
	chmod($pfad,0767);
	if((isset($_POST["send"])==true) and $text!=""){
		fwrite($d,"(".$date.") :".$user."_".$text."\n");
	}
	
	echo "<h1>Chi-Chat</h1>";
	echo "<h3>Du chattest jetzt mit ".$chatpartner."</h3>";
	
	//Kontakte anzeigen
	$dir="Chatverlauf";
	$arrayDateien= scandir($dir);
	if($user!="" and $chatpartner!=""){
		echo "<h2>Kontakte</h2>";
		foreach($arrayDateien as $value){
			$arrayNamen=explode("-",$value);	
			if($arrayNamen[0]==$user){
				echo" <h2><a href=Chi-Chat.php?user=".$user."&chatpartner=".$arrayNamen[1].">".$arrayNamen[1]."</a></h2>";	
			}elseif($arrayNamen[1]==$user){
				echo" <h2><a href=Chi-Chat.php?user=".$user."&chatpartner=".$arrayNamen[0].">".$arrayNamen[0]."</a></h2>";
			}
				
		}
	}
	//CSS Id's funktionieren nicht wenn iframe mit echo ausgegeben wird?
	//$datei führt beim iframe zu Fehler(Resource id)?
	echo "<center><iframe src=".$pfad." width='700px' height='430px' scrolling='yes' name='Chatbox'></iframe></center>";
	fclose($d);	
		
?>
		<center>
		<form action="" method="POST">
		<br/>
		<input name="textf" type="text" size="50" maxlength="80">
		<input name="send" type="Submit" value="Send">
		<input name="refresh" type="Submit" value="Refresh" onClick="window.location.reload()">
		<form/>
		</center>
		
		
		<div id="menu" class="menu">
		<a href="index.html"		id="Hauptseite">Hauptseite</a>
		<a href="Profil.html"		id="Firmenprofil">Firmenprofil</a>
		<a href="Produkt.html" 		id="Produkt">Produkt</a>
		<a href="Impressum.html"	id="Impressum">Impressum</a>
		<a href="Newsletter.html"	id="Newsletter">Newsletter</a>
		<a href="login.php"		id="login">Chat</a>
		</div>
	</body> 
</html>
