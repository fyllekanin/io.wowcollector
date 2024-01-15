package io.wowcollector.restserviceimpl.services.collections.aggregator;

import io.wowcollector.entityview.http.battlenet.BattleNetCharacter;
import io.wowcollector.entityview.http.battlenet.BattleNetUserMountCollection;
import io.wowcollector.entityview.repository.MountDocument;
import io.wowcollector.entityview.repository.collectionview.CollectionCategory;
import io.wowcollector.entityview.repository.collectionview.MountCollectionViewDocument;
import io.wowcollector.entityview.response.collection.mount.MountCategoryResponse;
import io.wowcollector.entityview.response.collection.mount.MountResponse;
import io.wowcollector.repository.RepositoryFactory;
import io.wowcollector.repository.repositories.mount.MountRepository;
import io.wowcollector.repository.repositories.setting.SettingRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MountAggregator {
    private static final String UNKNOWN_NAME = "Unknown";
    private final List<Integer> myCollectedIds;
    private final BattleNetCharacter myCharacter;
    private final MountRepository myMountRepository;

    private final MountCollectionViewDocument myCollectionView;
    private final List<Integer> myUnobtainableMounts;

    public MountAggregator(RepositoryFactory repositoryFactory,
                           BattleNetUserMountCollection collection, BattleNetCharacter character,
                           MountCollectionViewDocument mountCollectionViewDocument) {
        myCollectedIds = collection.getMounts()
                .stream()
                .map(item -> item.getUserMountDetails()
                        .getId())
                .collect(Collectors.toList());
        myCharacter = character;

        myCollectionView = mountCollectionViewDocument;
        myMountRepository = repositoryFactory.of(MountRepository.class);
        myUnobtainableMounts = repositoryFactory.of(SettingRepository.class)
                .getUnobtainableMounts();
    }

    public List<MountCategoryResponse> getMountCollection() {
        return getMountCategories(getMountDocumentsMap(myMountRepository.getAll()));
    }

    private List<MountCategoryResponse> getMountCategories(Map<Integer, MountDocument> mounts) {
        List<MountCategoryResponse> result = new ArrayList<>(myCollectionView.getCategories()
                                                                     .stream()
                                                                     .map(category -> getMountCategory(category,
                                                                                                       mounts))
                                                                     .filter(item -> !item.getUserMounts()
                                                                             .isEmpty()
                                                                             || !item.getSubCategories()
                                                                             .isEmpty())
                                                                     .sorted(Comparator.comparing(MountCategoryResponse::getOrder))
                                                                     .toList());
        if (myCollectionView.isUnknownIncluded()) {
            result.add(getUnknownCategory(mounts));
        }
        return result;
    }

    private MountCategoryResponse getUnknownCategory(Map<Integer, MountDocument> mounts) {
        return MountCategoryResponse.newBuilder()
                .withOrder(99999)
                .withName(UNKNOWN_NAME)
                .withSubCategories(Collections.emptyList())
                .withUserMounts(mounts.values()
                                        .stream()
                                        .map(this::getMountResponse)
                                        .collect(Collectors.toList()))
                .build();
    }

    private MountCategoryResponse getMountCategory(CollectionCategory category, Map<Integer, MountDocument> mounts) {

        return MountCategoryResponse.newBuilder()
                .withOrder(category.getOrder())
                .withName(category.getName())
                .withUserMounts(category.getMounts()
                                        .stream()
                                        .map(mount -> {
                                            MountDocument current = mounts.remove(mount.getId());
                                            return current != null ? getMountResponse(current) : null;
                                        })
                                        .filter(Objects::nonNull)
                                        .sorted(Comparator.comparing(MountResponse::getName))
                                        .collect(Collectors.toList()))
                .withSubCategories(category.getCategories()
                                           .stream()
                                           .map(item -> getMountCategory(item, mounts))

                                           .filter(item -> !item.getUserMounts()
                                                   .isEmpty()
                                                   || !item.getSubCategories()
                                                   .isEmpty())
                                           .sorted(Comparator.comparing(MountCategoryResponse::getOrder))
                                           .collect(Collectors.toList()))
                .build();
    }

    private Map<Integer, MountDocument> getMountDocumentsMap(List<MountDocument> mountDocuments) {
        Map<Integer, MountDocument> result = new HashMap<>();
        for (MountDocument item : mountDocuments) {
            if (item.getFaction() != null && !item.getFaction()
                    .equals(myCharacter.getFaction()
                                    .getType())) {
                continue;
            }
            if ((item.isUnobtainable() || myUnobtainableMounts.contains(item.getId())) &&
                    !myCollectedIds.contains(item.getId())) {
                continue;
            }

            result.put(item.getId(), item);
        }
        return result;
    }

    private MountResponse getMountResponse(MountDocument mount) {
        return MountResponse.newBuilder()
                .withIsCollected(myCollectedIds.contains(mount.getId()))
                .withId(mount.getId())
                .withDescription(mount.getDescription())
                .withName(mount.getName())
                .withIcon(mount.getIcon())
                .withCreatureDisplay(mount.getCreatureDisplay())
                .build();
    }
}
