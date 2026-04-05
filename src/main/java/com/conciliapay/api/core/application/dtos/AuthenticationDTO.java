package com.conciliapay.api.core.application.dtos;

public record AuthenticationDTO(
        String email,
        String password
) {
}
