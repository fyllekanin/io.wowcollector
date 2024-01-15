package io.wowcollector.entityview.repository;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;

import java.util.Objects;

public class MountDocument extends AbstractDocument {
    @SerializedName("name")
    private final String myName;
    @SerializedName("id")
    private final int myId;
    @SerializedName("description")
    private final String myDescription;
    @SerializedName("source")
    private final String mySource;
    @SerializedName("faction")
    private final String myFaction;
    @SerializedName("creatureDisplay")
    private final String myCreatureDisplay;
    @SerializedName("isUnobtainable")
    private final boolean myIsUnobtainable;
    @SerializedName("icon")
    private final String myIcon;

    private MountDocument(Builder builder) {
        super(builder);
        myName = builder.myName;
        myId = builder.myId;
        myDescription = builder.myDescription;
        mySource = builder.mySource;
        myFaction = builder.myFaction;
        myCreatureDisplay = builder.myCreatureDisplay;
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

    public String getDescription() {
        return myDescription;
    }

    public String getSource() {
        return mySource;
    }

    public String getFaction() {
        return myFaction;
    }

    public String getCreatureDisplay() {
        return myCreatureDisplay;
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
        MountDocument that = (MountDocument) o;
        return super.equals(that) &&
                Objects.equals(myId, that.myId) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(myDescription, that.myDescription) &&
                Objects.equals(mySource, that.mySource) &&
                Objects.equals(myFaction, that.myFaction) &&
                Objects.equals(myCreatureDisplay, that.myCreatureDisplay) &&
                Objects.equals(myIsUnobtainable, that.myIsUnobtainable) &&
                Objects.equals(myIcon, that.myIcon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myName, myId, myDescription, mySource, myFaction, myCreatureDisplay,
                            myIsUnobtainable, myIcon);
    }

    public static class Builder extends AbstractDocumentBuilder {
        private String myName;
        private int myId;
        private String myDescription;
        private String mySource;
        private String myFaction;
        private String myCreatureDisplay;
        private boolean myIsUnobtainable;
        private String myIcon;

        private Builder() {

        }

        private Builder(MountDocument document) {
            myObjectId = document.getObjectId();
            myName = document.getName();
            myId = document.getId();
            myDescription = document.getDescription();
            mySource = document.getSource();
            myFaction = document.getFaction();
            myCreatureDisplay = document.getCreatureDisplay();
            myIsUnobtainable = document.isUnobtainable();
            myIcon = document.getIcon();
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

        public Builder withDescription(String description) {
            myDescription = description;
            return this;
        }

        public Builder withSource(String source) {
            mySource = source;
            return this;
        }

        public Builder withFaction(String faction) {
            myFaction = faction;
            return this;
        }

        public Builder withCreatureDisplay(String creatureDisplay) {
            myCreatureDisplay = creatureDisplay;
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

        public MountDocument build() {
            return new MountDocument(this);
        }
    }
}
