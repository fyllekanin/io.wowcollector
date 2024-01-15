package io.wowcollector.entitityview.http.battlenet;

import io.wowcollector.entityview.http.battlenet.BattleNetFaction;
import io.wowcollector.entityview.http.battlenet.BattleNetGender;
import io.wowcollector.entityview.http.battlenet.BattleNetPlayableClass;
import io.wowcollector.entityview.http.battlenet.BattleNetPlayableRace;
import io.wowcollector.entityview.http.battlenet.realm.BattleNetRealm;
import io.wowcollector.entityview.http.battlenet.BattleNetWowAccountCharacter;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetWowAccountCharacterTest {
    @Test
    public void shouldBePossibleToBuild() {
        // Given
        BattleNetRealm realm = BattleNetRealm.newBuilder()
                .build();
        BattleNetPlayableClass clazz = BattleNetPlayableClass.newBuilder()
                .build();
        BattleNetPlayableRace race = BattleNetPlayableRace.newBuilder()
                .build();
        BattleNetGender gender = BattleNetGender.newBuilder()
                .build();
        BattleNetFaction faction = BattleNetFaction.newBuilder()
                .build();

        // When
        BattleNetWowAccountCharacter result = BattleNetWowAccountCharacter.newBuilder()
                .withName("name")
                .withId(5)
                .withRealm(realm)
                .withPlayableClass(clazz)
                .withPlayableRace(race)
                .withGender(gender)
                .withFaction(faction)
                .withLevel(10)
                .build();

        // Then
        assertEquals(result.getName(), "name");
        assertEquals(result.getId(), 5);
        assertEquals(result.getRealm(), realm);
        assertEquals(result.getPlayableClass(), clazz);
        assertEquals(result.getPlayableRace(), race);
        assertEquals(result.getGender(), gender);
        assertEquals(result.getFaction(), faction);
        assertEquals(result.getLevel(), 10);
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetWowAccountCharacter.class)
                .verify();
    }
}
