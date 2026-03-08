package br.com.zls.flyer.domain.valueobjects;

import java.time.LocalDate;

import br.com.zls.flyer.domain.exceptions.InvalidBirthdayDateException;

public class BirthdayDate {
  private final LocalDate value;

  public BirthdayDate(LocalDate birthdayDate) {
    this.validate(birthdayDate);
    this.value = birthdayDate;
  }

  public LocalDate getValue() {
    return this.value;
  }

  private void validate(LocalDate birthdayDate) {
    boolean isAfter = birthdayDate.isAfter(LocalDate.now().minusYears(14));
    boolean isBefore = birthdayDate.isBefore(LocalDate.now().minusYears(120));

    if (isAfter || isBefore) {
      throw new InvalidBirthdayDateException();
    }
  }
}
