package io.wowcollector.entityview.http.battlenet;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.entityview.http.battlenet.realm.BattleNetRealm;

import java.util.Objects;

public class BattleNetWowAccountCharacter {
    @SerializedName("name")
    private final String myName;
    @SerializedName("id")
    private final long myId;
    @SerializedName("realm")
    private final BattleNetRealm myRealm;
    @SerializedName("playable_class")
    private final BattleNetPlayableClass myPlayableClass;
    @SerializedName("playable_race")
    private final BattleNetPlayableRace myPlayableRace;
    @SerializedName("gender")
    private final BattleNetGender myGender;
    @SerializedName("faction")
    private final BattleNetFaction myFaction;
    @SerializedName("level")
    private final long myLevel;

    private BattleNetWowAccountCharacter(Builder builder) {
        myName = builder.myName;
        myId = builder.myId;
        myRealm = builder.myRealm;
        myPlayableClass = builder.myPlayableClass;
        myPlayableRace = builder.myPlayableRace;
        myGender = builder.myGender;
        myFaction = builder.myFaction;
        myLevel = builder.myLevel;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return myName;
    }

    public long getId() {
        return myId;
    }

    public BattleNetRealm getRealm() {
        return myRealm;
    }

    public BattleNetPlayableClass getPlayableClass() {
        return myPlayableClass;
    }

    public BattleNetPlayableRace getPlayableRace() {
        return myPlayableRace;
    }

    public BattleNetGender getGender() {
        return myGender;
    }

    public BattleNetFaction getFaction() {
        return myFaction;
    }

    public long getLevel() {
        return myLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetWowAccountCharacter that = (BattleNetWowAccountCharacter) o;
        return Objects.equals(myName, that.myName) &&
                Objects.equals(myId, that.myId) &&
                Objects.equals(myRealm, that.myRealm) &&
                Objects.equals(myPlayableClass, that.myPlayableClass) &&
                Objects.equals(myPlayableRace, that.myPlayableRace) &&
                Objects.equals(myGender, that.myGender) &&
                Objects.equals(myFaction, that.myFaction) &&
                Objects.equals(myLevel, that.myLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myName, myId, myRealm, myPlayableClass, myPlayableRace, myGender, myFaction, myLevel);
    }

    public static class Builder {
        private String myName;
        private long myId;
        private BattleNetRealm myRealm;
        private BattleNetPlayableClass myPlayableClass;
        private BattleNetPlayableRace myPlayableRace;
        private BattleNetGender myGender;
        private BattleNetFaction myFaction;
        private long myLevel;

        private Builder() {

        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withId(long id) {
            myId = id;
            return this;
        }

        public Builder withRealm(BattleNetRealm realm) {
            myRealm = realm;
            return this;
        }

        public Builder withPlayableClass(BattleNetPlayableClass playableClass) {
            myPlayableClass = playableClass;
            return this;
        }

        public Builder withPlayableRace(BattleNetPlayableRace playableRace) {
            myPlayableRace = playableRace;
            return this;
        }

        public Builder withGender(BattleNetGender gender) {
            myGender = gender;
            return this;
        }

        public Builder withFaction(BattleNetFaction faction) {
            myFaction = faction;
            return this;
        }

        public Builder withLevel(long level) {
            myLevel = level;
            return this;
        }

        public BattleNetWowAccountCharacter build() {
            return new BattleNetWowAccountCharacter(this);
        }
    }
}
