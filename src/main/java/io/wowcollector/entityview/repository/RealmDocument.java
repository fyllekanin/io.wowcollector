package io.wowcollector.entityview.repository;

import java.util.Objects;

import org.bson.types.ObjectId;

import com.google.gson.annotations.SerializedName;

import io.wowcollector.common.data.BlizzardRegion;

public class RealmDocument extends AbstractDocument {
    @SerializedName("id")
    private final int myId;
    @SerializedName("name")
    private final String myName;
    @SerializedName("slug")
    private final String mySlug;
    @SerializedName("region")
    private final BlizzardRegion myRegion;

    private RealmDocument(Builder builder) {
        super(builder);
        myId = builder.myId;
        myName = builder.myName;
        mySlug = builder.mySlug;
        myRegion = builder.myRegion;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder newBuilderFromCurrent() {
        return new Builder(this);
    }

    public int getId() {
        return myId;
    }

    public String getName() {
        return myName;
    }

    public String getSlug() {
        return mySlug;
    }

    public BlizzardRegion getRegion() {
        return myRegion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RealmDocument that = (RealmDocument) o;
        return super.equals(that) &&
                Objects.equals(myId, that.myId) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(mySlug, that.mySlug) &&
                Objects.equals(myRegion, that.myRegion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myId, myName, mySlug, myRegion);
    }

    public static class Builder extends AbstractDocumentBuilder {
        private int myId;
        private String myName;
        private String mySlug;
        private BlizzardRegion myRegion;

        private Builder() {

        }

        private Builder(RealmDocument document) {
            myObjectId = document.getObjectId();
            myId = document.getId();
            myName = document.getName();
            mySlug = document.getSlug();
            myRegion = document.getRegion();
        }

        public Builder withObjectId(ObjectId objectId) {
            myObjectId = objectId;
            return this;
        }

        public Builder withId(int id) {
            myId = id;
            return this;
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withSlug(String slug) {
            mySlug = slug;
            return this;
        }

        public Builder withRegion(BlizzardRegion region) {
            myRegion = region;
            return this;
        }

        public RealmDocument build() {
            return new RealmDocument(this);
        }
    }
}
