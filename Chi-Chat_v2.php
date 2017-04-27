

<?php
session_start();
session_regenerate_id(true);

?>



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
	//error_reporting(E_ALL);
	//$user=$_GET['user'];
	//$chatpartner=$_GET['chatpartner'];
	
	//https://www.w3schools.com/php/func_string_strcasecmp.asp
	/*
	"0 - if the two strings are equal
	<0 - if string1 is less than string2
	>0 - if string1 is greater than string2"
	*/


	if((strcasecmp($_SESSION['username'],$_SESSION['chatp']))>0){
		$pfad="Chatverlauf/".$_SESSION['username']."-".$_SESSION['chatp'];
		$datei=fopen($pfad, "a");
	}else{
		$pfad="Chatverlauf/".$_SESSION['chatp']."-".$_SESSION['username'];
		$datei=fopen($pfad, "a");
	}
	

	if(isset($_POST['logout'])){
	session_unset();
	session_destroy();
	$_SESSION = array();
	header("Location:login.php");
	}
	
	//https://www.w3schools.com/php/php_date.asp
	$date=date("Y-m-d h:i:sa");
	$text=$_POST['textf'];
	chmod($pfad,0767);
	if((isset($_POST["send"])==true) and $text!=""){
		fwrite($datei,"(".$date.") :".$_SESSION['username']."_".$text."\n");
	}
	$dir="Chatverlauf";
	//http://php.net/manual/de/function.scandir.php
	//"Gibt bei Erfolg ein Array von Dateinamen zurück"
	$arrayDateien= scandir($dir);
	
	if($_POST["chatpartner"]!=""){
		foreach($arrayDateien as $value){
			//"explode — Teilt einen String anhand einer Zeichenkette"
			$arrayNamen=explode("-",$value);
			if($arrayNamen[0]==$_POST["chatpartner"]){
				$_SESSION["chatp"]=$_POST["chatpartner"];
			}elseif($arrayNamen[1]==$_POST["chatpartner"]){
				$_SESSION["chatp"]=$_POST["chatpartner"];
			}
		}
	}
	

	echo "<h3>Du chattest jetzt mit ".$_SESSION['chatp']."</h3>";
	echo "<h2>Kontakte</h2>";
	if($_SESSION["username"]!="" and $_SESSION["chatp"]!=""){
		foreach($arrayDateien as $value){
			//"explode — Teilt einen String anhand einer Zeichenkette"
			$arrayNamen=explode("-",$value);
			if($arrayNamen[0]==$_SESSION["username"]){
				echo" <h2>".$arrayNamen[1]."</h2>";
			}elseif($arrayNamen[1]==$_SESSION['username']){
				echo" <h2>".$arrayNamen[0]."</h2>";
			}
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
		<input name="logout" type="Submit" value="Logout">
		<input name="chatpartner" type="text" size="50" maxlength="80">
		<input name="wechseln" type="Submit" value="Chatpartner wechsel">
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