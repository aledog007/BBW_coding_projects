package ch.bbw.pr.ueberflieger.model;
/**
 * Vogel
 * @author Peter Rutschmann
 * @version 20.04.2018
 */
///NICHT verändern!!!!
public class Vogel {
	protected String name;
	protected String farbe;
	
	public Vogel() {
		name = "unbekannt";
		farbe = "unbekannt";
	}
	
	public Vogel(String name, String farbe) {
		super();
		this.name = name;
		this.farbe = farbe;
	}

	public void fliegen()
	{
		System.out.println("Vogel " + name + " fliegt.");
	}
	
	public void flattern()
	{
		System.out.println("Vogel " + name + " flattert.");
	}
}
