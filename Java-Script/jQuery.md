# Präsentation - jQuery
----------------------------------------------------------------------------

## jQuery - eine JavaScript-Bibliothek

* jQuery ist eine JavaScript-Bibliothek welche die Zusammenarbeit mit JavaScript, HTML und CSS sich zum Hauptaugenmerk macht und diese erleichtert.
* Man muss es runterladen, um es nutzen zu können.
* Um es angenehmer zu nutzen, sollte man die Bibliothek dorthin entpacken, wo die eigenen HTML-Dokumente liegen.
* Ist zur Zeit in der aktuellen Version: 3.2.1

[![jQuery](https://github.com/int-lecture/repo-05/blob/master/Java-Script/jQuery.png)](http://jquery.com/)

## Eigenschaften von jQuery

* Kann mit Leichtigkeit verschiedene visuelle Effekte realisieren.
* Kann auf beliebige DOM-Elemente zugreifen und diese verändern. --> (Document Object Model) XML/HTML-Dokumente
* Eventhandling --> Gut geignet für Reaktionen auf Events (Mausclicks, Mausbewegungen etc.)
* Interaktion mit AJAX --> Eine Technologie, welche Interaktionen mit Server oder Webseite erlaubt, ohne die Seite neuladen zu müssen.
* Hat eine sehr große Fülle an JavaScript-Plugins (unter anderem für die Erstellung von Nutzer-Interfaces) 

## Syntax von jQuery

### $("selector").aktion("Eigenschaften_der_Aktion");

>Selector: Elemente, die bearbeitet, manipuliert oder verändert werden sollen (HTML-Elemente).
* Mehrere Selectoren durch Kommata, mehrere Aktionen durch "."-Schachtelung, sowie mehrere Eigenschaften der Aktionen durch Kommata möglich:
```jquery  
  $("selector1, selector2, ... selectorN").aktion.aktion2.aktion3("Eigenschaft1",..."EigenschaftN");
``` 
 >Filter: Elemente, welche die Arbeit mit Selektoren um einiges verfeinern.
 
 
 ## jQuery und CSS
 Ebenso wie in CSS kann man mit Hilfe von jQuery Mengen von Elementen/Objekten gleichzeitig mit einer Aktion bearbeiten oder verändern.
 
  * Um CSS-style-Elemente zu beinflußen gibt es die .css() Methode
  * css() - Methode gibt zurück oder setzt eine CSS-Eigenschaft:
  * Syntax (zurück geben): 
  ```jQuery
    css("propertyname");
  ```
  * Syntax (setzen): 
  ```jQuery
    css("propertyname", "value");
  ```
  * Beispiel:
  ```jQuery
    $("p").css("background-color", "yellow");
  ```
 ## jQuery und AJAX
 
  * Asynchronous JavaScript and XML.
  * Mit AJAX kann man Serveraustausch und Webseitenaktionen durchführen ohne Seite neuladen zu müssen
  * Beispiel: https://www.w3schools.com/jquery/jquery_ajax_intro.asp
  * load() - Methode die Inhalt eines Servers in einen ausgewählten Element läd.
  * Syntax: 
  ```jQuery
    $(selector).load(URL,data,callback);
  ```
  * get() - Methode sendet eine Anfrage an den Server mit einem HTTP GET Request.
  * Syntax: 
  ```jQuery
    $.get(URL,callback);
  ```
  * post() - Methode schickt einen HTTP POST Request an den Server. 
  * Syntax: 
  ```jQuery
    $.post(URL,data,callback);
  ```
  ## Einige Effekte von jQuery
 
  * draggable() - Plugin: http://viralpatel.net/blogs/demo/drag-drop-example.html
  * Beispiel-Code: 
  ```jQuery
  function init(){
    $("#les10_ex1").draggable();
  }
 ```
  * fadeIn(), fadeTo(), fadeOut() - http://www.mkyong.com/jquery/jquery-fadein-fadeout-and-fadeto-example/
  * Syntax: 
  ```jQuery
    $(selector).fadeIn(speed,callback);
  ```
  * Beispiel-Code
  ```jQuery
    function fadeOutDiv(){
        $('#les8_ex5').fadeOut(5000);
    }
    function fadeInDiv(){
      $('#les8_ex5').fadeIn(5000);
    }
    function fadeToDiv(){
      $('#les8_ex6').fadeTo(5000, 0.5);
    }
  ```
  * animate() - https://jqueryui.com/resources/demos/effect/animate.html
  *  Syntax: 
  ```jQuery
    $(selector).animate({params},speed,callback);
  ```
  * hide() - https://jqueryui.com/hide/
  * Syntax
   ```jQuery
    $(selector).hide(speed,callback);
  ```
  * Für viele weitere Effekte: 
  [![jQueryUI](https://github.com/int-lecture/repo-05/blob/master/Java-Script/jQueryUI.png)](https://jqueryui.com/)
  
 Quellen:
 * http://www.site-do.ru/js/jquery1.php
 * https://codyhouse.co/demo/back-to-top/index.html
 * http://www.mkyong.com/jquery/jquery-fadein-fadeout-and-fadeto-example/
 * https://www.w3schools.com/jquery
 * http://viralpatel.net/blogs/implement-drag-and-drop-example-jquery-javascript-html/
 * https://jqueryui.com/
 * www.jquery.com
         
 ![](https://img.clipartfest.com/f3d3929542dfdb6a4761649c5b95ac48_vielen-dank-fr-eure-danke-fr-die-aufmerksamkeit-clipart_960-720.jpeg)

