package io.wowcollector.entityview.repository.collectionview;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.entityview.repository.AbstractDocument;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

public class MountCollectionViewDocument extends AbstractDocument {
    @SerializedName("name")
    private final String myName;
    @SerializedName("isDefault")
    private final boolean myIsDefault;
    @SerializedName("categories")
    private final List<CollectionCategory> myCategories;
    @SerializedName("author")
    private final String myAuthor;
    @SerializedName("isUnknownIncluded")
    private final boolean myIsUnknownIncluded;

    private MountCollectionViewDocument(Builder builder) {
        super(builder);
        myName = builder.myName;
        myIsDefault = builder.myIsDefault;
        myCategories = builder.myCategories;
        myAuthor = builder.myAuthor;
        myIsUnknownIncluded = builder.myIsUnknownIncluded;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return myName;
    }

    public boolean isDefault() {
        return myIsDefault;
    }

    public List<CollectionCategory> getCategories() {
        return myCategories;
    }

    public String getAuthor() {
        return myAuthor;
    }

    public boolean isUnknownIncluded() {
        return myIsUnknownIncluded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MountCollectionViewDocument that = (MountCollectionViewDocument) o;
        return super.equals(that) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(myIsDefault, that.myIsDefault) &&
                Objects.equals(myCategories, that.myCategories) &&
                Objects.equals(myAuthor, that.myAuthor) &&
                Objects.equals(myIsUnknownIncluded, that.myIsUnknownIncluded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myName, myIsDefault, myCategories, myAuthor,
                            myIsUnknownIncluded);
    }

    public Builder newBuilderFromCurrent() {
        return new Builder(this);
    }

    public static class Builder extends AbstractDocumentBuilder {
        private String myName;
        private boolean myIsDefault;
        private List<CollectionCategory> myCategories;
        private String myAuthor;
        private boolean myIsUnknownIncluded;

        private Builder() {

        }

        private Builder(MountCollectionViewDocument original) {
            myName = original.myName;
            myIsDefault = original.myIsDefault;
            myCategories = original.myCategories;
            myAuthor = original.myAuthor;
            myIsUnknownIncluded = original.myIsUnknownIncluded;
        }

        public Builder withObjectId(ObjectId objectId) {
            myObjectId = objectId;
            return this;
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withIsDefault(boolean isDefault) {
            myIsDefault = isDefault;
            return this;
        }

        public Builder withCategories(List<CollectionCategory> categories) {
            myCategories = categories;
            return this;
        }

        public Builder withAuthor(String author) {
            myAuthor = author;
            return this;
        }

        public Builder withIsUnknownIncluded(boolean isUnknownIncluded) {
            myIsUnknownIncluded = isUnknownIncluded;
            return this;
        }

        public MountCollectionViewDocument build() {
            return new MountCollectionViewDocument(this);
        }
    }
}
