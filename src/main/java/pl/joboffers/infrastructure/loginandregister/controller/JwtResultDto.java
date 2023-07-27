package pl.joboffers.infrastructure.loginandregister.controller;

import lombok.Builder;

@Builder
public record JwtResultDto(String username, String token) {
}
