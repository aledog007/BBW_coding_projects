# Java Streams – Was ist das überhaupt?

## Die Grundidee

Stell dir ein **Förderband in einer Fabrik** vor. Auf dem Förderband liegen Teile (z.B. Schrauben). Die laufen durch verschiedene Stationen:

1. Station: Kaputte Schrauben aussortieren → `filter()`
2. Station: Schrauben anmalen → `map()`
3. Station: In eine Kiste packen → `collect()`

Genau so funktionieren Streams in Java. Du hast eine Liste mit Daten, und die laufen durch eine "Pipeline" von Operationen. Am Ende kommt das Ergebnis raus.

```
Liste → filter → map → collect → Ergebnis
```

## Warum Streams statt for-Schleifen?

Mit einer for-Schleife musst du alles selber machen:

```java
List<String> namen = List.of("Anna", "Bob", "Clara", "Anton");
List<String> ergebnis = new ArrayList<>();

for (String name : namen) {
    if (name.startsWith("A")) {
        ergebnis.add(name.toUpperCase());
    }
}
```

Mit Streams geht das kürzer und lesbarer:

```java
List<String> ergebnis = namen.stream()
    .filter(name -> name.startsWith("A"))
    .map(String::toUpperCase)
    .collect(Collectors.toList());
```

Beides macht das gleiche, aber der Stream-Code liest sich fast wie ein Satz:
"Nimm die Namen, filtere die mit A, mach sie gross, sammel sie ein."

---

## Die wichtigsten Stream-Methoden

### filter() – Elemente rausfiltern

Behält nur die Elemente, die eine Bedingung erfüllen. Alles andere fliegt raus.

```java
List<Integer> zahlen = List.of(1, 2, 3, 4, 5, 6);

List<Integer> geradeZahlen = zahlen.stream()
    .filter(z -> z % 2 == 0)
    .collect(Collectors.toList());
// Ergebnis: [2, 4, 6]
```

### map() – Jedes Element umwandeln

Nimmt jedes Element und macht was anderes draus. Wie eine Umwandlungsstation.

```java
List<String> namen = List.of("anna", "bob");

List<String> gross = namen.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());
// Ergebnis: ["ANNA", "BOB"]
```

### flatMap() – Verschachtelte Listen plattdrücken

Wenn du eine Liste von Listen hast und alles in eine einzige Liste packen willst.

```java
List<List<String>> verschachtelt = List.of(
    List.of("A", "B"),
    List.of("C", "D")
);

List<String> flach = verschachtelt.stream()
    .flatMap(Collection::stream)
    .collect(Collectors.toList());
// Ergebnis: ["A", "B", "C", "D"]
```

### collect() – Ergebnisse einsammeln

Sammelt die Ergebnisse am Ende der Pipeline ein. Meistens in eine Liste, Map oder Set.

```java
// In eine Liste:
.collect(Collectors.toList())

// In ein Set (keine Duplikate):
.collect(Collectors.toSet())

// In eine Map:
.collect(Collectors.toMap(key, value))
```

### reduce() – Alles auf einen Wert zusammenrechnen

Wenn du alle Elemente zu einem einzigen Wert kombinieren willst, z.B. eine Summe.

```java
List<Integer> zahlen = List.of(1, 2, 3, 4, 5);

int summe = zahlen.stream()
    .reduce(0, Integer::sum);
// Ergebnis: 15
```

### sorted() – Sortieren

Sortiert die Elemente. Standardmässig aufsteigend.

```java
List<String> namen = List.of("Clara", "Anna", "Bob");

List<String> sortiert = namen.stream()
    .sorted()
    .collect(Collectors.toList());
// Ergebnis: ["Anna", "Bob", "Clara"]

// Absteigend:
.sorted(Comparator.reverseOrder())

// Nach einem bestimmten Wert sortieren (z.B. Map.Entry):
.sorted(Map.Entry.comparingByValue())
```

### skip() – Zeilen überspringen

Überspringt die ersten N Elemente. Mega nützlich wenn du z.B. einen CSV-Header überspringen willst.

```java
List<String> zeilen = List.of("Name,Alter", "Anna,20", "Bob,22");

zeilen.stream()
    .skip(1)  // Header-Zeile weg
    .forEach(System.out::println);
// Gibt aus: Anna,20 und Bob,22
```

### forEach() – Für jedes Element etwas tun

Macht etwas mit jedem Element, gibt aber nichts zurück. Gut für Ausgaben oder Seiteneffekte.

```java
List<String> namen = List.of("Anna", "Bob");

namen.stream()
    .forEach(name -> System.out.println("Hallo " + name));
// Gibt aus: Hallo Anna, Hallo Bob
```

### Collectors.groupingBy() – Nach Kategorien gruppieren

Teilt die Daten in Gruppen auf. Gibt eine Map zurück.

```java
List<String> woerter = List.of("Apfel", "Birne", "Ananas", "Banane");

Map<Character, List<String>> gruppen = woerter.stream()
    .collect(Collectors.groupingBy(w -> w.charAt(0)));
// Ergebnis: {A=[Apfel, Ananas], B=[Birne, Banane]}
```

### Collectors.counting() – Zählen pro Gruppe

Kombination mit groupingBy: Zählt wie viele Elemente pro Gruppe vorhanden sind.

```java
List<String> parteien = List.of("SVP", "SP", "SVP", "FDP", "SP", "SVP");

Map<String, Long> anzahl = parteien.stream()
    .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
// Ergebnis: {SVP=3, SP=2, FDP=1}
```

### Collectors.summingLong() – Summe pro Gruppe

Summiert einen Long-Wert pro Gruppe auf. Nützlich bei Wahldaten etc.

```java
// Beispiel: Stimmen pro Partei summieren
.collect(Collectors.groupingBy(
    teile -> teile[7],  // Parteiname
    Collectors.summingLong(teile -> Long.parseLong(teile[10]))  // Stimmen
));
```

### Collectors.averagingDouble() – Durchschnitt pro Gruppe

Berechnet den Durchschnitt pro Gruppe.

```java
// Beispiel: Durchschnittstemperatur pro Monat
.collect(Collectors.groupingBy(
    wetter -> wetter.monat(),
    Collectors.averagingDouble(wetter -> wetter.temperatur())
));
```

### Stream.of() – Stream aus festen Werten

Wenn du keinen Stream aus einer Liste brauchst, sondern aus festen Werten.

```java
Stream.of("Hallo", "Welt", "!")
    .forEach(System.out::println);
```

### IntStream.range() – Zahlenbereich als Stream

Erzeugt einen Stream von Zahlen. Mega praktisch zum Generieren von Daten.

```java
IntStream.range(0, 5)
    .forEach(i -> System.out.println("Zahl: " + i));
// Gibt aus: 0, 1, 2, 3, 4

// range(0, 5) → 0 bis 4 (5 ist NICHT dabei!)
// rangeClosed(0, 5) → 0 bis 5 (5 IST dabei!)
```

### mapToDouble() – In Zahlen-Stream umwandeln

Wandelt einen normalen Stream in einen DoubleStream um. Nützlich für Berechnungen.

```java
List<String> preise = List.of("10.5", "20.3", "5.0");

double summe = preise.stream()
    .mapToDouble(Double::parseDouble)
    .sum();
// Ergebnis: 35.8
```

---

## 📌 Häufige Fehler – Aufpassen!

- **Stream schon verbraucht**: Ein Stream kann nur EINMAL benutzt werden. Danach ist er "leer". Wenn du ihn nochmal brauchst, musst du einen neuen erstellen.

```java
// FALSCH:
Stream<String> s = namen.stream();
s.filter(...);
s.map(...);  // FEHLER! Stream schon verbraucht!

// RICHTIG:
namen.stream().filter(...).map(...).collect(...);
```

- **collect() vergessen**: Ohne `collect()` oder `forEach()` am Ende passiert gar nichts. Streams sind "lazy" – sie machen erst was, wenn du ein Ergebnis abfragst.

- **Reihenfolge beachten**: Erst filtern, dann umwandeln ist effizienter als umgekehrt. Warum? Weil du weniger Elemente umwandeln musst.

```java
// GUT: erst filter, dann map
.filter(...)
.map(...)

// SCHLECHT: erst map, dann filter (unnötige Arbeit)
.map(...)
.filter(...)
```

- **Typen beachten bei parseLong/parseDouble**: Wenn ein String Anführungszeichen hat, musst du die zuerst entfernen mit `.replace("\"", "")`.

---

## Zusammenfassung

| Methode | Was macht sie? | Gibt zurück |
|---------|---------------|-------------|
| `filter()` | Elemente filtern | Stream |
| `map()` | Elemente umwandeln | Stream |
| `flatMap()` | Verschachtelte Streams plattmachen | Stream |
| `sorted()` | Sortieren | Stream |
| `skip()` | N Elemente überspringen | Stream |
| `forEach()` | Aktion pro Element | nichts (void) |
| `collect()` | Ergebnisse einsammeln | Liste/Map/Set |
| `reduce()` | Auf einen Wert reduzieren | ein Wert |
| `groupingBy()` | Nach Gruppen aufteilen | Map |
| `counting()` | Pro Gruppe zählen | Map mit Long |
| `summingLong()` | Pro Gruppe summieren | Map mit Long |
| `averagingDouble()` | Pro Gruppe Durchschnitt | Map mit Double |

Streams sind im Grunde einfach eine elegantere Art, Daten zu verarbeiten. Statt viele for-Schleifen zu schreiben, kettest du einfach Operationen aneinander – wie Stationen am Förderband.
