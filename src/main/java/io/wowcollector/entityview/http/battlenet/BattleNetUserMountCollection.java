package io.wowcollector.entityview.http.battlenet;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class BattleNetUserMountCollection {
    @SerializedName("mounts")
    private final List<BattleNetUserMount> myMounts;

    private BattleNetUserMountCollection(Builder builder) {
        myMounts = builder.myMounts;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<BattleNetUserMount> getMounts() {
        return myMounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetUserMountCollection that = (BattleNetUserMountCollection) o;
        return Objects.equals(myMounts, that.myMounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myMounts);
    }

    public static class Builder {
        private List<BattleNetUserMount> myMounts;

        public Builder withMounts(List<BattleNetUserMount> mounts) {
            myMounts = mounts;
            return this;
        }

        public BattleNetUserMountCollection build() {
            return new BattleNetUserMountCollection(this);
        }
    }
}
