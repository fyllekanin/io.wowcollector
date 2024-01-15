package io.wowcollector.repository.repositories.migration;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import io.wowcollector.entityview.repository.MigrationDocument;
import io.wowcollector.repository.migrations.IndexMigration;
import io.wowcollector.repository.migrations.Migration;
import io.wowcollector.repository.repositories.AbstractRepository;

public class MigrationRepository extends AbstractRepository<MigrationDocument> {
    private static final String INDEX_NAME = "name";
    private static final AtomicBoolean myIsDone = new AtomicBoolean(false);
    private static final String COLLECTION = "migrations";
    private static final Logger LOGGER = Logger.getLogger(MigrationRepository.class.getName());

    private final MongoCollection<Document> myCollection;

    public MigrationRepository(MongoDatabase database) {
        myCollection = database.getCollection(COLLECTION);
    }

    private static List<Migration> getMigrations() {
        return List.of(new IndexMigration());
    }

    public synchronized void run(MongoDatabase database) {
        if (myIsDone.get()) {
            throw new RuntimeException("Migration is only allowed to be triggered once");
        }
        LOGGER.info("Starting migration");

        for (Migration migration : getMigrations()) {
            if (isMigrationDone(migration.getName())) {
                continue;
            }

            migration.run(database);
            myCollection.insertOne(Document.parse(GSON.toJson(MigrationDocument.newBuilder()
                    .withName(migration.getName())
                    .build())));
        }

        myIsDone.set(true);
        LOGGER.info("Finished migration");
    }

    private boolean isMigrationDone(String name) {
        Bson query = Filters.and(Filters.exists(INDEX_NAME), Filters.eq(INDEX_NAME, name));
        return myCollection.countDocuments(query) > 0;
    }
}
