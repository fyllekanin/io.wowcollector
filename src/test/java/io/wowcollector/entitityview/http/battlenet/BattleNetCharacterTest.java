package io.wowcollector.entitityview.http.battlenet;

import io.wowcollector.entityview.http.battlenet.BattleNetCharacter;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterActiveSpec;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterClass;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterRace;
import io.wowcollector.entityview.http.battlenet.BattleNetFaction;
import io.wowcollector.entityview.http.battlenet.BattleNetGender;
import io.wowcollector.entityview.http.battlenet.realm.BattleNetRealm;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetCharacterTest {

    @Test
    public void shouldBePossibleToBuild() {
        // Given
        BattleNetFaction faction = BattleNetFaction.newBuilder()
                .build();
        BattleNetRealm realm = BattleNetRealm.newBuilder()
                .build();
        BattleNetCharacterRace race = BattleNetCharacterRace.newBuilder()
                .build();
        BattleNetCharacterClass clazz = BattleNetCharacterClass.newBuilder()
                .build();
        BattleNetGender gender = BattleNetGender.newBuilder()
                .build();
        BattleNetCharacterActiveSpec spec = BattleNetCharacterActiveSpec.newBuilder()
                .build();

        // When
        BattleNetCharacter result = BattleNetCharacter.newBuilder()
                .withFaction(faction)
                .withRealm(realm)
                .withLevel(50)
                .withName("name")
                .withId(5)
                .withRace(race)
                .withClazz(clazz)
                .withGender(gender)
                .withActiveSpec(spec)
                .build();

        // Then
        assertEquals(result.getFaction(), faction);
        assertEquals(result.getRealm(), realm);
        assertEquals(result.getLevel(), 50);
        assertEquals(result.getName(), "name");
        assertEquals(result.getId(), 5);
        assertEquals(result.getRace(), race);
        assertEquals(result.getClazz(), clazz);
        assertEquals(result.getGender(), gender);
        assertEquals(result.getActiveSpec(), spec);
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetCharacter.class)
                .verify();
    }
}
