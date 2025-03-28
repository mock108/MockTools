package io.github.mockup.polaris.web.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.github.mockup.polaris.core.database.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private final String SECRET_KEY = "my-super-secure-secret-key-1234567890!!";

	/**
	 * JWT生成
	 * @param user ユーザEntity情報
	 * @return JWT文字列
	 */
	public String generateToken(User user) {
		return Jwts.builder()
				.setIssuer("Mockup Polaris")
				.setSubject(user.getUsername())
				.claim("role", user.getRole())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1時間
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * JWT(RefreshToekn)生成
	 * @param user ユーザEntity情報
	 * @return JWT文字列
	 */
	public String generateRefreshToken(User user) {
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7日間
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * 有効期限チェック
	 * @param token JWT文字列
	 * @return 期限切れの場合 False
	 */
	public boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}

	/**
	 * JWTからUsernameを抽出
	 * @param token JWT文字列
	 * @return Username
	 */
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	/**
	 * JWT解析
	 * @param token JWT文字列
	 * @return 解析結果
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
