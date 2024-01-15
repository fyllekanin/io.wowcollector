package io.wowcollector.repository.migrations;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import io.wowcollector.repository.repositories.achievement.AchievementCategoryRepository;
import io.wowcollector.repository.repositories.achievement.AchievementRepository;
import io.wowcollector.repository.repositories.mount.MountRepository;
import io.wowcollector.repository.repositories.mountcollectionview.MountCollectionViewRepository;
import io.wowcollector.repository.repositories.realm.RealmRepository;
import io.wowcollector.repository.repositories.setting.SettingRepository;
import io.wowcollector.repository.repositories.toy.ToyRepository;
import io.wowcollector.repository.repositories.user.UserRepository;

public class IndexMigration implements Migration {

    public String getName() {
        return "index-migration";
    }

    public void run(MongoDatabase database) {
        database.getCollection("migrations")
                .createIndex(Indexes.ascending("name"), new IndexOptions().unique(true));
        database.getCollection("seeds")
                .createIndex(Indexes.ascending("name"), new IndexOptions().unique(true));

        database.getCollection(MountCollectionViewRepository.getCollection())
                .createIndex(Indexes.ascending("name"));
        database.getCollection(MountCollectionViewRepository.getCollection())
                .createIndex(Indexes.ascending("isDefault"));
        database.getCollection(MountCollectionViewRepository.getCollection())
                .createIndex(Indexes.ascending("author"));

        database.getCollection(MountRepository.getCollection())
                .createIndex(Indexes.ascending("id"), new IndexOptions().unique(true));

        database.getCollection(RealmRepository.getCollection())
                .createIndex(Indexes.ascending("id"));
        database.getCollection(RealmRepository.getCollection())
                .createIndex(Indexes.ascending("region"));
        database.getCollection(RealmRepository.getCollection())
                .createIndex(Indexes.ascending("id", "region"), new IndexOptions().unique(true));

        database.getCollection(UserRepository.getCollection())
                .createIndex(Indexes.ascending("battleTag"), new IndexOptions().unique(true));

        database.getCollection(AchievementCategoryRepository.getCollection())
                .createIndex(Indexes.ascending("id"), new IndexOptions().unique(true));

        database.getCollection(AchievementRepository.getCollection())
                .createIndex(Indexes.ascending("id"), new IndexOptions().unique(true));
        database.getCollection(AchievementRepository.getCollection())
                .createIndex(Indexes.ascending("categoryId"));

        database.getCollection(ToyRepository.getCollection())
                .createIndex(Indexes.ascending("id"), new IndexOptions().unique(true));

        database.getCollection(SettingRepository.getCollection())
                .createIndex(Indexes.ascending("key"), new IndexOptions().unique(true));
    }
}
