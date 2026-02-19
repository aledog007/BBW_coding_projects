package ch.schule;
import java.text.DecimalFormat;

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
			System.out.println("Wrong value for Parameter ticketCost!");
			price = 1;

			// price = ticketCost < 0 ? -ticketCost : 1;
		} else {
			price = ticketCost;
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
	public void setPrice(double newPrice) {
		if (newPrice <= 0) {
			System.out.println("Wrong value Parameter newPrice!");
			return;
		}

		price = newPrice;
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
	public void printTicket() {
		if (balance < price) {
			// Nicht genug Geld eingeworfen
			System.out.println("Nicht genug Geld eingeworfen.");
			return;
		}

		// Simulate the printing of a ticket.
		System.out.println("##################");
		System.out.println("# The RedLion Line");
		System.out.println("# Ticket");
		System.out.printf("# Price: %4.1f\n", + price);
		System.out.println("##################");
		System.out.println();

		// Update the total collected with the balance.
		total += balance;
		// Clear the balance.
		balance -= price; // balance = balance - price;

		if (balance > 0) {
			DecimalFormat myFormatter = new DecimalFormat("###.###");
			String value = myFormatter.format(balance);
			System.out.println("get your refound CHF " + value);
			System.out.printf("Bitte noch den Rest entnehmen: %4.1f", +(balance));
			balance = 0.0;
		}
	}
}
