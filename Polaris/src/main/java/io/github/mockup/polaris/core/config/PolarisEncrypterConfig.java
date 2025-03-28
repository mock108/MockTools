package io.github.mockup.polaris.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.mockup.algol.AlgolEncrypter;
import io.github.mockup.algol.AlgolEncrypterBuilder;

/**
 * Polaris標準暗号化設定
 * @author mockup
 */
@Configuration
public class PolarisEncrypterConfig {
	/** AlgolEncrypter定義 */
	@Bean
	AlgolEncrypter algolEncrypter() {
		return AlgolEncrypterBuilder.defaultAlgolEncrypter();
	}
}
