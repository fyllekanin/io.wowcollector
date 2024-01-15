package io.wowcollector.entityview.http.battlenet;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class BattleNetCreatureDisplay {
    @SerializedName("assets")
    private final List<BattleNetAsset> myAssets;

    private BattleNetCreatureDisplay(Builder builder) {
        myAssets = builder.myAssets;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<BattleNetAsset> getAssets() {
        return myAssets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetCreatureDisplay that = (BattleNetCreatureDisplay) o;
        return Objects.equals(myAssets, that.myAssets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myAssets);
    }

    public static class Builder {
        private List<BattleNetAsset> myAssets;

        private Builder() {

        }

        public Builder withAssets(List<BattleNetAsset> assets) {
            myAssets = assets;
            return this;
        }

        public BattleNetCreatureDisplay build() {
            return new BattleNetCreatureDisplay(this);
        }
    }
}
