//https://stackoverflow.com/questions/13056810/how-to-implement-a-put-call-with-json-data-using-ajax-and-jquery
//https://learn.jquery.com/using-jquery-core/document-ready/

/*"A page can't be manipulated safely until the document is "ready." jQuery detects this state of readiness for you. 
Code included inside $( document ).ready() will only run once the page Document Object Model (DOM) is ready for JavaScript code to execute."*/
$( document ).ready(function() {  
	
});


/**
* Eine Funktion welche, die Felder der Registrierungs-Form ausliest, diese in ein JSON-Object umwandelt
* und an der gegebenen URL mit den Daten ein Registrierungsversuch startet.
* Zuvor wird geprüft ob die beiden Passwörter identisch sind und ob diese lang genug sind.
* Falls erfolgreich, wird die Registrierung durchgeführt
* Falls erfolglos, wird eine "Alert-Meldung" ausgegeben.
*/
$('#btn2').bind('click', function() {

	var password= $("#pw1").val();
	var confirm= $("#cp").val();

	if (password != confirm){
		$("#error1").html("Passwörter nicht identisch");
		return false;
	}
	
	if( password <8 ){
		$("#error2").html("Passwort zu klein!");
		return false;
	}
	console.log;
	var myJSON = {
	"pseudonym": $("#pn1").val(),
	"user": $("#email").val(),
	"password": password 
	};
	//Ip übergeben die eingegeben wurde
	var url= $("#url").val();
	
    alert(JSON.stringify(myJSON));
    $.ajax({
		url: "http://"+url+":5002/register",
		type: 'PUT',    
		data: JSON.stringify(myJSON),
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success: function(response) {
			alert("Registrierung erfolgreich!");
		},
		error: function(xhr, status, error){
			//alert(xhr.status);             
			//alert(error); 
			alert("Registrierung fehlgeschlagen. Sie müssen die Server-IP eingeben.");
		}
	});
	return false;
	
});