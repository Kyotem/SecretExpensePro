    package han.se.fswd.tep.service;

    import han.se.fswd.tep.exceptions.InvalidTokenException;
    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.JwtException;
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

        private static final String SECRET = "GuPXPT^-TK.wuSyv4+OX\"j<~=ealJPVpQUTV5t037^Wg92m~e`"; // JWT Secret
        private static final long EXPIRATION_DURATION = 120; // Expiry time in minutes

        // Token blacklist (in-memory so it is wiped on each restart)
        private final Set<String> tokenBlacklist = new ConcurrentSkipListSet<>();

        // Blacklists a token
        public void blacklistToken(String token) {
            tokenBlacklist.add(token); // Add token to the blacklist
        }

        // Generate the signing key from the secret
        private static SecretKey getSigningKey() {
            return Keys.hmacShaKeyFor(SECRET.getBytes());
        }

        // Generates a JWT with an expiration time for a specific user ID.
        public String generateToken(int userID) {
            Instant now = Instant.now();

            return Jwts.builder()
                    .subject(String.valueOf(userID))
                    .issuedAt(Date.from(now))
                    .expiration(Date.from(now.plus(EXPIRATION_DURATION, ChronoUnit.MINUTES))) // Token expires in 5 minutes
                    .signWith(getSigningKey())
                    .compact();
        }

        // Verifies and parses JWT claims.
        public Claims verifyAndGetClaims(String token) {
            try {
                return Jwts.parser()
                        .verifyWith(getSigningKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
            } catch (JwtException e) {
                throw new InvalidTokenException("Invalid token", e);
            }
        }

        // Checks if the token is valid, returns false if not valid.
        public void validateToken(String token) {
            Claims claims = verifyAndGetClaims(token);

            // Checks if token is blacklisted
            if(isTokenBlacklisted(token)) {
                throw new InvalidTokenException("Token is Blacklsited");
            }

            // Check if token is expired
            if(isTokenExpired(claims)) {
                throw new InvalidTokenException("Token is Expired");
            }
        }

        // Checks if a token is blacklisted
        public boolean isTokenBlacklisted(String token) {
            return tokenBlacklist.contains(token);
        }

        // Checks if a token is expired
        private boolean isTokenExpired(Claims claims) {
            Instant expiration = claims.getExpiration().toInstant();
            return expiration.isBefore(Instant.now());
        }
    }
