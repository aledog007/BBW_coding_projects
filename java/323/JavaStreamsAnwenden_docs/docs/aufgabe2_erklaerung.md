# Aufgabe 2 – Wahldaten als Kreisdiagramm

## Was macht diese Aufgabe?

Wir lesen die Nationalratswahl-Daten 2023 aus einer CSV-Datei, filtern nur die Gemeinde **Aeugst am Albis** raus, berechnen die Stimmen pro Partei und zeigen das als Kreisdiagramm (PieChart) an.

Die CSV-Datei heisst `KTZH_00000693_00004943.csv` und liegt unter `src/main/resources/`.

## Wie sieht die CSV-Datei aus?

```
"Datum","Geschaeft","Einheit_Code","Einheit_Name","Einheit_BFS","Einheit_BFSSub","Einheit_StimmenTotal","Partei","Partei_Kandstimmen","Partei_Zusatzstimmen","Partei_ParteistimmenTotal"
2023-10-22,"Nationalratswahl 2023","ZH001","Aeugst am Albis",1,1,28994,"SVP",9824,104,9928
2023-10-22,"Nationalratswahl 2023","ZH001","Aeugst am Albis",1,1,28994,"SP",3889,49,3938
```

Wichtig zu wissen:
- Erste Zeile = Header (muss übersprungen werden mit `skip(1)`)
- Spalte 3 (Index 3) = Einheit_Name (z.B. "Aeugst am Albis")
- Spalte 7 (Index 7) = Partei (z.B. "SVP")
- Spalte 10 (Index 10) = Partei_ParteistimmenTotal (die Zahl die wir brauchen)
- Manche Werte haben Anführungszeichen, manche nicht

## Schritt-für-Schritt durch die Stream-Pipeline

### Schritt 1: CSV-Datei lesen mit Files.lines()

```java
Path pfad = Path.of("src/main/resources/KTZH_00000693_00004943.csv");

Map<String, Long> stimmenProPartei = Files.lines(pfad)
```

`Files.lines()` liest die Datei Zeile für Zeile und gibt einen `Stream<String>` zurück. Jede Zeile ist ein String.

### Schritt 2: Header überspringen mit skip(1)

```java
    .skip(1)
```

Die erste Zeile ist der Header ("Datum","Geschaeft",...). Den brauchen wir nicht, also überspringen wir ihn.

### Schritt 3: Nur Aeugst am Albis mit filter()

```java
    .filter(zeile -> zeile.contains("Aeugst am Albis"))
```

Wir wollen nur die Zeilen, die "Aeugst am Albis" enthalten. Alles andere wird rausgefiltert.

### Schritt 4: Zeile in Teile splitten mit map()

```java
    .map(zeile -> zeile.split(","))
```

Jede Zeile wird am Komma aufgetrennt. Aus dem String wird ein String-Array.

Beispiel:
```
"2023-10-22,\"Nationalratswahl 2023\",\"ZH001\",\"Aeugst am Albis\",1,1,28994,\"SVP\",9824,104,9928"
                                    ↓ split(",")
["2023-10-22", "\"Nationalratswahl 2023\"", "\"ZH001\"", "\"Aeugst am Albis\"", "1", "1", "28994", "\"SVP\"", "9824", "104", "9928"]
```

### Schritt 5: Gruppieren und summieren mit collect()

```java
    .collect(Collectors.groupingBy(
        teile -> teile[7].replace("\"", ""),           // Parteiname (ohne Anführungszeichen)
        Collectors.summingLong(
            teile -> Long.parseLong(teile[10].replace("\"", ""))  // Stimmen
        )
    ));
```

Das ist der wichtigste Teil. Was passiert hier?

1. **groupingBy(teile -> teile[7])** – Gruppiert nach Parteiname (Index 7)
2. **summingLong(teile -> Long.parseLong(teile[10]))** – Summiert die Stimmen (Index 10) pro Gruppe

Das `.replace("\"", "")` entfernt die Anführungszeichen aus den Werten.

Das Ergebnis ist eine `Map<String, Long>`:
```
{SVP=9928, SP=3938, GP=2290, GLP=4206, FDP=3692, ...}
```

## Prozente berechnen

Für ein Kreisdiagramm brauchen wir Prozente. Die berechnen wir so:

```java
long total = stimmenProPartei.values().stream()
    .mapToLong(Long::longValue)
    .sum();

// Dann für jede Partei:
double prozent = (stimmen * 100.0) / total;
```

## Was ist ein PieChart?

Ein Kreisdiagramm – jedes Tortenstück zeigt den Anteil einer Partei.

```java
DefaultPieDataset dataset = new DefaultPieDataset();

stimmenProPartei.forEach((partei, stimmen) -> {
    double prozent = (stimmen * 100.0) / total;
    dataset.setValue(partei + " (" + String.format("%.1f", prozent) + "%)", stimmen);
});

JFreeChart chart = ChartFactory.createPieChart(
    "Nationalratswahl 2023 – Aeugst am Albis",  // Titel
    dataset,                                       // Daten
    true,                                          // Legende anzeigen
    true,                                          // Tooltips
    false                                          // URLs (brauchen wir nicht)
);
```

## Die ganze Pipeline nochmal im Überblick

```
CSV-Datei
  ↓ Files.lines()         → Stream<String> (jede Zeile)
  ↓ skip(1)               → Header weg
  ↓ filter()              → nur Aeugst am Albis
  ↓ map(split)            → Stream<String[]> (Zeile als Array)
  ↓ collect(groupingBy)   → Map<String, Long> (Partei → Stimmen)
  ↓ Prozente berechnen
  ↓ PieChart erstellen
  ↓ Speichern als PNG
```

---

## Zusammenfassung

- `Files.lines()` liest eine CSV-Datei zeilenweise als Stream
- `skip(1)` überspringt den Header
- `filter()` lässt nur bestimmte Zeilen durch (hier: Aeugst am Albis)
- `map(zeile -> zeile.split(","))` trennt die Zeile in einzelne Felder
- `groupingBy()` + `summingLong()` gruppiert nach Partei und summiert die Stimmen
- Anführungszeichen mit `.replace("\"", "")` entfernen
- Für Kreisdiagramme: `DefaultPieDataset` und `ChartFactory.createPieChart()`
