package io.wowcollector.entityview.response.collection.toy;

import com.google.gson.annotations.SerializedName;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

public class ToyResponse {
    @SerializedName("id")
    @Schema(name = "id")
    private final int myId;
    @SerializedName("name")
    @Schema(name = "name")
    private final String myName;
    @SerializedName("isCollected")
    @Schema(name = "isCollected")
    private final boolean myIsCollected;
    @SerializedName("icon")
    @Schema(name = "icon")
    private final String myIcon;

    private ToyResponse(Builder builder) {
        myId = builder.myId;
        myName = builder.myName;
        myIsCollected = builder.myIsCollected;
        myIcon = builder.myIcon;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int getId() {
        return myId;
    }

    public String getName() {
        return myName;
    }

    public boolean isCollected() {
        return myIsCollected;
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
        ToyResponse that = (ToyResponse) o;
        return Objects.equals(myId, that.myId) &&
                Objects.equals(myIsCollected, that.myIsCollected) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(myIcon, that.myIcon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myName, myId, myIsCollected, myIcon);
    }

    public static class Builder {
        private int myId;
        private String myName;
        private boolean myIsCollected;
        private String myIcon;

        private Builder() {
            // Empty
        }

        public Builder withId(int id) {
            myId = id;
            return this;
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withIsCollected(boolean isCollected) {
            myIsCollected = isCollected;
            return this;
        }

        public Builder withIcon(String icon) {
            myIcon = icon;
            return this;
        }

        public ToyResponse build() {
            return new ToyResponse(this);
        }
    }
}
