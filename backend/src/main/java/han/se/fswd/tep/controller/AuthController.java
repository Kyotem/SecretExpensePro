package han.se.fswd.tep.controller;

import han.se.fswd.tep.exceptions.EmptyLoginRequestException;
import han.se.fswd.tep.module.LoginRequest;
import han.se.fswd.tep.module.LoginResponse;
import han.se.fswd.tep.service.UserService;
import han.se.fswd.tep.service.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            throw new EmptyLoginRequestException("Something went wrong processing your rqeuest");
        }

        // Auth the user and retrieve JWT based on UserID
        String token = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

        // Create the response with the JWT token
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        // Get the token from the request
        String token = getJWTFromRequest(request);

        // Blacklist the token
        jwtUtil.blacklistToken(token);

        // Clear the security context to log the user out
        SecurityContextHolder.clearContext();

        // Better to use a DTO Object for the response FIXME
        return ResponseEntity.ok(Map.of("message", "User successfully logged out"));
    }


    // Utility method to extract JWT from the request
    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
