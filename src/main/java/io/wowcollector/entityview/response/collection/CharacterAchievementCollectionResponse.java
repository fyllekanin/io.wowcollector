package io.wowcollector.entityview.response.collection;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.entityview.response.collection.achievement.AchievementCategoryResponse;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

public class CharacterAchievementCollectionResponse {
    @SerializedName("achievementCategories")
    @Schema(name = "achievementCategories")
    private final List<AchievementCategoryResponse> myAchievementCategories;

    private CharacterAchievementCollectionResponse(Builder builder) {
        myAchievementCategories = builder.myAchievementCategories;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder newBuilderFromCurrent() {
        return new Builder(this);
    }

    public List<AchievementCategoryResponse> getAchievementCategories() {
        return myAchievementCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CharacterAchievementCollectionResponse that = (CharacterAchievementCollectionResponse) o;
        return Objects.equals(myAchievementCategories, that.myAchievementCategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myAchievementCategories);
    }

    public static class Builder {
        private List<AchievementCategoryResponse> myAchievementCategories;

        private Builder() {

        }

        private Builder(CharacterAchievementCollectionResponse original) {
            myAchievementCategories = original.myAchievementCategories;
        }

        public Builder withAchievementCategories(List<AchievementCategoryResponse> achievementCategories) {
            myAchievementCategories = achievementCategories;
            return this;
        }

        public CharacterAchievementCollectionResponse build() {
            return new CharacterAchievementCollectionResponse(this);
        }
    }
}
