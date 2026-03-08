package br.com.zls.flyer.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_CONTENT)
public class InvalidBirthdayDateException extends RuntimeException {
  public InvalidBirthdayDateException(String message) {
    super(message);
  }

  public InvalidBirthdayDateException() {
    super("Data de aniversário inválida!");
  }
}
