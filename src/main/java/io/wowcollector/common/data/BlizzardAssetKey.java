package io.wowcollector.common.data;

import com.google.gson.annotations.SerializedName;

public enum BlizzardAssetKey {
    @SerializedName("avatar")
    AVATAR("avatar"),
    @SerializedName("inset")
    INSET("inset"),
    @SerializedName("main-raw")
    MAIN_RAW("main-raw"),
    @SerializedName("icon")
    ICON("icon");

    private final String myValue;

    BlizzardAssetKey(String value) {
        myValue = value;
    }

    public String getValue() {
        return myValue;
    }
}
