package io.wowcollector.entityview.http.battlenet;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class BattleNetUserMountDetails {
    @SerializedName("name")
    private final String myName;
    @SerializedName("id")
    private final int myId;

    private BattleNetUserMountDetails(Builder builder) {
        myName = builder.myName;
        myId = builder.myId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return myName;
    }

    public int getId() {
        return myId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetUserMountDetails that = (BattleNetUserMountDetails) o;
        return Objects.equals(myId, that.myId) &&
                Objects.equals(myName, that.myName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myName, myId);
    }

    public static class Builder {
        private String myName;
        private int myId;

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withId(int id) {
            myId = id;
            return this;
        }

        public BattleNetUserMountDetails build() {
            return new BattleNetUserMountDetails(this);
        }
    }
}
