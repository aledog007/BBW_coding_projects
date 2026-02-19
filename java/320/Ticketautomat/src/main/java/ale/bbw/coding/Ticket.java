package ale.bbw.coding;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Ticket {
    boolean halbtax;
    boolean firstClass;
    double price;

    //setter Methoden werden gebraucht um eine Variable zu setzen die Getter Methode wiederum um die Variable zu erhalten
    public void setHalbtax(boolean halbtax){
        this.halbtax = halbtax;
    }

    public boolean getHalbtax() {
        return this.halbtax;
    }

    public void setFirstClass(boolean firstClass) {
        this.firstClass = firstClass;
    }

    public boolean getFirstClass() {
        return this.firstClass;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void summaryTicket() {
        Location location = new Location();
        HashMap<String, List<AbstractMap.SimpleEntry<String, Double>>> locations = location.getLocations();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bitte gib die Postleizahl ein wo du hin möchtest (8000, 8600 oder 8180): ");

        // String userInput = scanner.next();
        String userInput = scanner.nextLine();

        System.out.println("Bitte gib die Postleizahl ein wo du hin möchtest (8000, 8600 oder 8180): " + locations.get(userInput));

        //TODO Frag den Lehrer oder überlege die implementation mit der Logik
        if (userInput == "8000") {
            double sum = locations.get(userInput).getFirst().getValue();

            System.out.println("möchtest du ein Halbtax (Ja/Nein): ");
            String userHalbtax = scanner.nextLine();

            System.out.println("Möchtest du ein 1.Klass Bilet (Ja/Nein): ");
            String userFirstClass = scanner.nextLine();

            if ("Ja" == userHalbtax) {
                sum = sum * 0.5;
                System.out.println(sum);
            }
            if ("Ja" == userFirstClass) {
                sum = sum  * 2;
                System.out.println(sum);
            }


        }
        //System.out.println(locations.get("8000"));
    }
}
