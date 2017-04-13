<?php
      	        $chatpartner = $_GET["chatpartner"];
		$username = $_GET["user"];
		if($username==""){
			echo "Geben Sie ihren Namen und den Namen ihres Chatpartners ein"; 
		}elseif($chatpartner!=""){
			echo" <a href=Chi-Chat.php?user=".$username."&chatpartner=".$chatpartner.">".$chatpartner."</a>";	
		}
		
		//http://php.net/manual/de/function.scandir.php
		//"Gibt bei Erfolg ein Array von Dateinamen zurück"
		$dir="Chatverlauf";
		$arrayDateien= scandir($dir);
		if($username!="" and $chatpartner!=""){

			foreach($arrayDateien as $value){
				//"explode — Teilt einen String anhand einer Zeichenkette"
				$arrayNamen=explode("-",$value);
				if($arrayNamen[0]==$username){
					echo" <a href=Chi-Chat.php?user=".$username."&chatpartner=".$arrayNamen[1].">".$arrayNamen[1]."</a> \n";	
				}elseif($arrayNamen[1]==$username){
					echo" <a href=Chi-Chat.php?user=".$username."&chatpartner=".$arrayNamen[0].">".$arrayNamen[0]."</a> \n";
				}
			}
		}
?>

<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Chi-Chat</title>
  </head>
  
  <body>
			<form method="get" action="">
			User:<input type="text" name="user" />
			<br/>
			Chatpartner:<input type="text" name="chatpartner"/>
			<br/>
			<input type="submit" class="button" value="Click!" />
			</form>
 </body> 
</html>