package io.wowcollector.entityview.response.collection.achievement;

import com.google.gson.annotations.SerializedName;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

public class AchievementCategoryResponse {
    @SerializedName("name")
    @Schema(name = "name")
    private final String myName;
    @SerializedName("achievements")
    @Schema(name = "achievements")
    private final List<AchievementResponse> myAchievements;
    @SerializedName("subCategories")
    @Schema(name = "subCategories")
    private final List<AchievementCategoryResponse> mySubCategories;
    @SerializedName("displayOrder")
    @Schema(name = "displayOrder")
    private final long myDisplayOrder;

    private AchievementCategoryResponse(Builder builder) {
        myName = builder.myName;
        myAchievements = builder.myAchievements;
        mySubCategories = builder.mySubCategories;
        myDisplayOrder = builder.myDisplayOrder;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return myName;
    }

    public List<AchievementResponse> getAchievements() {
        return myAchievements;
    }

    public List<AchievementCategoryResponse> getSubCategories() {
        return mySubCategories;
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
        AchievementCategoryResponse that = (AchievementCategoryResponse) o;
        return Objects.equals(myName, that.myName) &&
                Objects.equals(myAchievements, that.myAchievements) &&
                Objects.equals(mySubCategories, that.mySubCategories) &&
                Objects.equals(myDisplayOrder, that.myDisplayOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myName, myAchievements, mySubCategories, myDisplayOrder);
    }

    public static class Builder {
        private String myName;
        private List<AchievementResponse> myAchievements;
        private List<AchievementCategoryResponse> mySubCategories;
        private long myDisplayOrder;

        private Builder() {

        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withAchievements(List<AchievementResponse> achievements) {
            myAchievements = achievements;
            return this;
        }

        public Builder withSubCategories(List<AchievementCategoryResponse> subCategories) {
            mySubCategories = subCategories;
            return this;
        }

        public Builder withDisplayOrder(long displayOrder) {
            myDisplayOrder = displayOrder;
            return this;
        }

        public AchievementCategoryResponse build() {
            return new AchievementCategoryResponse(this);
        }
    }
}
