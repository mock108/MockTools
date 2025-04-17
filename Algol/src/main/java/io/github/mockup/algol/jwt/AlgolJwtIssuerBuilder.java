package io.github.mockup.algol.jwt;

import java.nio.file.Path;
import java.security.PrivateKey;
import java.time.Duration;

import io.github.mockup.algol.exception.AlgolJwtException;
import io.github.mockup.algol.util.AlgolKeyLoaderUtil;

/**
 * AlgolJwtIssuer の構築用ビルダークラス。
 * JWTトークンの発行に使用する秘密鍵と有効期限を指定して、Issuerインスタンスを生成する。
 */
public class AlgolJwtIssuerBuilder {

	/** 秘密鍵ファイルパス */
	private String privateKeyPath;

	/** トークンの有効期限（デフォルト: 15分） */
	private Duration expiration = Duration.ofMinutes(15);

	/** デフォルトの秘密鍵ファイルパス（ホームディレクトリ以下） */
	private static final String DEFAULT_PRIVATE_KEY_PATH = "/.algol/secret/private.pem";

	/**
	 * Builderインスタンスを生成する。
	 *
	 * @return AlgolJwtIssuerBuilder
	 */
	public static AlgolJwtIssuerBuilder builder() {
		return new AlgolJwtIssuerBuilder();
	}

	/**
	 * デフォルトの秘密鍵ファイルパスを使用して AlgolJwtIssuer を構築する。
	 * <p>
	 * ホームディレクトリ直下の {@code /.algol/secret/private.pem} を使用し、
	 * 有効期限はデフォルト（15分）に設定される。
	 *
	 * @return AlgolJwtIssuer インスタンス
	 * @throws AlgolJwtException 秘密鍵の読み込みに失敗した場合
	 */
	public static AlgolJwtIssuer defaultAlgolJwtIssuer() {
		return builder()
				.key(Path.of(System.getProperty("user.home"), DEFAULT_PRIVATE_KEY_PATH).toString())
				.build();
	}

	/**
	 * 秘密鍵ファイルのパスを設定する。
	 *
	 * @param keyPath 秘密鍵ファイルの絶対パス
	 * @return AlgolJwtIssuerBuilder（チェーン可能）
	 */
	public AlgolJwtIssuerBuilder key(String keyPath) {
		this.privateKeyPath = keyPath;
		return this;
	}

	/**
	 * JWTの有効期限を設定する。
	 *
	 * @param expiration 有効期限（例: {@code Duration.ofMinutes(30)})
	 * @return AlgolJwtIssuerBuilder（チェーン可能）
	 */
	public AlgolJwtIssuerBuilder expiration(Duration expiration) {
		this.expiration = expiration;
		return this;
	}

	/**
	 * AlgolJwtIssuer インスタンスを構築する。
	 *
	 * @return AlgolJwtIssuer
	 * @throws AlgolJwtException 秘密鍵パスが未設定、または鍵読み込みに失敗した場合
	 */
	public AlgolJwtIssuer build() {
		if (privateKeyPath == null) {
			throw new AlgolJwtException("JWT秘密鍵パスが設定されていません。");
		}
		try {
			PrivateKey privateKey = AlgolKeyLoaderUtil.loadPrivateKeyFromPem(Path.of(privateKeyPath));
			return new AlgolJwtIssuer(privateKey, expiration);
		} catch (Exception e) {
			throw new AlgolJwtException("JWT秘密鍵の読み込みに失敗しました", e);
		}
	}
}
