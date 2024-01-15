package io.wowcollector.repository.repositories.achievement;

import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.wowcollector.entityview.repository.achievement.AchievementDocument;
import io.wowcollector.repository.repositories.AbstractRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AchievementRepository extends AbstractRepository<AchievementDocument> {
    private static final String INDEX_ID = "id";
    private static final String INDEX_CATEGORY_ID = "categoryId";
    private static final String COLLECTION = "achievements";
    private static final Logger LOGGER = Logger.getLogger(AchievementRepository.class.getName());

    private final MongoCollection<Document> myCollection;

    public AchievementRepository(MongoDatabase database) {
        myCollection = database.getCollection(COLLECTION);
    }

    public static String getCollection() {
        return COLLECTION;
    }

    public void createMany(List<AchievementDocument> documents) {
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

    public void create(AchievementDocument document) {
        try {
            myCollection.insertOne(getDocument(document));
            LOGGER.info(String.format("Inserted achievement, id: \"%d\"", document.getId()));
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to insert achievement, id: \"%d\"",
                                        document.getId()));
            LOGGER.severe(exception.getMessage());
        }
    }

    public void update(long id, AchievementDocument document) {
        Bson query = Filters.and(Filters.exists(INDEX_ID), Filters.eq(INDEX_ID, id));
        try {
            myCollection.updateOne(query, new Document("$set", getDocument(document)));
            LOGGER.info(String.format("Updated achievement, id: \"%d\"", document.getId()));
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to update achievement category, id: \"%d\"",
                                        document.getId()));
            LOGGER.severe(exception.getMessage());
        }
    }

    public AchievementDocument getById(long id) {
        Bson query = Filters.and(Filters.exists(INDEX_ID), Filters.eq(INDEX_ID, id));
        try {
            Document document = myCollection.find(query)
                    .first();
            return getAchievementDocument(document);
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to get achievement, id: \"%d\"",
                                        id));
            LOGGER.fine(exception.getMessage());
            return null;
        }
    }

    public List<AchievementDocument> getByCategoryId(long categoryId) {
        Bson query = Filters.and(Filters.exists(INDEX_CATEGORY_ID), Filters.eq(INDEX_CATEGORY_ID, categoryId));
        try {
            Iterator<Document> documents = myCollection.find(query)
                    .iterator();
            List<AchievementDocument> result = new ArrayList<>();

            while (documents.hasNext()) {
                result.add(getAchievementDocument(documents.next()));
            }
            return result;
        } catch (MongoWriteException exception) {
            LOGGER.severe(String.format("Failed to get achievements with categoryId: \"%d\"",
                                        categoryId));
            LOGGER.fine(exception.getMessage());
            return null;
        }
    }

    public List<AchievementDocument> getAll() {
        Iterator<Document> documents = myCollection.find()
                .iterator();
        List<AchievementDocument> result = new ArrayList<>();

        while (documents.hasNext()) {
            result.add(getAchievementDocument(documents.next()));
        }
        return result;
    }

    private AchievementDocument getAchievementDocument(Document document) {
        return GSON.fromJson(GSON.toJson(document), AchievementDocument.class);
    }
}
