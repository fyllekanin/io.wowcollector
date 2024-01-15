package io.wowcollector.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Calendar;
import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

public class JwtUtilsTest {
    private static final String SECRET = "9g79qz2OhPQcY6tzg08lTbz0URdStuKGDVoEhoA/hOsSg3Rtgyjmbk/HrmozEscMwrQesGv3hn4TN0AMYD4KIg==";

    @Test
    public void shouldReturnNullIfNullHeader() {
        // Given
        String header = null;

        // When
        String token = JwtUtils.getTokenFromAuthHeader(header);

        // Then
        assertNull(token);
    }

    @Test
    public void shouldReturnNullIfHeaderIsEmpty() {
        // Given
        String header = "";

        // When
        String token = JwtUtils.getTokenFromAuthHeader(header);

        // Then
        assertNull(token);
    }

    @Test
    public void shouldReturnNullIfHeaderDoNotContainBearer() {
        // Given
        String header = "thisisthetoken";

        // When
        String token = JwtUtils.getTokenFromAuthHeader(header);

        // Then
        assertNull(token);
    }

    @Test
    public void shouldReturnToken() {
        // Given
        String header = "Bearer thisisthetoken";

        // When
        String token = JwtUtils.getTokenFromAuthHeader(header);

        // Then
        assertEquals(token, "thisisthetoken");
    }

    @Test
    public void shouldReturnNullIfSignedClaimsIsNull() {
        // Given
        String signedClaims = null;

        // When
        ObjectId objectId = JwtUtils.getIdIfValid(signedClaims, SECRET);

        // Then
        assertNull(objectId);
    }

    @Test
    public void shouldReturnNullIfExpired() {
        // Given
        ObjectId objectId = ObjectId.get();
        String signedClaims = JwtUtils.getToken(objectId, getExpirationDate(-5), SECRET);

        // When
        ObjectId result = JwtUtils.getIdIfValid(signedClaims, SECRET);

        // Then
        assertNull(result);
    }

    @Test
    public void shouldReturnObjectIdIfValid() {
        // Given
        ObjectId objectId = ObjectId.get();
        String signedClaims = JwtUtils.getToken(objectId, getExpirationDate(5), SECRET);

        // When
        ObjectId result = JwtUtils.getIdIfValid(signedClaims, SECRET);

        // Then
        assertEquals(objectId, result);
    }

    private Date getExpirationDate(int seconds) {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
}
