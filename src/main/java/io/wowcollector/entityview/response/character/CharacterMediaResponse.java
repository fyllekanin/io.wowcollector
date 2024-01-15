package io.wowcollector.entityview.response.character;

import java.util.Objects;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.google.gson.annotations.SerializedName;

public class CharacterMediaResponse {
    @SerializedName("avatar")
    @Schema(name = "avatar")
    private final String myAvatar;
    @SerializedName("inset")
    @Schema(name = "inset")
    private final String myInset;
    @SerializedName("mainRaw")
    @Schema(name = "mainRaw")
    private final String myMainRaw;

    private CharacterMediaResponse(Builder builder) {
        myAvatar = builder.myAvatar;
        myInset = builder.myInset;
        myMainRaw = builder.myMainRaw;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getAvatar() {
        return myAvatar;
    }

    public String getInset() {
        return myInset;
    }

    public String getMainRaw() {
        return myMainRaw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CharacterMediaResponse that = (CharacterMediaResponse) o;
        return Objects.equals(myAvatar, that.myAvatar) &&
                Objects.equals(myInset, that.myInset) &&
                Objects.equals(myMainRaw, that.myMainRaw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myAvatar, myInset, myMainRaw);
    }

    public static class Builder {
        private String myAvatar;
        private String myInset;
        private String myMainRaw;

        private Builder() {

        }

        public Builder withAvatar(String avatar) {
            myAvatar = avatar;
            return this;
        }

        public Builder withInset(String inset) {
            myInset = inset;
            return this;
        }

        public Builder withMainRaw(String mainRaw) {
            myMainRaw = mainRaw;
            return this;
        }

        public CharacterMediaResponse build() {
            return new CharacterMediaResponse(this);
        }
    }
}
