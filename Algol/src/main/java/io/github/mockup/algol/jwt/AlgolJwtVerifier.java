package io.github.mockup.algol.jwt;

import java.security.PublicKey;

import io.github.mockup.algol.exception.AlgolJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * AlgolJwtVerifier は、RSA公開鍵を用いて JWT の署名検証およびクレーム抽出を行うクラスです。
 */
public class AlgolJwtVerifier {

    /** 検証に使用する公開鍵 */
    private final PublicKey publicKey;

    /**
     * コンストラクタ
     *
     * @param publicKey トークン検証用の公開鍵（RSA）
     */
    public AlgolJwtVerifier(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * JWTトークンの署名および有効期限を検証し、トークンのクレームを返却する。
     *
     * @param token JWTトークン（Bearerトークン）
     * @return トークンに含まれるクレーム（Claims）
     * @throws AlgolJwtException 署名不正や有効期限切れなど、トークン検証に失敗した場合
     */
    public Claims verify(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new AlgolJwtException("JWTの検証に失敗しました", e);
        }
    }
}
