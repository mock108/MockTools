package io.github.mockup.algol;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import io.github.mockup.algol.exception.AlgolEncrypterException;

public class AlgolEncrypter {
	/** 暗号化アルゴリズム */
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	/** シークレットキー */
	private final SecretKey secretKey;
	/**  暗号化時のスタートブロック */
	private final IvParameterSpec iv;

	public AlgolEncrypter(SecretKey secretKey, IvParameterSpec iv) throws AlgolEncrypterException {
		this.secretKey = secretKey;
		this.iv = iv;
	}

	/**
	 * テキスト暗号化
	 * @param plainText プレーンテキスト
	 * @return 暗号化文字列
	 * @throws PolarisEncrypterException 暗号化処理例外
	 */
	public String encryptstr(String plainText) throws AlgolEncrypterException {
		if (plainText == null || plainText.trim().length() == 0) {
			return null;
		}
		return new String(Base64.getEncoder().encode(encrypt(plainText)));
	}

	/**
	 * テキスト暗号化
	 * @param plainText プレーンテキスト
	 * @return 暗号化Byte配列
	 * @throws PolarisEncrypterException 暗号化処理例外
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
			throw new AlgolEncrypterException("暗号化のデコードに失敗しました。", e);
		}
	}

	/**
	 * Byte配列復号化
	 * @param cryprtArgs 暗号化Byte配列
	 * @return 復号後文字列
	 * @throws PolarisEncrypterException 暗号化処理例外
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
			throw new AlgolEncrypterException("暗号化のデコードに失敗しました。", e);
		}
	}

	/**
	 * 文字列復号化
	 * @param cryprtText 暗号化文字列
	 * @return 復号後文字列
	 * @throws PolarisEncrypterException
	 */
	public String decrypt(String cryprtText) throws AlgolEncrypterException {
		if (cryprtText == null || cryprtText.trim().length() == 0) {
			return null;
		}
		return decrypt(Base64.getDecoder().decode(cryprtText));
	}
}
