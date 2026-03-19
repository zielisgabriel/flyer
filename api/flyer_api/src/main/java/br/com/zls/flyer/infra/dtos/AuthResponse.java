package br.com.zls.flyer.infra.dtos;

public record AuthResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    String username
) {
}
