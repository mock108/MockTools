package io.github.mockup.zenith.core.config;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.mockup.zenith.core.ZenithEncryptFileUtil;
import io.github.mockup.zenith.core.exeception.ZenithEncrypterException;
import io.github.mockup.zenith.core.exeception.ZenithInitializeEncrypterException;

/**
 * Zenith標準暗号化設定
 * @author mockup
 */
@Configuration
public class ZenithEncrypterConfig {
	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(ZenithEncrypterConfig.class);
	/** 暗号化アルゴリズム */
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	/** SecretKeyファイルPath */
	private final String SECRET_KEY_FILEPATH;
	/** ivファイルPath */
	private final String SECRET_IV_FILEPATH;

	public ZenithEncrypterConfig(
			@Value("${zenith.encrypt.key}") String encryptKey, 
			@Value("${zenith.encrypt.vi}") String encryptVi) {
		SECRET_KEY_FILEPATH = System.getProperty("user.home") + encryptKey;
		SECRET_IV_FILEPATH = System.getProperty("user.home") + encryptVi;
	}
	
	/** ZenithEncrypter定義 */
	@Bean
	ZenithEncrypter zenithEncrypter() throws ZenithInitializeEncrypterException {
		return new ZenithEncrypter(getSecretKey(), getIv());
	}

	/**
	 * ユーザホームからSecretKeyを取得する。
	 * @return SecretKey
	 * @throws ZenithInitializeEncrypterException Zenith暗号化初期処理例外
	 */
	private SecretKey getSecretKey() throws ZenithInitializeEncrypterException {
		try {
			return new SecretKeySpec(ZenithEncryptFileUtil.readBytes(SECRET_KEY_FILEPATH), "AES");
		} catch (Exception e) {
			log.error("暗号化初期処理のsecretkeyの取得に失敗しました。({})", SECRET_KEY_FILEPATH);
			throw new ZenithInitializeEncrypterException(e);
		}
	}

	/**
	 * ユーザホームからIvParameterSpecを取得する。
	 * @return IvParameterSpec
	 * @throws ZenithInitializeEncrypterException Zenith暗号化初期処理例外
	 */
	private IvParameterSpec getIv() throws ZenithInitializeEncrypterException {
		try {
			return new IvParameterSpec(ZenithEncryptFileUtil.readBytes(SECRET_IV_FILEPATH));
		} catch (Exception e) {
			log.error("暗号化初期処理のivの取得に失敗しました。({})", SECRET_IV_FILEPATH);
			throw new ZenithInitializeEncrypterException(e);
		}
	}

	/**
	 * Zenith標準暗号化
	* @author mockup
	 */
	public static class ZenithEncrypter {
		/** シークレットキー */
		private final SecretKey secretKey;
		/**  暗号化時のスタートブロック */
		private final IvParameterSpec iv;

		public ZenithEncrypter(SecretKey secretKey, IvParameterSpec iv) throws ZenithInitializeEncrypterException {
			this.secretKey = secretKey;
			this.iv = iv;
		}

		/**
		 * テキスト暗号化
		 * @param plainText プレーンテキスト
		 * @return 暗号化文字列
		 * @throws ZenithEncrypterException 暗号化処理例外
		 */
		public String encryptstr(String plainText) throws ZenithEncrypterException {
			if (plainText == null || plainText.trim().length() == 0) {
				return null;
			}
			return new String(Base64.getEncoder().encode(encrypt(plainText)));
		}

		/**
		 * テキスト暗号化
		 * @param plainText プレーンテキスト
		 * @return 暗号化Byte配列
		 * @throws ZenithEncrypterException 暗号化処理例外
		 */
		public byte[] encrypt(String plainText) throws ZenithEncrypterException {
			if (plainText == null || plainText.trim().length() == 0) {
				return null;
			}
			try {
				Cipher cipher = Cipher.getInstance(ALGORITHM);
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
				return cipher.doFinal(plainText.getBytes());
			} catch (Exception e) {
				throw new ZenithEncrypterException("暗号化のデコードに失敗しました。", e);
			}
		}

		/**
		 * Byte配列復号化
		 * @param cryprtArgs 暗号化Byte配列
		 * @return 復号後文字列
		 * @throws ZenithEncrypterException 暗号化処理例外
		 */
		public String decrypt(byte[] cryprtArgs) throws ZenithEncrypterException {
			if (cryprtArgs == null || cryprtArgs.length == 0) {
				return null;
			}
			try {
				Cipher cipher = Cipher.getInstance(ALGORITHM);
				cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
				return new String(cipher.doFinal(cryprtArgs));
			} catch (Exception e) {
				throw new ZenithEncrypterException("暗号化のデコードに失敗しました。", e);
			}
		}

		/**
		 * 文字列復号化
		 * @param cryprtText 暗号化文字列
		 * @return 復号後文字列
		 * @throws ZenithEncrypterException
		 */
		public String decrypt(String cryprtText) throws ZenithEncrypterException {
			if (cryprtText == null || cryprtText.trim().length() == 0) {
				return null;
			}
			return decrypt(Base64.getDecoder().decode(cryprtText));
		}
	}
}
