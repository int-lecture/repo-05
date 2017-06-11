$(document).ready(function(){



//Wenn test vor $(".media-body")... dann funktioniert es.wieso?
test();
listCookies();
getCookie();


$(".send-button").bind("click",function() {
	sendMessage();
});

$(".media-body").click(function() {
	//alert($(this).attr('id'));
	//$("#chatbox").empty();
	newChat($(this).attr('id'));
	
});

var pseudonym;
var token;
var expires;
var sequencenumber;
var chatpartner;
var fetchedmessages =[];
var allChatMessages=[];

	//holt die Nachrichten vom Chatserver
	function getMessages(){
		
		$.ajax({
			url: "http://141.19.142.59:5000/messages/"+pseudonym,
			type:"GET",
			//https://stackoverflow.com/questions/5507234/how-to-use-basic-auth-with-jquery-and-ajax
			headers: {
				"Authorization": "Token "+token
				},
			contentType: "application/json; charset=utf-8",
			dataType:"json",
			success : function(response, textStatus, xhr){
				//alert("Erfolg");
				fetchedmessages=response;
				displayMessages()
			},
			error : function (xhr,status,error){
				alert("Fehler");
			}
	
		});
		
	}
	//https://stackoverflow.com/questions/7188145/call-a-javascript-function-every-5-seconds-continuously
	setInterval(function() {
		getMessages();
	}, 5000);
	
	function newChat(partner){
		
		chatpartner= partner;
		document.getElementById("chatpartner").innerHTML=chatpartner;
		getMessages();
	}
	
	
	/*
	function addNewContacts(){
	
	}
	*/
	
	function listCookies() {
    var theCookies = document.cookie.split();
	alert(theCookies);
    var aString = '';
    for (var i = 1 ; i <= theCookies.length; i++) {
        aString += i + ' ' + theCookies[i-1] + "\n";
    }
    return aString;
	}

	function getCookie() {
		var cookies = document.cookie;
		var array = cookies.split(",");
		pseudonym=array[0].substring(10);
		expires=array[1].substring(9);
		token=array[2].substring(7);
	}

	function sendMessage(){
		
		
			
		if($("message").val()!=""){
			var myJSON = {
				"token": token,
				"to":chatpartner,
				"from":pseudonym,
				"text": $("#message").val(),
				"date": "2017-06-09T15:33:01+0200"
				};
			
			$.ajax({
				url: "http://141.19.142.59:5000/send",
				type: 'PUT',
				data: JSON.stringify(myJSON),
				contentType: "application/json; charset=utf-8",
				dataType: 'json',
				success : function(result, textStatus, xhr){
					//alert("es geht!");
					allChatMessages.push({
											"token": token,
											"to":chatpartner,
											"from":pseudonym,
											"text": $("#message").val(),
											"date": new Date().toISOString(),
											"sequence":result.sequence
										});
					displayMessages();
				},
				error : function(xhr,status,error){
					alert(status);
				}
			});
		}else{
			//tu nichts
		}
		
	}

	
	function displayMessages(){
		//https://api.jquery.com/jquery.merge/
		//https://stackoverflow.com/questions/14651162/what-is-the-difference-bewteen-jquery-merge-and-javascript-native-function-con
		allChatMessages=allChatMessages.concat(fetchedmessages);
		$("#chatbox").empty();
		//http://api.jquery.com/JQuery.each/
		$.each(allChatMessages, function(index,value) {
			if(value.to==pseudonym&&value.from==chatpartner){
			$("#chatbox").append("<div class='msg'><div class='media-body'><small class='pull-right time'><i class='fa fa-clock-o'></i>"+ value.date+"</small>"+
                                 "<h4 class='media-heading'>"+ value.from +"</h4><small class='col-sm-11'>"+value.text+"</small></div></div>");
			}
			if(value.to==chatpartner&&value.from==pseudonym){
			$("#chatbox").append("<div class='msg'><div class='media-body'><small class='pull-right time'><i class='fa fa-clock-o'></i>"+ value.date+"</small>"+
                                 "<h4 class='media-heading'>"+ value.from +"</h4><small class='col-sm-11'>"+value.text+"</small></div></div>");
			}
		});
			
	}
	
	function test(){
		$("#contacts").append("<div class='contact btn'><div class='media-body' id ='gandalf'><div class='col-xs-3 col-sm-3 Benutzerbild' ><div class='Bild'>"+
                                "<img src='32.png'></div></div><h3 class='media-heading'>gandalf</h3></div></div>");
		$("#contacts").append("<div class='contact btn'><div class='media-body' id ='frodo'><div class='col-xs-3 col-sm-3 Benutzerbild' ><div class='Bild'>"+
                                "<img src='32.png'></div></div><h3 class='media-heading'>frodo</h3></div></div>");
		$("#contacts").append("<div class='contact btn'><div class='media-body' id ='peter'><div class='col-xs-3 col-sm-3 Benutzerbild' ><div class='Bild'>"+
                                "<img src='32.png'></div></div><h3 class='media-heading'>peter</h3></div></div>");
						
	}
 });