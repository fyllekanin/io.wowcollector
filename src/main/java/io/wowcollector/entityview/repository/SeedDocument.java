package io.wowcollector.entityview.repository;

import java.util.Objects;

import org.bson.types.ObjectId;

import com.google.gson.annotations.SerializedName;

public class SeedDocument extends AbstractDocument {
    @SerializedName("name")
    private final String myName;

    private SeedDocument(Builder builder) {
        super(builder);
        myName = builder.myName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return myName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SeedDocument that = (SeedDocument) o;
        return super.equals(that) &&
                Objects.equals(myName, that.myName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myName);
    }

    public static class Builder extends AbstractDocumentBuilder {
        private String myName;

        private Builder() {
        }

        public Builder withObjectId(ObjectId objectId) {
            myObjectId = objectId;
            return this;
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public SeedDocument build() {
            return new SeedDocument(this);
        }
    }
}
