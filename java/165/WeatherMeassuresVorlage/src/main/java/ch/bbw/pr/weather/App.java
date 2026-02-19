package ch.bbw.pr.weather;

import com.mongodb.client.*;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonDateTime;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.security.cert.CollectionCertStoreParameters;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

/**
 * Try out mongoDB
 *
 * @author Peter Rutschmann
 * @version 18.11.2022
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello Weather");

        String connectionString = "mongodb://root:1234@localhost:27017";
        MongoClient mongoClient = MongoClients.create(connectionString);

        //list all databases
        System.out.println("List all databases:");
        mongoClient.listDatabases().forEach((Consumer<? super Document>) result -> System.out.println(result.toJson()));

        // hier wird ein neus Document erstellt
        MongoDatabase statisticDB = mongoClient.getDatabase("weathermeasuredb");
        MongoCollection<Document> statisticCollection = statisticDB.getCollection("measures");

        // hier wird ein neus Document erstellt

        // First document (Winterthur)
        Document doc1 = new Document();
        Document station1 = new Document();
        Document measure1 = new Document();
        Document measure2 = new Document();

        doc1.append("type", "Wettermessung");
        doc1.append("date", new BsonDateTime(new Date().getTime()));
        station1.append("city", "Winterthur");
        station1.append("PLZ", "8400");
        doc1.append("station", station1);

        measure1.append("kind", "temperature");
        measure1.append("value", 20.1);
        measure2.append("kind", "windspeed");
        measure2.append("value", 2.3);
        List<Document> measures1 = List.of(measure1, measure2);
        doc1.append("measures", measures1);

        statisticCollection.insertOne(doc1);

        // Second document (Bern)
        Document doc2 = new Document();
        Document station2 = new Document();
        Document measure3 = new Document();
        Document measure4 = new Document();

        doc2.append("type", "Wettermessung");
        doc2.append("date", new BsonDateTime(new Date().getTime()));
        station2.append("city", "Bern");
        station2.append("PLZ", "3000");
        doc2.append("station", station2);

        measure3.append("kind", "humidity");
        measure3.append("value", 45.7);
        measure4.append("kind", "rainfall");
        measure4.append("value", 12.5);
        List<Document> measures2 = List.of(measure3, measure4);
        doc2.append("measures", measures2);

        statisticCollection.insertOne(doc2);

        mongoClient.close();
    }
}
