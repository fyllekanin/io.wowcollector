package io.wowcollector.entityview.repository.user;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterActiveSpec;
import io.wowcollector.entityview.http.battlenet.BattleNetFaction;
import io.wowcollector.entityview.http.battlenet.BattleNetGender;
import io.wowcollector.entityview.http.battlenet.BattleNetMedia;
import io.wowcollector.entityview.http.battlenet.BattleNetPlayableClass;
import io.wowcollector.entityview.http.battlenet.BattleNetPlayableRace;
import io.wowcollector.entityview.http.battlenet.realm.BattleNetRealm;

import java.util.Objects;

public class UserCharacter {
    @SerializedName("faction")
    private final BattleNetFaction myFaction;
    @SerializedName("realm")
    private final BattleNetRealm myRealm;
    @SerializedName("level")
    private final long myLevel;
    @SerializedName("name")
    private final String myName;
    @SerializedName("id")
    private final long myId;
    @SerializedName("region")
    private final BlizzardRegion myRegion;
    @SerializedName("media")
    private final BattleNetMedia myMedia;
    @SerializedName("race")
    private final BattleNetPlayableRace myRace;
    @SerializedName("gender")
    private final BattleNetGender myGender;
    @SerializedName("clazz")
    private final BattleNetPlayableClass myClazz;
    @SerializedName("specialization")
    private final BattleNetCharacterActiveSpec mySpecialization;

    private UserCharacter(Builder builder) {
        myFaction = builder.myFaction;
        myRealm = builder.myRealm;
        myLevel = builder.myLevel;
        myName = builder.myName;
        myId = builder.myId;
        myRegion = builder.myRegion;
        myMedia = builder.myMedia;
        myRace = builder.myRace;
        myGender = builder.myGender;
        myClazz = builder.myClazz;
        mySpecialization = builder.mySpecialization;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder newBuilderFromCurrent() {
        return new Builder(this);
    }

    public BattleNetFaction getFaction() {
        return myFaction;
    }

    public long getLevel() {
        return myLevel;
    }

    public BattleNetRealm getRealm() {
        return myRealm;
    }

    public String getName() {
        return myName;
    }

    public long getId() {
        return myId;
    }

    public BlizzardRegion getRegion() {
        return myRegion;
    }

    public BattleNetMedia getMedia() {
        return myMedia;
    }

    public BattleNetPlayableRace getRace() {
        return myRace;
    }

    public BattleNetGender getGender() {
        return myGender;
    }

    public BattleNetPlayableClass getClazz() {
        return myClazz;
    }

    public BattleNetCharacterActiveSpec getSpecialization() {
        return mySpecialization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserCharacter that = (UserCharacter) o;
        return Objects.equals(myFaction, that.myFaction) &&
                Objects.equals(myRealm, that.myRealm) &&
                Objects.equals(myLevel, that.myLevel) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(myId, that.myId) &&
                Objects.equals(myRegion, that.myRegion) &&
                Objects.equals(myMedia, that.myMedia) &&
                Objects.equals(myRace, that.myRace) &&
                Objects.equals(myGender, that.myGender) &&
                Objects.equals(myClazz, that.myClazz) &&
                Objects.equals(mySpecialization, that.mySpecialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myFaction, myRealm, myLevel, myName, myId, myRegion, myMedia, myRace, myGender, myClazz,
                            mySpecialization);
    }

    public static class Builder {
        private BattleNetFaction myFaction;
        private BattleNetRealm myRealm;
        private long myLevel;
        private String myName;
        private long myId;
        private BlizzardRegion myRegion;
        private BattleNetMedia myMedia;
        private BattleNetPlayableRace myRace;
        private BattleNetGender myGender;
        private BattleNetPlayableClass myClazz;
        private BattleNetCharacterActiveSpec mySpecialization;

        private Builder() {

        }

        private Builder(UserCharacter original) {
            myFaction = original.myFaction;
            myRealm = original.myRealm;
            myLevel = original.myLevel;
            myName = original.myName;
            myId = original.myId;
            myRegion = original.myRegion;
            myMedia = original.myMedia;
            myRace = original.myRace;
            myGender = original.myGender;
            myClazz = original.myClazz;
            mySpecialization = original.mySpecialization;
        }

        public Builder withFaction(BattleNetFaction faction) {
            myFaction = faction;
            return this;
        }

        public Builder withRealm(BattleNetRealm realm) {
            myRealm = realm;
            return this;
        }

        public Builder withLevel(long level) {
            myLevel = level;
            return this;
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withId(long id) {
            myId = id;
            return this;
        }

        public Builder withRegion(BlizzardRegion region) {
            myRegion = region;
            return this;
        }

        public Builder withMedia(BattleNetMedia media) {
            myMedia = media;
            return this;
        }

        public Builder withRace(BattleNetPlayableRace race) {
            myRace = race;
            return this;
        }

        public Builder withGender(BattleNetGender gender) {
            myGender = gender;
            return this;
        }

        public Builder withClazz(BattleNetPlayableClass clazz) {
            myClazz = clazz;
            return this;
        }

        public Builder withSpecialization(BattleNetCharacterActiveSpec specialization) {
            mySpecialization = specialization;
            return this;
        }

        public UserCharacter build() {
            return new UserCharacter(this);
        }
    }
}
