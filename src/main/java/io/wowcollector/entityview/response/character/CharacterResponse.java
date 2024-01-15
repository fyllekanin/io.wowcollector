package io.wowcollector.entityview.response.character;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.common.data.BlizzardRegion;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

public class CharacterResponse {
    @SerializedName("id")
    @Schema(name = "id")
    private final long myId;
    @SerializedName("name")
    @Schema(name = "name")
    private final String myName;
    @SerializedName("realm")
    @Schema(name = "realm")
    private final String myRealm;
    @SerializedName("region")
    @Schema(name = "region")
    private final BlizzardRegion myRegion;
    @SerializedName("media")
    @Schema(name = "media")
    private final CharacterMediaResponse myMedia;
    @SerializedName("level")
    @Schema(name = "level")
    private final long myLevel;
    @SerializedName("faction")
    @Schema(name = "faction")
    private final String myFaction;
    @SerializedName("race")
    @Schema(name = "race")
    private final String myRace;
    @SerializedName("gender")
    @Schema(name = "gender")
    private final String myGender;
    @SerializedName("clazz")
    @Schema(name = "clazz")
    private final String myClazz;
    @SerializedName("specialization")
    @Schema(name = "specialization")
    private final String mySpecialization;

    private CharacterResponse(Builder builder) {
        myId = builder.myId;
        myName = builder.myName;
        myRealm = builder.myRealm;
        myRegion = builder.myRegion;
        myMedia = builder.myMedia;
        myLevel = builder.myLevel;
        myFaction = builder.myFaction;
        myRace = builder.myRace;
        myGender = builder.myGender;
        myClazz = builder.myClazz;
        mySpecialization = builder.mySpecialization;
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

    public String getRealm() {
        return myRealm;
    }

    public BlizzardRegion getRegion() {
        return myRegion;
    }

    public CharacterMediaResponse getMedia() {
        return myMedia;
    }

    public long getLevel() {
        return myLevel;
    }

    public String getFaction() {
        return myFaction;
    }

    public String getRace() {
        return myRace;
    }

    public String getGender() {
        return myGender;
    }

    public String getClazz() {
        return myClazz;
    }

    public String getSpecialization() {
        return mySpecialization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CharacterResponse that = (CharacterResponse) o;
        return Objects.equals(myName, that.myName) &&
                Objects.equals(myRealm, that.myRealm) &&
                Objects.equals(myId, that.myId) &&
                Objects.equals(myRegion, that.myRegion) &&
                Objects.equals(myMedia, that.myMedia) &&
                Objects.equals(myLevel, that.myLevel) &&
                Objects.equals(myFaction, that.myFaction) &&
                Objects.equals(myRace, that.myRace) &&
                Objects.equals(myGender, that.myGender) &&
                Objects.equals(myClazz, that.myClazz) &&
                Objects.equals(mySpecialization, that.mySpecialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myName, myRealm, myId, myRegion, myMedia, myLevel, myFaction, myRace, myGender, myClazz,
                            mySpecialization);
    }

    public static class Builder {
        private long myId;
        private String myName;
        private String myRealm;
        private BlizzardRegion myRegion;
        private CharacterMediaResponse myMedia;
        private long myLevel;
        private String myFaction;
        private String myRace;
        private String myGender;
        private String myClazz;
        private String mySpecialization;

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

        public Builder withRealm(String realm) {
            myRealm = realm;
            return this;
        }

        public Builder withRegion(BlizzardRegion region) {
            myRegion = region;
            return this;
        }

        public Builder withMedia(CharacterMediaResponse media) {
            myMedia = media;
            return this;
        }

        public Builder withLevel(long level) {
            myLevel = level;
            return this;
        }

        public Builder withFaction(String faction) {
            myFaction = faction;
            return this;
        }

        public Builder withRace(String race) {
            myRace = race;
            return this;
        }

        public Builder withGender(String gender) {
            myGender = gender;
            return this;
        }

        public Builder withClazz(String clazz) {
            myClazz = clazz;
            return this;
        }

        public Builder withSpecialization(String specialization) {
            mySpecialization = specialization;
            return this;
        }

        public CharacterResponse build() {
            return new CharacterResponse(this);
        }
    }
}
