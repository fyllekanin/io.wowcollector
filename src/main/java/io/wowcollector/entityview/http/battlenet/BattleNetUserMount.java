package io.wowcollector.entityview.http.battlenet;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class BattleNetUserMount {
    @SerializedName("mount")
    private final BattleNetUserMountDetails myUserMountDetails;

    private BattleNetUserMount(Builder builder) {
        myUserMountDetails = builder.myUserMountDetails;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public BattleNetUserMountDetails getUserMountDetails() {
        return myUserMountDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetUserMount that = (BattleNetUserMount) o;
        return Objects.equals(myUserMountDetails, that.myUserMountDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myUserMountDetails);
    }

    public static class Builder {
        private BattleNetUserMountDetails myUserMountDetails;

        public Builder withUserMountDetails(BattleNetUserMountDetails userMountDetails) {
            myUserMountDetails = userMountDetails;
            return this;
        }

        public BattleNetUserMount build() {
            return new BattleNetUserMount(this);
        }
    }
}
