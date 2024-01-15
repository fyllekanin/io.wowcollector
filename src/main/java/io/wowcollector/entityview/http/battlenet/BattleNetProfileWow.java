package io.wowcollector.entityview.http.battlenet;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class BattleNetProfileWow {
    @SerializedName("wow_accounts")
    private final List<BattleNetWowAccount> myWowAccounts;

    private BattleNetProfileWow(Builder builder) {
        myWowAccounts = builder.myWowAccounts;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<BattleNetWowAccount> getWoWAccounts() {
        return myWowAccounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetProfileWow that = (BattleNetProfileWow) o;
        return Objects.equals(myWowAccounts, that.myWowAccounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myWowAccounts);
    }

    public static class Builder {
        private List<BattleNetWowAccount> myWowAccounts;

        private Builder() {

        }

        public Builder withWowAccounts(List<BattleNetWowAccount> wowAccounts) {
            myWowAccounts = wowAccounts;
            return this;
        }

        public BattleNetProfileWow build() {
            return new BattleNetProfileWow(this);
        }
    }
}
