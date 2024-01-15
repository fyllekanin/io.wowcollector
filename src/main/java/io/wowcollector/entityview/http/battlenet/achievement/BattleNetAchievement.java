package io.wowcollector.entityview.http.battlenet.achievement;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.entityview.http.battlenet.BattleNetMedia;

import java.util.Objects;

public class BattleNetAchievement {
    @SerializedName("id")
    private final long myId;
    @SerializedName("name")
    private final String myName;
    @SerializedName("description")
    private final String myDescription;
    @SerializedName("points")
    private final long myPoints;
    @SerializedName("is_account_wide")
    private final boolean myIsAccountWide;
    @SerializedName("media")
    private final BattleNetMedia myMedia;
    @SerializedName("display_order")
    private final long myDisplayOrder;

    private BattleNetAchievement(Builder builder) {
        myId = builder.myId;
        myName = builder.myName;
        myDescription = builder.myDescription;
        myPoints = builder.myPoints;
        myIsAccountWide = builder.myIsAccountWide;
        myMedia = builder.myMedia;
        myDisplayOrder = builder.myDisplayOrder;
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

    public boolean isAccountWide() {
        return myIsAccountWide;
    }

    public BattleNetMedia getMedia() {
        return myMedia;
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
        BattleNetAchievement that = (BattleNetAchievement) o;
        return Objects.equals(myName, that.myName) &&
                Objects.equals(myId, that.myId) &&
                Objects.equals(myDescription, that.myDescription) &&
                Objects.equals(myPoints, that.myPoints) &&
                Objects.equals(myIsAccountWide, that.myIsAccountWide) &&
                Objects.equals(myMedia, that.myMedia) &&
                Objects.equals(myDisplayOrder, that.myDisplayOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myName, myId, myDescription, myPoints, myIsAccountWide, myMedia, myDisplayOrder);
    }

    public static class Builder {
        private long myId;
        private String myName;
        private String myDescription;
        private long myPoints;
        private boolean myIsAccountWide;
        private BattleNetMedia myMedia;
        private long myDisplayOrder;

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

        public Builder withIsAccountWide(boolean isAccountWide) {
            myIsAccountWide = isAccountWide;
            return this;
        }

        public Builder withMedia(BattleNetMedia media) {
            myMedia = media;
            return this;
        }

        public Builder withDisplayOrder(long displayOrder) {
            myDisplayOrder = displayOrder;
            return this;
        }

        public BattleNetAchievement build() {
            return new BattleNetAchievement(this);
        }
    }
}
