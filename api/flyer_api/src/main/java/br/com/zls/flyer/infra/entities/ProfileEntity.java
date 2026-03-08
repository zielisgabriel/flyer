package br.com.zls.flyer.infra.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.zls.flyer.domain.entities.Profile;
import br.com.zls.flyer.domain.exceptions.InvalidBirthdayDateException;
import br.com.zls.flyer.domain.valueobjects.BirthdayDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "profiles")
public class ProfileEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "auth_id", nullable = false)
  private String authId;

  @Column(name = "bio", length = 255, nullable = true)
  private String bio;

  @Column(name = "avatar_url", nullable = true)
  private String avatarUrl;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "birthday_date", nullable = false)
  private LocalDate birthdayDate;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @PrePersist
  private void createProfile() {
    this.createdAt = LocalDateTime.now();
  }

  @PreUpdate
  private void updateProfile() {
    this.updatedAt = LocalDateTime.now();
  }

  public Profile toDomain() {
    Profile profile = new Profile();

    profile.setId(id);
    profile.setAuthId(authId);
    profile.setAvatarUrl(avatarUrl);
    profile.setBio(bio);
    profile.setBirthdayDate(new BirthdayDate(birthdayDate));
    profile.setCreatedAt(createdAt);
    profile.setUpdatedAt(updatedAt);
    profile.setFirstName(firstName);
    profile.setLastName(lastName);
    profile.setUsername(username);

    return profile;
  }
}
