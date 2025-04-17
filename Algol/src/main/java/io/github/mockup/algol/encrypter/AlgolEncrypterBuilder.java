package io.github.mockup.algol.encrypter;

import java.nio.file.Path;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.github.mockup.algol.exception.AlgolEncrypterException;
import io.github.mockup.algol.util.AlgolKeyLoaderUtil;

/**
 * AlgolEncrypter の構築用ビルダークラス。
 * AES暗号化に必要なSecretKeyおよびIVをファイルから読み込み、Encrypterを生成する。
 */
public class AlgolEncrypterBuilder {

	/** SecretKeyファイルのデフォルトパス（ホームディレクトリ以下） */
	private static final String DEFAULT_ENCRYPT_KEY = "/.algol/secret/secret.key";

	/** IVファイルのデフォルトパス（ホームディレクトリ以下） */
	private static final String DEFAULT_ENCRYPT_IV = "/.algol/secret/secret.iv";

	/** SecretKeyファイルパス */
	private String secretKeyFilePath;

	/** IVファイルパス */
	private String secretIvFilePath;

	/**
	 * デフォルトのキーおよびIVパスから Encrypter を構築する。
	 * <p>
	 * ホームディレクトリ直下の {@code /.algol/secret/secret.key} および
	 * {@code /.algol/secret/secret.iv} を使用。
	 *
	 * @return AlgolEncrypter のインスタンス
	 * @throws AlgolEncrypterException 鍵の読み込みに失敗した場合
	 */
	public static AlgolEncrypter defaultAlgolEncrypter() {
		return builder()
				.key(Path.of(System.getProperty("user.home"), DEFAULT_ENCRYPT_KEY).toString())
				.iv(Path.of(System.getProperty("user.home"), DEFAULT_ENCRYPT_IV).toString())
				.build();
	}

	/**
	 * Builderインスタンスを生成する。
	 *
	 * @return AlgolEncrypterBuilder
	 */
	public static AlgolEncrypterBuilder builder() {
		return new AlgolEncrypterBuilder();
	}

	/**
	 * 秘密鍵ファイルのパスを設定する。
	 *
	 * @param key 秘密鍵ファイルのパス
	 * @return AlgolEncrypterBuilder（チェーン可能）
	 */
	public AlgolEncrypterBuilder key(String key) {
		this.secretKeyFilePath = key;
		return this;
	}

	/**
	 * IVファイルのパスを設定する。
	 *
	 * @param iv IVファイルのパス
	 * @return AlgolEncrypterBuilder（チェーン可能）
	 */
	public AlgolEncrypterBuilder iv(String iv) {
		this.secretIvFilePath = iv;
		return this;
	}

	/**
	 * AlgolEncrypterインスタンスを構築する。
	 *
	 * @return AlgolEncrypter
	 * @throws AlgolEncrypterException 鍵やIVのパス未設定、または読み込み失敗時
	 */
	public AlgolEncrypter build() throws AlgolEncrypterException {
		if (secretKeyFilePath == null || secretIvFilePath == null) {
			String msg = "";
			msg += secretKeyFilePath == null ? "暗号化key未設定" : "";
			msg += secretIvFilePath == null ? "暗号化Iv未設定" : "";
			throw new AlgolEncrypterException(msg);
		}
		return new AlgolEncrypter(getSecretKey(), getIv());
	}

	/**
	 * SecretKey をファイルから読み込んで取得する。
	 *
	 * @return SecretKey
	 * @throws AlgolEncrypterException 読み込み失敗時
	 */
	private SecretKey getSecretKey() throws AlgolEncrypterException {
		try {
			return new SecretKeySpec(AlgolKeyLoaderUtil.readBytes(secretKeyFilePath), "AES");
		} catch (Exception e) {
			throw new AlgolEncrypterException(e);
		}
	}

	/**
	 * IV（初期化ベクトル）をファイルから読み込んで取得する。
	 *
	 * @return IvParameterSpec
	 * @throws AlgolEncrypterException 読み込み失敗時
	 */
	private IvParameterSpec getIv() throws AlgolEncrypterException {
		try {
			return new IvParameterSpec(AlgolKeyLoaderUtil.readBytes(secretIvFilePath));
		} catch (Exception e) {
			throw new AlgolEncrypterException(e);
		}
	}
}
