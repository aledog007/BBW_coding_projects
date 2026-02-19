package ch.bbw.af;

import java.awt.*;

// Aufagabe von 13.5.0

// Problem: System.out.println(MyfirstCar.model);

// Er hat  keine Variable korrekt rein geschrieben und dazu den String Model quasi Hinzu fügen wollen

// Korrektur laut zeile mit Variable richtig schreiben: System.out.println(MyfirstCar);

// Aufgabe 13.5.1
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello People my Name is Alessio Fano and today I instruct you in cars");

        System.out.println("");

        Cars Porsche = new Cars( "Porsche", 385 ,340, Color.black);
        System.out.println(Porsche);

        System.out.println("");

        Cars Audi = new Cars( "Audi", 231, 250, Color.white);
        System.out.println(Audi);

        System.out.println("");

        Cars Mercedes = new Cars( "Mercedes", 635 ,350, Color.gray);
        System.out.println(Mercedes);

        System.out.println("");

        Cars BMW = new Cars( "BMW", 385 ,314, Color.red);
        System.out.println(BMW);

        System.out.println("");

        System.out.println("I Hope you liked it mister Rutschmann!");
    }
}
