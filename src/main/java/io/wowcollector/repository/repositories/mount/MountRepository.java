package io.wowcollector.repository.repositories.mount;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.wowcollector.entityview.repository.MountDocument;
import io.wowcollector.repository.repositories.AbstractRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class MountRepository extends AbstractRepository<MountDocument> {
    private static final String INDEX_ID = "id";
    private static final String COLLECTION = "mounts";
    private static final Logger LOGGER = Logger.getLogger(MountRepository.class.getName());

    private final MongoCollection<Document> myCollection;

    public MountRepository(MongoDatabase database) {
        myCollection = database.getCollection(COLLECTION);
    }

    public static String getCollection() {
        return COLLECTION;
    }

    public void create(MountDocument document) {
        try {
            myCollection.insertOne(getDocument(document));
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to insert mount, id: \"%d\"",
                                        document.getId()));
            LOGGER.fine(exception.getMessage());
        }
    }

    public void update(int id, MountDocument document) {
        Bson query = Filters.and(Filters.exists(INDEX_ID), Filters.eq(INDEX_ID, id));
        try {
            myCollection.updateOne(query, new Document("$set", getDocument(document)));
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to update mount, id: \"%d\"",
                                        document.getId()));
            LOGGER.fine(exception.getMessage());
        }
    }

    public void deleteAll() {
        try {
            myCollection.deleteMany(new Document());
        } catch (MongoWriteException exception) {
            LOGGER.severe("Failed to clear the mounts collection");
            LOGGER.fine(exception.getMessage());
        }
    }

    public List<MountDocument> getAll() {
        Iterator<Document> documents = myCollection.find()
                .iterator();
        List<MountDocument> result = new ArrayList<>();

        while (documents.hasNext()) {
            result.add(getMountDocument(documents.next()));
        }
        return result;
    }

    private MountDocument getMountDocument(Document document) {
        return GSON.fromJson(GSON.toJson(document), MountDocument.class);
    }
}
