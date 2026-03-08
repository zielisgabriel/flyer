package br.com.zls.flyer.infra.adapters;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.com.zls.flyer.domain.entities.Profile;
import br.com.zls.flyer.domain.repositories.ProfileRepository;
import br.com.zls.flyer.infra.entities.ProfileEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryAdapter implements ProfileRepository {
  private final JpaProfileRepositoryImpl repository;

  @Override
  public Optional<Profile> findById(UUID id) {
    Optional<ProfileEntity> profileOptional = this.repository.findById(id);

    return Optional.of(profileOptional.get().toDomain());
  }
}
