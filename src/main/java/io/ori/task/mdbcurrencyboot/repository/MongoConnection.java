package io.ori.task.mdbcurrencyboot.repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MongoConnection {
    private final ConnectionString URI;
    private static final Logger logger = LoggerFactory.getLogger(MongoConnection.class);
    public MongoConnection() {
        String userName = System.getenv("MONGO_CLUSTER_USERNAME");
        String password = System.getenv("MONGO_CLUSTER_PASSWORD");
        this.URI = new ConnectionString("mongodb+srv://" + userName + ":" + password + "@cluster07228.lxtqr9w.mongodb.net/?retryWrites=true&w=majority");
        logger.info("Connection URI: " + URI);
    }
    private MongoClientSettings setMongoSettings() {
        logger.info("Setting Mongo Settings...");
        return MongoClientSettings.builder()
                .applyConnectionString(this.URI)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
    }
    private MongoClient getMongoClient(MongoClientSettings mongoClient) {
        logger.info("Start getMongoClient...");
        return MongoClients.create(mongoClient);
    }
    private MongoDatabase getDatabase(MongoClient client, String databaseName) {
        logger.info("Start getDatabase...");
        return client.getDatabase(databaseName);
    }

    public MongoCollection<Document> getCollection(String dataBaseName, String collectionName) {
        logger.info("Start getConnection...");
        MongoClientSettings settings = this.setMongoSettings();
        MongoClient client = this.getMongoClient(settings);
        MongoDatabase database = this.getDatabase(client, dataBaseName);
        return database.getCollection(collectionName);
    }
}
