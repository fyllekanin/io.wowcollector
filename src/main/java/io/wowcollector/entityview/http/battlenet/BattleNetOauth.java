package io.wowcollector.entityview.http.battlenet;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class BattleNetOauth {
    @SerializedName("access_token")
    private final String myAccessToken;
    @SerializedName("token_type")
    private final String myTokenType;

    private BattleNetOauth(Builder builder) {
        myAccessToken = builder.myAccessToken;
        myTokenType = builder.myTokenType;
    }

    public String getAccessToken() {
        return myAccessToken;
    }

    public String getTokenType() {
        return myTokenType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetOauth that = (BattleNetOauth) o;
        return Objects.equals(myAccessToken, that.myAccessToken) &&
                Objects.equals(myTokenType, that.myTokenType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myAccessToken, myTokenType);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String myAccessToken;
        private String myTokenType;

        private Builder() {

        }

        public Builder withAccessToken(String accessToken) {
            myAccessToken = accessToken;
            return this;
        }

        public Builder withTokenType(String tokenType) {
            myTokenType = tokenType;
            return this;
        }

        public BattleNetOauth build() {
            return new BattleNetOauth(this);
        }
    }
}
