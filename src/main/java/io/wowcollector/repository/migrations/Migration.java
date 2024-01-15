package io.wowcollector.repository.migrations;

import com.mongodb.client.MongoDatabase;

public interface Migration {

    String getName();

    void run(MongoDatabase database);
}
