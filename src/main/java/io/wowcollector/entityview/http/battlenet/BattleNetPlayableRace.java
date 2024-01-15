package io.wowcollector.entityview.http.battlenet;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class BattleNetPlayableRace {
    @SerializedName("name")
    private final String myName;
    @SerializedName("id")
    private final long myId;

    private BattleNetPlayableRace(Builder builder) {
        myName = builder.myName;
        myId = builder.myId;
    }

    public String getName() {
        return myName;
    }

    public long getId() {
        return myId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetPlayableRace that = (BattleNetPlayableRace) o;
        return Objects.equals(myName, that.myName) &&
                Objects.equals(myId, that.myId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myName, myId);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String myName;
        private long myId;

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

        public BattleNetPlayableRace build() {
            return new BattleNetPlayableRace(this);
        }
    }
}
