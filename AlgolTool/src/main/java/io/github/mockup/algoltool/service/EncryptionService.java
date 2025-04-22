package io.github.mockup.algoltool.service;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.github.mockup.algol.encrypter.AlgolEncrypter;
import io.github.mockup.algol.encrypter.AlgolEncrypterBuilder;
import io.github.mockup.algoltool.common.config.AlgolConfig;
import lombok.RequiredArgsConstructor;

/**
 * {@code EncryptionService} は、AES暗号化および復号を行うサービスクラスです。
 * 鍵情報は {@link AlgolConfig} から読み取り、{@link AlgolEncrypter} を通じて実行します。
 */
@Service
@RequiredArgsConstructor
public class EncryptionService {
	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(EncryptionService.class);

	private final AlgolConfig config;

	/**
	 * 指定したプレーンテキストをAESで暗号化し、Base64形式の文字列として返却します。
	 *
	 * @param plainText 暗号化対象のプレーンテキスト
	 * @return 暗号化されたBase64文字列
	 */
	public String encryptText(String plainText) {
		AlgolEncrypter encrypter = getEncrypter();
		return encrypter.encryptstr(plainText);
	}

	/**
	 * Base64形式の暗号文字列をAESで復号し、元の文字列として返却します。
	 *
	 * @param plainText 復号対象の暗号文字列（Base64形式）
	 * @return 復号された元のプレーンテキスト
	 */
	public String decryptText(String plainText) {
		log.debug("参照Dir: {}", config.getSecretDir());
		AlgolEncrypter encrypter = getEncrypter();
		return encrypter.decrypt(plainText);
	}

	/**
	 * 暗号器（{@link AlgolEncrypter}）を初期化します。
	 * 設定された秘密鍵とIVファイルをもとに構築されます。
	 *
	 * @return {@link AlgolEncrypter} インスタンス
	 */
	private AlgolEncrypter getEncrypter() {
		return AlgolEncrypterBuilder.builder()
				.key(Path.of(config.getSecretDir(), "secret.key").toString())
				.iv(Path.of(config.getSecretDir(), "secret.iv").toString())
				.build();
	}
}
