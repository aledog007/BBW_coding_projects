# Aufgabe 3 – Nationalräte als Balkendiagramm

## Was macht diese Aufgabe?

Wir lesen die gewählten Nationalräte aus der CSV-Datei `chart-19-national-council-elected-ch-de.csv`, zählen wie viele Sitze jede Partei hat und zeigen das als Balkendiagramm an.

## Wie sieht die CSV-Datei aus?

```csv
"Name","Kanton","Partei","Geschlecht","Status","Kantons-Nr."
"Balmer Bettina","ZH","FDP","F","-","1"
"Portmann Hans-Peter","ZH","FDP","M","bisherig","1"
"Girod Bastien","ZH","GRÜNE","M","bisherig","1"
```

Wichtig: **Alle Werte stehen in Anführungszeichen!** Das ist der Unterschied zu Aufgabe 2, wo nur manche Werte Anführungszeichen hatten.

- Spalte 0 = Name
- Spalte 1 = Kanton
- **Spalte 2 = Partei** (die brauchen wir!)
- Spalte 3 = Geschlecht
- Spalte 4 = Status
- Spalte 5 = Kantons-Nummer

## Schritt-für-Schritt durch die Stream-Pipeline

### Schritt 1: CSV lesen und Header weg

```java
Path pfad = Path.of("src/main/resources/chart-19-national-council-elected-ch-de.csv");

Map<String, Long> sitzeProPartei = Files.lines(pfad)
    .skip(1)  // Header überspringen
```

Gleich wie bei Aufgabe 2 – `Files.lines()` liest die Datei, `skip(1)` überspringt den Header.

### Schritt 2: Zeile splitten und Anführungszeichen entfernen

```java
    .map(zeile -> zeile.split(","))
    .map(teile -> new String[]{
        teile[2].replace("\"", "").trim()  // nur Partei brauchen wir
    })
```

Oder einfacher – wir splitten und nehmen direkt die Partei:

```java
    .map(zeile -> {
        String[] teile = zeile.split(",");
        return teile[2].replace("\"", "").trim();  // Parteiname clean
    })
```

Aus `"FDP"` wird `FDP`. Das `.replace("\"", "")` entfernt die Anführungszeichen, `.trim()` entfernt Leerzeichen.

### Schritt 3: Gruppieren und ZÄHLEN

```java
    .collect(Collectors.groupingBy(
        partei -> partei,
        Collectors.counting()
    ));
```

Hier ist der grosse **Unterschied zu Aufgabe 2**:

| Aufgabe 2 | Aufgabe 3 |
|-----------|-----------|
| `summingLong()` – Stimmen addieren | `counting()` – Zeilen zählen |
| Jede Zeile hat einen Zahlenwert | Jede Zeile = 1 Person |

Bei Aufgabe 2 hatten wir Stimmen die wir zusammenrechnen mussten. Hier zählen wir einfach, wie oft jede Partei vorkommt. Jede Zeile = ein Nationalrat. Also: Zeilen zählen = Sitze zählen.

Das Ergebnis:
```
{SVP=62, SP=41, FDP=29, Mitte=29, GRÜNE=23, GLP=10, EVP=3, ...}
```

### Schritt 4: Sortieren

Damit das Diagramm schön aussieht, sortieren wir die Parteien nach Anzahl Sitze (absteigend):

```java
Map<String, Long> sortiert = sitzeProPartei.entrySet().stream()
    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
    .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (e1, e2) -> e1,
        LinkedHashMap::new  // LinkedHashMap behält die Reihenfolge!
    ));
```

Was passiert hier genau?

1. `entrySet().stream()` – Wir machen aus der Map wieder einen Stream (jedes Element ist ein Key-Value-Paar)
2. `sorted(comparingByValue().reversed())` – Sortieren nach Wert, absteigend (die Partei mit den meisten Sitzen zuerst)
3. `collect(toMap(...))` – Wieder in eine Map sammeln, aber diesmal eine `LinkedHashMap`, die die Reihenfolge beibehält

Eine normale `HashMap` hat keine feste Reihenfolge! Darum brauchen wir `LinkedHashMap`.

### Schritt 5: Balkendiagramm erstellen

```java
DefaultCategoryDataset dataset = new DefaultCategoryDataset();

sortiert.forEach((partei, sitze) -> {
    dataset.addValue(sitze, "Sitze", partei);
});

JFreeChart chart = ChartFactory.createBarChart(
    "Nationalratswahl 2023 – Sitze pro Partei",
    "Partei",
    "Anzahl Sitze",
    dataset
);
```

## Die ganze Pipeline im Überblick

```
CSV-Datei
  ↓ Files.lines()                     → Stream<String>
  ↓ skip(1)                           → Header weg
  ↓ map(split + replace)              → Stream<String> (nur Parteiname)
  ↓ collect(groupingBy + counting())  → Map<String, Long>
  ↓ entrySet().stream()               → Nochmal Stream
  ↓ sorted(byValue, reversed)         → Sortiert
  ↓ collect(toMap + LinkedHashMap)     → Sortierte Map
  ↓ forEach → Dataset füllen
  ↓ BarChart erstellen + speichern
```

## Vergleich: Aufgabe 2 vs. Aufgabe 3

| | Aufgabe 2 | Aufgabe 3 |
|---|-----------|-----------|
| Datenquelle | Wahldaten (Stimmen) | Nationalräte (Personen) |
| Diagramm | Kreisdiagramm (Pie) | Balkendiagramm (Bar) |
| Gruppieren nach | Partei | Partei |
| Collector | `summingLong()` | `counting()` |
| Sortierung | Keine | Nach Anzahl absteigend |
| CSV-Besonderheit | Gemischte Anführungszeichen | Alle Werte in Anführungszeichen |

📌 **Merke dir**: `counting()` = "Wie viele gibt es pro Gruppe?", `summingLong()` = "Was ist die Summe pro Gruppe?"

---

## Zusammenfassung

- CSV hat alle Werte in Anführungszeichen → mit `.replace("\"", "")` entfernen
- `Collectors.counting()` zählt die Elemente pro Gruppe (statt zu summieren)
- Sortierung geht über `entrySet().stream().sorted()` 
- `LinkedHashMap` behält die Sortier-Reihenfolge bei, eine normale `HashMap` nicht
- Jede Zeile in der CSV = ein Nationalrat, also Zeilen zählen = Sitze zählen
