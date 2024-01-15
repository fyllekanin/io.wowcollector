package io.wowcollector.entityview.repository;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;

import java.util.Objects;

public class ToyDocument extends AbstractDocument {
    @SerializedName("name")
    private final String myName;
    @SerializedName("id")
    private final int myId;
    @SerializedName("isUnobtainable")
    private final boolean myIsUnobtainable;
    @SerializedName("icon")
    private final String myIcon;

    private ToyDocument(Builder builder) {
        super(builder);
        myName = builder.myName;
        myId = builder.myId;
        myIsUnobtainable = builder.myIsUnobtainable;
        myIcon = builder.myIcon;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder newBuilderFromCurrent() {
        return new Builder(this);
    }

    public String getName() {
        return myName;
    }

    public int getId() {
        return myId;
    }

    public boolean isUnobtainable() {
        return myIsUnobtainable;
    }

    public String getIcon() {
        return myIcon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ToyDocument that = (ToyDocument) o;
        return super.equals(that) &&
                Objects.equals(myId, that.myId) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(myIsUnobtainable, that.myIsUnobtainable) &&
                Objects.equals(myIcon, that.myIcon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myName, myId,
                            myIsUnobtainable, myIcon);
    }

    public static class Builder extends AbstractDocumentBuilder {
        private String myName;
        private int myId;
        private boolean myIsUnobtainable;
        private String myIcon;

        private Builder() {

        }

        public Builder(ToyDocument original) {
            myName = original.myName;
            myId = original.myId;
            myIsUnobtainable = original.myIsUnobtainable;
            myIcon = original.myIcon;
        }

        public Builder withObjectId(ObjectId objectId) {
            myObjectId = objectId;
            return this;
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withId(int id) {
            myId = id;
            return this;
        }

        public Builder isUnobtainable(boolean isUnobtainable) {
            myIsUnobtainable = isUnobtainable;
            return this;
        }

        public Builder withIcon(String itemIcon) {
            myIcon = itemIcon;
            return this;
        }

        public ToyDocument build() {
            return new ToyDocument(this);
        }
    }
}
