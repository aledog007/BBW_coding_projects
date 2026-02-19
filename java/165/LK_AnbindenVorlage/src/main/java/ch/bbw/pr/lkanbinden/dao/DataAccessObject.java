package ch.bbw.pr.lkanbinden.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * DataAccessObject
 * Encapsulate data access to database
 * @author Alessio Fano
 * @version 20.02.2025
 */
public class DataAccessObject {
   private final String connectionString = "mongodb://root:1234@localhost:27017";
   private MongoClient mongoClient;
   private MongoCollection<Document> collection;

   public void startup() {
      mongoClient = MongoClients.create(connectionString);
      collection = mongoClient.getDatabase("yourDatabaseName").getCollection("yourCollectionName");
   }

   public void listAllDatabases() {
      System.out.println("List all databases:");
      mongoClient.listDatabases().forEach((Consumer<? super Document>) result -> System.out.println(result.toJson()));
   }

   public void shutdown() {
      mongoClient.close();
   }

   public void aufgabeEins() {
      //Todo Hier Aufgabe 1 implementieren
      Document document = new Document()
              .append("Anlass", "Jahresrückblick")
              .append("Durchführung", new Date());
      collection.insertOne(document);
   }

   public void aufgabeZwei() {
      //Todo Hier Aufgabe 2 implementieren
      Document weihnachten = new Document()
              .append("Datum", new Date(2024, 12, 24));

      Document stephanstag = new Document()
              .append("Datum", new Date(2024, 12, 26));

      Document document = new Document()
              .append("Weihnachten", weihnachten)
              .append("Stephanstag", stephanstag);
      collection.insertOne(document);
   }

   public void aufgabeDrei() {
      //Todo Hier Aufgabe 3 implementieren
      Document menu1 = new Document()
              .append("Art", "Salat")
              .append("Preis", 8.50);

      Document menu2 = new Document()
              .append("Art", "Pasta")
              .append("Preis", 15.50);

      Document menu3 = new Document()
              .append("Art", "Tiramisu")
              .append("Preis", 12.50);

      List<Document> menu = new ArrayList<>();
      menu.add(menu1);
      menu.add(menu2);
      menu.add(menu3);

      Document document = new Document()
              .append("Anlass", "Geburtstag")
              .append("Menü", menu);

      collection.insertOne(document);
   }
}