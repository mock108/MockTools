package io.github.mockup.algol.util.generator;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AesKeyGenerator {

	private static final int AES_KEY_SIZE = 128; // 128bit AES鍵
	private static final int IV_LENGTH = 16;     // AESのブロックサイズは常に16バイト

	/**
	 * AES鍵（SecretKey）をBase64文字列で返す
	 */
	public static CharSequence generateKey() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(AES_KEY_SIZE);
			SecretKey key = keyGen.generateKey();
			return Base64.getEncoder().encodeToString(key.getEncoded());
		} catch (Exception e) {
			throw new RuntimeException("AES鍵の生成に失敗しました", e);
		}
	}

	/**
	 * 初期化ベクトル（IV）をBase64文字列で返す
	 */
	public static CharSequence generateIv() {
		byte[] iv = new byte[IV_LENGTH];
		new SecureRandom().nextBytes(iv);
		return Base64.getEncoder().encodeToString(iv);
	}
}
