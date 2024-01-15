package io.wowcollector.entitityview.repository.user;

import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterActiveSpec;
import io.wowcollector.entityview.http.battlenet.BattleNetFaction;
import io.wowcollector.entityview.http.battlenet.BattleNetGender;
import io.wowcollector.entityview.http.battlenet.BattleNetMedia;
import io.wowcollector.entityview.http.battlenet.BattleNetPlayableClass;
import io.wowcollector.entityview.http.battlenet.BattleNetPlayableRace;
import io.wowcollector.entityview.http.battlenet.realm.BattleNetRealm;
import io.wowcollector.entityview.repository.user.UserCharacter;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserCharacterTest {
    private final static BattleNetFaction FACTION = BattleNetFaction.newBuilder()
            .build();
    private final static BattleNetRealm REALM = BattleNetRealm.newBuilder()
            .build();
    private final static BlizzardRegion REGION = BlizzardRegion.EU;
    private final static BattleNetMedia MEDIA = BattleNetMedia.newBuilder()
            .build();
    private final static BattleNetPlayableRace RACE = BattleNetPlayableRace.newBuilder()
            .build();
    private final static BattleNetGender GENDER = BattleNetGender.newBuilder()
            .build();
    private final static BattleNetPlayableClass CLASS = BattleNetPlayableClass.newBuilder()
            .build();
    private final static BattleNetCharacterActiveSpec SPEC = BattleNetCharacterActiveSpec.newBuilder()
            .build();

    @Test
    public void shouldBeSuccessfulToBuild() {
        // When
        UserCharacter result = getDocument();

        // Then
        assertEquals(result.getFaction(), FACTION);
        assertEquals(result.getRealm(), REALM);
        assertEquals(result.getLevel(), 5);
        assertEquals(result.getName(), "name");
        assertEquals(result.getId(), 10);
        assertEquals(result.getRegion(), REGION);
        assertEquals(result.getMedia(), MEDIA);
        assertEquals(result.getRace(), RACE);
        assertEquals(result.getGender(), GENDER);
        assertEquals(result.getClazz(), CLASS);
        assertEquals(result.getSpecialization(), SPEC);
    }

    @Test
    public void shouldContainOriginalValues() {
        // When
        UserCharacter result = getDocument();
        UserCharacter copy = result.newBuilderFromCurrent()
                .build();

        // Then
        assertEquals(result.getFaction(), copy.getFaction());
        assertEquals(result.getRealm(), copy.getRealm());
        assertEquals(result.getLevel(), copy.getLevel());
        assertEquals(result.getName(), copy.getName());
        assertEquals(result.getId(), copy.getId());
        assertEquals(result.getRegion(), copy.getRegion());
        assertEquals(result.getMedia(), copy.getMedia());
        assertEquals(result.getRace(), copy.getRace());
        assertEquals(result.getGender(), copy.getGender());
        assertEquals(result.getClazz(), copy.getClazz());
        assertEquals(result.getSpecialization(), copy.getSpecialization());
    }

    @Test
    public void shouldVerifyEqualsAndHas() {
        EqualsVerifier.simple()
                .forClass(UserCharacter.class)
                .verify();
    }

    private UserCharacter getDocument() {
        return UserCharacter
                .newBuilder()
                .withFaction(FACTION)
                .withRealm(REALM)
                .withLevel(5)
                .withName("name")
                .withId(10)
                .withRegion(REGION)
                .withMedia(MEDIA)
                .withRace(RACE)
                .withGender(GENDER)
                .withClazz(CLASS)
                .withSpecialization(SPEC)
                .build();
    }
}
