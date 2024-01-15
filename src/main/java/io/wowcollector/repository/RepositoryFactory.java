package io.wowcollector.repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.wowcollector.repository.repositories.AbstractRepository;
import io.wowcollector.repository.repositories.Repository;
import io.wowcollector.repository.repositories.achievement.AchievementCategoryRepository;
import io.wowcollector.repository.repositories.achievement.AchievementRepository;
import io.wowcollector.repository.repositories.migration.MigrationRepository;
import io.wowcollector.repository.repositories.mount.MountRepository;
import io.wowcollector.repository.repositories.mountcollectionview.MountCollectionViewRepository;
import io.wowcollector.repository.repositories.realm.RealmRepository;
import io.wowcollector.repository.repositories.seed.SeedRepository;
import io.wowcollector.repository.repositories.setting.SettingRepository;
import io.wowcollector.repository.repositories.toy.ToyRepository;
import io.wowcollector.repository.repositories.user.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Map;
import java.util.logging.Logger;

@Singleton
public class RepositoryFactory {
    private static final Logger LOGGER = Logger.getLogger(RepositoryFactory.class.getName());
    private Map<Class<?>, Repository> myRepositories;

    @ConfigProperty(name = "WOW_MONGODB_DATABASE")
    private String myDatabaseName;
    @ConfigProperty(name = "RUN_MIGRATION_SEED", defaultValue = "false")
    private boolean myRunMigrationSeed;

    @Inject
    private MongoClient myMongoClient;

    @PostConstruct
    public void init() {
        try {
            MongoDatabase myDatabase = myMongoClient.getDatabase(myDatabaseName);

            CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    CodecRegistries.fromProviders(PojoCodecProvider
                                                          .builder()
                                                          .automatic(true)
                                                          .build()));
            myDatabase.withCodecRegistry(pojoCodecRegistry);

            myRepositories = Map.of(
                    MountRepository.class, new MountRepository(myDatabase),
                    RealmRepository.class, new RealmRepository(myDatabase),
                    MountCollectionViewRepository.class, new MountCollectionViewRepository(myDatabase),
                    UserRepository.class, new UserRepository(myDatabase),
                    AchievementRepository.class, new AchievementRepository(myDatabase),
                    AchievementCategoryRepository.class, new AchievementCategoryRepository(myDatabase),
                    ToyRepository.class, new ToyRepository(myDatabase),
                    SettingRepository.class, new SettingRepository(myDatabase));

            if (myRunMigrationSeed) {
                new MigrationRepository(myDatabase).run(myDatabase);
                new SeedRepository(myDatabase).run(myDatabase);
            }
        } catch (Exception e) {
            LOGGER.warning(String.format("Database connection failed: %s", e.getMessage()));
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractRepository<?>> T of(Class<T> clazz) {
        return (T) myRepositories.getOrDefault(clazz, null);
    }
}
