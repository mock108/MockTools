package io.github.mockup.algol.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import io.github.mockup.algol.exception.AlgolJwtException;

/**
 * Algol暗号化ファイルUtil
 */
public class AlgolKeyLoaderUtil {
	/**
	 * ファイル読み取り処理
	 * @param path 読み取りファイルPath
	 * @return ファイルバイナリ
	 * @throws IOException 読み取り例外
	 */
	public static byte[] readBytes(String path) throws IOException {
		System.out.println(path);
		try (InputStream i = new FileInputStream(path)) {
			return Base64.getDecoder().decode(i.readAllBytes());
		}
	}

	/**
	 * 公開鍵ロード
	 * @param pemPath 暗号鍵Path
	 * @return 公開鍵
	 * @throws AlgolJwtException 公開鍵読み込み例外
	 */
	public static PublicKey loadPublicKeyFromPem(Path pemPath) throws AlgolJwtException {
		try {
			String key = Files.readString(pemPath)
					.replace("-----BEGIN PUBLIC KEY-----", "")
					.replace("-----END PUBLIC KEY-----", "")
					.replaceAll("\\s", "");

			byte[] decoded = Base64.getDecoder().decode(key);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
			return KeyFactory.getInstance("RSA").generatePublic(spec);
		} catch (Exception e) {
			throw new AlgolJwtException("公開鍵読み込み例外", e);
		}
	}

	/**
	 * 秘密鍵ロード
	 * @param pemPath 暗号鍵Path
	 * @return 秘密鍵
	 * @throws AlgolJwtException 秘密鍵読み込み例外
	 */
	public static PrivateKey loadPrivateKeyFromPem(Path pemPath) throws AlgolJwtException {
		try {
			String key = Files.readString(pemPath)
					.replace("-----BEGIN PRIVATE KEY-----", "")
					.replace("-----END PRIVATE KEY-----", "")
					.replaceAll("\\s", "");

			byte[] decoded = Base64.getDecoder().decode(key);
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
			return KeyFactory.getInstance("RSA").generatePrivate(spec);
		} catch (Exception e) {
			throw new AlgolJwtException("秘密鍵読み込み例外", e);
		}
	}
}
