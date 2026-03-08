package br.com.zls.flyer.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.zls.flyer.domain.valueobjects.BirthdayDate;
import lombok.Data;

@Data
public class Profile {
  private UUID id;
  private String authId;
  private String bio;
  private String avatarUrl;
  private String firstName;
  private BirthdayDate birthdayDate;
  private String lastName;
  private String username;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
