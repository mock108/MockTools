package io.github.mockup.polaris.web.dto.request;

/**
 * 登録要求
 */
public record RegisterRequest(String username, String password, String role) {
}
