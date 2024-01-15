package io.wowcollector.repository.seeders;

import com.mongodb.client.MongoDatabase;

public interface Seeder {
    String getName();

    void run(MongoDatabase database);
}
