package br.com.zls.flyer.infra.security;

import br.com.zls.flyer.infra.entities.PermissionEntity;
import br.com.zls.flyer.infra.entities.RoleEntity;
import br.com.zls.flyer.infra.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    public UUID getId() {
        return userEntity.getId();
    }

    public String getEmail() {
        return userEntity.getEmail();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (RoleEntity role : userEntity.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            for (PermissionEntity permission : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return userEntity.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userEntity.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userEntity.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return userEntity.isEnabled();
    }
}
