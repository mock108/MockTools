package io.github.mockup.zenith.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.mockup.algol.encrypter.AlgolEncrypter;
import io.github.mockup.algol.encrypter.AlgolEncrypterBuilder;

/**
 * Zenith標準暗号化設定
 * @author mockup
 */
@Configuration
public class ZenithEncrypterConfig {
	/** algolEncrypter定義 */
	@Bean
	AlgolEncrypter algolEncrypter() {
		return AlgolEncrypterBuilder.defaultAlgolEncrypter();
	}
}
