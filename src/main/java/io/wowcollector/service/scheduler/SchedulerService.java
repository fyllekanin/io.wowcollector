package io.wowcollector.service.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.Startup;
import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.repository.RepositoryFactory;
import io.wowcollector.service.http.BattleNetHttpService;
import io.wowcollector.service.http.WowHeadHttpService;
import io.wowcollector.service.scheduler.task.ScanAchievements;
import io.wowcollector.service.scheduler.task.ScanMounts;
import io.wowcollector.service.scheduler.task.ScanRealms;
import io.wowcollector.service.scheduler.task.ScanToys;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Startup
@Singleton
public class SchedulerService {
    private final ScheduledExecutorService myScheduler = Executors.newScheduledThreadPool(8);
    private final BattleNetHttpService myBattleNetHttpService;
    private final boolean myIsScannerApp;

    @Inject
    private RepositoryFactory myRepositoryFactory;
    @Inject
    private WowHeadHttpService myWowHeadHttpService;

    public SchedulerService(@ConfigProperty(name = "BATTLE_NET_CLIENT_ID") String clientId,
                            @ConfigProperty(name = "BATTLE_NET_CLIENT_SECRET") String secret,
                            @ConfigProperty(name = "IS_SCANNER_APP", defaultValue = "false") boolean scannerApp) {
        myBattleNetHttpService = new BattleNetHttpService(clientId, secret);
        myIsScannerApp = scannerApp;
    }

    @PostConstruct
    public void init() {
        if (myIsScannerApp) {
            scheduleScanners();
        }
    }

    private void scheduleScanners() {

        myScheduler.scheduleAtFixedRate(
                new ScanAchievements(BlizzardRegion.EU, myRepositoryFactory,
                                     myBattleNetHttpService),
                1,
                12,
                TimeUnit.HOURS);
        myScheduler.scheduleAtFixedRate(
                new ScanMounts(BlizzardRegion.EU, myRepositoryFactory, myBattleNetHttpService, myWowHeadHttpService),
                3,
                12,
                TimeUnit.HOURS);

        myScheduler.scheduleAtFixedRate(
                new ScanToys(BlizzardRegion.EU, myRepositoryFactory, myBattleNetHttpService),
                5,
                12,
                TimeUnit.HOURS
        );


        long initialDelay = 7;
        for (BlizzardRegion region : BlizzardRegion.values()) {
            myScheduler.scheduleAtFixedRate(
                    new ScanRealms(region, myRepositoryFactory, myBattleNetHttpService),
                    initialDelay,
                    12,
                    TimeUnit.HOURS);

            initialDelay = initialDelay + 2;
        }
    }
}
