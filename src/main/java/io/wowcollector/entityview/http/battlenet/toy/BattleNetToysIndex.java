package io.wowcollector.entityview.http.battlenet.toy;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class BattleNetToysIndex {
    @SerializedName("toys")
    private final List<BattleNetToyIndex> myToys;

    private BattleNetToysIndex(Builder builder) {
        myToys = builder.myToys;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<BattleNetToyIndex> getToys() {
        return myToys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetToysIndex that = (BattleNetToysIndex) o;
        return Objects.equals(myToys, that.myToys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myToys);
    }

    public static class Builder {
        private List<BattleNetToyIndex> myToys;

        private Builder() {

        }

        public Builder withToys(List<BattleNetToyIndex> toys) {
            myToys = toys;
            return this;
        }

        public BattleNetToysIndex build() {
            return new BattleNetToysIndex(this);
        }
    }
}
