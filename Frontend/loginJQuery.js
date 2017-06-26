
/*"A page can't be manipulated safely until the document is "ready." jQuery detects this state of readiness for you. 
Code included inside $( document ).ready() will only run once the page Document Object Model (DOM) is ready for JavaScript code to execute."*/
$(document).ready(function(){

});

/**
* Eine Funktion, die bei dem Anmelden, Werte der Felder: "E-mail" und "Passwort"
* dem User bzw. Passwort zuordnet und diese nutzt um sich per AJAX an der gegebenen URL anzumelden.
* Falls erfolgreich wird ein Cookie mit dem Pseudonym, dem Ablaufdatum und dem Token erstellt, und zum Chatfenster gewechselt.
* Falls erfolglos wird eine "Alert-meldung" ausgegeben.
*/
$('#btn1').bind('click', function() {
		var user = $("#email2").val();
		var myJSON={
			"user": user,
			"password": $("#pw2").val()
		};
		var url= $("#url").val();
		$.ajax({
			url: "http://"+url+":5001/login",
			type:"POST",
			data:JSON.stringify(myJSON),
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success : function(response) {
				//https://www.w3schools.com/js/js_cookies.asp
				document.cookie="pseudonym ="+response.pseudonym+
				",expires ="+response["expire-date"]+
				",token ="+response.token+",url ="+url;
				
				window.location.href ="Chat.html";
			},
			error : function(xhr, status, error){
				alert("Fehler beim Login. Sie müssen die Server-IP eingeben.");
				
			}
		});
		return false;
});