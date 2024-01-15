package io.wowcollector.entityview.response.collection;

import java.util.List;
import java.util.Objects;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.google.gson.annotations.SerializedName;

import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.entityview.repository.RealmDocument;

public class RegionRealmResponse {
    @SerializedName("realms")
    @Schema(name = "realms")
    private final List<RealmDocument> myRealms;
    @SerializedName("regions")
    @Schema(name = "regions")
    private final List<BlizzardRegion> myRegions;

    private RegionRealmResponse(Builder builder) {
        myRealms = builder.myRealms;
        myRegions = builder.myRegions;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<RealmDocument> getRealms() {
        return myRealms;
    }

    public List<BlizzardRegion> getRegions() {
        return myRegions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RegionRealmResponse that = (RegionRealmResponse) o;
        return Objects.equals(myRealms, that.myRealms) &&
                Objects.equals(myRegions, that.myRegions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myRealms, myRegions);
    }

    public static class Builder {
        private List<RealmDocument> myRealms;
        private List<BlizzardRegion> myRegions;

        private Builder() {

        }

        public Builder withRealms(List<RealmDocument> realms) {
            myRealms = realms;
            return this;
        }

        public Builder withRegions(List<BlizzardRegion> regions) {
            myRegions = regions;
            return this;
        }

        public RegionRealmResponse build() {
            return new RegionRealmResponse(this);
        }
    }
}
