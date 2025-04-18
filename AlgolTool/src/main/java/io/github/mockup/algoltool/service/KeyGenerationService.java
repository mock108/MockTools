package io.github.mockup.algoltool.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.springframework.stereotype.Service;

import io.github.mockup.algol.util.generator.AesKeyGenerator;
import io.github.mockup.algol.util.generator.RsaKeyPairGenerator;
import io.github.mockup.algoltool.common.config.AlgolConfig;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class KeyGenerationService {

	private final AlgolConfig config;

	public void generateAllKeys() throws IOException {
		createSecretDirectory();

		// RSA鍵生成
		var rsaKeyPair = RsaKeyPairGenerator.generate();
		Files.writeString(Path.of(config.getPrivate()), rsaKeyPair.privateKey(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		Files.writeString(Path.of(config.getPublic()), rsaKeyPair.publicKey(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

		// AES鍵・IV生成
		Files.writeString(Path.of(config.getSecretKey()), AesKeyGenerator.generateKey(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		Files.writeString(Path.of(config.getSecretIV()), AesKeyGenerator.generateIv(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}

	private void createSecretDirectory() throws IOException {
		Path secretDir = Path.of(config.getSecretDir());
		if (!Files.exists(secretDir)) {
			Files.createDirectories(secretDir);
		}
	}
}