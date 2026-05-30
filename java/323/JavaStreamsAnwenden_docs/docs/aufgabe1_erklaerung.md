# Aufgabe 1 – Einfaches Balkendiagramm

## Was macht diese Aufgabe?

man erstellst ein simples Balkendiagramm mit JFreeChart. Die Daten kommen nicht aus einer Datei, sondern man definiert sie direkt im Code mit `Stream.of()`.

## Wie funktioniert das Ganze?

### Schritt 1: Daten als Stream erstellen

Statt eine CSV-Datei zu lesen, erstellen wir die Daten direkt:

```java
Stream.of("Java", "Python", "C++", "JavaScript")
```

Das gibt uns einen Stream mit vier Strings.

### Schritt 2: Die Daten in ein Dataset packen

JFreeChart braucht ein spezielles Datenobjekt, ein sogenanntes **Dataset**. Für ein Balkendiagramm ist das ein `DefaultCategoryDataset`.

Stell dir das Dataset wie eine Tabelle vor:

| Kategorie | Wert |
|-----------|------|
| Java | 30 |
| Python | 25 |
| C++ | 15 |
| JavaScript | 20 |

```java
DefaultCategoryDataset dataset = new DefaultCategoryDataset();
dataset.addValue(30, "Beliebtheit", "Java");
dataset.addValue(25, "Beliebtheit", "Python");
dataset.addValue(15, "Beliebtheit", "C++");
dataset.addValue(20, "Beliebtheit", "JavaScript");
```

Die drei Parameter bei `addValue()` sind:
1. **Wert** (die Zahl für den Balken)
2. **Reihe/Serie** (z.B. "Beliebtheit" – steht in der Legende)
3. **Kategorie** (z.B. "Java" – steht auf der X-Achse)

### Schritt 3: Chart erstellen

```java
JFreeChart chart = ChartFactory.createBarChart(
    "Programmiersprachen Beliebtheit",  // Titel
    "Sprache",                          // X-Achse Beschriftung
    "Punkte",                           // Y-Achse Beschriftung
    dataset                             // unsere Daten
);
```

### Schritt 4: Als Bild speichern oder anzeigen

```java
ChartUtils.saveChartAsPNG(
    new File("balkendiagramm.png"),
    chart,
    800,  // Breite
    600   // Höhe
);
```

## Welche Stream-Methode kommt wo vor?

In dieser Aufgabe ist der Stream-Teil noch recht simpel:

- **`Stream.of()`** – Um die Datenpunkte zu erstellen
- **`forEach()`** – Um jedes Element ins Dataset zu packen

```java
Stream.of(
    new String[]{"Java", "30"},
    new String[]{"Python", "25"},
    new String[]{"C++", "15"},
    new String[]{"JavaScript", "20"}
).forEach(daten -> {
    dataset.addValue(
        Integer.parseInt(daten[1]),
        "Beliebtheit",
        daten[0]
    );
});
```

## Was ist JFreeChart?

JFreeChart ist eine Java-Bibliothek zum Erstellen von Diagrammen. Sie ist in unserem `pom.xml` als Dependency eingetragen:

```xml
<dependency>
    <groupId>org.jfree</groupId>
    <artifactId>jfreechart</artifactId>
    <version>1.5.4</version>
</dependency>
```

Die wichtigsten Klassen:
- `ChartFactory` – Erstellt verschiedene Chart-Typen (Bar, Pie, Line...)
- `DefaultCategoryDataset` – Datenobjekt für Balken-/Liniendiagramme
- `DefaultPieDataset` – Datenobjekt für Kreisdiagramme
- `ChartUtils` – Zum Speichern als PNG

## Wie würde man das dem Lehrer erklären?

> "Ich erstelle mit `Stream.of()` einen Stream von Datenpunkten. Jeder Datenpunkt hat einen Namen und einen Wert. Mit `forEach()` packe ich jeden Datenpunkt in ein JFreeChart-Dataset. Dann erstelle ich mit `ChartFactory.createBarChart()` ein Balkendiagramm und speichere es als PNG."

Kurz gesagt: Stream erstellen → Daten ins Dataset → Chart bauen → Speichern.

---

## Zusammenfassung

- Aufgabe 1 ist der Einstieg – einfache Daten, einfacher Stream
- `Stream.of()` erstellt einen Stream aus festen Werten
- `forEach()` iteriert über den Stream und füllt das Dataset
- JFreeChart braucht immer: Dataset → ChartFactory → Speichern/Anzeigen
- Das `DefaultCategoryDataset` funktioniert wie eine Tabelle mit Wert, Reihe und Kategorie
