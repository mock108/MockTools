package io.github.mockup.algoltool.service;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.github.mockup.algol.encrypter.AlgolEncrypter;
import io.github.mockup.algol.encrypter.AlgolEncrypterBuilder;
import io.github.mockup.algoltool.common.config.AlgolConfig;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncryptionService {
	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(EncryptionService.class);

	private final AlgolConfig config;

	public String encryptText(String plainText) {
		AlgolEncrypter encrypter = getEncrypter();
		String encrypted = encrypter.encryptstr(plainText);
		return encrypted;
	}

	public String decryptText(String plainText) {
		log.debug("参照Dir: {}", config.getSecretDir());
		AlgolEncrypter encrypter = getEncrypter();
		String decrypted = encrypter.decrypt(plainText);
		return decrypted;
	}

	private AlgolEncrypter getEncrypter() {
        return AlgolEncrypterBuilder.builder()
                .key(Path.of(config.getSecretDir(), "secret.key").toString())
                .iv(Path.of(config.getSecretDir(), "secret.iv").toString())
                .build();
	}
}