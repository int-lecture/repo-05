//https://stackoverflow.com/questions/13056810/how-to-implement-a-put-call-with-json-data-using-ajax-and-jquery

$( document ).ready(function() {  
	
});

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
	
    alert(JSON.stringify(myJSON));
    $.ajax({
		url: "http://141.19.142.59:5002/register",
		type: 'PUT',    
		data: JSON.stringify(myJSON),
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success: function(response) {
			alert("Registrierung erfolgreich!");
			//zum Login Fenster wechseln
		},
		error: function(xhr, status, error){
			//alert(xhr.status);             
			//alert(error); 
			alert("Registrierung fehlgeschlagen");
		}
	});
	return false;
	
});