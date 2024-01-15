package io.wowcollector.entityview.http.battlenet;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.common.data.BlizzardAssetKey;

import java.util.Objects;

public class BattleNetAsset {
    @SerializedName("key")
    private final BlizzardAssetKey myKey;
    @SerializedName("value")
    private final String myValue;

    private BattleNetAsset(Builder builder) {
        myKey = builder.myKey;
        myValue = builder.myValue;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public BlizzardAssetKey getKey() {
        return myKey;
    }

    public String getValue() {
        return myValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetAsset that = (BattleNetAsset) o;
        return Objects.equals(myKey, that.myKey) &&
                Objects.equals(myValue, that.myValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myKey, myValue);
    }

    public static class Builder {
        private BlizzardAssetKey myKey;
        private String myValue;

        private Builder() {

        }

        public Builder withKey(BlizzardAssetKey key) {
            myKey = key;
            return this;
        }

        public Builder withValue(String value) {
            myValue = value;
            return this;
        }

        public BattleNetAsset build() {
            return new BattleNetAsset(this);
        }
    }
}