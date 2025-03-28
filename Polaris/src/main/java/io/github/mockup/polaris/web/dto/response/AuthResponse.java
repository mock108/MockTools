package io.github.mockup.polaris.web.dto.response;

/**
 * 認証応答
 */
public record AuthResponse(String accessToken, String refreshToken) {}
