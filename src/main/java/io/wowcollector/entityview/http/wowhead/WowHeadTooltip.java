package io.wowcollector.entityview.http.wowhead;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class WowHeadTooltip {
    @SerializedName("icon")
    private final String myIcon;

    private WowHeadTooltip(Builder builder) {
        myIcon = builder.myIcon;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getIcon() {
        return myIcon;
    }

    public String getFullIconUrl() {
        return String.format("https://wow.zamimg.com/images/wow/icons/large/%s.jpg", myIcon);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        WowHeadTooltip that = (WowHeadTooltip) o;
        return Objects.equals(myIcon, that.myIcon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myIcon);
    }

    public static class Builder {
        private String myIcon;

        private Builder() {

        }

        public Builder withIcon(String icon) {
            myIcon = icon;
            return this;
        }

        public WowHeadTooltip build() {
            return new WowHeadTooltip(this);
        }
    }
}
