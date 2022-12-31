package io.ori.task.mdbcurrencyboot.service;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import io.ori.task.mdbcurrencyboot.repository.MongoStart;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoService {
    @Autowired
    private MongoStart mongo;
    @Autowired
    private PairsService pairsService;

    private MongoCollection<Document> collection;

    public MongoService(){
    }

    public void addPairs(String pair1, String pair2) {
        collection = mongo.getCollection("currency", "pairs");
        collection.insertOne(Document.parse(pairsService.getStringPairsHttpClientSync(pair1, pair2)));
    }

    public String getPairs(String currency) {
        collection = mongo.getCollection("currency", "pairs");
        Document doc = Document.parse("{pair: \"ADA:USDT\"}");

        StringBuilder cursorString = new StringBuilder();
        MongoCursor<Document> cursor = collection.find(doc).iterator();

        try {
            while(cursor.hasNext()){
                cursorString.append(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

//        Document res = collection.find(doc).first();
//        String result = res.toJson();
//        return result;
//        return findIterable.toString();
        return cursorString.toString();
    }
}
