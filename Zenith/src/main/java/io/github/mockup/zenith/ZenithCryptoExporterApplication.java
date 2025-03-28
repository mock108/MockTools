package io.github.mockup.zenith;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.mockup.zenith.core.ZenithEncryptFileUtil;
import io.github.mockup.zenith.core.config.ZenithEncrypterConfig.ZenithEncrypter;
import io.github.mockup.zenith.core.exeception.ZenithInitializeEncrypterException;

@SpringBootApplication
public class ZenithCryptoExporterApplication {
	/** SecretKeyファイルPath */
	private static final String SECRET_KEY_FILEPATH = System.getProperty("user.home") + "/.zenith/secret/secret.key";
	/** ivファイルPath */
	private static final String SECRET_IV_FILEPATH = System.getProperty("user.home") + "/.zenith/secret/secret.iv";

	public static void main(String args[]) throws Exception {
//		execCreateSecretFile();
//		execEncryptStr("docker");
//		execDecryptStr("509w4JHUX7kYw5/l/+GbsA==");
	}

	@SuppressWarnings("unused")
	private static void execEncryptStr(String str) {
		try {
			ZenithEncrypter ze = new ZenithEncrypter(getSecretKey(), getIv());
			System.out.println(ze.encryptstr(str));
		} catch (ZenithInitializeEncrypterException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unused")
	private static void execDecryptStr(String str) {
		try {
			ZenithEncrypter ze = new ZenithEncrypter(getSecretKey(), getIv());
			System.out.println(ze.decrypt(str));
		} catch (ZenithInitializeEncrypterException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unused")
	private static void execCreateSecretFile() {
		// IV(暗号化時のスタートブロック用の初期値）
		byte[] iv = generateRandomAlphanumericString(16).getBytes();
		// 暗号解読キー
		byte[] key = generateRandomAlphanumericString(32).getBytes();
		File secretkeyFile = new File(SECRET_KEY_FILEPATH);
		File ivFile = new File(SECRET_IV_FILEPATH);
		if (secretkeyFile.exists()) {
			// OLDフォルダ作成
			File oldFolder = Path.of(secretkeyFile.getParentFile().getPath(), "old").toFile();
			if (!oldFolder.exists()) {
				oldFolder.mkdir();
			}
			try {
				Files.copy(secretkeyFile.toPath(),
						Path.of(oldFolder.getPath(),
								LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
										+ secretkeyFile.getName()));
				Files.copy(ivFile.toPath(),
						Path.of(oldFolder.getPath(),
								LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
										+ ivFile.getName()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		// ファイル書き込み処理
		try {
			ZenithEncryptFileUtil.writeBytes(key, SECRET_KEY_FILEPATH);
			ZenithEncryptFileUtil.writeBytes(iv, SECRET_IV_FILEPATH);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("ivファイル保存場所=" + ivFile.toString());
		System.out.println("secret.iv=" + Base64.getEncoder().encode(iv));
		System.out.println("keyファイル保存場所=" + secretkeyFile.toString());
		System.out.println("secret.key=" + new String(key));
		try {
			ZenithEncrypter ze = new ZenithEncrypter(getSecretKey(), getIv());
			byte[] encryptTest = ze.encrypt("test");
			System.out.println("encryptTest=" + encryptTest);
			System.out.println("decryptTest=" + ze.decrypt(encryptTest));
		} catch (ZenithInitializeEncrypterException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ランダム文字列
	 * @param length
	 * @return
	 */
	private static String generateRandomAlphanumericString(int length) {
		// 使用可能な文字セット
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);

		// ランダム文字列を構築
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}

	/**
	 * ユーザホームからSecretKeyを取得する。
	 * @return SecretKey
	 * @throws ZenithInitializeEncrypterException Zenith暗号化初期処理例外
	 */
	private static SecretKey getSecretKey() throws ZenithInitializeEncrypterException {
		try {
			return new SecretKeySpec(ZenithEncryptFileUtil.readBytes(SECRET_KEY_FILEPATH), "AES");
		} catch (Exception e) {
			throw new ZenithInitializeEncrypterException(e);
		}
	}

	/**
	 * ユーザホームからIvParameterSpecを取得する。
	 * @return IvParameterSpec
	 * @throws ZenithInitializeEncrypterException Zenith暗号化初期処理例外
	 */
	private static IvParameterSpec getIv() throws ZenithInitializeEncrypterException {
		try {
			return new IvParameterSpec(ZenithEncryptFileUtil.readBytes(SECRET_IV_FILEPATH));
		} catch (Exception e) {
			throw new ZenithInitializeEncrypterException(e);
		}
	}
}
