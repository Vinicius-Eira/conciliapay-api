package com.conciliapay.api.core.application.dtos;

public record RegisterDTO(
        String name,
        String email,
        String password
) {
}
