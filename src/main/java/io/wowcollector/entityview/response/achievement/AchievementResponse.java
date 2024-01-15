package io.wowcollector.entityview.response.achievement;

import com.google.gson.annotations.SerializedName;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

public class AchievementResponse {
    @SerializedName("id")
    @Schema(name = "id")
    private final long myId;
    @SerializedName("name")
    @Schema(name = "name")
    private final String myName;
    @SerializedName("description")
    @Schema(name = "description")
    private final String myDescription;
    @SerializedName("icon")
    @Schema(name = "icon")
    private final String myIcon;
    @SerializedName("completedAt")
    @Schema(name = "completedAt")
    private final long myCompletedAt;

    private AchievementResponse(Builder builder) {
        myId = builder.myId;
        myName = builder.myName;
        myDescription = builder.myDescription;
        myIcon = builder.myIcon;
        myCompletedAt = builder.myCompletedAt;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return myId;
    }

    public String getName() {
        return myName;
    }

    public String getDescription() {
        return myDescription;
    }

    public String getIcon() {
        return myIcon;
    }

    public long getCompletedAt() {
        return myCompletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AchievementResponse that = (AchievementResponse) o;
        return Objects.equals(myId, that.myId) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(myDescription, that.myDescription) &&
                Objects.equals(myIcon, that.myIcon) &&
                Objects.equals(myCompletedAt, that.myCompletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myId, myName, myDescription, myIcon, myCompletedAt);
    }

    public static class Builder {
        private long myId;
        private String myName;
        private String myDescription;
        private String myIcon;
        private long myCompletedAt;

        private Builder() {

        }

        public Builder withId(long id) {
            myId = id;
            return this;
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withDescription(String description) {
            myDescription = description;
            return this;
        }

        public Builder withIcon(String icon) {
            myIcon = icon;
            return this;
        }

        public Builder withCompletedAt(long completedAt) {
            myCompletedAt = completedAt;
            return this;
        }

        public AchievementResponse build() {
            return new AchievementResponse(this);
        }
    }
}
