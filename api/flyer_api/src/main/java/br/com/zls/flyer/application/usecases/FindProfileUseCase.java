package br.com.zls.flyer.application.usecases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.zls.flyer.domain.entities.Profile;
import br.com.zls.flyer.domain.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindProfileUseCase {
  private final ProfileRepository profileRepository;

  public Profile complete(UUID id) {
    Optional<Profile> profile = this.profileRepository.findById(id);
    
    return profile.orElseThrow();
  }
}
