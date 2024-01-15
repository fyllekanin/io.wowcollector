package io.wowcollector.entityview.http.battlenet;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.entityview.http.battlenet.realm.BattleNetRealm;

import java.util.Objects;

public class BattleNetCharacter {
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
    @SerializedName("race")
    private final BattleNetCharacterRace myRace;
    @SerializedName("character_class")
    private final BattleNetCharacterClass myClazz;
    @SerializedName("gender")
    private final BattleNetGender myGender;
    @SerializedName("active_spec")
    private final BattleNetCharacterActiveSpec myActiveSpec;

    private BattleNetCharacter(Builder builder) {
        myFaction = builder.myFaction;
        myRealm = builder.myRealm;
        myLevel = builder.myLevel;
        myName = builder.myName;
        myId = builder.myId;
        myRace = builder.myRace;
        myClazz = builder.myClazz;
        myGender = builder.myGender;
        myActiveSpec = builder.myActiveSpec;
    }

    public static Builder newBuilder() {
        return new Builder();
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

    public BattleNetCharacterRace getRace() {
        return myRace;
    }

    public BattleNetCharacterClass getClazz() {
        return myClazz;
    }

    public BattleNetGender getGender() {
        return myGender;
    }

    public BattleNetCharacterActiveSpec getActiveSpec() {
        return myActiveSpec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetCharacter that = (BattleNetCharacter) o;
        return Objects.equals(myFaction, that.myFaction) &&
                Objects.equals(myRealm, that.myRealm) &&
                Objects.equals(myLevel, that.myLevel) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(myId, that.myId) &&
                Objects.equals(myRace, that.myRace) &&
                Objects.equals(myClazz, that.myClazz) &&
                Objects.equals(myGender, that.myGender) &&
                Objects.equals(myActiveSpec, that.myActiveSpec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myFaction, myRealm, myLevel, myName, myId, myRace, myClazz, myGender, myActiveSpec);
    }

    public static class Builder {
        private BattleNetFaction myFaction;
        private BattleNetRealm myRealm;
        private long myLevel;
        private String myName;
        private long myId;
        private BattleNetCharacterRace myRace;
        private BattleNetCharacterClass myClazz;
        private BattleNetGender myGender;

        private BattleNetCharacterActiveSpec myActiveSpec;

        private Builder() {

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

        public Builder withRace(BattleNetCharacterRace race) {
            myRace = race;
            return this;
        }

        public Builder withClazz(BattleNetCharacterClass clazz) {
            myClazz = clazz;
            return this;
        }

        public Builder withGender(BattleNetGender gender) {
            myGender = gender;
            return this;
        }

        public Builder withActiveSpec(BattleNetCharacterActiveSpec activeSpec) {
            myActiveSpec = activeSpec;
            return this;
        }

        public BattleNetCharacter build() {
            return new BattleNetCharacter(this);
        }
    }
}
