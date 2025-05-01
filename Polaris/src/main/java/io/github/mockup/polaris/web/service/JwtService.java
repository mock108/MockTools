package io.github.mockup.polaris.web.service;

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mockup.algol.jwt.AlgolJwtIssuer;
import io.github.mockup.algol.jwt.AlgolJwtVerifier;
import io.github.mockup.algol.jwt.JwtIssueData;
import io.github.mockup.polaris.core.database.entity.User;
import io.jsonwebtoken.Claims;

@Service
public class JwtService {
	/** JWT発行 */
	@Autowired
	private AlgolJwtIssuer algolJwtIssuer;
	/** JWT復号 */
	@Autowired
	private AlgolJwtVerifier algolJwtVerifier;

	/**
	 * JWT生成
	 * @param user ユーザEntity情報
	 * @return JWT文字列
	 */
	public String generateToken(User user) {
		JwtIssueData jwtData = new JwtIssueData();
		jwtData.setSubject(user.getUsername());
		jwtData.setRolesCsv(user.getRole());
		jwtData.setExpiration(Duration.ofHours(5));
		return algolJwtIssuer.issue(jwtData);
	}

	/**
	 * JWT(RefreshToekn)生成
	 * @param user ユーザEntity情報
	 * @return JWT文字列
	 */
	public String generateRefreshToken(User user) {
		JwtIssueData jwtData = new JwtIssueData();
		jwtData.setSubject(user.getUsername());
		jwtData.setExpiration(Duration.ofDays(7));
		return algolJwtIssuer.issue(jwtData);
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
		return algolJwtVerifier.verifyAndExtractClaims(token);
	}
}
