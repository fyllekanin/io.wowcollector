package io.wowcollector.entityview.repository;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.common.data.SettingKey;
import org.bson.types.ObjectId;

import java.util.Objects;


public class SettingDocument extends AbstractDocument {
    @SerializedName("key")
    private final SettingKey myKey;
    @SerializedName("value")
    private final String myValue;

    private SettingDocument(Builder builder) {
        super(builder);
        myKey = builder.myKey;
        myValue = builder.myValue;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public SettingKey getKey() {
        return myKey;
    }

    public String getValue() {
        return myValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SettingDocument that = (SettingDocument) o;
        return super.equals(that) &&
                Objects.equals(myKey, that.myKey) &&
                Objects.equals(myValue, that.myValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myKey, myValue);
    }

    public Builder newBuilderFromCurrent() {
        return new Builder(this);
    }

    public static class Builder extends AbstractDocumentBuilder {
        private SettingKey myKey;
        private String myValue;

        private Builder() {
        }

        private Builder(SettingDocument original) {
            myObjectId = original.getObjectId();
            myKey = original.myKey;
            myValue = original.myValue;
        }

        public Builder withObjectId(ObjectId objectId) {
            myObjectId = objectId;
            return this;
        }

        public Builder withKey(SettingKey key) {
            myKey = key;
            return this;
        }

        public Builder withValue(String value) {
            myValue = value;
            return this;
        }

        public SettingDocument build() {
            return new SettingDocument(this);
        }
    }
}
