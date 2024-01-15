package io.wowcollector.entityview.repository.achievement;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.entityview.repository.AbstractDocument;
import org.bson.types.ObjectId;

import java.util.Objects;

public class AchievementCategoryDocument extends AbstractDocument {
    @SerializedName("id")
    private final long myId;
    @SerializedName("name")
    private final String myName;
    @SerializedName("isRootCategory")
    private final boolean myIsRootCategory;
    @SerializedName("rootCategoryId")
    private final long myRootCategoryId;
    @SerializedName("displayOrder")
    private final long myDisplayOrder;

    private AchievementCategoryDocument(Builder builder) {
        super(builder);
        myId = builder.myId;
        myName = builder.myName;
        myIsRootCategory = builder.myIsRootCategory;
        myRootCategoryId = builder.myRootCategoryId;
        myDisplayOrder = builder.myDisplayOrder;
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

    public boolean isRootCategory() {
        return myIsRootCategory;
    }

    public long getRootCategoryId() {
        return myRootCategoryId;
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
        AchievementCategoryDocument that = (AchievementCategoryDocument) o;
        return super.equals(that) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(myId, that.myId) &&
                Objects.equals(myIsRootCategory, that.myIsRootCategory) &&
                Objects.equals(myRootCategoryId, that.myRootCategoryId) &&
                Objects.equals(myDisplayOrder, that.myDisplayOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myName, myId, myIsRootCategory, myRootCategoryId, myDisplayOrder);
    }

    public static class Builder extends AbstractDocumentBuilder {
        private long myId;
        private String myName;
        private boolean myIsRootCategory;
        private long myRootCategoryId;
        private long myDisplayOrder;

        private Builder() {

        }

        private Builder(AchievementCategoryDocument original) {
            myObjectId = original.getObjectId();
            myId = original.myId;
            myName = original.myName;
            myIsRootCategory = original.myIsRootCategory;
            myRootCategoryId = original.myRootCategoryId;
            myDisplayOrder = original.myDisplayOrder;
        }

        @Override
        public Builder withObjectId(ObjectId objectId) {
            myObjectId = objectId;
            return this;
        }

        public Builder withId(long id) {
            myId = id;
            return this;
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withIsRootCategory(boolean isRootCategory) {
            myIsRootCategory = isRootCategory;
            return this;
        }

        public Builder withRootCategoryId(long rootCategoryId) {
            myRootCategoryId = rootCategoryId;
            return this;
        }

        public Builder withDisplayOrder(long displayOrder) {
            myDisplayOrder = displayOrder;
            return this;
        }

        public AchievementCategoryDocument build() {
            return new AchievementCategoryDocument(this);
        }
    }
}
