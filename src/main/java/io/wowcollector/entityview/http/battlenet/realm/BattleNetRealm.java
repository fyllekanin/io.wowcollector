package io.wowcollector.entityview.http.battlenet.realm;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class BattleNetRealm {
    @SerializedName("id")
    private final int myId;
    @SerializedName("name")
    private final String myName;
    @SerializedName("slug")
    private final String mySlug;

    private BattleNetRealm(Builder builder) {
        myId = builder.myId;
        myName = builder.myName;
        mySlug = builder.mySlug;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int getId() {
        return myId;
    }

    public String getName() {
        return myName;
    }

    public String getSlug() {
        return mySlug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetRealm that = (BattleNetRealm) o;
        return Objects.equals(myId, that.myId) &&
                Objects.equals(myName, that.myName) &&
                Objects.equals(mySlug, that.mySlug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myId, myName, mySlug);
    }

    public static class Builder {
        private int myId;
        private String myName;
        private String mySlug;

        public Builder withId(int id) {
            myId = id;
            return this;
        }

        public Builder withName(String name) {
            myName = name;
            return this;
        }

        public Builder withSlug(String slug) {
            mySlug = slug;
            return this;
        }

        public BattleNetRealm build() {
            return new BattleNetRealm(this);
        }
    }
}
