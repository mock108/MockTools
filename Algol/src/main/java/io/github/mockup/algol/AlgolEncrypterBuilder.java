package io.github.mockup.algol;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.github.mockup.algol.exception.AlgolEncrypterException;
import io.github.mockup.algol.util.AlgolEncryptFileUtil;

public class AlgolEncrypterBuilder {
	/** SecretKeyファイルPath */
	private String secretKeyFilePath;
	/** ivファイルPath */
	private String secretIvFilePath;
	/** 暗号化KeyデフォルトPath */
	private static final String DEFAULT_ENCRYPT_KEY = "/.algol/secret/secret.key";
	/** 暗号化IVデフォルトPath */
	private static final String DEFAULT_ENCRYPT_IV = "/.algol/secret/secret.iv";

	public static AlgolEncrypter defaultAlgolEncrypter() {
		return builder()
				.key(System.getProperty("user.home") + DEFAULT_ENCRYPT_KEY)
				.iv(System.getProperty("user.home") + DEFAULT_ENCRYPT_IV)
				.build();
	}

	public static AlgolEncrypterBuilder builder() {
		return new AlgolEncrypterBuilder();
	}

	public AlgolEncrypterBuilder key(String key) {
		this.secretIvFilePath = key;
		return this;
	}

	public AlgolEncrypterBuilder iv(String iv) {
		this.secretIvFilePath = iv;
		return this;
	}

	public AlgolEncrypter build() throws AlgolEncrypterException {
		if (secretKeyFilePath == null || secretIvFilePath == null) {
			String msg = "";
			msg += secretKeyFilePath == null ? "暗号化key未設定" : "";
			msg += secretIvFilePath == null ? "暗号化Iv未設定" : "";
			new AlgolEncrypterException(msg);
		}
		return new AlgolEncrypter(getSecretKey(), getIv());
	}

	/**
	 * ユーザホームからSecretKeyを取得する。
	 * @return SecretKey
	 * @throws PolarisInitializeEncrypterException Polaris暗号化初期処理例外
	 */
	private SecretKey getSecretKey() throws AlgolEncrypterException {
		try {
			return new SecretKeySpec(AlgolEncryptFileUtil.readBytes(secretKeyFilePath), "AES");
		} catch (Exception e) {
			throw new AlgolEncrypterException(e);
		}
	}

	/**
	 * ユーザホームからIvParameterSpecを取得する。
	 * @return IvParameterSpec
	 * @throws PolarisInitializeEncrypterException Polaris暗号化初期処理例外
	 */
	private IvParameterSpec getIv() throws AlgolEncrypterException {
		try {
			return new IvParameterSpec(AlgolEncryptFileUtil.readBytes(secretIvFilePath));
		} catch (Exception e) {
			throw new AlgolEncrypterException(e);
		}
	}
}
