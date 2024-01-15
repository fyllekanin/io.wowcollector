package io.wowcollector.entityview.http.battlenet.mount;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class BattleNetMountsIndex {
    @SerializedName("mounts")
    private final List<BattleNetMountIndex> myMounts;

    private BattleNetMountsIndex(Builder builder) {
        myMounts = builder.myMounts;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<BattleNetMountIndex> getMounts() {
        return myMounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetMountsIndex that = (BattleNetMountsIndex) o;
        return Objects.equals(myMounts, that.myMounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myMounts);
    }

    public static class Builder {
        private List<BattleNetMountIndex> myMounts;

        public Builder withMounts(List<BattleNetMountIndex> mounts) {
            myMounts = mounts;
            return this;
        }

        public BattleNetMountsIndex build() {
            return new BattleNetMountsIndex(this);
        }
    }
}
