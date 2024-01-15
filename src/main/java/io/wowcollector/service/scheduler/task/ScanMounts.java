package io.wowcollector.service.scheduler.task;

import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.entityview.http.battlenet.BattleNetFaction;
import io.wowcollector.entityview.http.battlenet.BattleNetMount;
import io.wowcollector.entityview.http.battlenet.mount.BattleNetMountSource;
import io.wowcollector.entityview.http.battlenet.mount.BattleNetMountsIndex;
import io.wowcollector.entityview.http.wowhead.WowHeadTooltip;
import io.wowcollector.entityview.repository.MountDocument;
import io.wowcollector.repository.RepositoryFactory;
import io.wowcollector.repository.repositories.mount.MountRepository;
import io.wowcollector.service.http.BattleNetHttpService;
import io.wowcollector.service.http.WowHeadHttpService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ScanMounts implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(ScanMounts.class.getName());
    private final static String CREATURE_DISPLAY_URL = "https://render.worldofwarcraft" +
            ".com/us/npcs/zoom/creature-display-{id}.jpg";
    private final BlizzardRegion myRegion;
    private final MountRepository myMountRepository;
    private final BattleNetHttpService myBattleNetHttpService;
    private final WowHeadHttpService myWowHeadHttpService;

    public ScanMounts(BlizzardRegion region, RepositoryFactory repositoryFactory,
                      BattleNetHttpService battleNetService,
                      WowHeadHttpService wowHeadHttpService) {
        myRegion = region;
        myMountRepository = repositoryFactory.of(MountRepository.class);
        myBattleNetHttpService = battleNetService;
        myWowHeadHttpService = wowHeadHttpService;
    }

    public void run() {
        try {
            LOGGER.info(String.format("Starting: scan of mounts for region %s", myRegion.getRegion()));
            List<MountDocument> existingMounts = myMountRepository.getAll();
            BattleNetMountsIndex mountsIndex = getMountsIndex();
            if (mountsIndex == null) {
                return;
            }

            mountsIndex.getMounts()
                    .forEach(mountIndex -> {

                        BattleNetMount mount = myBattleNetHttpService.getMount(myRegion, mountIndex.getId());
                        if (mount == null) {
                            LOGGER.warning(String.format("Could not fetch mount with id %d", mountIndex.getId()));
                            return;
                        }

                        String asset = mount.getCreatureDisplays()
                                .stream()
                                .findFirst()
                                .map(item -> CREATURE_DISPLAY_URL.replace("{id}", String.valueOf(item.getId())))
                                .orElse(null);

                        MountDocument newMountDocument = MountDocument.newBuilder()
                                .withObjectId(null)
                                .withId(mountIndex.getId())
                                .withName(mountIndex.getName())
                                .withDescription(mount.getDescription())
                                .withSource(Optional.ofNullable(mount.getSource())
                                                    .map(BattleNetMountSource::getType)
                                                    .orElse(null))
                                .withFaction(Optional.ofNullable(mount.getFaction())
                                                     .map(BattleNetFaction::getType)
                                                     .orElse(null))
                                .withCreatureDisplay(asset)
                                .isUnobtainable(mount.getShouldExcludeIfUncollected())
                                .build();

                        WowHeadTooltip tooltip = myWowHeadHttpService.getMountIcon(mountIndex.getId());
                        if (tooltip != null && tooltip.getIcon() != null) {
                            newMountDocument = newMountDocument
                                    .newBuilderFromCurrent()
                                    .withIcon(tooltip.getFullIconUrl())
                                    .build();
                        }

                        MountDocument existing = existingMounts.stream()
                                .filter(item -> item.getId() == mountIndex.getId())
                                .findFirst()
                                .orElse(null);
                        if (existing == null) {
                            myMountRepository.create(newMountDocument);
                            LOGGER.info(String.format("Added new mount %d", mountIndex.getId()));
                        } else if (!existing.newBuilderFromCurrent()
                                .withObjectId(null)
                                .build()
                                .equals(newMountDocument)) {
                            myMountRepository.update(mountIndex.getId(), newMountDocument);
                            LOGGER.info(String.format("Updated existing mount %d", mountIndex.getId()));
                        }
                    });

            LOGGER.info(String.format("Done: scan of mounts for region %s", myRegion.getRegion()));
        } catch (Exception e) {
            LOGGER.severe(String.format("Failed: scan of mounts, reason: %s", e.getMessage()));
        }
    }

    private BattleNetMountsIndex getMountsIndex() {
        try {
            return myBattleNetHttpService.getMounts(myRegion);
        } catch (URISyntaxException e) {
            LOGGER.warning(String.format("Mount scan got URI exception: %s", e.getMessage()));
            return null;
        } catch (IOException e) {
            LOGGER.warning(String.format("Mount scan got IO exception: %s", e.getMessage()));
            return null;
        } catch (InterruptedException e) {
            LOGGER.warning(String.format("Mount scan was interrupted: %s", e.getMessage()));
            return null;
        }
    }
}
