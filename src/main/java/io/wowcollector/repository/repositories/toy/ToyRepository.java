package io.wowcollector.repository.repositories.toy;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.wowcollector.entityview.repository.ToyDocument;
import io.wowcollector.repository.repositories.AbstractRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ToyRepository extends AbstractRepository<ToyDocument> {
    private static final String INDEX_ID = "id";
    private static final String COLLECTION = "toys";
    private static final Logger LOGGER = Logger.getLogger(ToyRepository.class.getName());

    private final MongoCollection<Document> myCollection;

    public ToyRepository(MongoDatabase database) {
        myCollection = database.getCollection(COLLECTION);
    }

    public static String getCollection() {
        return COLLECTION;
    }

    public void create(ToyDocument document) {
        try {
            myCollection.insertOne(getDocument(document));
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to insert toy, id: \"%d\"",
                                        document.getId()));
            LOGGER.fine(exception.getMessage());
        }
    }

    public void update(int id, ToyDocument document) {
        Bson query = Filters.and(Filters.exists(INDEX_ID), Filters.eq(INDEX_ID, id));
        try {
            myCollection.updateOne(query, new Document("$set", getDocument(document)));
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to update toy, id: \"%d\"",
                                        document.getId()));
            LOGGER.fine(exception.getMessage());
        }
    }

    public void createMany(List<ToyDocument> documents) {
        try {
            myCollection.insertMany(documents.stream()
                                            .map(this::getDocument)
                                            .collect(Collectors.toList()));
        } catch (MongoWriteException exception) {
            LOGGER.severe("Failed to insert toys");
            LOGGER.fine(exception.getMessage());
        }
    }

    public void deleteAll() {
        try {
            myCollection.deleteMany(new Document());
        } catch (MongoWriteException exception) {
            LOGGER.severe("Failed to clear the toys collection");
            LOGGER.fine(exception.getMessage());
        }
    }

    public List<ToyDocument> getAll() {
        Iterator<Document> documents = myCollection.find()
                .iterator();
        List<ToyDocument> result = new ArrayList<>();

        while (documents.hasNext()) {
            result.add(getToyDocument(documents.next()));
        }
        return result;
    }

    private ToyDocument getToyDocument(Document document) {
        return GSON.fromJson(GSON.toJson(document), ToyDocument.class);
    }
}
