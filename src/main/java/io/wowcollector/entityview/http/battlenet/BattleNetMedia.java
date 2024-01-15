package io.wowcollector.entityview.http.battlenet;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.common.data.BlizzardAssetKey;

import java.util.List;
import java.util.Objects;

public class BattleNetMedia {
    @SerializedName("assets")
    private final List<BattleNetAsset> myAssets;
    @SerializedName("id")
    private final long myId;

    private BattleNetMedia(Builder builder) {
        myAssets = builder.myAssets;
        myId = builder.myId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<BattleNetAsset> getAssets() {
        return myAssets;
    }

    public long getId() {
        return myId;
    }

    public String getAssetWithKey(BlizzardAssetKey key) {
        return getAssets()
                .stream()
                .filter(item -> key.equals(item.getKey()))
                .map(BattleNetAsset::getValue)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetMedia that = (BattleNetMedia) o;
        return Objects.equals(myAssets, that.myAssets) &&
                Objects.equals(myId, that.myId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myAssets, myId);
    }

    public static class Builder {
        private List<BattleNetAsset> myAssets;
        private long myId;

        private Builder() {

        }

        public Builder withAssets(List<BattleNetAsset> assets) {
            myAssets = assets;
            return this;
        }

        public Builder withId(long id) {
            myId = id;
            return this;
        }

        public BattleNetMedia build() {
            return new BattleNetMedia(this);
        }
    }
}
