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

@Service
@RequiredArgsConstructor
public class KeyGenerationService {
	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(KeyGenerationService.class);

	private final AlgolConfig config;

	public void generateAllKeys() throws IOException {
		log.debug("参照Dir: {}", config.getSecretDir());

		OpenOption[] oos = { StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING };
		createSecretDirectory();
		if (Path.of(config.getPrivate()).toFile().exists() || Path.of(config.getPublic()).toFile().exists()) {
			log.info("既にRSA鍵が存在します。");
			if (Path.of(config.getPublic()).toFile().exists())
				log.info("public : {}", config.getPublic());
			if (Path.of(config.getPrivate()).toFile().exists())
				log.info("private : {}", config.getPrivate());
		} else {
			// RSA鍵生成
			var rsaKeyPair = RsaKeyPairGenerator.generate();
			Files.writeString(Path.of(config.getPrivate()), rsaKeyPair.privateKey(), oos);
			log.info("ファイル生成: {}", config.getPrivate());
			Files.writeString(Path.of(config.getPublic()), rsaKeyPair.publicKey(), oos);
			log.info("ファイル生成: {}", config.getPublic());
		}

		if (Path.of(config.getSecretKey()).toFile().exists() || Path.of(config.getSecretIV()).toFile().exists()) {
			log.info("既にAES鍵・IVが存在します。");
			if (Path.of(config.getSecretKey()).toFile().exists())
				log.info("AES鍵: {}", config.getSecretKey());
			if (Path.of(config.getSecretIV()).toFile().exists())
				log.info("IV: {}", config.getSecretIV());
		} else {
			// AES鍵・IV生成
			Files.writeString(Path.of(config.getSecretKey()), AesKeyGenerator.generateKey(), oos);
			log.info("ファイル生成: {}", config.getSecretKey());
			Files.writeString(Path.of(config.getSecretIV()), AesKeyGenerator.generateIv(), oos);
			log.info("ファイル生成: {}", config.getSecretIV());
		}
	}

	private void createSecretDirectory() throws IOException {
		Path secretDir = Path.of(config.getSecretDir());
		if (!Files.exists(secretDir)) {
			log.info("参照Dirが見つからないため生成します。");
			Files.createDirectories(secretDir);
		}
	}
}