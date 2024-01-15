package io.wowcollector.entityview.response.collection;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.entityview.response.collection.mount.MountCategoryResponse;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

public class CharacterMountCollectionResponse {
    @SerializedName("mountCategories")
    @Schema(name = "mountCategories")
    private final List<MountCategoryResponse> myMountCategories;

    private CharacterMountCollectionResponse(Builder builder) {
        myMountCategories = builder.myMountCategories;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder newBuilderFromCurrent() {
        return new Builder(this);
    }

    public List<MountCategoryResponse> getMountCategories() {
        return myMountCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CharacterMountCollectionResponse that = (CharacterMountCollectionResponse) o;
        return Objects.equals(myMountCategories, that.myMountCategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myMountCategories);
    }

    public static class Builder {
        private List<MountCategoryResponse> myMountCategories;

        private Builder() {

        }

        private Builder(CharacterMountCollectionResponse original) {
            myMountCategories = original.myMountCategories;
        }

        public Builder withMountCategories(List<MountCategoryResponse> mountCategories) {
            myMountCategories = mountCategories;
            return this;
        }

        public CharacterMountCollectionResponse build() {
            return new CharacterMountCollectionResponse(this);
        }
    }
}
