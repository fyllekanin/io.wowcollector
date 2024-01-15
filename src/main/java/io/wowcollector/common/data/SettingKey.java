package io.wowcollector.common.data;

import com.google.gson.annotations.SerializedName;

public enum SettingKey {
    @SerializedName("unobtainableMounts")
    UNOBTAINABLE_MOUNTS("unobtainableMounts");

    private final String myValue;

    SettingKey(String value) {
        myValue = value;
    }

    public String getValue() {
        return myValue;
    }
}
