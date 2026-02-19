package ch.schule;

import java.util.Scanner;

public class TestTicketMachine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test der TicketMachine
		// Objekt der TicketMachine
//		TicketMachine ticketMachine = new TicketMachine(200);
//		// Balance �berpr�fen
//		System.out.println("Balance ist: " + ticketMachine.getBalance());
//		// Geld einwerfen
//		ticketMachine.insertMoney(100);
//		// chsck Balance 
//		System.out.println("Balance ist: " + ticketMachine.getBalance());
//
//		// neues Objekt erzeugen
//		TicketMachine ticket2 = new TicketMachine(250);
//		// check Balance 
//		System.out.println("Balance (ticket 2) ist: " + ticket2.getBalance());
//		
		
		// scanner to read from user
		Scanner scanner = new Scanner(System.in);
		// Menu
		System.out.println("PLZ (8400,8550,8600):  ");
		// read plz from user
		String plz = scanner.next();
		System.out.println(plz);
		// possible destinations
		String allPlz = "8400,8550,8600";
		// city is a possible destination
		boolean stadt = false;
		// calculate the price
		double preis = 0.0;
		// city name
		String stadName = "";
		if (allPlz.contains(plz)) {     // check if plz is a true one 
			System.out.println("yes!");

			switch (Integer.parseInt(plz)) {  // switch to check the city 
			case 8400:
				System.out.println("Winti");  // getPlz// getCity
				stadName = "Winti";
				stadt = true;
				preis = 17.80;
				break;
			case 8550:
				System.out.println("Embri");
				stadName = "Embri";
				stadt = true;
				preis = 18.80;
				break;
			case 8600:
				System.out.println("Zurich");
				stadName = "Zurich";
				stadt = true;
				preis = 19.90;
				break;

			default:
				System.out.println("no valid city choosen");
				break;
			}

		} else {
			// keine stadt gew�hlt
			stadName = "Swiss All tour";
			stadt = true;
			preis = 1999.90;
			System.out.println("no city choosen");
		}
		// show the city name
		System.out.println("Stadt: " + stadName);
		// check 1. class 
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
			preis = preis * 2.5;
		}

		System.out.println("Stad: " + stadName);
		System.out.println("Preis: " + preis);

		System.out.println("Eingabe von Cash!");
		double eingabe = scanner.nextDouble();

		// Machine erstellen aktueller Preis setzen !!!!
		TicketMachine ticket = new TicketMachine(preis);

		// eingabe in die Ticketmachine!!!!!
		ticket.insertMoney(eingabe);
		// Methoden der TicketMachine
		ticket.printTicket();

		// einwerfen
//		ticket.insertMoney(3.8);
//		
//		ticket.printTicket();
//		
//		ticket.insertMoney(5.0);
//		ticket.printTicket();
//		
//		

	}

}
