package io.wowcollector.entityview.response.user;

import com.google.gson.annotations.SerializedName;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

public class InitialUserResponse {
    @SerializedName("defaultView")
    @Schema(name = "defaultView")
    private final String myDefaultView;

    private InitialUserResponse(Builder builder) {
        myDefaultView = builder.myDefaultView;
    }

    public static Builder newBuilder() {
        return new Builder();
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
        InitialUserResponse that = (InitialUserResponse) o;
        return Objects.equals(myDefaultView, that.myDefaultView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myDefaultView);
    }

    public static class Builder {
        private String myDefaultView;

        private Builder() {

        }

        public Builder withDefaultView(String defaultView) {
            myDefaultView = defaultView;
            return this;
        }

        public InitialUserResponse build() {
            return new InitialUserResponse(this);
        }
    }
}
