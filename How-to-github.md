# How-to: Verwenden von GitHub mit Eclipse
## 1. Herunderladen eines neuen Projekts
1. File -> Import -> git -> Clone URI -> https-Adresse von GitHub kopieren -> durch die Dialoge klicken (dabei Speicherort wählen)

## 2. Hochladen von Änderungen
0. Vor dem Hochladen auf mögliche Versionskonflikte über fetch prüfen und über merge beheben (s.u.)
1. Rechtsklick auf das Projekt -> Team -> Add to Index
2. Rechtsklick auf das Projekt -> Team -> Commit...
3. Im unten erscheinenden Dialog in einer Zeile die Änderungen beschreiben
4. Falls unter der Commit Notice nicht automatisch "Signed-off-by: [...]" erscheint, dies in den Einstellungen ändern
5. Commit and push...
6. Durch die Dialoge klicken. Bei Nachfrage nach den Benutzerdaten die GitHub-Accountdaten eingeben.

## 3. Herunterladen von Änderungen aus GitHub

### 3.1. Herunterladen über pull
* Diese Variante eignet sich, wenn es keine Versionskonflikte gibt, d.h. die Dateien, die ihr seit dem letzten Herunterladen verändert habt,
  hat sonst niemand aus der Gruppe geändert
  
1. Rechtsklick auf das Projekt -> Team -> pull

### 3.2. Herunterladen über fetch und merge
* Diese Variante eignet sich, falls jemand anders die Dateien, die ihr seit dem letzten Herunterladen verändert habt, auch bearbeitet hat.
* Im Zweifelsfall immer diese Variante verwenden, da diese Variante immer funktioniert!

1. Rechtsklick auf das Projekt -> Team -> fetch from Upstream
    * Im Anschluss könnt ihr über Team -> Compare to... die Versionen miteinander vergleichen
2. Rechtsklick auf das Projekt -> Team -> Merge
3. Die neuen Änderungen (Commits) werden angezeigt und können übernommen werden. Auf Versionskonflikte werdet ihr hingewiesen.
