package io.wowcollector.entityview.http.battlenet;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class BattleNetUserInfo {
    @SerializedName("battletag")
    private final String myBattleTag;

    private BattleNetUserInfo(Builder builder) {
        myBattleTag = builder.myBattleTag;
    }

    public String getBattleTag() {
        return myBattleTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetUserInfo that = (BattleNetUserInfo) o;
        return Objects.equals(myBattleTag, that.myBattleTag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myBattleTag);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String myBattleTag;

        private Builder() {

        }

        public Builder withBattleTag(String battleTag) {
            myBattleTag = battleTag;
            return this;
        }

        public BattleNetUserInfo build() {
            return new BattleNetUserInfo(this);
        }
    }
}
