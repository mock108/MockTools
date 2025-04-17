package io.github.mockup.algol.jwt;

import java.nio.file.Path;
import java.security.PublicKey;

import io.github.mockup.algol.exception.AlgolJwtException;
import io.github.mockup.algol.util.AlgolKeyLoaderUtil;

/**
 * AlgolJwtVerifier の構築用ビルダークラス。
 * JWTの署名検証に使用する公開鍵を指定して、Verifierインスタンスを生成する。
 */
public class AlgolJwtVerifierBuilder {

    /** 公開鍵ファイルのパス */
    private String publicKeyPath;

    /** デフォルトの公開鍵ファイルパス（ホームディレクトリ以下） */
    private static final String DEFAULT_PUBLIC_KEY_PATH = "/.algol/secret/public.pem";

    /**
     * デフォルトの公開鍵パスから AlgolJwtVerifier を構築する。
     * <p>
     * ホームディレクトリ直下の {@code /.algol/secret/public.pem} を使用。
     *
     * @return AlgolJwtVerifier インスタンス
     * @throws AlgolJwtException 公開鍵の読み込みに失敗した場合
     */
    public static AlgolJwtVerifier defaultAlgolJwtVerifier() {
        return builder()
                .key(Path.of(System.getProperty("user.home"), DEFAULT_PUBLIC_KEY_PATH).toString())
                .build();
    }

    /**
     * Builderインスタンスを生成する。
     *
     * @return AlgolJwtVerifierBuilder
     */
    public static AlgolJwtVerifierBuilder builder() {
        return new AlgolJwtVerifierBuilder();
    }

    /**
     * 公開鍵ファイルのパスを設定する。
     *
     * @param path 公開鍵ファイルの絶対パス
     * @return AlgolJwtVerifierBuilder（チェーン可能）
     */
    public AlgolJwtVerifierBuilder key(String path) {
        this.publicKeyPath = path;
        return this;
    }

    /**
     * AlgolJwtVerifier インスタンスを構築する。
     *
     * @return AlgolJwtVerifier
     * @throws AlgolJwtException 公開鍵パス未設定、または鍵読み込みに失敗した場合
     */
    public AlgolJwtVerifier build() {
        if (publicKeyPath == null) {
            throw new AlgolJwtException("JWT公開鍵パスが設定されていません。");
        }
        try {
            PublicKey publicKey = AlgolKeyLoaderUtil.loadPublicKeyFromPem(Path.of(publicKeyPath));
            return new AlgolJwtVerifier(publicKey);
        } catch (Exception e) {
            throw new AlgolJwtException("JWT公開鍵の読み込みに失敗しました", e);
        }
    }
}
