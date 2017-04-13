<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Chi-Chat</title>
  </head>
  
  <body>
		
<?php
	$user=$_GET['user'];
	$chatpartner=$_GET['chatpartner'];
	$datei;
	$pfad;
	
	if((strcasecmp($user,$chatpartner))>0){
		$pfad="Chatverlauf/".$user."-".$chatpartner;
		$datei=fopen("Chatverlauf/".$user."-".$chatpartner, "a");
	}elseif((strcasecmp($user,$chatpartner))<=0){
		$pfad="Chatverlauf/".$chatpartner."-".$user;
		$datei=fopen("Chatverlauf/".$chatpartner."-".$user, "a");
	}
	chmod($datei, 0755);
	$date=date("Y-m-d h:i:sa");
	$text=$_POST['textf'];
	if((isset($_POST["send"])==true) and $text!=""){
		fwrite($datei,"(".$date.") :".$user."_".$text."\n");
	}


	//$verlauf=file_get_contents($pfad);
	//echo nl2br($verlauf);
	echo "<iframe src=".$pfad." width='500px' height='700px'></iframe>";
	fclose($datei);	
		
?>
		<br/>
		<br/>
		<form action="" method="POST" target="">
		<!-- textbox -->
		<input name="textf" type="text" size="50" maxlength="80">
		<!-- Senden Button -->
		<input name="send" type="Submit" value="Send">
		<input name="refresh" type="Submit" value="Refresh">
		<form/>

		

</body> 
</html>