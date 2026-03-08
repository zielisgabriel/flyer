package br.com.zls.flyer.infra.adapters;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zls.flyer.infra.entities.ProfileEntity;

public interface JpaProfileRepositoryImpl extends JpaRepository<ProfileEntity, UUID> {
  
}
