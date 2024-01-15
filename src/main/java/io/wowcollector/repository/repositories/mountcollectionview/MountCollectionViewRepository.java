package io.wowcollector.repository.repositories.mountcollectionview;

import com.google.gson.Gson;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.wowcollector.entityview.repository.collectionview.MountCollectionViewDocument;
import io.wowcollector.repository.repositories.AbstractRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MountCollectionViewRepository extends AbstractRepository<MountCollectionViewDocument> {
    private static final String INDEX_NAME = "name";
    private static final String INDEX_DEFAULT = "isDefault";
    private static final String INDEX_AUTHOR = "author";
    private static final String COLLECTION = "mount-collection-views";

    private static final Logger LOGGER = Logger.getLogger(MountCollectionViewRepository.class.getName());
    private static final Gson GSON = new Gson();

    private final MongoCollection<Document> myCollection;
    private MountCollectionViewDocument myDefault;

    public MountCollectionViewRepository(MongoDatabase database) {
        myCollection = database.getCollection(COLLECTION);
    }

    public static String getCollection() {
        return COLLECTION;
    }

    public void createMany(List<MountCollectionViewDocument> documents) {
        try {
            myCollection.insertMany(documents.stream()
                                            .map(this::getDocument)
                                            .collect(Collectors.toList()));
            LOGGER.info("Inserted achievements");
        } catch (MongoWriteException exception) {
            LOGGER.severe("Failed to insert achievements");
            LOGGER.severe(exception.getMessage());
        }
    }

    public MountCollectionViewDocument getDefault() {
        if (myDefault != null) {
            return myDefault;
        }
        Bson query = Filters.and(Filters.exists(INDEX_DEFAULT), Filters.eq(INDEX_DEFAULT, true));
        try {
            Document document = myCollection.find(query)
                    .first();
            myDefault = getMountCollectionViewDocument(document);
            return myDefault;
        } catch (MongoWriteException exception) {
            LOGGER.severe("Failed to get a default mount collection view");
            LOGGER.fine(exception.getMessage());
            return null;
        }
    }

    private MountCollectionViewDocument getMountCollectionViewDocument(Document document) {
        return GSON.fromJson(GSON.toJson(document), MountCollectionViewDocument.class);
    }
}
