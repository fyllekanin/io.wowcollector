package io.wowcollector.repository.repositories.setting;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.wowcollector.common.data.SettingKey;
import io.wowcollector.entityview.repository.SettingDocument;
import io.wowcollector.repository.repositories.AbstractRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SettingRepository extends AbstractRepository<SettingDocument> {
    private static final String INDEX_KEY = "key";
    private static final String COLLECTION = "settings";
    private static final Logger LOGGER = Logger.getLogger(SettingRepository.class.getName());

    private final MongoCollection<Document> myCollection;

    public SettingRepository(MongoDatabase database) {
        myCollection = database.getCollection(COLLECTION);
    }

    public static String getCollection() {
        return COLLECTION;
    }

    public void create(SettingDocument document) {
        try {
            myCollection.insertOne(getDocument(document));
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to insert setting, key: \"%s\"",
                                        document.getKey()));
            LOGGER.fine(exception.getMessage());
        }
    }

    public void update(SettingKey key, SettingDocument document) {
        Bson query = Filters.and(Filters.exists(INDEX_KEY), Filters.eq(INDEX_KEY, key));
        try {
            myCollection.updateOne(query, new Document("$set", getDocument(document)));
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to update setting, key: \"%s\"",
                                        document.getKey()));
            LOGGER.fine(exception.getMessage());
        }
    }

    public void deleteAll() {
        try {
            myCollection.deleteMany(new Document());
        } catch (MongoWriteException exception) {
            LOGGER.severe("Failed to clear the settings collection");
            LOGGER.fine(exception.getMessage());
        }
    }

    public List<Integer> getUnobtainableMounts() {
        Bson query = Filters.and(Filters.exists(INDEX_KEY), Filters.eq(INDEX_KEY,
                                                                       SettingKey.UNOBTAINABLE_MOUNTS.getValue()));
        try {
            Document document = myCollection.find(query)
                    .first();
            SettingDocument settingsDocument = getSettingDocument(document);
            if (settingsDocument != null && settingsDocument.getValue() != null) {
                return Arrays.stream(settingsDocument.getValue()
                                             .split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to get setting, key: \"%s\"",
                                        SettingKey.UNOBTAINABLE_MOUNTS));
            LOGGER.fine(exception.getMessage());
            return Collections.emptyList();
        }
    }

    public SettingDocument getByKey(SettingKey key) {
        Bson query = Filters.and(Filters.exists(INDEX_KEY), Filters.eq(INDEX_KEY, key));
        try {
            Document document = myCollection.find(query)
                    .first();
            return getSettingDocument(document);
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to get setting, key: \"%s\"",
                                        key));
            LOGGER.fine(exception.getMessage());
            return null;
        }
    }

    private SettingDocument getSettingDocument(Document document) {
        return GSON.fromJson(GSON.toJson(document), SettingDocument.class);
    }
}