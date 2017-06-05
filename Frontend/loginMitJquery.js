$(document).ready(function(){

});


$('#btn1').bind('click', function() {
  
		var myJSON={
			"password":$("#pw2").val(),
			"user":$("#pn2")
		};
		
		$.ajax({
			url: "http://141.19.142.59:5001/login",
			type:"POST",
			data:JSON.stringify(myJSON),
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success : function(response) {
				//https://www.w3schools.com/js/js_cookies.asp
				document.cookie="pseudonym ="+response.pseudonym+
				",expires ="+response["expire-date"]+
				",token ="+response.token;
				//zur Chatbox weiterleiten
			},
			error : function(xhr, status, error){
				alert("Fehler beim Login");
				
			}
		});
		return false;
	
	
});