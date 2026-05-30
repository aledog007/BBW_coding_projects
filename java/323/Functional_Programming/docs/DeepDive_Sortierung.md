# Deep Dive: Wie Java im Hintergrund sortiert

### 1. Die normale Sortierung (Natural Order)

**Wie ich vorgegangen bin und was ich herausgefunden habe:**
Ich habe mich zuerst gefragt, was tief im Code passiert, wenn ich in meinem Programm einfach nur `Collections.sort(liste)` aufrufe. Um das herauszufinden, habe ich mich in meiner Entwicklungsumgebung durch den Quellcode von Java geklickt.

Die Folge davon war, dass ich gesehen habe, wie Java die Aufgabe immer weiter nach unten delegiert, bis der Code bei `Arrays.sort()` landet. Dort habe ich eine versteckte Klasse namens `ComparableTimSort` gefunden. Das Fazit meiner Analyse: Java geht hier einfach blind davon aus, dass meine Objekte das `Comparable`-Interface nutzen. Es ruft für alle Elemente in meiner Liste direkt meine eigene `compareTo()`-Methode auf. Anhand meiner Rückgabewerte (Minus, Null oder Plus) weiss Java dann, wie es die Elemente im Array anordnen muss.

* **Quelle/Link:** [Offizielle JavaDoc zu Comparable](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Comparable.html) -> vereinfachte Erklärung: [StackOverflow-Thread zu Comparable](https://stackoverflow.com/questions/2723397/what-is-the-comparable-interface-in-java) und Gemini zum besser verstehen der Logik dahinter.

### 2. Rückwärts sortieren (Reverse Order)

**Wie ich vorgegangen bin und was ich herausgefunden habe:**
Als Nächstes wollte ich wissen, wie Java eine bestehende Sortierung umdreht, wenn ich `Collections.reverseOrder()` benutze. Ich habe diesen Befehl im Code analysiert.

Dabei bin ich in den Tiefen von `java.util.Collections` auf eine geheime innere Klasse gestossen, die `ReverseComparator` heisst. Ich habe mir angeschaut, wie deren `compare()`-Methode geschrieben ist. Das Ergebnis war eine ziemliche Überraschung: Der komplizierte Sortier-Algorithmus von Java bleibt komplett gleich! Java wendet hier einfach einen simplen Trick an: Beim Vergleichen werden die beiden Objekte einfach vertauscht. Anstatt dass das Programm also rechnet "A vergleicht sich mit B", rechnet es "B vergleicht sich mit A". Die logische Folge davon ist, dass sich das ganze Endergebnis genau spiegelt und die Liste rückwärts sortiert wird.

* **Quelle/Link:** [Offizielle JavaDoc zu Collections.reverseOrder](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Collections.html#reverseOrder()) -> vereinfachte Erklärung: [StackOverflow-Thread zu ReverseComparator](https://stackoverflow.com/questions/1066589/how-does-collections-reverseorder-work) und Gemini zum besser verstehen der Logik dahinter.

### 3. Der Sortier-Algorithmus (TimSort)

**Wie ich vorgegangen bin und was ich herausgefunden habe:**
Schliesslich wollte ich herausfinden, welchen Algorithmus Java da eigentlich im Hintergrund benutzt. Ich habe im Internet recherchiert und herausgefunden, dass Java (seit Version 7) den sogenannten "TimSort"-Algorithmus verwendet.

Ich habe mir durchgelesen, wie dieser funktioniert. Es ist eine Mischung aus den bekannten Algorithmen *Merge Sort* und *Insertion Sort*. Ich habe gelernt, dass TimSort zuerst meine Liste durchsucht und nach kleinen Blöcken Ausschau hält, die zufällig schon sortiert sind (diese nennt man "Runs"). Sind diese Blöcke zu kurz, füllt er sie auf. Danach fügt er diese Blöcke clever zusammen. Die Folge dieses Aufbaus ist, dass Java extrem schnell sortiert, besonders wenn Listen (wie in der echten Welt oft üblich) schon ein bisschen vorsortiert sind.

* **Quelle/Link:** [Wikipedia-Artikel zu Timsort](https://de.wikipedia.org/wiki/Timsort) -> weiterer Artikel: [GeeksforGeeks-Artikel zu Timsort](https://www.geeksforgeeks.org/timsort/) und Gemini zum besser verstehen der Logik dahinter.

### 4. Zusammenfassung meiner Recherche

Um all diese Informationen zusammenzutragen, habe ich verschiedene Dinge getan. Ich habe direkt in meiner IDE den Quellcode vom OpenJDK analysiert, indem ich mit 'Ctrl + Klick' (bzw. 'Cmd + Klick') die Methodenaufrufe von Collections.sort() Schritt für Schritt zurückverfolgt habe. Um zu prüfen, ob ich die Logik im Quellcode richtig verstanden habe, habe ich anschliessend die offiziellen JavaDocs sowie StackOverflow gelesen und Gemini genutzt, um mir die komplexeren Details des TimSort-Algorithmus in einfachen Worten erklären zu lassen.