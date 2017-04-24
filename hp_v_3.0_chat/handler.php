<!doctype html>
<html>
	
	<head>
	
		<title>Herzlichen GLückwunsch</title>
	
	</head>

	

	<body>

	
	
		<?php  
     
	
			if(isset($_POST['Submit'])){ 	//check if form was submitted
		
				if($_POST['antwort']=='j'){
			
					$username=$_POST['vorname'];
			
					$username2=$_POST['nachname'];
			
					$eMail_=$_POST['E-mail'];
			
					if($username=="" or $username2=="" or $eMail_==""){
			
					echo "Geben Sie ihren Namen und ihre E-mail Adresse ein.";
		
						}else{
$fp = fopen('newsletter.txt', 'a'); //a = append //w=write
			
						fwrite($fp, "[ Vname:" . $username . " " . "Nname:" . $username2 . "E-mail:" . $eMail_ . "] ");
			
						fclose($fp);
 echo "Herzlichen Glückwunsch! Die Anmeldung zu unserem Newsletter war erfolgreich!";
		
						}
	
			}else{
	echo "Sie müssen die Datenschutzbedingungen akzeptieren, um sich registrieren zu können.";
	
			}
		

		
}
?>

	
	</body>
	
</html>