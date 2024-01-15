package io.wowcollector.entityview.http.battlenet.realm;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class BattleNetRealms {
    @SerializedName("realms")
    private final List<BattleNetRealm> myRealms;

    private BattleNetRealms(Builder builder) {
        myRealms = builder.myRealms;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<BattleNetRealm> getRealms() {
        return myRealms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetRealms that = (BattleNetRealms) o;
        return Objects.equals(myRealms, that.myRealms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myRealms);
    }

    public static class Builder {
        private List<BattleNetRealm> myRealms;

        public Builder withRealms(List<BattleNetRealm> realms) {
            myRealms = realms;
            return this;
        }

        public BattleNetRealms build() {
            return new BattleNetRealms(this);
        }
    }
}
