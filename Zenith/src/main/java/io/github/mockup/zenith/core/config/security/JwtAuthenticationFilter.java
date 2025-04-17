package io.github.mockup.zenith.core.config.security;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final String secretKey = "my-super-secure-secret-key-1234567890!!";

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7); // "Bearer " 除去

		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
					.build()
					.parseClaimsJws(token)
					.getBody();

			String username = claims.getSubject();
			String role = (String) claims.get("role");

			List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
					authorities);

			SecurityContextHolder.getContext().setAuthentication(authToken);

		} catch (JwtException e) {
			// 無効なトークンは無視してフィルタを通す（未認証扱い）
		}

		filterChain.doFilter(request, response);
	}
}