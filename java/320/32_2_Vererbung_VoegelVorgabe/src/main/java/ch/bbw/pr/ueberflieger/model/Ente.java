package ch.bbw.pr.ueberflieger.model;
/**
 * Ente
 * @author Peter Rutschmann
 * @version 20.04.2018
 */

///NICHT verändern!!!!
public class Ente extends Vogel{
	public Ente(String name, String farbe) {
		super(name, farbe);
	}

	public void schwimmen()
	{
		System.out.println("Ente " + name + " schwimmt");
	}
}
