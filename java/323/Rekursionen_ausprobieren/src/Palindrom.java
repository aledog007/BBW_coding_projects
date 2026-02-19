/**
 * Palindrom Class
 * * @author Alessio Fano
 * @version 03.02.2026
 */
public class Palindrom {

    public static void main(String[] args) {
        String wort = "OTTO";
        char[] data = wort.toLowerCase().toCharArray();

        System.out.println("Überprüfe: " + wort);
        if (isPalindrom(data, 0, data.length - 1)) {
            System.out.println("Die Eingabe ist ein Palindrom");
        } else {
            System.out.println("Die Eingabe ist kein Palindrom");
        }
    }

    /**
     * Rekursive Prüfung auf Palindrom
     */
    public static boolean isPalindrom(char[] data, int front, int rear) {
        // Abbruchbedingung
        if (front >= rear) {
            return true;
        }
        // Rekursiver Schritt
        if (data[front] == data[rear]) {
            return isPalindrom(data, front + 1, rear - 1);
        }
        return false;
    }
}