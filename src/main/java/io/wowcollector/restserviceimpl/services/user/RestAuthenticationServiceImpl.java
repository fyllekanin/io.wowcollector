package io.wowcollector.restserviceimpl.services.user;

import com.google.gson.Gson;
import io.wowcollector.common.data.BlizzardAssetKey;
import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.common.util.JwtUtils;
import io.wowcollector.entityview.http.battlenet.BattleNetMedia;
import io.wowcollector.entityview.http.battlenet.BattleNetOauth;
import io.wowcollector.entityview.http.battlenet.BattleNetUserInfo;
import io.wowcollector.entityview.repository.user.UserCharacter;
import io.wowcollector.entityview.repository.user.UserDocument;
import io.wowcollector.entityview.repository.user.UserSettings;
import io.wowcollector.entityview.response.character.CharacterMediaResponse;
import io.wowcollector.entityview.response.character.CharacterResponse;
import io.wowcollector.entityview.response.user.AuthorizationResponse;
import io.wowcollector.repository.RepositoryFactory;
import io.wowcollector.repository.repositories.user.UserRepository;
import io.wowcollector.restservice.user.RestAuthenticationService;
import io.wowcollector.restserviceimpl.services.user.mapper.WoWProfileMapper;
import io.wowcollector.service.http.BattleNetHttpService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RestAuthenticationServiceImpl implements RestAuthenticationService {
    private static final Logger LOGGER = Logger.getLogger(RestAuthenticationServiceImpl.class.getName());
    private static final String AUTHORIZE_URL = "https://oauth.battle.net/oauth/authorize";
    private static final String SCOPE = "wow.profile";
    private static final String RESPONSE_TYPE = "code";

    @Inject
    private BattleNetHttpService myBattleNetHttpService;
    @Inject
    private RepositoryFactory myRepositoryFactory;

    @ConfigProperty(name = "BATTLE_NET_CLIENT_ID")
    private String myClientId;
    @ConfigProperty(name = "APPLICATION_URL")
    private Optional<String> myRedirectUri;
    @ConfigProperty(name = "JWT_SECRET")
    private Optional<String> myJwtSecret;
    @ConfigProperty(name = "LOWEST_CHARACTER_LEVEL", defaultValue = "60")
    private String myLowestCharacterLevel;

    @Override
    public Response redirect() {
        if (myRedirectUri.isEmpty()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("APPLICATION_URL environment missing")
                    .build();
        }
        String url = String.format(AUTHORIZE_URL + "?client_id=%s&scope=%s" +
                                           "&redirect_uri=%s&response_type=%s&state=%s",
                                   myClientId,
                                   SCOPE,
                                   (myRedirectUri.get() + "/login"),
                                   RESPONSE_TYPE,
                                   UUID.randomUUID());
        try {
            return Response
                    .temporaryRedirect(new URI(url))
                    .build();
        } catch (URISyntaxException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public Response oauth(String code) {
        if (myRedirectUri.isEmpty()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("APPLICATION_URL environment missing")
                    .build();
        }

        if (myJwtSecret.isEmpty()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("JWT_SECRET environment missing")
                    .build();
        }

        try {
            if (code == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("code query parameter missing")
                        .build();
            }
            BattleNetOauth oauth = myBattleNetHttpService.getBattleNetOauth(
                    (myRedirectUri.get() + "/login"),
                    SCOPE,
                    code);
            BattleNetUserInfo userInfo = myBattleNetHttpService
                    .getBattleNetUserInfo(oauth.getAccessToken());

            if (!myRepositoryFactory.of(UserRepository.class)
                    .isUserPresent(userInfo.getBattleTag())) {
                initializeUser(oauth, userInfo);
            } else {
                updateUser(oauth, userInfo);
            }

            UserDocument user = myRepositoryFactory.of(UserRepository.class)
                    .getByBattleTag(userInfo.getBattleTag());

            AuthorizationResponse response = getAuthResponse(user, myJwtSecret.get());
            return Response.status(200)
                    .entity(new Gson().toJson(response))
                    .build();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    public Response getRefresh(String refreshToken) {
        if (myJwtSecret.isEmpty()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("JWT_SECRET environment missing")
                    .build();
        }

        try {
            ObjectId objectId = JwtUtils.getIdIfValid(refreshToken, myJwtSecret.get());
            if (objectId != null) {
                UserDocument user = myRepositoryFactory.of(UserRepository.class)
                        .getByObjectId(objectId);

                return Response.status(Response.Status.OK)
                        .entity(getAuthResponse(user, myJwtSecret.get()))
                        .build();
            }

            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    public Response getProtected() {
        return Response.status(200)
                .entity("{\"key\": 200 }")
                .build();
    }

    private void updateUser(BattleNetOauth oauth, BattleNetUserInfo userInfo) {
        WoWProfileMapper mapper = new WoWProfileMapper(myBattleNetHttpService,
                                                       Integer.parseInt(myLowestCharacterLevel));
        List<UserCharacter> characters = Arrays.stream(BlizzardRegion.values())
                .parallel()
                .flatMap(item -> mapper.getCharacters(item, oauth)
                        .stream())
                .collect(Collectors.toList());

        UserDocument original = myRepositoryFactory.of(UserRepository.class)
                .getByBattleTag(userInfo.getBattleTag());
        UserDocument updated = original.newBuilderFromCurrent()
                .withCharacters(characters)
                .build();

        myRepositoryFactory.of(UserRepository.class)
                .update(updated);
    }

    private void initializeUser(BattleNetOauth oauth, BattleNetUserInfo userInfo) {
        WoWProfileMapper mapper = new WoWProfileMapper(myBattleNetHttpService,
                                                       Integer.parseInt(myLowestCharacterLevel));
        List<UserCharacter> characters = Arrays.stream(BlizzardRegion.values())
                .parallel()
                .flatMap(item -> mapper.getCharacters(item, oauth)
                        .stream())
                .collect(Collectors.toList());

        UserDocument document = UserDocument.newBuilder()
                .withBattleTag(userInfo.getBattleTag())
                .withSettings(UserSettings.newBuilder()
                                      .withDefaultCharacterId(characters.isEmpty() ? 0
                                                                      : Collections.max(characters,
                                                                                        Comparator.comparing(UserCharacter::getLevel))
                                              .getId())
                                      .build())
                .withCharacters(characters)
                .build();
        myRepositoryFactory.of(UserRepository.class)
                .create(document);
    }

    private AuthorizationResponse getAuthResponse(UserDocument user, String jtwSecret) {
        CharacterResponse character = user.getCharacters()
                .stream()
                .filter(item -> item.getId() == user.getSettings()
                        .getDefaultCharacterId())
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
                .findFirst()
                .orElse(null);

        return AuthorizationResponse.newBuilder()
                .withAccessToken(JwtUtils.getToken(user.getObjectId(), getDateWithDayForward(1),
                                                   jtwSecret))
                .withRefreshToken(JwtUtils.getToken(user.getObjectId(), getDateWithDayForward(5),
                                                    jtwSecret))
                .withBattleTag(user.getBattleTag())
                .withDefaultCharacter(character)
                .build();
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

    private Date getDateWithDayForward(int day) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }
}
