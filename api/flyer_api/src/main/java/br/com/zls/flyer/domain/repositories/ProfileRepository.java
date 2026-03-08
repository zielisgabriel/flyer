package br.com.zls.flyer.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import br.com.zls.flyer.domain.entities.Profile;

public interface ProfileRepository {
  Optional<Profile> findById(UUID id);
}
