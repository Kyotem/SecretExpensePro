package han.se.fswd.tep.security;

import han.se.fswd.tep.exceptions.InvalidTokenException;
import han.se.fswd.tep.module.User;
import han.se.fswd.tep.dao.UserDaoImpl;
import han.se.fswd.tep.service.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDaoImpl userDaoImpl;

    @Autowired
    public JWTAuthenticationFilter(JwtUtil jwtUtil, UserDaoImpl userDaoImpl) {
        this.jwtUtil = jwtUtil;
        this.userDaoImpl = userDaoImpl;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = getJWTFromRequest(request);
        String requestURI = request.getRequestURI();
        try {
            if (token != null && jwtUtil.validateToken(token)) {
                if ("/login".equals(requestURI)) {
                    // NOT USING EXCEPTIONS DUE TO THE FACT THAT FILTERS RUN OUTSIDE THE NORMAL SCOPE
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("You are already authenticated");
                    return;
                } else {
                    authenticateUserFromToken(token, request);
                }
            }
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException ex) {
            // NOT USING EXCEPTIONS DUE TO THE FACT THAT FILTERS RUN OUTSIDE THE NORMAL SCOPE
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(ex.getMessage());
        }
    }

    private void authenticateUserFromToken(String token, HttpServletRequest request) {

        int userID = Integer.parseInt(jwtUtil.getUserIdfromToken(token));
        User user = userDaoImpl.findById(userID);

        if (user != null) {
            // Create authentication token without authorities
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, null);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    // Retrieves the JWT from the request and returns as a String.
    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
