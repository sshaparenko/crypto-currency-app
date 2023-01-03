package io.ori.task.mdbcurrencyboot.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import io.ori.task.mdbcurrencyboot.repository.MongoConnection;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.Arrays;

@Service
public class MongoService {
    @Autowired
    private MongoConnection mongo;
    @Autowired
    private PairsService pairsService;

    private MongoCollection<Document> collection;
    private static final Logger logger = LoggerFactory.getLogger(MongoService.class);

    public MongoService(){
    }

    public void addPairs(String pair1, String pair2) {
        collection = mongo.getCollection("currency", "pairs");
        collection.insertOne(Document.parse(pairsService.getStringPairsHttpClientSync(pair1, pair2)));
    }

    public String getPairs(String currency) {
        collection = mongo.getCollection("currency", "pairs");
        Document doc = Document.parse("{pair: \"" + currency + ":USDT\"}");
        StringBuilder cursorString = new StringBuilder();
        MongoCursor<Document> cursor = collection.find(doc).iterator();
        try {
            while(cursor.hasNext()){
                cursorString.append(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
        return cursorString.toString();
    }

    public String getMinPrice(String name) {
        //this.addPairs(name, "USDT");
        collection = mongo.getCollection("currency", "pairs");
        Document doc = Document.parse("{pair: \"" + name + ":USDT\"}");
        Document docResult = collection.find(doc).first();
        return docResult.getString("low");
    }
}
