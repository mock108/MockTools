package io.github.mockup.algol.jwt;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import io.jsonwebtoken.Claims;

/**
 * AlgolJwtClaimExtractor は、JWTクレーム（Claims）から特定の情報を安全に取り出すユーティリティクラスです。
 * 主に subject, roles（空白区切り形式）, 有効期限を扱います。
 */
public class AlgolJwtClaimExtractor {

    private AlgolJwtClaimExtractor() {
        // インスタンス化禁止のユーティリティクラス
    }

    /**
     * クレームから subject（サブジェクト）を取得します。
     *
     * @param claims JWTのClaimsオブジェクト
     * @return subject（トークン発行対象の識別子）
     */
    public static String getSubject(Claims claims) {
        return claims.getSubject();
    }

    /**
     * クレームから roles を空白区切りのCSV文字列として取得し、List形式に変換します。
     * <p>
     * 例: "USER ADMIN" → List.of("USER", "ADMIN")
     *
     * @param claims JWTのClaimsオブジェクト
     * @return 権限ロールのリスト
     */
    public static List<String> getRolesFromCsv(Claims claims) {
        String rolesStr = claims.get("roles", String.class);
        return Arrays.stream(rolesStr.split("\\s+"))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .toList();
    }

    /**
     * クレームからトークンの有効期限（exp）を取得します。
     *
     * @param claims JWTのClaimsオブジェクト
     * @return 有効期限のInstant
     */
    public static Instant getExpiration(Claims claims) {
        return claims.getExpiration().toInstant();
    }
}
