package io.wowcollector.entityview.response.user;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.entityview.response.achievement.AchievementResponse;
import io.wowcollector.entityview.response.character.CharacterResponse;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

public class UserHomePageResponse {
    @SerializedName("latestAchievements")
    @Schema(name = "latestAchievements")
    private final List<AchievementResponse> myLatestAchievements;
    @SerializedName("characters")
    @Schema(name = "characters")
    private final List<CharacterResponse> myCharacters;

    private UserHomePageResponse(Builder builder) {
        myLatestAchievements = builder.myLatestAchievements;
        myCharacters = builder.myCharacters;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<AchievementResponse> getLatestAchievements() {
        return myLatestAchievements;
    }

    public List<CharacterResponse> getCharacters() {
        return myCharacters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserHomePageResponse that = (UserHomePageResponse) o;
        return Objects.equals(myLatestAchievements, that.myLatestAchievements) &&
                Objects.equals(myCharacters, that.myCharacters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myLatestAchievements, myCharacters);
    }

    public static class Builder {
        private List<AchievementResponse> myLatestAchievements;
        private List<CharacterResponse> myCharacters;

        private Builder() {

        }

        public Builder withLatestAchievements(List<AchievementResponse> achievements) {
            myLatestAchievements = achievements;
            return this;
        }

        public Builder withCharacters(List<CharacterResponse> characters) {
            myCharacters = characters;
            return this;
        }

        public UserHomePageResponse build() {
            return new UserHomePageResponse(this);
        }
    }
}
