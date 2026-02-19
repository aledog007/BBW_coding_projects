/**
 * Main Class Führt alle Rekursions-Beispiele zusammen
 * @author Alessio Fano
 * @version 19.02.2026
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Alles in einer Klasse :)\n");

        // 1. Beispiel: Palindrom (Statische Methode)
        // Nutzt die Signatur: isPalindrom(char[] data, int front, int rear)
        String wort = "OTTO";
        char[] chars = wort.toLowerCase().toCharArray();
        boolean istPalindrom = Palindrom.isPalindrom(chars, 0, chars.length - 1);
        System.out.println("1. Palindrom-Test (" + wort + "): " + istPalindrom);

        // 2. Beispiel: Grösster gemeinsamer Teiler (Statische Methode)
        // Nutzt die Signatur: gcd(int x, int y)
        int a = 28, b = 20;
        int ggtResultat = GGT.gcd(a, b);
        System.out.println("2. GGT von " + a + " und " + b + ": " + ggtResultat);

        // 3. Beispiel: Fibonacci (Instanz-Methode)
        // Nutzt die Signatur: calculate(int value)
        Fibonacci fibo = new Fibonacci();
        int stelle = 6;
        int fiboZahl = fibo.calculate(stelle);
        System.out.println("3. Fibonacci-Zahl an Stelle " + stelle + ": " + fiboZahl);

    }
}