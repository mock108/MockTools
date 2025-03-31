package io.github.mockup.zenith.core.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.mockup.zenith.core.interceptor.RestTemplateLoggingInterceptor;

@Configuration
public class RestClientConfig {

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplateBuilder()
				.additionalInterceptors(new RestTemplateLoggingInterceptor())
				.build();
	}
}