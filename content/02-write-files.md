# Dateien ins Filesystem schreiben

In diesem Kapitel werden wir lernen, wie Dateien ins Filesystem geschrieben werden können.

Auch hier werden wir die verschiedenen Programmiersprache jeweils in einem eigenen Kapitel behandeln, da die jeweilige Arbeitsweise mit Dateien sich deutlich unterscheidet.

## Java

Wie gewohnt in Java, gibt es je Anwendungsfall eine eigene Klasse:
- `java.io.FileWriter`
- `java.io.BufferedWriter`
- `java.io.PrintWriter`
- `java.nio.file.Files`

### FileWriter

Analog zum FileReader gibt es auch eine `java.io.FileWriter`-Klasse.

> [!IMPORTANT]
> Wenn das File noch nicht existiert, wird es erstellt, 
> existiert es bereits, wird der Inhalt **überschrieben**.

```java
// Java

import java.io.FileWriter;

public class FileWriterUsage {
    private void writeWithFileWriter() throws IOException {
        FileWriter writer = new FileWriter("look-at-my-file.txt");
        char[] content = ("Look at my file\n"
                + "My file is amazing\n"
                + "Give it a lick").toCharArray();
        writer.write(content);
        writer.close();
    }
}
```

Analog zum FileReader ist die Funktionsweise der Klasse:
- der Konstruktor erstellt eine Datei unter dem angegebenen Pfad zur Datei bzw. sollte die Datei bereits vorhanden sein, öffnet er die Datei am angegebenen Pfad.
- die Methode `write(char[])`, um den Inhalt in das File zu schreiben.
- die Methode `close()`, um die Datei wieder freizugeben und andere den schreibenden Zugriff zu erlauben

**Referenz**: [Java-Referenz java.io.FileWriter](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/FileWriter.html)

### BufferedWriter

Auch hier finden wir das Pendant zum BufferedFileReader: `java.io.BufferedWriter`. Die Funktionsweise sieht folgendermaßen aus:

```java
// Java

import java.io.FileWriter;
import java.io.BufferedWriter;

public class FileWriterUsage {
    private void writeWithBufferedWriter() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("narwhals.txt"));
        writer.append("Narwhals, Narwhals\n");
        writer.append("Swimming in the ocean\n");
        writer.append("Causing a commotion\n");
        writer.append("'Cos they are so awesome");
        writer.close();
    }
}
```

Analog zum BufferedFileReader benötigt der `BufferedWriter` einen Stream im Konstruktor, der definiert, wohin die Daten hingeschreiben werden sollen. 

Mit der Methode `append(String)` wird der mitgegebene String in das File hineingeschrieben. Hier müssen aber die Zeilenumbrüche `\n` im mitgegebenen String beinhaltet sein. 

Analog zu allen Dateiverarbeitungsklassen, muss der BufferedWriter mit der Methode `close()` geschlossen werden.

**Referenz**: [Java-Referenz java.io.BufferedWriter](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/BufferedWriter.html)

### PrintWriter

Eine weitere Klasse, um in Files zu schreiben, in die Klasse `java.io.PrintWriter`. Sie benötigt einen Stream, in dem die Datei geschrieben werden soll. In unserem Beispiel wird ein FileWriter dafür herangezogen.

```java
// Java

import java.io.FileWriter;
import java.io.PrintWriter;

public class PrintWriterUsage {
    private void writeWithPrintWriter() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter("never-gonna-give-files-up.txt"));
        writer.println("Never gonna give you up");
        writer.println("Never gonna let you down");
        writer.println("Never gonna run around and desert you");
        writer.printf("%s (%d)", "Never gonna make you cry", 15);
        writer.close();
    }
}
```

Der Vorteil der Klasse `PrintWriter` ist, dass sie jene Methoden anbietet, die wir bereits aus den Konsolenausgaben kennen: `print([...])`, `println([...])` und `printf([...])` und diese funktionieren exakt gleich mit dem einzigen Unterschied, dass das Ergebnis in den Stream geschrieben wird, der im Konstruktor angegeben wurde.

Auch hier muss am Ende der Writer mit der Methode `close()` geschlossen werden!

**Referenz:** [Java-Referenz java.io.PrintWriter](https://docs.oracle.com/javase/8/docs/api/java/io/PrintWriter.html)

### Files

Die letzte Möglichkeit, die wir hier präsentieren ist wieder mit der Klasse `java.nio.file.Files`. Sie werden fragen, warum man nicht immer diese Klasse verwendet, da sie eigentlich ziemlich alles kann. Der Grund liegt daran, dass man immer versuchen sollte die möglichst spezifischsten Klassen zu verwenden, die für den Anwendungsfall genau geeignet sind. Und oft ist es auch eine Präferenz des Entwicklers...

```java
// Java

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class NIOFileFilesUsage {
    private void writeWithFilesClass() throws IOException {
        String text = "I wanna be the very best\n" +
                      "Like no one ever was\n" +
                      "To catch them is my real test\n" +
                      "To train them is my cause";
        Files.write(Path.of("gotta-catch-em-all.txt"), 
                    text.getBytes(StandardCharsets.UTF_8), 
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
```

In diesem Beispiel wird die Datei `gotta-catch-em-all.txt` erstellt, sofern sie noch nicht existiert (`StandardOpenOption.CREATE`). Weiters schreiben wir den Inhalt der Variable `text` ans Ende der Datei. Warum ans Ende? Weil wir die Option `StandardOpenOption.APPEND` verwenden.

**Referenz**: [Java-Referenz java.nio.file.Files](https://docs.oracle.com/javase/21/docs/api/java/nio/file/Files.html)

Hier die wichtigen Varianten von `StandardOpenOption` wären:
- `APPEND`: Ans Ende des Files hinzufügen.
- `CREATE`: Sollte das File nicht existieren, neues Files erstellen.
- `CREATE_NEW`: Wie CREATE mit dem Unterschied: Sollte das File bereits existieren wirft es einen Fehler.
- `DELETE_ON_CLOSE`: Das geöffnete File wird beim Close gelöscht.
- `READ`: Readonly.
- `WRITE`: Öffnen im WRITE-Modus (man kann in das File schreiben).

**Referenz**: [Java-Referenz java.nio.file.StandardOpenOption](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/StandardOpenOption.html)

## C#

In C# gibt es ebenfalls mehrere Möglichkeiten, um Dateien zu (be)schreiben:
- `System.IO.StreamWriter`
- `System.IO.TextWriter`
- `System.IO.BinaryWriter`
- `System.IO.File`

### `System.IO.StreamWriter`

```csharp
// C#
using System;
using System.IO;

class Program
{
    static void Main()
    {
        // Pfad zur zu erstellenden Datei angeben
        string path = @"C:\example.txt";

        // Den Inhalt, der in die Datei geschrieben werden soll
        string content = "Hello, world!";

        // StreamWriter initialisieren und die Datei erstellen
        using (StreamWriter writer = new StreamWriter(path))
        {
            // Den Inhalt in die Datei schreiben
            writer.WriteLine(content);
        }

        Console.WriteLine("Datei erfolgreich erstellt und Inhalt geschrieben.");
    }
}
```
[Microsoft-Dokumentation](https://learn.microsoft.com/de-de/dotnet/api/system.io.streamwriter?view=net-8.0)

### `System.IO.TextWriter`

```csharp
// C#
using System;
using System.IO;

class Program
{
    static void Main()
    {
        // Pfad zur zu erstellenden Datei angeben
        string path = @"C:\example.txt";

        // Den Inhalt, der in die Datei geschrieben werden soll
        string content = "Hello, world!";

        // TextWriter initialisieren und die Datei erstellen
        using (TextWriter writer = new StreamWriter(path))
        {
            // Den Inhalt in die Datei schreiben
            writer.WriteLine(content);
        }

        Console.WriteLine("Datei erfolgreich erstellt und Inhalt geschrieben.");
    }
}
```
[Microsoft-Dokumentation](https://learn.microsoft.com/de-de/dotnet/api/system.io.textwriter?view=net-8.0)

### `System.IO.BinaryWriter`

```csharp
// C#
using System;
using System.IO;

class Program
{
    static void Main()
    {
        // Pfad zur zu erstellenden Binärdatei angeben
        string path = @"C:\example.bin";

        // Die Binärdaten, die in die Datei geschrieben werden sollen
        byte[] data = { 0x48, 0x65, 0x6C, 0x6C, 0x6F, 0x2C, 0x20, 0x77, 0x6F, 0x72, 0x6C, 0x64, 0x21 };

        // BinaryWriter initialisieren und die Datei erstellen
        using (BinaryWriter writer = new BinaryWriter(File.OpenWrite(path)))
        {
            // Die Binärdaten in die Datei schreiben
            writer.Write(data);
        }

        Console.WriteLine("Datei erfolgreich erstellt und Inhalt geschrieben.");
    }
}
```

[Microsoft-Dokumentation](https://learn.microsoft.com/de-de/dotnet/api/system.io.binarywriter?view=net-8.0)

### `System.IO.File`

Die Klasse `System.IO.File` ist eine Utility-Klasse, die viele Möglichkeiten bietet, um mit Dateien zu arbeiten. Hier präsentieren wir, wie eine Datei geschrieben wird.

Eine Variante ist, dass der Inhalt der Datei auf einmal geschrieben wird:
```csharp
// C#
using System;
using System.IO;

class Program
{
    static void Main()
    {
        // Pfad zur zu erstellenden Datei angeben
        string path = @"C:\example.txt";

        // Den Inhalt, der in die Datei geschrieben werden soll
        string content = "Hello, world!";

        // Die Datei erstellen und den Inhalt schreiben
        File.WriteAllText(path, content);

        Console.WriteLine("Datei erfolgreich erstellt und Inhalt geschrieben.");
    }
}
```

Typischerweise zum Beispiel für CSV-Dateien kann man den Inhalt anhand eines Arrays von strings:
```csharp
// C#
using System;
using System.IO;

class Program
{
    static void Main()
    {
        // Pfad zur zu erstellenden Datei angeben
        string path = @"C:\example.txt";

        // Die Zeilen, die in die Datei geschrieben werden sollen
        string[] lines = { "Line 1", "Line 2", "Line 3" };

        // Die Datei erstellen und die Zeilen schreiben
        File.WriteAllLines(path, lines);

        Console.WriteLine("Datei erfolgreich erstellt und Inhalt geschrieben.");
    }
}
```
[Microsoft-Dokumentation](https://learn.microsoft.com/de-de/dotnet/api/system.io.file?view=net-8.0)

Zurück zur [Startseite](../README.md)
