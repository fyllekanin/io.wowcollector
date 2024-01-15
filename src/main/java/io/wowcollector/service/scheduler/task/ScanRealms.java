package io.wowcollector.service.scheduler.task;

import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.entityview.http.battlenet.realm.BattleNetRealms;
import io.wowcollector.entityview.repository.RealmDocument;
import io.wowcollector.repository.RepositoryFactory;
import io.wowcollector.repository.repositories.realm.RealmRepository;
import io.wowcollector.service.http.BattleNetHttpService;

import java.util.List;
import java.util.logging.Logger;

public class ScanRealms implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(ScanRealms.class.getName());

    private final BlizzardRegion myRegion;
    private final RealmRepository myRealmRepository;
    private final BattleNetHttpService myBattleNetHttpService;

    public ScanRealms(BlizzardRegion region, RepositoryFactory repositoryFactory,
                      BattleNetHttpService battleNetService) {
        myRegion = region;
        myRealmRepository = repositoryFactory.of(RealmRepository.class);
        myBattleNetHttpService = battleNetService;
    }

    public void run() {
        try {
            LOGGER.info(String.format("Starting: scan of realms, region: %s", myRegion));
            List<RealmDocument> existingRealms = myRealmRepository.getAll();
            BattleNetRealms battleNetRealms = myBattleNetHttpService.getRealms(myRegion);

            battleNetRealms.getRealms()
                    .forEach(realm -> {

                        RealmDocument newRealmDocument = RealmDocument.newBuilder()
                                .withObjectId(null)
                                .withSlug(realm.getSlug())
                                .withName(realm.getName())
                                .withId(realm.getId())
                                .withRegion(myRegion)
                                .build();

                        RealmDocument existing = existingRealms.stream()
                                .filter(item -> item.getId() == realm.getId() && myRegion.equals(item.getRegion()))
                                .findFirst()
                                .orElse(null);

                        if (existing == null) {
                            myRealmRepository.create(newRealmDocument);
                            LOGGER.info(String.format("Added new realm %d", realm.getId()));
                        } else if (!existing.newBuilderFromCurrent()
                                .withObjectId(null)
                                .build()
                                .equals(newRealmDocument)) {
                            myRealmRepository.update(realm.getId(), newRealmDocument);
                            LOGGER.info(String.format("Updated existing realm %d", realm.getId()));
                        }
                    });
            LOGGER.info(String.format("Done: scan of realms, region: %s", myRegion));
        } catch (Exception e) {
            LOGGER.severe(String.format("Failed: scan of realms, reason: %s", e.getMessage()));
        }
    }
}
