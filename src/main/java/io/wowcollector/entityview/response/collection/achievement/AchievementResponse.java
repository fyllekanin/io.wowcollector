package io.wowcollector.entityview.response.collection.achievement;

import com.google.gson.annotations.SerializedName;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

public class AchievementResponse {
    @SerializedName("id")
    @Schema(name = "id")
    private final long myId;
    @SerializedName("name")
    @Schema(name = "name")
    private final String myName;
    @SerializedName("description")
    @Schema(name = "description")
    private final String myDescription;
    @SerializedName("points")
    @Schema(name = "points")
    private final long myPoints;
    @SerializedName("icon")
    @Schema(name = "icon")
    private final String myIcon;
    @SerializedName("displayOrder")
    @Schema(name = "displayOrder")
    private final long myDisplayOrder;
    @SerializedName("isCollected")
    @Schema(name = "isCollected")
    private final boolean myIsCollected;

    private AchievementResponse(Builder builder) {
        myId = builder.myId;
        myName = builder.myName;
        myDescription = builder.myDescription;
        myPoints = builder.myPoints;
        myIcon = builder.myIcon;
        myDisplayOrder = builder.myDisplayOrder;
        myIsCollected = builder.myIsCollected;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return myId;
    }

    public String getName() {
        return myName;
    }

    public String getDescription() {
        return myDescription;
    }

    public long getPoints() {
        return myPoints;
    }

    public String getIcon() {
        return myIcon;
    }

    public long getDisplayOrder() {
        return myDisplayOrder;
    }

    public boolean isCollected() {
        return myIsCollected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AchievementResponse that = (AchievementResponse) o;
        return Objects.equals(myId, that.myId) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(myDescription, that.myDescription) &&
                Objects.equals(myPoints, that.myPoints) &&
                Objects.equals(myIcon, that.myIcon) &&
                Objects.equals(myDisplayOrder, that.myDisplayOrder) &&
                Objects.equals(myIsCollected, that.myIsCollected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myId, myName, myDescription, myPoints, myIcon, myDisplayOrder, myIsCollected);
    }

    public static class Builder {
        private long myId;
        private String myName;
        private String myDescription;
        private long myPoints;
        private String myIcon;
        private long myDisplayOrder;
        private boolean myIsCollected;

        private Builder() {

        }

        public Builder withId(long id) {
            myId = id;
            return this;
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withDescription(String description) {
            myDescription = description;
            return this;
        }

        public Builder withPoints(long points) {
            myPoints = points;
            return this;
        }

        public Builder withIcon(String icon) {
            myIcon = icon;
            return this;
        }

        public Builder withDisplayOrder(long displayOrder) {
            myDisplayOrder = displayOrder;
            return this;
        }

        public Builder withIsCollected(boolean isCollected) {
            myIsCollected = isCollected;
            return this;
        }

        public AchievementResponse build() {
            return new AchievementResponse(this);
        }
    }
}
