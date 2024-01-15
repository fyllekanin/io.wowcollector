package io.wowcollector.entityview.http.battlenet.achievement;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class BattleNetAchievementIndex {
    @SerializedName("achievements")
    private final List<BattleNetAchievementIndexItem> myAchievements;
    @SerializedName("parent_category")
    private final BattleNetAchievementCategory myParentCategory;
    @SerializedName("display_order")
    private final long myDisplayOrder;

    private BattleNetAchievementIndex(Builder builder) {
        myAchievements = builder.myAchievements;
        myParentCategory = builder.myParentCategory;
        myDisplayOrder = builder.myDisplayOrder;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<BattleNetAchievementIndexItem> getAchievements() {
        return myAchievements;
    }

    public BattleNetAchievementCategory getParentCategory() {
        return myParentCategory;
    }

    public long getDisplayOrder() {
        return myDisplayOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetAchievementIndex that = (BattleNetAchievementIndex) o;
        return Objects.equals(myAchievements, that.myAchievements) &&
                Objects.equals(myParentCategory, that.myParentCategory) &&
                Objects.equals(myDisplayOrder, that.myDisplayOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myAchievements, myParentCategory, myDisplayOrder);
    }

    public static class Builder {
        private List<BattleNetAchievementIndexItem> myAchievements;
        private BattleNetAchievementCategory myParentCategory;
        private long myDisplayOrder;

        private Builder() {

        }

        public Builder withAchievements(List<BattleNetAchievementIndexItem> achievements) {
            myAchievements = achievements;
            return this;
        }

        public Builder withParentCategory(BattleNetAchievementCategory parentCategory) {
            myParentCategory = parentCategory;
            return this;
        }

        public Builder withDisplayOrder(long displayOrder) {
            myDisplayOrder = displayOrder;
            return this;
        }

        public BattleNetAchievementIndex build() {
            return new BattleNetAchievementIndex(this);
        }
    }
}
