# Aufgabe 4 – Wetterdaten als Liniendiagramm

## Was macht diese Aufgabe?

Wir generieren Wetterdaten (Temperatur pro Tag und Monat) direkt im Code, berechnen die Durchschnittstemperatur pro Monat und zeigen das Ganze als Liniendiagramm (LineChart) an.

## Warum generieren statt CSV?

Bei den anderen Aufgaben haben wir CSV-Dateien gelesen. Hier machen wir es anders: Wir erzeugen die Daten selber mit Streams. Das ist die "Kür-Aufgabe" und zeigt, dass Streams nicht nur zum Lesen von Dateien gut sind, sondern auch zum Generieren von Daten.

## Was ist ein Record?

Ein Record ist eine spezielle Art von Klasse in Java (ab Java 16). Stell dir vor, du willst eine Klasse die einfach nur Daten hält – keine Logik, nur Felder. Normalerweise musst du dafür eine ganze Klasse mit Konstruktor, Gettern und toString schreiben. Mit einem Record geht das in einer Zeile:

```java
// Normale Klasse (viel Code):
public class WetterDaten {
    private final String monat;
    private final int tag;
    private final double temperatur;
    
    public WetterDaten(String monat, int tag, double temperatur) {
        this.monat = monat;
        this.tag = tag;
        this.temperatur = temperatur;
    }
    
    public String monat() { return monat; }
    public int tag() { return tag; }
    public double temperatur() { return temperatur; }
    // ... toString, equals, hashCode ...
}

// Record (eine Zeile!):
record WetterDaten(String monat, int tag, double temperatur) {}
```

Der Record generiert automatisch:
- Konstruktor
- Getter (heissen gleich wie die Felder, also `monat()` statt `getMonat()`)
- `toString()`, `equals()`, `hashCode()`

## Die Stream-Pipeline zum Daten generieren

### Schritt 1: Monate definieren

```java
String[] monate = {"Jan", "Feb", "Mär", "Apr", "Mai", "Jun",
                   "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"};
```

### Schritt 2: IntStream.range() + flatMap() zum Generieren

```java
List<WetterDaten> daten = IntStream.range(0, 12)  // 0 bis 11 (12 Monate)
    .boxed()                                        // int → Integer (für flatMap)
    .flatMap(monat -> IntStream.range(1, 31)       // 30 Tage pro Monat
        .mapToObj(tag -> new WetterDaten(
            monate[monat],
            tag,
            basisTemp[monat] + (Math.random() * 10 - 5)  // Zufallstemperatur
        ))
    )
    .collect(Collectors.toList());
```

Ok, das sieht erstmal wild aus. Schritt für Schritt:

1. **`IntStream.range(0, 12)`** – Erzeugt die Zahlen 0 bis 11 (für jeden Monat eine)
2. **`.boxed()`** – Wandelt `int` in `Integer` um (braucht man damit `flatMap` funktioniert)
3. **`.flatMap(...)`** – Für JEDEN Monat erzeugen wir 30 Tage. `flatMap` "drückt" die verschachtelten Streams zu einem einzigen Stream platt.
4. **`IntStream.range(1, 31)`** – Erzeugt Tage 1 bis 30
5. **`.mapToObj(...)`** – Macht aus jedem Tag ein `WetterDaten`-Objekt
6. **`basisTemp[monat] + random`** – Basistemperatur des Monats plus/minus ein bisschen Zufall

Die Basistemperaturen könnten so aussehen:

```java
double[] basisTemp = {2, 3, 7, 12, 17, 21, 24, 23, 18, 12, 6, 3};
```

Im Januar ist es ca. 2°C, im Juli ca. 24°C usw. Die Zufallskomponente `Math.random() * 10 - 5` addiert einen Wert zwischen -5 und +5.

### Schritt 3: Durchschnittstemperatur pro Monat berechnen

```java
Map<String, Double> durchschnitt = daten.stream()
    .collect(Collectors.groupingBy(
        WetterDaten::monat,                          // Gruppieren nach Monat
        Collectors.averagingDouble(WetterDaten::temperatur)  // Durchschnitt berechnen
    ));
```

Hier kommt **`averagingDouble()`** zum Einsatz. Das berechnet automatisch den Durchschnitt aller Temperaturen pro Gruppe (= pro Monat).

Das Ergebnis:
```
{Jan=1.8, Feb=3.2, Mär=7.1, Apr=11.5, Mai=16.8, Jun=20.3, ...}
```

### Schritt 4: Liniendiagramm erstellen

```java
DefaultCategoryDataset dataset = new DefaultCategoryDataset();

// Monate in der richtigen Reihenfolge durchgehen
for (String monat : monate) {
    dataset.addValue(durchschnitt.get(monat), "Temperatur °C", monat);
}

JFreeChart chart = ChartFactory.createLineChart(
    "Durchschnittstemperatur pro Monat",  // Titel
    "Monat",                               // X-Achse
    "Temperatur (°C)",                     // Y-Achse
    dataset                                // Daten
);
```

Warum eine for-Schleife statt Stream? Weil die Monate in der richtigen Reihenfolge sein müssen (Jan, Feb, Mär...) und eine Map die Reihenfolge nicht garantiert.

## Die komplexen Streams erklärt

Der Trick bei dieser Aufgabe ist der `flatMap`. Stell dir das so vor:

```
Monat 0 (Jan) → erzeugt 30 WetterDaten-Objekte
Monat 1 (Feb) → erzeugt 30 WetterDaten-Objekte
Monat 2 (Mär) → erzeugt 30 WetterDaten-Objekte
...
```

Ohne `flatMap` hätten wir einen `Stream<Stream<WetterDaten>>` – also einen Stream von Streams. Das ist unbrauchbar. `flatMap` macht daraus einen einzigen `Stream<WetterDaten>` mit allen 360 Datenpunkten (12 Monate × 30 Tage).

## Die ganze Pipeline im Überblick

```
IntStream.range(0, 12)         → Monate als Zahlen (0-11)
  ↓ boxed()                    → Integer statt int
  ↓ flatMap(...)               → für jeden Monat 30 Tage generieren
    ↓ IntStream.range(1, 31)   → Tage 1-30
    ↓ mapToObj(...)            → WetterDaten-Record erstellen
  ↓ collect(toList)            → Liste mit 360 WetterDaten

daten.stream()
  ↓ groupingBy(monat)          → Gruppieren nach Monat
  ↓ averagingDouble(temp)      → Durchschnitt pro Monat
  → Map<String, Double>

Map → Dataset → LineChart → PNG
```

## Vergleich mit den anderen Aufgaben

| | Aufgabe 1 | Aufgabe 2 | Aufgabe 3 | Aufgabe 4 |
|---|-----------|-----------|-----------|-----------|
| Datenquelle | Stream.of() | CSV | CSV | Generiert |
| Chart-Typ | Bar | Pie | Bar | Line |
| Collector | - | summingLong | counting | averagingDouble |
| Besonderheit | Einstieg | Filter nach Gemeinde | Sortierung | flatMap + Record |

📌 **Aufgabe 4 zeigt**: Streams können auch Daten generieren, nicht nur verarbeiten. Das ist der entscheidende Unterschied zu den CSV-Aufgaben.

---

## Zusammenfassung

- Ein `record` ist eine kompakte Klasse nur für Daten (braucht keinen Boilerplate-Code)
- `IntStream.range()` erzeugt Zahlenfolgen, super zum Generieren
- `flatMap()` "plättet" verschachtelte Streams zu einem einzigen Stream
- `.boxed()` wandelt primitive Streams (int) in Objekt-Streams (Integer) um
- `averagingDouble()` berechnet den Durchschnitt pro Gruppe
- `ChartFactory.createLineChart()` erstellt ein Liniendiagramm
- Diese Aufgabe kombiniert alles: Records, IntStream, flatMap, groupingBy, averagingDouble
