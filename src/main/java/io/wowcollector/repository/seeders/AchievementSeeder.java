package io.wowcollector.repository.seeders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoDatabase;
import io.wowcollector.entityview.repository.achievement.AchievementDocument;
import io.wowcollector.repository.repositories.achievement.AchievementRepository;
import org.bson.Document;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AchievementSeeder implements Seeder {
    @Override
    public String getName() {
        return "achievements-seed";
    }

    @Override
    public void run(MongoDatabase database) {
        Gson gson = new Gson();

        database.getCollection(AchievementRepository.getCollection())
                .insertMany(getData().stream()
                                    .map(item -> Document.parse(gson.toJson(item)))
                                    .collect(Collectors.toList()));
    }

    private List<AchievementDocument> getData() {
        try {
            Type listType = new TypeToken<ArrayList<AchievementDocument>>() {
            }.getType();
            InputStream inputStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("seeds/achievements.json");
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            return new Gson().fromJson(reader, listType);
        } catch (Exception e) {
            return null;
        }
    }
}
