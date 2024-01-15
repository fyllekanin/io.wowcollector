package io.wowcollector.entityview.response.user;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.entityview.response.character.CharacterResponse;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

public class AuthorizationResponse {
    @SerializedName("battleTag")
    @Schema(name = "battleTag")
    private final String myBattleTag;
    @SerializedName("defaultCharacter")
    @Schema(name = "defaultCharacter")
    private final CharacterResponse myDefaultCharacter;
    @SerializedName("accessToken")
    @Schema(name = "accessToken")
    private final String myAccessToken;
    @SerializedName("refreshToken")
    @Schema(name = "refreshToken")
    private final String myRefreshToken;

    private AuthorizationResponse(Builder builder) {
        myBattleTag = builder.myBattleTag;
        myDefaultCharacter = builder.myDefaultCharacter;
        myAccessToken = builder.myAccessToken;
        myRefreshToken = builder.myRefreshToken;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getBattleTag() {
        return myBattleTag;
    }

    public CharacterResponse myDefaultCharacter() {
        return myDefaultCharacter;
    }

    public String getAccessToken() {
        return myAccessToken;
    }

    public String getRefreshToken() {
        return myRefreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AuthorizationResponse that = (AuthorizationResponse) o;
        return Objects.equals(myBattleTag, that.myBattleTag) &&
                Objects.equals(myDefaultCharacter, that.myDefaultCharacter) &&
                Objects.equals(myAccessToken, that.myAccessToken) &&
                Objects.equals(myRefreshToken, that.myRefreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myBattleTag, myDefaultCharacter, myAccessToken, myRefreshToken);
    }

    public static class Builder {
        private String myBattleTag;
        private CharacterResponse myDefaultCharacter;
        private String myAccessToken;
        private String myRefreshToken;

        private Builder() {

        }

        public Builder withBattleTag(String battleTag) {
            myBattleTag = battleTag;
            return this;
        }

        public Builder withDefaultCharacter(CharacterResponse defaultCharacter) {
            myDefaultCharacter = defaultCharacter;
            return this;
        }

        public Builder withAccessToken(String accessToken) {
            myAccessToken = accessToken;
            return this;
        }

        public Builder withRefreshToken(String refreshToken) {
            myRefreshToken = refreshToken;
            return this;
        }

        public AuthorizationResponse build() {
            return new AuthorizationResponse(this);
        }
    }
}
