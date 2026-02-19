package ch.bbw.pr.ueberflieger;

import ch.bbw.pr.ueberflieger.model.Ente;
import ch.bbw.pr.ueberflieger.model.Huhn;
import ch.bbw.pr.ueberflieger.model.Vogel;

/**
 * Application
 * @author Peter Rutschmann
 * @version 27.11.2024
 */
public class Application {
	public static void main(String[] args) {
		System.out.println("Die Ueberflieger");
		System.out.println();

		// hier die Codezeilen aus der Aufgabe implementieren.
		// wenn etwas nicht geht, notieren Sie als Kommentar, wieso
		// und kommentieren die Codezeile aus.
		Vogel birdy = new Vogel("Birdy", "Schwarz");
		Huhn helena = new Huhn ("Helena", "Weiss");
		Ente quak = new Ente("Quak", "Gelb");
		birdy.fliegen();
		helena.fliegen();
		quak.schwimmen();

		Vogel einVogel = birdy;
		einVogel.fliegen();
		Vogel nochEinVogel = helena;
		nochEinVogel.fliegen();
		/*
		hier ist uch ein fehler weil nochEinVogel/helena ist ein Huhn (in der Klasse Huhn) und nicht eine Ente
		und die die Methode schwimmen() nicht in der Klasse Vogel existiert heisst man
		müsste entweder die Methode schwimmen() in der Klasse Vogel implementieren oder ich kann eine Typumwandlung/casten (er ist keine ente ich sehe ihn nur als Ente an)
		machen wie in der Zeile 58 ( ((Ente)nochEinVogel).schwimmen(); )
		nochEinVogel.schwimmen();
		*/

        Ente ede = new Ente("Ede", "Braun");
		Ente emma = quak;
		emma.schwimmen();
		// Hier crasht er weil er nicht casten kann Cast sind machtbar aber gefährlich zum das beheben kann ich ein try catch machen
		Ente elsa = (Ente) birdy;
		elsa.schwimmen();

		Vogel beate = new Ente("Beate", "Gefleckt");
		beate.fliegen();

		/*
		hier ist uch ein fehler weil beate ist ein Ente (in der Klasse Vogel) und ein Vogel aber das Programm sieht ihn nicht als Ente
		und die die Methode schwimmen() nicht in der Klasse Vogel existiert heisst man
		müsste entweder die Methode schwimmen() in der Klasse Vogel implementieren oder ich kann eine Typumwandlung/casten (er ist keine ente ich sehe ihn nur als Ente an)
		machen wie in der Zeile 58 ( ((Ente)beate).schwimmen(); )

		beate.schwimmen();
		*/

        ((Ente) birdy).schwimmen();
		((Ente) beate).schwimmen();





	}
}





