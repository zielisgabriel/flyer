package br.com.zls.flyer.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.zls.flyer.domain.entities.Profile;
import br.com.zls.flyer.domain.exceptions.InvalidBirthdayDateException;
import br.com.zls.flyer.domain.repositories.ProfileRepository;
import br.com.zls.flyer.domain.valueobjects.BirthdayDate;

@ExtendWith(MockitoExtension.class)
public class FindProfileUseCaseTest {

  @Mock
  private ProfileRepository profileRepository;

  @InjectMocks
  private FindProfileUseCase findProfileUseCase;

  private UUID profileId;
  private Profile profile;

  @BeforeEach
  void setUp() {
    profileId = UUID.randomUUID();

    profile = new Profile();
    profile.setId(profileId);
    profile.setAuthId("auth-123");
    profile.setFirstName("Gabriel");
    profile.setLastName("Silva");
    profile.setUsername("gabriel");
    profile.setBio("Hello world");
    profile.setAvatarUrl("https://example.com/avatar.png");
    profile.setBirthdayDate(new BirthdayDate(LocalDate.now().minusYears(20)));
    profile.setCreatedAt(LocalDateTime.now());
    profile.setUpdatedAt(LocalDateTime.now());
  }

  @Test
  @DisplayName("Must return a profile when found")
  public void mustReturnAProfileWhenFound() {
    when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));

    Profile result = findProfileUseCase.complete(profileId);

    assertNotNull(result);
    assertEquals(profileId, result.getId());
    assertEquals("Gabriel", result.getFirstName());
    assertEquals("gabriel", result.getUsername());
    verify(profileRepository, times(1)).findById(profileId);
  }

  @Test
  @DisplayName("Must throw an exception when profile is not found")
  public void mustThrowWhenProfileNotFound() {
    when(profileRepository.findById(profileId)).thenReturn(Optional.empty());

    assertThrows(NoSuchElementException.class, () -> findProfileUseCase.complete(profileId));
    verify(profileRepository, times(1)).findById(profileId);
  }

  @Test
  @DisplayName("Must throw an InvalidBirthdayDateException when profile has a birthday date minus than 14 years")
  public void mustThrowAnInvalidBirthdayDateExceptionWhenProfileHasABirthdayDateMinusThan14Years() {
    assertThrows(InvalidBirthdayDateException.class, () -> profile.setBirthdayDate(new BirthdayDate(LocalDate.now().minusYears(13))));
  }

  @Test
  @DisplayName("Must throw an InvalidBirthdayDateException when profile has a birthday date plus than 120 years")
  public void mustThrowAnInvalidBirthdayDateExceptionWhenProfileHasABirthdayDatePlusThan120Years() {
    assertThrows(InvalidBirthdayDateException.class, () -> profile.setBirthdayDate(new BirthdayDate(LocalDate.now().minusYears(121))));
  }
}
