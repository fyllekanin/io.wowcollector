package io.wowcollector.entityview.repository.collectionview;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class CollectionMountCategory {
    @SerializedName("id")
    private final int myId;

    private CollectionMountCategory(Builder builder) {
        myId = builder.myId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int getId() {
        return myId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CollectionMountCategory that = (CollectionMountCategory) o;
        return Objects.equals(myId, that.myId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myId);
    }

    public Builder newBuilderFromCurrent() {
        return new Builder(this);
    }

    public static class Builder {
        private int myId;

        private Builder() {

        }

        public Builder(CollectionMountCategory original) {
            myId = original.myId;
        }

        public Builder withId(int id) {
            myId = id;
            return this;
        }

        public Builder withAsset(String asset) {
            return this;
        }

        public CollectionMountCategory build() {
            return new CollectionMountCategory(this);
        }
    }
}
