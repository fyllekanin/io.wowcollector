package io.wowcollector.entityview.repository.achievement;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.entityview.http.battlenet.BattleNetMedia;
import io.wowcollector.entityview.repository.AbstractDocument;
import org.bson.types.ObjectId;

import java.util.Objects;

public class AchievementDocument extends AbstractDocument {
    @SerializedName("id")
    private final long myId;
    @SerializedName("name")
    private final String myName;
    @SerializedName("description")
    private final String myDescription;
    @SerializedName("points")
    private final long myPoints;
    @SerializedName("isAccountWide")
    private final boolean myIsAccountWide;
    @SerializedName("media")
    private final BattleNetMedia myMedia;
    @SerializedName("displayOrder")
    private final long myDisplayOrder;
    @SerializedName("categoryId")
    private final long myCategoryId;

    private AchievementDocument(Builder builder) {
        super(builder);
        myId = builder.myId;
        myName = builder.myName;
        myDescription = builder.myDescription;
        myPoints = builder.myPoints;
        myIsAccountWide = builder.myIsAccountWide;
        myMedia = builder.myMedia;
        myDisplayOrder = builder.myDisplayOrder;
        myCategoryId = builder.myCategoryId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder newBuilderFromCurrent() {
        return new Builder(this);
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

    public long getCategoryId() {
        return myCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AchievementDocument that = (AchievementDocument) o;
        return super.equals(that) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(myId, that.myId) &&
                Objects.equals(myDescription, that.myDescription) &&
                Objects.equals(myPoints, that.myPoints) &&
                Objects.equals(myIsAccountWide, that.myIsAccountWide) &&
                Objects.equals(myMedia, that.myMedia) &&
                Objects.equals(myDisplayOrder, that.myDisplayOrder) &&
                Objects.equals(myCategoryId, that.myCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myName, myId, myDescription, myPoints, myIsAccountWide, myMedia,
                            myDisplayOrder,
                            myCategoryId);
    }

    public static class Builder extends AbstractDocumentBuilder {
        private long myId;
        private String myName;
        private String myDescription;
        private long myPoints;
        private boolean myIsAccountWide;
        private BattleNetMedia myMedia;
        private long myDisplayOrder;
        private long myCategoryId;

        private Builder() {

        }

        private Builder(AchievementDocument original) {
            myObjectId = original.getObjectId();
            myId = original.myId;
            myName = original.myName;
            myDescription = original.myDescription;
            myPoints = original.myPoints;
            myIsAccountWide = original.myIsAccountWide;
            myMedia = original.myMedia;
            myDisplayOrder = original.myDisplayOrder;
            myCategoryId = original.myCategoryId;
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

        public Builder withCategoryId(long categoryId) {
            myCategoryId = categoryId;
            return this;
        }

        @Override
        public Builder withObjectId(ObjectId objectId) {
            myObjectId = objectId;
            return this;
        }

        public AchievementDocument build() {
            return new AchievementDocument(this);
        }
    }
}
