package ch.bbw.pr.lkanbinden;

import ch.bbw.pr.lkanbinden.dao.DataAccessObject;

/**
 * Lernkontrolle lk anbinden
 *
 * @author Peter Rutschmann
 * @version 4.06.2024
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello Lernkontrolle");
        DataAccessObject dao = new DataAccessObject();
        dao.startup();
        dao.listAllDatabases();
        dao.aufgabeEins();
        dao.aufgabeZwei();
        dao.aufgabeDrei();
        dao.shutdown();
    }
}
