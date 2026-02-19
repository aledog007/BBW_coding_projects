/**
 * Fibonacci Class
 * @author Alessio Fano
 * @version 03.02.2026
 */
public class Fibonacci {

    public static void main(String[] args) {
        int value = 6;
        Fibonacci myFibonacci = new Fibonacci();
        System.out.println("Berechnen der " + value + ". Fibonacci-Zahl:");
        System.out.println("Ergebnis: " + myFibonacci.calculate(value));
    }

    public int calculate(int value) { // 
        // Abbruchbedingung
        if (value <= 1) {
            return value;
        }
        // Rekursiver Aufruf: Summe der zwei vorhergehenden Zahlen
        return calculate(value - 1) + calculate(value - 2);
    }
}