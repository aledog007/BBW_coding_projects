package ch.bbw.af;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Aufgabe 13.5.4
public class Main {
    public static void main(String[] args) {

        List<Flugzeug> FlugzeugeList = new ArrayList<>();
        FlugzeugeList.add(new Flugzeug("A380", 509 , Color.white));
        FlugzeugeList.add(new Flugzeug("A319", 231, Color.DARK_GRAY));
        System.out.println(FlugzeugeList);

        System.out.println("\nI did it right mister Rutschmann!");
    }
}