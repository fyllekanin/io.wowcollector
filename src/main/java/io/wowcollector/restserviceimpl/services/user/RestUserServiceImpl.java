package io.wowcollector.restserviceimpl.services.user;

import com.google.gson.Gson;
import io.wowcollector.common.data.BlizzardAssetKey;
import io.wowcollector.common.util.JwtUtils;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterAchievement;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterAchievements;
import io.wowcollector.entityview.http.battlenet.BattleNetMedia;
import io.wowcollector.entityview.repository.achievement.AchievementDocument;
import io.wowcollector.entityview.repository.user.UserCharacter;
import io.wowcollector.entityview.repository.user.UserDocument;
import io.wowcollector.entityview.request.user.UserUpdateRequest;
import io.wowcollector.entityview.response.achievement.AchievementResponse;
import io.wowcollector.entityview.response.character.CharacterMediaResponse;
import io.wowcollector.entityview.response.character.CharacterResponse;
import io.wowcollector.entityview.response.user.InitialUserResponse;
import io.wowcollector.entityview.response.user.UserHomePageResponse;
import io.wowcollector.repository.RepositoryFactory;
import io.wowcollector.repository.repositories.achievement.AchievementRepository;
import io.wowcollector.repository.repositories.user.UserRepository;
import io.wowcollector.restservice.user.RestUserService;
import io.wowcollector.service.http.BattleNetHttpService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class RestUserServiceImpl implements RestUserService {
    private static final Logger LOGGER = Logger.getLogger(RestUserService.class.getName());

    @Inject
    private RepositoryFactory myRepositoryFactory;
    @Inject
    private BattleNetHttpService myBattleNetHttpService;
    @ConfigProperty(name = "JWT_SECRET")
    private Optional<String> myJwtSecret;

    @Override
    public Response getUserHomePage(String authHeader) {
        if (myJwtSecret.isEmpty()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("JWT_SECRET environment missing")
                    .build();
        }

        try {
            ObjectId userObjectId = JwtUtils.getIdIfValid(JwtUtils.getTokenFromAuthHeader(authHeader),
                                                          myJwtSecret.get());
            UserDocument userDocument = myRepositoryFactory.of(UserRepository.class)
                    .getByObjectId(userObjectId);

            return Response.status(Response.Status.OK)
                    .entity(new Gson().toJson(UserHomePageResponse.newBuilder()
                                                      .withLatestAchievements(getAchievements(userDocument))
                                                      .withCharacters(getCharacters(userDocument))
                                                      .build()))
                    .build();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public Response getInitial(String authHeader) {
        if (myJwtSecret.isEmpty()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("JWT_SECRET environment missing")
                    .build();
        }

        try {
            InitialUserResponse response = null;
            if (authHeader != null) {
                if (!isAccessTokenValid(authHeader)) {
                    return Response.status(Response.Status.UNAUTHORIZED)
                            .build();
                }
                ObjectId userObjectId = JwtUtils.getIdIfValid(JwtUtils.getTokenFromAuthHeader(authHeader),
                                                              myJwtSecret.get());
                UserDocument userDocument = myRepositoryFactory.of(UserRepository.class)
                        .getByObjectId(userObjectId);

                response = InitialUserResponse.newBuilder()
                        .withDefaultView(userDocument.getSettings()
                                                 .getDefaultView())
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity(new Gson().toJson(response))
                    .build();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public Response updateUser(String authHeader, String body) {
        if (myJwtSecret.isEmpty()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("JWT_SECRET environment missing")
                    .build();
        }

        try {
            ObjectId userObjectId = JwtUtils.getIdIfValid(JwtUtils.getTokenFromAuthHeader(authHeader),
                                                          myJwtSecret.get());
            UserDocument userDocument = myRepositoryFactory.of(UserRepository.class)
                    .getByObjectId(userObjectId);
            UserUpdateRequest userUpdateRequest = new Gson().fromJson(body, UserUpdateRequest.class);

            if (userDocument.getCharacters()
                    .stream()
                    .noneMatch(item -> item.getId() == userUpdateRequest.getDefaultCharacterId())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(String.format(
                                "The default character %d is not a valid character ID",
                                userUpdateRequest.getDefaultCharacterId()))
                        .build();
            }

            UserDocument updated = userDocument.newBuilderFromCurrent()
                    .withSettings(userDocument.getSettings()
                                          .newBuilderFromCurrent()
                                          .withDefaultCharacterId(userUpdateRequest.getDefaultCharacterId())
                                          .withDefaultView(userUpdateRequest.getDefaultView())
                                          .build())
                    .build();

            myRepositoryFactory.of(UserRepository.class)
                    .update(updated);

            return Response.status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    private List<AchievementResponse> getAchievements(UserDocument userDocument) {
        UserCharacter defaultCharacter = userDocument.getCharacters()
                .stream()
                .filter(item -> item.getId() == userDocument.getSettings()
                        .getDefaultCharacterId())
                .findFirst()
                .orElse(null);
        if (defaultCharacter == null) {
            return Collections.emptyList();
        }

        BattleNetCharacterAchievements achievements =
                myBattleNetHttpService.getCharacterAchievements(defaultCharacter.getRegion(),
                                                                defaultCharacter.getRealm()
                                                                        .getSlug(), defaultCharacter.getName());

        List<BattleNetCharacterAchievement> items = achievements.getAchievements()
                .stream()
                .parallel()
                .sorted(Comparator.comparingLong(BattleNetCharacterAchievement::getCompletedTimestamp)
                                .reversed())
                .toList();
        List<AchievementResponse> result = new ArrayList<>();

        for (BattleNetCharacterAchievement item : items) {
            if (result.size() == 5) {
                break;
            }
            AchievementDocument document = myRepositoryFactory.of(AchievementRepository.class)
                    .getById(item.getId());
            if (document == null) {
                continue;
            }

            result.add(AchievementResponse.newBuilder()
                               .withCompletedAt(item.getCompletedTimestamp())
                               .withId(document.getId())
                               .withName(document.getName())
                               .withDescription(document.getDescription())
                               .withIcon(document.getMedia()
                                                 .getAssetWithKey(BlizzardAssetKey.ICON))
                               .build());
        }

        return result;
    }

    private List<CharacterResponse> getCharacters(UserDocument user) {
        return user.getCharacters()
                .stream()
                .map(item -> CharacterResponse.newBuilder()
                        .withId(item.getId())
                        .withName(item.getName())
                        .withRegion(item.getRegion())
                        .withMedia(getCharacterMedia(item.getMedia()))
                        .withRealm(item.getRealm()
                                           .getSlug())
                        .withLevel(item.getLevel())
                        .withFaction(item.getFaction()
                                             .getType())
                        .withRace(item.getRace()
                                          .getName())
                        .withGender(item.getGender()
                                            .getType())
                        .withClazz(item.getClazz()
                                           .getName())
                        .withSpecialization(item.getSpecialization()
                                                    .getName())
                        .build())
                .toList();
    }

    private CharacterMediaResponse getCharacterMedia(BattleNetMedia media) {
        CharacterMediaResponse.Builder builder = CharacterMediaResponse.newBuilder();
        if (media == null) {
            return builder.build();
        }
        return builder.withAvatar(media.getAssetWithKey(BlizzardAssetKey.AVATAR))
                .withInset(media.getAssetWithKey(BlizzardAssetKey.INSET))
                .withMainRaw(media.getAssetWithKey(BlizzardAssetKey.MAIN_RAW))
                .build();
    }

    private boolean isAccessTokenValid(String header) {
        String token = JwtUtils.getTokenFromAuthHeader(header);
        if (token == null) {
            return false;
        }

        return myJwtSecret.isPresent() && JwtUtils.getIdIfValid(token, myJwtSecret.get()) != null;
    }
}
