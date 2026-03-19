package br.com.zls.flyer.infra.controllers;

import br.com.zls.flyer.domain.exceptions.JwtAuthException;
import br.com.zls.flyer.infra.dtos.AuthResponse;
import br.com.zls.flyer.infra.dtos.RefreshTokenRequest;
import br.com.zls.flyer.infra.security.CustomUserDetails;
import br.com.zls.flyer.infra.security.CustomUserDetailsService;
import br.com.zls.flyer.infra.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    /**
     * POST /auth/login is handled by CustomAuthenticationFilter.
     * This endpoint documents the expected request/response format.
     */
    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> loginInfo() {
        return ResponseEntity.ok(Map.of(
            "message", "Send a POST request to /auth/login with username and password"
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new JwtAuthException("Invalid or expired refresh token");
        }

        if (!jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new JwtAuthException("Provided token is not a refresh token");
        }

        String username = jwtTokenProvider.extractUsername(refreshToken);
        log.info("Refreshing token for user: {}", username);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String newAccessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(newAccessToken, newRefreshToken, "Bearer", username));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            log.info("User logged out: {}", userDetails.getUsername());
        }
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
