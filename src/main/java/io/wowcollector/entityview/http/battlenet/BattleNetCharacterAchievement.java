package io.wowcollector.entityview.http.battlenet;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class BattleNetCharacterAchievement {
    @SerializedName("id")
    private final long myId;
    @SerializedName("completed_timestamp")
    private final long myCompletedTimestamp;

    private BattleNetCharacterAchievement(Builder builder) {
        myId = builder.myId;
        myCompletedTimestamp = builder.myCompletedTimestamp;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return myId;
    }

    public long getCompletedTimestamp() {
        return myCompletedTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetCharacterAchievement that = (BattleNetCharacterAchievement) o;
        return Objects.equals(myId, that.myId) &&
                Objects.equals(myCompletedTimestamp, that.myCompletedTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myId, myCompletedTimestamp);
    }

    public static class Builder {
        private long myId;
        private long myCompletedTimestamp;

        private Builder() {

        }

        public Builder withId(long id) {
            myId = id;
            return this;
        }

        public Builder withCompletedTimestamp(long completedTimestamp) {
            myCompletedTimestamp = completedTimestamp;
            return this;
        }

        public BattleNetCharacterAchievement build() {
            return new BattleNetCharacterAchievement(this);
        }
    }
}
