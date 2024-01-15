package io.wowcollector.repository.repositories.achievement;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.wowcollector.entityview.repository.achievement.AchievementCategoryDocument;
import io.wowcollector.repository.repositories.AbstractRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class AchievementCategoryRepository extends AbstractRepository<AchievementCategoryDocument> {
    private static final String INDEX_ID = "id";
    private static final String COLLECTION = "achievement-categories";
    private static final Logger LOGGER = Logger.getLogger(AchievementCategoryRepository.class.getName());

    private final MongoCollection<Document> myCollection;

    public AchievementCategoryRepository(MongoDatabase database) {
        myCollection = database.getCollection(COLLECTION);
    }

    public static String getCollection() {
        return COLLECTION;
    }

    public void create(AchievementCategoryDocument document) {
        try {
            myCollection.insertOne(getDocument(document));
            LOGGER.info(String.format("Inserted achievement category, id: \"%d\"", document.getId()));
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to insert achievement, id: \"%d\"",
                                        document.getId()));
            LOGGER.fine(exception.getMessage());
        }
    }

    public AchievementCategoryDocument getById(long id) {
        Bson query = Filters.and(Filters.exists(INDEX_ID), Filters.eq(INDEX_ID, id));
        try {
            Document document = myCollection.find(query)
                    .first();
            return getAchievementCategoryDocument(document);
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to get achievement, id: \"%d\"",
                                        id));
            LOGGER.fine(exception.getMessage());
            return null;
        }
    }

    public void update(long id, AchievementCategoryDocument document) {
        Bson query = Filters.and(Filters.exists(INDEX_ID), Filters.eq(INDEX_ID, id));
        try {
            myCollection.updateOne(query, new Document("$set", getDocument(document)));
            LOGGER.info(String.format("Updated achievement category, id: \"%d\"", document.getId()));
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to update achievement category, id: \"%d\"",
                                        document.getId()));
            LOGGER.fine(exception.getMessage());
        }
    }

    public List<AchievementCategoryDocument> getAll() {
        Iterator<Document> documents = myCollection.find()
                .iterator();
        List<AchievementCategoryDocument> result = new ArrayList<>();

        while (documents.hasNext()) {
            result.add(getAchievementCategoryDocument(documents.next()));
        }
        return result;
    }

    private AchievementCategoryDocument getAchievementCategoryDocument(Document document) {
        return GSON.fromJson(GSON.toJson(document), AchievementCategoryDocument.class);
    }
}
