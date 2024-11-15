package han.se.fswd.tep.service;

import han.se.fswd.tep.exceptions.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class JwtUtil {

    private static final String SECRET = "GuPXPT^-TK.wuSyv4+OX\"j<~=ealJPVpQUTV5t037^Wg92m~e`";
    private static final long EXPIRATION_DURATION = 120; // Expiration time in Minutes
    private final Set<String> tokenBlacklist = new ConcurrentSkipListSet<>();

    // Generate signing key
    private static SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generate a token for a user ID
    public String generateToken(int userID) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(String.valueOf(userID))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(EXPIRATION_DURATION, ChronoUnit.MINUTES)))
                .signWith(getSigningKey())
                .compact();
    }

    // Blacklist a token
    public void blacklistToken(String token) {
        tokenBlacklist.add(token);
    }

    // Check if a token is blacklisted
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }

    // Validate a token, throw exception if invalid
    public boolean validateToken(String token) {
        Claims claims = verifyAndGetClaims(token);

        if (isTokenBlacklisted(token)) {
            throw new InvalidTokenException("Token is blacklisted");
        }

        if (isTokenExpired(claims)) {
            throw new InvalidTokenException("Token is expired");
        }
        return true;
    }

    // Parse and verify claims
    public Claims verifyAndGetClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token", e);
        }
    }

    // Check if token is expired
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().toInstant().isBefore(Instant.now());
    }

    public String getUserIdfromToken(String token) {
        return verifyAndGetClaims(token).getSubject();
    }

}
