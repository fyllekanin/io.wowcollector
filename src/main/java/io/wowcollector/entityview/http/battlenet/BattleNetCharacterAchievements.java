package io.wowcollector.entityview.http.battlenet;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class BattleNetCharacterAchievements {
    @SerializedName("achievements")
    private final List<BattleNetCharacterAchievement> myAchievements;

    private BattleNetCharacterAchievements(Builder builder) {
        myAchievements = builder.myAchievements;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<BattleNetCharacterAchievement> getAchievements() {
        return myAchievements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetCharacterAchievements that = (BattleNetCharacterAchievements) o;
        return Objects.equals(myAchievements, that.myAchievements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myAchievements);
    }

    public static class Builder {
        private List<BattleNetCharacterAchievement> myAchievements;

        private Builder() {

        }

        public Builder withAchievements(List<BattleNetCharacterAchievement> achievements) {
            myAchievements = achievements;
            return this;
        }

        public BattleNetCharacterAchievements build() {
            return new BattleNetCharacterAchievements(this);
        }
    }
}
