package io.github.mockup.zenith.core.interceptor;

import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.github.mockup.zenith.web.login.dto.response.AuthResponse;
import io.github.mockup.zenith.web.login.service.AuthProxyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class AccessTokenInterceptor implements HandlerInterceptor {
	private final String SECRET_KEY = "my-super-secure-secret-key-1234567890!!";

	private final AuthProxyService authProxyService;

	public AccessTokenInterceptor(@Autowired AuthProxyService authProxyService) {
		this.authProxyService = authProxyService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);

		if (session == null) {
			response.sendRedirect("/login");
			return false;
		}

		String accessToken = (String) session.getAttribute("accessToken");
		if (accessToken == null) {
			response.sendRedirect("/login");
			return false;
		}

		try {
			// トークン有効かどうか判定
			if (!isAccessTokenValid(accessToken)) {
				// 有効期限切れ → リフレッシュを試みる
				AuthResponse newTokens = authProxyService.refreshTokenFromCookie(request);
				session.setAttribute("accessToken", newTokens.accessToken());
				// CookieはSpringがレスポンスに自動で付ける or 別途更新ロジック
			}

		} catch (Exception e) {
			// リフレッシュ失敗 → ログイン画面へ
			session.invalidate();
			response.sendRedirect("/login");
			return false;
		}

		return true;
	}

	private boolean isAccessTokenValid(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
					.build()
					.parseClaimsJws(token)
					.getBody();

			return !claims.getExpiration().before(new Date());
		} catch (JwtException e) {
			return false;
		}
	}
}