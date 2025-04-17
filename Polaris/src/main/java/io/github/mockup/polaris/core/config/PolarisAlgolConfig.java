package io.github.mockup.polaris.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.mockup.algol.encrypter.AlgolEncrypter;
import io.github.mockup.algol.encrypter.AlgolEncrypterBuilder;

/**
 * Polaris標準暗号化設定
 * @author mockup
 */
@Configuration
public class PolarisAlgolConfig {
	/** AlgolEncrypter定義 */
	@Bean
	AlgolEncrypter algolEncrypter() {
		return AlgolEncrypterBuilder.defaultAlgolEncrypter();
	}
}
