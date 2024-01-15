package io.wowcollector.entityview.http.battlenet.mount;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class BattleNetMountSource {
    @SerializedName("type")
    private final String myType;
    @SerializedName("name")
    private final String myName;

    private BattleNetMountSource(Builder builder) {
        myType = builder.myType;
        myName = builder.myName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getType() {
        return myType;
    }

    public String getName() {
        return myName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetMountSource that = (BattleNetMountSource) o;
        return Objects.equals(myType, that.myType) &&
                Objects.equals(myName, that.myName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myType, myName);
    }

    public static class Builder {
        private String myType;
        private String myName;

        public Builder withType(String type) {
            myType = type;
            return this;
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public BattleNetMountSource build() {
            return new BattleNetMountSource(this);
        }
    }
}
