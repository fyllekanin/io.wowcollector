package io.wowcollector.service.http;

import com.google.common.util.concurrent.RateLimiter;
import com.google.gson.Gson;
import io.quarkus.cache.CacheResult;
import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacter;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterAchievements;
import io.wowcollector.entityview.http.battlenet.BattleNetMedia;
import io.wowcollector.entityview.http.battlenet.BattleNetMount;
import io.wowcollector.entityview.http.battlenet.BattleNetOauth;
import io.wowcollector.entityview.http.battlenet.BattleNetProfileWow;
import io.wowcollector.entityview.http.battlenet.BattleNetToken;
import io.wowcollector.entityview.http.battlenet.BattleNetUserInfo;
import io.wowcollector.entityview.http.battlenet.BattleNetUserMountCollection;
import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievement;
import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievementCategoryIndex;
import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievementIndex;
import io.wowcollector.entityview.http.battlenet.mount.BattleNetMountsIndex;
import io.wowcollector.entityview.http.battlenet.realm.BattleNetRealms;
import io.wowcollector.entityview.http.battlenet.toy.BattleNetToy;
import io.wowcollector.entityview.http.battlenet.toy.BattleNetToysIndex;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.logging.Logger;

@Singleton
public class BattleNetHttpService {
    private static final Logger LOGGER = Logger.getLogger(BattleNetHttpService.class.getName());
    private final RateLimiter myRateLimiter = RateLimiter.create(90);
    private final String myClientId;
    private final String myClientSecret;
    private volatile BattleNetToken myToken;

    public BattleNetHttpService(@ConfigProperty(name = "BATTLE_NET_CLIENT_ID") String clientId,
                                @ConfigProperty(name = "BATTLE_NET_CLIENT_SECRET") String clientSecret) {
        myClientId = clientId;
        myClientSecret = clientSecret;
    }

    public BattleNetCharacterAchievements getCharacterAchievements(BlizzardRegion region, String realm,
                                                                   String character) {
        HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                          .GET(),
                                                  "https://" + region + ".api.blizzard" +
                                                          ".com/profile/wow/character/" + realm + "/" + character.toLowerCase()
                                                          + "/achievements"
                                                          + "?namespace=profile-" + region
                                                          + "&locale=en_US",
                                                  true);

        if (response != null && response.statusCode() == 200) {
            return new Gson().fromJson(response.body(), BattleNetCharacterAchievements.class);
        } else {
            return null;
        }
    }

    public BattleNetMedia getAchievementMedia(BlizzardRegion region, long id) {
        HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                          .GET(),
                                                  "https://" + region + ".api.blizzard" +
                                                          ".com/data/wow/media/achievement/" + id + "?namespace" +
                                                          "=static" +
                                                          "-" + region +
                                                          "&locale=en_US",
                                                  true);

        if (response != null && response.statusCode() == 200) {
            return new Gson().fromJson(response.body(), BattleNetMedia.class);
        } else {
            return null;
        }
    }

    public BattleNetMedia getItemMedia(BlizzardRegion region, int id) {
        HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                          .GET(),
                                                  "https://" + region + ".api.blizzard" +
                                                          ".com/data/wow/media/item/" + id + "?namespace" +
                                                          "=static" +
                                                          "-" + region +
                                                          "&locale=en_US",
                                                  true);

        if (response != null && response.statusCode() == 200) {
            return new Gson().fromJson(response.body(), BattleNetMedia.class);
        } else {
            return null;
        }
    }

    public BattleNetAchievementCategoryIndex getAchievementCategoryIndex(BlizzardRegion region) {
        HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                          .GET(),
                                                  "https://" + region + ".api.blizzard" +
                                                          ".com/data/wow/achievement-category/index?namespace=static" +
                                                          "-" + region +
                                                          "&locale=en_US",
                                                  true);

        if (response != null && response.statusCode() == 200) {
            return new Gson().fromJson(response.body(), BattleNetAchievementCategoryIndex.class);
        } else {
            return null;
        }
    }

    public BattleNetAchievementIndex getAchievementCategory(BlizzardRegion region, long id) {
        HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                          .GET(),
                                                  "https://" + region + ".api.blizzard" +
                                                          ".com/data/wow/achievement-category/" + id + "?namespace" +
                                                          "=static" +
                                                          "-" + region +
                                                          "&locale=en_US",
                                                  true);

        if (response != null && response.statusCode() == 200) {
            return new Gson().fromJson(response.body(), BattleNetAchievementIndex.class);
        } else {
            return null;
        }
    }

    public BattleNetAchievement getAchievement(BlizzardRegion region, long id) {
        HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                          .GET(),
                                                  "https://" + region + ".api.blizzard" +
                                                          ".com/data/wow/achievement/" + id + "?namespace" +
                                                          "=static" +
                                                          "-" + region +
                                                          "&locale=en_US",
                                                  true);

        if (response != null && response.statusCode() == 200) {
            return new Gson().fromJson(response.body(), BattleNetAchievement.class);
        } else {
            return null;
        }
    }

    public BattleNetProfileWow getWoWProfile(BlizzardRegion region, String userAccessToken) {
        try {
            HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                              .GET(),
                                                      "https://" + region.getRegion() + ".api.blizzard" +
                                                              ".com/profile/user/wow?namespace=profile-" + region.getRegion() +
                                                              "&locale" +
                                                              "=en_US",
                                                      true,
                                                      userAccessToken);

            if (response != null && response.statusCode() >= 200 && response.statusCode() < 300) {
                return new Gson().fromJson(response.body(), BattleNetProfileWow.class);
            }

            return null;
        } catch (Exception exception) {
            LOGGER.severe(String.format("Failed to fetch profile for region: %s, message: %s", region,
                                        exception.getMessage()));
            return null;
        }
    }

    public BattleNetUserInfo getBattleNetUserInfo(String userAccessToken) {
        HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                          .GET(),
                                                  "https://oauth.battle.net/oauth/userinfo?",
                                                  true,
                                                  userAccessToken);

        if (response != null && response.statusCode() >= 200 && response.statusCode() < 300) {
            return new Gson().fromJson(response.body(), BattleNetUserInfo.class);
        }

        return null;
    }

    public BattleNetOauth getBattleNetOauth(String redirectUri, String scope, String code) throws IOException,
            InterruptedException {
        String basicAuth = Base64.getEncoder()
                .encodeToString((myClientId + ":" + myClientSecret).getBytes(StandardCharsets.UTF_8));
        String requestBody = String.join("&",
                                         "redirect_uri=" + redirectUri,
                                         "scope=" + scope,
                                         "grant_type=authorization_code",
                                         "code=" + code);

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://oauth.battle.net/token"))
                .header("Authorization", "Basic " + basicAuth)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return new Gson().fromJson(response.body(), BattleNetOauth.class);
        } else {
            LOGGER.warning(String.format("Failed to fetch oauth, message: %s", response.body()));
        }

        return null;
    }

    public BattleNetMountsIndex getMounts(BlizzardRegion region) throws URISyntaxException, IOException,
            InterruptedException {
        HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                          .GET(),
                                                  "https://" + region + ".api.blizzard" +
                                                          ".com/data/wow/mount/index?namespace=static-" + region +
                                                          "&locale=en_US",
                                                  true);

        if (response != null && response.statusCode() == 200) {
            return new Gson().fromJson(response.body(), BattleNetMountsIndex.class);
        } else {
            return null;
        }
    }

    public BattleNetCharacter getCharacter(BlizzardRegion region, String realm, String character) {
        try {
            HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                              .GET(),
                                                      "https://" + region + ".api.blizzard" +
                                                              ".com/profile/wow/character/" + realm + "/" + character.toLowerCase()
                                                              + "?namespace=profile-" + region
                                                              + "&locale=en_US",
                                                      true);
            if (response != null && response.statusCode() == 200) {
                return new Gson().fromJson(response.body(), BattleNetCharacter.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Optional<BattleNetMedia> getCharacterMedia(BlizzardRegion region, String realm, String character) {
        try {
            HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                              .GET(),
                                                      "https://" + region + ".api.blizzard" +
                                                              ".com/profile/wow/character/" + realm + "/" + character.toLowerCase()
                                                              + "/character-media?namespace=profile-" + region +
                                                              "&locale=en_US",
                                                      true);
            if (response != null && response.statusCode() == 200) {
                return Optional.ofNullable(new Gson().fromJson(response.body(), BattleNetMedia.class));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public BattleNetMount getMount(BlizzardRegion region, int id) {
        try {
            HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                              .GET(),
                                                      "https://" + region + ".api.blizzard" +
                                                              ".com/data/wow/mount/" + id + "?namespace=static-" + region + "&locale=en_US",
                                                      true);

            if (response != null && response.statusCode() == 200) {
                return new Gson().fromJson(response.body(), BattleNetMount.class);
            } else {
                LOGGER.warning(String.format("Failed to fetch mount %d, statusCode: %d", id, response.statusCode()));
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @CacheResult(cacheName = "getUserMountCollection")
    public BattleNetUserMountCollection getUserMountCollection(BlizzardRegion region, String realm, String character) {
        try {
            HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                              .GET(),
                                                      "https://" + region + ".api.blizzard" +
                                                              ".com/profile/wow/character/" + realm + "/" + character.toLowerCase()
                                                              + "/collections/mounts?namespace=profile" +
                                                              "-" + region + "&locale=en_US",
                                                      true);

            if (response != null && response.statusCode() == 200) {
                return new Gson().fromJson(response.body(), BattleNetUserMountCollection.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public BattleNetRealms getRealms(BlizzardRegion region) {
        try {
            HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                              .GET(),
                                                      "https://" + region + ".api.blizzard" +
                                                              ".com/data/wow/realm/index?namespace=dynamic-" + region + "&locale=en_US",
                                                      true);

            if (response != null && response.statusCode() == 200) {
                return new Gson().fromJson(response.body(), BattleNetRealms.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public BattleNetToy getToy(BlizzardRegion region, int id) {
        try {
            HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                              .GET(),
                                                      "https://" + region + ".api.blizzard" +
                                                              ".com/data/wow/toy/" + id + "?namespace=static-" + region + "&locale=en_US",
                                                      true);

            if (response != null && response.statusCode() == 200) {
                return new Gson().fromJson(response.body(), BattleNetToy.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public BattleNetToysIndex getToys(BlizzardRegion region) {
        try {
            HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                              .GET(),
                                                      "https://" + region + ".api.blizzard" +
                                                              ".com/data/wow/toy/index?namespace=static-" + region +
                                                              "&locale=en_US",
                                                      true);

            if (response != null && response.statusCode() == 200) {
                return new Gson().fromJson(response.body(), BattleNetToysIndex.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private HttpResponse<String> doRequest(HttpRequest.Builder builder, String url, boolean retry) {
        return doRequest(builder, url, retry, null);
    }

    private HttpResponse<String> doRequest(HttpRequest.Builder builder, String url, boolean retry, String token) {
        myRateLimiter.acquire();
        try {
            String tokenToBeUsed = token != null ? token : getAccessToken();

            builder.uri(new URI(url + "&access_token=" + tokenToBeUsed));
            HttpResponse<String> result = HttpClient.newBuilder()
                    .build()
                    .send(builder.build(), HttpResponse.BodyHandlers.ofString());
            if (result.statusCode() == 401 && retry) {
                return doRequest(builder, url, false);
            }
            if (result.statusCode() == 429 && retry) {
                Thread.sleep(1000);
                return doRequest(builder, url, true);
            }
            return result;
        } catch (Exception exception) {
            if (exception instanceof URISyntaxException) {
                LOGGER.warning("Request failed because of URISyntaxException");
            } else if (exception instanceof IOException) {
                if (retry) {
                    return doRequest(builder, url, false);
                }
                LOGGER.warning("Request failed because of IOException");
                return null;
            } else if (exception instanceof InterruptedException) {
                if (retry) {
                    return doRequest(builder, url, false);
                }
                LOGGER.warning("Request failed because of InterruptedException");
                return null;
            }
            LOGGER.warning(String.format("Request failed for %s, with message: %s", url, exception.getMessage()));
            return null;
        }
    }

    private String getBasicAuthenticationHeader(String clientId, String clientSecret) {
        return "Basic " + Base64.getEncoder()
                .encodeToString((clientId + ":" + clientSecret).getBytes());
    }

    private synchronized String getAccessToken() {
        if (myToken == null || myToken.isExpired()) {
            if (myToken != null) {
                LOGGER.severe(String.format("expires in: %d", myToken.getExpiresIn()));
                LOGGER.severe(String.format("issued at: %d", myToken.getIssuedAt()));
            }
            myToken = resolveToken();
        }
        return myToken == null ? null : myToken.getAccessToken();
    }

    private BattleNetToken resolveToken() {
        LOGGER.info("Starting token resolve");
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://oauth.battle.net/token"))
                    .header("Authorization", getBasicAuthenticationHeader(myClientId, myClientSecret))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                    .build();

            HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Finished token resolve");
            if (response.statusCode() == 200) {
                return new Gson().fromJson(response.body(), BattleNetToken.class)
                        .newBuilderFromCurrent()
                        .withIssuedAt(System.currentTimeMillis())
                        .build();
            } else {
                LOGGER.severe(response.body());
                return null;
            }
        } catch (Exception exception) {
            if (exception instanceof URISyntaxException) {
                LOGGER.warning("New token failed because of URISyntaxException");
            } else if (exception instanceof IOException) {
                LOGGER.warning("New token failed because of IOException");
            } else if (exception instanceof InterruptedException) {
                LOGGER.warning("New token failed because of InterruptedException");
            }
            LOGGER.info("Failed token resolve");
        }
        return null;
    }
}
