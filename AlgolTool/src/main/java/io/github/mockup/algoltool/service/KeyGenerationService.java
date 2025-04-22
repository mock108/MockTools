package io.github.mockup.algoltool.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.github.mockup.algol.util.generator.AesKeyGenerator;
import io.github.mockup.algol.util.generator.RsaKeyPairGenerator;
import io.github.mockup.algoltool.common.config.AlgolConfig;
import lombok.RequiredArgsConstructor;

/**
 * {@code KeyGenerationService} は、暗号処理に必要な RSA 鍵ペア、
 * AES 鍵および IV（初期化ベクトル）を生成・保存するサービスクラスです。
 *
 * 鍵は {@link AlgolConfig} で指定されたパスに保存されます。
 */
@Service
@RequiredArgsConstructor
public class KeyGenerationService {

	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(KeyGenerationService.class);

	private final AlgolConfig config;

	/**
	 * RSA鍵ペア、AES鍵、IV をファイルとして生成・保存します。
	 * すでに存在する場合はスキップされ、ログにパスを出力します。
	 *
	 * @throws IOException ファイル操作に失敗した場合
	 */
	public void generateAllKeys() throws IOException {
		log.debug("参照Dir: {}", config.getSecretDir());

		OpenOption[] oos = { StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING };
		createSecretDirectory();

		// RSA鍵生成・保存
		if (Path.of(config.getPrivate()).toFile().exists() || Path.of(config.getPublic()).toFile().exists()) {
			log.info("既にRSA鍵が存在します。");
			if (Path.of(config.getPublic()).toFile().exists())
				log.info("public : {}", config.getPublic());
			if (Path.of(config.getPrivate()).toFile().exists())
				log.info("private : {}", config.getPrivate());
		} else {
			var rsaKeyPair = RsaKeyPairGenerator.generate();
			Files.writeString(Path.of(config.getPrivate()), rsaKeyPair.privateKey(), oos);
			log.info("ファイル生成: {}", config.getPrivate());
			Files.writeString(Path.of(config.getPublic()), rsaKeyPair.publicKey(), oos);
			log.info("ファイル生成: {}", config.getPublic());
		}

		// AES鍵・IV生成・保存
		if (Path.of(config.getSecretKey()).toFile().exists() || Path.of(config.getSecretIV()).toFile().exists()) {
			log.info("既にAES鍵・IVが存在します。");
			if (Path.of(config.getSecretKey()).toFile().exists())
				log.info("AES鍵: {}", config.getSecretKey());
			if (Path.of(config.getSecretIV()).toFile().exists())
				log.info("IV: {}", config.getSecretIV());
		} else {
			Files.writeString(Path.of(config.getSecretKey()), AesKeyGenerator.generateKey(), oos);
			log.info("ファイル生成: {}", config.getSecretKey());
			Files.writeString(Path.of(config.getSecretIV()), AesKeyGenerator.generateIv(), oos);
			log.info("ファイル生成: {}", config.getSecretIV());
		}
	}

	/**
	 * 鍵格納ディレクトリが存在しない場合は作成します。
	 *
	 * @throws IOException ディレクトリ作成に失敗した場合
	 */
	private void createSecretDirectory() throws IOException {
		Path secretDir = Path.of(config.getSecretDir());
		if (!Files.exists(secretDir)) {
			log.info("参照Dirが見つからないため生成します。");
			Files.createDirectories(secretDir);
		}
	}
}
