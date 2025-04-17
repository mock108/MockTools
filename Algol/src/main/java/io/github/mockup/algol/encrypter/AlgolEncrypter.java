package io.github.mockup.algol.encrypter;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import io.github.mockup.algol.exception.AlgolEncrypterException;

/**
 * AlgolEncrypter は、AES 暗号化・復号を行うユーティリティクラスです。
 * 暗号モードには「AES/CBC/PKCS5Padding」を使用し、指定された SecretKey と IV を使って処理します。
 */
public class AlgolEncrypter {

	/** 暗号化アルゴリズム */
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

	/** シークレットキー（対称暗号用） */
	private final SecretKey secretKey;

	/** 初期化ベクトル（IV） */
	private final IvParameterSpec iv;

	/**
	 * コンストラクタ
	 *
	 * @param secretKey AES暗号化用のSecretKey
	 * @param iv 初期化ベクトル（IvParameterSpec）
	 * @throws AlgolEncrypterException 初期化に失敗した場合
	 */
	public AlgolEncrypter(SecretKey secretKey, IvParameterSpec iv) throws AlgolEncrypterException {
		this.secretKey = secretKey;
		this.iv = iv;
	}

	/**
	 * 文字列をAES暗号化して Base64エンコードされた文字列として返却します。
	 *
	 * @param plainText 暗号化対象のプレーンテキスト
	 * @return Base64エンコードされた暗号文字列（null可）
	 * @throws AlgolEncrypterException 暗号化に失敗した場合
	 */
	public String encryptstr(String plainText) throws AlgolEncrypterException {
		if (plainText == null || plainText.trim().length() == 0) {
			return null;
		}
		return Base64.getEncoder().encodeToString(encrypt(plainText));
	}

	/**
	 * 文字列をAES暗号化して、暗号化されたバイト配列を返却します。
	 *
	 * @param plainText 暗号化対象のプレーンテキスト
	 * @return 暗号化されたバイト配列（null可）
	 * @throws AlgolEncrypterException 暗号化に失敗した場合
	 */
	public byte[] encrypt(String plainText) throws AlgolEncrypterException {
		if (plainText == null || plainText.trim().length() == 0) {
			return null;
		}
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			return cipher.doFinal(plainText.getBytes());
		} catch (Exception e) {
			throw new AlgolEncrypterException("暗号化の実行に失敗しました。", e);
		}
	}

	/**
	 * 暗号化されたバイト配列をAES復号して、復号された文字列を返します。
	 *
	 * @param cryprtArgs 暗号化されたバイト配列
	 * @return 復号された文字列（null可）
	 * @throws AlgolEncrypterException 復号に失敗した場合
	 */
	public String decrypt(byte[] cryprtArgs) throws AlgolEncrypterException {
		if (cryprtArgs == null || cryprtArgs.length == 0) {
			return null;
		}
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			return new String(cipher.doFinal(cryprtArgs));
		} catch (Exception e) {
			throw new AlgolEncrypterException("復号に失敗しました。", e);
		}
	}

	/**
	 * Base64文字列としてエンコードされた暗号文字列を復号し、元の文字列に戻します。
	 *
	 * @param cryprtText Base64エンコードされた暗号文字列
	 * @return 復号された文字列（null可）
	 * @throws AlgolEncrypterException 復号に失敗した場合
	 */
	public String decrypt(String cryprtText) throws AlgolEncrypterException {
		if (cryprtText == null || cryprtText.trim().length() == 0) {
			return null;
		}
		return decrypt(Base64.getDecoder().decode(cryprtText));
	}
}
