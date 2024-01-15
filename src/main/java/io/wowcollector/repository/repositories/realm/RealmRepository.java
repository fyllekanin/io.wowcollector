package io.wowcollector.repository.repositories.realm;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.wowcollector.entityview.repository.RealmDocument;
import io.wowcollector.repository.repositories.AbstractRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class RealmRepository extends AbstractRepository<RealmDocument> {
    private static final String INDEX_ID = "id";
    private static final String INDEX_REGION = "region";
    private static final String COLLECTION = "realms";
    private static final Logger LOGGER = Logger.getLogger(RealmRepository.class.getName());

    private final MongoCollection<Document> myCollection;

    public RealmRepository(MongoDatabase database) {
        myCollection = database.getCollection(COLLECTION);
    }

    public static String getCollection() {
        return COLLECTION;
    }

    public void create(RealmDocument document) {
        try {
            myCollection.insertOne(getDocument(document));
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to insert realm, id: \"%d\"",
                                        document.getId()));
            LOGGER.fine(exception.getMessage());
        }
    }

    public void update(int id, RealmDocument document) {
        Bson query = Filters.and(Filters.exists(INDEX_ID), Filters.exists(INDEX_REGION), Filters.eq(INDEX_ID, id),
                                 Filters.eq(INDEX_REGION, document.getRegion()
                                         .getRegion()));
        try {
            myCollection.updateOne(query, new Document("$set", getDocument(document)));
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to update realm, id: \"%d\"",
                                        document.getId()));
            LOGGER.fine(exception.getMessage());
        }
    }

    public void deleteAll() {
        try {
            myCollection.deleteMany(new Document());
        } catch (MongoWriteException exception) {
            LOGGER.severe("Failed to clear the realms collection");
            LOGGER.fine(exception.getMessage());
        }
    }

    public List<RealmDocument> getAll() {
        Iterator<Document> documents = myCollection.find()
                .iterator();
        List<RealmDocument> result = new ArrayList<>();

        while (documents.hasNext()) {
            result.add(getRealmDocument(documents.next()));
        }
        return result;
    }

    private RealmDocument getRealmDocument(Document document) {
        return GSON.fromJson(GSON.toJson(document), RealmDocument.class);
    }
}
