package io.github.mockup.zenith.web.login.service;

import java.util.Arrays;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.mockup.zenith.web.login.dto.polaris.PolarisAuthRequest;
import io.github.mockup.zenith.web.login.dto.polaris.PolarisAuthResponse;
import io.github.mockup.zenith.web.login.dto.response.AuthResponse;

@Service
public class AuthProxyService {

	@Autowired
	private RestTemplate restTemplate;
	private final String POLARIS_LOGIN_URL;
	private final String POLARIS_REFRESH_URL;

	public AuthProxyService(@Value("${polaris.url}") String polarisUrl) {
		this.POLARIS_LOGIN_URL = polarisUrl + "/auth/login";
		this.POLARIS_REFRESH_URL = polarisUrl + "/auth/refresh";
	}

	public PolarisAuthResponse login(String username, String password) {
		PolarisAuthRequest request = new PolarisAuthRequest(username, password);
		ResponseEntity<PolarisAuthResponse> polarisResponse = restTemplate.postForEntity(
				POLARIS_LOGIN_URL, request, PolarisAuthResponse.class);
		return polarisResponse.getBody();
	}

	public AuthResponse refreshTokenFromCookie(HttpServletRequest request) {
	    String refreshToken = extractRefreshTokenFromCookie(request);

	    // リクエストヘッダーに Authorization を追加
	    HttpHeaders headers = new HttpHeaders();
	    headers.setBearerAuth(refreshToken);
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Void> entity = new HttpEntity<>(headers);

		ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
				POLARIS_REFRESH_URL, entity, AuthResponse.class);

		return response.getBody();
	}

	private String extractRefreshTokenFromCookie(HttpServletRequest request) {
		return Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
				.filter(c -> "refreshToken".equals(c.getName()))
				.findFirst()
				.map(Cookie::getValue)
				.orElseThrow(() -> new RuntimeException("No refresh token cookie found"));
	}
}
