package io.wowcollector.entityview.request.user;

import com.google.gson.annotations.SerializedName;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

public class UserUpdateRequest {
    @SerializedName("defaultCharacterId")
    @Schema(name = "defaultCharacterId")
    private final long myDefaultCharacterId;
    @SerializedName("defaultView")
    @Schema(name = "defaultView")
    private final String myDefaultView;

    private UserUpdateRequest(Builder builder) {
        myDefaultCharacterId = builder.myDefaultCharacterId;
        myDefaultView = builder.myDefaultView;
    }

    private static Builder newBuilder() {
        return new Builder();
    }

    public long getDefaultCharacterId() {
        return myDefaultCharacterId;
    }

    public String getDefaultView() {
        return myDefaultView;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserUpdateRequest that = (UserUpdateRequest) o;
        return Objects.equals(myDefaultCharacterId, that.myDefaultCharacterId) &&
                Objects.equals(myDefaultView, that.myDefaultView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myDefaultCharacterId, myDefaultView);
    }

    public static class Builder {
        private long myDefaultCharacterId;
        private String myDefaultView;

        private Builder() {

        }

        public Builder withDefaultCharacterId(long defaultCharacterId) {
            myDefaultCharacterId = defaultCharacterId;
            return this;
        }

        public Builder withDefaultView(String defaultView) {
            myDefaultView = defaultView;
            return this;
        }

        public UserUpdateRequest build() {
            return new UserUpdateRequest(this);
        }
    }
}
