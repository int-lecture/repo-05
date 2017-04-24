<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Chi-Chat</title>
	<link rel="stylesheet" type="text/css" href="style.css" />
  </head>
  
  <body>
		<h1>Chi-Chat</h1>;
<?php
	//Error Reporting komplett abschalten
	error_reporting(0);
	$user=$_GET['user'];
	$chatpartner=$_GET['chatpartner'];
	$datei;
	$pfad;
	
	//https://www.w3schools.com/php/func_string_strcasecmp.asp
	/*
	"0 - if the two strings are equal
	<0 - if string1 is less than string2
	>0 - if string1 is greater than string2"
	*/
	if((strcasecmp($user,$chatpartner))>0){
		$pfad="Chatverlauf/".$user."-".$chatpartner;
		$datei=fopen($pfad, "a");
	}else{
		$pfad="Chatverlauf/".$chatpartner."-".$user;
		$datei=fopen($pfad, "a");
	}
	
	//https://www.w3schools.com/php/php_date.asp
	$date=date("Y-m-d h:i:sa");
	$text=$_POST['textf'];
	chmod($pfad,0767);
	if((isset($_POST["send"])==true) and $text!=""){
		fwrite($datei,"(".$date.") :".$user."_".$text."\n");
	}
	
	echo "<h3>Du chattest jetzt mit ".$chatpartner."</h3>";
	
	$dir="Chatverlauf";
	//http://php.net/manual/de/function.scandir.php
	//"Gibt bei Erfolg ein Array von Dateinamen zurück"
	$arrayDateien= scandir($dir);
	echo "<h2>Kontakte</h2>";
	
	foreach($arrayDateien as $value){
	//"explode — Teilt einen String anhand einer Zeichenkette"
	$arrayNamen=explode("-",$value);
	if($arrayNamen[0]==$user){
			echo" <h2><a href=Chi-Chat.php?user=".$user."&chatpartner=".$arrayNamen[1].">".$arrayNamen[1]."</a></h2>";	
		}elseif($arrayNamen[1]==$user){
			echo" <h2><a href=Chi-Chat.php?user=".$user."&chatpartner=".$arrayNamen[0].">".$arrayNamen[0]."</a></h2>";
		}
	}
	
	//CSS Id's funktionieren nicht wenn iframe mit echo ausgegeben wird?	
	//$datei führt beim iframe zu Fehler(Resource id)?
	echo "<center><iframe src=".$pfad." width='700px' height='430px' scrolling='yes' name='Chatbox'></iframe></center>";
	fclose($datei);	
	
	//http://stackoverflow.com/questions/32420145/how-to-refresh-the-current-page
	//header("Refresh: 7");
		
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
		<a href="login.php"			id="login">Chat</a>
		</div>
	</body> 
</html>