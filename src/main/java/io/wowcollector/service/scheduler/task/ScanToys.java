package io.wowcollector.service.scheduler.task;

import io.wowcollector.common.data.BlizzardAssetKey;
import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.entityview.http.battlenet.BattleNetMedia;
import io.wowcollector.entityview.http.battlenet.toy.BattleNetToy;
import io.wowcollector.entityview.http.battlenet.toy.BattleNetToysIndex;
import io.wowcollector.entityview.repository.ToyDocument;
import io.wowcollector.repository.RepositoryFactory;
import io.wowcollector.repository.repositories.toy.ToyRepository;
import io.wowcollector.service.http.BattleNetHttpService;

import java.util.List;
import java.util.logging.Logger;

public class ScanToys implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(ScanToys.class.getName());

    private final BlizzardRegion myRegion;
    private final ToyRepository myRepository;
    private final BattleNetHttpService myBattleNetHttpService;

    public ScanToys(BlizzardRegion region, RepositoryFactory repositoryFactory,
                    BattleNetHttpService battleNetHttpService) {
        myRegion = region;
        myRepository = repositoryFactory.of(ToyRepository.class);
        myBattleNetHttpService = battleNetHttpService;
    }

    public void run() {
        try {
            LOGGER.info(String.format("Starting: scan of toys, region: %s", myRegion));
            List<ToyDocument> existingToys = myRepository.getAll();
            BattleNetToysIndex toysIndex = myBattleNetHttpService.getToys(myRegion);

            toysIndex.getToys()
                    .forEach(toy -> {

                        BattleNetToy battleNetToy = myBattleNetHttpService.getToy(myRegion, toy.getId());
                        BattleNetMedia media = myBattleNetHttpService.getItemMedia(myRegion, battleNetToy.getMedia()
                                .getId());

                        ToyDocument newToyDocument = ToyDocument.newBuilder()
                                .withObjectId(null)
                                .withId(toy.getId())
                                .withName(toy.getName())
                                .withIcon(media.getAssetWithKey(BlizzardAssetKey.ICON))
                                .isUnobtainable(battleNetToy.getShouldExcludeIfUncollected())
                                .build();

                        ToyDocument existing = existingToys.stream()
                                .filter(item -> item.getId() == toy.getId())
                                .findFirst()
                                .orElse(null);

                        if (existing == null) {
                            myRepository.create(newToyDocument);
                            LOGGER.info(String.format("Added new toy %d", toy.getId()));
                        } else if (!existing.newBuilderFromCurrent()
                                .withObjectId(null)
                                .build()
                                .equals(newToyDocument)) {
                            myRepository.update(toy.getId(), newToyDocument);
                            LOGGER.info(String.format("Updated existing toy %d", toy.getId()));
                        }
                    });
            LOGGER.info(String.format("Done: scan of toys, region: %s", myRegion));
        } catch (Exception e) {
            LOGGER.severe(String.format("Failed: scan of toys, reason: %s", e.getMessage()));
        }
    }
}
