package io.wowcollector.entityview.repository;

import java.util.Objects;

import org.bson.types.ObjectId;

import com.google.gson.annotations.SerializedName;

public abstract class AbstractDocument {
    @SerializedName("_id")
    private final ObjectId myObjectId;

    protected AbstractDocument(AbstractDocumentBuilder builder) {
        myObjectId = builder.myObjectId;
    }

    public ObjectId getObjectId() {
        return myObjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AbstractDocument that = (AbstractDocument) o;
        return Objects.equals(myObjectId, that.myObjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myObjectId);
    }

    public static abstract class AbstractDocumentBuilder {
        protected ObjectId myObjectId;

        public abstract AbstractDocumentBuilder withObjectId(ObjectId objectId);
    }
}
