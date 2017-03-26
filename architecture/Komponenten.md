# Komponentenbeschreibung

 * UI:
 Benutzerobfläche, (Schnittstele) über die die Nutzer miteinander und mit dem Programm (aber auch Hardware: Server, Datenbank) kommunizieren. 
 Aufgabe: Erleichterte Kommunikation und Bediengung (für ua. "Nicht-Informatiker) zwischen einander und Zugriff auf Server und Daten. Veranschaulichung der Funktionen des Programms, damit es besser/einfacher nutzbar ist. 
 Genutzte Daten: Eingaben und Eingabeaufforderungen bzw. Befehle von den Nutzer/ Benutzerdaten/Kommunikationsdaten (Fotos... Videos) etc.
 
 * Datenbank:
 Speichert bzw. enthält sämtliche Daten (wie Benutzerprofile, Chatverläufe, Medien, URLs....) und stellt auch diese zur Verfügung an den Nutzenden oder Anfragenden dar. Datenbank kann auch Daten empfangen - Es findet unter anderem ein Austausch statt.
   Arbeitet mit sämtlichen Daten, die sie enthält und die für das Programm relevant sind. Arbeitet auch mit langlebigen und "großen" Daten wie: Fotos, Musik, Videos.
   
  * Load - Balancer:
  Dient zur Verteilung des Daten- und Netzwerkverkehrs auf den Servern, um diese etwas zu entlasten. Steigert auch die Performance und erhöht Skallierbarkeit und Kapazität des Projekts. 
  Daten mit den gearbeitet wird: Verbindungen, URLs, Nutzeranfragen, Transportkanäle.
  
  * Server:
  Ist ein System (mit eigenem Betriebssystem, mit eigener Festplatte, Prozessor etc.), welches bestimmte Dienste an seine   Nutzer/Clients zur Verfügung stellt: wie Datenlagerung, Internetverbindungen etc.
  Aufgaben: Host-Server, Internet-Provider-Server, Daten-Server
  Arbeitet mit den Daten, die die der Server evtl. hat (z.B. Profildaten, Nachrichten). Oder Verbindungen und IP der Benutzer sowie Internet-Traffic-Daten, wenn dieser Internet-Dienste an seine Nutzer gewährt.
  
  * Rechner:
  Ist die Computereinheit - die der menschliche Nutzer bedient um das Chat-Programm zu benutzen. (Auf diesem wird der Chat-Messenger installiert)
  Arbeitet mit den Installationsdaten des Programms - sowie auch sämtlichen nutzenden und versendeten Daten (Fotos, Videos etc..)
  
  * Verschlüsselung:
  Dient dazu Daten mit Hilfe eines Chiffres für einen dritten nicht lesbar zu machen. Die Daten werden bevor sie versendet werden mit dem Chiffre verschlüsselt ( z.B. Buchstaben durch Zahlen ersetzen), und bei Ankunft am Anfänger mit dem selben Chiffre wieder entschlüsselt.
  
  * Authentifizierung:
  Wird benutzt um sicherzustellen, dass bestimmte Daten nur für die dazugehörigen Nutzer sichtbar sind. Erfolgt meistens über Abfrage von Name und Passwort.
  
  * Account-Manager:
  Ist ein Instrument, dass verschiedene Accounts(Konten) der Bentzer verwaltet. 
  Aufgaben: Anlegen neuer Accounts; Änderung der Accounts; Löschen des Accounts; Import und Export des Accounts. 
  Arbeitet mit den Daten der Benutzer (Profil-Daten) sowie Daten anderer Benutzer (Freundeliste etc.)
  
  * Nachrichten-Verteiler:
  Ist ein Instrument, welches dafür Zuständig ist bestimmte Nachrichten (auch Foto- und Video-) an Personengruppen zu verschicken.
  Ist in der Lage diese Personen einzuordnen und auszuwählen.
  Arbeitet mit den Daten der Benutzer (Kontakt-, Freundelisten) Aber auch Profildaten und Nachrichten-Daten.
  
  * Statische Inhalte:
  Sind Daten, die kaum verändert oder gelöscht werden. (Langlebige Daten)
  Daten: Einstelungen, Profildaten etc. 
  
  
  
  # Komponenten die man mehrmals verwenden kann
  
  * Zuviele Nutzeranfragen können zu Performanceverlust führen.Dies könnte man durch mehrere Webserver verhindern.
  * Für mehrere Benutzer - mehrere UIs.
  
  




