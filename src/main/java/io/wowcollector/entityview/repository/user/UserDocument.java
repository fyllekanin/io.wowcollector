package io.wowcollector.entityview.repository.user;

import com.google.gson.annotations.SerializedName;
import io.wowcollector.entityview.repository.AbstractDocument;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

public class UserDocument extends AbstractDocument {
    @SerializedName("battleTag")
    private final String myBattleTag;
    @SerializedName("characters")
    private final List<UserCharacter> myCharacters;
    @SerializedName("settings")
    private final UserSettings mySettings;

    private UserDocument(Builder builder) {
        super(builder);
        myBattleTag = builder.myBattleTag;
        myCharacters = builder.myCharacters;
        mySettings = builder.mySettings;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder newBuilderFromCurrent() {
        return new Builder(this);
    }

    public String getBattleTag() {
        return myBattleTag;
    }

    public List<UserCharacter> getCharacters() {
        return myCharacters;
    }

    public UserSettings getSettings() {
        return mySettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDocument that = (UserDocument) o;
        return super.equals(that) &&
                Objects.equals(myBattleTag, that.myBattleTag) &&
                Objects.equals(myCharacters, that.myCharacters) &&
                Objects.equals(mySettings, that.mySettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myBattleTag, myCharacters, mySettings);
    }

    public static class Builder extends AbstractDocumentBuilder {
        private String myBattleTag;
        private List<UserCharacter> myCharacters;
        private UserSettings mySettings;

        private Builder() {

        }

        private Builder(UserDocument document) {
            myObjectId = document.getObjectId();
            myBattleTag = document.getBattleTag();
            myCharacters = document.getCharacters();
            mySettings = document.getSettings();
        }

        public Builder withObjectId(ObjectId objectId) {
            myObjectId = objectId;
            return this;
        }

        public Builder withBattleTag(String battleTag) {
            myBattleTag = battleTag;
            return this;
        }

        public Builder withCharacters(List<UserCharacter> characters) {
            myCharacters = characters;
            return this;
        }

        public Builder withSettings(UserSettings settings) {
            mySettings = settings;
            return this;
        }

        public UserDocument build() {
            return new UserDocument(this);
        }
    }
}
