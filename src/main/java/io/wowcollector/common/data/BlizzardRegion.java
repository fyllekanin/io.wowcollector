package io.wowcollector.common.data;

import com.google.gson.annotations.SerializedName;

public enum BlizzardRegion {
    @SerializedName("eu")
    EU("eu"),
    @SerializedName("us")
    US("us"),
    @SerializedName("kr")
    KR("kr"),
    @SerializedName("tw")
    TW("tw");

    private final String myRegion;

    BlizzardRegion(String region) {
        myRegion = region;
    }

    public String getRegion() {
        return myRegion;
    }

    @Override
    public String toString() {
        return myRegion;
    }

    public static BlizzardRegion get(String region) {
        for (BlizzardRegion item : BlizzardRegion.values()) {
            if (item.getRegion()
                    .equals(region)) {
                return item;
            }
        }
        throw new EnumConstantNotPresentException(BlizzardRegion.class, region);
    }
}