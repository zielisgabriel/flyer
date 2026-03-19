package br.com.zls.flyer.infra.security;

import br.com.zls.flyer.domain.repositories.UserRepository;
import br.com.zls.flyer.infra.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user by username: {}", username);
        UserEntity userEntity = userRepository.findByUsername(username)
            .or(() -> userRepository.findByEmail(username))
            .orElseThrow(() -> {
                log.warn("User not found with username/email: {}", username);
                return new UsernameNotFoundException("User not found: " + username);
            });
        log.debug("User found: {}", username);
        return new CustomUserDetails(userEntity);
    }
}
