# Schwierigkeiten und Schwächen 
****************************************************************************************************************                   
* PHP Undefined Fehler treten auf->Notlösung : error_reporting(0); (unschön).

* Man muss bei erneutem Besuch der Seite den Namen eingeben um die Kontaktliste zu sehen.(Cookies?).

* Es wird noch zwischen Groß-und Kleinschreibung unterschieden was dazu führt, 
 dass die Kontaktliste nicht angezeigt wird wenn man statt z.B.
 "John", "john" eingibt.Es wird also eine neue Textdatei angelegt.

* Beim Refreshen der Seite muss man runter scrollen um die aktuellen Nachrichten zu sehen.

* Wir haben den Clear button der zum Löschen des Chatverlaufs diente, entfernt, da der Chatverlauf beider Nutzer gelöscht wurde.

* Wenn man beim Login nur seinen Namen eingibt, wird die Kontaktliste nicht mehr angezeigt.

* Wir hatten Probleme mit den Zugriffsrechten des Ordners "Chatverlauf" die sich ständig änderten, 
 dadurch konnte nicht mehr auf die Textdateien zugegriffen werden.
 ->Durch 0767 gelöst.chmod(&pfad,0767) statt chmod(&datei,0767) Ausführen[x] hat gefehlt.

* Gibt man einen neuen Chatpartner ein wird dieser erst zu den Kontakten hinzugefügt wenn die Textdatei erstellt wurde.

* Wir hatten lange Schwierigkeiten einzelne "Chaträume" zu erstellen.Unsere erste Lösung bestand darin für jeden Chat 
 eine Chat.php Datei durch einlesen (file_get_contents) zu erstellen.Alle Zeilen wurden dann in ein Array gespeichert 
 und die Zeile die für die Anzeige des Chats zuständig war (iframe) durch eine neue ersetzt.
 Herr Prof. Smits hat uns dann erklärt wie es mit GET funktioniert.

* Man muss "nur" die Namen wissen um einen "Chatraum" zu betreten.

* Vermutung: Da für jeden Chat eine Datei erstellt wird, würde dies mit zunehmenden Nutzern zu schlechterer Performance führen.
 -> Speichern der Verläufe auf eine Datenbank.(Oder beim Nutzer?)
 -> Löschung der Verläufe nach einer bestimmten Zeit wodurch bei uns auch die Kontake gelöscht werden. 

* Manchmal funktioniert alles super und dann dauert es mehrere Sekunden bis eine eingetippte Nachricht angezeigt wird.
  Kann es sein das der Server für eine bestimmte zeit nicht mehr aktualisiert? 

* Eines der Schwierigkeiten war unteranderem eine "richtige" Vorgehensweise, oder die "beste" Art und Weise
  zu finden eine funktionierende Kontaktliste einzubinden bzw.zu implementieren. Und auch zu verstehen wie die Kontaktliste 
  (also im Falle unseres Chats) funktionieren soll und wie sie erstellt werden sollte.

* Mehrere Nutzer können in einem Account eingeloggt sein
