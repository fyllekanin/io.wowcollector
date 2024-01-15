package io.wowcollector.service.http;

import com.google.gson.Gson;
import io.wowcollector.entityview.http.wowhead.WowHeadTooltip;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class WowHeadHttpService {
    private static final Logger LOGGER = Logger.getLogger(WowHeadHttpService.class.getName());
    private static final Pattern ITEM_ID_PATTERN = Pattern.compile("/item=(\\d+)/");
    private static final Pattern SPELL_ID_PATTERN = Pattern.compile("/spell=(\\d+)/");


    public WowHeadTooltip getMountIcon(int mountId) {
        HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                          .GET(),
                                                  "https://www.wowhead.com/mount/" + mountId,
                                                  true);
        if (response != null && response.statusCode() == 301) {
            Optional<String> header = response.headers()
                    .firstValue("location");
            if (header.isPresent()) {
                Matcher matcher = null;
                if (header.get()
                        .contains("item=")) {
                    matcher = ITEM_ID_PATTERN.matcher(header.get());
                    if (matcher.find() && matcher.group(1) != null) {
                        return getWowHeadTooltip("item", matcher.group(1));
                    }
                } else if (header.get()
                        .contains("spell=")) {
                    matcher = SPELL_ID_PATTERN.matcher(header.get());
                    if (matcher.find() && matcher.group(1) != null) {
                        return getWowHeadTooltip("spell", matcher.group(1));
                    }
                }
            }
        }
        return null;
    }

    public WowHeadTooltip getWowHeadTooltip(String type, String id) {
        HttpResponse<String> response = doRequest(HttpRequest.newBuilder()
                                                          .GET(),
                                                  "https://nether.wowhead.com/tooltip/" + type + "/" + id +
                                                          "?dataEnv=1&locale=0",
                                                  true);
        if (response != null && response.statusCode() == 200) {
            return new Gson().fromJson(response.body(), WowHeadTooltip.class);
        } else {
            return null;
        }
    }

    private HttpResponse<String> doRequest(HttpRequest.Builder builder, String url, boolean retry) {
        try {
            builder.uri(new URI(url));
            return HttpClient.newBuilder()
                    .build()
                    .send(builder.build(), HttpResponse.BodyHandlers.ofString());
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
}
