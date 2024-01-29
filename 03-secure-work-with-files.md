# Fehlerbehandlung und try with ressources

Wie im letzten Kapitel gelernt, ist es wichtig Fehlerfälle zu behandeln. Bei Datei-Operationen können durchaus Fehler auftreten:
- Die Datei kann nicht gefunden werden
- Die Datei ist derzeit nicht beschreibbar
- Die Festplatte ist voll
- ...
All diese Fehler sind solche, auf die wir innerhalb unseres Programms wenig Einfluss haben, da die Datei in einem Programm geöffnet ist und deswegen nicht beschreibbar ist. Oder wir haben die Datei angelegt und beim nächsten Mal öffnen, wurde die Datei manuell gelöscht, ... 

Daher ist es wichtig Fehler abzufangen und darauf zu reagieren.

## Java

In Java haben wir einen weiteren Vorteil bei der Nutzung des **try-Statements**: wir können Ressourcen angeben. Ein Beispiel sagt mehr als 1000 Worte:

```java
private static void readWithTryWithResource() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader("library.txt"))) {
        String currentLine = reader.readLine();
        while (currentLine != null) {
            System.out.println(currentLine);
            currentLine = reader.readLine();
        }
    }
}
```

Hier geben wir eine sogenannte Ressource an, die innerhalb des **try-Statements** verwendet wird. Der Riesenvorteil dieser Variante ist der, dass das geöffnete File automatisch geschlossen wird, wenn das try-Statement abgeschlossen ist und auch, wenn ein Fehler auftritt. Die Methode **.close()** muss nicht mehr aufgerufen werden!!!

Weitere Informationen zu diesem Thema findest du in diesem [Tutorial](https://www.baeldung.com/java-try-with-resources). Zum Beispiel, wie man try mit mehreren Ressources oder wie man eigene Typen von Ressources definiert.

Zurück zur [Startseite](README.md)
