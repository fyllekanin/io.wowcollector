package io.wowcollector.entityview.http.battlenet;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.entityview.http.battlenet.mount.BattleNetMountSource;

import java.util.List;
import java.util.Objects;

public class BattleNetMount {
    @SerializedName("id")
    private final int myId;
    @SerializedName("description")
    private final String myDescription;
    @SerializedName("source")
    private final BattleNetMountSource mySource;
    @SerializedName("faction")
    private final BattleNetFaction myFaction;
    @SerializedName("creature_displays")
    private final List<BattleNetEntityDisplay> myCreatureDisplays;
    @SerializedName("should_exclude_if_uncollected")
    private final boolean myShouldExcludeIfUncollected;

    private BattleNetMount(Builder builder) {
        myId = builder.myId;
        myDescription = builder.myDescription;
        mySource = builder.mySource;
        myFaction = builder.myFaction;
        myCreatureDisplays = builder.myCreatureDisplays;
        myShouldExcludeIfUncollected = builder.myShouldExcludeIfUncollected;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int getId() {
        return myId;
    }

    public String getDescription() {
        return myDescription;
    }

    public BattleNetMountSource getSource() {
        return mySource;
    }

    public BattleNetFaction getFaction() {
        return myFaction;
    }

    public List<BattleNetEntityDisplay> getCreatureDisplays() {
        return myCreatureDisplays;
    }

    public boolean getShouldExcludeIfUncollected() {
        return myShouldExcludeIfUncollected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetMount that = (BattleNetMount) o;
        return Objects.equals(myId, that.myId) &&
                Objects.equals(myDescription, that.myDescription) &&
                Objects.equals(mySource, that.mySource) &&
                Objects.equals(myFaction, that.myFaction) &&
                Objects.equals(myCreatureDisplays, that.myCreatureDisplays) &&
                Objects.equals(myShouldExcludeIfUncollected, that.myShouldExcludeIfUncollected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myId, myDescription, mySource, myFaction, myCreatureDisplays, myShouldExcludeIfUncollected);
    }

    public static class Builder {
        private int myId;
        private String myDescription;
        private BattleNetMountSource mySource;
        private BattleNetFaction myFaction;
        private List<BattleNetEntityDisplay> myCreatureDisplays;
        private boolean myShouldExcludeIfUncollected;

        public Builder withId(int id) {
            myId = id;
            return this;
        }

        public Builder withDescription(String description) {
            myDescription = description;
            return this;
        }

        public Builder withSource(BattleNetMountSource source) {
            mySource = source;
            return this;
        }

        public Builder withFaction(BattleNetFaction faction) {
            myFaction = faction;
            return this;
        }

        public Builder withCreatureDisplays(List<BattleNetEntityDisplay> creatureDisplays) {
            myCreatureDisplays = creatureDisplays;
            return this;
        }

        public Builder withShouldExcludeIfUncollected(boolean shouldExcludeIfUncollected) {
            myShouldExcludeIfUncollected = shouldExcludeIfUncollected;
            return this;
        }

        public BattleNetMount build() {
            return new BattleNetMount(this);
        }
    }
}
