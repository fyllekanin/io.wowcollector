package io.wowcollector.entityview.repository.collectionview;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CollectionCategory {
    @SerializedName("parent")
    private final String myParent;
    @SerializedName("name")
    private final String myName;
    @SerializedName("order")
    private final int myOrder;
    @SerializedName("mounts")
    private final List<CollectionMountCategory> myMounts;
    @SerializedName("categories")
    private final List<CollectionCategory> myCategories;

    private CollectionCategory(Builder builder) {
        myParent = builder.myParent;
        myName = builder.myName;
        myOrder = builder.myOrder;
        myMounts = builder.myMounts;
        myCategories = builder.myCategories;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getParent() {
        return myParent;
    }

    public String getName() {
        return myName;
    }

    public int getOrder() {
        return myOrder;
    }

    public List<CollectionMountCategory> getMounts() {
        return Objects.requireNonNullElse(myMounts, Collections.emptyList());
    }

    public List<CollectionCategory> getCategories() {
        return Objects.requireNonNullElse(myCategories, Collections.emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CollectionCategory that = (CollectionCategory) o;
        return Objects.equals(myName, that.myName) &&
                Objects.equals(myParent, that.myParent) &&
                Objects.equals(myOrder, that.myOrder) &&
                Objects.equals(myMounts, that.myMounts) &&
                Objects.equals(myCategories, that.myCategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myName, myParent, myOrder, myMounts, myCategories);
    }

    public Builder newBuilderFromCurrent() {
        return new Builder(this);
    }

    public static class Builder {
        private String myParent;
        private String myName;
        private int myOrder;
        private List<CollectionMountCategory> myMounts;
        private List<CollectionCategory> myCategories;

        private Builder() {

        }

        private Builder(CollectionCategory original) {
            myParent = original.myParent;
            myName = original.myName;
            myOrder = original.myOrder;
            myMounts = original.myMounts;
            myCategories = original.myCategories;
        }

        public Builder withParent(String parent) {
            myParent = parent;
            return this;
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withOrder(int order) {
            myOrder = order;
            return this;
        }

        public Builder withMounts(List<CollectionMountCategory> mounts) {
            myMounts = mounts;
            return this;
        }

        public Builder withCategories(List<CollectionCategory> categories) {
            myCategories = categories;
            return this;
        }

        public CollectionCategory build() {
            return new CollectionCategory(this);
        }
    }
}
