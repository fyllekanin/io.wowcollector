package io.wowcollector.entityview.http.battlenet;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class BattleNetWowAccount {
    @SerializedName("characters")
    private final List<BattleNetWowAccountCharacter> myCharacters;

    private BattleNetWowAccount(Builder builder) {
        myCharacters = builder.myCharacters;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<BattleNetWowAccountCharacter> getCharacters() {
        return myCharacters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BattleNetWowAccount that = (BattleNetWowAccount) o;
        return Objects.equals(myCharacters, that.myCharacters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myCharacters);
    }

    public static class Builder {
        private List<BattleNetWowAccountCharacter> myCharacters;

        private Builder() {

        }

        public Builder withWowAccounts(List<BattleNetWowAccountCharacter> characters) {
            myCharacters = characters;
            return this;
        }

        public BattleNetWowAccount build() {
            return new BattleNetWowAccount(this);
        }
    }
}
