package br.com.zls.flyer.infra.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zls.flyer.application.usecases.FindProfileUseCase;
import br.com.zls.flyer.domain.entities.Profile;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/profile")
@RequiredArgsConstructor
public class FindProfileController {
  private final FindProfileUseCase findProfileUseCase;

  @GetMapping(path = "/{id}")
  public Profile complete(@PathParam("id") UUID id) {
    return this.findProfileUseCase.complete(id);
  }
}
