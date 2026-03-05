/**
 * Fibonacci Class
 * @author Alessio Fano
 * @version 19.02.2026
 */
public class Fibonacci {

    public static void main(String[] args) {
        int value = 6;
        Fibonacci myFibonacci = new Fibonacci();
        System.out.println("Berechnen der " + value + ". Fibonacci-Zahl:");
        System.out.println("Ergebnis: " + myFibonacci.calculate(value));
    }

    public int calculate(int value) {
        // Abbruchbedingung
        if (value <= 1) {
            return value;
        }
        return calculate(value - 1) + calculate(value - 2);

        // Treppe AI Genereriert:
        // Rekursiver Aufruf: Summe der zwei vorhergehenden Zahlen
        //
        // Beispiel für value = 6:
        // calculate(6)
        //    ├─ calculate(5)
        //    │    ├─ calculate(4)
        //    │    │    ├─ calculate(3)
        //    │    │    │    ├─ calculate(2)
        //    │    │    │    │    ├─ calculate(1) → 1
        //    │    │    │    │    └─ calculate(0) → 0
        //    │    │    │    │    (zurück: 1)
        //    │    │    │    └─ calculate(1) → 1
        //    │    │    │    (zurück: 2)
        //    │    │    └─ calculate(2)
        //    │    │         ├─ calculate(1) → 1
        //    │    │         └─ calculate(0) → 0
        //    │    │         (zurück: 1)
        //    │    │    (zurück: 3)
        //    │    └─ calculate(3)
        //    │         └─ ...
        //    │    (zurück: 5)
        //    └─ calculate(4)
        //         └─ ...
        //    (zurück: 8)
        //
    }
}