package ale.bbw.coding;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectIOExample {

    private static final String filepath = "/home/ale/Desktop/coding_bbw/java/320/how-to-read-an-object-from-file-in-java/obj/obj";

    public static void main(String[] args) {
        ObjectIOExample objectIO = new ObjectIOExample();

        // Neues Student-Objekt erstellen
        Student student = new Student("John", "Frost", 22);

        // Objekt in Datei schreiben
        objectIO.writeObjectToFile(filepath, student);

        // Objekt aus Datei lesen
        Student st = (Student) objectIO.readObjectFromFile(filepath);
        System.out.println(st);
    }

    public void writeObjectToFile(String filepath, Object serObj) {
        try (FileOutputStream fileOut = new FileOutputStream(filepath);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(serObj);
            System.out.println("Das Objekt wurde erfolgreich in die Datei geschrieben.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object readObjectFromFile(String filepath) {
        try (FileInputStream fileIn = new FileInputStream(filepath);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            Object obj = objectIn.readObject();
            System.out.println("Das Objekt wurde erfolgreich aus der Datei gelesen.");
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
