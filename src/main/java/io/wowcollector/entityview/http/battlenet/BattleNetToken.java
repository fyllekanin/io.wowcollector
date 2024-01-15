package io.wowcollector.entityview.http.battlenet;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class BattleNetToken {
    @SerializedName("access_token")
    private final String myAccessToken;
    @SerializedName("expires_in")
    private final long myExpiresIn;
    @SerializedName("issued_at")
    private final long myIssuedAt;

    private BattleNetToken(Builder builder) {
        myAccessToken = builder.myAccessToken;
        myExpiresIn = builder.myExpiresIn;
        myIssuedAt = builder.myIssuedAt;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public synchronized String getAccessToken() {
        return myAccessToken;
    }

    public synchronized boolean isExpired() {
        if (myExpiresIn <= 0) {
            return true;
        }

        long expirationTimeMillis = myIssuedAt + (myExpiresIn * 1000);

        return expirationTimeMillis <= System.currentTimeMillis();
    }

    public synchronized long getExpiresIn() {
        return myExpiresIn;
    }

    public synchronized long getIssuedAt() {
        return myIssuedAt;
    }

    public Builder newBuilderFromCurrent() {
        return new Builder(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetToken that = (BattleNetToken) o;
        return Objects.equals(myAccessToken, that.myAccessToken) &&
                Objects.equals(myExpiresIn, that.myExpiresIn) &&
                Objects.equals(myIssuedAt, that.myIssuedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myAccessToken, myExpiresIn, myIssuedAt);
    }

    public static class Builder {
        private String myAccessToken;
        private long myExpiresIn;
        private long myIssuedAt;

        private Builder() {

        }

        private Builder(BattleNetToken original) {
            myAccessToken = original.myAccessToken;
            myExpiresIn = original.myExpiresIn;
            myIssuedAt = original.myIssuedAt;
        }

        public Builder withAccessToken(String accessToken) {
            myAccessToken = accessToken;
            return this;
        }

        public Builder withExpiresIn(long expiresIn) {
            myExpiresIn = expiresIn;
            return this;
        }

        public Builder withIssuedAt(long issuedAt) {
            myIssuedAt = issuedAt;
            return this;
        }

        public BattleNetToken build() {
            return new BattleNetToken(this);
        }
    }
}
