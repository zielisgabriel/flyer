package br.com.zls.flyer.domain.repositories;

import br.com.zls.flyer.infra.entities.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(UUID id);
    UserEntity save(UserEntity user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
