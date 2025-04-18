package io.github.mockup.algoltool.service;

import java.nio.file.Path;

import org.springframework.stereotype.Service;

import io.github.mockup.algol.encrypter.AlgolEncrypter;
import io.github.mockup.algol.encrypter.AlgolEncrypterBuilder;
import io.github.mockup.algoltool.common.config.AlgolConfig;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncryptionService {
	private final AlgolConfig config;

	public String encryptText(String plainText) {
		AlgolEncrypter encrypter = getEncrypter();
		String encrypted = encrypter.encryptstr(plainText);
		return encrypted;
	}

	public String decryptText(String plainText) {
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