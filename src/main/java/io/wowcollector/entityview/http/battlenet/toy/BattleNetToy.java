package io.wowcollector.entityview.http.battlenet.toy;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.entityview.http.battlenet.BattleNetEntityDisplay;
import io.wowcollector.entityview.http.battlenet.BattleNetEntityItem;

import java.util.Objects;

public class BattleNetToy {
    @SerializedName("id")
    private final int myId;
    @SerializedName("media")
    private final BattleNetEntityDisplay myMedia;
    @SerializedName("item")
    private final BattleNetEntityItem myItem;
    @SerializedName("should_exclude_if_uncollected")
    private final boolean myShouldExcludeIfUncollected;

    private BattleNetToy(Builder builder) {
        myId = builder.myId;
        myMedia = builder.myMedia;
        myItem = builder.myItem;
        myShouldExcludeIfUncollected = builder.myShouldExcludeIfUncollected;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int getId() {
        return myId;
    }

    public BattleNetEntityDisplay getMedia() {
        return myMedia;
    }

    public BattleNetEntityItem getItem() {
        return myItem;
    }

    public boolean getShouldExcludeIfUncollected() {
        return myShouldExcludeIfUncollected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetToy that = (BattleNetToy) o;
        return Objects.equals(myId, that.myId) &&
                Objects.equals(myMedia, that.myMedia) &&
                Objects.equals(myItem, that.myItem) &&
                Objects.equals(myShouldExcludeIfUncollected, that.myShouldExcludeIfUncollected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myId, myMedia, myItem, myShouldExcludeIfUncollected);
    }

    public static class Builder {
        private int myId;
        private BattleNetEntityDisplay myMedia;
        private BattleNetEntityItem myItem;
        private boolean myShouldExcludeIfUncollected;

        private Builder() {
            // Empty
        }

        public Builder withId(int id) {
            myId = id;
            return this;
        }

        public Builder withMedia(BattleNetEntityDisplay media) {
            myMedia = media;
            return this;
        }

        public Builder withItem(BattleNetEntityItem item) {
            myItem = item;
            return this;
        }

        public Builder withShouldExcludeIfUncollected(boolean shouldExcludeIfUncollected) {
            myShouldExcludeIfUncollected = shouldExcludeIfUncollected;
            return this;
        }

        public BattleNetToy build() {
            return new BattleNetToy(this);
        }
    }
}
