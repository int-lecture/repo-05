$(document).ready(function){
var cookie = document.cookie;
var pseudonym;
var sequencenumber;


	function getMessages(){
		$.ajax({
			url: "http://141.19.142.59:5000/messages/"+//pseudonym und sequence
			type:"GET",
			data:JSON.stringify(myJSON),
			dataType:"json",
			succes : function(response){
		
			//chatverlauf anzeigen
		
			},
			error : function (xhr,status,error){
				alert(status);
			}
	
	
		});
		//return?
	}


	function sendMessage(){
		var text = $("text").val(); //id muss rein
		
		var myJSON = {
			"token"
			"to": $("#pn").val(),
			"from": $("#email").val(),
			"text": password ,
			"sequence":
			
			};
		
		$.ajax({
			url: "http://141.19.142.59:5000/send",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			dataType:"json",
			succes : function(response){
				
			},
			error : function(xhr,status,error){
				alert(status);
			}
		});
		//return?
	}
	
	function displayMessages(var text){
		
		//div erstellen mit Text
		
	}
		

 });