package io.wowcollector.entityview.response.collection.mount;

import com.google.gson.annotations.SerializedName;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

public class MountCategoryResponse {
    @SerializedName("name")
    @Schema(name = "name")
    private final String myName;
    @SerializedName("mounts")
    @Schema(name = "mounts")
    private final List<MountResponse> myUserMounts;
    @SerializedName("subCategories")
    @Schema(name = "subCategories")
    private final List<MountCategoryResponse> mySubCategories;
    @SerializedName("order")
    @Schema(name = "order")
    private final int myOrder;

    private MountCategoryResponse(Builder builder) {
        myUserMounts = builder.myUserMounts;
        mySubCategories = builder.mySubCategories;
        myName = builder.myName;
        myOrder = builder.myOrder;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<MountResponse> getUserMounts() {
        return myUserMounts;
    }

    public List<MountCategoryResponse> getSubCategories() {
        return mySubCategories;
    }

    public String getName() {
        return myName;
    }

    public int getOrder() {
        return myOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MountCategoryResponse that = (MountCategoryResponse) o;
        return Objects.equals(myUserMounts, that.myUserMounts) &&
                Objects.equals(mySubCategories, that.mySubCategories) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(myOrder, that.myOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myUserMounts, mySubCategories, myName, myOrder);
    }

    public static class Builder {
        private String myName;
        private List<MountResponse> myUserMounts;
        private List<MountCategoryResponse> mySubCategories;
        private int myOrder;

        private Builder() {
            // Empty
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withUserMounts(List<MountResponse> userMounts) {
            myUserMounts = userMounts;
            return this;
        }

        public Builder withSubCategories(List<MountCategoryResponse> subCategories) {
            mySubCategories = subCategories;
            return this;
        }

        public Builder withOrder(int order) {
            myOrder = order;
            return this;
        }

        public MountCategoryResponse build() {
            return new MountCategoryResponse(this);
        }
    }
}
