package io.github.mockup.algol.util.generator;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RsaKeyPairGenerator {

	public static RsaKey generate() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);
			KeyPair keyPair = keyGen.generateKeyPair();
			return new RsaKey(keyPair.getPrivate(), keyPair.getPublic());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("RSA鍵ペアの生成に失敗しました", e);
		}
	}

	public static class RsaKey {
		private final PrivateKey privateKey;
		private final PublicKey publicKey;

		public RsaKey(PrivateKey privateKey, PublicKey publicKey) {
			this.privateKey = privateKey;
			this.publicKey = publicKey;
		}

		public CharSequence privateKey() {
			return toPem("PRIVATE KEY", privateKey.getEncoded());
		}

		public CharSequence publicKey() {
			return toPem("PUBLIC KEY", publicKey.getEncoded());
		}

		private static String toPem(String type, byte[] encoded) {
			String base64 = Base64.getEncoder().encodeToString(encoded);
			StringBuilder sb = new StringBuilder();
			sb.append("-----BEGIN ").append(type).append("-----\n");
			for (int i = 0; i < base64.length(); i += 64) {
				sb.append(base64, i, Math.min(i + 64, base64.length())).append("\n");
			}
			sb.append("-----END ").append(type).append("-----\n");
			return sb.toString();
		}
	}
}
