
	<html>
	<head>
	<title>Herzlichen GLückwunsch</title>
	</head>

	<body>

	
	<?php

	
	if($_POST["antwort"]=="j"){

			$f = fopen("data.txt", "w");
			fwrite($f, $_POST["vorname"]); 
			fwrite($f, "***"); 
			fwrite($f, $_POST["nachname"]); 
			fwrite($f, "***"); 
			fwrite($f, $_POST["e-mail"]); 
			fclose($f);
			echo "Sie haben sich erfolgreich registriert!"; 
	}

	elseif ($_POST["antwort"]=="n"){

	echo "Sie müssen die Datenschutzbedingungen akzeptieren um sich registrieren zu können.";
	}
	else {
	echo " Sie haben nicht alle Felder ausgefüllt!";

	}


	?>

	</body>
	</html>