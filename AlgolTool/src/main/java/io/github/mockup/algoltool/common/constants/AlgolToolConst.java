package io.github.mockup.algoltool.common.constants;

/**
 * {@code AlgolToolConst} は、AlgolTool における暗号鍵・JWT鍵ファイル名などの定数を定義するユーティリティクラスです。
 */
public class AlgolToolConst {

	/**
	 * 暗号化に関する定数（AES鍵・IV）
	 */
	public static class Encrypt {
		/** AES秘密鍵ファイル名 */
		public static final String KEY_FILENAME = "secret.key";
		/** AES初期化ベクトル（IV）ファイル名 */
		public static final String IV_FILENAME = "secret.iv";
	}

	/**
	 * JWTに関する定数（RSA鍵ファイル）
	 */
	public static class JWT {
		/** 公開鍵ファイル名（PEM形式） */
		public static final String PUBLIC_FILENAME = "public.pem";
		/** 秘密鍵ファイル名（PEM形式） */
		public static final String PRIVATE_FILENAME = "private.pem";
	}
}
