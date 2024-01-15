package io.wowcollector.entityview.repository.user;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class UserSettings {
    @SerializedName("defaultView")
    private final String myDefaultView;
    @SerializedName("defaultCharacterId")
    private final long myDefaultCharacterId;

    private UserSettings(Builder builder) {
        myDefaultView = builder.myDefaultView;
        myDefaultCharacterId = builder.myDefaultCharacterId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getDefaultView() {
        return myDefaultView;
    }

    public long getDefaultCharacterId() {
        return myDefaultCharacterId;
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
        UserSettings that = (UserSettings) o;
        return Objects.equals(myDefaultView, that.myDefaultView) &&
                Objects.equals(myDefaultCharacterId, that.myDefaultCharacterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myDefaultView, myDefaultCharacterId);
    }

    public static class Builder {
        private String myDefaultView;
        private long myDefaultCharacterId;

        private Builder() {

        }

        private Builder(UserSettings original) {
            myDefaultView = original.myDefaultView;
            myDefaultCharacterId = original.myDefaultCharacterId;
        }

        public Builder withDefaultView(String defaultView) {
            myDefaultView = defaultView;
            return this;
        }

        public Builder withDefaultCharacterId(long defaultCharacterId) {
            myDefaultCharacterId = defaultCharacterId;
            return this;
        }

        public UserSettings build() {
            return new UserSettings(this);
        }
    }
}
