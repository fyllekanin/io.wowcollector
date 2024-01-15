package io.wowcollector.entityview.http.battlenet.achievement;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BattleNetAchievementCategoryIndex {
    @SerializedName("categories")
    private final List<BattleNetAchievementCategory> myCategories;
    @SerializedName("root_categories")
    private final List<BattleNetAchievementCategory> myRootCategories;

    private BattleNetAchievementCategoryIndex(Builder builder) {
        myCategories = builder.myCategories;
        myRootCategories = builder.myRootCategories;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<BattleNetAchievementCategory> getCategories() {
        return myCategories;
    }

    public List<BattleNetAchievementCategory> getRootCategories() {
        return myRootCategories;
    }

    public List<BattleNetAchievementCategory> getAllCategories() {
        return Stream.concat(myCategories.stream(), myRootCategories.stream())
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetAchievementCategoryIndex that = (BattleNetAchievementCategoryIndex) o;
        return Objects.equals(myCategories, that.myCategories) &&
                Objects.equals(myRootCategories, that.myRootCategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myCategories, myRootCategories);
    }

    public static class Builder {
        private List<BattleNetAchievementCategory> myCategories;
        private List<BattleNetAchievementCategory> myRootCategories;

        private Builder() {

        }

        public Builder withCategories(List<BattleNetAchievementCategory> categories) {
            myCategories = categories;
            return this;
        }

        public Builder withRootCategories(List<BattleNetAchievementCategory> rootCategories) {
            myRootCategories = rootCategories;
            return this;
        }

        public BattleNetAchievementCategoryIndex build() {
            return new BattleNetAchievementCategoryIndex(this);
        }
    }
}
