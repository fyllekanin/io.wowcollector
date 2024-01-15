package io.wowcollector.entityview.response.collection.mount;

import com.google.gson.annotations.SerializedName;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

public class MountResponse {
    @SerializedName("name")
    @Schema(name = "name")
    private final String myName;
    @SerializedName("description")
    @Schema(name = "description")
    private final String myDescription;
    @SerializedName("id")
    @Schema(name = "id")
    private final int myId;
    @SerializedName("isCollected")
    @Schema(name = "isCollected")
    private final boolean myIsCollected;
    @SerializedName("creatureDisplay")
    @Schema(name = "creatureDisplay")
    private final String myCreatureDisplay;
    @SerializedName("icon")
    @Schema(name = "icon")
    private final String myIcon;

    private MountResponse(Builder builder) {
        myName = builder.myName;
        myDescription = builder.myDescription;
        myId = builder.myId;
        myIsCollected = builder.myIsCollected;
        myCreatureDisplay = builder.myCreatureDisplay;
        myIcon = builder.myIcon;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return myName;
    }

    public String getDescription() {
        return myDescription;
    }

    public int getId() {
        return myId;
    }

    public boolean isCollected() {
        return myIsCollected;
    }

    public String getCreatureDisplay() {
        return myCreatureDisplay;
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
        MountResponse that = (MountResponse) o;
        return Objects.equals(myId, that.myId) &&
                Objects.equals(myIsCollected, that.myIsCollected) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(myDescription, that.myDescription) &&
                Objects.equals(myCreatureDisplay, that.myCreatureDisplay) &&
                Objects.equals(myIcon, that.myIcon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myName, myDescription, myId, myIsCollected, myIcon);
    }

    public static class Builder {
        private String myName;
        private String myDescription;
        private int myId;
        private boolean myIsCollected;
        private String myCreatureDisplay;
        private String myIcon;

        private Builder() {
            // Empty
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withDescription(String description) {
            myDescription = description;
            return this;
        }

        public Builder withId(int id) {
            myId = id;
            return this;
        }

        public Builder withIsCollected(boolean isCollected) {
            myIsCollected = isCollected;
            return this;
        }

        public Builder withCreatureDisplay(String creatureDisplay) {
            myCreatureDisplay = creatureDisplay;
            return this;
        }

        public Builder withIcon(String icon) {
            myIcon = icon;
            return this;
        }

        public MountResponse build() {
            return new MountResponse(this);
        }
    }
}
