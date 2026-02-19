package ch.bbw.af;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Aufgabe 13.5.0
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello People my Name is Alessio Fano and today I instruct you in cars");

        System.out.println("");

        List<Cars> germancars = new ArrayList<>();
        Cars Porsche = new Cars( "Porsche", 385 ,340, Color.black);
        Cars Audi = new Cars( "Audi", 231, 250, Color.white);
        Cars Mercedes = new Cars( "Mercedes", 635 ,350, Color.gray);
        Cars BMW = new Cars( "BMW", 385 ,314, Color.red);
        germancars.add(Porsche);
        germancars.add(Audi);
        germancars.add(Mercedes);
        germancars.add(BMW);
        System.out.println(germancars);

        System.out.println("");

        System.out.println("I Hope you liked it mister Rutschmann!");
    }
}
