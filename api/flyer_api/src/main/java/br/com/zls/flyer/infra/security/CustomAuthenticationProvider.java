package br.com.zls.flyer.infra.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        log.debug("Authenticating user: {}", username);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!userDetails.isEnabled()) {
            log.warn("Account disabled for user: {}", username);
            throw new DisabledException("Account is disabled for user: " + username);
        }

        if (!userDetails.isAccountNonLocked()) {
            log.warn("Account locked for user: {}", username);
            throw new LockedException("Account is locked for user: " + username);
        }

        if (!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
            log.warn("Invalid credentials for user: {}", username);
            throw new BadCredentialsException("Invalid credentials for user: " + username);
        }

        log.info("User authenticated successfully: {}", username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
