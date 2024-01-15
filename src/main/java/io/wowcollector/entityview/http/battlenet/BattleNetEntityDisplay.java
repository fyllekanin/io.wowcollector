package io.wowcollector.entityview.http.battlenet;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class BattleNetEntityDisplay {
    @SerializedName("id")
    private final int myId;

    private BattleNetEntityDisplay(Builder builder) {
        myId = builder.myId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int getId() {
        return myId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetEntityDisplay that = (BattleNetEntityDisplay) o;
        return Objects.equals(myId, that.myId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myId);
    }

    public static class Builder {
        private int myId;

        private Builder() {

        }

        public Builder withId(int id) {
            myId = id;
            return this;
        }

        public BattleNetEntityDisplay build() {
            return new BattleNetEntityDisplay(this);
        }
    }
}
