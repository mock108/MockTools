package io.github.mockup.algol.jwt;

import java.security.PrivateKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * AlgolJwtIssuer は、RSA秘密鍵を使用して JWT を生成するクラスです。
 * トークンの subject（発行対象）および roles（権限）をクレームとして付与し、署名付きトークンを返します。
 */
public class AlgolJwtIssuer {

	/** 秘密鍵（RS256署名用） */
	private final PrivateKey privateKey;

	/** トークンの有効期限（Duration） */
	private final Duration expiration;

	/**
	 * コンストラクタ
	 *
	 * @param privateKey 署名に使用する秘密鍵（RSA）
	 * @param expiration トークンの有効期間
	 */
	public AlgolJwtIssuer(PrivateKey privateKey, Duration expiration) {
		this.privateKey = privateKey;
		this.expiration = expiration;
	}

	/**
	 * JWTトークンを発行する。
	 *
	 * @param subject トークンの subject（ユーザー識別子など）
	 * @param rolesCsv 権限ロールの空白区切り文字列（例: "USER ADMIN"）
	 * @return 署名済みJWTトークン（Base64文字列）
	 */
	public String issue(String subject, String rolesCsv) {
		Instant now = Instant.now();
		return Jwts.builder()
				.setSubject(subject)
				.claim("roles", rolesCsv)
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(expiration)))
				.signWith(privateKey, SignatureAlgorithm.RS256)
				.compact();
	}
}
