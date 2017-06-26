/*"A page can't be manipulated safely until the document is "ready." jQuery detects this state of readiness for you. 
Code included inside $( document ).ready() will only run once the page Document Object Model (DOM) is ready for JavaScript code to execute."*/
$(document).ready(function(){



//Wenn test vor $(".media-body")... dann funktioniert es.wieso?
test();
listCookies();
getCookie();

/**
*Falls der "Senden-Button" geklickt wird, wird die Funktion "sendMessage()"
*gerufen und ausgeführt.
*/
$(".send-button").bind("click",function() {
	sendMessage();
});

/**
*Es wird die Funktion "newChat(partner)" aufgerufen und
*es wird zu dem jeweiligen Partner "gewechselt", falls dieser angeklickt wird*/
$(".media-body").click(function() {
	//alert($(this).attr('id'));
	//$("#chatbox").empty();
	newChat($(this).attr('id'));
	
});

var pseudonym;
var token;
var expires;
var url;
var sequencenumber;
var chatpartner;
//Die Nachrichten die wir vom Chat Server bekommen.
var fetchedmessages =[];
//Um den Chatverlauf wiederherzustellen
var allChatMessages=[];


	//holt die Nachrichten vom Chatserver
	/**
	* Die Funktion holt die Nachrichten per "GET"-Aufruf von der jeweiligen URL, es wird ein "Authorization"-Header und ein Token 
	* hinzugefügt. Bei erfolg, werden die neuen Nachrichten per "displayMessages()" angezeigt. Bei Fehlschlag wird eine Fehlermeldung ausgegeben 
	* und es wird zur Startseite gewechselt. */
	function getMessages(){
		
		$.ajax({
			url: "http://"+url+":5000/messages/"+pseudonym,
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
				window.location.replace("index.html");
			}
	
		});
		
	}
	//https://stackoverflow.com/questions/7188145/call-a-javascript-function-every-5-seconds-continuously
	/**Die "getMessages() Funktion wird alle 5 Sekunden augerufen, um immer die neuesten Nachrichten anzuzeigen."*/
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
	//noch nicht implementiert...
	}
	*/
	
	/**Listet die jeweiligen Cookie-elemente als String und gibt diesen zurück*/
	function listCookies() {
    var theCookies = document.cookie.split();
	alert(theCookies);
    var aString = '';
    for (var i = 1 ; i <= theCookies.length; i++) {
        aString += i + ' ' + theCookies[i-1] + "\n";
    }
    return aString;
	}

	/**Splitet die jeweiligen Cookies nach Pseudonym, Ablaufdatum, Token, und der eingegeben URL des Servers. */
	function getCookie() {
		var cookies = document.cookie;
		var array = cookies.split(",");
		pseudonym=array[0].substring(10);
		expires=array[1].substring(9);
		token=array[2].substring(7);
		url=array[3].substring(5);
		
	}

	/**
	* Sendet die Nachrichten per "PUT" als JSON-Objekt an die angegebene URL.
	* Bei Erfolg wird dem Array "allChatMessages" die aktuelle Nachricht hinzugefügt und es werden die aktuellen Nachrichten angezeigt.
	* Bei Fehlschlag wird der Status-Code ausgegeben.*/
	function sendMessage(){
			
		if($("message").val()!=""){
			//https://stackoverflow.com/questions/3605214/javascript-add-leading-zeroes-to-date
			//https://stackoverflow.com/questions/1091372/getting-the-clients-timezone-in-javascript
			//https://stackoverflow.com/questions/13359294/date-getday-javascript-returns-wrong-day
			var d = new Date();
			var datestring = d.getFullYear()+"-"+("0"+(d.getMonth()+1)).slice(-2)+"-"+("0"+(d.getDate())).slice(-2)+
			"T"+d.getHours()+":"+d.getMinutes()+":"+("0"+(d.getSeconds())).slice(-2)+"+0200";
			
			var myJSON = {
				"token": token,
				"to":chatpartner,
				"from":pseudonym,
				"text": $("#message").val(),
				"date": datestring
				};
			
			$.ajax({
				url: "http://"+url+":5000/send",
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
											"date": datestring,
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

	/**
	* Es werden die aktuellen Nachrichten angezeigt, zuvor werden diese aus dem Array, in dem alle
	* Nachrichten vorhanden sind herausgefiltert (nach dem jeweiligen Pseudonym und Chatpartner). */
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
	
	/**Es werden am Anfang ein paar "hart-gecodete" Test-Kontakte hinzugefügt. */
	function test(){
		$("#contacts").append("<div class='contact btn'><div class='media-body' id ='gandalf'><div class='col-xs-3 col-sm-3 Benutzerbild' ><div class='Bild'>"+
                                "<img src='32.png'></div></div><h3 class='media-heading'>gandalf</h3></div></div>");
		$("#contacts").append("<div class='contact btn'><div class='media-body' id ='frodo'><div class='col-xs-3 col-sm-3 Benutzerbild' ><div class='Bild'>"+
                                "<img src='32.png'></div></div><h3 class='media-heading'>frodo</h3></div></div>");
		$("#contacts").append("<div class='contact btn'><div class='media-body' id ='peter'><div class='col-xs-3 col-sm-3 Benutzerbild' ><div class='Bild'>"+
                                "<img src='32.png'></div></div><h3 class='media-heading'>peter</h3></div></div>");
						
	}
 });