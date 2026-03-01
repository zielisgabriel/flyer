package br.com.zls.flyer.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Profile {
  private UUID id;
  private String authId;
  private String bio;
  private String avatarUrl;
  private String firstName;
  private LocalDate birthdayDate;
  private String lastName;
  private String username;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
