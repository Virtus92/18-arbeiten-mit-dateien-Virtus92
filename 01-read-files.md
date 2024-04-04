# Dateien aus dem Filesystem auslesen

In den verschiedenen Programmiersprachen gibt es unterschiedliche Methoden/Klassen, die sich mit dem Interagieren mit Dateien beschäftigen. Welche Klasse man wählt hängt in der Regel davon ab, welcher Anwendungsfall vorliegt:
- Wie groß ist die Datei?
- Ist der Inhalt der Datei strukturiert und wenn ja nach welchem Format
- Muss die Datei unbedingt gleich vollständig ausgelesen werden oder reicht es, wenn man diese Zeile für Zeile ausliest
- Sollen nur bestimmte Werte aus der Datei heraus extrahiert werden oder immer eine ganze Zeile?
- ...

Da Klassennamen und die Art, wie die Klassen arbeiten je Programmiersprache sehr unterschiedlich sind werden wir in diesem Kapitel je Programmiersprache ein eigenes Kapitel erstellen.

## Java

In Java werden 3 viel verwendete Klassen vorgestellt, die sich mit Dateien beschäftigen und auch auf ihre Anwendungsgebiete eingehen:
- `java.util.Scanner`
- `java.io.FileReader`
- `java.io.BufferedReader`
- `java.nio.file.Files`


### Scanner

Die `java.util.Scanner`-Klasse ist eine allgemeine Klasse, die darauf abzielt, Inputs der meistverwendeten Art und Weisen zu implementieren. So kennen wir diese Klasse bereits in Bezug auf die Konsoleneingaben. 

```
new Scanner(System.in);
```

Genauso können damit aber auch Dateien ausgelesen werden. Das hängt mit dem Objekt zusammen, das wir im Konstruktor mitgeben. Für Dateien verwenden wir ein `File`-Objekt:

```Java
// Java

import java.io.File;
import java.util.Scanner;

public class ScannerUsageWithFiles {
    public void readWithScanner() throws FileNotFoundException {
        File libraryFile = new File("library.txt");
        Scanner fileScanner = new Scanner(libraryFile);
        while (fileScanner.hasNextLine()) {
            System.out.println(fileScanner.nextLine());
        }
        fileScanner.close();
    }
}
```

Im Konstruktor der File-Klasse müssen wir den Pfad (relativ oder absolut) zur Datei mitgeben. Hier haben wir uns für den relativen Pfad entschieden: `"library.txt"`. 

![Projektstruktur](img/project-structure.png)

Der relative Pfad bezieht sich auf den Basispfad des Projekts. In diesem Falle der Ordner: `"C:\User\Digi-Mindset\Documents\Files"`, in dem sich die Datei "library.txt" befindet.

Anschließend kann ein Scanner mit `fileScanner.hasNextLine()` überprüfen, ob noch eine Zeile vorhanden ist. Man könnte andere Scanner-Methoden verwenden wie `nextInt()`. Allerdings macht dies die Arbeit mit Files wesentlich schwieriger. Manchmal ist das aber genau der Anwendungsfall, der vorliegt: Zahlen getrennt durch einen Leerzeichen in einer Datei. Dann macht die Nutzung eines Scanners und von `nextInt()` einen Sinn.

Um die nächste Zeile auszulesen verwendet man die Methode: `fileScanner.nextLine()`.

Die Klasse `Scanner` wird hier angeführt, weil sie bereits aus vorigen Kapiteln bekannt ist. Allerdings soll diese eher nur dann verwendet werden, wenn:
- Die speziellen Funktionalitäten von Scanner benötigt werden.
- Es sich um eine Textdatei (also keine Binaries).
- Es sich um eine kleinere Datei handelt.

**Referenz**: [Java-Referenz java.util.Scanner](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Scanner.html)

### FileReader

Eine weitere weitverbreitete Klasse, um Dateien auszulesen, ist der `java.io.FileReader`. Diese Klasse ist für den Fall gedacht, einen Stream an Buchstaben auszulesen.

Hier ein Beispiel:

```java
// Java

import java.io.FileReader;

public class FileReaderUsage {
    private void readWithFileReader() throws IOException {
        FileReader reader = new FileReader("library.txt");
        char[] content = new char[100];
        reader.read(content);
        System.out.println(content);
        reader.close();
    }
}
```

`FileReader` ist für kleinere Dateien gedacht, da das File auf einmal ausgelesen wird. Im Gegensatz zu den anderen vorgestellten Varianten gibt es hier keine Möglichkeit das File Zeile für Zeile auszulesen. 

Hier ist anzumerken:
- Im Constructor wird der Pfad zur Datei (hier auch: relativ oder absolut) angegeben.
- Die Datei wird anhand der Methode `read(Array)` ausgelesen und speichert den gelesenen Inhalt in das Array
- Das FileReader-Objekt muss am Ende durch die `close()`-Methode geschlossen werden.

**Referenz**: [Java-Referenz java.io.FileReader](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/FileReader.html)

### BufferedReader

Ein guter Weg, große Dateien zu öffnen, ist der `java.io.BufferedReader`. Er ist besonders praktisch für große Dateien, die man aufgrund des hohen Speicherbedarfs nicht auf einmal einlesen möchte. Er ist außerdem praktischer als der FileReader, da es vorgegebene Funktionen für das zeilenweise Lesen gibt: die häufigste Art des Zugriffs auf Dateien.

Um die Einschränkungen des FileReaders umzugehen, kann man den `BufferedReader` verwenden. Dieser ist insbesondere für das Auslesen großer Dateien geeignet, da die Datei Zeile für Zeile ausgelesen werden kann.

Zum Beispiel:

```java
// Java

import java.io.BufferedReader;
import java.io.FileReader;

public class BufferedReaderUsage {

    private void readWithBufferedReader() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("library.txt"));
        String currentLine = reader.readLine();
        while (currentLine != null) {
            System.out.println(currentLine);
            currentLine = reader.readLine();
        }
        reader.close();
    }
}
```

Der BufferedReader bietet uns folgende Methoden an:
- Zuerst muss dem BufferedReader ein Stream übergeben werden. In unserem Beispiel nehmen wir den `FileReader` vom letzten Beispiel. 
- Das Auslesen eine Zeile erfolgt mit der Methode `readLine()`, die einen String zurückgibt
- Am Ende muss der BufferedReader wie bei den anderen Klassen mit der `close()`-Methode geschlossen werden.

**Referenz**: [Java-Referenz java.io.BufferedReader](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/BufferedReader.html)

### Files

Die Klasse `java.nio.file.Files` ist eine Klasse, die statische Operationen auf Dateien, Ordner und andere Typen von Files anbietet. Diese Klasse bietet also mehr Möglichkeiten an, als die oben genannten Klassen. Man könnte sie so betrachten, wie eine Klasse, die alle Möglichkeiten eine File-Explorers bietet: Eine Klasse, die das Kopieren, Erstellen, Löschen von Dateien und Ordnern bietet, ...

In Bezug auf das Auslesen von Dateien bietet diese Klasse:
- die Methode `readAllLines()`, die alle Zeilen einer Datei auf einmal ausliest und eine Liste von Strings liefert.
- die Methode `lines()`, die zwar alle Zeilen einer Datei ausliest allerdings im Lazy-Modus, sodass jede Zeile erst zu dem Zeitpunkt ausgelesen wird, als sie gebraucht wird. Das ist besonders für große Dateien sehr gut geeignet.

Wichtig ist zu wissen, dass als Parameter kein Pfad als String mitgegeben wird, sondern ein Objekt vom Typ `Path`!

Folgende Beispiele zeigen, wie diese 2 Methoden verwendet werden bzw. ein Object vom Typ Path erstellt:

```java
// Java

import java.nio.file.Files;
import java.nio.file.Paths;

public class IOFilesUsage {
    private void readWholeFileWithFilesClass() throws IOException {
        List<String> allLines = Files.readAllLines(Paths.get("library.txt"));
        for (String line : allLines) {
            System.out.println(line);
        }
    }

    private void readSomeLinesWithFilesClass() throws IOException {
        Stream<String> allLines = Files.lines(Paths.get("library.txt"));
        allLines.skip(3L)
                .forEach(line -> System.out.println(line)); //Die jeweilige Zeile wird erst jetzt aus dem File gelesen
        allLines.close();
    }
}
```

**Referenz**: [Java-Referenz java.nio.file.Files](https://docs.oracle.com/javase/21/docs/api/java/nio/file/Files.html)

## C#

In C# gibt es ebenfalls mehrere Möglichkeiten, um Files auszulesen:
- `System.IO.StreamReader`
- `System.IO.TextReader`
- `System.IO.BinaryReader`
- `System.IO.File`

### `System.IO.StreamReader`

Mit einem StreamReader kann man Dateien Zeile für Zeile oder als gesamte Datei auslesen. 

```csharp
//C# - Beispiel Datei auslesen Zeile für Zeile
using System;
using System.IO;

class Program
{
    static void Main()
    {
        // Pfad zur Datei angeben
        string path = @"C:\example.txt";

        // StreamReader initialisieren und die Datei öffnen
        using (StreamReader sr = new StreamReader(path))
        {
            // Zeilenweise lesen, bis das Dateiende erreicht ist
            string line;
            while ((line = sr.ReadLine()) != null)
            {
                // Den gelesenen Inhalt verarbeiten
                Console.WriteLine(line);
            }
        }
    }
}
```

```csharp
//C# - Beispiel Datei vollständig auslesen
using System;
using System.IO;

class Program
{
    static void Main()
    {
        // Pfad zur Datei angeben
        string path = @"C:\example.txt";

        // StreamReader initialisieren und die Datei öffnen
        using (StreamReader sr = new StreamReader(path))
        {
            // Den gesamten Inhalt der Datei lesen
            string content = sr.ReadToEnd();
            // Den gelesenen Inhalt verarbeiten
            Console.WriteLine(content);
        }
    }
}
```

[Microsoft-Dokumentation](https://learn.microsoft.com/de-de/dotnet/api/system.io.streamreader?view=net-8.0)

### `System.IO.TextReader`

Wenn man speziell mit Text-Dateien arbeiten möchte ist die Klasse `System.IO.TextReader` besonders gut geeignet. Wie man damit eine Datei ausliest zeigen wir im folgenden Beispiel. Auch hier besteht die Möglichkeit sowohl die Datei Zeile für Zeile oder als Gesamtes auszulesen. 

```csharp
//C#
using System;
using System.IO;

class Program
{
    static void Main()
    {
        // Pfad zur Datei angeben
        string path = @"C:\example.txt";

        // TextReader initialisieren und die Datei öffnen
        using (TextReader reader = File.OpenText(path))
        {
            // Zeilenweise lesen, bis das Dateiende erreicht ist
            string line;
            while ((line = reader.ReadLine()) != null)
            {
                // Den gelesenen Inhalt verarbeiten
                Console.WriteLine(line);
            }
        }
    }
}

```

```csharp
// C#
using System;
using System.IO;

class Program
{
static void Main()
{
// Pfad zur Datei angeben
string path = @"C:\example.txt";

        // TextReader initialisieren und die Datei öffnen
        using (TextReader reader = File.OpenText(path))
        {
            // Den gesamten Inhalt der Datei lesen
            string content = reader.ReadToEnd();

            // Den gelesenen Inhalt verarbeiten
            Console.WriteLine(content);
        }
    }
}
```

[Microsoft-Dokumentation](https://learn.microsoft.com/de-de/dotnet/api/system.io.textreader?view=net-8.0)

### `System.IO.BinaryReader`

Im Falle einer Binary-Datei bietet uns `System.IO.BinaryReader` folgende Möglichkeit:

```csharp
// C#
using System;
using System.IO;

class Program
{
    static void Main()
    {
        // Pfad zur Binärdatei angeben
        string path = @"C:\example.bin";

        // BinaryReader initialisieren und die Datei öffnen
        using (BinaryReader reader = new BinaryReader(File.OpenRead(path)))
        {
            // Daten aus der Datei lesen
            byte[] buffer = new byte[1024]; // Puffer zum Lesen der Daten
            int bytesRead;
            while ((bytesRead = reader.Read(buffer, 0, buffer.Length)) > 0)
            {
                // Die gelesenen Daten verarbeiten (hier einfach ausgeben)
                Console.WriteLine("Gelesene Bytes: " + bytesRead);
                Console.WriteLine("Inhalt: " + BitConverter.ToString(buffer, 0, bytesRead));
            }
        }
    }
}
```

[Microsoft-Dokumentation](https://learn.microsoft.com/de-de/dotnet/api/system.io.binaryreader?view=net-8.0)

### `System.IO.File`

Die Klasse `System.IO.File` ist eine Utility-Klasse, die viele Möglichkeiten bietet, um mit Dateien zu arbeiten. Hier präsentieren wir, wie eine Datei vollständig ausgelesen wird. Im Gegensatz zum `StreamReader` bietet `File` keine Möglichkeit eine Datei Zeile für Zeile auszulesen.

```csharp
//C# Beispiel Zeile für Zeile
using System;
using System.IO;

class Program
{
    static void Main()
    {
        // Pfad zur Textdatei angeben
        string path = @"C:\example.txt";

        // Die Textdatei zeilenweise in ein String-Array lesen
        string[] lines = File.ReadAllLines(path);

        // Die gelesenen Zeilen verarbeiten
        foreach (string line in lines)
        {
            Console.WriteLine(line);
        }
    }
}
```

```csharp
//C# Beispiel ganze Datei auf einmal
using System;
using System.IO;

class Program
{
    static void Main()
    {
        // Pfad zur Textdatei angeben
        string path = @"C:\example.txt";

        // Den gesamten Inhalt der Textdatei lesen
        string content = File.ReadAllText(path);

        // Den gelesenen Inhalt verarbeiten
        Console.WriteLine(content);
    }
}
```


[Microsoft-Dokumentation](https://learn.microsoft.com/de-de/dotnet/api/system.io.file?view=net-8.0)

Zurück zur [Startseite](README.md)
