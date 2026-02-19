package ch.bbw.pr.ueberflieger.model;

/**
 * Huhn
 * 
 * @author Peter Rutschmann
 * @version 28.03.2019
 */
///NICHT verändern!!!!
public class Huhn extends Vogel {

	public Huhn() {
		// super() ruft den Konstruktor der Basisklasse auf.
		super();
	}

	public Huhn(String name, String farbe) {
		/*
		 * super(name, vorname) ruft den Konstruktor der Basisklasse auf und uebergibt
		 * dabei den Namen und den Vornamen. Der Konstruktor hat also zwei Parameter.
		 */
		super(name, farbe);
	}

	@Override
	public void fliegen() {
		System.out.println("Huhn " + name + " fliegt.");
	}
	
	public void gehtAuchFlattern() {
		/* Aufruf der Methode der Baisklasse geht mit flattern
		 * oder mit super.flattern.
		 */
		flattern();
		super.flattern();
		
		// Hier wird explizit das fliegen der Basisklasse Vogel aufgerufen
		super.fliegen();
	}
}
