package ch.bbw.af;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {

        Land Italien = new Land( "Italien", 58850717 , "Pizza");
        System.out.println(Italien);

        List<Land> Landliste = new ArrayList<>();
        Landliste.add(new Land("Italien", 58850717 , "Pizza"));
        Landliste.add(new Land("Schweiz", 8703000, "Käse"));
        System.out.println(Landliste);

        System.out.println("\nThat was close!");
    }
}