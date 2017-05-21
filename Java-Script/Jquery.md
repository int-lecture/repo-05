# Präsentation - jQuery
----------------------------------------------------------------------------

## jQuery - eine JavaScript-Bibliothek

* jQuery ist eine JavaScript-Bibliothek welche die Zusammenarbeit mit JavaScript, HTML und CSS sich zum Hauptaugenmerk macht und diese erleichtert.


## Eigenschaften von jQuery

* Kann mit Leichtigkeit verschiedene visuelle Effekte realisieren.
* Kann auf beliebige DOM-Elemente zugreifen und diese verändern. --> (Document Object Model) XML/HTML-Dokumente
* Eventhandling --> Gut geignet für Reaktionen auf Events (Mausclicks, Mausbewegungen etc.)
* Interaktion mit AJAX --> Eine Technologie, welche Interaktionen mit Server oder Webseite erlaubt, ohne die Seite neuladen zu müssen.
* Hat eine sehr große Fülle an JavaScript-Plugins (unter anderem für die Erstellung von Nutzer-Interfaces) 
<br /> (Quelle: http://www.site-do.ru/js/jquery1.php)

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
 
 
