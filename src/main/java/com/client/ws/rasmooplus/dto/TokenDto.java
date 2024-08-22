package com.client.ws.rasmooplus.dto;

public record TokenDto(String token, String type, Long expiration) {
}
