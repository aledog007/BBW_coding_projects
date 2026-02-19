package solution;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * TicketMachine models a naive ticket machine that issues flat-fare tickets.
 * The price of a ticket is specified via the constructor. It is a naive machine
 * in the sense that it trusts its users to insert enough money before trying to
 * print a ticket. It also assumes that users enter sensible amounts.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002.02.06
 */
public class TicketMachine {
	// The price of a ticket from this machine.
	private double price;
	// The amount of money entered by a customer so far.
	private double balance;
	// The total amount of money collected by this machine.
	private double total;

	/**
	 * Create a machine that issues tickets of the given price. Note that the price
	 * must be greater than zero, otherwise it is initialized to one cent.
	 */
	public TicketMachine(double ticketCost) {
		if (ticketCost <= 0) {
			System.out.println("Falscher Wert f�r Parameter ticketCost!");
			this.price = 1;

			// price = ticketCost < 0 ? -ticketCost : 1;
		} else {
			this.price = ticketCost;
		}

		balance = 0;
		total = 0;
	}

		
	/**
	 * Create a machine that issues tickets of a price of 1000 cents.
	 */
	public TicketMachine() {
		price = 1000;
		balance = 0;
		total = 0;
	}

	/**
	 * Return the price of a ticket.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Set the price of a ticket. Note that the price must be greater than zero,
	 * otherwise an error message is printed.
	 */
	public void setPrice(double price) {
		if (price <= 0) {
			System.out.println("Falscher Wert f�r Parameter newPrice!");
			return;
		}

		this.price = price;
	}

	/**
	 * Return the amount of money already inserted for the next ticket.
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Return the amount of money totally inserted into the ticket machine.
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Receive an amount of money in cents from a customer.
	 */

	public void empty() {
		balance = 0;
		total = 0;
	}

	/**
	 * Receive an amount of money in cents from a customer.
	 */
	public void insertMoney(double amount) {
		balance += amount;
	}

	/**
	 * 
	 * showPrice zeigt den Price an
	 * 
	 * 
	 */
	public void showPrice() {
		System.out.println("The Price of a ticket is " + price + " cents");
	}

	/**
	 * Print a ticket. Update the total collected and reduce the balance to zero.
	 */
	public boolean printTicket() {
		
		if (balance < price) {
			// Nicht genug Geld eingeworfen
			System.out.println("Nicht genug Geld eingeworfen.");
			return false;
		}

		// Simulate the printing of a ticket.
		System.out.println("##################");
		System.out.println("# The RedLion Line");
		System.out.println("# Ticket");
		System.out.println("# " + price + " swiss francs.");
		System.out.println("##################");
		System.out.println();

		// Update the total collected with the balance.
		total += balance;
		// Clear the balance.
		balance -= price; // balance = balance - price;

		if (balance > 0) {
//			DecimalFormat myFormatter = new DecimalFormat("###.###");
//			String value = myFormatter.format(balance);
//			System.out.println("Entnehmen Sie Ihr Restgeld: CHF " + value);
			System.out.printf("Bitte noch den Rest entnehmen: %4.2f", +(balance));
			
			balance = 0.0;
			// Geld entnehmen
			return true;
		}
		return true;
	}

	public void menu() {
		// zum einlesen
		Scanner scanner = new Scanner(System.in);
		// Menu
		System.out.print("PLZ (8400,8550,8600):  ");

		String plz = scanner.next();
//		System.out.println(plz);

		String allPlz = "8400,8550,8600";
		boolean stadt = false;
		double preis = 0.0;
		String stadName = "";
		if (allPlz.contains(plz)) {
//			System.out.println("yes");

			switch (Integer.parseInt(plz)) {
			case 8400:
//				System.out.println("Winti");
				stadName = "Winti";
				stadt = true;
				preis = 7.80;
				break;
			case 8550:
//				System.out.println("Embri");
				stadName = "Embri";
				stadt = true;
				preis = 8.80;
				break;
			case 8600:
//				System.out.println("Zurich");
				stadName = "Zurich";
				stadt = true;
				preis = 9.90;
				break;

			default:
				System.out.println("no valid city choosen");
				break;
			}

		} else {
			stadName = "SwissAllTours";
			stadt = true;
			preis = 2999.90;
			System.out.println("no city choosen");
		}

		System.out.println("Stadt: " + stadName);

		System.out.println("1. Klasse (1) 2. Klasse (2)");
		int klasse = scanner.nextInt();

		System.out.println("klasse : " + klasse + ". Klasse ");

		System.out.println("Halbtax (1,0) : ");
		int halbTax = scanner.nextInt();

		System.out.println("halbtax: " + halbTax);

		if (halbTax == 1) {

			preis = preis / 2;
		}

		if (klasse == 1) {
			preis = preis * 2;
		}

		System.out.println("Stad: " + stadName);
		System.out.println("Preis: " + preis);

		// aktueller Preis in der TicketMachine setzen
		this.setPrice(preis);
		System.out.println("Eingabe von Cash!");
		double eingabe = scanner.nextDouble();
		// eingabe
		this.insertMoney(eingabe);

		// Solange kein Ticket printed
		while (!printTicket()) {
			// Solange nicht genug Geld eingeworfen
//			System.out.println("Bitte noch den Rest eingeben "+(preis-balance));
			System.out.printf("Bitte noch den Rest eingeben: %4.1f", +(preis - balance));
			eingabe = scanner.nextDouble();
			// von TicketMachine
			this.insertMoney(eingabe);
		}

		scanner.close();

	}
}
