# Fehlerbehandlung und try with ressources

Wie im Kapitel Exception handling gelernt, ist es wichtig Fehlerfälle zu behandeln. Bei Datei-Operationen können durchaus Fehler auftreten:
- Die Datei kann nicht gefunden werden
- Die Datei ist derzeit nicht beschreibbar
- Die Festplatte ist voll
- ...

All diese Fehler sind solche, auf die wir innerhalb unseres Programms wenig Einfluss haben. Z.B. möchten wir auf eine Datei zugreifen und diese Datei ist in einem anderen Programm geöffnet und deswegen nicht beschreibbar. Oder wir haben die Datei angelegt und beim nächsten Mal öffnen, wurde die Datei manuell gelöscht, ... 

Daher ist es wichtig Fehler abzufangen und darauf zu reagieren.

## Java

In Java haben wir einen weiteren Vorteil bei der Nutzung des `try`-Statements: wir können Ressourcen angeben. Das folgende Beispiel verdeutlicht es:

```java
// Java 

import java.io.BufferedReader;
import java.io.FileReader;

public class BufferedReaderUsage {
    private static void readWithTryWithResource() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("library.txt"))) {
            String currentLine = reader.readLine();
            while (currentLine != null) {
                System.out.println(currentLine);
                currentLine = reader.readLine();
            }
        }
    }
}
```

Hier geben wir eine sogenannte Ressource an, die innerhalb des `try`-Statements verwendet wird. Der Vorteil dieser Variante ist, dass das geöffnete File automatisch geschlossen wird, wenn das `try`-Statement abgeschlossen ist und auch, wenn ein Fehler auftritt. Die Methode `close()` muss nicht mehr aufgerufen werden!!!

Weitere Informationen zu diesem Thema findest du in diesem [Tutorial](https://www.baeldung.com/java-try-with-resources). Zum Beispiel, wie man try mit mehreren Ressources oder wie man eigene Typen von Ressources definiert.

## C#

In C# funktioniert es geringfügig anders. Um eine Ressource zu verwenden, kommt das `using`-Statement zur Anwendung. Der `try-catch-finally`-Block wird dann rund herum gesetzt.

```C#
// C# 
using System;
using System.IO;

public class StreamReaderUsage
{
    private static void ReadWithTryWithResource()
    {
        try 
        {
            using (StreamReader reader = new StreamReader("library.txt"))
            {
                string currentLine = reader.ReadLine();
                while (currentLine != null)
                {
                    Console.WriteLine(currentLine);
                    currentLine = reader.ReadLine();
                }
            }
        } catch (Exception ex) 
        {
            // Exception handling
        }
    }
}
```

Zurück zur [Startseite](../README.md)
